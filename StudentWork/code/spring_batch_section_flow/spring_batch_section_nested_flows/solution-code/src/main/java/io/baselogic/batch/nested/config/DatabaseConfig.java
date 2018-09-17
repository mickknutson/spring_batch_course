package io.baselogic.batch.nested.config;

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
public class DatabaseConfig {

    //---------------------------------------------------------------------------//

    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .ignoreFailedDrops(true)
                .continueOnError(true)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    BatchQueryDao batchQueryDao(){
        return new BatchQueryDao();
    }

} // The End...
