package com.tk.baas.dao.daoImp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.tk.baas.dao.OrderDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;

@Component("OrderDao")
public class OrderDaoImp implements OrderDao {
    
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public boolean addOrder(String userId, String[] proItemIds, 
			                String[] proIds, String addressId)
			                throws Exception{
		//TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Address address = session.get(Address.class, addressId);
		
		System.out.println("user-id--->" + user.getId() +"address-->" + address.getId());
		
		Cart cart = user.getCart();
        List<SaleOrder> list = new ArrayList<SaleOrder>();
		
        for(int i = 0; i < proItemIds.length; i ++){
        	
        	String proItemId = proItemIds[i];
        	String proId = proIds[i];
			CartSellerProItem proItem = session.get(CartSellerProItem.class, proItemId);
			Product product = session.get(Product.class, proId);
			String sellerId = product.getSeller().getId();
			
			//相同商家
			boolean flag = false;
        	for(SaleOrder temp : list){
        		if(temp.getSellerId().equals(sellerId)){
        			temp.getProItems().add(proItem);
        			temp.setMoney(temp.getMoney()+proItem.getNum()*product.getPrice());
        		    flag = true;
        		    break;
        		}
        	}
        	
        	
        	//订单中的不同商家
        	if(!flag){
        		SaleOrder order = new SaleOrder();
        		order.setAddress(address);
        		
        		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        		
        		
        		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		Date date = sf.parse(sf.format(new Date()));
        		System.out.println(date);
        		
        		order.setBoughtDate(date);
        		order.setProItems(new HashSet<CartSellerProItem>());
//        		order.setSellerState("waiting");
        		order.setUserId(userId);
//        		order.setUserState("pay");
        		order.setMoney(proItem.getNum() * product.getPrice());
        		order.setSellerId(sellerId);
        		order.setShopName(product.getSeller().getShopName());
        		order.getProItems().add(proItem);
        		list.add(order);
        	}
        }
		
        System.out.println("length-->" + proItemIds.length);
        for(SaleOrder order : list){
        	
        	user.getOrder().add(order);
        	order.setState("unsent");
        	
        	user.setMoney(user.getMoney() - order.getMoney());
        	Seller temp = session.get(Seller.class, order.getSellerId());
        	temp.setShopMoney(temp.getShopMoney() + order.getMoney());
        	
        	session.save(order);
        	session.update(temp);
        }
        session.update(user);
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public boolean deleteUserOrders(String userId, String[] orderIds) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Set<SaleOrder> set = user.getOrder();
		for(String orderId : orderIds){
            for(SaleOrder order : set){
            	order.setUserDeleted(true);
            	user.getOrder().remove(order);
                session.update(order);
        }}
		session.update(user);
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteSellerOrders(String sellerId, String[] orderIds) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Seller seller = session.get(Seller.class, sellerId);
		Set<SaleOrder> set = seller.getOrder();
		for(String orderId : orderIds){
            for(SaleOrder order : set){
            	order.setUserDeleted(true);
            	seller.getOrder().remove(order);
                session.update(order);
        }}
		session.update(seller);
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean updateAddress(String orderId, Address address) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		SaleOrder to = session.get(SaleOrder.class, orderId);
		to.setAddress(address);
		session.update(to);
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean updateParam(SaleOrder order) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		SaleOrder to = session.get(SaleOrder.class, order.getId());
		to.setState(order.getState());
		session.update(to);
		
		session.getTransaction().commit();
		return true;
	}
    
	@Override
	public ResultPage getUserOrder(String userId, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from SaleOrder o where o.userId = '" + userId 
				   + "' order by o.boughtDate desc";
		Query q = session.createQuery(hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<SaleOrder> list = q.list();
		
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
	public ResultPage getUserOrder(String userId, String key,
			String region, String left, String right, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
	    session.beginTransaction();
	    
	    String tn;//搜索的商品名称name	  
		String to;//排序的依据orderBy
		String tg;//筛选的区间
		String tl;//区间下限
		String tr;//区间上限
		
		if(key == null || key.equals(""))tn = "";
		else tn = key;
		
		if(region == null || region.equals(""))tg = "";
		else {
			if(left == null  || left.equals(""))tl = "-1";
			else tl = left;
			
			if(right == null || right.equals(""))tr = "999999999";	
			else tr = right;
			
			tg = " and (saleOrder." + region + " >= '" + tl + "'"
			   + " and  saleOrder." + region + " <= '" + tr + "')";
		}
		to = " order by saleOrder.boughtDate desc";
		String hql = null;
	    hql = "from User user "
			+ "inner join user.order saleOrder "
	    	+ "inner join saleOrder.proItems proItems "
	    	+ "inner join proItems.product pro where  "
	    	+ "(user.id = '"   + userId    + "') and ("
	    	+ "saleOrder.shopName like '%" + tn + "%' or "
	    	+ "saleOrder.state like '"     + tn + "'  or "
	        + "pro.name like '%"           + tn + "%' or "
	        + "pro.type like '%"           + tn + "%' )" 
	        + tg + to;
	                
	     //分页模糊搜索   
	    Query q = session.createQuery("select distinct saleOrder " + hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<SaleOrder> list = q.list();
		
	    System.out.println("size--->" + list.size());
	    System.out.println(hql);
	    
	    //获得记录数
	    q = session.createQuery("select count(*) " + hql);
	    String num = "0";
        if(q.uniqueResult() != null ) num = q.uniqueResult().toString();
		
        System.out.println("num-->" + num);
        
        ResultPage resultPage = new ResultPage();
	    resultPage.setCurrentPage(page.getCurrentPage());
		resultPage.setPageSize(page.getPageSize());
	    resultPage.setTotalNum(Integer.parseInt(num));
		resultPage.setResult(list);
	    
	    session.getTransaction().commit();
        return resultPage;
	}

	@Override
	public ResultPage getSellerOrder(String sellerId, String key,
			String region, String left, String right, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
	    session.beginTransaction();
	    
	    String tn;//搜索的商品名称name	  
		String to;//排序的依据orderBy
		String tg;//筛选的区间
		String tl;//区间下限
		String tr;//区间上限
		
		if(key == null || key.equals(""))tn = "";
		else tn = key;
		
		if(region == null || region.equals(""))tg = "";
		else {
			if(left == null  || left.equals(""))tl = "-1";
			else tl = left;
			
			if(right == null || right.equals(""))tr = "999999999";	
			else tr = right;
			
			tg = " and (saleOrder." + region + " >= '" + tl + "'"
			   + " and  saleOrder." + region + " <= '" + tr + "')";
		}
		to = " order by saleOrder.boughtDate desc";
		String hql = null;
	    hql = "from Seller seller "
			+ "inner join seller.order saleOrder "
	    	+ "inner join saleOrder.proItems proItems "
	    	+ "inner join proItems.product pro where  "
	    	+ "(seller.id = '"  + sellerId + "') and ("
	    	+ "saleOrder.shopName like '%" + tn + "%' or "
	    	+ "saleOrder.state ='"         + tn + "'  or "
	        + "pro.name like '%"           + tn + "%' or "
	        + "pro.type like '%"           + tn + "%' )" 
	        + tg + to;
	                
	     //分页模糊搜索   
	    Query q = session.createQuery("select saleOrder " + hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<SaleOrder> list = q.list();
		
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
	public ResultPage getOrderListByAll(String key, String region, String left, 
			                            String right, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
	    session.beginTransaction();
	    
	    String tn;//搜索的商品名称name	  
		String to;//排序的依据orderBy
		String tg;//筛选的区间
		String tl;//区间下限
		String tr;//区间上限
		
		if(key == null || key.equals(""))tn = "";
		else tn = key;
		
		if(region == null || region.equals(""))tg = "";
		else {
			if(left == null  || left.equals(""))tl = "-1";
			else tl = left;
			
			if(right == null || right.equals(""))tr = "999999999";	
			else tr = right;
			
			tg = " and (saleOrder." + region + " >= " + tl 
			   + " and saleOrder."  + region + " <= " + tr + ") ";
		}
		to = " order by saleOrder.boughtDate desc";
		String hql = null;
	    hql = "from SaleOrder saleOrder "
	    	+ "inner join saleOrder.proItems proItems "
	    	+ "inner join proItems.product pro where ("	
	    	+ "saleOrder.shopName like '%" + tn + "%' or "
	        + "saleOrder.state =  '"       + tn + "'  or "
	        + "pro.name like '%"           + tn + "%' or "
	        + "pro.type like '%"           + tn + "%' )" 
	        + tg + to;
	                
	     //分页模糊搜索   
	    Query q = session.createQuery("select saleOrder " + hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<SaleOrder> list = q.list();
		
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
	public ResultPage getSellerOrder(String sellerId, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		String hql = "from SaleOrder o where o.sellerId = '" + sellerId 
				   + "' order by o.boughtDate desc";
		Query q = session.createQuery(hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<SaleOrder> list = q.list();
		
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
}
