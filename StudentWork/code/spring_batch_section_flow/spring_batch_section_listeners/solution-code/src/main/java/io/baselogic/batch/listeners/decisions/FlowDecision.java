package io.baselogic.batch.listeners.decisions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

@Slf4j
public class FlowDecision implements JobExecutionDecider {

    private int count = 0;

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;

        if(count % 2 == 0){
            log.info("*** --> FlowDecision is EVEN");
            return new FlowExecutionStatus("EVEN");
        } else {
            log.info("*** --> FlowDecision is ODD");
            return new FlowExecutionStatus("ODD");
        }
    }

} // The End...
