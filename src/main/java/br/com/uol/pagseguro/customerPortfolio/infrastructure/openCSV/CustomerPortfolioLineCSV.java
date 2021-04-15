package br.com.uol.pagseguro.customerPortfolio.infrastructure.openCSV;

import com.opencsv.bean.CsvBindByPosition;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public class CustomerPortfolioLineCSV {
    @CsvBindByPosition(position = 0)
	private String businessUnit;
    @CsvBindByPosition(position = 1)
	private String name;
    @CsvBindByPosition(position = 2)
	private String segment;
    @CsvBindByPosition(position = 3)
	private String link;
    @CsvBindByPosition(position = 4)
	private String status;

	public CustomerPortfolio buildCustomerPortFolio() {
		return CustomerPortfolio.builder()
				.businessUnit(this.businessUnit)
				.name(this.name)
				.segment(this.segment)
				.link(this.link)
				.status(this.status)
				.build();
	}
}
