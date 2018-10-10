package io.baselogic.batch.chunks.config;

import io.baselogic.batch.chunks.listeners.ChunkListener;
import io.baselogic.batch.chunks.steps.ConsoleItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
