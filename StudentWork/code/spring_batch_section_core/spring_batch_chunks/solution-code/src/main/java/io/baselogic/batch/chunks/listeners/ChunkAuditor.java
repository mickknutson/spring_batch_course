package io.baselogic.batch.chunks.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
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

        int count = context.getStepContext().getStepExecution().getReadCount();

        log.info("--> After Chunk:");
        log.info("\t Step Name: {}", stepName);
        log.info("\t ItemCount: {}", count);
    }

} // The End...
