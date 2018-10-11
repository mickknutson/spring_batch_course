package io.baselogic.batch.retry.config;

import io.baselogic.batch.retry.listeners.CustomChunkListener;
import io.baselogic.batch.retry.listeners.CustomItemWriterListener;
import io.baselogic.batch.retry.listeners.CustomStepExecutionListener;
import io.baselogic.batch.retry.processor.CustomRetryableException;
import io.baselogic.batch.retry.processor.RetryItemProcessor;
import io.baselogic.batch.retry.processor.RetryItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // Lab: Add ClassPathResource to import CSV file for ItemReader
    @Value("products.csv")
    private ClassPathResource inputResource;


    //---------------------------------------------------------------------------//
    // Lab: Add Step with chunk details
    @Bean
    @SuppressWarnings("unchecked")
    public Step stepA(StepBuilderFactory stepBuilderFactory) {

        return stepBuilderFactory.get("step")
                .listener(new CustomStepExecutionListener())

                .<String, String>chunk(10)

                .listener(customChunkListener())

                .reader(reader())
                .processor(processor(null))
                .writer(writer(null))
                .listener(new CustomItemWriterListener())

                .faultTolerant()
                .retry(CustomRetryableException.class)

                .retryLimit(10)
                .build();
    }

    @Bean
    public CustomChunkListener customChunkListener(){
        return new CustomChunkListener();
    }


    @Bean
    @StepScope
    public ListItemReader reader() {

        List<String> items = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            items.add(String.valueOf(i));
        }

        ListItemReader<String> reader = new ListItemReader(items);

        return reader;
    }

    @Bean
    @StepScope
    public RetryItemProcessor processor(@Value("#{jobParameters[retry]}") String retry) {
        RetryItemProcessor processor = new RetryItemProcessor();

        processor.setRetry(
                StringUtils.hasText(retry) && retry.equalsIgnoreCase("processor")
        );

        return processor;
    }

    @Bean
    @StepScope
    public RetryItemWriter writer(@Value("#{jobParameters[retry]}")String retry) {
        RetryItemWriter writer = new RetryItemWriter();

        writer.setRetry(
                StringUtils.hasText(retry) && retry.equalsIgnoreCase("writer")
        );

        return writer;
    }


} // The End...
