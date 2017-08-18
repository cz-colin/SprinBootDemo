package com.cloudzon.dto;

import org.springframework.web.multipart.MultipartFile;

public class BroadcastMessageDTO {

	private String title;
	private String description;
	private MultipartFile image;
	private String latitude;
	private String longitude;
	private Double expiresIn;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Double getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Double expiresIn) {
		this.expiresIn = expiresIn;
	}

}
