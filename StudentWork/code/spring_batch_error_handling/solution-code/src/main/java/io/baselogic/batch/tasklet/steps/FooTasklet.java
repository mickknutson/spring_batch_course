package io.baselogic.batch.tasklet.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class FooTasklet implements Tasklet {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String message;

    //---------------------------------------------------------------------------//
    @Value("#{jobParameters['messageZ']}")
    public void setMessage(final String message) {
        this.message = message;
    }

    //---------------------------------------------------------------------------//
    @Override
    public RepeatStatus execute(final StepContribution contribution,
                                final ChunkContext chunkContext) throws Exception {
        logger.info("--> STEP FOO message [{}] processing!", message);

        return RepeatStatus.FINISHED;
    }

    //---------------------------------------------------------------------------//
}
