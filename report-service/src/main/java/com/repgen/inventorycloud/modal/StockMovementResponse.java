package com.repgen.inventorycloud.modal;

import java.util.List;

public class StockMovementResponse{

	private String status;
	private String message;
	private String code;
	private Stock stock;
	private List<TransactionLog> transactionLogsIssue;

	private List<TransactionLog> transactionLogsRecived;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
	
	
}