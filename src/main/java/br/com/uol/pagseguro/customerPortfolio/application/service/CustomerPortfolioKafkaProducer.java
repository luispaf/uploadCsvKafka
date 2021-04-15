package br.com.uol.pagseguro.customerPortfolio.application.service;

import java.util.List;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public interface CustomerPortfolioKafkaProducer {
	void send(List<CustomerPortfolio> customersPortfolio);
}
