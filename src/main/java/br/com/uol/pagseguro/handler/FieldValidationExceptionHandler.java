package br.com.uol.pagseguro.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@AllArgsConstructor
@Log4j2
public class FieldValidationExceptionHandler {
	private MessageSource messageSource;
	
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorApiDTO handle(MethodArgumentNotValidException ex) {
		log.error("Validation Errors: {}", ex.getMessage());
		return buildErroApiDTO(ex);
	}
    
	private ErrorApiDTO buildErroApiDTO(MethodArgumentNotValidException ex) {
		List<ValidationDTO> listFormErros = convertToFormValidationDTO(ex.getBindingResult().getFieldErrors(),messageSource);
		return ErrorApiDTO.builder()
				.message("ERROR TO VALID FORM")
				.validationsError(listFormErros)
				.build();
	}
	
	private List<ValidationDTO> convertToFormValidationDTO(List<FieldError> fieldErrors, MessageSource messageSource) {
		return fieldErrors.stream().map(e -> toFormValidationDTO(e)).collect(Collectors.toList());
	}
	
	private ValidationDTO toFormValidationDTO(FieldError fieldError) {
		return ValidationDTO.builder()
				.field(fieldError.getField())
				.message(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
				.build();
	}
}
