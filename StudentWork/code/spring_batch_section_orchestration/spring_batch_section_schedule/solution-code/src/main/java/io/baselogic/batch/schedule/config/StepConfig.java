package io.baselogic.batch.schedule.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());


    //---------------------------------------------------------------------------//
    // Lab: Add Step with chunk details
    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {

        return stepBuilderFactory.get("stepA")
                .tasklet(tasklet())
                .build();
    }


    @Bean
    @StepScope
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");

            log.info(
                    String.format(">> I was run at %s",
                            formatter.format(new Date())));
            return RepeatStatus.FINISHED;
        };
    }


} // The End...
