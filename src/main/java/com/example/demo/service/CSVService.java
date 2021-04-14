package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.CargaPadraoDTO;
import com.example.demo.DTO.MsgDTO;
import com.example.demo.generic.ProducerKafka;
import com.opencsv.CSVReader;


@Service
public class CSVService {
	
	  ProducerKafka producerKafka = new ProducerKafka();
	  private static final String CSV_PATH = "/home/usertqi/Desktop/Projeto_kafka/carga.csv";
		  
	  public ByteArrayInputStream carregar() throws Exception {		  
	    List<CargaPadraoDTO> tutorials = lerCsv();	
	    ByteArrayInputStream in = montarRetornoCSV(tutorials);
	    return in;
	  }
	  

	  public static ByteArrayInputStream montarRetornoCSV(List<CargaPadraoDTO> lista) {
			
		    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
		    CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
		      for (CargaPadraoDTO ite : lista) {
		        List<String> data = new ArrayList<String>();
		        data.add(ite.getNM_UNID_NEGO());
		        data.add(ite.getNM_CART());
		        data.add(ite.getNM_SEGM());
		        data.add(ite.getNM_LINK());
		        data.add(ite.getFL_ATIV());
		        csvPrinter.printRecord(data);
		      }

		      csvPrinter.flush();
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new RuntimeException("Falha ao importar CSV: " + e.getMessage());
		    }
      }
	 
	  public List<CargaPadraoDTO> lerCsv() throws Exception{

			CSVReader csvReader = new CSVReader(new FileReader(new File(CSV_PATH)));
			List<Map<String, String>> linhas = new ArrayList<Map<String, String>>();
			
			// Pega a primeira linhna q é o cabeçalho que vai ser um array
			String[] headers = csvReader.readNext();
			
			// gera uma nova lista com os headers
			String[] listaReaders = headers[0].split(";");
			
			
			String[] colunas = null;
			
			while ((colunas = csvReader.readNext()) != null) {
				// Gera as msg no prosucer
				producerKafka.gerarProducer(colunas[0]);
				
				// aqui tem q consumir para retornar as msg do kafka
				
				// Segue o fluxo par retorno do CSV
				Map<String, String> campos = new HashMap<String, String>();
				String[] listaColunas = colunas[0].split(";");
				for (int i = 0; i < listaColunas.length; i++) {
				campos.put(listaReaders[i], listaColunas[i]);
				}
				linhas.add(campos);
			}
			
			List<CargaPadraoDTO> lista = montarListaRetorno(linhas);
			return lista;
		}
	  
	  public List<CargaPadraoDTO> montarListaRetorno(List<Map<String, String>> linhas) {
		  List<CargaPadraoDTO> lista = new ArrayList<CargaPadraoDTO>();
			// Cabeçalho, adciono novamente porq antes eu dou next ai pula a linha
		  	CargaPadraoDTO cab = new CargaPadraoDTO();
		  	cab.setNM_UNID_NEGO("NM_UNID_NEGO");
		  	cab.setNM_CART("NM_CART");
		  	cab.setNM_SEGM("NM_SEGM");
		  	cab.setNM_LINK("NM_LINK");
		  	cab.setFL_ATIV("FL_ATIV");
			lista.add(cab);	
			
			// demais colunas
			linhas.forEach(cols -> {
				CargaPadraoDTO cg = new CargaPadraoDTO();
				cg.setNM_UNID_NEGO(cols.get("NM_UNID_NEGO"));
				cg.setNM_CART(cols.get("NM_CART"));
				cg.setNM_SEGM(cols.get("NM_SEGM"));
				cg.setNM_LINK(cols.get("NM_LINK"));
				cg.setFL_ATIV(cols.get("FL_ATIV"));
				lista.add(cg);			
			});
			
			return lista;
	  }
	  
	  public MsgDTO lerAquivoCSV(MultipartFile file) throws Exception {
		  try {
			  byte[] bytes = file.getBytes(); String completeData = new String(bytes);
			  String[] rows = completeData.split("\n");
			  
			  MsgDTO validaCampos = validarCampos(rows);
			  
			  if (validaCampos.getStatus()) {				  
				  for (String lin : rows) { 
					 // Envia a mensagem 
					 producerKafka.gerarProducer(lin);
				  }	
			  }
			return validaCampos;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	  }
	  
	  public MsgDTO validarCampos(String[] rows) throws Exception {
		 
		  MsgDTO msg = new MsgDTO();
		  msg.setMsg("Arquivo validado e enviado com sucesso!");
	      msg.setStatus(true);
		 
	      Long cont = 1L;
		 
	      for (String lin : rows) { 
	    	  if (cont > 1) {
	    		  String[] cols =  lin.split(";");   		  
	    		  
	    		  try {
	    			  if (cols[0] == null || cols[0].isEmpty()) {
		    			  msg.setMsg("Linha: " + cont + " coluna: 1 invalida!" );
		    			  msg.setStatus(false);
		    		  }
					} catch (Exception e) {
						 msg.setMsg("Linha: " + cont + " Coluna 1 não encontrada!");
		    			 msg.setStatus(false);
					}
	    		  
	    		  try {
	    			  if (cols[1] == null || cols[1].isEmpty()) {
		    			  msg.setMsg("Linha: " + cont + " coluna: 2 invalida!" );
		    			  msg.setStatus(false);				  
		    		  }
					} catch (Exception e) {
						 msg.setMsg("Linha: " + cont + " Coluna 2 não encontrada!");
		    			 msg.setStatus(false);
					}
	    		  
	    		  try {
	    			  if (cols[2] == null || cols[2].isEmpty()) {
		    			  msg.setMsg("Linha: " + cont + " coluna: 3 invalida!" );
		    			  msg.setStatus(false); 
		    		  }
					} catch (Exception e) {
						 msg.setMsg("Linha: " + cont +  " Coluna 3 não encontrada!");
		    			 msg.setStatus(false);
					}
	    		  
	    		  try {
	    			  if (cols[3] == null || cols[3].isEmpty()) {
		    			  msg.setMsg("Linha: " + cont + " coluna: 4 invalida!" );
		    			  msg.setStatus(false);
		    		  }				
					} catch (Exception e) {
						 msg.setMsg("Linha: " + cont +  " Coluna 4 não encontrada!");
		    			 msg.setStatus(false);
					}
	    		  
	    		  try {
	    			  if ((cols[4] == null || cols[4].isEmpty())) {
		    			  msg.setMsg("Linha: " + cont + " coluna: 5 invalida!");
		    			  msg.setStatus(false); 
		    		  }					
					} catch (Exception e) {
						 msg.setMsg("Linha: " + cont + " Coluna 5 não encontrada!");
		    			 msg.setStatus(false);
					}
	    		  
	    		  try {
	    			  if (!"0".equals(cols[4].subSequence(0, 1)) && !"1".equals(cols[4].subSequence(0, 1))) {    				  
	    				  msg.setMsg("Linha: " + cont + " Coluna FL_ATIV aceita apenas valores 0 ou 1" );
	    				  msg.setStatus(false); 
	    			  }						
					} catch (Exception e) {
						 msg.setMsg("Linha: " + cont +  " Coluna 5 não encontrada!");
		    			 msg.setStatus(false);
					}
	    		  
	    		  if (!msg.getStatus()) {
	    			  return msg;
	    		  }
	    	  }
	    	  cont ++;	    		  
		  }	
	      return msg;
	  }
}
