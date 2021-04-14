package com.example.demo.service;

import java.text.Normalizer;

public class teste {

	public static void main(String[] args) {
		String teste = removerAcentos("açougue!");
		System.out.println(teste);
	}
	
	public static String removerAcentos(String valorAcentuado){
		   return Normalizer.normalize(valorAcentuado, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
