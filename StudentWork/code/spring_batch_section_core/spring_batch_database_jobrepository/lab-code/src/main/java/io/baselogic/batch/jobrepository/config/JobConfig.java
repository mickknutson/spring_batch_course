package io.baselogic.batch.jobrepository.config;

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
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step stepA, Step stepB, Step stepC
    ) {
        return jobBuilderFactory.get("taskletJob")
                .start(stepA)
                .next(stepB)
                .next(stepC)
                .build();
    }


} // The End...
