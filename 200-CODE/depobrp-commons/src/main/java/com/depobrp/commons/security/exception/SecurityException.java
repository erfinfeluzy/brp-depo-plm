package com.depobrp.commons.security.exception;

public class SecurityException extends RuntimeException {


	public SecurityException(String message, Throwable t) {
		super(message, t);
		this.message = message;
	}

	public SecurityException(Throwable t) {
		super(t);
	}

	public SecurityException(String message) {
		super(message, null);
		this.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8487333437931372703L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
