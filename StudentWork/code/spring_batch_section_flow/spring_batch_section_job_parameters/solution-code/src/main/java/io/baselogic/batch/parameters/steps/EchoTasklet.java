package io.baselogic.batch.parameters.steps;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class EchoTasklet implements Tasklet {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private String message;

    //---------------------------------------------------------------------------//

    public EchoTasklet(String message){
        this.message = message;
    }


    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {

        log.info("==> Step [{}] has been executed on thread {}",
                chunkContext.getStepContext().getStepName(),
                Thread.currentThread().getName());
        log.info("Job Parameter 'message': [{}]", message);

//        chunkContext.getStepContext().getStepExecutionContext().put("stepKey",
//                "stepKeyValue."+chunkContext.getStepContext().getStepName());

        return RepeatStatus.FINISHED;
    }


} // The End...
