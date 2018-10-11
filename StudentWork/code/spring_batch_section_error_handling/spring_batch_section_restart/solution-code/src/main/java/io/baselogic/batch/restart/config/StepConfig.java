package io.baselogic.batch.restart.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    //---------------------------------------------------------------------------//
    // Lab: Add ClassPathResource to import CSV file for ItemReader
    @Value("products.csv")
    private ClassPathResource inputResource;


    //---------------------------------------------------------------------------//
    // Lab: Add Step with chunk details

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .tasklet(restartTasklet())
                .build();
    }

    @Bean
    public Step step2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step2")
                .tasklet(restartTasklet())
                .build();
    }








    @Bean
    @StepScope
    public Tasklet restartTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();

                if(stepExecutionContext.containsKey("ran")) {
                    System.out.println("This time we'll let it go.");
                    return RepeatStatus.FINISHED;
                }
                else {
                    System.out.println("I don't think so...");
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("ran", true);
                    throw new RuntimeException("Not this time...");
                }
            }
        };
    }



} // The End...
