package com.fileprocessing.mail.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="current-stores")
public class CurrentStores {
	
	@Id
	private int storeId;
	private String storeLocation;
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
	public CurrentStores(int storeId, String storeLocation) {
		super();
		this.storeId = storeId;
		this.storeLocation = storeLocation;
	}
	public CurrentStores() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CurrentStores [storeId=" + storeId + ", storeLocation=" + storeLocation + "]";
	}

}
