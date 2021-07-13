package io.marklove.spring.security.jwt.persistences.repository;

import java.util.Optional;

import io.marklove.spring.security.jwt.enums.ERole;
import io.marklove.spring.security.jwt.persistences.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
