package io.baselogic.batch.retry.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class RetryItemProcessor implements ItemProcessor<String, String> {

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private boolean retry = false;
	private int attemptCount = 0;

	@Override
	public String process(String item) throws Exception {

		log.info("processing item " + item);

		if(retry && item.equalsIgnoreCase("42")) {
			attemptCount++;

			if(attemptCount >= 5) {
                log.info("Success!");
				retry = false;
				return String.valueOf(Integer.valueOf(item) * -1);
			}
			else {
                log.info("*** --> Processing of item " + item + " failed");
				throw new CustomRetryableException("Process failed.  Attempt:" + attemptCount);
			}
		}
		else {
			return String.valueOf(Integer.valueOf(item) * -1);
		}
	}

	public void setRetry(boolean retry) {
		this.retry = retry;
	}
}
