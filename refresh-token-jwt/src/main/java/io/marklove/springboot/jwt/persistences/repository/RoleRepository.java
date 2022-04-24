package io.marklove.springboot.jwt.persistences.repository;

import java.util.Optional;

import io.marklove.springboot.jwt.enums.ERole;
import io.marklove.springboot.jwt.persistences.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
