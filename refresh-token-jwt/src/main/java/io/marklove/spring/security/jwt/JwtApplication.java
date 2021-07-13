package io.marklove.spring.security.jwt;

import io.marklove.spring.security.jwt.persistences.entities.Role;
import io.marklove.spring.security.jwt.persistences.entities.User;
import io.marklove.spring.security.jwt.enums.ERole;
import io.marklove.spring.security.jwt.persistences.repository.RoleRepository;
import io.marklove.spring.security.jwt.persistences.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

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
			roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseGet(null));
			roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseGet(null));
			roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).orElseGet(null));
			user.setRoles(roles);
			userRepository.save(user);
			System.out.println(user);
		}
	}

}
