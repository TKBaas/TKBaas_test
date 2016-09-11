package com.tk.baas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;


public interface UserService {
	
	public boolean userRegist(String phone, String passwod);
	
	public boolean userLogin(String phone, String password) ;
	
	public boolean changeMoney(String id, double money) ;
	
	public boolean changePassword(String id, String old_password, String new_password) ;
	
	public boolean updateBlackUser(String id, boolean flag) ;
	
	public boolean updateBlackList(String[] id, boolean flag) ;
	
	public boolean addUser(User user) ;
	
	public boolean updateUser(User user) ;
	
	public User getUserById(String id) ;
	
	public User getUserByPhone(String phone) ;
	
	public ResultPage getUserList(String key, Page page) ;

}
