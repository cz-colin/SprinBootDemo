package com.cloudzon.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotNull
	@Size(min = 1, max = 15)
	private String username;

	@NotNull
	private String verificationCode;

	private Boolean rememberMe;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "LoginDTO{" + "username='" + username + '\'' + ", rememberMe=" + rememberMe + '}';
	}

}
