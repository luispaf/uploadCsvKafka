package com.example.demo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Carga;
import com.example.demo.service.CargaSevice;

@Service
public class KafkaConsumer {
	@Autowired
	CargaSevice cargaSevice;
	 
	@KafkaListener(topics = "LOJA_NOVO_PEDIDO",groupId = "group_id")
	public void cosume(String message) {
		montarCarga(message);
	}
	
	public void montarCarga(String message) {
		String culunas[] = message.split(";");		
		String status = culunas[4];
		// se for cabeçalho ignora
		if (("0".equals(status)) || ("1".equals(status))) {
			Carga cg = new Carga();
			cg.setNomeUnidadeNegocio(culunas[0]);
			cg.setNomeCarteira(culunas[1]);
			cg.setNomeSeguimento(culunas[2]);
			cg.setNomeLink(culunas[3]);
			cg.setAtivo(culunas[4].equals("1") ? true : false);
			cargaSevice.salvar(cg);			
		}
	}
}
