package io.baselogic.batch.skip.processor;

public class CustomRetryableException extends Exception {

	public CustomRetryableException() {
		super();
	}

	public CustomRetryableException(String msg) {
		super(msg);
	}
}
