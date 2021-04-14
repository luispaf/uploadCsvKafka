package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Carga;
import com.example.demo.service.CargaSevice;
import com.example.demo.service.serviceImpl.CSVService;

@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {
	
	@Autowired
	CargaSevice cargaService;
	 @Autowired
	 CSVService fileService;
	
	  @GetMapping("/get")
	  public ResponseEntity<?> gerarCsv() throws Exception{
		  List<Carga>  listaCarga = cargaService.listar();
		 
		  if (!listaCarga.isEmpty()) {
			  String filename = "tutorials.csv"; InputStreamResource file = new InputStreamResource(fileService.consumirRegistroTabelaTmp(listaCarga));
			  
			  return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
					  "attachment; filename=" + filename)
					  .contentType(MediaType.parseMediaType("application/csv")) .body(file);				  
		  } else {
			  return new ResponseEntity<String>("Nenhum Registro encontrado!", HttpStatus.OK);
		  }
		  
	  }
}
