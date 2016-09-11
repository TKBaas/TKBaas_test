package com.tk.baas.dao;

import java.util.List;

import com.tk.baas.model.Address;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.Product;

public interface OrderDao {
    
	public boolean addOrder(String userId, String[] proItemIds, 
                            String[] proIds, String addressId)
                            throws Exception;
	
	
	public boolean deleteUserOrders(String userId, String[] orderIds);
	public boolean deleteSellerOrders(String sellerId, String[] orderIds);
	
	
	//更换收货地址
	public boolean updateAddress(String orderId, Address address);
	/**
	 * 更新订单的用户和卖家的状态，使用时需同时传入用户和卖家的状态
	 * 1.用户状态:unPay未付款，pay已付款，waiting等待中，
	 *   accepted已收货，unAccess待评价，accessed已评价
	 * 2.卖家状态：waiting等待付款，accepted已接单，success交易成功
	 */
	public boolean updateParam(SaleOrder order);
	
	
	
	//单纯用户订单
	public ResultPage getUserOrder(String userId, Page page);
	//获得用户订单的同时，支持关键字，排序，区间筛选
	public ResultPage getUserOrder(String userId, String key,   String region, 
                                   String left,   String right, Page page);
    //单纯商家订单
	public ResultPage getSellerOrder(String sellerId, Page page);
	//获得商家订单的同时，支持关键字，排序，区间筛选
	public ResultPage getSellerOrder(String sellerId, String key,   String region,
                                     String left,     String right, Page page);
    //管理员查询
    public ResultPage getOrderListByAll(String key,  String region, 
                                        String left, String right, Page page);
    
    //事务性
    //public boolean swapMoney(String userId, String sellerId, double money);
}
