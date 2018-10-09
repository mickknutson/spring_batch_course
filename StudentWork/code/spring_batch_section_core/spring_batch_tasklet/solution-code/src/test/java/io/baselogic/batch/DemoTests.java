package io.baselogic.batch;

import io.baselogic.batch.common.config.BatchConfig;
import io.baselogic.batch.common.config.BatchDao;
import io.baselogic.batch.common.config.DatabaseConfig;
import io.baselogic.batch.tasklet.config.JobConfig;
import io.baselogic.batch.tasklet.config.StepConfig;
import io.baselogic.batch.common.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;


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
 -> MetaDataInstanceFactory
 StepRunner
 * Utility class for executing steps outside of a {@link Job}. This is useful in
 * end to end testing in order to allow for the testing of a step individually
 * without running every Step in a job.

 StepScopeTestExecutionListener
 StepScopeTestUtils
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        DatabaseConfig.class,
        BatchConfig.class,
        JobConfig.class,
        StepConfig.class
})
@SpringBatchTest
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class DemoTests {

    private static final String LINE = "+" + new String(new char[40]).replace('\0', '-') + "+";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private BatchDao batchDao;

    @Before
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    //---------------------------------------------------------------------------//
    // Jobs

    @Autowired
    private Job job;


    //---------------------------------------------------------------------------//


    public JobParameters getJobParameters() {
        // given
//        return jobLauncherTestUtils.getUniqueJobParameters();

        return new JobParametersBuilder()
                .addString("message", "JobParameter message")
                .toJobParameters();

    }

    /*public StepExecution getStepExecution() {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        execution.getExecutionContext().putString("message", "StepExecution message");
        return execution;
    }*/

    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//


    @Test
    public void test__launch_job__all_steps() throws Exception {
        log.info(LINE);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        //-------------------------------------------------------------------//
        // Lab: Assert that the ExitStatus is COMPLETED
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        //-------------------------------------------------------------------//
        // Lab: Assert that 4 steps where executed:
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(4);


        log.info(LINE);
    }


    //---------------------------------------------------------------------------//


    @Test
    public void test_echo_job__stepA() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepA");

        log.info(logJobExecution(jobExecution));

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            log.info(logStepExecution(stepExecution));

        });

        assertSoftly(
                softAssertions -> {
                    assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);
                    assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
                }
        );


    }



    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//

    /**
     * Need to create a query for a single job execution
     * @param jobExecution
     * @return
     */
    protected String logJobExecution(JobExecution jobExecution) {

        String results = batchDao.logJobExecutions(jobExecution);
        batchDao.logJobExecutions();
        batchDao.logStepExecutions();
        batchDao.countJobExecutions();
        batchDao.countJobInstances();
        batchDao.consoleLine('m');

        return results;
    }


    /**
     * Need to create a query for a single step execution
     * @param stepExecution
     * @return
     */
    protected String logStepExecution(StepExecution stepExecution) {

        String results = batchDao.logStepExecutions(stepExecution);

        return results;
    }

} // The End...
