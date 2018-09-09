package io.baselogic.batch.tasklet.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class FailingTasklet implements Tasklet{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String message;

    private boolean shouldFail = false;

    //---------------------------------------------------------------------------//
    public FailingTasklet(String message){
        this.message = message;
    }

//    public FailingTasklet(String message, boolean shouldFail){
//        this.message = message;
//        this.shouldFail = shouldFail;
//    }

    //---------------------------------------------------------------------------//
    @Override
    public RepeatStatus execute(final StepContribution contribution,
                                final ChunkContext chunkContext) throws Exception {
        logger.info("--> STEP message [{}] processing!", message);

        logger.info("Should this step fail: {}", isShouldFail());

        if (isShouldFail()){
            throw new Exception("Tasklet FAILED");
        }

        return RepeatStatus.FINISHED;
    }

    //---------------------------------------------------------------------------//
    public boolean isShouldFail() {
        return shouldFail;
    }
    public void setShouldFail(boolean shouldFail) {
        this.shouldFail = shouldFail;
    }

}
