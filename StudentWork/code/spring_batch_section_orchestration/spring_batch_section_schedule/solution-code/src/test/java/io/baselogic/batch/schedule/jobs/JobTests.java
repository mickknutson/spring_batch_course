package io.baselogic.batch.schedule.jobs;

import io.baselogic.batch.common.config.BatchConfig;
import io.baselogic.batch.common.config.BatchDao;
import io.baselogic.batch.common.config.DatabaseConfig;
import io.baselogic.batch.common.config.TestConfig;
import io.baselogic.batch.schedule.config.JobConfig;
import io.baselogic.batch.schedule.config.StepConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(classes = {
        TestConfig.class,
        DatabaseConfig.class,
        BatchConfig.class,
        JobConfig.class,
        StepConfig.class
})

@SpringBatchTest

@SpringBootTest
@RunWith(SpringRunner.class)
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobTests {

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private static final String LINE = "+" + new String(new char[55]).replace('\0', '-') + "+";

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
    // Lab: Autowire the Job
    @Autowired
    private Job job;


    //---------------------------------------------------------------------------//
    // Lab: Create method to get JobParameters for running Job
    public JobParameters getJobParameters() {
        return jobLauncherTestUtils.getUniqueJobParameters();
    }


    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//

    @Test
    public void noop() throws Exception {}

//    @Test
    public void test__launch_job__all_steps() throws Exception {
        log.info(LINE);

        //-------------------------------------------------------------------//
        // Lab: Set Job to be run for this test:
        jobLauncherTestUtils.setJob(job);


        //-------------------------------------------------------------------//
        // Lab: Create JobExecution with JobParameters:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                getJobParameters());



        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions equal 1:
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(0);


//        jobExecution.getStepExecutions().forEach(stepExecution -> {
//
//            assertThat(stepExecution.getReadCount()).isEqualTo(0);
//            assertThat(stepExecution.getWriteCount()).isEqualTo(0);
//            assertThat(stepExecution.getCommitCount()).isEqualTo(0);
//
//            assertThat(stepExecution.getWriteSkipCount()).isEqualTo(0);
//            assertThat(stepExecution.getProcessSkipCount()).isEqualTo(0);
//
//        });
//
//        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        log.info(LINE);

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
