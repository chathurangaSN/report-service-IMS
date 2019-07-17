package com.repgen.inventorycloud.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.repgen.inventorycloud.hystrix.ItemDetailsResponseCommand;
import com.repgen.inventorycloud.modal.*;
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
import com.repgen.inventorycloud.pdfgen.GeneratePdfReport;
import org.springframework.http.MediaType;



@Service
public class StockMovementServiceImpl implements StockMovementService{

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<StockMovementDetails> fetchDetails( String itemCode) { // , Integer uomId, Integer brandId
		
//		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		
//		OAuth2AuthenticationDetails details =(OAuth2AuthenticationDetails)
//				SecurityContextHolder.getContext().getAuthentication().getDetails();
//		httpHeaders.add("Authorization","bearer".concat(details.getTokenValue()));
		
		StockMovementResponse response;
		try {
			StockMovementResponseCommand movementResponseCommand = new StockMovementResponseCommand(itemCode, httpHeaders, restTemplate);
			 response = movementResponseCommand.execute();	// itemId,uomId,brandId,
		}catch(Exception ex) {
			throw new MessageBodyConstraintViolationException("Remote Stock service failed.");
		}

//		ItemDetailsResponse detailsResponse;
//		try {
//			ItemDetailsResponseCommand itemDetailsResponseCommand = new ItemDetailsResponseCommand(itemCode, httpHeaders, restTemplate);
//			detailsResponse = itemDetailsResponseCommand.execute();	// itemId,uomId,brandId,
//		}catch(Exception ex) {
//			throw new MessageBodyConstraintViolationException("Remote Item service failed.");
//		}
		
		
		
		if(response.getStatus().equals("Failed")) {
			System.out.println("Failed by here exception");
			throw new MessageBodyConstraintViolationException("Stock log entry not available.");
		}else {
			DecimalFormat roundOffFomatter = new DecimalFormat("#.##");
			StockMovementDetails movementDetails = new StockMovementDetails();
			movementDetails.setResponse("Success");
			
			movementDetails.setOpenStockDate(response.getStock().getDate());
			Double openStockQuantity = response.getStock().getStockDetails().get(0).getQuantity();
			movementDetails.setOpenStockQuantity(openStockQuantity);
			
			List<TransactionEntries> issueEntries = new ArrayList<TransactionEntries>();
			List<TransactionLog> issueLogArray = response.getTransactionLogsIssue();
			Double issueTotal = 0.0;
			issueTotal = issueLogArray.stream().mapToDouble(value -> value.getTransactionDetails().get(0).getQuantity()).sum();
			issueLogArray.stream().map(transactionLog -> issueEntries.add(new TransactionEntries(transactionLog.getDate()
					,transactionLog.getTransactionDetails().get(0).getQuantity())));

//			for (int i = 0; i < issueLogArray.size(); i++) {
//				TransactionEntries entries = new TransactionEntries();
//				entries.setDateTime(issueLogArray.get(i).getDate());
//				entries.setQuantity(issueLogArray.get(i).getTransactionDetails().get(0).getQuantity());
//				issueEntries.add(entries);
//				issueTotal = issueTotal+issueLogArray.get(i).getTransactionDetails().get(0).getQuantity();
//			}
			movementDetails.setIssueLog(issueEntries);
			movementDetails.setTotalIssueQuantity(issueTotal);
			movementDetails.setIssueCount(issueLogArray.size());
			
			List<TransactionEntries> receivedEntries = new ArrayList<TransactionEntries>();
			List<TransactionLog> receivedLogArray = response.getTransactionLogsRecived();
			Double receivedTotal = 0.0;
			receivedTotal = receivedLogArray.stream().mapToDouble(value -> value.getTransactionDetails().get(0).getQuantity()).sum();
			receivedLogArray.stream().map(transactionLog -> receivedEntries.add(new TransactionEntries(transactionLog.getDate()
					,transactionLog.getTransactionDetails().get(0).getQuantity())));
//			for (int i = 0; i < receivedLogArray.size(); i++) {
//				TransactionEntries entries = new TransactionEntries();
//				entries.setDateTime(receivedLogArray.get(i).getDate());
//				entries.setQuantity(receivedLogArray.get(i).getTransactionDetails().get(0).getQuantity());
//				receivedEntries.add(entries);
//				receivedTotal = receivedTotal +receivedLogArray.get(i).getTransactionDetails().get(0).getQuantity();
//			}
			movementDetails.setRevivedLog(receivedEntries);
			movementDetails.setTotalRevivedQuantity(receivedTotal);
			movementDetails.setRevivedCount(receivedLogArray.size());
			
			Double stockRemaining = (openStockQuantity + receivedTotal)-issueTotal;
			movementDetails.setStockRemaining(stockRemaining);
			
			Double countIssues= (double) issueLogArray.size();
			System.out.println(countIssues);
			Double averageIssueQuantity = issueTotal/countIssues;
			averageIssueQuantity =  Math.round(averageIssueQuantity * 100.0)/100.0;
			movementDetails.setAverageIssueQuantity(averageIssueQuantity);
			
			Double countReceived= (double) receivedLogArray.size();
			System.out.println(countReceived);
			Double averageRevivedQuantity = receivedTotal/countReceived;
			averageRevivedQuantity =  Math.round(averageRevivedQuantity * 100.0)/100.0;
			movementDetails.setAverageRevivedQuantity(averageRevivedQuantity);
			
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(movementDetails);
			
		}
		
		
//		return responseEntity;
	}

	
	
	@Override
	public ResponseEntity<?> generateReport() {
		 ByteArrayInputStream bis = null;
		 ResponseEntity<StockMovementDetails> details= fetchDetails("1");
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
