package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.api.kakao.AccessTokenInfo;
import io.marklove.carinsurance.api.kakao.KakaoApiClient;
import io.marklove.carinsurance.api.kakao.KakaoInfo;
import io.marklove.carinsurance.api.kakao.KakaoProperties;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class KakaoAuthenticator extends AbstractAuthenticator {

    private final KakaoApiClient kakaoApiClient;

    public KakaoAuthenticator(JwtUtils jwtUtils,
                              RefreshTokenRepository refTokenRepo,
                              UserService userService,
                              AuthenticationService authService,
                              MemberRepository memberRepo,
                              KakaoApiClient kakaoApiClient) {
        super(jwtUtils, refTokenRepo, userService, authService, memberRepo);
        this.kakaoApiClient = kakaoApiClient;
    }

    @Override
    SNSInfoDto verifyToken(String token) {
        AccessTokenInfo accessTokenInfo = kakaoApiClient.getUserInfo(token)
                                                        .orElseThrow(() -> new CommonException(ErrorCode.AUTH_INVALID_TOKEN));

        if (accessTokenInfo.getExpiresInMillis() > 0) {
            var expireDate = LocalDateTime.ofEpochSecond(accessTokenInfo.getExpiresInMillis() / 1000,
                                                         0,
                                                         ZoneOffset.UTC);
            if (expireDate.isBefore(LocalDateTime.now()))
                throw new CommonException(ErrorCode.AUTH_TOKEN_EXPIRED);
        }

        var snsId = String.valueOf(accessTokenInfo.getId());

        String email = "";
        KakaoInfo kakaoInfo = accessTokenInfo.getKakao_account();
        if (kakaoInfo != null) {
            email = kakaoInfo.getEmail();
        }

        if (StringUtils.isEmpty(email)) {
            throw new CommonException(ErrorCode.EMAIL_NOT_EXIST);
        }

        String nickName = "";
        KakaoProperties kakaoProperties = accessTokenInfo.getProperties();
        if (kakaoProperties != null) {
            nickName = kakaoProperties.getNickname();
        }

        return new SNSInfoDto(snsId, nickName, email, SNSType.KAKAO, token);
    }
}
