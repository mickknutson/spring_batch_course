package io.baselogic.batch.partition.config;

import io.baselogic.batch.partition.processors.ProductFieldSetMapper;
import io.baselogic.batch.partition.processors.ProductItemProcessor;
import io.baselogic.batch.partition.processors.ProductJdbcItemWriter;
import io.baselogic.batch.partition.domain.Product;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Value("2")
    private int gridSize;

    @Value("products.csv")
    private ClassPathResource products;

    //---------------------------------------------------------------------------//
    // Steps


    @Bean
    @SuppressWarnings("unchecked")
    public Step readWriteProducts(StepBuilderFactory stepBuilderFactory,
                     ItemWriter<Product> writer,
                                  ProductItemProcessor processor,
                     FlatFileItemReader reader) {
        return stepBuilderFactory.get("readWriteProducts")
                .<Product, Product> chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .throttleLimit(20)
                .build();
    }


    @Bean
    public Step step1Master(StepBuilderFactory stepBuilderFactory,
                            PartitionHandler partitionHandler,
                            MultiResourcePartitioner partitioner) {
        return stepBuilderFactory.get("step1.master")
                .partitioner("step1", partitioner)
                .partitionHandler(partitionHandler)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public PartitionHandler partitionHandler(ThreadPoolTaskExecutor taskExecutor,
                                             Step readWriteProducts) {
        TaskExecutorPartitionHandler retVal = new TaskExecutorPartitionHandler();
        retVal.setTaskExecutor(taskExecutor);
        retVal.setStep(readWriteProducts);
        retVal.setGridSize(10);
        return retVal;
    }


    @Bean
    public MultiResourcePartitioner partitioner(ThreadPoolTaskExecutor taskExecutor)
            throws Exception{
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("fileName");
        Resource[] resources
                = resourcePatternResolver.getResources("file:src/main/resources/data/products*.csv");
        partitioner.setResources(resources);
        return partitioner;
    }





    //---------------------------------------------------------------------------//
    // Readers

    @Bean
    @StepScope
    public FlatFileItemReader reader2(@Value("#{jobParameters['inputResource']}") String products){
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"PRODUCT_ID", "NAME", "UNIT_PRICE", "QUANTITY"};
        tokenizer.setNames(tokens);

        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ProductFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);

//        reader.setResource(products);

        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader reader(@Value("#{jobParameters['inputResource']}") String inputResource)
    throws Exception{
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"PRODUCT_ID", "NAME", "UNIT_PRICE", "QUANTITY"};
        tokenizer.setNames(tokens);

        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ProductFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);

        Resource[] resources
                = resourcePatternResolver.getResources("file:src/main/resources/data/products*.csv");

        reader.setResource(resources[0]);
        return reader;
    }


    @Bean
    @StepScope
    public JdbcCursorItemReader productItemReader(EmbeddedDatabase dataSource){
        JdbcCursorItemReader<Product> reader = new JdbcCursorItemReader<>();

        reader.setSql("select id, name, price from product WHERE");
        reader.setDataSource(dataSource);
        return reader;
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader pagingProductItemReader(EmbeddedDatabase dataSource){
        JdbcPagingItemReader<Product> reader = new JdbcPagingItemReader<>();

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause("select id, name, price");
        queryProvider.setFromClause("from product");
        queryProvider.setWhereClause("where status = 'NEW’ AND 1=1");
        queryProvider.setSortKey("id");

        reader.setPageSize(10);
        reader.setDataSource(dataSource);
        return reader;
    }



    //---------------------------------------------------------------------------//
    // Processors

    @Bean
    public ProductItemProcessor processor(){
        return new ProductItemProcessor();
    }


    //---------------------------------------------------------------------------//
    // Writers

    @Bean
    public ItemWriter<Product> writer(EmbeddedDatabase dataSource){
        return new ProductJdbcItemWriter(dataSource);
    }




} // The End...
