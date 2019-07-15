package com.repgen.inventorycloud.modal;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.boot.archive.spi.ArchiveException;
import org.springframework.http.HttpStatus;


public class CriticalItem {
	
	private Long id;
    private String name;
	private Brand brand;
	private UOM uom;
	private Integer quentity;
	
	
	private Integer criticalLevel;
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public UOM getuOm() {
		return uom;
	}
	public void setuOm(UOM uOm) {
		this.uom = uOm;
	}
	public Integer getQuentity() {
		return quentity;
	}
	public void setQuentity(Integer quentity) {
		this.quentity = quentity;
	}
	public Integer getCriticalLevel() {
		return criticalLevel;
	}
	public void setCriticalLevel(Integer criticalLevel) {
		this.criticalLevel = criticalLevel;
	}


}
