package com.example.demo.generic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.example.demo.DTO.CargaPadraoDTO;


public class ConsumerKafka {
	
	public static final String ATIVO = "1";
		
	/**
	 * Metodo inicializa após iniciar a aplciação
	 */	
	public List<CargaPadraoDTO> consumir() {
		var consumer = new KafkaConsumer<String, String>(properties());
		consumer.subscribe(Collections.singleton("LOJA_NOVO_PEDIDO"));
		List<CargaPadraoDTO> lista = new ArrayList<CargaPadraoDTO>();
		
		while (true) {			
			// De quanto em quanto tempo  irá consumir esse producer  
			var records = consumer.poll(Duration.ofMillis(100));
			
			if (records.isEmpty()) {
				System.out.println("Nenhum registro encontrado!");	
				continue;
			} else {
				for (var record : records) {
					// Monta a lista de objetos				
					String[] cols = record.value().toString().split(",");
					CargaPadraoDTO cg = new CargaPadraoDTO();
					cg.setNM_UNID_NEGO(cols[0]);
					cg.setNM_CART(cols[1]);
					cg.setNM_SEGM(cols[2]);
					cg.setNM_LINK(cols[3]);
					cg.setFL_ATIV(cols[4]);
					
					// Pega apenas os ativos
					if ((cg.getFL_ATIV().equals(ATIVO)) || (cg.getFL_ATIV().equals("FL_ATIV"))) {
						lista.add(cg);
						System.out.println(record.value());
						System.out.println("--------------------");
					}			
					
					/*
					 * try { Thread.sleep(100); } catch (InterruptedException e) {
					 * e.printStackTrace(); }
					 */
				}
				return lista;
			}			
		}
		
	}
	
	public static Properties properties() {
		var properties =  new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ConsumerKafka.class.getSimpleName());
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, ConsumerKafka.class.getSimpleName() + "-" + UUID.randomUUID().toString()); // Nome do seviço
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
		return properties;
	}	
}
