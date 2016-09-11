package com.tk.baas.dao;

import java.util.List;

import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;

public interface UserDao {
	
	public boolean addUser(User user);
	
	
	//根据用户手机查询
	public User getUser(String phone);
	//用于管理员的用户分页查询
	public ResultPage getUserList(String key, Page page);
	//可用于展示用户详情
	public User getUserDetail(String userId);
	
	
	//用于用户信息修改
	public boolean updateUser(User user);
	//加入黑名单,true表示加入黑名单
    public boolean updateBlackList(String userId , boolean flag);
	//1.用户充值  2.用户购买后钱包扣钱（money为负）
	public boolean updateMoney(String userId , double money);
	
	
	public boolean deleteUser(String userId);
	public boolean deleteUsers(String[] usersId);
}
