package io.marklove.spring.security.jwt.payloads.responses;

/**
 * @author ngupq
 */
public class TokenVerifyResponse {
    private String token;

    public TokenVerifyResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
