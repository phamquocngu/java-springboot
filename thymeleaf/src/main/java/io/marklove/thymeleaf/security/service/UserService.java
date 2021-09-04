package io.marklove.thymeleaf.security.service;

import io.marklove.thymeleaf.security.entity.User;

/**
 * @author ngupq
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
