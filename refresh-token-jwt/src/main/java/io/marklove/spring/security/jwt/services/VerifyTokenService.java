package io.marklove.spring.security.jwt.services;

import io.marklove.spring.security.jwt.payloads.requests.security.VerifyResetPassReq;
import io.marklove.spring.security.jwt.payloads.requests.security.SignupReq;

/**
 * @author ngupq
 */
public interface VerifyTokenService {
    String register(SignupReq signupReq);
    boolean verifyRegister(String token);
    String resetPassword(String email);
    boolean verifyResetPassword(VerifyResetPassReq verifyResetPassReq);
}
