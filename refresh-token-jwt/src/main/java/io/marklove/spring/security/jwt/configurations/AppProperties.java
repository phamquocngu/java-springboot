package io.marklove.spring.security.jwt.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngupq
 */
@Configuration
@Slf4j
public class AppProperties {

    @Bean
    @ConfigurationProperties(prefix = "app.jwt")
    public JwtProperties jwtConfiguration() throws Exception {
        try {
            return new JwtProperties();
        } catch (Exception e) {
            log.error("init configuration for jwt error: " + e.getMessage());
            throw new Exception("init configuration for jwt error: " + e.getMessage());
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "app.signup")
    public SignupProperties signupProperties() throws Exception {
        try {
            return new SignupProperties();
        } catch (Exception e) {
            log.error("init configuration for sign-up error: " + e.getMessage());
            throw new Exception("init configuration for sign-up error: " + e.getMessage());
        }

    }

    @Bean
    @ConfigurationProperties(prefix = "app.reset-password")
    public ResetPasswordProperties resetPasswordProperties() throws Exception {
        try {
            return new ResetPasswordProperties();
        } catch (Exception e) {
            log.error("init configuration for reset-password error: " + e.getMessage());
            throw new Exception("init configuration for reset-password error: " + e.getMessage());
        }
    }
}
