/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.marklove.oauth2token.service;

import io.marklove.oauth2token.config.LoginConfig;
import io.marklove.oauth2token.persistence.domain.entity.User;
import io.marklove.oauth2token.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailsService.class);
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Autowired
    UserRepository repository;
    @Autowired
    LoginAttemptService loginAttemptService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    MessageSource ms;
    @Autowired
    LoginConfig loginConfig;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info("To check user: " + login);
        
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            logger.info("IP: " + ip + " is blocked for " + loginConfig.getLockTimeAfterLoginFail());
            throw new RuntimeException(loginConfig.getMaxLoginAttempt() + " times login failed. Blocked 2 minutes!");
        }

        if (login == null || login.isEmpty()) {
            throw new RuntimeException(ms.getMessage("user-email-empty", null, Locale.ENGLISH));
        }

        Optional<User> optionalUser;
         if (login.matches(EMAIL_REGEX)) {
            optionalUser = repository.findByEmail(login);
        } else {
            optionalUser = repository.findByUsername(login);
        }
        
        optionalUser.orElseThrow(() -> new RuntimeException(ms.getMessage("user-not-found", null, Locale.ENGLISH)));

        User user = optionalUser.get();
        String password = request.getParameter("password");
        if(!user.isAccountNonLocked()) {
        	throw new RuntimeException(ms.getMessage("account-inactive", null, Locale.ENGLISH));
        }
        if(!user.isEnabled()) {
        	throw new RuntimeException(ms.getMessage("account-block", null, Locale.ENGLISH));
        }
        if(!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
        	throw new RuntimeException(ms.getMessage("invalid-password", null, Locale.ENGLISH));
        }
        
        return user;
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }

        return xfHeader.split(",")[0];
    }
}
