package io.baselogic.batch.file_input.jobs;

import io.baselogic.batch.common.config.BatchDao;
import io.baselogic.batch.file_input.config.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private BatchDao batchDao;

    @Before
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
        jdbcTemplate.update("delete from product");
    }

    //---------------------------------------------------------------------------//
    // Jobs

    @Autowired
    private Job job;


    //---------------------------------------------------------------------------//


    public JobParameters getJobParameters() {
//        return jobLauncherTestUtils.getUniqueJobParameters();

        return new JobParametersBuilder()
                .addString("inputResource", "src/main/resources/products.csv")
                .addLong("commit.interval", 2L)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
    }

    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//


    @Test
    public void test_job__all_steps() throws Exception {
        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        log.info(logJobExecution(jobExecution));

        jobExecution.getStepExecutions().forEach(stepExecution -> {
            log.info(logStepExecution(stepExecution));

        });

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
