package io.baselogic.batch.partition.config;

import io.baselogic.batch.partition.listeners.BatchJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {

    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job partitionerJob(JobBuilderFactory jobBuilderFactory,
                              Step readWriteProducts,
                              BatchJobListener jobListener) {
        return jobBuilderFactory.get("batchFilePartitionJob")
                .start(readWriteProducts)
                .listener(jobListener)
                .build();
    }

    @Bean
    public BatchJobListener batchJobListener(){
        return new BatchJobListener();
    }


} // The End...
