package io.baselogic.batch.restart.jobs;

import io.baselogic.batch.common.config.BatchConfig;
import io.baselogic.batch.common.config.DatabaseConfig;
import io.baselogic.batch.common.config.TestConfig;
import io.baselogic.batch.restart.config.JobConfig;
import io.baselogic.batch.restart.config.StepConfig;
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

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

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
        return new JobParametersBuilder()
                .toJobParameters();
    }

    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//


    @Test
    public void noop() throws Exception {}

//    @Test
    public void test__launch_job__all_steps() throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Set Job to be run for this test:
        jobLauncherTestUtils.setJob(job);

        JobExecution jobExecution = null;

        try {
            // Run # 1 fails:
            jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        } catch (Exception e){
            log.error("jobExecution failed: {}", e.getMessage(), e);
        }

        try {
            // Run # 2 fails:
            jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        } catch (Exception e){
            log.error("jobExecution failed: {}", e.getMessage(), e);
        }

        try {
            // Run #3 passes:
            jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        } catch (Exception e){
            log.error("jobExecution failed: {}", e.getMessage(), e);
        }

        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions equal 1:
//        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);
//
//
//        jobExecution.getStepExecutions().forEach(stepExecution -> {
//
//            assertThat(stepExecution.getReadCount()).isEqualTo(100);
//            assertThat(stepExecution.getWriteCount()).isEqualTo(100);
//            assertThat(stepExecution.getCommitCount()).isEqualTo(11);
//
//        });
//
//        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

    }


} // The End...
