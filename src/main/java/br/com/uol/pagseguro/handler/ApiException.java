package br.com.uol.pagseguro.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class ApiException extends RuntimeException {
	private HttpStatus httpStatusCode;
	private ErrorApiDTO apiErrorResponse;

	private ApiException(HttpStatus httpStatusCode, String message, Exception e) {
		super(message, e);
		this.httpStatusCode = httpStatusCode;
		this.apiErrorResponse = ErrorApiDTO.builder()
				.message(message)
				.description(ApiException.getMessage(e))
				.build();
	}

	public static ApiException throwApiException(HttpStatus httpStatusCode, String message) {
		return new ApiException(httpStatusCode, message, null);
	}

	public static ApiException throwApiException(HttpStatus httpStatusCode, String message, Exception e) {
		return new ApiException(httpStatusCode, message, e);
	}
	

	private static final long serialVersionUID = 1L;


	private static String getMessage(Exception e) {
		if(e == null){
			return null;
		}
		return e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
	}

	public ResponseEntity<ErrorApiDTO> toResponseEntity() {
        log.error(this.apiErrorResponse.toString());
        return ResponseEntity.status(this.httpStatusCode).body(this.apiErrorResponse);
	}
}