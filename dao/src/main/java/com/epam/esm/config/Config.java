package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class Config {
    @Bean
    @Profile("prod")
    public EntityManagerFactory postgresConfigEntityManager() {
        return Persistence
                .createEntityManagerFactory("rest-api-advanced");
    }

    @Bean
    @Profile("dev")
    public EntityManagerFactory h2ConfigEntityManager(){
        return Persistence
                .createEntityManagerFactory("h2config");
    }

    @Bean
    public int pageSize(){
        return 8;
    }
}
