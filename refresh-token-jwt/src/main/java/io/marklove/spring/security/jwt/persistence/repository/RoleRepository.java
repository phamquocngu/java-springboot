package io.marklove.spring.security.jwt.persistence.repository;

import java.util.Optional;

import io.marklove.spring.security.jwt.persistence.enums.ERole;
import io.marklove.spring.security.jwt.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
