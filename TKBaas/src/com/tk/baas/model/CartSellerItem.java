package com.tk.baas.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

//购物车的商家
@Entity
public class CartSellerItem {
	
    private String id;
    private String sellerId;
    private String shopName;
    private Set<CartSellerProItem> proItem;
	
    @Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid" , strategy="uuid")
    public String getId() {
		return id;
	}
    
	public void setId(String id) {
		this.id = id;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="cartSeller_id")
	public Set<CartSellerProItem> getProItem() {
		return proItem;
	}
	
	public void setProItem(Set<CartSellerProItem> proItem) {
		this.proItem = proItem;
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
}
