package io.baselogic.batch.introduction.jobs;

import io.baselogic.batch.introduction.config.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

//---------------------------------------------------------------------------//
// Lab: add @TestExecutionListeners
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})

//---------------------------------------------------------------------------//
// Lab: add @ContextConfiguration
@ContextConfiguration(classes = {
        TestConfig.class,
        DatabaseConfig.class,
        BatchConfig.class,
        JobConfig.class,
        StepConfig.class
})


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    //---------------------------------------------------------------------------//
    // Jobs


    @Test
    public void test__launch_job__all_steps() throws Exception {


        //-------------------------------------------------------------------//
        // Lab: Launch Job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        //-------------------------------------------------------------------//
        // Lab: Verify Exit Status
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);


        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);


    }


} // The End...