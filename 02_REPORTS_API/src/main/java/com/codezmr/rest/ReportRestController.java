package com.codezmr.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codezmr.request.SearchRequest;
import com.codezmr.response.SearchResponse;
import com.codezmr.services.ReportsService;
import com.codezmr.services.ReportsServiceImpl;

@RestController
public class ReportRestController {

	@Autowired
	private ReportsService service;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanName(){
		List<String> planNames = service.getUniqePlanNames();
		return new ResponseEntity<List<String>> (planNames, HttpStatus.OK);
	}
	
	
	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatus(){
		List<String> planStatuses = service.getUniqePlanStatuses();
		
		return new ResponseEntity<List<String>>(planStatuses, HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest searchRequest){
		
		List<SearchResponse> response = service.search(searchRequest);
		return new ResponseEntity<List<SearchResponse>>(response,HttpStatus.OK);
	}
	
	
	@GetMapping("/excel")
	public void excelReport(HttpServletResponse response) throws Exception {
		
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormat = new SimpleDateFormat("yyy-MM-DD_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey=  "Content-Disposition";
		String headerValue = "attachment;filename=report-"+currentDateTime+".xls";
		
		response.setHeader(headerKey, headerValue);
		
		service.genrateExcel(response);
	}
	
	
	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("yyy-MM-DD_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		
		String headerKey=  "Content-Disposition";
		String headerValue = "attachment;filename=report-"+currentDateTime+".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		service.genratePdf(response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
