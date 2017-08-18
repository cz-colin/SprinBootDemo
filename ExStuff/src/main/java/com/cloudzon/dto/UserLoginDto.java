package com.cloudzon.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginDto {

	@NotEmpty(message = "Mobile number can not be null")
	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
