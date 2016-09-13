package com.tk.baas.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class User implements Serializable{
	
	//user序列化
	private static final long serialVersionUID = -7593115492604457199L;

	private String id;
	
	private String username;//用户名
	
	private String password;//密码
	
	private String phone;//手机号码
	
	private String displayPicture;//头像URL
		
	private String sex;//性别
	
	private double money;//钱包
	
	private boolean blackList;//黑名单

	private Cart cart;
	
	private Set<Address> address;
	
	private Set<SaleOrder> order;
	
	private List<Comment> comment;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid" , strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public boolean isBlackList() {
		return blackList;
	}

	public void setBlackList(boolean blackList) {
		this.blackList = blackList;
	}

	public String getDisplayPicture() {
		return displayPicture;
	}

	public void setDisplayPicture(String displayPicture) {
		this.displayPicture = displayPicture;
	}
    
	@OneToOne(fetch=FetchType.EAGER)
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
    
	@OneToMany(fetch=FetchType.LAZY)
	public Set<SaleOrder> getOrder() {
		return order;
	}

	public void setOrder(Set<SaleOrder> order) {
		this.order = order;
	}
    
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
