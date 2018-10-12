package io.baselogic.batch.threading.processor;

import org.springframework.batch.item.ItemProcessor;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class SkipItemProcessor implements ItemProcessor<String, String> {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private boolean skip = false;
	private int attemptCount = 0;

	@Override
	public String process(String item) throws Exception {

        log.info("{} item processed, executed on thread [{}]",
                item,
                Thread.currentThread().getName());

		if(skip &&
                (item.equalsIgnoreCase("42") ||
                        item.equalsIgnoreCase("55"))
        ){
			attemptCount++;

			System.out.println("\n*** --> Processing of item " + item + " failed");
			throw new CustomSkipableException("Process failed.  Attempt:" + attemptCount);
		}
		else {
			return String.valueOf(Integer.valueOf(item) * -1);
		}
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
