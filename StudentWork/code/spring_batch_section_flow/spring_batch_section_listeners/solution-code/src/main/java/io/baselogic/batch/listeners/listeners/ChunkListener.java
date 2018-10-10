package io.baselogic.batch.listeners.listeners;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class ChunkListener {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeChunk and log step details
    @BeforeChunk
    public void beforeChunk(ChunkContext context) {

        String stepName = context.getStepContext().getStepExecution().getStepName();

        log.info("--> Before Chunk:");
        log.info("\t Step Name: {}", stepName);
    }


    //---------------------------------------------------------------------------//
    // Lab: Create @AfterChunk and log step details
    @AfterChunk
    public void afterChunk(ChunkContext context) {

        String stepName = context.getStepContext().getStepExecution().getStepName();

        int count = context.getStepContext().getStepExecution().getReadCount();

        log.info("--> After Chunk:");
        log.info("\t Step Name: {}", stepName);
        log.info("\t ItemCount: {}", count);
    }

} // The End...
