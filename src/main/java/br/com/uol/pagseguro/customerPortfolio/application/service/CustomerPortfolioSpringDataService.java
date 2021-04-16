package br.com.uol.pagseguro.customerPortfolio.application.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.uol.pagseguro.customerPortfolio.application.api.CustomersPortfolioResponse;
import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import br.com.uol.pagseguro.customerPortfolio.infrastructure.springData.CustomerPortfolioSpringDataJPARepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@AllArgsConstructor
public class CustomerPortfolioSpringDataService implements CustomerPortfolioService {
	private CustomerPortfolioSpringDataJPARepository customerPortfolioRepository;
	private ConverterCsvToCustomerPortfolio converterCsvToCustomerPortfolio;
	private ConverterCustomerPortfolioToCsv converterCustomerPortfolioToCsv;
	private CustomerPortfolioKafkaProducer customerPortfolioKafkaProducer;
	private static String FILE_NAME = "customer-portfolio.csv";

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
		List<CustomerPortfolio> customersPortfolio = converterCsvToCustomerPortfolio.parseToCustomersPortfolio(file);
		log.info("[start] Valida campos");
		converterCsvToCustomerPortfolio.validaCampos(customersPortfolio);
		log.info("[finish] Valida campos");
		customerPortfolioKafkaProducer.send(customersPortfolio);
		log.info("[finish] CustomerPortfolioSpringDataService - saveCustomersPortfolioByFile");
	}

	@Override
	public CustomersPortfolioResponse findCustomersPortfolioInCsvFile() {
		log.info("[start] CustomerPortfolioSpringDataService - findCustomersPortfolioInCsvFile");
		List<CustomerPortfolio> customersPortfolio = findCustomersPortfolio();
		ByteArrayInputStream byteArray = converterCustomerPortfolioToCsv.parseToCustomersPortfolio(customersPortfolio);
		log.info("[finish] CustomerPortfolioSpringDataService - findCustomersPortfolioInCsvFile");
		
		return CustomersPortfolioResponse.builder()
				.fileName(FILE_NAME)
				.file(new InputStreamResource(byteArray))
				.build();
	}
	
	@Override
	public List<CustomerPortfolio> findCustomersPortfolio() {
		return customerPortfolioRepository.findByStatusActive();
	}
}
