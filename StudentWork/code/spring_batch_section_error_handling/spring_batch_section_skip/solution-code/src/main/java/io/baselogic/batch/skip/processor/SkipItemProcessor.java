package io.baselogic.batch.skip.processor;

import org.springframework.batch.item.ItemProcessor;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class SkipItemProcessor implements ItemProcessor<String, String> {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private boolean skip = false;
	private int attemptCount = 0;

	@Override
	public String process(String item) throws Exception {
		System.out.println("processing item " + item);
		if(skip && item.equalsIgnoreCase("42")) {
			attemptCount++;

			System.out.println("*** Processing of item " + item + " failed");
			throw new CustomRetryableException("Process failed.  Attempt:" + attemptCount);
		}
		else {
			return String.valueOf(Integer.valueOf(item) * -1);
		}
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
