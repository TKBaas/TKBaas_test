package com.tk.baas.dao;

import java.util.List;

import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;

/**
 * @author csw82_000
 *
 */
public interface SellerDao {
    
	public boolean addSeller(Seller seller);
	
	
	public boolean deleteSeller(String[] sellerId);
	
	
	//商家修改
	public boolean updateBySeller(Seller seller);
	//管理员修改
	public boolean updateByAdmin(Seller seller);
	//修改商家图片,管理员、商家可登录
	public boolean updatePicture(String sellerId, String picURL);
	//public boolean updateMoney(String userId , double money);
	
	
	//登录，验证
	public Seller getSeller(String phone);
	//展示商家详情
	public Seller getSellerDetail(String sellerId);
	//用于用户查询商家，提供关键字查询，销量、信用等排序
    public ResultPage getSellersForUser(String key, String sort, Page page);
    //用于管理员查询商家，提供了关键字查询，销量、信用等排序及区间筛选
  	public ResultPage getSellersForAdmin(String key, String sort, String region, 
  			                               String left, String right, Page page);
}
