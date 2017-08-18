package com.cloudzon.dto;

public class LoginSignUpResponseDTO {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LoginSignUpResponseDTO(String userId) {
		super();
		this.userId = userId;
	}

}
