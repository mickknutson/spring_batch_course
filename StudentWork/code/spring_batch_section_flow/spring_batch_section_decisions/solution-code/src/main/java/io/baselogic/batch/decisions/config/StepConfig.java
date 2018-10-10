package io.baselogic.batch.decisions.config;

import io.baselogic.batch.decisions.steps.EchoTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {


    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step startingStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("startingStep")
                .tasklet(new EchoTasklet("** Entry STEP **"))
                .build();
    }

    @Bean
    public Step flipACoinStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("flipACoinStep")
                .tasklet(new EchoTasklet("** STEP Flipping a coin"))
                .build();
    }

    @Bean
    public Step oddStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("oddStep")
                .tasklet(new EchoTasklet("** STEP ODD"))
                .build();
    }

    @Bean
    public Step evenStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("evenStep")
                .tasklet(new EchoTasklet("** STEP EVEN")).build();
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


    //---------------------------------------------------------------------------//
    // Listeners


} // The End...
