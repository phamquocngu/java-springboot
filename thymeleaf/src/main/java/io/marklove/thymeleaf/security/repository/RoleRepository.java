package io.marklove.thymeleaf.security.repository;

import io.marklove.thymeleaf.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ngupq
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
