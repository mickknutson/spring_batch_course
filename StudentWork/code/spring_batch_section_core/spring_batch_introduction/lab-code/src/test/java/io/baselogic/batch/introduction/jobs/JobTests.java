package io.baselogic.batch.introduction.jobs;

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
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobTests {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

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