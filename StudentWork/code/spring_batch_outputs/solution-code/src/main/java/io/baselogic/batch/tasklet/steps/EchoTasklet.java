package io.baselogic.batch.tasklet.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class EchoTasklet implements Tasklet{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String message;

    //---------------------------------------------------------------------------//

    public EchoTasklet(String message){
        this.message = message;
    }


    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(final StepContribution contribution,
                                final ChunkContext chunkContext) throws Exception {
        logger.info("--> STEP message [{}] processing!", message);

        return RepeatStatus.FINISHED;
    }

    //---------------------------------------------------------------------------//

}
