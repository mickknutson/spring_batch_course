package io.baselogic.batch.retry.config;

import io.baselogic.batch.retry.steps.EchoTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {


    //---------------------------------------------------------------------------//
    // Steps


    @Bean
    public Step childJobStep(JobRepository jobRepository,
                             JobLauncher jobLauncher,
                             PlatformTransactionManager transactionManager,
                             Job childJob
    ) {
        return new JobStepBuilder(new StepBuilder("childJobStep"))
                .job(childJob)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }



    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .tasklet(new EchoTasklet("** STEP 1"))
                .build();
    }

    @Bean
    public Step step2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step2")
                .tasklet(new EchoTasklet("** STEP 2"))
                .build();
    }

    @Bean
    public Step step3(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step3")
                .tasklet(new EchoTasklet("** STEP 3"))
                .build();
    }

    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepA")
                .tasklet(new EchoTasklet("** (child) STEP A"))
                .build();
    }

    @Bean
    public Step stepB(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepB")
                .tasklet(new EchoTasklet("** (child) STEP B")).build();
    }

    @Bean
    public Step stepC(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepC")
                .tasklet(new EchoTasklet("** (child) STEP C")).build();
    }


    //---------------------------------------------------------------------------//
    // Tasklets



    //---------------------------------------------------------------------------//
    // Readers


    //---------------------------------------------------------------------------//
    // Processors



    //---------------------------------------------------------------------------//
    // Writers



    //---------------------------------------------------------------------------//
    // Listeners


} // The End...
