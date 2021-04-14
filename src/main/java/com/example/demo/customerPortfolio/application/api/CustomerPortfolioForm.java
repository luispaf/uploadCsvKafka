package com.example.demo.customerPortfolio.application.api;

import javax.validation.constraints.Pattern;

import com.example.demo.customerPortfolio.domain.CustomerPortfolio;

import lombok.Value;

@Value
public class CustomerPortfolioForm {
	private String businessUnit;
	private String name;
	private String segment;
	private String link;
	@Pattern(regexp = "0|1")
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
