package io.baselogic.batch.chunks.jobs;

import io.baselogic.batch.chunks.config.DatabaseConfig;
import io.baselogic.batch.chunks.config.JobConfig;
import io.baselogic.batch.chunks.config.TaskletConfig;
import io.baselogic.batch.chunks.config.TestConfig;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, TaskletConfig.class, DatabaseConfig.class, JobConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
@DirtiesContext
@SuppressWarnings("Duplicates")
public class ChunksJobTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    //---------------------------------------------------------------------------//

    public JobParameters getJobParameters() {
        return new JobParametersBuilder()
                .addLong("commit.interval", 2L)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
    }

    /**
     * FIXME: Add StringBuilder and return data in this method:
     * @param jobExecution
     */
    private void logJobExecution(JobExecution jobExecution){
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: {}", stepExecution.getStepName());

        }

    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_tasklet_job__all_steps() throws Exception {
        logger.info("--------------------------------------------->>>");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: " + stepExecution);

            if (stepExecution.getStepName().equals("stepA")) {
                assertThat(stepExecution.getCommitCount()).isEqualTo(1);
            }
        }

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);

        //logger.info(logJobExecution(jobExecution));
        logJobExecution(jobExecution);


        logger.info("<<<---------------------------------------------");
    }



} // The End...
