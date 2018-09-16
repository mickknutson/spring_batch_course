package io.baselogic.batch.transitions.config;

import io.baselogic.batch.transitions.steps.EchoTasklet;
import io.baselogic.batch.transitions.steps.StatusTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    @Value("products.csv")
    private ClassPathResource inputResource;


    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepA")
                .tasklet(new EchoTasklet("** STEP A"))
                .build();
    }

    @Bean
    public Step stepB(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepB")
                .tasklet(new EchoTasklet("** STEP B")).build();
    }

    @Bean
    public Step stepC(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepC")
                .tasklet(new EchoTasklet("** STEP C")).build();
    }

    @Bean
    public Step stepD(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepD")
                .tasklet(new EchoTasklet("** STEP D")).build();
    }

    @Bean
    public Step completedStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("completedStep")
                .tasklet(new StatusTasklet(ExitStatus.COMPLETED))
                .build();
    }

    @Bean
    public Step executingStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("finishedStep")
                .tasklet(new StatusTasklet(ExitStatus.EXECUTING))
                .build();
    }

    @Bean
    public Step failedStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("failedStep")
                .tasklet(new StatusTasklet(ExitStatus.FAILED))
                .build();
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
