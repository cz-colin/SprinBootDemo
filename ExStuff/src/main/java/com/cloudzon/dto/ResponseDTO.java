package com.cloudzon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseDTO {

	private Boolean isSuccess;

	private Boolean showMessage;

	private Object response;

	public ResponseDTO() {
		super();
	}

	public ResponseDTO(Object response, Boolean showMessage) {
		super();
		this.isSuccess = Boolean.TRUE;
		this.response = response;
		this.showMessage = showMessage;
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

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
