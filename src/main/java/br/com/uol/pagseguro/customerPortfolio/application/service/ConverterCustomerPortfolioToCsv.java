package br.com.uol.pagseguro.customerPortfolio.application.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public interface ConverterCustomerPortfolioToCsv {
	ByteArrayInputStream parseToCustomersPortfolio(List<CustomerPortfolio> customersPortfolio);
}
