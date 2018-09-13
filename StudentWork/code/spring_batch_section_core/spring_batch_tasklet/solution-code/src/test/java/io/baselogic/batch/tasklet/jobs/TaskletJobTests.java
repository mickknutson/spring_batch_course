package io.baselogic.batch.tasklet.jobs;

import io.baselogic.batch.tasklet.config.DatabaseConfig;
import io.baselogic.batch.tasklet.config.JobConfig;
import io.baselogic.batch.tasklet.config.TaskletConfig;
import io.baselogic.batch.tasklet.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.JobScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

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
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class, TaskletConfig.class, DatabaseConfig.class, JobConfig.class})
@SpringBatchTest
@SuppressWarnings("Duplicates")
public class TaskletJobTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @Before
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    //---------------------------------------------------------------------------//

    private String logJobExecution(JobExecution jobExecution){

        StringBuilder sb = new StringBuilder();

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            sb.append("Processed: ").append(stepExecution.getStepName()).append("\n");

        });
        return sb.toString();
    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_tasklet_job__all_steps() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            logger.info("Processed: " + stepExecution);

            if (stepExecution.getStepName().equals("stepA")) {
                assertThat(stepExecution.getCommitCount()).isEqualTo(1);
            }
        });

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);

        logger.info(logJobExecution(jobExecution));
    }



} // The End...
