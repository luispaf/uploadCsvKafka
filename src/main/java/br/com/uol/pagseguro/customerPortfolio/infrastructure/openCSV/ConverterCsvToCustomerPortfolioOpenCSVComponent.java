package br.com.uol.pagseguro.customerPortfolio.infrastructure.openCSV;

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
	
	@Override
	public void validaCampos(List<CustomerPortfolio> customersPortfolio) {		 
	      for (CustomerPortfolio customer : customersPortfolio) { 	    		  
    		  try {
    			  if (customer.getBusinessUnit() == null || customer.getBusinessUnit().isEmpty()) {		    			  
	    			  log.error("Há NM_UNID_NEGO inválido, nulos/vazio");
	    			  throw new RuntimeException("Há NM_UNID_NEGO inválido nulos/vazio");
	    		  }
				} catch (Exception e) {
					log.error(e.getMessage());
	    			throw new RuntimeException("Falha ao validar NM_UNID_NEGO: " + e.getMessage());
				}
    		  
    		  try {
    			  if (customer.getName() == null || customer.getName().isEmpty()) {
    				  log.error("Há NM_CART inválido, nulos/vazio");
	    			  throw new RuntimeException("Há NM_UNID_NEGO inválido, nulos/vazio");			  
	    		  }
				} catch (Exception e) {
					log.error(e.getMessage());
	    			throw new RuntimeException("Falha ao validar NM_CART: " + e.getMessage());
				}
    		  
    		  try {
    			  if (customer.getSegment() == null || customer.getSegment().isEmpty()) {
    				  log.error("Há NM_SEGM inválido, nulos/vazio");
	    			  throw new RuntimeException("Há NM_SEGM inválido, nulos/vazio");
	    		  }
				} catch (Exception e) {
					log.error(e.getMessage());
	    			throw new RuntimeException("Falha ao validar NM_SEGM: " + e.getMessage());
				}
    		  
    		  try {
    			  if (customer.getLink() == null || customer.getLink().isEmpty()) {
    				  log.error("Há NM_LINK inválido, nulos/vazio");
	    			  throw new RuntimeException("Há NM_LINK inválido, nulos/vazio");
	    		  }				
				} catch (Exception e) {
					log.error(e.getMessage());
	    			throw new RuntimeException("Falha ao validar NM_LINK: " + e.getMessage());
				}
    		  
    		  try {
    			  if ((customer.getStatus() == null || customer.getStatus().isEmpty())) {
    				  log.error("Há FL_ATIV inválido, nulos/vazio");
	    			  throw new RuntimeException("Há FL_ATIV inválido, nulos/vazio");
	    		  }					
				} catch (Exception e) {
					log.error(e.getMessage());
	    			throw new RuntimeException("Falha ao validar FL_ATIV: " + e.getMessage());
				}
    		  
    		  try {
    			  if (!"0".equals(customer.getStatus()) && !"1".equals(customer.getStatus())) {	    				  
    				  log.error("FL_ATIV aceita apenas valores 0 ou 1");
	    			  throw new RuntimeException("FL_ATIV aceita apenas valores 0 ou 1");
    			  }						
				} catch (Exception e) {
					log.error(e.getMessage());
	    			throw new RuntimeException("Falha ao validar FL_ATIV: " + e.getMessage());
				}  		  
		  }	
	}
}
