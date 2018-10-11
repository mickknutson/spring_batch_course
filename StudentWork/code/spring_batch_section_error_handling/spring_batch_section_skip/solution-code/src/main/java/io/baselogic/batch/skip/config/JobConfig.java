package io.baselogic.batch.skip.config;

import io.baselogic.batch.skip.listeners.CustomItemReadListener;
import io.baselogic.batch.skip.listeners.CustomItemWriterListener;
import io.baselogic.batch.skip.listeners.CustomStepExecutionListener;
import io.baselogic.batch.skip.listeners.CustomJobExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {

    //---------------------------------------------------------------------------//
    // Lab: Configure Job
    @Bean
    public Job skipJob(JobBuilderFactory jobBuilderFactory,
                       CustomJobExecutionListener customJobExecutionListener,
                              Step skipStep) {
        return jobBuilderFactory.get("chunkJobFileAuditor")
                .incrementer(new RunIdIncrementer())
                .start(skipStep)
                .listener(customJobExecutionListener)
                .build();
    }

    @Bean
    public CustomJobExecutionListener jobListener(){
        return new CustomJobExecutionListener();
    }

    @Bean
    public CustomStepExecutionListener stepExecutionListener(){
        return new CustomStepExecutionListener();
    }

    @Bean
    public CustomItemReadListener itemReadListener(){
        return new CustomItemReadListener();
    }

    @Bean
    public CustomItemWriterListener itemWriterListener(){
        return new CustomItemWriterListener();
    }

} // The End...
