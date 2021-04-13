package com.example.demo.generic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class ConsumerKafka {
	
	public List<String> lista_loja_novo_pedido = new ArrayList<String>();
	
	public static void main(String[] args) {
		var consumer = new KafkaConsumer<String, String>(properties());
		consumer.subscribe(Collections.singleton("LOJA_NOVO_PEDIDO"));
		
		while (true) {
			var records = consumer.poll(Duration.ofMillis(100));
			if (records.isEmpty()) {
				System.out.println("Não encontrou registros!");
				continue;			
			}
			for (var record : records) {
				System.out.println("--------------------");
				System.out.println("Processando pedido!");
				System.out.println(record.value());
				System.out.println(record.partition());
				System.out.println(record.offset());
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Ordem processada com sucesso!");
			}			
		}		
	}
	
	public void consumir() {
		var consumer = new KafkaConsumer<String, String>(properties());
		consumer.subscribe(Collections.singleton("LOJA_NOVO_PEDIDO"));
		
		while (true) {
			var records = consumer.poll(Duration.ofMillis(100));
			if (records.isEmpty()) {
				System.out.println("Não encontrou registros!");
				continue;			
			}
			for (var record : records) {
				System.out.println("--------------------");
				System.out.println("Processando pedido!");
				System.out.println(record.value());
				System.out.println(record.partition());
				System.out.println(record.offset());
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.lista_loja_novo_pedido.add(record.value());
				System.out.println("Ordem processada com sucesso!");
			}			
		}
	}
	
	public static Properties properties() {
		var properties =  new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ConsumerKafka.class.getSimpleName());
		return properties;
	}
	
	public List<String> listar() {
		List<String> lista = this.lista_loja_novo_pedido;
		this.lista_loja_novo_pedido = new ArrayList<String>();
		return lista;
	}
}
