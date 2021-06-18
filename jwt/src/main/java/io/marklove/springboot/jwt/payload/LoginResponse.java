package io.marklove.springboot.jwt.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private static final String tokenType = "Bearer";

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
