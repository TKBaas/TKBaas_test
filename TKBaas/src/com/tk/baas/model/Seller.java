package com.tk.baas.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Seller implements Serializable{
	
	private static final long serialVersionUID = 5746181806261942731L;

	private String id;
	
	private String password;//商家密码
	
	private String name;//商家姓名
	
	private String shopName;//店铺名
	
	private String shopPicture;//店铺照片url
	
	private String shopDescription;//商店简介
	
	private double shopMoney;//商店账户金额
	
	private int grade;//商店等级
	
	private String chinaID;//身份证号
	
	private String phone;//手机号
	
	private int sales;//店铺销售量
	
	private boolean blackList;

	private Set<Product> product;
	
	private Set<SaleOrder> order;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getChinaID() {
		return chinaID;
	}

	public void setChinaID(String chinaID) {
		this.chinaID = chinaID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShopPicture() {
		return shopPicture;
	}

	public void setShopPicture(String shopPicture) {
		this.shopPicture = shopPicture;
	}

	public String getShopDescription() {
		return shopDescription;
	}

	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}

	public double getShopMoney() {
		return shopMoney;
	}

	public void setShopMoney(double shopMoney) {
		this.shopMoney = shopMoney;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}
    
	@OneToMany(fetch=FetchType.LAZY,
			   mappedBy="seller")
	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}
    
	@OneToMany(fetch=FetchType.LAZY)
	public Set<SaleOrder> getOrder() {
		return order;
	}

	public void setOrder(Set<SaleOrder> order) {
		this.order = order;
	}

	
	public boolean isBlackList() {
		return blackList;
	}

	public void setBlackList(boolean blackList) {
		this.blackList = blackList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
