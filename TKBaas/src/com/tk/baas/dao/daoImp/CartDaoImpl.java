package com.tk.baas.dao.daoImp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.tk.baas.dao.CartDao;
import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerItem;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.Product;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;

@Component("CartDao")
public class CartDaoImpl implements CartDao{
    
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addIncart(String userId, String productId,
			                 int num) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Product product = session.get(Product.class, productId);
		String sellerId = product.getSeller().getId();
		//获得购物车
		Cart cart = user.getCart();
		if(cart == null){
			
			cart = new Cart();
			cart.setSellerItem(new HashSet<CartSellerItem>());
			user.setCart(cart);
		}
		
		//添加进购物车
		boolean flag2 = false;
		Set<CartSellerItem> set = cart.getSellerItem();
		for(CartSellerItem itSeller : set){
			//购物车有该商家
			if(itSeller.getSellerId().equals(sellerId)){
				
				flag2 = true;
				boolean flag = false;
				Set<CartSellerProItem> set2 = itSeller.getProItem(); 
				for(CartSellerProItem proItem : set2){
					if(proItem.getProduct().getId().equals(productId)){
						CartSellerProItem item = session.get(CartSellerProItem.class, proItem.getId());
						item.setNum(item.getNum() + num);
						session.update(item);
						flag = true;
						break;
					}
				}
				if(flag)break;
				else{//购物车有该商家但没有该商品
					CartSellerProItem proItem = new CartSellerProItem();
					proItem.setNum(num);
					proItem.setProduct(product);
					CartSellerItem item = session.get(CartSellerItem.class, itSeller.getId());
					item.getProItem().add(proItem);
					session.save(proItem);
					session.update(item);
					break;
				}
			}
			if(flag2)break;
		}
		
		//购物车没有该商家
		if(!flag2){
			CartSellerItem newSellerItem = new CartSellerItem();
			CartSellerProItem newProItem = new CartSellerProItem();
			newProItem.setNum(num);
			newProItem.setProduct(product);
			newSellerItem.setProItem(new HashSet<CartSellerProItem>());
			newSellerItem.getProItem().add(newProItem);
			newSellerItem.setSellerId(product.getSeller().getId());
			newSellerItem.setShopName(product.getSeller().getShopName());
			cart.getSellerItem().add(newSellerItem);
			
			session.save(newProItem);
			session.save(newSellerItem);
		}
		
		session.saveOrUpdate(cart);
		session.update(user);
		session.getTransaction().commit();
		return true;
	}

	@Override
	public Cart getUserProduct(String userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Cart cart = user.getCart();
		
		session.getTransaction().commit();
		return cart;
	}

	@Override
	public boolean delIncart(String userId, String[] productId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Cart cart = session.get(Cart.class, user.getCart().getId());
		Set<CartSellerItem> sellerItems = cart.getSellerItem();
		
		for(String proId : productId){
			
			Product product = session.get(Product.class, proId);
			String sellerId = product.getSeller().getId();
		    //在购物车查找商品的商家
			for(CartSellerItem sellerItem : sellerItems){
		    	if(sellerItem.getSellerId().equals(sellerId)){
		    		
		    		CartSellerItem sellerTemp = session.get(
		    				  CartSellerItem.class, sellerItem.getId());
		    		Set<CartSellerProItem> proItems = 
		    				  sellerTemp.getProItem();
		    		//在购物车中查找商家是否有该商品
		    		for(CartSellerProItem proItem : proItems){
		    			
		    			if(proItem.getProduct().getId().equals(proId)){
		    				
		    				sellerTemp.getProItem().remove(proItem);
		    				CartSellerProItem proItemTemp = 
		    						session.get(CartSellerProItem.class, proItem.getId());
		    			    
		    				if(sellerTemp.getProItem().size() == 0){
		    					cart.getSellerItem().remove(sellerTemp);
		    					session.update(cart);
		    					session.update(sellerTemp);
		    				}else{
		    					session.update(sellerTemp);
		    				}
		    				
		    				//session.delete(proItemTemp);
		    				break;
		    			}
		    		}
		    		break;
		 }}}
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean updateCart(String userId, String[] productId, int[] num) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Cart cart = user.getCart();
		Set<CartSellerItem> sellerItems = cart.getSellerItem();
		
        for(int i = 0; i < productId.length; i++){
			
        	String proId = productId[i];
        	int newNum = num[i];
			Product product = session.get(Product.class, proId);
			String sellerId = product.getSeller().getId();
		    
			for(CartSellerItem sellerItem : sellerItems){
		    	if(sellerItem.getSellerId().equals(sellerId)){
		    		
		    		CartSellerItem sellerTemp = session.get(
		    				  CartSellerItem.class, sellerItem.getId());
		    		Set<CartSellerProItem> proItems = 
		    				  sellerTemp.getProItem();
		    		
		    		for(CartSellerProItem proItem : proItems){
		    			if(proItem.getProduct().getId().equals(proId)){
		    				CartSellerProItem proTemp = session.get(
		    						CartSellerProItem.class, proItem.getId());
		    				proTemp.setNum(newNum);
		    				session.update(proTemp);
		    				break;
		    			}
		    		}
		    		break;
		 }}}
		session.getTransaction().commit();
		return true;
	}
	
	@Override
	public CartSellerProItem getCartSellerProItem(String proItemId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		CartSellerProItem proItem  = 
				session.get(CartSellerProItem.class, proItemId);
		Product product = session.get(Product.class, proItem.getProduct().getId());
		Seller seller = product.getSeller();
		String sellerId = seller.getId();
		String shopName = seller.getShopName();
		
		session.getTransaction().commit();
		return proItem;
	}
}
