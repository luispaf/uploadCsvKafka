package br.com.uol.pagseguro.customerPortfolio.application.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.uol.pagseguro.customerPortfolio.application.service.CustomerPortfolioService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
@Log4j2
@AllArgsConstructor
public class CustomerPortfolioController implements CustomerPortfolioAPI {
	private CustomerPortfolioService customerPortfolioService;

	@Override
	public void postCustomersPortfolioByFile(MultipartFile file) {
		log.info("[start] CustomerPortfolioController - postCustomersPortfolioByFile");
		customerPortfolioService.saveCustomersPortfolioByFile(file);
		log.info("[finish] CustomerPortfolioController - postCustomersPortfolioByFile");
	}

	@Override
	public ResponseEntity<?> getCustomersPortfolio() throws Exception {
		log.info("[start] CustomerPortfolioController - postCustomersPortfolioByFile");
		CustomersPortfolioResponse response = customerPortfolioService.findCustomersPortfolioInCsvFile();
		log.info("[start] CustomerPortfolioController - postCustomersPortfolioByFile");
		
	    return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			  "attachment; filename=" + response.getFileName())
			  .contentType(MediaType.parseMediaType("application/csv")) .body(response.getFile());				  
		  
		  
	}
}
