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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class, DatabaseConfig.class, JobConfig.class})
//@SpringBootTest(classes = {TestConfig.class, DatabaseConfig.class, JobConfig.class})
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


    @Test
    public void test_echo_job() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            logger.info(logStepExecution(stepExecution));
        });

        assertSoftly(
                softAssertions -> {
                    assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
                    assertThat(jobExecution.getStepExecutions().size()).isEqualTo(3);
                }
        );

        logger.info(logJobExecution(jobExecution));
    }

    //---------------------------------------------------------------------------//


    @Test
    public void test_echo_job__stepA() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepA");

        logger.info(logJobExecution(jobExecution));

        jobExecution.getStepExecutions().forEach((stepExecution) -> {
            logger.info(logStepExecution(stepExecution));

        });

        assertSoftly(
                softAssertions -> {
                    assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);
                    assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
                }
        );


    }


} // The End...
