package io.baselogic.batch.parameters.config;

import io.baselogic.batch.parameters.steps.EchoTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
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

    /**
     * Revisit:
     * @Value("#{stepExecutionContext['stepKey']}")
     * vs
     * @Value("#{jobParameters[message]}")
     *
     * @param message
     * @return
     */
    @Bean
    @StepScope
    public Tasklet echoTasklet(@Value("#{jobParameters['message']}") String message) {
        return new EchoTasklet(message);
    }


    //---------------------------------------------------------------------------//
    // Readers


    //---------------------------------------------------------------------------//
    // Processors



    //---------------------------------------------------------------------------//
    // Writers



    //---------------------------------------------------------------------------//
    // Listeners


} // The End...
