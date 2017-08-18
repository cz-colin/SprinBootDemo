package com.cloudzon.errors;

import java.util.ArrayList;
import java.util.List;

public class ErrorDto {

	private String errorMessage;
	private String errorCode;
	private String developerMessage;

	private List<FieldErrorVM> fieldErrors;

	public ErrorDto() {

	}

	public ErrorDto(String errorMessage, String errorCode, String developerMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.developerMessage = developerMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public List<FieldErrorVM> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorVM> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public void add(String objectName, String field, String message) {
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(new FieldErrorVM(objectName, field, message));
	}

}
