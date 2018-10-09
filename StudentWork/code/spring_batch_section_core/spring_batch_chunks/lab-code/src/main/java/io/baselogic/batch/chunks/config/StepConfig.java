package io.baselogic.batch.chunks.config;

import io.baselogic.batch.chunks.domain.TextLineItem;
import io.baselogic.batch.chunks.listeners.ChunkListener;
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


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    //---------------------------------------------------------------------------//
    // Lab: Add ClassPathResource to import CSV file for ItemReader



    //---------------------------------------------------------------------------//
    // Lab: Add Step with chunk details






    //---------------------------------------------------------------------------//
    // Lab: Create FlatFileItemReader





    //---------------------------------------------------------------------------//
    // Writers
    @Bean
    public ConsoleItemWriter consoleItemWriter(){
        return new ConsoleItemWriter();
    }


    //---------------------------------------------------------------------------//
    // Listeners
    @Bean
    public ChunkListener chunkListener(){
        return new ChunkListener();
    }


} // The End...
