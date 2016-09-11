package com.tk.baas.service.serviceImp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tk.baas.dao.CartDao;
import com.tk.baas.model.Cart;
import com.tk.baas.service.CartService;

@Component("CartService")
public class CartServiceImp implements CartService{
	
	@Resource(name="CartDao")
	private CartDao cartDao;//调用dao

	@Override
	public boolean addIncart(String userId, String productId, int num) {
		// TODO Auto-generated method stub
		return cartDao.addIncart(userId, productId, num);
	}

	@Override
	public Cart getUserProduct(String userId) {
		// TODO Auto-generated method stub
		return cartDao.getUserProduct(userId);
	}

	@Override
	public boolean delIncart(String userId, String[] productId) {
		// TODO Auto-generated method stub
		return cartDao.delIncart(userId, productId);
	}

	@Override
	public boolean updateCart(String userId, String[] productId, int[] num) {
		// TODO Auto-generated method stub
		return cartDao.updateCart(userId, productId, num);
	}
}
