package io.baselogic.batch.introduction.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("Duplicates")
public class BatchConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // DataSource

    // using the default MapJobRepositoryFactoryBean


    //---------------------------------------------------------------------------//
    // Launcher and Repository

    // using the default Launcher and Repository


} // The End...
