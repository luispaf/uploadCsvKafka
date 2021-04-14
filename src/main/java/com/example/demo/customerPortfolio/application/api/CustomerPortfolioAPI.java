package com.example.demo.customerPortfolio.application.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customerPortfolio")
public interface CustomerPortfolioAPI {
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CustomerPortfolioDTO postAddCustomerPortfolio(@RequestBody @Valid CustomerPortfolioForm customerPortfolioForm);

}
