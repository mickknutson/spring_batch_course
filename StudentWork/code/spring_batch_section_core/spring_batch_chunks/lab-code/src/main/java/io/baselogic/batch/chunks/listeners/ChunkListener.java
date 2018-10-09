package io.baselogic.batch.chunks.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class ChunkListener {

    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeChunk lifecycle method
    @BeforeChunk
    public void beforeChunk(ChunkContext context) {

        String stepName = context.getStepContext().getStepExecution().getStepName();

        log.info("--> Before Chunk:");
        log.info("\t Step Name: {}", stepName);
    }

    //---------------------------------------------------------------------------//
    // Lab: Create @AfterChunk lifecycle method
    @AfterChunk
    public void afterChunk(ChunkContext context) {

        String stepName = context.getStepContext().getStepExecution().getStepName();

        int readCount = context.getStepContext().getStepExecution().getReadCount();
        int writeCount = context.getStepContext().getStepExecution().getWriteCount();
        int writeSkipCount = context.getStepContext().getStepExecution().getWriteSkipCount();

        log.info("--> After Chunk:");
        log.info("\t Step Name: {}", stepName);
        log.info("\t readCount: {}", readCount);
        log.info("\t writeCount: {}", writeCount);
        log.info("\t writeSkipCount: {}", writeSkipCount);
    }

} // The End...
