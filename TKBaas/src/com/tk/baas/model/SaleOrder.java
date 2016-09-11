package com.tk.baas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SaleOrder implements Serializable{

	private static final long serialVersionUID = 1881820052145587451L;

	private String id;
	
	private Set<CartSellerProItem> proItems;//用户购车车的相同店铺的商品
	
	private double money;//订单总额
	
	private String shopName;//商店名字
	
	private Date boughtDate;//下单日期
	
	//用户状态，unPay未付款，pay已付款，waiting等待中，accepted已收货，unAccess待评价，accessed已评价
//	private String userState;
	
	//卖家状态，waiting等待付款，accepted已接单，success交易成功
	private String state;
	
	private Address address;
    
	private String sellerId;//商店Id,建立商品链接
	
	private String userId;//用户id,查询
	
	private boolean userDeleted;
	
	private boolean sellerDeleted;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Date getBoughtDate() {
		return boughtDate;
	}

	public void setBoughtDate(Date boughtDate) {
		this.boughtDate = boughtDate;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
	@OneToOne
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    
	@OneToMany(fetch=FetchType.EAGER)
	public Set<CartSellerProItem> getProItems() {
		return proItems;
	}

	public void setProItems(Set<CartSellerProItem> proItems) {
		this.proItems = proItems;
	}

	public boolean isUserDeleted() {
		return userDeleted;
	}

	public void setUserDeleted(boolean userDeleted) {
		this.userDeleted = userDeleted;
	}

	public boolean isSellerDeleted() {
		return sellerDeleted;
	}

	public void setSellerDeleted(boolean sellerDeleted) {
		this.sellerDeleted = sellerDeleted;
	}
	
	
}
