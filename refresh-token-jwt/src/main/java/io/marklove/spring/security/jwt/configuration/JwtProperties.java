package io.marklove.spring.security.jwt.configuration;

/**
 * @author ngupq
 */
public class JwtProperties {
    private String secretKey;
    private int expirationMs;
    private int refreshExpirationMs;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getExpirationMs() {
        return expirationMs;
    }

    public void setExpirationMs(int expirationMs) {
        this.expirationMs = expirationMs;
    }

    public int getRefreshExpirationMs() {
        return refreshExpirationMs;
    }

    public void setRefreshExpirationMs(int refreshExpirationMs) {
        this.refreshExpirationMs = refreshExpirationMs;
    }
}
