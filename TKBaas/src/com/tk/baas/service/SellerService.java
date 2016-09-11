package com.tk.baas.service;

import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;

public interface SellerService {
	
	public boolean addSeller(Seller seller) ;//注册添加商家
	
	public Seller login(Seller seller);//商家登录
	
	public boolean deleteSeller(String[] sellerId) ;//删除商家
	
	/**
	 * 更新商家信息
	 * @param role "seller""admin"两种修改权限
	 * @param seller 修改的商家
	 * @return
	 */
	public boolean updateSeller(String role,Seller seller);
	
	public Seller getSellerOne(String id) ;//获取商家详细
	
	//用户查询商家列表
	public ResultPage getSellersForUser(String key, String sort, Page page);
	
	//管理员查询商家列表
	public ResultPage getSellersForAdmin(String key, String sort, String region, String left, String right, Page page);
}
