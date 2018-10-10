package io.baselogic.batch.common.jobs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.junit4.SpringRunner;


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
