package io.baselogic.batch.tasklet.config;

import io.baselogic.batch.tasklet.steps.EchoTasklet;
import io.baselogic.batch.tasklet.steps.NoOpTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings("Duplicates")
public class StepConfig {


    //---------------------------------------------------------------------------//
    // Steps
    @Bean
    public Step noOpStep(StepBuilderFactory stepBuilderFactory, NoOpTasklet noOpTasklet) {
        return stepBuilderFactory.get("noOpStep").tasklet(noOpTasklet).build();
    }


    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepA")
                .tasklet(new EchoTasklet("** STEP A")).build();
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
    public Step endStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("endStep")
                .tasklet((contribution, chunkContext) -> null)
                .build();
    }


    //---------------------------------------------------------------------------//
    // Tasklets

    /**
     * Creating Tasklet manually, could not Autowire Tasklet with @Component
     * @return
     */
    @Bean
    public NoOpTasklet noOpTasklet(){
        return new NoOpTasklet();
    }




} // The End...
