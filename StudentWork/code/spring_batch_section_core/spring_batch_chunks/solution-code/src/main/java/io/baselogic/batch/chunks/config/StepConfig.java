package io.baselogic.batch.chunks.config;

import io.baselogic.batch.chunks.domain.TextLineItem;
import io.baselogic.batch.chunks.listeners.ChunkAuditor;
import io.baselogic.batch.chunks.steps.ConsoleItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@Slf4j
@SuppressWarnings("Duplicates")
public class StepConfig {

    @Value("products.csv")
    private ClassPathResource inputResource;

    //---------------------------------------------------------------------------//
    // Steps


    @Bean
    @SuppressWarnings("unchecked")
    public Step stepFileAuditor(StepBuilderFactory stepBuilderFactory,
                                ConsoleItemWriter writer,
                                FlatFileItemReader reader,
                                ChunkAuditor chunkAuditor) {
        return stepBuilderFactory.get("stepFileReadAndAudit")
                .<TextLineItem, TextLineItem> chunk(2)
                .reader(reader)
                .writer(writer)
                .listener(chunkAuditor)
                .build();
    }


    //---------------------------------------------------------------------------//
    // Tasklets




    //---------------------------------------------------------------------------//
    // Readers

    @Bean
    public ConsoleItemWriter<String> writer() {
        return new ConsoleItemWriter<>();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<TextLineItem> reader() {
        FlatFileItemReader<TextLineItem> reader = new FlatFileItemReader<>();
        reader.setResource(inputResource);
        reader.setLinesToSkip(0);

        DefaultLineMapper<TextLineItem> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id");

        BeanWrapperFieldSetMapper<TextLineItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TextLineItem.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);

        return reader;
    }



    //---------------------------------------------------------------------------//
    // Processors



    //---------------------------------------------------------------------------//
    // Writers

    @Bean
    public ConsoleItemWriter consoleItemWriter(){
        return new ConsoleItemWriter();
    }


    //---------------------------------------------------------------------------//
    // Listeners

    @Bean
    public ChunkAuditor chunkAuditor(){
        return new ChunkAuditor();
    }


} // The End...
