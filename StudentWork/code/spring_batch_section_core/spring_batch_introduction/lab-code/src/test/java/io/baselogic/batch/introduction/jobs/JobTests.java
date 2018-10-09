package io.baselogic.batch.introduction.jobs;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void test__launch_job__all_steps() throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Launch Job


        //-------------------------------------------------------------------//
        // Lab: Verify Exit Status



        //-------------------------------------------------------------------//
        // Lab: Verify Step Executions




    }


} // The End...