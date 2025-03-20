package com.oneil.insurance.exception;


public class ResponseStatusException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3982825484815058980L;

	public ResponseStatusException(String message) {
        super(message);
    }
}