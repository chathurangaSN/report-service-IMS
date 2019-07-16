package com.repgen.inventorycloud.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.repgen.inventorycloud.exception.MessageBodyConstraintViolationException;
import com.repgen.inventorycloud.hystrix.StockMovementResponseCommand;
import com.repgen.inventorycloud.modal.StockMovementDetails;
import com.repgen.inventorycloud.modal.StockMovementResponse;
import com.repgen.inventorycloud.modal.TransactionEntries;
import com.repgen.inventorycloud.modal.TransactionLog;
import com.repgen.inventorycloud.pdfgen.GeneratePdfReport;
import org.springframework.http.MediaType;



@Service
public class StockMovementServiceImpl implements StockMovementService{

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<StockMovementDetails> fetchdetails( Integer itemId) { // , Integer uomId, Integer brandId
		
//		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		
//		OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails)
//				SecurityContextHolder.getContext().getAuthentication().getDetails();
//		httpHeaders.add("Authorization","bearer".concat(details.getTokenValue()));
		
		StockMovementResponse response;
		try {
			StockMovementResponseCommand movementResponseCommand = new StockMovementResponseCommand(itemId, httpHeaders, restTemplate);
			 response = movementResponseCommand.execute();	// itemId,uomId,brandId,
		}catch(Exception ex) {
			throw new MessageBodyConstraintViolationException("remote service fail");
		}
		
		
		
		if(response.getResponse().equals("failed")) {
			System.out.println("Failed by here exception");
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}else {
			DecimalFormat roundOffFomatter = new DecimalFormat("#.##");
			StockMovementDetails movementDetails = new StockMovementDetails();
			movementDetails.setResponse("success");
			
			movementDetails.setOpenStockDate(response.getStock().getDate());
			Double openStockQuantity = response.getStock().getStockDetails().get(0).getQuantity();
			movementDetails.setOpenStockQuantity(openStockQuantity);
			
			List<TransactionEntries> issueEntries = new ArrayList<TransactionEntries>();
			List<TransactionLog> issueLogArray = response.getTransactionLogsIssue();
			Double issueTotal = 0.0;
			
			for (int i = 0; i < issueLogArray.size(); i++) {
				TransactionEntries entries = new TransactionEntries();
				entries.setDateTime(issueLogArray.get(i).getDate());
				entries.setQuantity(issueLogArray.get(i).getTransactionDetails().get(0).getQuantity());
				issueEntries.add(entries);
				issueTotal = issueTotal+issueLogArray.get(i).getTransactionDetails().get(0).getQuantity();
			}
			movementDetails.setIssueLog(issueEntries);
			movementDetails.setTotalIssueQuantity(issueTotal);
			movementDetails.setIssueCount(issueLogArray.size());
			
			List<TransactionEntries> recivedEntries = new ArrayList<TransactionEntries>();
			List<TransactionLog> recivedLogArray = response.getTransactionLogsRecived();
			Double recivedTotal = 0.0;
			
			for (int i = 0; i < recivedLogArray.size(); i++) {
				TransactionEntries entries = new TransactionEntries();
				entries.setDateTime(recivedLogArray.get(i).getDate());
				entries.setQuantity(recivedLogArray.get(i).getTransactionDetails().get(0).getQuantity());
				recivedEntries.add(entries);
				recivedTotal = recivedTotal +recivedLogArray.get(i).getTransactionDetails().get(0).getQuantity();
			}
			movementDetails.setRevivedLog(recivedEntries);
			movementDetails.setTotalRevivedQuantity(recivedTotal);
			movementDetails.setRevivedCount(recivedLogArray.size());
			
			Double stockRemaining = (openStockQuantity + recivedTotal)-issueTotal;
			movementDetails.setStockRemaining(stockRemaining);
			
			Double countIssues= (double) issueLogArray.size();
			System.out.println(countIssues);
			Double averageIssueQuantity = issueTotal/countIssues;
			averageIssueQuantity =  Math.round(averageIssueQuantity * 100.0)/100.0;
			movementDetails.setAverageIssueQuantity(averageIssueQuantity);
			
			Double countRecived= (double) recivedLogArray.size();
			System.out.println(countRecived);
			Double averageRevivedQuantity = recivedTotal/countRecived;
			averageRevivedQuantity =  Math.round(averageRevivedQuantity * 100.0)/100.0;
			movementDetails.setAverageRevivedQuantity(averageRevivedQuantity);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(movementDetails);
			
		}
		
		
//		return responseEntity;
	}

	
	
	@Override
	public ResponseEntity<?> generateReport() {
		 ByteArrayInputStream bis = null;
		 ResponseEntity<StockMovementDetails> details= fetchdetails(1);
		 StockMovementDetails stockMovementDetails = details.getBody();
			try {
				bis = GeneratePdfReport.citiesReport(stockMovementDetails);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        HttpHeaders headers = new HttpHeaders();
//	        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
	}

	
	
}
