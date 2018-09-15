package io.baselogic.batch.transitions.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@SuppressWarnings("Duplicates")
public class JobConfig {


    //---------------------------------------------------------------------------//
    // Jobs

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step stepA, Step stepB, Step stepC, Step stepD) {
        return jobBuilderFactory.get("defaultJob")
                .flow(stepA).on("*").to(stepB) // stepB will always get Executed
                .from(stepB).on("*").to(stepC) // stepC will always get Executed if stepB is Executed
                .next(stepD).end()
                .build();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public Job continueOnExitStatusJob(JobBuilderFactory jobBuilderFactory,
                              Step completedStep,
                              Step stepA, Step stepB, Step stepC, Step stepD) {
        return jobBuilderFactory.get("continueOnExitStatusJob")
                .flow(completedStep).on("COMPLETED").to(stepA)
                .from(stepA).on("*").to(stepB)
                .from(stepB).on("*").to(stepC)
                .next(stepD).end()
                .build();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public Job failingJob(JobBuilderFactory jobBuilderFactory,
                          Step failedStep) {
        return jobBuilderFactory.get("failingJob")
                .flow(failedStep).on("FAILED").fail()
                .end()
                .build();
    }




} // The End...
