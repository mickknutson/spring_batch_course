package io.baselogic.batch.introduction.config;

import io.baselogic.batch.introduction.steps.EchoTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;


//@Configuration
@EnableBatchProcessing
public class EchoJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //---------------------------------------------------------------------------//


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        return new MapJobRepositoryFactoryBean(transactionManager())
                .getObject();
    }

    @Bean @Autowired
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        final SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean @Autowired
    public Job job(final Step stepA, final Step stepB, final Step stepC) {
        return jobBuilderFactory.get("echoJob")
                .flow(stepA).on("*").to(stepB)
                .next(stepC).end()
                .build();
    }

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
