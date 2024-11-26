package com.library.exception;

public class CustomError extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomError(String msg) {
		super(msg);
	}

}
