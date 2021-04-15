package br.com.uol.pagseguro.customerPortfolio.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/customerPortfolio")
public interface CustomerPortfolioAPI {
	@PostMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void postCustomersPortfolioByFile(@RequestParam("file") MultipartFile file);
}
