package io.baselogic.batch.introduction.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class HelloWorldConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    //---------------------------------------------------------------------------//
    // DataSource

    // using the default MapJobRepositoryFactoryBean


    //---------------------------------------------------------------------------//
    // Launcher and Repository

    // using the default Launcher and Repository


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job() {
        return jobBuilderFactory.get("helloWorldJob")
                .start(step1())
                .build();
    }

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(
                        (contribution, chunkContext) -> {
                            System.out.println("Hello World!");
                            return RepeatStatus.FINISHED;
                        }

                ).build();
    }

} // The End...

