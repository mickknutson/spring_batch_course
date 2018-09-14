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
import static org.assertj.core.api.SoftAssertions.assertSoftly;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        TestConfig.class,
        DatabaseConfig.class,
        BatchConfig.class,
        JobConfig.class,
        StepConfig.class
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
//@SpringBatchTest
@SpringBootTest
@Slf4j
@SuppressWarnings("Duplicates")
public class IntroductionJobTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    //---------------------------------------------------------------------------//


    @Test
    public void test_hello_world_job() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertSoftly(
                softAssertions -> {
                    assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
                    assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);
                }
        );
    }


} // The End...