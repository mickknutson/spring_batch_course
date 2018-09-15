package io.baselogic.batch.split.steps;

import io.baselogic.batch.split.domain.TextLineItem;
import org.springframework.batch.item.ItemReader;

public class MockExceptionItemReader implements ItemReader {

    @Override
    public TextLineItem read() throws Exception {
        throw new RuntimeException("MockExceptionItemReader");
    }

} // The End...
