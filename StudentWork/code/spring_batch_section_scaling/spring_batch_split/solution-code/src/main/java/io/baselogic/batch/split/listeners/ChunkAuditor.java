package io.baselogic.batch.split.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class ChunkAuditor {

    @BeforeChunk
    public void beforeChunk(ChunkContext context) {

        String stepName = context.getStepContext().getStepExecution().getStepName();

        log.info("--> Before Chunk:");
        log.info("\t Step Name: {}", stepName);
    }

    @AfterChunk
    public void afterChunk(ChunkContext context) {

        String stepName = context.getStepContext().getStepExecution().getStepName();
        long id = context.getStepContext().getStepExecution().getId();

        int count = context.getStepContext().getStepExecution().getReadCount();

        log.info("--> After Chunk:");
        log.info("\t Step ID: {}", id);
        log.info("\t Step Name: {}", stepName);
        log.info("\t ItemCount: {}", count);

        log.info("\n\t StepExecution: {}", context.getStepContext().getStepExecution());
    }

} // The End...
