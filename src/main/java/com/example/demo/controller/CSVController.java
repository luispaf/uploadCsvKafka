package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.CargaPadraoDTO;
import com.example.demo.DTO.MsgDTO;
import com.example.demo.service.CSVService;
import com.opencsv.CSVParser;

@RestController
@RequestMapping(value = "/csv")
public class CSVController {

	 @Autowired
	  CSVService fileService;
	  
	  @GetMapping("/lercsv")
	  public ResponseEntity<?> gerarCsv() throws Exception{
		  // retorna JSON
		  //List<CargaPadraoDTO>  lista = fileService.lerCsv();
		  //return new ResponseEntity<List<CargaPadraoDTO>>(lista, HttpStatus.OK);
		  
		  // Retorna CSV
			
			  String filename = "tutorials.csv"; InputStreamResource file = new
			  InputStreamResource(fileService.carregar());
			  
			  return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
			  "attachment; filename=" + filename)
			  .contentType(MediaType.parseMediaType("application/csv")) .body(file);
			 
	  }
	  	  
	  @PostMapping("/send")
	  public ResponseEntity<?>  fileUpload(@RequestParam("arquivo") MultipartFile file) throws Exception{				
		  MsgDTO msgRet = fileService.lerAquivoCSV(file);
		  return new ResponseEntity<MsgDTO>(msgRet, HttpStatus.OK);
	  }
}
