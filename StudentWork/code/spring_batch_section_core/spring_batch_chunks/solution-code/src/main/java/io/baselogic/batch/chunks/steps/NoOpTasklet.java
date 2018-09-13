package io.baselogic.batch.chunks.steps;

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
public class NoOpTasklet implements Tasklet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("NoOp Tasklet processing...");
        return RepeatStatus.FINISHED;
    }

} // The End...