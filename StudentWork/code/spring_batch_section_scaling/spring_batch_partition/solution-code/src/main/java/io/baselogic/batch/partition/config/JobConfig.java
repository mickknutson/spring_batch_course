package io.baselogic.batch.partition.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {

    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job partitionerJob(JobBuilderFactory jobBuilderFactory,
                              Step partitionStep) {
        return jobBuilderFactory.get("partitionerJob")
                .start(partitionStep)
                .build();
    }


} // The End...
