package io.baselogic.batch.tasklet.jobs;

import io.baselogic.batch.tasklet.config.EchoJobWithParametersConfig;
import io.baselogic.batch.tasklet.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.ExecutionContextTestUtils;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;


/**
 *
 * Need to look at the classes from the batch-test library:

 AssertFile
 DataSourceInitializer
 ExecutionContextTestUtils
 -> JobLauncherTestUtils
 JobRepositoryTestUtils
 JobScopeTestExecutionListener
 JobScopeTestUtils
 JsrTestUtils
 MetaDataInstanceFactory
 StepRunner
 * Utility class for executing steps outside of a {@link Job}. This is useful in
 * end to end testing in order to allow for the testing of a step individually
 * without running every Step in a job.

 StepScopeTestExecutionListener
 StepScopeTestUtils
 *
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, EchoJobWithParametersConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
@ComponentScan(basePackages = {"io.baselogic.batch.introduction.steps"})
public class EchoJobWithParametersTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private Step stepA;

    //---------------------------------------------------------------------------//

    public JobParameters getJobParameters(String shouldFail) {
        return new JobParametersBuilder()
            .addLong("commit.interval", 2L)
            .addLong("timestamp", System.currentTimeMillis())
            .addString("inputResource", "products.csv")
            .addString("shouldFail", shouldFail)

            .addString("messageZ", "job parameter message ZZZ")
            .toJobParameters();
    }

    private void logJobExecution(JobExecution jobExecution){
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: {}", stepExecution.getStepName());

        }

    }

    @Test
    public void test_echo_job_with_parameters() throws Exception {
        logger.info("--------------------------------------------->>>");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters("false"));

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: " + stepExecution);

            if (stepExecution.getStepName().equals("stepA")) {
                assertThat(stepExecution.getCommitCount()).isEqualTo(1);
            }
        }

        logger.info("***** ExecutionContextTestUtils: "+ ExecutionContextTestUtils.getValueFromJob(jobExecution, "inputResource"));

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(4);

        logger.info("<<<---------------------------------------------");
    }


    // FIXME: Need to handle exception
    @Test
    public void test_echo_job_with_parameters__should_Fail() throws Exception {
        logger.info("--------------------------------------------->>>");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters("true"));

        logJobExecution(jobExecution);

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(2);

        logger.info("<<<---------------------------------------------");
    }


    @Test
    public void test_echo_job_with_parameters__JobLauncher() throws Exception {

        JobExecution jobExecution = jobLauncher.run(job, getJobParameters("false"));

        logJobExecution(jobExecution);

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(4);
    }


} // The End...
