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

import com.tk.baas.dao.ProductDao;
import com.tk.baas.model.Cart;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.Picture;
import com.tk.baas.model.ProDetailPicture;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;

@Component("ProductDao")
public class ProductDaoImp implements ProductDao {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	
	@Override
	public boolean addProduct(String sellerId ,Product product) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Seller seller = session.get(Seller.class, sellerId);
		//product.setDetailPicture(new HashSet<ProDetailPicture>());
		product.setIfDeleted(false);
		product.setSeller(seller);
		product.setComment(new HashSet<Comment>());
		product.setSales(0);
		seller.getProduct().add(product);
		//product.setUser(new HashSet<User>());
		session.save(product);
		session.update(seller);
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteProduct(String productId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Product product = session.get(Product.class, productId);
		product.setIfDeleted(true);
		session.update(product);
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteProducts(String[] productsId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		for(String id : productsId){
			Product product = session.get(Product.class, id);
			product.setIfDeleted(true);
			session.update(product);
		}
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean updateBySeller(Product product) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
        Product newPro = session.get(Product.class, product.getId());
		newPro.setCity(product.getCity());
        newPro.setDescription(product.getDescription());
		newPro.setName(product.getName());
        newPro.setPrice(product.getPrice());
		//newPro.setSales(product.getSales());
        newPro.setStore(product.getStore());
		newPro.setType(product.getType());
        session.update(newPro);
		
		session.getTransaction().commit();
		return true;
	}
    
	@Override
	public boolean updateByAdmin(Product product) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
        Product newPro = session.get(Product.class, product.getId());
		newPro.setCity(product.getCity());
        newPro.setDescription(product.getDescription());
		newPro.setName(product.getName());
        newPro.setPrice(product.getPrice());
		newPro.setSales(product.getSales());
        newPro.setStore(product.getStore());
		newPro.setType(product.getType());
        session.update(newPro);
		
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public ResultPage getSellerProduct(String sellerId, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		String hql = "from Product p "
				   + "inner join p.seller s "
				   + "where s.id = '" + sellerId +"'";
		Query q = session.createQuery("select p " + hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<Product> list = q.list();
		
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
	public ResultPage getProductListByAll(String key, String sort,String region,
			String left, String right, Page page) {
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
	
		if(sort == null || sort.equals(""))to = "";
		else  to = " order by p." + sort;
		
		if(region == null || region.equals(""))tg = "";
		else {
			if(left == null  || left.equals(""))tl = "-1";
			else tl = left;
			
			if(right == null || right.equals(""))tr = "100000000";	
			else tr = right;
			
			tg = " and (p." + region + " between "
				         + tl + " and "+ tr + ") ";
		}
		String hql = null;
	    hql = "from Product p where (p.name like '%"+tn+"%' or "
	        + "p.city like '%" + tn + "%' or "
	        + "p.type like '%" + tn + "%' )"
	        + tg + to;
	    
	    System.out.println("HQL--->>" + hql);
	    
	     //分页模糊搜索   
	    Query q = session.createQuery(hql)
	       		.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<Product> list = q.list();
		
	    //获得记录数
	    q = session.createQuery("select count(*) " + hql);
	    String num = "0";
        if(q.uniqueResult() != null ) num = q.uniqueResult().toString();
		 
        System.out.println("HQL-NUM--->" + num + "-->>>" + list.size());
        
        ResultPage resultPage = new ResultPage();
	    resultPage.setCurrentPage(page.getCurrentPage());
		resultPage.setPageSize(page.getPageSize());
	    resultPage.setTotalNum(Integer.parseInt(num));
		resultPage.setResult(list);
	    
	    session.getTransaction().commit();
        return resultPage;
	}
	
	@Override
	public boolean addSales(String productId, int sale) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Product product = session.get(Product.class, productId);
		product.setSales(product.getSales()+sale);
		session.update(product);
		
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public Product getProduct(String productId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Product product = session.get(Product.class, productId);
		if(product == null)return null;
		int ta = product.getSeller().getGrade();
		int tb = product.getComment().size();
		int tc = product.getPicture().size();
		int td = product.getDetailPicture().size();
		//int td = product.getUser().size();
		
		session.getTransaction().commit();
		return product;
	}
	
	@Override
	public boolean updatePicture(String productId, String oldPicName,
			                     String newPicName, String newTinyPicName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Product product = session.get(Product.class, productId);
		Set<Picture> set = product.getPicture();
		
		for(Picture pic : set){
			if(pic.getPictureUrl().equals(oldPicName)){
				Picture temp = session.get(Picture.class, pic.getId());
				temp.setPictureUrl(newPicName);
				temp.setTinyPictureUrl(newTinyPicName);
				session.update(temp);
				break;
			}
		}
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public boolean updateDetailPicture(String productId, 
			       String oldPicName, String newPicName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Product product = session.get(Product.class, productId);
		Set<ProDetailPicture> set = product.getDetailPicture();
		
		for(ProDetailPicture pic : set){
			if(pic.getDetailPicUrl().equals(oldPicName)){
				ProDetailPicture temp = session.get(ProDetailPicture.class, pic.getId());
				temp.setDetailPicUrl(newPicName);
				session.update(temp);
				break;
			}
		}
		session.getTransaction().commit();
		return true;
	}
}
