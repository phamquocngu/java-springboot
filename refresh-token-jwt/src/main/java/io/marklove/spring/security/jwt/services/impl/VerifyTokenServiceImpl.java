package io.marklove.spring.security.jwt.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.marklove.spring.security.jwt.configurations.ResetPasswordProperties;
import io.marklove.spring.security.jwt.configurations.SignupProperties;
import io.marklove.spring.security.jwt.constants.MessageCode;
import io.marklove.spring.security.jwt.exceptions.BusinessException;
import io.marklove.spring.security.jwt.payloads.requests.ResetPasswordRequest;
import io.marklove.spring.security.jwt.payloads.requests.SignupRequest;
import io.marklove.spring.security.jwt.persistences.entities.Role;
import io.marklove.spring.security.jwt.persistences.entities.User;
import io.marklove.spring.security.jwt.persistences.entities.VerifyToken;
import io.marklove.spring.security.jwt.persistences.repository.RoleRepository;
import io.marklove.spring.security.jwt.persistences.repository.UserRepository;
import io.marklove.spring.security.jwt.persistences.repository.VerifyTokenRepository;
import io.marklove.spring.security.jwt.services.VerifyTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ngupq
 */
@Service
public class VerifyTokenServiceImpl implements VerifyTokenService {
    private static final Logger logger = LoggerFactory.getLogger(VerifyTokenServiceImpl.class);

    @Autowired
    private VerifyTokenRepository verifyTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SignupProperties signupProperties;
    @Autowired
    private ResetPasswordProperties rsPasswordProp;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String register(SignupRequest signUpRequest) {
        // Check username, email existed
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BusinessException(MessageCode.Error.code5000, null);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BusinessException(MessageCode.Error.code5001, null);
        }

        User user = objectMapper.convertValue(signUpRequest, User.class);

        Set<Role> roles = signupProperties.getDefaultRoles().stream()
                .map(r -> roleRepository.findByName(r)
                        .orElseThrow(() -> new BusinessException(MessageCode.Error.code5002, null)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        userRepository.save(user);
        // Save register
        VerifyToken verifyToken = new VerifyToken(user);
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(signupProperties.getExpirationHours());
        verifyToken.setExpired(localDateTime);
        verifyTokenRepository.save(verifyToken);
        logger.debug("register success email: " + user.getEmail());
        // Send email

        return verifyToken.getVerifyToken();
    }

    @Override
    public boolean verifyRegister(String token) {

        VerifyToken verifyToken = verifyTokenRepository.findByVerifyToken(token)
                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5004, null));

        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(verifyToken.getExpired())) {
            verifyTokenRepository.delete(verifyToken);
            logger.error("verifyRegister token expired: " + token);
            throw new BusinessException(MessageCode.Error.code5005, null);
        }

        User user = verifyToken.getUser();
        user.setEnable(true);
        userRepository.save(user);
        //Delete token
        verifyTokenRepository.delete(verifyToken);
        logger.debug("active success username: " + user.getUsername());

        return true;
    }

    @Override
    public String resetPassword(String email) {
        User user = userRepository.findByEmailAndEnable(email, true)
                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5001, null));
        //Save verify
        VerifyToken verifyToken = new VerifyToken(user);
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(rsPasswordProp.getExpirationHours());
        verifyToken.setExpired(localDateTime);
        verifyTokenRepository.save(verifyToken);
        logger.debug("reset password save verify token success");
        //Send email

        return verifyToken.getVerifyToken();
    }

    @Override
    public boolean verifyResetPassword(ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();
        String oldPass = resetPasswordRequest.getOldPassword();
        String newPass = resetPasswordRequest.getNewPassword();

        VerifyToken verifyToken = verifyTokenRepository.findByVerifyToken(token)
                .orElseThrow(() -> new BusinessException(MessageCode.Error.code5006, null));

        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(verifyToken.getExpired())) {
            verifyTokenRepository.delete(verifyToken);
            logger.error("token reset password expired: " + token);
            throw new BusinessException(MessageCode.Error.code5007, null);
        }

        User user = verifyToken.getUser();
        if (!user.getPassword().equals(encoder.encode(oldPass))) {
            logger.error("old password invalid: ");
            throw new BusinessException(MessageCode.Error.code5007, null);
        }

        user.setPassword(encoder.encode(newPass));
        userRepository.save(user);
        //Delete token
        verifyTokenRepository.delete(verifyToken);
        logger.debug("reset password success");
        //Send email

        return true;
    }
}