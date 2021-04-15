package br.com.uol.pagseguro.customerPortfolio.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import br.com.uol.pagseguro.customerPortfolio.infrastructure.CustomerPortfolioSpringDataJPARepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class CustomerPortfolioSpringDataService implements CustomerPortfolioService {
	private CustomerPortfolioSpringDataJPARepository customerPortfolioRepository;
	private ConverterCsvToCustomerPortfolio converter;
	private CustomerPortfolioKafkaProducer customerPortfolioKafkaProducer;
	
	@Override
	public CustomerPortfolio saveCustomerPortfolio(CustomerPortfolio customerPortFolioByForm) {
		log.info("[start] CustomerPortfolioSpringDataService - createCustomerPortfolio");
		CustomerPortfolio customerPortfolio = customerPortfolioRepository.save(customerPortFolioByForm);
		log.info("[finish] CustomerPortfolioSpringDataService - createCustomerPortfolio");
		return customerPortfolio;
	}

	@Override
	public void saveCustomersPortfolioByFile(MultipartFile file) {
		log.info("[start] CustomerPortfolioSpringDataService - saveCustomersPortfolioByFile");
		List<CustomerPortfolio> customersPortfolio = converter.parseToCustomersPortfolio(file);
		customerPortfolioKafkaProducer.send(customersPortfolio);
		log.info("[finish] CustomerPortfolioSpringDataService - saveCustomersPortfolioByFile");
	}
}
