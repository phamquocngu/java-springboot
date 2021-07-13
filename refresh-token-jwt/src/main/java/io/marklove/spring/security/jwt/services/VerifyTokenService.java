package io.marklove.spring.security.jwt.services;

import io.marklove.spring.security.jwt.payloads.requests.ResetPasswordRequest;
import io.marklove.spring.security.jwt.payloads.requests.SignupRequest;

/**
 * @author ngupq
 */
public interface VerifyTokenService {
    String register(SignupRequest signupRequest);
    boolean verifyRegister(String token);
    String resetPassword(String email);
    boolean verifyResetPassword(ResetPasswordRequest resetPasswordRequest);
}
