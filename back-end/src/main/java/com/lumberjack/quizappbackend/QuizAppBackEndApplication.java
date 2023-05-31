package com.lumberjack.quizappbackend;

import com.lumberjack.quizappbackend.model.User;
import com.lumberjack.quizappbackend.model.UserRole;
import com.lumberjack.quizappbackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class QuizAppBackEndApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${admin.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(QuizAppBackEndApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserService userService) {
        return args -> {

            if (userService.findUserByUsername("admin").isPresent()) {
                return;
            }

            Set<String> roles = new HashSet<>();
            roles.add(UserRole.ADMIN.getRole());
            roles.add(UserRole.USER.getRole());

            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(password));
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setRoles(roles);

            userService.saveUser(admin);

        };
    }

}
