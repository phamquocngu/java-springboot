package io.marklove.carinsurance.service;

import io.marklove.carinsurance.security.JwtResponse;

public interface SNSAuthenticator {

    JwtResponse authenticate(String token);

    JwtResponse authenticate(String token, String userName);

    /*JwtResponse emailAuthenticate(String username, String password);

    JwtResponse mockAuthenticate (String userId);*/
}
