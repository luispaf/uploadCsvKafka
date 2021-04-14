package com.example.demo.customerPortfolio.application.api;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.customerPortfolio.domain.CustomerPortfolio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@EqualsAndHashCode
public class CustomerPortfolioDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String businessUnit;
	private String name;
	private String segment;
	private String link;
	private String status;

	public CustomerPortfolioDTO(CustomerPortfolio customerPortfolio) {
		this.id = customerPortfolio.getId();
		this.businessUnit = customerPortfolio.getBusinessUnit();
		this.name = customerPortfolio.getName();
		this.segment = customerPortfolio.getSegment();
		this.link = customerPortfolio.getLink();
		this.status = customerPortfolio.getStatus();
	}
}
