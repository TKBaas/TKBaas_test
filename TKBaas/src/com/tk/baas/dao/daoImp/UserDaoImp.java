package com.tk.baas.dao.daoImp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.tk.baas.dao.UserDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Cart;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.User;

@Component("UserDao")
public class UserDaoImp implements UserDao {
    
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		user.setAddress(new HashSet<Address>());
		user.setBlackList(false);
		user.setComment(new ArrayList<Comment>());
		user.setOrder(new HashSet<SaleOrder>());
		session.save(user);
		
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public boolean deleteUser(String userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = new User();
		user.setId(userId);
		session.delete(user);
		
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public boolean deleteUsers(String[] usersId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		for(String temp : usersId){
			User user = new User();
			user.setId(temp);
			session.delete(user);
		}
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public User getUser(String phone) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		List<User> list = session.createQuery
				("from User u where u.phone = ?").setString(0, phone).list();
        
		session.getTransaction().commit();
		if(list.isEmpty())return null;   
		return list.get(0);
	}
	
	@Override
	public ResultPage getUserList(String key, Page page) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.getCurrentSession();
		 session.beginTransaction();
		 
		 String hql = "from User u where "
		 		    + "u.username like '%"  + key + "%' or "
	                + "u.id like '%"        + key + "%' or "
	                + "u.phone like '%"     + key + "%' or "
	                + "u.sex like '%"       + key + "%' or "
	                + "u.blackList like '%" + key + "%'";
	     //分页模糊搜索   
	     Query q = session.createQuery(hql).
	       		setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	     List<User> list = q.list();
	     //获得记录数
	     q = session.createQuery("select count(*) " + hql);
	     String num = "0";
         if(q.uniqueResult() != null ) num = q.uniqueResult().toString();
		 
         ResultPage resultPage = new ResultPage();
	     resultPage.setCurrentPage(page.getCurrentPage());
		 resultPage.setPageSize(page.getPageSize());
	     resultPage.setTotalNum(Integer.parseInt(num));
		 resultPage.setResult(list);
	     
	     session.getTransaction().commit();
         return resultPage;
	}
	
	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User newUser = session.get(User.class, user.getId());
		newUser.setDisplayPicture(user.getDisplayPicture());
		newUser.setPassword(user.getPassword());
		newUser.setSex(user.getSex());
		newUser.setUsername(user.getUsername());
		
		session.update(newUser);
		session.getTransaction().commit();
		return true;
	}
    
	@Override
	public boolean updateMoney(String userId, double money) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		if(user != null){
			user.setMoney(user.getMoney()+money);
			session.update(user);
		}
		
		session.getTransaction().commit();
		if(user != null)return true;
		else return false;
	}
	
	@Override
	public boolean updateBlackList(String userId , boolean flag) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		user.setBlackList(flag);
		
		session.update(user);
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public User getUserDetail(String userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		int ta = user.getOrder().size();
		
		session.getTransaction().commit();
		return user;
	}
}
