package io.baselogic.batch.decisions.steps;

import lombok.extern.slf4j.Slf4j;
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
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {
        log.info("--> Processing STEP [{}] !", message);
        log.info("\t id: [{}], name: [{}]",
                chunkContext.getStepContext().getId(),
                chunkContext.getStepContext().getStepName()
        );


        return RepeatStatus.FINISHED;
    }

    //---------------------------------------------------------------------------//

} // The End...
