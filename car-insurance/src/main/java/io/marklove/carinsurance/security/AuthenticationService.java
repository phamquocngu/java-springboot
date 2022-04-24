package io.marklove.carinsurance.security;

import io.marklove.carinsurance.constant.Role;

public interface AuthenticationService {

    JwtResponse authenticateForToken(JwtRequest authRequest, Role role);

    JwtResponse processJwtResponse(long userId);

    void logout();
}
