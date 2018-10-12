package io.baselogic.batch.partition.listeners;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<String, String> {

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public void onSkipInRead(Throwable t){
        log.info("______+onSkipInRead: {}, executed on thread [{}]",
                t.getMessage(),
                Thread.currentThread().getName());
    }

    @Override
    public void onSkipInWrite(String item, Throwable t){
        log.info("______+onSkipInWrite: {}", item);
    }

    @Override
    public void onSkipInProcess(String item, Throwable t){
        log.info("______+onSkipInProcess: {}", item);
    }

} // The End...
