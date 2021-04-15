package br.com.uol.pagseguro.customerPortfolio.infrastructure;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBeanBuilder;

import br.com.uol.pagseguro.customerPortfolio.application.service.ConverterCsvToCustomerPortfolio;
import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ConverterCsvToCustomerPortfolioOpenCSVComponent implements ConverterCsvToCustomerPortfolio {

	@Override
	public List<CustomerPortfolio> parseToCustomersPortfolio(MultipartFile file) {
		try {
			log.info("[start] ConverterCsvToCustomerPortfolioOpenCSVComponent - parse");
			@SuppressWarnings({ "rawtypes", "unchecked" })
			List<CustomerPortfolioLineCSV> customersPortfolioByCSV = new CsvToBeanBuilder(
					new InputStreamReader(file.getInputStream()))
					.withType(CustomerPortfolioLineCSV.class)
					.withSeparator(';')
					.withSkipLines(1)
					.build()
					.parse();
			log.info("[finish] ConverterCsvToCustomerPortfolioOpenCSVComponent - parse");
			return customersPortfolioByCSV.stream()
					.map(CustomerPortfolioLineCSV::buildCustomerPortFolio)
					.collect(Collectors.toList());
		} catch (IllegalStateException | IOException e) {
			log.error(e);
			throw new RuntimeException("erro to read CSV file!");
		}
	}
}
