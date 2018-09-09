package io.baselogic.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FlowDecision implements JobExecutionDecider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        Random generator = new Random();
        int randomInt = generator.nextInt();

        logger.info("Executing Decision with randomInt = " + randomInt);

        if(randomInt % 2 == 0){
            return FlowExecutionStatus.COMPLETED;
        }
        return FlowExecutionStatus.FAILED;
    }
}
