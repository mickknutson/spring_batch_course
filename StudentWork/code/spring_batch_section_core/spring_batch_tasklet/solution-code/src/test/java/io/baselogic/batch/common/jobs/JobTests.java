package io.baselogic.batch.tasklet.jobs;

import io.baselogic.batch.common.config.BatchConfig;
import io.baselogic.batch.common.config.BatchDao;
import io.baselogic.batch.common.config.DatabaseConfig;
import io.baselogic.batch.tasklet.config.JobConfig;
import io.baselogic.batch.tasklet.config.StepConfig;
import io.baselogic.batch.tasklet.config.TestConfig;
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



    @Test
    public void test__launch_job__all_steps() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        //-------------------------------------------------------------------//
        // Lab: Assert that the ExitStatus is COMPLETED
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        //-------------------------------------------------------------------//
        // Lab: Assert that 4 steps where executed:
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(4);
    }



} // The End...
