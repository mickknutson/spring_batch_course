package io.baselogic.batch.tasklet.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class NoOpTasklet implements Tasklet, StepExecutionListener {

    //---------------------------------------------------------------------------//

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        log.info("beforeStep()  ExecutionContext size: {}", stepExecution.getExecutionContext().size());

        Map<String, JobParameter> params = stepExecution.getJobParameters().getParameters();

        params.forEach(
                (k, v) -> log.info("Job Parameter: {}, {}", k, v)
        );

        // Set stepExecutionContext parameter:
        stepExecution.getExecutionContext().putString("stepKey", "stepKeyValue");
        stepExecution.getExecutionContext().putString("message", "noOpTasklet message");
    }


    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {
        log.info("==> Step [{}] has been executed on thread {}",
                chunkContext.getStepContext().getStepName(),
                Thread.currentThread().getName());
        return RepeatStatus.FINISHED;
    }


    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {

        log.info("afterStep() ExecutionContext size: {}", stepExecution.getExecutionContext().size());

        stepExecution.getExecutionContext().entrySet()
                .forEach(e -> log.info("stepExecutionContext entry: [{}]", e));

        return ExitStatus.COMPLETED;
    }

} // The End...
