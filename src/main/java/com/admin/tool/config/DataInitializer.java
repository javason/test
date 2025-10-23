package com.admin.tool.config;

import com.admin.tool.entity.User;
import com.admin.tool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Initializing default users...");

            // Admin user
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@example.com")
                    .name("Admin User")
                    .role(User.Role.ADMIN)
                    .enabled(true)
                    .build();

            // Manager user
            User manager = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("manager123"))
                    .email("manager@example.com")
                    .name("Manager User")
                    .role(User.Role.MANAGER)
                    .enabled(true)
                    .build();

            // Regular user
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .email("user@example.com")
                    .name("Regular User")
                    .role(User.Role.USER)
                    .enabled(true)
                    .build();

            userRepository.save(admin);
            userRepository.save(manager);
            userRepository.save(user);

            log.info("Default users created successfully!");
            log.info("Admin - username: admin, password: admin123");
            log.info("Manager - username: manager, password: manager123");
            log.info("User - username: user, password: user123");
        }
    }
}
