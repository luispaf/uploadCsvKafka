package com.example.demo.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CARGA")
public class Carga implements Serializable{

	private static final long serialVersionUID = -2772604484025381847L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;	
	private String nomeUnidadeNegocio;
	private String nomeCarteira;
	private String nomeSeguimento;
	private String nomeLink;
	private Boolean ativo;
		
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNomeUnidadeNegocio() {
		return nomeUnidadeNegocio;
	}
	public void setNomeUnidadeNegocio(String nomeUnidadeNegocio) {
		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
	}
	public String getNomeCarteira() {
		return nomeCarteira;
	}
	public void setNomeCarteira(String nomeCarteira) {
		this.nomeCarteira = nomeCarteira;
	}
	
	public String getNomeSeguimento() {
		return nomeSeguimento;
	}
	public void setNomeSeguimento(String nomeSeguimento) {
		this.nomeSeguimento = nomeSeguimento;
	}
	public String getNomeLink() {
		return nomeLink;
	}
	public void setNomeLink(String nomeLink) {
		this.nomeLink = nomeLink;
	}	
	
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carga other = (Carga) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	
}
