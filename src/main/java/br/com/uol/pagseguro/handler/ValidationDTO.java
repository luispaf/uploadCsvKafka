package br.com.uol.pagseguro.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationDTO {
	private String field;
	private String message;
}
