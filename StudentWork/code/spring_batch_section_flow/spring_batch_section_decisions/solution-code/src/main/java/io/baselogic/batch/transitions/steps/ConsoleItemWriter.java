package io.baselogic.batch.transitions.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class ConsoleItemWriter<T> implements ItemWriter<T> {

    @Override
    public void write(List<? extends T> items) throws Exception {
        items.forEach((i) -> {
            log.info("\t Item: {}", i);
        });
    }
} // The End...