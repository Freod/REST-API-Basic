package com.epam.esm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class Config {
    @Bean
    @Profile("prod")
    public EntityManagerFactory entityManagerFactory() {
        return Persistence
                .createEntityManagerFactory("rest-api-advanced");
    }

    @Primary
    @Bean
    @Profile("dev")
    public EntityManagerFactory h2ConfigEntityManager(){
        return Persistence
                .createEntityManagerFactory("h2config");
    }

    @Bean
    @ConfigurationProperties(prefix="datasource.mine")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test");
        dataSource.setUsername("as");
        dataSource.setPassword("as");

        return dataSource;
    }

    @Bean
    public int pageSize(){
        return 8;
    }
}
