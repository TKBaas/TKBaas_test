package com.tk.baas.service;

import java.util.List;

import com.tk.baas.model.Address;
import com.tk.baas.model.Cart;
import com.tk.baas.model.OrderSearchForm;
import com.tk.baas.model.OrderSubmitForm;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.User;

public interface OrderService {
	
//	public boolean addOrder(SaleOrder order) ;
//	
//	public boolean deleteOrder(String OrderId) ;
//	
//	public boolean deleteOrders(String[] ordersId) ;
//	
//	public boolean updateOrder(SaleOrder order) ;
//
//	public List<SaleOrder> searchOrder(String key, String number, String table, String page) ;
//	
//	public List<SaleOrder> getSortedOrderlist(String table, String number, String sort, String desc,String page) ;
//	
//	public List<SaleOrder> getRangeOrderlist(String table, String left, String right, String number,String page) ;
//	
//	public List<SaleOrder> getOrderListByAll(String table, String key, String sort, String desc, String left, String right, String number, String page);
//    public boolean addOrder(String userId, Address address, List<Cart> list);
    public Cart getBuyList(String[] proItemIds, Cart cart);
    
    public boolean submitOrder(String userId ,OrderSubmitForm orderForm);
    
    public ResultPage getUserOrder(String userId, String currentPage, String pageSize);
    
    public ResultPage getSearchUserOrder(String userId, OrderSearchForm orderSearchForm);
    
    public ResultPage getSellerOrder(String sellerId, String currentPage, String pageSize);
    
    public ResultPage getSearchSellerOrder(String sellerId, OrderSearchForm orderSearchForm);
    
    public ResultPage getSearchAllOrder(OrderSearchForm orderSearchForm);
    
    public boolean deleteUserOrders(String userId, String[] orderIds);
    
    public boolean deleteSellerOrders(String sellerId, String[] orderIds);
    
    public boolean updateOrderAddress(String orderId, Address address);
    
    public boolean updateOrderParam(SaleOrder saleOrder);
}
