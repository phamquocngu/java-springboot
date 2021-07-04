package io.marklove.spring.security.jwt.persistence.repository;

import java.util.Optional;

import io.marklove.spring.security.jwt.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmailAndEnable(String username, boolean enable);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
}
