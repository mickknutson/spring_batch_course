package io.baselogic.batch.tasklet.steps;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


//---------------------------------------------------------------------------//
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class EchoTasklet implements Tasklet {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private String message;

    //---------------------------------------------------------------------------//

    public EchoTasklet(String message){
        this.message = message;
    }


    //---------------------------------------------------------------------------//
    // Lab: Create Tasklet execute method
    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {

        //-------------------------------------------------------------------//
        // Lab: Log the Step name from the StepContext:
        log.info("==> Step [{}] has been executed on thread {}",

                chunkContext.getStepContext().getStepName(),
                Thread.currentThread().getName());
        log.info("Job Parameter 'message': [{}]", message);

        return RepeatStatus.FINISHED;
    }


} // The End...
