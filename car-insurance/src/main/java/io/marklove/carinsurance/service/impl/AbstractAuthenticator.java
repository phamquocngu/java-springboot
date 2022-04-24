package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.dto.SNSInfoDto;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.repository.RefreshTokenRepository;
import io.marklove.carinsurance.security.AuthenticationService;
import io.marklove.carinsurance.security.JwtResponse;
import io.marklove.carinsurance.security.JwtUtils;
import io.marklove.carinsurance.service.SNSAuthenticator;
import io.marklove.carinsurance.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Log4j2
@Component
public abstract class AbstractAuthenticator implements SNSAuthenticator {

    private final JwtUtils jwtUtils;

    private final RefreshTokenRepository refTokenRepo;

    private final UserService userService;
    private final AuthenticationService authService;

    private final MemberRepository memberRepo;

    public AbstractAuthenticator(JwtUtils jwtUtils,
                                 RefreshTokenRepository refTokenRepo,
                                 UserService userService,
                                 AuthenticationService authService,
                                 MemberRepository memberRepo) {
        this.jwtUtils = jwtUtils;
        this.refTokenRepo = refTokenRepo;
        this.userService = userService;
        this.authService = authService;
        this.memberRepo = memberRepo;
    }

    @Value("${jwt.refreshToken.duration}")
    private long REFRESH_TOKEN_DURATION;

    abstract SNSInfoDto verifyToken(String token);

    @Override
    @Transactional
    public JwtResponse authenticate(String token) {
        return authenticate(token, "");
    }

    @Override
    @Transactional
    public JwtResponse authenticate(String token, String userName) {
        var snsInfo = verifyToken(token);
        var userInfo = memberRepo.getUserIdBySnsIdAndSnsType(snsInfo.getId(), snsInfo.getType());

        if (userInfo.isEmpty()) {
            if (!StringUtils.isEmpty(userName) && StringUtils.isEmpty(snsInfo.getName())) {
                snsInfo.setName(userName);
            }
            var user = userService.createUserWithSNSInfo(snsInfo.getName(),
                                                         snsInfo.getId(),
                                                         snsInfo.getEmail(),
                                                         snsInfo.getMobile(),
                                                         snsInfo.getType(),
                                                         snsInfo.getToken());
            userInfo = Optional.of(user.getId());
        }

        return userInfo.map(authService::processJwtResponse)
                       .orElseThrow(() -> new CommonException(ErrorCode.AUTH_INVALID_TOKEN));
    }

    /*@Override
    @Transactional
    public JwtResponse mockAuthenticate(String userIdStr) {
        int userId = Integer.parseInt(userIdStr);

        Optional<User> userOptional = userRepo.findById(userId);

        if (userOptional.isEmpty()) throw new BusinessException(ErrorCode.AUTH_INVALID_TOKEN);

        Optional<Integer> userInfo = Optional.of(userOptional.get().getId());

        return userInfo.map(this::processJwtResponse)
                       .orElseThrow(() -> new BusinessException(ErrorCode.AUTH_INVALID_TOKEN));

    }*/
}
