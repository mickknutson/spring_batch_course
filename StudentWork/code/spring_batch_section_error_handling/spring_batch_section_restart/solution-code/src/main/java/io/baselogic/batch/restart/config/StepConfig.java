package io.baselogic.batch.restart.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {


    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepA")
                .tasklet(restartTasklet())
                .build();
    }

    @Bean
    public Step stepB(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepB")
                .tasklet(restartTasklet())
                .build();
    }



    //---------------------------------------------------------------------------//
    // Tasklets

    @Bean
    @StepScope
    public Tasklet restartTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();

                if(stepExecutionContext.containsKey("ran")) {
                    log.info("This time we'll let it go.");
                    return RepeatStatus.FINISHED;
                }
                else {
                    log.error("I don't think so...");
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("ran", true);
//                    throw new RuntimeException("Not this time...");
                    return RepeatStatus.CONTINUABLE;
                }
            }
        };
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
