package com.repgen.inventorycloud.modal;

import java.util.List;

public class StockMovementResponse{
  	 
	
	public String response;
//	private String message;
	public Stock stock;
	
	public List<TransactionLog> transactionLogsIssue;
	
	public List<TransactionLog> transactionLogsRecived;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public List<TransactionLog> getTransactionLogsIssue() {
		return transactionLogsIssue;
	}

	public void setTransactionLogsIssue(List<TransactionLog> transactionLogsIssue) {
		this.transactionLogsIssue = transactionLogsIssue;
	}

	public List<TransactionLog> getTransactionLogsRecived() {
		return transactionLogsRecived;
	}

	public void setTransactionLogsRecived(List<TransactionLog> transactionLogsRecived) {
		this.transactionLogsRecived = transactionLogsRecived;
	}

	
	
	
	
	
	
	
}