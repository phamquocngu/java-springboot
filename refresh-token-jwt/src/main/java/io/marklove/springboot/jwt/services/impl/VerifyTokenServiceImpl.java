package io.marklove.springboot.jwt.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.marklove.springboot.jwt.configurations.ResetPasswordProperties;
import io.marklove.springboot.jwt.configurations.SignupProperties;
import io.marklove.springboot.jwt.constants.MessageCode;
import io.marklove.springboot.jwt.exceptions.CommonException;
import io.marklove.springboot.jwt.payloads.requests.security.VerifyResetPassReq;
import io.marklove.springboot.jwt.payloads.requests.security.SignupReq;
import io.marklove.springboot.jwt.persistences.entities.Role;
import io.marklove.springboot.jwt.persistences.entities.User;
import io.marklove.springboot.jwt.persistences.entities.VerifyToken;
import io.marklove.springboot.jwt.persistences.repository.RoleRepository;
import io.marklove.springboot.jwt.persistences.repository.UserRepository;
import io.marklove.springboot.jwt.persistences.repository.VerifyTokenRepository;
import io.marklove.springboot.jwt.services.VerifyTokenService;
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
    public String register(SignupReq signUpReq) {
        // Check username, email existed
        if (userRepository.existsByUsername(signUpReq.getUsername())) {
            throw new CommonException(MessageCode.Error.C5000, null);
        }

        if (userRepository.existsByEmail(signUpReq.getEmail())) {
            throw new CommonException(MessageCode.Error.C5001, null);
        }

        User user = objectMapper.convertValue(signUpReq, User.class);

        Set<Role> roles = signupProperties.getDefaultRoles().stream()
                .map(r -> roleRepository.findByName(r)
                        .orElseThrow(() -> new CommonException(MessageCode.Error.C5002, null)))
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
                .orElseThrow(() -> new CommonException(MessageCode.Error.C5004, null));

        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(verifyToken.getExpired())) {
            verifyTokenRepository.delete(verifyToken);
            logger.error("verifyRegister token expired: " + token);
            throw new CommonException(MessageCode.Error.C5005, null);
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
                .orElseThrow(() -> new CommonException(MessageCode.Error.C5003, null));
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
    public boolean verifyResetPassword(VerifyResetPassReq verifyResetPassReq) {
        String token = verifyResetPassReq.getToken();
        String oldPass = verifyResetPassReq.getOldPassword();
        String newPass = verifyResetPassReq.getNewPassword();

        VerifyToken verifyToken = verifyTokenRepository.findByVerifyToken(token)
                .orElseThrow(() -> new CommonException(MessageCode.Error.C5006, null));

        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(verifyToken.getExpired())) {
            verifyTokenRepository.delete(verifyToken);
            logger.error("token reset password expired: " + token);
            throw new CommonException(MessageCode.Error.C5007, null);
        }

        User user = verifyToken.getUser();
        if (!user.getPassword().equals(encoder.encode(oldPass))) {
            logger.error("old password invalid: ");
            throw new CommonException(MessageCode.Error.C5007, null);
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
