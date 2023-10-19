package com.example.SortBoxs.config;

import com.example.SortBoxs.entitys.Role;
import com.example.SortBoxs.entitys.User;
import com.example.SortBoxs.entitys.User.UserBuilder;
import com.example.SortBoxs.repository.UserRepository;
import com.example.SortBoxs.services.AuthenticationService;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminInitializer implements ApplicationRunner {
	
	private static org.slf4j.Logger  logger  = LoggerFactory.getLogger(AdminInitializer.class);

	private final UserRepository userRepository;
	private  String adminUsername = "Shannu@gmail.com";
	private  String adminPassword = "qwerty123";
	private final PasswordEncoder passwordEncoder;

	public AdminInitializer(UserRepository userRepository,


			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User admin = User.builder()
				.firstName("admin")
				.lastName("admin")
				.email(adminUsername)
				.password(adminPassword)
				.confirmPassword(adminPassword)
				.role(Role.ROLE_ADMIN)
				.enabled(true)
				.accountNonLocked(true)
				.build();


		if (!userRepository.existsByEmail(adminUsername)) {
			admin.setPassword(passwordEncoder.encode(adminPassword));
			userRepository.save(admin);
			


           logger.info("Admin user created successfully");
		} else {

           
			logger.info("admins in thier ");
		}
	}

}
