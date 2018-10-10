package io.baselogic.batch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create an EmbeddedDatabase.
 * This can be changed to any valid javax.sql.DataSource
 */
@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class DatabaseConfig {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());


    //---------------------------------------------------------------------------//
    // Lab: Create  EmbeddedDatabase:




    //---------------------------------------------------------------------------//
    // Lab: Create JdbcTemplate Bean



    @Bean
    public BatchDao batchDao(){
        return new BatchDao();
    }

} // The End...
