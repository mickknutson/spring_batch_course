package io.baselogic.batch.skip.jobs;

import io.baselogic.batch.common.config.BatchConfig;
import io.baselogic.batch.common.config.DatabaseConfig;
import io.baselogic.batch.common.config.TestConfig;
import io.baselogic.batch.skip.config.JobConfig;
import io.baselogic.batch.skip.config.StepConfig;
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
    private Job skipJob;


    //---------------------------------------------------------------------------//
    // Lab: Create method to get JobParameters for running Job
    public JobParameters getJobParameters() {
        return jobLauncherTestUtils.getUniqueJobParameters();
    }

    /**
     * Set parameter: 'skip=processor'
     * @return
     */
    public JobParameters getJobParameters_skip_processor() {
        return new JobParametersBuilder()
                .addString("skip", "processor")
                .addLong("commit.interval", 2L)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
    }

    /**
     * Set parameter: 'skip=writer'
     * @return
     */
    public JobParameters getJobParameters_skip_writer() {
        return new JobParametersBuilder()
                .addString("skip", "writer")
                .addLong("commit.interval", 2L)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
    }


    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//


    @Test
    public void test__launch_job__all_steps() throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Set Job to be run for this test:
        jobLauncherTestUtils.setJob(skipJob);


        //-------------------------------------------------------------------//
        // Lab: Create JobExecution with JobParameters:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());



        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions equal 1:
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);


        jobExecution.getStepExecutions().forEach(stepExecution -> {

            assertThat(stepExecution.getReadCount()).isEqualTo(100);
            assertThat(stepExecution.getWriteCount()).isEqualTo(100);
            assertThat(stepExecution.getCommitCount()).isEqualTo(11);

        });

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

    }



    @Test
    public void test__job__skip_processor() throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Set Job to be run for this test:
        jobLauncherTestUtils.setJob(skipJob);


        //-------------------------------------------------------------------//
        // Lab: Create JobExecution with JobParameters:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                getJobParameters_skip_processor());



        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions equal 1:
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);


        jobExecution.getStepExecutions().forEach(stepExecution -> {

            assertThat(stepExecution.getReadCount()).isEqualTo(100);
            assertThat(stepExecution.getWriteCount()).isEqualTo(98);
            assertThat(stepExecution.getCommitCount()).isEqualTo(11);

            assertThat(stepExecution.getReadSkipCount()).isEqualTo(0);
            assertThat(stepExecution.getProcessSkipCount()).isEqualTo(2);
            assertThat(stepExecution.getWriteSkipCount()).isEqualTo(0);

        });

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

    }


    @Test
    public void test__job__skip_writer() throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Set Job to be run for this test:
        jobLauncherTestUtils.setJob(skipJob);


        //-------------------------------------------------------------------//
        // Lab: Create JobExecution with JobParameters:
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                getJobParameters_skip_writer());



        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions equal 1:
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);


        jobExecution.getStepExecutions().forEach(stepExecution -> {

            assertThat(stepExecution.getReadCount()).isEqualTo(100);
            assertThat(stepExecution.getWriteCount()).isEqualTo(98);
            assertThat(stepExecution.getCommitCount()).isEqualTo(27);

            assertThat(stepExecution.getReadSkipCount()).isEqualTo(0);
            assertThat(stepExecution.getProcessSkipCount()).isEqualTo(0);
            assertThat(stepExecution.getWriteSkipCount()).isEqualTo(2);

        });

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

    }


} // The End...
