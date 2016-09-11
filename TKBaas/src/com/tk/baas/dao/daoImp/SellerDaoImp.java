package com.tk.baas.dao.daoImp;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.tk.baas.dao.SellerDao;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;

@Component("SellerDao")
public class SellerDaoImp implements SellerDao {
    
   @Resource(name="sessionFactory")	
   private SessionFactory sessionFactory;
	
   @Override
   public boolean addSeller(Seller seller) {
	// TODO Auto-generated method stub
	  Session session = sessionFactory.getCurrentSession();
	  session.beginTransaction();
	  
	  seller.setBlackList(false);
	  seller.setGrade(0);
	  seller.setOrder(new HashSet<SaleOrder>());
	  seller.setProduct(new HashSet<Product>());
	  session.save(seller);
	  
	  session.getTransaction().commit();
	  return true;
   }
   
   @Override
	public boolean deleteSeller(String[] sellerId) {
		// TODO Auto-generated method stub
		  Session session = sessionFactory.getCurrentSession();
		  session.beginTransaction();
		  
		  for(String id : sellerId){
			  Seller seller = new Seller();
			  seller.setId(id);
			  session.delete(seller);  
		  }
		  
		  session.getTransaction().commit();
		  return true;
	}
   
   @Override
	public Seller getSeller(String phone) {
		// TODO Auto-generated method stub
	    Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		List<Seller> list = session.createQuery
				("from Seller s where s.phone = ?").setString(0, phone).list();
       
		session.getTransaction().commit();
		if(list.isEmpty())return null;   
		return list.get(0);
	}
   
   @Override
	public ResultPage getSellersForAdmin(String key, String sort,
			String region, String left, String right, Page page) {
		// TODO Auto-generated method stub
	    Session session = sessionFactory.getCurrentSession();
	    session.beginTransaction();
	    
	    String tn;//搜索的商家名称name	  
		String to;//排序的依据orderBy
		String tg;//筛选的区间
		String tl;//区间下限
		String tr;//区间上限
		
		if(key == null || key.equals(""))tn = "";
		else tn = key;
	
		if(sort == null || sort.equals(""))to = "";
		else  to = " order by s." + sort;
		
		if(region == null || region.equals(""))tg = "";
		else {
			if(left == null  || left.equals(""))tl = "-1";
			else tl = left;
			
			if(right == null || right.equals(""))tr = "100000000";	
			else tr = right;
			
			tg = " and (s." + region + " between "
				         + tl + " and "+ tr + ") ";
		}
		String hql = null;
	    hql = "from Seller s where (s.name like '%"+tn+"%' or "
	        + "s.phone like '%"    + tn + "%' or "
	        + "s.shopName like '%" + tn + "%' or "
	        + "s.chinaID like '%"  + tn + "%' )"
	        + tg + to;
	                
	     //分页模糊搜索   
	    Query q = session.createQuery(hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<Seller> list = q.list();
		
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
	public ResultPage getSellersForUser(String key, String sort, Page page){
		// TODO Auto-generated method stub
	    Session session = sessionFactory.getCurrentSession();
	    session.beginTransaction();
	    
	    String tn;//搜索的商家名称name	  
		String to;//排序的依据orderBy
		
		if(key == null || key.equals(""))tn = "";
		else tn = key;
	
		if(sort == null || sort.equals(""))to = "";
		else  to = " order by s." + sort + " DESC";
		
		String hql = null;
	    hql = "from Seller s where (s.name like '%"+tn+"%' or "
	        + "s.phone like '%"    + tn + "%' or "
	        + "s.shopName like '%" + tn + "%' or "
	        + "s.chinaID like '%"  + tn + "%' )"
	        + to;
	                
	     //分页模糊搜索   
	    Query q = session.createQuery(hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<Seller> list = q.list();
		
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
	public boolean updateBySeller(Seller seller) {
		// TODO Auto-generated method stub
		  Session session = sessionFactory.getCurrentSession();
		  session.beginTransaction();
		  
		  Seller newSeller = session.get(Seller.class, seller.getId());
		  //newSeller.setChinaID(seller.getChinaID());
		  //newSeller.setGrade(seller.getGrade());
		  newSeller.setName(seller.getName());
		  newSeller.setPassword(seller.getPassword());
		  //newSeller.setPhone(seller.getPhone());
		  //newSeller.setSales(seller.getSales());
		  newSeller.setShopName(seller.getShopName());
		  newSeller.setShopDescription(seller.getShopDescription());
		  newSeller.setShopPicture(seller.getShopPicture());
		  //newSeller.setShopMoney(seller.getShopMoney());
		  
		  session.update(newSeller);
		  session.getTransaction().commit();
		  return true;
	}
    
    @Override
	public boolean updateByAdmin(Seller seller) {
		// TODO Auto-generated method stub
		  Session session = sessionFactory.getCurrentSession();
		  session.beginTransaction();
		  
		  Seller newSeller = session.get(Seller.class, seller.getId());
		  //newSeller.setChinaID(seller.getChinaID());
		  newSeller.setGrade(seller.getGrade());
		  newSeller.setName(seller.getName());
		  newSeller.setPassword(seller.getPassword());
		  newSeller.setPhone(seller.getPhone());
		  newSeller.setSales(seller.getSales());
		  newSeller.setShopName(seller.getShopName());
		  newSeller.setShopDescription(seller.getShopDescription());
		  newSeller.setShopPicture(seller.getShopPicture());
		  newSeller.setShopMoney(seller.getShopMoney());
		  newSeller.setBlackList(seller.isBlackList());
		  
		  session.update(newSeller);
		  session.getTransaction().commit();
		  return true;
	}
    
    @Override
    	public Seller getSellerDetail(String sellerId) {
    		// TODO Auto-generated method stub
    	    Session session = sessionFactory.getCurrentSession();
		    session.beginTransaction();
		
		    Seller seller = session.get(Seller.class, sellerId);
		    int ta = seller.getOrder().size();
		    int tb = seller.getProduct().size();
		    
		    session.getTransaction().commit();
		    return seller;
    	}
    
    @Override
    	public boolean updatePicture(String sellerId, String picURL) {
    		// TODO Auto-generated method stub
    	  Session session = sessionFactory.getCurrentSession();
		  session.beginTransaction();
		  
		  Seller newSeller = session.get(Seller.class, sellerId);
		  newSeller.setShopPicture(picURL);
		  
		  session.update(newSeller);
		  session.getTransaction().commit();
		  return true;
    	}
    
    
}
