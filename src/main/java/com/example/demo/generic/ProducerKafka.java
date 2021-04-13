package com.example.demo.generic;

import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class ProducerKafka {
	
	public void gerarProducer(String msg) throws InterruptedException, ExecutionException {
		try (var dispatcher = new KafkaDispatcher()){
			var key   = UUID.randomUUID().toString();// gera uma chave aleatoria, pode ser um id do usuraio, ele q faz dividir as ms nas aprtiçoes
			var value = msg;		
			dispatcher.send("LOJA_NOVO_PEDIDO", key, value);				
		};
	}
}
