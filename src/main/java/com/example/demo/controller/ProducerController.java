package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.MsgDTO;
import com.example.demo.service.serviceImpl.CSVService;

@RestController
@RequestMapping(value = "/producer")
public class ProducerController {

	  @Autowired
	  CSVService fileService;
	 
	  @PostMapping("/send")
	  public ResponseEntity<?>  fileUpload(@RequestParam("arquivo") MultipartFile file) throws Exception{				
		  MsgDTO msgRet = fileService.lerAquivoCSV(file);
		  return new ResponseEntity<MsgDTO>(msgRet, HttpStatus.OK);
	  }
	  
}