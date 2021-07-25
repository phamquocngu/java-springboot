package io.marklove.spring.security.jwt.services;

import io.marklove.spring.security.jwt.payloads.requests.security.ReqVerifyResetPass;
import io.marklove.spring.security.jwt.payloads.requests.security.ReqSignup;

/**
 * @author ngupq
 */
public interface VerifyTokenService {
    String register(ReqSignup reqSignup);
    boolean verifyRegister(String token);
    String resetPassword(String email);
    boolean verifyResetPassword(ReqVerifyResetPass reqVerifyResetPass);
}
