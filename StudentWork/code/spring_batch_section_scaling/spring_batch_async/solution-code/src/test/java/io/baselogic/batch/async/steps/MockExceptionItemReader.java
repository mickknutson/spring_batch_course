package io.baselogic.batch.async.steps;

import io.baselogic.batch.async.domain.TextLineItem;
import org.springframework.batch.item.ItemReader;

public class MockExceptionItemReader implements ItemReader {

    @Override
    public TextLineItem read() throws Exception {
        throw new RuntimeException("MockExceptionItemReader");
    }

} // The End...
