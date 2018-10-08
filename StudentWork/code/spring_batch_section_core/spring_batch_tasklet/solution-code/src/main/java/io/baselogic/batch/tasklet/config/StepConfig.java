package io.baselogic.batch.tasklet.config;

import io.baselogic.batch.tasklet.steps.EchoTasklet;
import io.baselogic.batch.tasklet.steps.NoOpTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {


    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step noOpStep(StepBuilderFactory stepBuilderFactory,
                         Tasklet noOpTasklet) {
        return stepBuilderFactory.get("noOpStep")
                .tasklet(noOpTasklet)
                .build();
    }

    /**
     * Note: using a Tasklet defined as a separate @Bean
     * @param stepBuilderFactory
     * @param echoTasklet
     * @return
     */
    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory,
                      Tasklet echoTasklet) {
        return stepBuilderFactory.get("stepA")
                .tasklet(echoTasklet)
                .build();
    }

    @Bean
    public Step stepB(StepBuilderFactory stepBuilderFactory,
                      Tasklet echoTasklet) {
        return stepBuilderFactory.get("stepB")
                .tasklet(echoTasklet).build();
    }

    @Bean
    public Step stepC(StepBuilderFactory stepBuilderFactory,
                      Tasklet echoTasklet) {
        return stepBuilderFactory.get("stepC")
                .tasklet(echoTasklet).build();
    }

    @Bean
    public Step endStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("endStep")
                .tasklet((contribution, chunkContext) -> null)
                .build();
    }


    //---------------------------------------------------------------------------//
    // Tasklets

    @Bean
    public Tasklet noOpTasklet() {
        return new NoOpTasklet();
    }


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
    // Lifecycles





} // The End...
