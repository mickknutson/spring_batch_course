package io.baselogic.batch.tasklet.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {


    //---------------------------------------------------------------------------//
    // Lab: Create Tasklet Job with 4 steps
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step noOpStep,
                   Step stepA, Step stepB, Step stepC) {
        return jobBuilderFactory.get("taskletJob")
                .start(noOpStep)
                .next(stepA)
                .next(stepB)
                .next(stepC)
                .build();
    }



} // The End...
