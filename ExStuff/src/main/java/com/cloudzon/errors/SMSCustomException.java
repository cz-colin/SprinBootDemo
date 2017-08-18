package com.cloudzon.errors;

public class SMSCustomException extends CustomParameterizedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SMSCustomException(String errorCode, String errorMessage, String developerMessage, Boolean showMessage) {
		super(402, new ErrorDto(errorCode, errorMessage, developerMessage), Boolean.FALSE, showMessage);
	}

}
