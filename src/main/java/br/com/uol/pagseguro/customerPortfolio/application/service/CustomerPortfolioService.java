package br.com.uol.pagseguro.customerPortfolio.application.service;

import org.springframework.web.multipart.MultipartFile;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public interface CustomerPortfolioService {
	CustomerPortfolio saveCustomerPortfolio(CustomerPortfolio buildCustomerPortFolio);
	void saveCustomersPortfolioByFile(MultipartFile file);
}
