package io.baselogic.batch.common.config;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class BatchConfig extends DefaultBatchConfigurer {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());


    //---------------------------------------------------------------------------//
    // Lab: Autowire transactionManager



    //---------------------------------------------------------------------------//
    // Lab: Autowire DataSource



    //---------------------------------------------------------------------------//
    // Lab: Create JobLauncher Bean




    //---------------------------------------------------------------------------//
    // Lab: Create JobRepository Bean



} // The End...
