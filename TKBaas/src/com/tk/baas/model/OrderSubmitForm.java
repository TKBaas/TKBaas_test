package com.tk.baas.model;

import java.util.List;

public class OrderSubmitForm {
	
	private String addressId;
	private String[] proIds;
    private String[] proItemIds;
    private double totalMoney;
    
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String[] getProIds() {
		return proIds;
	}
	public void setProIds(String[] proIds) {
		this.proIds = proIds;
	}
	public String[] getProItemIds() {
		return proItemIds;
	}
	public void setProItemIds(String[] proItemIds) {
		this.proItemIds = proItemIds;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
    
    
}
