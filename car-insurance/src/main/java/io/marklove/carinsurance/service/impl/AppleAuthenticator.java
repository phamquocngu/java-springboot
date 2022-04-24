package io.marklove.carinsurance.service.impl;

import io.marklove.carinsurance.dto.SNSInfoDto;
import io.marklove.carinsurance.repository.MemberRepository;
import io.marklove.carinsurance.repository.RefreshTokenRepository;
import io.marklove.carinsurance.security.AuthenticationService;
import io.marklove.carinsurance.security.JwtUtils;
import io.marklove.carinsurance.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class AppleAuthenticator extends AbstractAuthenticator {
    public AppleAuthenticator(JwtUtils jwtUtils,
                              RefreshTokenRepository refTokenRepo,
                              UserService userService,
                              AuthenticationService authService,
                              MemberRepository memberRepo) {
        super(jwtUtils, refTokenRepo, userService, authService, memberRepo);
    }

    @Override
    SNSInfoDto verifyToken(String token) {
        return null;
    }

    /*private final IdentityTokenDecoderImpl identityTokenDecoder;


    protected AppleAuthenticator(JwtUtils jwtUtils, RefreshTokenRepository refTokenRepo, UserService userService,
                                 AppleSigningKeyResolver appleAuthKeyResolver, UserRepository userRepo, IdentityTokenDecoderImpl identityTokenDecoder) {
        super(jwtUtils, refTokenRepo, userService, userRepo);
        this.identityTokenDecoder = identityTokenDecoder;
    }

    @Override
    SNSInfoDto verifyToken(String token) {
        AppleIdentityToken appleIdentityToken = identityTokenDecoder.decode(token);
        return new SNSInfoDto(appleIdentityToken.getSubject(), "", appleIdentityToken.getEmail(), SNSType.APPLE);
    }*/
}
