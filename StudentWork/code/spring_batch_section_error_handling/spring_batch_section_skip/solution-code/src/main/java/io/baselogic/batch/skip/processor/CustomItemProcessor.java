package io.baselogic.batch.skip.processor;

import io.baselogic.batch.skip.domain.TextLineItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

//@Component
public class CustomItemProcessor implements ItemProcessor<TextLineItem, TextLineItem> {

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public TextLineItem process(TextLineItem item) throws Exception {

        log.info("Processing...{}", item);
        String id = item.getId();

        if(! id.contains("PRD1")){
            throw new FlatFileParseException("Only PRD100 series supported", id);
        }
        return item;
    }

} // The End...