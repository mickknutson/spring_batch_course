package io.baselogic.batch.tasklet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings("Duplicates")
public class JobConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step noOpStep, Step stepA, Step stepB, Step stepC) {
        return jobBuilderFactory.get("taskletJob")
                .flow(noOpStep).on("*").to(stepB)
                .from(stepA).on("*").to(stepB)
                .next(stepC).end()
                .build();
    }



} // The End...
