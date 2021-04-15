package br.com.uol.pagseguro.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

@EnableKafka
@Configuration
public class KafkaConfig {
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	@Value(value = "${kafka.groupId}")
	private String groupId;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		return new DefaultKafkaConsumerFactory<>(config);		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =  new ConcurrentKafkaListenerContainerFactory<>();		
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ProducerFactory<String, CustomerPortfolio> customerPortfolioProducerFactory() {
	    return new DefaultKafkaProducerFactory<>(buildPropsProducer());
	}

	@Bean
	public KafkaTemplate<String, CustomerPortfolio> customerPortfolioKafkaTemplate() {
	    return new KafkaTemplate<>(customerPortfolioProducerFactory());
	}
	
	private Map<String, Object> buildPropsProducer() {
		Map<String, Object> propsProducer = new HashMap<>();
		propsProducer.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		propsProducer.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		propsProducer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		propsProducer.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return propsProducer;
	}
}
