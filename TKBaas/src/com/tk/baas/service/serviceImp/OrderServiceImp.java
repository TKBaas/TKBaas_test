package com.tk.baas.service.serviceImp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tk.baas.dao.CartDao;
import com.tk.baas.dao.CommentDao;
import com.tk.baas.dao.OrderDao;
import com.tk.baas.dao.SellerDao;
import com.tk.baas.dao.UserDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerItem;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.OrderSearchForm;
import com.tk.baas.model.OrderSubmitForm;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.User;
import com.tk.baas.service.OrderService;

@Component("OrderService")
public class OrderServiceImp implements OrderService{

	@Resource(name="OrderDao")
	private OrderDao orderDao;
	
	@Resource(name="CartDao")
	private CartDao cartDao;
	
	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Resource(name="SellerDao")
	private SellerDao sellerDao;

	@Resource(name="CommentDao")
	private CommentDao commentDao;
	
	@Override
	public Cart getBuyList(String[] proItemIds, Cart cart) {
		// TODO Auto-generated method stub
		Cart newCart = new Cart();
		newCart.setSellerItem(new HashSet<CartSellerItem>());
		
		for(int i = 0; i < proItemIds.length; i++){
			
			String proItemId  = proItemIds[i];
			Set<CartSellerItem> sellerItems = newCart.getSellerItem();
			CartSellerProItem proItem = cartDao.getCartSellerProItem(proItemId);
			Product product = proItem.getProduct();
			String sellerId = product.getSeller().getId();
			
			boolean flag = false;
			for(CartSellerItem sellerItem : sellerItems){
				
				if(sellerItem.getSellerId().equals(sellerId)){
					sellerItem.getProItem().add(proItem);
					flag = true;
					break;
				}
			}
			
			if(!flag){
				CartSellerItem sellerItem = new CartSellerItem();
				sellerItem.setSellerId(sellerId);
				sellerItem.setShopName(product.getSeller().getShopName());
				sellerItem.setProItem(new HashSet<CartSellerProItem>());
				sellerItem.getProItem().add(proItem);
				newCart.getSellerItem().add(sellerItem);
			}
		}
		return newCart;
	}
	
	@Override
	public boolean submitOrder(String userId, OrderSubmitForm orderForm) {
		// TODO Auto-generated method stub
		boolean flag = false;
		double user_money;
		try{
			
			User user = userDao.getUserDetail(userId);
			user_money = user.getMoney();
			if(user_money <= orderForm.getTotalMoney()) {
				return false;
			}
			
			 flag = orderDao.addOrder(userId ,
					         orderForm.getProItemIds(),
					         orderForm.getProIds(), orderForm.getAddressId());
			 
			 System.out.print("flag--->" + flag);
			 
			 
			if(flag){
				//userDao.updateMoney(userId, -orderForm.getTotalMoney());
				
				cartDao.delIncart(userId, orderForm.getProIds());
				for(String proId : orderForm.getProIds()){
					commentDao.addComment(userId, proId);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return flag;
		}
	}
	
	@Override
	public ResultPage getUserOrder(String userId, String currentPage,
			                       String pageSize) {
		int tc = 1;
		int ts = 20;
		if(currentPage != null && !currentPage.equals(""))
			tc = Integer.parseInt(currentPage);
		if(pageSize != null && !pageSize.equals(""))
			ts = Integer.parseInt(pageSize);
		
		Page page = new Page();
		page.setCurrentPage(tc);
		page.setPageSize(ts);
		return orderDao.getUserOrder(userId, page);
	}
	
	@Override
	public ResultPage getSearchUserOrder(String userId,
			OrderSearchForm orderSearchForm) {
		// TODO Auto-generated method stub
		int tc = 1;
		int ts = 20;
		if(orderSearchForm.getCurrentPage() > 0)tc = orderSearchForm.getCurrentPage();
		if(orderSearchForm.getPageSize() > 0)ts = orderSearchForm.getPageSize();
		
		Page page = new Page();
		page.setCurrentPage(tc);
		page.setPageSize(ts);
		return orderDao.getUserOrder(userId, orderSearchForm.getKey(),
				                     orderSearchForm.getRegion(),
				                     orderSearchForm.getLeft(),
				                     orderSearchForm.getRight(),
				                     page);
	}
	
	@Override
	public ResultPage getSellerOrder(String sellerId, String currentPage,
			                       String pageSize) {
		// TODO Auto-generated method stub
		int tc = 1;
		int ts = 20;
		if(currentPage != null && !currentPage.equals(""))
			tc = Integer.parseInt(currentPage);
		if(pageSize != null && !pageSize.equals(""))
			ts = Integer.parseInt(pageSize);
		
		Page page = new Page();
		page.setCurrentPage(tc);
		page.setPageSize(ts);
		return orderDao.getSellerOrder(sellerId, page);
	}
	
	@Override
	public ResultPage getSearchSellerOrder(String sellerId,
			OrderSearchForm orderSearchForm) {
		// TODO Auto-generated method stub
		int tc = 1;
		int ts = 20;
		if(orderSearchForm.getCurrentPage() > 0)tc = orderSearchForm.getCurrentPage();
		if(orderSearchForm.getPageSize() > 0)ts = orderSearchForm.getPageSize();
		
		Page page = new Page();
		page.setCurrentPage(tc);
		page.setPageSize(ts);
		return orderDao.getSellerOrder(sellerId, orderSearchForm.getKey(),
				                     orderSearchForm.getRegion(),
				                     orderSearchForm.getLeft(),
				                     orderSearchForm.getRight(),
				                     page);
	}
	
	@Override
	public ResultPage getSearchAllOrder(OrderSearchForm orderSearchForm) {
		// TODO Auto-generated method stub
		int tc = 1;
		int ts = 20;
		if(orderSearchForm.getCurrentPage() > 0)tc = orderSearchForm.getCurrentPage();
		if(orderSearchForm.getPageSize() > 0)ts = orderSearchForm.getPageSize();
		
		Page page = new Page();
		page.setCurrentPage(tc);
		page.setPageSize(ts);
		return orderDao.getOrderListByAll( orderSearchForm.getKey(),
				                           orderSearchForm.getRegion(),
				                           orderSearchForm.getLeft(),
				                           orderSearchForm.getRight(),
				                           page);
	}
	
	@Override
	public boolean deleteUserOrders(String userId, String[] orderIds) {
		// TODO Auto-generated method stub
		return orderDao.deleteUserOrders(userId, orderIds);
	}
	
	@Override
	public boolean deleteSellerOrders(String sellerId, String[] orderIds) {
		// TODO Auto-generated method stub
		return orderDao.deleteSellerOrders(sellerId, orderIds);
	}
	
	@Override
	public boolean updateOrderAddress(String orderId, Address address) {
		// TODO Auto-generated method stub
		return orderDao.updateAddress(orderId, address);
	}
	
	@Override
	public boolean updateOrderParam(SaleOrder saleOrder) {
		// TODO Auto-generated method stub
		return orderDao.updateParam(saleOrder);
	}
}
