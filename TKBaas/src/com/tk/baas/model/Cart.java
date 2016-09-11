package com.tk.baas.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
//购物车
@Entity
public class Cart {
	
    private String id;
    private Set<CartSellerItem> sellerItem;
    
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
	@JoinColumn(name="sellerItem_id")
	public Set<CartSellerItem> getSellerItem() {
		return sellerItem;
	}

	public void setSellerItem(Set<CartSellerItem> sellerItem) {
		this.sellerItem = sellerItem;
	}

}
