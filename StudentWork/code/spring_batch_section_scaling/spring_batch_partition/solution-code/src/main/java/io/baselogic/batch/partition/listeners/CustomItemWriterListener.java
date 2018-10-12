package io.baselogic.batch.partition.listeners;

import io.baselogic.batch.partition.domain.Movie;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;

import java.util.List;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class CustomItemWriterListener {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @BeforeWrite
    public void beforeWrite(List<Movie> items){
        log.info("______#BEFORE WRITE: [{}], executed on thread [{}]",
                items,
                Thread.currentThread().getName());
        items.forEach(m -> log.info("Movie: {}", m));
    }


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @AfterWrite
    public void afterWrite(List<Movie> items){
        log.info("______#AFTER WRITE: [{}]", items);
        items.forEach(m -> log.info("Movie: {}", m));
    }

    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @OnWriteError
    public void onWriteError(Exception e, List<Movie> items){
        log.error("______#AFTER WRITE ERROR: [{}]", e.getMessage());
        items.forEach(m -> log.info("Movie: {}", m));
    }

} // The End...
