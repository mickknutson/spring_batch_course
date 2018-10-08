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


//---------------------------------------------------------------------------//
// Lab: add @ContextConfiguration




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
    public void test_hello_world_job() throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Launch Job


        //-------------------------------------------------------------------//
        // Lab: Verify Exit Status



        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions




    }


} // The End...