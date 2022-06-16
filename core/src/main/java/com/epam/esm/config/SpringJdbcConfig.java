package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfig {
    @Bean
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("database/init-ddl.sql")
                .build();
    }

    @Bean
    @Primary
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/module2");
        dataSource.setSchema("public");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "simpleJdbcInsertGiftCertificates")
    public SimpleJdbcInsert simpleJdbcInsertGiftCertificates(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificates").usingGeneratedKeyColumns("id");
    }

    @Bean(name = "simpleJdbcInsertTags")
    public SimpleJdbcInsert simpleJdbcInsertTags(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("tags").usingGeneratedKeyColumns("id");
    }
}
