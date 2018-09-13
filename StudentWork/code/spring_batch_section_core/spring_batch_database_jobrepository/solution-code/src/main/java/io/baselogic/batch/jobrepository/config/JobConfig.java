package io.baselogic.batch.jobrepository.config;

import io.baselogic.batch.jobrepository.steps.EchoTasklet;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("Duplicates")
public class JobConfig extends DefaultBatchConfigurer {

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
    @Override
    public JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_");
        factory.setMaxVarCharLength(1_000);
        factory.afterPropertiesSet();
        return factory.getObject();
    }


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step stepA, Step stepB, Step stepC) {
        return jobBuilderFactory.get("taskletJob")
                .start(stepA)
                .next(stepB)
                .next(stepC)
                .build();
    }

    //---------------------------------------------------------------------------//
    // Steps

    @Bean
    public Step stepA(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepA")
                .tasklet(new EchoTasklet("stepA: job parameter message")).build();
    }

    @Bean
    public Step stepB(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepB")
                .tasklet(new EchoTasklet("stepB: job parameter message")).build();
    }

    @Bean
    public Step stepC(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepC")
                .tasklet(new EchoTasklet("stepC: job parameter message")).build();
    }

} // The End...
