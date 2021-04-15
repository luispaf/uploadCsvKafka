package br.com.uol.pagseguro.customerPortfolio.infrastructure.kafka;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import br.com.uol.pagseguro.config.KafkaProps;
import br.com.uol.pagseguro.customerPortfolio.application.service.CustomerPortfolioKafkaProducer;
import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor()
@Log4j2
public class CustomerPortfolioSpringKafkaProducer implements CustomerPortfolioKafkaProducer {
	private KafkaTemplate<String, CustomerPortfolio> kafkaTemplate;
	private KafkaProps kafkaProps;
	
	@Override
	public void send(List<CustomerPortfolio> customersPortfolio) {
		log.info("[start] CustomerPortfolioSpringKafkaProducer - send");
		customersPortfolio.parallelStream().forEach(c -> sendCustomerPortfolio(c));
		log.info("[finish] CustomerPortfolioSpringKafkaProducer - send");
	}
	
	public void sendCustomerPortfolio(CustomerPortfolio customerPortfolio) {
		kafkaTemplate.send(kafkaProps.getCustomerPortfolioTopic(), customerPortfolio);
	}
}
