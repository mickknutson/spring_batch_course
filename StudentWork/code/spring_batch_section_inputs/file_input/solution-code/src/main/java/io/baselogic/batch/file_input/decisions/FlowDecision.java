package io.baselogic.batch.file_input.decisions;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class FlowDecision implements JobExecutionDecider {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
