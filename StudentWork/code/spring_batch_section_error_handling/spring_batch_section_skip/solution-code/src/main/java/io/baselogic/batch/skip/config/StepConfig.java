package io.baselogic.batch.skip.config;

import io.baselogic.batch.skip.listeners.CustomChunkListener;
import io.baselogic.batch.skip.listeners.CustomItemReadListener;
import io.baselogic.batch.skip.listeners.CustomItemWriterListener;
import io.baselogic.batch.skip.listeners.CustomStepExecutionListener;
import io.baselogic.batch.skip.processor.CustomRetryableException;
import io.baselogic.batch.skip.processor.SkipItemProcessor;
import io.baselogic.batch.skip.processor.SkipItemWriter;
import org.springframework.batch.core.ChunkListener;
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

    //---------------------------------------------------------------------------//
    // Lab: Add ClassPathResource to import CSV file for ItemReader
    @Value("products.csv")
    private ClassPathResource inputResource;


    //---------------------------------------------------------------------------//
    // Lab: Add Step with chunk details
    @Bean
    public Step skipStep(StepBuilderFactory stepBuilderFactory,
                         CustomStepExecutionListener stepExecutionListener,
                         CustomChunkListener chunkListener,
                         CustomItemReadListener itemReadListener,
                         CustomItemWriterListener itemWriterListener) {
        return stepBuilderFactory.get("step")
                // StepExecutionListener Must be before the Chunk
                .listener(stepExecutionListener)

                .<String, String>chunk(10)

                .listener(chunkListener)
                .listener(itemReadListener)
                .listener(itemWriterListener)

                .reader(reader())
                .processor(processor(null)) // null due to late binding
                .writer(writer(null))

                .faultTolerant()
                .skip(CustomRetryableException.class)
                .skipLimit(15)

                .build();
    }




    @Bean
    @StepScope
    public ListItemReader<String> reader() {

        List<String> items = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            items.add(String.valueOf(i));
        }

        return new ListItemReader<>(items);
    }

    @Bean
    @StepScope
    public SkipItemProcessor processor(@Value("#{jobParameters['skip']}")String skip) {
        SkipItemProcessor processor = new SkipItemProcessor();

        processor.setSkip(StringUtils.hasText(skip) && skip.equalsIgnoreCase("processor"));

        return processor;
    }

    @Bean
    @StepScope
    public SkipItemWriter writer(@Value("#{jobParameters['skip']}")String skip) {
        SkipItemWriter writer = new SkipItemWriter();

        writer.setSkip(StringUtils.hasText(skip) && skip.equalsIgnoreCase("writer"));

        return writer;
    }


    @Bean
    public CustomChunkListener chunkListener(){
        return new CustomChunkListener();
    }

} // The End...
