package io.baselogic.batch.partition.config;

import io.baselogic.batch.partition.domain.Movie;
import io.baselogic.batch.partition.processors.CustomMultiResourcePartitioner;
import io.baselogic.batch.partition.processors.MovieFieldSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;

import java.io.IOException;
import java.text.ParseException;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Value("10")
    private int gridSize;

    //---------------------------------------------------------------------------//
    // Steps


    @Bean
    public Step partitionStep(StepBuilderFactory stepBuilderFactory,
                              TaskExecutor taskExecutor,
                              CustomMultiResourcePartitioner partitioner) {
        return stepBuilderFactory.get("partitionStep")
                .partitioner("partitionStep.master", partitioner)
                .step(slaveStep(stepBuilderFactory))
                .taskExecutor(taskExecutor)
                .build();
    }

    @Autowired
    private JsonFileItemWriter<Movie> jsonItemWriter;

    @Bean
    public Step slaveStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("slaveStep")
                .<Movie, Movie>chunk(1)
                .reader(itemReader("movies"))
                .writer(jsonItemWriter)
                .build();
    }



    //---------------------------------------------------------------------------//
    // Tasklets




    //---------------------------------------------------------------------------//
    // Readers


    /**
     * title,release_date,tagline
     * @param filename
     * @return
     * @throws UnexpectedInputException
     * @throws ParseException
     */
    @Bean
    @StepScope
    public FlatFileItemReader<Movie> itemReader(@Value("#{stepExecutionContext[fileName]}") String filename)
            throws UnexpectedInputException {

        FlatFileItemReader<Movie> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"title", "release_date", "tagline"};
        tokenizer.setNames(tokens);
        reader.setResource(new ClassPathResource("inputs/" + filename));

        DefaultLineMapper<Movie> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new MovieFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }


    //---------------------------------------------------------------------------//
    // Processors



    //---------------------------------------------------------------------------//
    // Writers

    @Bean
    @StepScope
    public JsonFileItemWriter<Movie> jsonItemWriter(@Value("#{stepExecutionContext[outputFileName]}") String filename) {
        return new JsonFileItemWriterBuilder<Movie>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .forceSync(true)
                .append(true)
                .resource(new FileSystemResource("target/batch/" + filename))
                .name("jsonItemWriter")
                .build();
    }


    //---------------------------------------------------------------------------//
    // Listeners



    //---------------------------------------------------------------------------//
    // Partitioner


    @Bean
    public CustomMultiResourcePartitioner partitioner() throws Exception{
        CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
        Resource[] resources;

        resources = resourcePatternResolver.getResources("file:src/main/resources/inputs/m*.csv");

//        try {
//            resources = resourcePatternResolver.getResources("file:src/main/resources/inputs/m*.csv");
//        } catch (IOException e) {
//            throw new RuntimeException("I/O problems when resolving the input file pattern.", e);
//        }

        partitioner.setResources(resources);

        return partitioner;
    }


    @Bean
    public PartitionHandler partitionHandler(Step partitionStep) {
        TaskExecutorPartitionHandler retVal = new TaskExecutorPartitionHandler();
        retVal.setTaskExecutor(taskExecutor);
        retVal.setStep(partitionStep);
        retVal.setGridSize(gridSize);
        return retVal;
    }



} // The End...
