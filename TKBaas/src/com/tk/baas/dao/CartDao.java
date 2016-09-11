package com.tk.baas.dao;

import java.util.List;

import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.ResultPage;

public interface CartDao {
	
	//用户添加进购物车
	public boolean addIncart(String userId, String productId, int num);
    //获得用户购物车
	public Cart getUserProduct(String userId);
	//用于订单
	public CartSellerProItem getCartSellerProItem(String proItemId);
	
    //用户从购物车删除
	public boolean delIncart(String userId, String[] productId);
    //更新购物车中商品的数目,默认productId与num对应
	public boolean updateCart(String userId, String[] productId, int[] num);
}
