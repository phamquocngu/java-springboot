package io.marklove.springboot.jwt.payloads.responses.security;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author ngupq
 */
@Getter
public class TokenVerifyRes implements Serializable {
    private final String token;

    public TokenVerifyRes(String token) {
        this.token = token;
    }
}
