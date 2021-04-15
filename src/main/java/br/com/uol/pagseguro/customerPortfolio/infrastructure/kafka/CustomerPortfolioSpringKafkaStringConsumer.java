package br.com.uol.pagseguro.customerPortfolio.infrastructure.kafka;

import java.text.Normalizer;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
		String culunas[] = message.split(";");		
		String status = culunas[4];
		// se for cabeçalho ignora
		if (("0".equals(status)) || ("1".equals(status))) {
			return Optional.ofNullable(CustomerPortfolio.builder()
			.businessUnit(removerAcentos(culunas[0]))
			.name(removerAcentos(culunas[1]))
			.segment(removerAcentos(culunas[2]))
			.link(removerAcentos(culunas[3]))
			.status(status)
			.build());
		} else {
			return Optional.empty();
		}
	}
	
	public String removerAcentos(String valorAcentuado){
		   return Normalizer.normalize(valorAcentuado, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
}
