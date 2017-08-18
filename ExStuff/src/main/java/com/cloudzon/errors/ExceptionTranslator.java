package com.cloudzon.errors;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cloudzon.dto.ResponseErrorDTO;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

	private final Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

	@ExceptionHandler(ConcurrencyFailureException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseErrorDTO processConcurrencyError(ConcurrencyFailureException ex) {
		return new ResponseErrorDTO(
				new ErrorDto("ErrorConstants.ERR_CONCURRENCY_FAILURE", "409", ErrorConstants.ERR_CONCURRENCY_FAILURE),
				Boolean.FALSE, Boolean.TRUE);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		ErrorDto dto = new ErrorDto("Field Error", "400", ErrorConstants.ERR_VALIDATION);
		for (FieldError fieldError : fieldErrors) {
			dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
		}
		return new ResponseErrorDTO(dto, Boolean.FALSE, Boolean.TRUE);
	}

	@ExceptionHandler(CustomParameterizedException.class)
	@ResponseBody
	public ResponseErrorDTO webApplicationException(CustomParameterizedException customParameterizedException,
			HttpServletResponse response) {
		response.setStatus(customParameterizedException.getStatus());
		return new ResponseErrorDTO(
				new ErrorDto(customParameterizedException.getErrorDto().getErrorCode(),
						customParameterizedException.getErrorDto().getErrorMessage(),
						customParameterizedException.getErrorDto().getDeveloperMessage()),
				customParameterizedException.getIsSuccess(), customParameterizedException.getShowMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ResponseErrorDTO processAccessDeniedException(AccessDeniedException e) {
		return new ResponseErrorDTO(new ErrorDto(e.getMessage(), "403", ErrorConstants.ERR_ACCESS_DENIED),
				Boolean.FALSE, Boolean.TRUE);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseErrorDTO processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		return new ResponseErrorDTO(
				new ErrorDto(exception.getMessage(), "405", ErrorConstants.ERR_METHOD_NOT_SUPPORTED), Boolean.FALSE,
				Boolean.TRUE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> processException(Exception ex) {
		if (log.isDebugEnabled()) {
			log.debug("An unexpected error occurred: {}", ex.getMessage(), ex);
		} else {
			log.error("An unexpected error occurred: {}", ex.getMessage());
		}
		BodyBuilder builder;
		ErrorDto errorDto;
		ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		if (responseStatus != null) {
			builder = ResponseEntity.status(responseStatus.value());
			errorDto = new ErrorDto("error." + responseStatus.value().value(), "407", responseStatus.reason());
		} else {
			builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
			errorDto = new ErrorDto(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "500", "Internal server error");
		}
		return builder.body(errorDto);
	}
}
