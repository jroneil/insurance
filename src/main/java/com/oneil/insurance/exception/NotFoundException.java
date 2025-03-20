package com.oneil.insurance.exception;


public class NotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2964032203941533258L;

	public NotFoundException(String message) {
        super(message);
    }
}