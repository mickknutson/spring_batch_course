package io.baselogic.batch.listeners.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;

import java.util.List;

@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class CustomItemWriterListener {

    @BeforeWrite
    public void beforeRead(List<String> items){
        log.info("______#BEFORE WRITE: [{}]", items);
        items.forEach(log::info);
    }

    @AfterWrite
    public void afterRead(List<String> items){
        log.info("______#AFTER WRITE: [{}]", items);
        items.forEach(log::info);
    }

    @OnWriteError
    public void onReadError(Exception e, List<String> items){
        log.error("______#AFTER WRITE ERROR: [{}]", e.getMessage());
    }

} // The End...
