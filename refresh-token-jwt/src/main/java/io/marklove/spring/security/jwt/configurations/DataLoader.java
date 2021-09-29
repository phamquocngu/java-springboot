package io.marklove.spring.security.jwt.configurations;

import io.marklove.spring.security.jwt.enums.ERole;
import io.marklove.spring.security.jwt.persistences.entities.Role;
import io.marklove.spring.security.jwt.persistences.entities.User;
import io.marklove.spring.security.jwt.persistences.repository.RoleRepository;
import io.marklove.spring.security.jwt.persistences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ngupq
 */
@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByUsername("ngupq")) {
            User user = new User();
            user.setUsername("ngupq");
            user.setEmail("ngupq@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setEnable(true);
            user.setAccountExpired(false);
            user.setAccountLocked(false);
            user.setCredentialsExpired(false);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ERole.ADMIN).orElseGet(null));
            roles.add(roleRepository.findByName(ERole.USER).orElseGet(null));
            roles.add(roleRepository.findByName(ERole.MODERATOR).orElseGet(null));
            user.setRoles(roles);
            userRepository.save(user);
            System.out.println(user);
        }
    }
}
