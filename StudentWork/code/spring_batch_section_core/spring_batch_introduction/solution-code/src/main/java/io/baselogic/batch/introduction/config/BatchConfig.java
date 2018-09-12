package io.baselogic.batch.introduction.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("Duplicates")
public class BatchConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // DataSource

    // using the default MapJobRepositoryFactoryBean


    //---------------------------------------------------------------------------//
    // Launcher and Repository

    // using the default Launcher and Repository


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step1) {
        return jobBuilderFactory.get("helloWorldJob")
                .start(step1)
                .build();
    }

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .tasklet(
                        (contribution, chunkContext) -> {
                            logger.info("Hello World!");
                            return RepeatStatus.FINISHED;
                        }

                ).build();
    }

} // The End...

