package io.marklove.carinsurance.controller;

import io.marklove.carinsurance.constant.Role;
import io.marklove.carinsurance.security.AuthenticationService;
import io.marklove.carinsurance.security.JwtRequest;
import io.marklove.carinsurance.security.JwtResponse;
import io.marklove.carinsurance.service.SNSAuthenticator;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@Api(tags = {"Authentication"})
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    private final SNSAuthenticator googleAuthenticator;
    private final SNSAuthenticator naverAuthenticator;
    private final SNSAuthenticator kakaoAuthenticator;
    private final SNSAuthenticator appleAuthenticator;

    @PostMapping("/admin/login")
    JwtResponse loginAdmin(@RequestBody JwtRequest request) {
        return authService.authenticateForToken(request, Role.ADMIN);
    }

    @PostMapping("/login")
    JwtResponse loginWeb(@RequestBody JwtRequest request) {
        switch (request.getGrantType()) {
            case GOOGLE:
                return googleAuthenticator.authenticate(request.getToken());
            case APPLE:
                return appleAuthenticator.authenticate(request.getToken(), request.getAppleUserName());
            case NAVER:
                return naverAuthenticator.authenticate(request.getToken());
            case KAKAO:
                return kakaoAuthenticator.authenticate(request.getToken());
            default:
                return authService.authenticateForToken(request, Role.MEMBER);
        }
    }
}
