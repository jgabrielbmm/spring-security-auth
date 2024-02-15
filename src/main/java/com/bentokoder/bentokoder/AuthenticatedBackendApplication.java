package com.bentokoder.bentokoder;

import com.bentokoder.bentokoder.models.ApplicationUser;
import com.bentokoder.bentokoder.models.Role;
import com.bentokoder.bentokoder.repositories.RoleRepository;
import com.bentokoder.bentokoder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AuthenticatedBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticatedBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder){
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			var admin = new ApplicationUser(1, "admin", encoder.encode("admin"), roles);
			userRepository.save(admin);
		};
	}
}

