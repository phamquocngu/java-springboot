package io.marklove.spring.security.jwt.security.services;

import io.marklove.spring.security.jwt.payload.request.ResetPasswordRequest;
import io.marklove.spring.security.jwt.payload.request.SignupRequest;
import org.springframework.stereotype.Service;

/**
 * @author ngupq
 */
public interface VerifyTokenService {
    String register(SignupRequest signupRequest);
    boolean verifyRegister(String token);
    String resetPassword(String email);
    boolean verifyResetPassword(ResetPasswordRequest resetPasswordRequest);
}
