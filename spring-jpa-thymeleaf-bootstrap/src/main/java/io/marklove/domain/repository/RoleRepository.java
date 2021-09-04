package io.marklove.domain.repository;

import io.marklove.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ngupq
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
