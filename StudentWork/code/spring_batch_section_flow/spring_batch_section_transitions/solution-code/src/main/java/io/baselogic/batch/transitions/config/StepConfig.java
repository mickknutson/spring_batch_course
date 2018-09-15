package io.baselogic.batch.transitions.config;

import io.baselogic.batch.transitions.listeners.ChunkAuditor;
import io.baselogic.batch.transitions.steps.ConsoleItemWriter;
import io.baselogic.batch.transitions.steps.EchoTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


@Configuration
@Slf4j
@SuppressWarnings("Duplicates")
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
    public Step endStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("endStep")
                .tasklet((contribution, chunkContext) -> null)
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

    @Bean
    public ConsoleItemWriter consoleItemWriter(){
        return new ConsoleItemWriter();
    }


    //---------------------------------------------------------------------------//
    // Listeners

    @Bean
    public ChunkAuditor chunkAuditor(){
        return new ChunkAuditor();
    }


} // The End...
