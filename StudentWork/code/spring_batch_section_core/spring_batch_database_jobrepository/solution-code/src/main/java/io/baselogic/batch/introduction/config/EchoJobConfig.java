package io.baselogic.batch.introduction.config;

import io.baselogic.batch.introduction.steps.EchoTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Priority;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class EchoJobConfig extends DefaultBatchConfigurer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")

    @Autowired
    private DataSource dataSource;

    //---------------------------------------------------------------------------//
    // DataSource

    /*@Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .ignoreFailedDrops(true)
                .continueOnError(true)
                .build();
    }*/





    //---------------------------------------------------------------------------//
    // Launcher and Repository

    @Bean
    @Override
    public JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(createJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }


    @Bean
    @DependsOn({"dataSource", "transactionManager"})
    @Override
    public JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_");
        factory.setMaxVarCharLength(1_000);
        return factory.getObject();
    }


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job(final Step stepA, final Step stepB, final Step stepC) {
        return jobBuilderFactory.get("echoJob")
                .flow(stepA).on("*").to(stepB)
                .next(stepC).end()
                .build();
    }

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step stepA() {
        return stepBuilderFactory.get("stepA")
                .tasklet(new EchoTasklet("stepA: job parameter message")).build();
    }

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("stepB")
                .tasklet(new EchoTasklet("stepB: job parameter message")).build();
    }

    @Bean
    public Step stepC() {
        return stepBuilderFactory.get("stepC")
                .tasklet(new EchoTasklet("stepC: job parameter message")).build();
    }

} // The End...
