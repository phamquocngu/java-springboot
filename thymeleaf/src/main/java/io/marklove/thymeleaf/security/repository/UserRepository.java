package io.marklove.thymeleaf.security.repository;

import io.marklove.thymeleaf.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ngupq
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}