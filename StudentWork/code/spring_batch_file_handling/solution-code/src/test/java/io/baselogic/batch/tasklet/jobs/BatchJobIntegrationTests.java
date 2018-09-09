package io.baselogic.batch.tasklet.jobs;

import io.baselogic.batch.tasklet.config.BatchConfig;
import io.baselogic.batch.tasklet.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.ExecutionContextTestUtils;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, BatchConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        JobScopeTestExecutionListener.class,
        StepScopeTestExecutionListener.class
})
@ComponentScan(basePackages = {"io.baselogic.batch.introduction.steps"})
public class BatchJobIntegrationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Value("${inputName}")
    private String inputName;

    @Value("${stepName}")
    private String stepName;

    @Value("${jobName}")
    private String jobName;


    @Test
    public void testLaunchJob() throws Exception {
        logger.info("--------------------------------------------->>>");
        JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addLong("commit.interval", 2L)
                .toJobParameters());

        assertThat(ExitStatus.COMPLETED).isEqualTo(jobExecution.getExitStatus());
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            logger.info("Processed: " + stepExecution);

            if (stepExecution.getStepName().equals(stepName)) {
                // The effect of the retries
                assertThat(new Double(Math.ceil(stepExecution.getReadCount() / 2. + 1)).intValue())
                .isEqualTo(stepExecution.getCommitCount());
            }
        }
        logger.info("<<<---------------------------------------------");
    }


    @Test
    public void run_jobLauncher_BatchJob() throws Exception {

        jobLauncher.run(job, new JobParametersBuilder()
                .addString("inputResource", "classpath:products.csv")
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters()
        );
    }

} // The End...
