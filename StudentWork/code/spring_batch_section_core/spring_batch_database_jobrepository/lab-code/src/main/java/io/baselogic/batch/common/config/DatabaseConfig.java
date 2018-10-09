package io.baselogic.batch.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Create an EmbeddedDatabase.
 * This can be changed to any valid javax.sql.DataSource
 */
@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class DatabaseConfig {


    //---------------------------------------------------------------------------//
    // Lab: Create  EmbeddedDatabase:




    //---------------------------------------------------------------------------//
    // Lab: Create JdbcTemplate Bean



    @Bean
    public BatchDao batchDao(){
        return new BatchDao();
    }

} // The End...
