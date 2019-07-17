package com.repgen.inventorycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.repgen.inventorycloud.service.StockMovementServiceImpl;

@RestController
@CrossOrigin
@RequestMapping(value = "/report")
public class StockMovementController {

	@Autowired
	StockMovementServiceImpl stockMovementServiceImpl;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@RequestMapping(value = "/stockmoment/{itemCode}", method = RequestMethod.GET)
	// fetch Stock Movement Report // /{brandId}/{itemId}/{uomId}
	
	public ResponseEntity<?> fetchStockMovement( @PathVariable String itemCode ) {
//			,@PathVariable Integer uomId, @PathVariable Integer brandId) { 
		
			return stockMovementServiceImpl.fetchDetails( itemCode); // , uomId, brandId

	}
	
	@RequestMapping(value = "/stockmoment/pdf", method = RequestMethod.GET) 
	// generate pdf
	
	public ResponseEntity<?> generatePDFDoc( ) { 
		
			return stockMovementServiceImpl.generateReport();

	}
}
