package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource(value = "classpath:database/db.properties")
public class SpringJdbcConfig {

    private final Environment environment;

    @Autowired
    public SpringJdbcConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Profile("prod")
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db_url"));
        hikariConfig.setUsername(environment.getProperty("db_username"));
        hikariConfig.setPassword(environment.getProperty("db_password"));
        hikariConfig.setSchema(environment.getProperty("db_schema"));
        hikariConfig.setDriverClassName(environment.getProperty("db_driver"));

        return hikariConfig;
    }

    @Bean
    @Profile("prod")
    public DataSource postgresDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("dev")
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsertGiftCertificates(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("gift_certificates").usingGeneratedKeyColumns("id");
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsertTags(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("tags").usingGeneratedKeyColumns("id");
    }
}
