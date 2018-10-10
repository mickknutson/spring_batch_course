package io.baselogic.batch.restart.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {

    //---------------------------------------------------------------------------//
    // Lab: Configure Job
    @Bean
    public Job jobFileAuditor(JobBuilderFactory jobBuilderFactory,
                              Step stepFileAuditor) {
        return jobBuilderFactory.get("chunkJobFileAuditor")
                .incrementer(new RunIdIncrementer())
                .start(stepFileAuditor)
                .build();
    }


} // The End...
