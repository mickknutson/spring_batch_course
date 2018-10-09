package io.baselogic.batch.listeners.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;

@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class CustomItemReadListener {

    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @BeforeRead
    public void beforeRead(){
        log.info("______+BEFORE READ");
    }


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @AfterRead
    public void afterRead(String item){
        log.info("______+AFTER READ: [{}]", item);
    }


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @OnReadError
    public void onReadError(Exception e){
        log.error("______+AFTER ERROR: [{}]", e.getMessage());
    }

} // The End...
