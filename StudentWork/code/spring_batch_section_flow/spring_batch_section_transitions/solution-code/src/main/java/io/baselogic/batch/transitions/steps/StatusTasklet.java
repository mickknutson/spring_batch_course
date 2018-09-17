package io.baselogic.batch.transitions.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class StatusTasklet implements Tasklet{

    private ExitStatus exitStatus;

    private String message;

    //---------------------------------------------------------------------------//

    public StatusTasklet(ExitStatus exitStatus){
        this.exitStatus = exitStatus;
        this.message = exitStatus.getExitCode();
    }


    //---------------------------------------------------------------------------//

    @Override
    public RepeatStatus execute(final StepContribution stepContribution,
                                final ChunkContext chunkContext) throws Exception {
        log.info("--> Processing STEP [{}] !", message);
        log.info("{} has been executed on thread {}",
                chunkContext.getStepContext().getStepName(),
                Thread.currentThread().getName());

        stepContribution.setExitStatus(exitStatus);

        return RepeatStatus.FINISHED;
    }

    //---------------------------------------------------------------------------//

} // The End...
