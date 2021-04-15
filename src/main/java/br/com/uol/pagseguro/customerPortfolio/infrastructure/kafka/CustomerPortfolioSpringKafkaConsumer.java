package br.com.uol.pagseguro.customerPortfolio.infrastructure.kafka;

//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//import br.com.uol.pagseguro.customerPortfolio.application.service.CustomerPortfolioService;
//import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;
//import lombok.AllArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Component
//@Log4j2
//@AllArgsConstructor
//public class CustomerPortfolioSpringKafkaConsumer {
//	CustomerPortfolioService customerPortfolioService;
//
//	@KafkaListener(
//			topics = "${kafka.customerPortfolioTopic}", 
//	        groupId = "${kafka.groupId}",
//	        containerFactory = "kafkaListenerContainerFactory",
//	        autoStartup = "true")
//	public void customerPortfolioListener(CustomerPortfolio customerPortfolio) {
//		log.info("[start] CustomerPortfolioSpringKafkaConsumer - customerPortfolioListener");
//		customerPortfolioService.saveCustomerPortfolio(customerPortfolio);
//		log.info("[finish] CustomerPortfolioSpringKafkaConsumer - customerPortfolioListener");
//	}
//}
