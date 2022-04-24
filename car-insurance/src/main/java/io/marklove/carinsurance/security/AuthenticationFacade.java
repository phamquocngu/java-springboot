package io.marklove.carinsurance.security;


public interface AuthenticationFacade {

    UserDetails getAuthentication();

    boolean hasRole(String role);

    Object getPrincipal();
}
