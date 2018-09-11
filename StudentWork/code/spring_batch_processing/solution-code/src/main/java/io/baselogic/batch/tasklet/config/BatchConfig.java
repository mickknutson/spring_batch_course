package io.baselogic.batch.tasklet.config;

import io.baselogic.batch.tasklet.steps.DataProcessorTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

//@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;



    @Bean
    public Step step1(final DataProcessorTasklet dataProcessorTasklet) {
        return stepBuilderFactory.get("step1")
                .tasklet(dataProcessorTasklet)
                .build();
    }

    @Bean
    public Job job(Step step1) throws Exception {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean
    public DataProcessorTasklet dataProcessorTasklet(){
        DataProcessorTasklet tasklet = new DataProcessorTasklet();

        // FIXME: Find out how to pass parameters:
        tasklet.setInputResource(new ClassPathResource("products.csv"));

        return tasklet;
    }

} // The End...