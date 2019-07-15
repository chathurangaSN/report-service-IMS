package com.repgen.inventorycloud.modal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class StockDetails {
	
	
    Integer id;
    
   
    Integer itemId;
    
   
//    Integer uomId;
//    
//    
//    Integer brandId;

   
    Double quantity;

  
    Stock stock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

//	public Integer getUomId() {
//		return uomId;
//	}
//
//	public void setUomId(Integer uomId) {
//		this.uomId = uomId;
//	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	
	public Double getQuantity() {
		return quantity;
	}

//	public Integer getBrandId() {
//		return brandId;
//	}
//
//	public void setBrandId(Integer brandId) {
//		this.brandId = brandId;
//	}


	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

    
}
