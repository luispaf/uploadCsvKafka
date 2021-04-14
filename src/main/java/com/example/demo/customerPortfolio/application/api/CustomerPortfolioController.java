package com.example.demo.customerPortfolio.application.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customerPortfolio.application.service.CustomerPortfolioService;
import com.example.demo.customerPortfolio.domain.CustomerPortfolio;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
@Log4j2
@AllArgsConstructor
public class CustomerPortfolioController implements CustomerPortfolioAPI {
	private CustomerPortfolioService customerPortfolioService;

	@Override
	public CustomerPortfolioDTO postAddCustomerPortfolio(@Valid CustomerPortfolioForm customerPortfolioForm) {
		log.info("[start] CustomerPortfolioController - postAddCustomerPortfolio");
		log.info("customerPortfolioForm {}",customerPortfolioForm);
		CustomerPortfolio customerPortfolio = customerPortfolioService.createCustomerPortfolio(customerPortfolioForm.buildCustomerPortFolio());
		log.info("[finish] CustomerPortfolioController - postAddCustomerPortfolio");
		return new CustomerPortfolioDTO(customerPortfolio);
	}
}
