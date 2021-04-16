package br.com.uol.pagseguro.customerPortfolio.infrastructure.kafka;

import java.text.Normalizer;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.uol.pagseguro.customerPortfolio.application.service.CustomerPortfolioService;
import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@AllArgsConstructor
public class CustomerPortfolioSpringKafkaStringConsumer {
	CustomerPortfolioService customerPortfolioService;

	@KafkaListener(topics = "${kafka.customerPortfolioTopic}",groupId = "group_id")
	public void cosumes(String message) {
		log.info("[start] CustomerPortfolioSpringKafkaStringConsumer - cosumes");
		buildCustomerPortfolioByMessage(message)
			.ifPresent(c -> customerPortfolioService.saveCustomerPortfolio(c));
		log.info("[finish] CustomerPortfolioSpringKafkaStringConsumer - cosumes");
	}
	
	public Optional<CustomerPortfolio> buildCustomerPortfolioByMessage(String message) {
		Gson gson = new Gson();
		CustomerPortfolio customerPortfolio = gson.fromJson(message, CustomerPortfolio.class);
		validarCampo(customerPortfolio);
		if (("0".equals(customerPortfolio.getStatus())) || ("1".equals(customerPortfolio.getStatus()))) {
			return Optional.ofNullable(customerPortfolio);
		} else {
			return Optional.empty();
		}
	}
	
	public CustomerPortfolio validarCampo(CustomerPortfolio customerPortfolio) {
		customerPortfolio.setBusinessUnit(removerCaracterEspecial(customerPortfolio.getBusinessUnit()));
		customerPortfolio.setName(removerCaracterEspecial(customerPortfolio.getName()));
		customerPortfolio.setSegment(removerCaracterEspecial(customerPortfolio.getSegment()));
		customerPortfolio.setLink(removerCaracterEspecial(customerPortfolio.getLink()));
		return customerPortfolio;
	}
	
	public String removerCaracterEspecial(String valorAcentuado){
		return Normalizer.normalize(valorAcentuado, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
}
