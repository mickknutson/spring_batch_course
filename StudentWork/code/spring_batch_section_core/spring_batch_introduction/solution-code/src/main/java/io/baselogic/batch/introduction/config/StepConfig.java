package io.baselogic.batch.introduction.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@SuppressWarnings("Duplicates")
public class StepConfig {

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .tasklet(
                        (contribution, chunkContext) -> {
                            log.info("Hello World!");
                            return RepeatStatus.FINISHED;
                        }

                ).build();
    }

    //---------------------------------------------------------------------------//
    // Tasklets




} // The End...
