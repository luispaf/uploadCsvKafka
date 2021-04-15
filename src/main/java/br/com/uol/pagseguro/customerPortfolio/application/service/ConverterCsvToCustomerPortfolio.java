package br.com.uol.pagseguro.customerPortfolio.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public interface ConverterCsvToCustomerPortfolio {
	List<CustomerPortfolio> parseToCustomersPortfolio(MultipartFile file);
}
