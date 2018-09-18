package io.baselogic.batch.transitions.jobs;

import io.baselogic.batch.common.config.BatchDao;
import io.baselogic.batch.transitions.config.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {TestConfig.class, DatabaseConfig.class, BatchConfig.class, JobConfig.class, StepConfig.class})
@SpringBatchTest
@RunWith(SpringRunner.class)
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobTests {

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
    @Qualifier("job")
    private Job job;

    @Autowired
    @Qualifier("continueOnExitStatusJob")
    private Job continueOnExitStatusJob;

    @Autowired
    @Qualifier("failingJob")
    private Job failingJob;

    @Autowired
    @Qualifier("jobFlow")
    private Job jobFlow;

    //---------------------------------------------------------------------------//


    public JobParameters getJobParameters() {
        return jobLauncherTestUtils.getUniqueJobParameters();

//        return new JobParametersBuilder()
//                .addLong("commit.interval", 2L)
//                .addLong("timestamp", System.currentTimeMillis())
//                .toJobParameters();
    }

    //---------------------------------------------------------------------------//


    @Test
    public void test__job__all_steps() throws Exception {

        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        if(log.isDebugEnabled()) {

            log.debug(logJobExecution(jobExecution));

            jobExecution.getStepExecutions().forEach((stepExecution) -> {
                log.debug(logStepExecution(stepExecution));

            });
        }

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(4);
    }

    //---------------------------------------------------------------------------//

    @Test
    public void test__continueOnExitStatusJob__all_steps() throws Exception {
        jobLauncherTestUtils.setJob(continueOnExitStatusJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        if(log.isDebugEnabled()) {

            log.debug(logJobExecution(jobExecution));

            jobExecution.getStepExecutions().forEach((stepExecution) -> {
                log.debug(logStepExecution(stepExecution));

            });
        }

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(5);
    }


    //---------------------------------------------------------------------------//

    @Test
    public void test_failingJob__all_steps() throws Exception {
        jobLauncherTestUtils.setJob(failingJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        log.info(logJobExecution(jobExecution));

        if(log.isDebugEnabled()) {

            jobExecution.getStepExecutions().forEach((stepExecution) -> {
                log.debug(logStepExecution(stepExecution));

            });
        }

        assertThat(jobExecution.getExitStatus().getExitCode())
                .isEqualTo(ExitStatus.FAILED.getExitCode());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);
    }


    //---------------------------------------------------------------------------//

    @Test
    public void test_jobFlow__all_steps() throws Exception {
        jobLauncherTestUtils.setJob(jobFlow);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        log.info(logJobExecution(jobExecution));

        if(log.isDebugEnabled()) {

            log.debug(logJobExecution(jobExecution));

            jobExecution.getStepExecutions().forEach((stepExecution) -> {
                log.debug(logStepExecution(stepExecution));

            });
        }

        assertThat(jobExecution.getExitStatus().getExitCode())
                .isEqualTo(ExitStatus.COMPLETED.getExitCode());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(5);
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

//        return batchDao.logJobExecutions();

        String results = batchDao.logJobExecutions(jobExecution);
        batchDao.logJobExecutions();
        batchDao.logStepExecutions();
        batchDao.countJobExecutions();
        batchDao.countJobInstances();

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
