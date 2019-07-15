package com.repgen.inventorycloud.modal;

import java.time.ZonedDateTime;
import java.util.List;

public class StockMovementDetails {

	String response;
	ZonedDateTime openStockDate;
	Double openStockQuantity;
	List<TransactionEntries> issueLog;
	List<TransactionEntries> revivedLog;
	Double totalIssueQuantity;
	Double totalRevivedQuantity;
	Integer issueCount;
	Integer revivedCount;
	Double stockRemaining;
	Double averageIssueQuantity;
	Double averageRevivedQuantity;
	
	public String itemName;
	public String uomName;
	public String brandName;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public ZonedDateTime getOpenStockDate() {
		return openStockDate;
	}
	public void setOpenStockDate(ZonedDateTime openStockDate) {
		this.openStockDate = openStockDate;
	}
	public Double getOpenStockQuantity() {
		return openStockQuantity;
	}
	public void setOpenStockQuantity(Double openStockQuantity) {
		this.openStockQuantity = openStockQuantity;
	}
	
	
	public List<TransactionEntries> getIssueLog() {
		return issueLog;
	}
	public void setIssueLog(List<TransactionEntries> issueLog) {
		this.issueLog = issueLog;
	}
	public List<TransactionEntries> getRevivedLog() {
		return revivedLog;
	}
	public void setRevivedLog(List<TransactionEntries> revivedLog) {
		this.revivedLog = revivedLog;
	}


	
	public Double getTotalIssueQuantity() {
		return totalIssueQuantity;
	}
	public void setTotalIssueQuantity(Double totalIssueQuantity) {
		this.totalIssueQuantity = totalIssueQuantity;
	}
	public Double getTotalRevivedQuantity() {
		return totalRevivedQuantity;
	}
	public void setTotalRevivedQuantity(Double totalRevivedQuantity) {
		this.totalRevivedQuantity = totalRevivedQuantity;
	}
	public Integer getIssueCount() {
		return issueCount;
	}
	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
	}
	public Integer getRevivedCount() {
		return revivedCount;
	}
	public void setRevivedCount(Integer revivedCount) {
		this.revivedCount = revivedCount;
	}
	public Double getStockRemaining() {
		return stockRemaining;
	}
	public void setStockRemaining(Double stockRemaining) {
		this.stockRemaining = stockRemaining;
	}
	public Double getAverageIssueQuantity() {
		return averageIssueQuantity;
	}
	public void setAverageIssueQuantity(Double averageIssueQuantity) {
		this.averageIssueQuantity = averageIssueQuantity;
	}
	public Double getAverageRevivedQuantity() {
		return averageRevivedQuantity;
	}
	public void setAverageRevivedQuantity(Double averageRevivedQuantity) {
		this.averageRevivedQuantity = averageRevivedQuantity;
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
