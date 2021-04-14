package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Carga;

public interface CargaSevice {

	public List<Carga> listar();
	public Carga salvar(Carga carga);
	public void deletar(Carga carga);
	public void salvarLista(List<Carga> lista);
}
