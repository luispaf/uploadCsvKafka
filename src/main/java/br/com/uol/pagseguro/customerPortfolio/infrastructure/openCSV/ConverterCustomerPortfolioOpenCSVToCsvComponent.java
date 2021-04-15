package br.com.uol.pagseguro.customerPortfolio.infrastructure.openCSV;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

import br.com.uol.pagseguro.customerPortfolio.application.service.ConverterCustomerPortfolioToCsv;
import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ConverterCustomerPortfolioOpenCSVToCsvComponent implements ConverterCustomerPortfolioToCsv {

	@Override
	public ByteArrayInputStream parseToCustomersPortfolio(List<CustomerPortfolio> customersPortfolio) {
		log.info("[start] ConverterCustomerPortfolioOpenCSVToCsvComponent - parseToCustomersPortfolio");
		final CSVFormat format = CSVFormat.DEFAULT
				.withQuoteMode(QuoteMode.MINIMAL)
				.withRecordSeparator(';');

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (CustomerPortfolio ite : customersPortfolio) {
				List<String> data = new ArrayList<String>();
				data.add(ite.getBusinessUnit());
				data.add(ite.getName());
				data.add(ite.getSegment());
				data.add(ite.getLink());
				data.add(ite.getStatus());
				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			log.info("[finish] ConverterCustomerPortfolioOpenCSVToCsvComponent - parseToCustomersPortfolio");
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Falha ao ler tabela TMP: " + e.getMessage());
		}
	}
}
