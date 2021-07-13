package io.marklove.spring.security.jwt.configurations;

/**
 * @author ngupq
 */
public class ResetPasswordProperties {
    private int expirationHours;

    public int getExpirationHours() {
        return expirationHours;
    }

    public void setExpirationHours(int expirationHours) {
        this.expirationHours = expirationHours;
    }
}
