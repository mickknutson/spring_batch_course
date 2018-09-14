package io.baselogic.batch.chunks.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
@Slf4j
@SuppressWarnings("Duplicates")
public class JobConfig {

    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job jobFileAuditor(JobBuilderFactory jobBuilderFactory, Step stepFileAuditor) {
        return jobBuilderFactory.get("chunkJobFileAuditor")
                .incrementer(new RunIdIncrementer())
                .start(stepFileAuditor)
                .build();
    }


} // The End...
