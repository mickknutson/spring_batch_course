package io.baselogic.batch.introduction.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class BatchConfig {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // DataSource

    // using the default MapJobRepositoryFactoryBean


    //---------------------------------------------------------------------------//
    // Launcher and Repository

    // using the default Launcher and Repository


} // The End...
