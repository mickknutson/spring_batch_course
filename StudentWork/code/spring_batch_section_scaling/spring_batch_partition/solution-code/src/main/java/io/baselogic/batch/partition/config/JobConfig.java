package io.baselogic.batch.partition.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


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
        Flow flow1 = new FlowBuilder<SimpleFlow>("flow1")
                .start(stepA)
                .next(stepB)
                .build();
        Flow flow2 = new FlowBuilder<SimpleFlow>("flow2")
                .start(stepC)
                .build();

        return jobBuilderFactory.get("job")
                .start(flow1)
                .split(new SimpleAsyncTaskExecutor())
                .add(flow2)
                .next(stepD)
                .end()
                .build();
    }


} // The End...
