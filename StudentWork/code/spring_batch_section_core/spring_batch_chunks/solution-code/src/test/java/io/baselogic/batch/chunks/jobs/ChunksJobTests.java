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
import static org.assertj.core.api.SoftAssertions.assertSoftly;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, TaskletConfig.class, DatabaseConfig.class, JobConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
//@DirtiesContext
@SuppressWarnings("Duplicates")
public class ChunksJobTests {

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

    protected String logStepExecution(StepExecution stepExecution) {

        StringBuilder sb = new StringBuilder();

        sb.append("------------------------------------------------");
        sb.append("Processed: ").append(stepExecution);
        sb.append("------------------------------------------------");
        sb.append("stepName: ").append(stepExecution.getStepName());
        sb.append("status: ").append(stepExecution.getStatus());
        sb.append("readCount: ").append(stepExecution.getReadCount());
        sb.append("writeCount: ").append(stepExecution.getWriteCount());
        sb.append("commitCount: ").append(stepExecution.getCommitCount());
        sb.append("rollbackCount: ").append(stepExecution.getRollbackCount());
        sb.append("readSkipCount: ").append(stepExecution.getReadSkipCount());
        sb.append("processSkipCount: ").append(stepExecution.getProcessSkipCount());
        sb.append("writeSkipCount: ").append(stepExecution.getWriteSkipCount());
        sb.append("startTime: ").append(stepExecution.getStartTime());
        sb.append("endTime: ").append(stepExecution.getEndTime());
        sb.append("lastUpdated: ").append(stepExecution.getLastUpdated());
        sb.append("executionContext: ").append(stepExecution.getExecutionContext());
        sb.append("exitStatus: ").append(stepExecution.getExitStatus());
        sb.append("terminateOnly: ").append(stepExecution.isTerminateOnly());
        sb.append("filterCount: ").append(stepExecution.getFilterCount());
        sb.append("failureExceptions: ").append(stepExecution.getFailureExceptions());

        sb.append("------------------------------------------------");

        return sb.toString();
    }

    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//
    //---------------------------------------------------------------------------//

    public JobParameters getJobParameters() {
        return new JobParametersBuilder()
                .addLong("commit.interval", 2L)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_job__all_steps() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            logger.info(logStepExecution(stepExecution));
        });

        assertSoftly(
                softAssertions -> {
                    assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
                    assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);
                }
        );
    }



} // The End...
