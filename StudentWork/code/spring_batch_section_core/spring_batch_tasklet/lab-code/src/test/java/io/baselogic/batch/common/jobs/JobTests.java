package io.baselogic.batch.common.jobs;

import io.baselogic.batch.common.config.BatchConfig;
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
import org.springframework.batch.test.JobScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;


//---------------------------------------------------------------------------//
// Lab: add @ContextConfiguration



//---------------------------------------------------------------------------//
// Lab: add @SpringBatchTest




@SpringBootTest
@RunWith(SpringRunner.class)
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
    // JOBS

    @Autowired
    private Job job;


    //---------------------------------------------------------------------------//
    // JOB PARAMETERS

    public JobParameters getJobParameters() {

        return new JobParametersBuilder()
                .addString("message", "JobParameter message")
                .toJobParameters();

    }



    @Test
    public void test__launch_job__all_steps() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters());

        //-------------------------------------------------------------------//
        // Lab: Assert that the ExitStatus is COMPLETED


        //-------------------------------------------------------------------//
        // Lab: Assert that 4 steps where executed:




    }



} // The End...
