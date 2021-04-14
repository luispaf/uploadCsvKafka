package br.com.uol.pagseguro.customerPortfolio.application.service;

import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import br.com.uol.pagseguro.customerPortfolio.infrastructure.CustomerPortfolioSpringDataJPARepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class CustomerPortfolioSpringDataService implements CustomerPortfolioService {
	private CustomerPortfolioSpringDataJPARepository customerPortfolioRepository;
	
	@Override
	public CustomerPortfolio createCustomerPortfolio(CustomerPortfolio customerPortFolioByForm) {
		log.info("[start] CustomerPortfolioSpringDataService - createCustomerPortfolio");
		CustomerPortfolio customerPortfolio = customerPortfolioRepository.save(customerPortFolioByForm);
		log.info("[finish] CustomerPortfolioSpringDataService - createCustomerPortfolio");
		return customerPortfolio;
	}
}
