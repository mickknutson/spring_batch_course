package io.baselogic.batch.jobrepository.jobs;

import io.baselogic.batch.jobrepository.config.DatabaseConfig;
import io.baselogic.batch.jobrepository.config.JobConfig;
import io.baselogic.batch.jobrepository.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
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
@SpringBootTest(classes = {TestConfig.class, DatabaseConfig.class, JobConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
//@DirtiesContext
@SuppressWarnings("Duplicates")
public class JobRepositoryJobTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    //---------------------------------------------------------------------------//

    private String logJobExecution(JobExecution jobExecution) {

        StringBuilder sb = new StringBuilder();

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            sb.append("Processed: ").append(stepExecution.getStepName()).append("\n");

        });

        return sb.toString();
    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_echo_job() throws Exception {
        logger.info("--------------------------------------------->>>");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            logger.info("Processed: " + stepExecution);

            if (stepExecution.getStepName().equals("stepA")) {
                assertThat(stepExecution.getCommitCount()).isEqualTo(1);
            }
        });

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);

        logger.info(logJobExecution(jobExecution));

        logger.info("<<<---------------------------------------------");
    }


} // The End...
