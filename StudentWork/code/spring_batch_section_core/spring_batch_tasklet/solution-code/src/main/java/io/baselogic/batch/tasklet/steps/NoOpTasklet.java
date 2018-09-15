package io.baselogic.batch.tasklet.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * NOTE: Investigate why @Component's are not found until after DefaultBatchConfigurer.class
 */
@Slf4j
public class NoOpTasklet implements Tasklet {

    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("NoOp Tasklet processing...");
        return RepeatStatus.FINISHED;
    }

} // The End...
