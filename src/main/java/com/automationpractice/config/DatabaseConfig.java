package com.automationpractice.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatabaseConfig {

    /**
     * Custom DataSource that configures SQLite WAL mode on every new connection.
     * WAL (Write-Ahead Logging) allows concurrent reads while writing,
     * which is required for Hibernate's connection pool to work properly.
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        HikariDataSource ds = properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        // Set SQLite PRAGMAs that fix concurrency issues
        ds.setConnectionInitSql(
            "PRAGMA journal_mode=WAL; " +
            "PRAGMA synchronous=NORMAL; " +
            "PRAGMA busy_timeout=10000;"
        );

        return ds;
    }
}
