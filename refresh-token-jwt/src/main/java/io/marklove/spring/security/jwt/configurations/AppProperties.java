package io.marklove.spring.security.jwt.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngupq
 */
@Configuration
public class AppProperties {

    private static final Logger logger = LoggerFactory.getLogger(AppProperties.class);

    @Bean
    @ConfigurationProperties(prefix = "app.jwt")
    public JwtProperties jwtConfiguration() throws Exception {
        try {
            return new JwtProperties();
        } catch (Exception e) {
            logger.error("init configuration for jwt error: " + e.getMessage());
            throw new Exception("init configuration for jwt error: " + e.getMessage());
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "app.signup")
    public SignupProperties signupProperties() throws Exception {
        try {
            return new SignupProperties();
        } catch (Exception e) {
            logger.error("init configuration for sign-up error: " + e.getMessage());
            throw new Exception("init configuration for sign-up error: " + e.getMessage());
        }

    }

    @Bean
    @ConfigurationProperties(prefix = "app.reset-password")
    public ResetPasswordProperties resetPasswordProperties() throws Exception {
        try {
            return new ResetPasswordProperties();
        } catch (Exception e) {
            logger.error("init configuration for reset-password error: " + e.getMessage());
            throw new Exception("init configuration for reset-password error: " + e.getMessage());
        }
    }
}
