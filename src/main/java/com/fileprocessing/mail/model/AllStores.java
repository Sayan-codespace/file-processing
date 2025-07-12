package com.fileprocessing.mail.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="stores")
public class AllStores {
	
	@Id
	private int storeId;
	private String storeLocation;
	private String isValid;
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreLocation() {
		return storeLocation;
	}
	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public AllStores(int storeId, String storeLocation, String isValid) {
		super();
		this.storeId = storeId;
		this.storeLocation = storeLocation;
		this.isValid = isValid;
	}
	public AllStores() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AllStores [storeId=" + storeId + ", storeLocation=" + storeLocation + ", isValid=" + isValid + "]";
	}
}
