package com.tk.baas.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Product implements Serializable{
	
	
	private static final long serialVersionUID = 4482831662205735412L;

	private String id;
	
	private String name;//商品名称
	
	private String type;//商品类别
	
	private String city;//商品产地
	
	private int store;//商品库存
	
	private double price;//商品价格
	
	private String description;//商品描述
	
	private int sales;//商品销售量
	
	private boolean ifDeleted;//商品是否删除下架
	
	//private Set<User> user;
	
	private Seller seller;
	
	private Set<Comment> comment;
	
	private Set<Picture> picture;
	
	private Set<ProDetailPicture> detailPicture;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public boolean isIfDeleted() {
		return ifDeleted;
	}

	public void setIfDeleted(boolean ifDeleted) {
		this.ifDeleted = ifDeleted;
	}
    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable(name="pros_seller",
	           joinColumns=@JoinColumn(name="pro_id"),
	           inverseJoinColumns=@JoinColumn(name="seller_id"))
	public Seller getSeller() {
		return seller;
	}
    
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
    
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="pro_id")
	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	@OneToMany(fetch=FetchType.EAGER, 
			   cascade=CascadeType.ALL)
	@JoinColumn(name="pro_id")
	public Set<Picture> getPicture() {
		return picture;
	}

	public void setPicture(Set<Picture> picture) {
		this.picture = picture;
	}
    
	@OneToMany(fetch=FetchType.LAZY,
			   cascade=CascadeType.ALL)
	@JoinColumn(name="pro_id")
	public Set<ProDetailPicture> getDetailPicture() {
		return detailPicture;
	}

	public void setDetailPicture(Set<ProDetailPicture> detailPicture) {
		this.detailPicture = detailPicture;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
