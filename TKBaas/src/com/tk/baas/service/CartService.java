package com.tk.baas.service;

import com.tk.baas.model.Cart;

/**
 * 购物车业务逻辑类
 * @author Administrator
 */
public interface CartService {
	//用户添加进购物车
	public boolean addIncart(String userId, String productId, int num);
	
    //获得用户购物车
	public Cart getUserProduct(String userId);
    
	//用户从购物车删除
	public boolean delIncart(String userId, String[] productId);
    
	//更新购物车中商品的数目,默认productId与num对应
	public boolean updateCart(String userId, String[] productId, int[] num);
}
