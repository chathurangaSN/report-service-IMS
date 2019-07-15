package com.repgen.inventorycloud.modal;

import java.time.ZonedDateTime;

public class TransactionEntries {
	
	ZonedDateTime dateTime;
	Double quantity;
	
	public ZonedDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	

}
