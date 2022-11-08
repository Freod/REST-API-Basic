package com.epam.esm;

import com.epam.esm.config.RsaKeyProperties;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository/*, PasswordEncoder encoder*/) {
        return args -> {
            userRepository.save(
                    new User(
                            null,
                            "admin",
//                            encoder.encode("password"),
                            "{noop}password",
                            "ADMIN",
                            null
                    )
            );
            userRepository.save(
                    new User(
                            null,
                            "user",
//                            encoder.encode("password"),
                            "{noop}password",
                            "USER",
                            null
                    )
            );
        };
    }
}
