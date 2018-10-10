package io.baselogic.batch.skip.steps;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter<T> implements ItemWriter<T> {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    //-----------------------------------------------------------------------//
    // Lab: Create write method:
    @Override
    public void write(List<? extends T> items) throws Exception {
        items.forEach(i -> {
            log.info("\t Item: {}", i);
        });
    }

} // The End...