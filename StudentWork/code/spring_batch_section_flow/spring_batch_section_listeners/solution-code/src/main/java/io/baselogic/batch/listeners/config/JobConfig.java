package io.baselogic.batch.listeners.config;

import io.baselogic.batch.listeners.listeners.JobListener;
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
    // Lab: Create @BeforeRead and log step details
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   JobListener jobListener,
                   Step stepA) {

        return jobBuilderFactory.get("listenerJob")
                .start(stepA)
                .listener(jobListener)
                .build();
    }

    //---------------------------------------------------------------------------//
    // Decisions



} // The End...
