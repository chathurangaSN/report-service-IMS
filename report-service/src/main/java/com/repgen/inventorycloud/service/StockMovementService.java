package com.repgen.inventorycloud.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import com.repgen.inventorycloud.modal.StockMovementDetails;

public interface StockMovementService {

	ResponseEntity<StockMovementDetails> fetchDetails(String itemCode);// ,Integer uomId,Integer brandId
	
	ResponseEntity<?> generateReport();
}
