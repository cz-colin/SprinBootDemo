package com.cloudzon.dto;

import com.cloudzon.errors.ErrorDto;

public class ResponseErrorDTO {

	private ErrorDto response;
	private Boolean isSuccess;
	private Boolean showMessage;

	public ResponseErrorDTO() {
	}

	public ResponseErrorDTO(ErrorDto response, Boolean isSuccess, Boolean showMessage) {
		super();
		this.response = response;
		this.isSuccess = isSuccess;
		this.showMessage = showMessage;
	}

	public ErrorDto getResponse() {
		return response;
	}

	public void setResponse(ErrorDto response) {
		this.response = response;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Boolean getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(Boolean showMessage) {
		this.showMessage = showMessage;
	}

}
