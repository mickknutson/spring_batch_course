package io.baselogic.batch.transitions.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step stepA, Step stepB, Step stepC, Step stepD) {
        return jobBuilderFactory.get("defaultJob")
                .flow(stepA).on("*").to(stepB) // stepB will always get Executed
                .from(stepB).on("*").to(stepC) // stepC will always get Executed if stepB is Executed
                .next(stepD).end()
                .build();
    }

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

    @Bean
    public Job failingJob(JobBuilderFactory jobBuilderFactory,
                          Step failedStep) {
        return jobBuilderFactory.get("failingJob")
                .flow(failedStep).on("FAILED").fail()
                .end()
                .build();
    }


    @Bean
    public Job jobFlow(JobBuilderFactory jobBuilderFactory,
                   Step stepA, Step stepB, Step stepC, Step stepD) {

        Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
                .start(stepA)
                .next(stepB)
                .next(stepC)
                .next(stepD)
                .build();

        return jobBuilderFactory.get("job")
                .start(flow1)
                .next(stepD)
                .end()
                .build();
    }



} // The End...
