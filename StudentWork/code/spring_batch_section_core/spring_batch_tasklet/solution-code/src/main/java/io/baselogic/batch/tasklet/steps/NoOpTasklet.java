package io.baselogic.batch.tasklet.steps;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * FIXME: Investigate why @Component's are not found until after DefaultBatchConfigurer.class
 */
//@Component
//@StepScope
@Slf4j
public class NoOpTasklet implements Tasklet {

    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("NoOp Tasklet processing...");
        return RepeatStatus.FINISHED;
    }

} // The End...
