package io.baselogic.batch.file_input.config;

import io.baselogic.batch.file_input.listeners.ChunkListener;
import io.baselogic.batch.file_input.process.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import javax.sql.DataSource;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {

    @Value("products.csv")
    private ClassPathResource products;

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    @SuppressWarnings("unchecked")
    public Step step(StepBuilderFactory stepBuilderFactory,
                                ItemWriter<Product> writer,
                                FlatFileItemReader reader,
                                ChunkListener chunkListener) {
        return stepBuilderFactory.get("stepFileReadAndSaveToDatabase")
                .<Product, Product> chunk(2)
                .reader(reader)
                .writer(writer)
                .listener(chunkListener)
                .build();
    }

    //---------------------------------------------------------------------------//
    // Tasklets




    //---------------------------------------------------------------------------//
    // Readers

    @Bean
    @StepScope
    public FlatFileItemReader reader(@Value("#{jobParameters['inputResource']}") String inputResource){
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"PRODUCT_ID", "NAME", "UNIT_PRICE", "QUANTITY"};
        tokenizer.setNames(tokens);

        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new ProductFieldSetMapper());

//        reader.setResource(new ClassPathResource(inputResource));
        reader.setResource(products);
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }



    @Bean
    @StepScope
    public JdbcCursorItemReader productItemReader(EmbeddedDatabase dataSource,
                                                  ProductRowMapper productRowMapper){
        JdbcCursorItemReader<Product> reader = new JdbcCursorItemReader<>();

        reader.setSql("select id, name, price from product WHERE");
        reader.setDataSource(dataSource);
        reader.setRowMapper(productRowMapper);
        return reader;
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader pagingProductItemReader(EmbeddedDatabase dataSource,
                                                        ProductRowMapper productRowMapper){
        JdbcPagingItemReader<Product> reader = new JdbcPagingItemReader<>();

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setSelectClause("select id, name, price");
        queryProvider.setFromClause("from product");
        queryProvider.setWhereClause("where status = 'NEW’ AND 1=1");
        queryProvider.setSortKey("id");

        reader.setPageSize(10);
        reader.setDataSource(dataSource);
        reader.setRowMapper(productRowMapper);
        return reader;
    }



    //---------------------------------------------------------------------------//
    // Processors

    @Bean
    public ProductItemProcessor processor(){
        return new ProductItemProcessor();
    }

    @Bean
    public ProductRowMapper productRowMapper(){
        return new ProductRowMapper();
    }


    //---------------------------------------------------------------------------//
    // Writers

    @Bean
    public ItemWriter<Product> writer(EmbeddedDatabase dataSource){
        return new ProductJdbcItemWriter(dataSource);
    }



    //---------------------------------------------------------------------------//
    // Listeners
    @Bean
    public ChunkListener chunkListener(){
        return new ChunkListener();
    }


} // The End...
