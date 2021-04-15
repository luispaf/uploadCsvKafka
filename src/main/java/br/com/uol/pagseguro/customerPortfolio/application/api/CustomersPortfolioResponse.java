package br.com.uol.pagseguro.customerPortfolio.application.api;

import org.springframework.core.io.InputStreamResource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomersPortfolioResponse {
	private String fileName;
	private InputStreamResource file;
}
