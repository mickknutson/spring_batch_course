package io.baselogic.batch.file_input.config;

import io.baselogic.batch.file_input.decisions.FlowDecision;
import io.baselogic.batch.file_input.process.BatchJobListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step step,
                   JobExecutionListener batchJobListener) {
        return jobBuilderFactory.get("batchFileProcessJob")
                .listener(batchJobListener)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }


    @Bean
    public BatchJobListener batchJobListener(){
        return new BatchJobListener();
    }



} // The End...
