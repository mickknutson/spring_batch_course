package io.baselogic.batch.tasklet.config;

import io.baselogic.batch.tasklet.steps.EchoTasklet;
import io.baselogic.batch.tasklet.steps.FailingTasklet;
import io.baselogic.batch.tasklet.steps.NoOpTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;


//@Configuration
@ComponentScan(basePackages = {"io.baselogic.batch.tasklet.steps"})
@EnableBatchProcessing
public class EchoJobWithParametersConfig {

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

    //---------------------------------------------------------------------------//

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        final SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean
    public Job job(final Step step1, final Step stepA, final Step stepB, final Step stepC) {
        return jobBuilderFactory.get("echoJob")
                .flow(stepA).on("FAILED").to(stepFailure())
//                .from(stepA).on("*").to(stepB)
                .from(stepA).on("*").to(step1)
                .from(step1).on("*").to(stepB)
                .next(stepC).end()
                .build();


//        return jobBuilderFactory.get("cherryShoeJob")
//                .incrementer(new RunIdIncrementer()).listener(jobListener)
//                .flow(noOpStep).next(jobExecutionDecider)
//                .on(FlowExecutionStatus.STOPPED.getName()).to(noOpStep)
//                .from(noOpStep).next(jobExecutionDecider)
//                .on(FlowExecutionStatus.UNKNOWN.getName()).to(step1).next(step2).next(step3)
//                .end().build();
    }

    //---------------------------------------------------------------------------//


    @Autowired
    @Qualifier("fooTasklet")
    private Tasklet fooTasklet;


    @Bean
    @StepScope
    public FailingTasklet failingTasklet(
            @Value("#{jobParameters['shouldFail']}") boolean shouldFail
    ){
        FailingTasklet failingTasklet = new FailingTasklet("FAILING TASK");
        failingTasklet.setShouldFail(shouldFail);
        return failingTasklet;
    }

    @Bean
    Step noOpStep(StepBuilderFactory stepBuilderFactory, NoOpTasklet noOpTasklet) {
        return stepBuilderFactory.get("noOpStep").tasklet(noOpTasklet).build();
    }



    //---------------------------------------------------------------------------//

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(fooTasklet)
                .build();
    }

    @Bean
    public Step stepA(final FailingTasklet failingTasklet) {
        return stepBuilderFactory.get("stepA")
                .tasklet(failingTasklet)
                .build();
    }

    @Bean
    public Step stepB() {
        return stepBuilderFactory.get("stepB")
                .tasklet(new EchoTasklet("** STEP B")).build();
    }

    @Bean
    public Step stepC() {
        return stepBuilderFactory.get("stepC")
                .tasklet(new EchoTasklet("** STEP C")).build();
    }

    @Bean
    public Step stepFailure() {
        return stepBuilderFactory.get("stepFailure")
                .tasklet(new EchoTasklet("** Houston, we have a problem!")).build();
    }

}
