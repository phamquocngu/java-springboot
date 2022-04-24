package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.api.naver.NaverApiClient;
import io.marklove.carinsurance.api.naver.UserInfo;
import io.marklove.carinsurance.constant.SNSType;
import io.marklove.carinsurance.dto.SNSInfoDto;
import io.marklove.carinsurance.exception.CommonException;
import io.marklove.carinsurance.exception.ErrorCode;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.repository.RefreshTokenRepository;
import io.marklove.carinsurance.security.AuthenticationService;
import io.marklove.carinsurance.security.JwtUtils;
import io.marklove.carinsurance.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NaverAuthenticator extends AbstractAuthenticator {

    private final NaverApiClient naverApiClient;

    public NaverAuthenticator(JwtUtils jwtUtils,
                              RefreshTokenRepository refTokenRepo,
                              UserService userService,
                              AuthenticationService authService,
                              MemberRepository memberRepo,
                              NaverApiClient naverApiClient) {
        super(jwtUtils, refTokenRepo, userService, authService, memberRepo);
        this.naverApiClient = naverApiClient;
    }

    @Override
    SNSInfoDto verifyToken(String token) {
        UserInfo nUsrInfo = naverApiClient.getUserInfo(token)
                                          .orElseThrow(() -> new CommonException(ErrorCode.AUTH_INVALID_TOKEN));

        var snsId = String.valueOf(nUsrInfo.getId());

        if(StringUtils.isEmpty(nUsrInfo.getEmail())) {
            throw new CommonException(ErrorCode.AUTH_WRONG_EMAIL);
        }
        String naverName = nUsrInfo.getNickname();
        if(StringUtils.isEmpty(naverName)) {
            naverName = nUsrInfo.getName();
        }
        return new SNSInfoDto(snsId, naverName, nUsrInfo.getEmail(), nUsrInfo.getMobile(), SNSType.NAVER, token);
    }
}
