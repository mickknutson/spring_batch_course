package io.baselogic.batch.partition.steps;

import io.baselogic.batch.partition.domain.TextLineItem;
import org.springframework.batch.item.ItemReader;

public class MockExceptionItemReader implements ItemReader {

    @Override
    public TextLineItem read() throws Exception {
        throw new RuntimeException("MockExceptionItemReader");
    }

} // The End...
