package io.baselogic.batch.chunks.steps;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class ConsoleItemWriter<T> implements ItemWriter<T> {

    @Override
    public void write(List<? extends T> items) throws Exception {
        items.forEach((i) -> {
            log.info("\t Item: {}", i);
        });
    }
} // The End...