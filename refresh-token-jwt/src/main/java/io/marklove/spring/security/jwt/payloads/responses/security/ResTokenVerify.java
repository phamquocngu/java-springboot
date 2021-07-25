package io.marklove.spring.security.jwt.payloads.responses.security;

/**
 * @author ngupq
 */
public class ResTokenVerify {
    private String token;

    public ResTokenVerify(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
