package io.marklove.thymeleaf.security.service;

/**
 * @author ngupq
 */
public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}
