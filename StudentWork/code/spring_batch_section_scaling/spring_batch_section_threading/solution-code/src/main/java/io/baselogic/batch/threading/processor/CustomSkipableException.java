package io.baselogic.batch.threading.processor;

public class CustomSkipableException extends Exception {

	public CustomSkipableException() {
		super();
	}

	public CustomSkipableException(String msg) {
		super(msg);
	}
}
