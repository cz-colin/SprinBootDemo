package com.cloudzon.errors;

/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
 * </pre>
 *
 * Can be translated with:
 *
 * <pre>
 * "error.myCustomError" :  "The server says {{param0}} to {{param1}}"
 * </pre>
 */
public class CustomParameterizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int status;
	private final ErrorDto errorDto;
	private final Boolean isSuccess;
	private final Boolean showMessage;

	public CustomParameterizedException(int status, ErrorDto errorDto, Boolean isSuccess, Boolean showMessage) {
		super();
		this.status = status;
		this.errorDto = errorDto;
		this.isSuccess = isSuccess;
		this.showMessage = showMessage;
	}

	public int getStatus() {
		return status;
	}

	public ErrorDto getErrorDto() {
		return errorDto;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public Boolean getShowMessage() {
		return showMessage;
	}
}
