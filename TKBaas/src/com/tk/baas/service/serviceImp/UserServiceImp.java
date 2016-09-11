package com.tk.baas.service.serviceImp;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tk.baas.dao.UserDao;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;
import com.tk.baas.service.UserService;

@Component("UserService")
public class UserServiceImp implements UserService{

	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Override
	public boolean userRegist(String phone, String password) {
		
		User user = new User();
		
		if(null == userDao.getUser(phone)) {
			user.setPhone(phone);
			user.setPassword(password);
			return addUser(user);
		}
			
		return false;
		
	}
	
	@Override
	public boolean userLogin(String phone, String password) {
		 
		User user = userDao.getUser(phone);
		
		if(null != user && user.getPassword().equals(password)) 
			return true;
		
		return false;
		
	}
	
	@Override
	public boolean changeMoney(String id, double money) {
		
		return userDao.updateMoney(id, money);
	}
	
	@Override
	public boolean changePassword(String user_id, String old_password,
			String new_password) {
		
		User user = getUserById(user_id);
		
		if(null != user && user.getPassword().equals(old_password)) {
			user.setPassword(new_password);
			return updateUser(user);
		}
			
		return false;
	}
	
	
	@Override
	public boolean updateBlackList(String[] id, boolean flag) {
		boolean result = false;
		for(String userId : id) {
			result = userDao.updateBlackList(userId, flag);
			if(!result) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean addUser(User user) {
		
		boolean addUser = userDao.addUser(user);
		
		return addUser;
	}

	
	@Override
	public boolean updateUser(User user) {
		
		boolean updateUser = userDao.updateUser(user);
		return updateUser;
	}
	
	@Override
	public User getUserById(String id) {
		User user = userDao.getUserDetail(id);
		return user;
	}
	
	public User getUserByPhone(String phone) {
		User user = userDao.getUser(phone);
		return user;
	}

	@Override
	public ResultPage getUserList(String key, Page page) {
		if( page.getCurrentPage()<=0)
			page.setCurrentPage(1);
		if(page.getPageSize() <=0)
			page.setPageSize(1);
		ResultPage userList = userDao.getUserList(key, page);
		return userList;
	}

	@Override
	public boolean updateBlackUser(String id, boolean flag) {
		boolean result = userDao.updateBlackList(id, flag);
		return result;
	}

}
