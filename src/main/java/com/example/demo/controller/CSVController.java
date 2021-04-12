package com.example.demo.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CSVService;

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
	  public String fileUpload(@ModelAttribute("arquivo") MultipartFile file){
			/*
			 * try{ InputStream inputStream = file.getInputStream();
			 * IOUtils.readLines(inputStream, StandardCharsets.UTF_8) .stream()
			 * .forEach(this::handleLine); } catch (IOException e) { // handle exception }
			 */
			return "OK";
		}
}
