package com.tk.baas.dao.daoImp;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.tk.baas.dao.AddressDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;

@Component("AddressDao")
public class AddressDaoImp implements AddressDao {
    
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addAddress(String userId, Address address) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		if(address.isDefaultAddress() == true){
			
			Set<Address> set = user.getAddress();
			String id = null;
			for(Address temp : set){
				if(temp.isDefaultAddress() == true){
					Address add = session.get(Address.class, temp.getId());
					add.setDefaultAddress(false);
					session.update(add);
					break;
				}
		}}
		if(user.getAddress().size() == 0)address.setDefaultAddress(true);
		
		user.getAddress().add(address);
		session.save(address);
		session.update(user);
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteAddress(String userId, String addressId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Set<Address> set = user.getAddress();
		boolean flag = false;
		for(Address temp : set){
			if(temp.getId().equals(addressId)){
				user.getAddress().remove(temp);
				temp.setDeleted(true);
				session.update(user);
				session.update(temp);
				flag = true;
				break;
			}
		}
		session.getTransaction().commit();
		return flag == true ? true : false;
	}

	@Override
	public boolean updateAddress(Address address) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Address newAdd = session.get(Address.class, address.getId());
		newAdd.setCity(address.getCity());
		newAdd.setCountyTown(address.getCountyTown());
		newAdd.setStreet(address.getStreet());
		newAdd.setReceiver(address.getReceiver());
		newAdd.setProvince(address.getProvince());
		newAdd.setPhone(address.getPhone());
		newAdd.setDetailsAddress(address.getDetailsAddress());
		newAdd.setDeleted(address.isDeleted());
		session.update(newAdd);
		
		session.getTransaction().commit();
		return true;
	}
    
	@Override
	public boolean updateDefaultAddress(String userId, String addressId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Address address = session.get(Address.class, addressId);
		User user = session.get(User.class, userId);
		Set<Address> set = user.getAddress();
		String id = null;
		for(Address temp : set){
			if(temp.isDefaultAddress() == true){
				id = temp.getId();
				break;
			}
		}
		if(id != null){
			Address add = session.get(Address.class, id);
			add.setDefaultAddress(false);
			session.update(add);
		}
		address.setDefaultAddress(true);
		session.update(address);
		
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public ResultPage getAddress(String userId, String key, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		 
		String hql = "from User user inner join user.address a "
				   + "where user.id = '" + userId + "' and ("
				   + "a.province       like '%" + key + "%' or "
	               + "a.city           like '%" + key + "%' or "
	               + "a.countyTown     like '%" + key + "%' or "
	               + "a.detailsAddress like '%" + key + "%' or "
	               + "a.street         like '%" + key + "%' or "
	               + "a.receiver       like '%" + key + "%' or " 
	               + "a.phone          like '%" + key + "%' )";
	    //分页模糊搜索   
	    Query q = session.createQuery("select a " + hql).
	       		setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<Address> list = q.list();
		 
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
	public boolean updateAddressAll(String userId, Address address) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Address newAdd = session.get(Address.class, address.getId());
		newAdd.setCity(address.getCity());
		newAdd.setCountyTown(address.getCountyTown());
		newAdd.setStreet(address.getStreet());
		newAdd.setReceiver(address.getReceiver());
		newAdd.setProvince(address.getProvince());
		newAdd.setPhone(address.getPhone());
		newAdd.setDetailsAddress(address.getDetailsAddress());
		newAdd.setDeleted(address.isDeleted());
		
		if(address.isDefaultAddress()){
			
		User user = session.get(User.class, userId);
		Set<Address> set = user.getAddress();
		String id = null;
		for(Address temp : set){
			if(temp.isDefaultAddress() == true){
				id = temp.getId();
				break;
			}
		}
		if(id != null && !id.equals(address.getId())){
			Address addt = session.get(Address.class, id);
			addt.setDefaultAddress(false);
			newAdd.setDefaultAddress(true);
			session.update(addt);
		}
	   }
		
		session.update(newAdd);
		session.getTransaction().commit();
		return true;
	}
}
