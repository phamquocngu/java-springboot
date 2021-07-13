package io.marklove.spring.security.jwt.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ngupq
 */
@Configuration
public class AppProperties {
    @Bean
    @ConfigurationProperties(prefix = "app.jwt")
    public JwtProperties jwtConfiguration() {
        return new JwtProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.signup")
    public SignupProperties signupProperties() {
        return new SignupProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "app.reset-password")
    public ResetPasswordProperties resetPasswordProperties() {
        return new ResetPasswordProperties();
    }
}
