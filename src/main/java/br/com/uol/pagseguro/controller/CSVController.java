package br.com.uol.pagseguro.controller;

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

import br.com.uol.pagseguro.DTO.MsgDTO;
import br.com.uol.pagseguro.service.CSVService;

@RestController
@RequestMapping(value = "/csv")
public class CSVController {

	 @Autowired
	  CSVService fileService;
	  
	 /**
	  *Lê um arquivo na pasta csv e envia para o kafka
	  *pode retornar um Json ou uma lista de CSV 
	  */
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
	  
	  /**
	   * Recebe um CSV e já envia para o Kafka, depois de validar os campos
	   * Como enviar do postman: Seleciona POST-Marcar BODY - marca FORM-DATA -  KEY = arquivo, e escolhe o arquivo CSV
	   * */
	  @PostMapping("/send")
	  public ResponseEntity<?>  fileUpload(@RequestParam("arquivo") MultipartFile file) throws Exception{				
		  MsgDTO msgRet = fileService.lerAquivoCSV(file);
		  return new ResponseEntity<MsgDTO>(msgRet, HttpStatus.OK);
	  }
}
