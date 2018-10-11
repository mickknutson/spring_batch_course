package io.baselogic.batch.retry.listeners;

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
    public void beforeWrite(List<String> items){
        log.info("______#BEFORE WRITE: [{}]", items);
        items.forEach(log::info);
    }


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @AfterWrite
    public void afterWrite(List<String> items){
        log.info("______#AFTER WRITE: [{}]", items);
        items.forEach(log::info);
    }

    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @OnWriteError
    public void onWriteError(Exception e, List<String> items){
        log.error("______#AFTER WRITE ERROR: [{}]", e.getMessage());
    }

} // The End...
