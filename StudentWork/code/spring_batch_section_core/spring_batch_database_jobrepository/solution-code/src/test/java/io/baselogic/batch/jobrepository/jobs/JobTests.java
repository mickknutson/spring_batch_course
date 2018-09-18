package io.baselogic.batch.jobrepository.jobs;

import io.baselogic.batch.common.config.BatchDao;
import io.baselogic.batch.jobrepository.config.DatabaseConfig;
import io.baselogic.batch.jobrepository.config.JobConfig;
import io.baselogic.batch.jobrepository.config.StepConfig;
import io.baselogic.batch.jobrepository.config.TestConfig;
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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        DatabaseConfig.class,
        JobConfig.class,
        StepConfig.class
})
@SpringBatchTest
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
    private Job job;


    //---------------------------------------------------------------------------//


    public JobParameters getJobParameters() {
        return jobLauncherTestUtils.getUniqueJobParameters();

//        return new JobParametersBuilder()
//                .addLong("commit.interval", 2L)
//                .addLong("timestamp", System.currentTimeMillis())
//                .toJobParameters();
    }

    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//

    @Test
    public void test_echo_job() throws Exception {

        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        log.info(logJobExecution(jobExecution));

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            log.debug(logStepExecution(stepExecution));

        });


        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);
    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_echo_job__stepA() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepA");

        log.info(logJobExecution(jobExecution));


        if(log.isDebugEnabled()) {

            jobExecution.getStepExecutions().forEach(stepExecution -> {
                log.debug(logStepExecution(stepExecution));

            });

            // List all steps from the database:
            log.debug(batchDao.logStepExecutions());
        }


        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);

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
