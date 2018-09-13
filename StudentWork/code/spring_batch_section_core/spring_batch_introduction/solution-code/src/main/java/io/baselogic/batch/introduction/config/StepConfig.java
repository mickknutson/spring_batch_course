package io.baselogic.batch.introduction.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings("Duplicates")
public class StepConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .tasklet(
                        (contribution, chunkContext) -> {
                            logger.info("Hello World!");
                            return RepeatStatus.FINISHED;
                        }

                ).build();
    }

    //---------------------------------------------------------------------------//
    // Tasklets




} // The End...
