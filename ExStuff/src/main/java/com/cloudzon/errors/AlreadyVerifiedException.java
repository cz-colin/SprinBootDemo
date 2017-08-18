package com.cloudzon.errors;

public class AlreadyVerifiedException extends CustomParameterizedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyVerifiedException(AlreadyVerifiedExceptionType alreadyVerifiedExceptionType) {
		super(409, new ErrorDto(alreadyVerifiedExceptionType.getErrorCode(),
				alreadyVerifiedExceptionType.getErrorMessage(), alreadyVerifiedExceptionType.getDeveloperMessage()),
				Boolean.FALSE, Boolean.TRUE);
	}

	public enum AlreadyVerifiedExceptionType {
		Password("40902", "You have already changed your password", "You have already changed your password"), User(
				"40903", "The user has already been verified",
				"The user has already been verified"), Token("40903", "Token is already used", "Token is used");

		private final String errorMessage;
		private final String errorCode;
		private final String developerMessage;

		AlreadyVerifiedExceptionType(String errorCode, String errorMessage, String developerMessage) {
			this.errorCode = errorCode;
			this.errorMessage = errorMessage;
			this.developerMessage = developerMessage;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public String getDeveloperMessage() {
			return developerMessage;
		}
	}
}
