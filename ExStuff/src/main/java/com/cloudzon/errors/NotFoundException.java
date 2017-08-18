package com.cloudzon.errors;

public class NotFoundException extends CustomParameterizedException {

	private static final long serialVersionUID = -8691443257932301841L;

	public NotFoundException(NotFound notFound, Boolean showMessage) {
		super(404, new ErrorDto(notFound.getStatusCode(), notFound.getErrorMessage(), notFound.getDeveloperMessage()),
				Boolean.FALSE, showMessage);
	}

	public enum NotFound {
		UserNotFound("40401", "User does not exists", "User Not Found For that ID"), ImageNotFound("40402",
				"Image does not exists", "Image Not Found"), TokenNotFound("40403", "Token does not exists",
						"Token Not Found"), VerificationCodeNotFound("40404", "Invalid verification code",
								"Verification Code Not Matched"), DevelopementNotFound("40405", "Under development",
										"Under development");

		private String statusCode;
		private String errorMessage;
		private String developerMessage;

		private NotFound(String statusCode, String errorMessage, String developerMessage) {

			this.statusCode = statusCode;
			this.errorMessage = errorMessage;
			this.developerMessage = developerMessage;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public String getDeveloperMessage() {
			return developerMessage;
		}

		public void setDeveloperMessage(String developerMessage) {
			this.developerMessage = developerMessage;
		}
	}
}
