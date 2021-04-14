package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Carga;
import com.example.demo.repository.CargaRepository;
import com.example.demo.service.CargaSevice;

@Service
public class CargaServiveImpl implements CargaSevice{

	@Autowired
	CargaRepository cargaRepository;
	
	@Override
	public List<Carga> listar() {
		return cargaRepository.buscarAtivos();
	}

	@Override
	public Carga salvar(Carga carga) {
		return cargaRepository.save(carga);
	}

	@Override
	public void deletar(Carga carga) {
		cargaRepository.delete(carga);		
	}

	@Override
	public void salvarLista(List<Carga> lista) {
		cargaRepository.saveAll(lista);
	}

}
