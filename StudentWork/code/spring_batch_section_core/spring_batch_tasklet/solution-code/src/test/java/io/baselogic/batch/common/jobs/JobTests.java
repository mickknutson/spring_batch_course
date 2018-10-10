package io.baselogic.batch.common.jobs;

import io.baselogic.batch.common.config.BatchConfig;
import io.baselogic.batch.common.config.DatabaseConfig;
import io.baselogic.batch.common.config.TestConfig;
import io.baselogic.batch.tasklet.config.JobConfig;
import io.baselogic.batch.tasklet.config.StepConfig;
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


//---------------------------------------------------------------------------//
// Lab: add @ContextConfiguration
@ContextConfiguration(classes = {
        TestConfig.class,
        DatabaseConfig.class,
        BatchConfig.class,
        JobConfig.class,
        StepConfig.class
})

//---------------------------------------------------------------------------//
// Lab: add @SpringBatchTest
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
    // JOBS

    @Autowired
    private Job job;

    //---------------------------------------------------------------------------//
    // JOB PARAMETERS

    public JobParameters getJobParameters() {
        // given
//        return jobLauncherTestUtils.getUniqueJobParameters();

        return new JobParametersBuilder()
                .addString("message", "JobParameter message")
                .toJobParameters();

    }



    @Test
    public void test__launch_job__all_steps() throws Exception {

        jobLauncherTestUtils.setJob(job);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                getJobParameters()
        );

        //-------------------------------------------------------------------//
        // Lab: Assert that the ExitStatus is COMPLETED
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        //-------------------------------------------------------------------//
        // Lab: Assert that 4 steps where executed:
        assertThat(
                jobExecution.getStepExecutions().size()
        ).isEqualTo(4);
    }



} // The End...
