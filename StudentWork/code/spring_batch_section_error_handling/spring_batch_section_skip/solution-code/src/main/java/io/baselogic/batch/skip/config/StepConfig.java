package io.baselogic.batch.skip.config;

import io.baselogic.batch.skip.domain.TextLineItem;
import io.baselogic.batch.skip.listeners.ChunkListener;
import io.baselogic.batch.skip.processor.CustomItemProcessor;
import io.baselogic.batch.skip.steps.ConsoleItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


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
    @SuppressWarnings("unchecked")
    public Step stepA(StepBuilderFactory stepBuilderFactory,
                                FlatFileItemReader reader,
                                CustomItemProcessor processor,
                                ConsoleItemWriter writer,
                                ChunkListener chunkListener) {
        return stepBuilderFactory.get("stepFileReadAndAudit")
                .<TextLineItem, TextLineItem> chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(chunkListener)
                .faultTolerant()
                .skipLimit(10)
                .skip(FlatFileParseException.class)
                .build();
    }






    //---------------------------------------------------------------------------//
    // Lab: Create FlatFileItemReader
    @Bean
    @StepScope
    public FlatFileItemReader<TextLineItem> reader() {

        // FlatFileItemReader to read TextLineItem's:
        FlatFileItemReader<TextLineItem> reader = new FlatFileItemReader<>();

        // Read items from inputResource:
        reader.setResource(inputResource);

        // This CSV file does not have a header, so no need to skip items:
        reader.setLinesToSkip(0);

        // Map each line from the CSV file into a TextLineItem
        DefaultLineMapper<TextLineItem> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        // take the data from each line and map it to the 'id' field:
        tokenizer.setNames("id");

        // Now map the id field into a new TextLineItem.class for each line:
        BeanWrapperFieldSetMapper<TextLineItem> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TextLineItem.class);

        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);

        return reader;
    }



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


    @Bean
    public CustomItemProcessor processor(){
        return new CustomItemProcessor();
    }


} // The End...
