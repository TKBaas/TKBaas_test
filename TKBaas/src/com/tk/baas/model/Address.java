package com.tk.baas.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Address {

	private String id;
	
	private String receiver;//收货人
	
	private String phone;//联系电话
	
	private String province;//省份
	
	private String city;//市
	
	private String countyTown;//县或区
	
	private String street;//街道
	
	private String detailsAddress;//详细地址，用户自定义地址
	
	private boolean defaultAddress;//是否为默认地址
	
	private boolean deleted;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid" , strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountyTown() {
		return countyTown;
	}

	public void setCountyTown(String countyTown) {
		this.countyTown = countyTown;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDetailsAddress() {
		return detailsAddress;
	}

	public void setDetailsAddress(String detailsAddress) {
		this.detailsAddress = detailsAddress;
	}

	public boolean isDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
    
}
