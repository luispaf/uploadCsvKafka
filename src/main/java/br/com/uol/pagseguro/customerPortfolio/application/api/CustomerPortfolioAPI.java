package br.com.uol.pagseguro.customerPortfolio.application.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/customers-portfolio")
public interface CustomerPortfolioAPI {
	@PostMapping
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void postCustomersPortfolioByFile(@RequestParam("file") MultipartFile file);
	
	@GetMapping
	public ResponseEntity<?> getCustomersPortfolio() throws Exception;
}
