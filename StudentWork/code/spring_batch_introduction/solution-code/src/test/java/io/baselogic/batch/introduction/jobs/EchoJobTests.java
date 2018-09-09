package io.baselogic.batch.introduction.jobs;

import io.baselogic.batch.introduction.config.EchoJobConfig;
import io.baselogic.batch.introduction.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.test.ExecutionContextTestUtils;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, EchoJobConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
public class EchoJobTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    //---------------------------------------------------------------------------//

    public JobParameters getJobParameters(String shouldFail) {
        return new JobParametersBuilder()
                .addLong("commit.interval", 2L)
                .addLong("timestamp", System.currentTimeMillis())
                .addString("inputResource", "products.csv")
                .addString("shouldFail", shouldFail)

                .addString("messageZ", "job parameter message ZZZ")
                .toJobParameters();
    }

    private void logJobExecution(JobExecution jobExecution){
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: {}", stepExecution.getStepName());

        }

    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_echo_job() throws Exception {
        logger.info("--------------------------------------------->>>");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(getJobParameters("false"));

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: " + stepExecution);

            if (stepExecution.getStepName().equals("stepA")) {
                assertThat(stepExecution.getCommitCount()).isEqualTo(1);
            }
        }

        logger.info("ExecutionContextTestUtils: "+ ExecutionContextTestUtils.getValueFromJob(jobExecution, "name"));

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);

        logger.info("<<<---------------------------------------------");
    }


} // The End...
