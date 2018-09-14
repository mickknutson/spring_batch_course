package io.baselogic.batch.jobrepository.steps;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


@Slf4j
public class EchoTasklet implements Tasklet{

    private String message;

    //---------------------------------------------------------------------------//

    public EchoTasklet(String message){
        this.message = message;
    }


    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(final StepContribution contribution,
                                final ChunkContext chunkContext) throws Exception {
        log.info("--> STEP message [{}] processing!", message);

        return RepeatStatus.FINISHED;
    }

    //---------------------------------------------------------------------------//

}
