package io.baselogic.batch.chunks.jobs;

import io.baselogic.batch.chunks.config.DatabaseConfig;
import io.baselogic.batch.chunks.config.JobConfig;
import io.baselogic.batch.chunks.config.StepConfig;
import io.baselogic.batch.chunks.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;


@ContextConfiguration(classes = {TestConfig.class, DatabaseConfig.class, JobConfig.class, StepConfig.class})
@SpringBatchTest
@RunWith(SpringRunner.class)
@SuppressWarnings("Duplicates")
public class ChunksJobTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @Before
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

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

        sb.append("------------------------------------------------\n");
        sb.append("Processed: ").append(stepExecution).append("\n");
        sb.append("------------------------------------------------\n");
        sb.append("stepName: ").append(stepExecution.getStepName()).append("\n");
        sb.append("status: ").append(stepExecution.getStatus()).append("\n");
        sb.append("readCount: ").append(stepExecution.getReadCount()).append("\n");
        sb.append("writeCount: ").append(stepExecution.getWriteCount()).append("\n");
        sb.append("commitCount: ").append(stepExecution.getCommitCount()).append("\n");
        sb.append("rollbackCount: ").append(stepExecution.getRollbackCount()).append("\n");
        sb.append("readSkipCount: ").append(stepExecution.getReadSkipCount()).append("\n");
        sb.append("processSkipCount: ").append(stepExecution.getProcessSkipCount()).append("\n");
        sb.append("writeSkipCount: ").append(stepExecution.getWriteSkipCount()).append("\n");
        sb.append("startTime: ").append(stepExecution.getStartTime()).append("\n");
        sb.append("endTime: ").append(stepExecution.getEndTime()).append("\n");
        sb.append("lastUpdated: ").append(stepExecution.getLastUpdated()).append("\n");
        sb.append("exitStatus: ").append(stepExecution.getExitStatus()).append("\n");
        sb.append("terminateOnly: ").append(stepExecution.isTerminateOnly()).append("\n");
        sb.append("filterCount: ").append(stepExecution.getFilterCount()).append("\n");
        sb.append("failureExceptions: ").append(stepExecution.getFailureExceptions()).append("\n");
        sb.append("------------------------------------------------\n");
        sb.append("executionContext: ").append(stepExecution.getExecutionContext()).append("\n");
        sb.append("------------------------------------------------\n");

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

        logger.info(logJobExecution(jobExecution));

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
