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
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)

                // NOTE: We first Drop the Batch Schema:
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")

                // NOTE: Next we Drop the Batch Schema:
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .ignoreFailedDrops(true)
                .continueOnError(true)
                .build();
    }


    //---------------------------------------------------------------------------//
    // Lab: Create JdbcTemplate Bean
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }



    @Bean
    public BatchDao batchDao(){
        return new BatchDao();
    }

} // The End...
