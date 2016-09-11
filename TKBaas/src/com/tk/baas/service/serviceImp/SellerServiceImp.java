package com.tk.baas.service.serviceImp;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tk.baas.dao.SellerDao;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.service.SellerService;

@Component("SellerService")
public class SellerServiceImp implements SellerService{

	@Resource(name="SellerDao")
	private SellerDao sellerDao;//调用dao
	
	/**
	 * 商家注册（添加商家）
	 */
	@Override
	public boolean addSeller(Seller seller) {
		//先判断手机号是否已经注册了
		Seller result = sellerDao.getSeller(seller.getPhone());
		boolean addSeller;
		if(result == null){//用户可以注册
			addSeller = sellerDao.addSeller(seller);
		}else{//号码已经被注册
			addSeller = false;
		}
		return addSeller;
	}
	
	/**
	 * 商家登录
	 */
	@Override
	public Seller login(Seller seller) {
		Seller result = sellerDao.getSeller(seller.getPhone());
		if(result==null || !result.getPassword().equals(seller.getPassword())){//没有这个商家或密码不匹配
			return null;
		}else{
			return result;
		}
	}
	
	/**
	 * 通过id查找商家信息
	 */
	@Override
	public Seller getSellerOne(String id) {
		Seller seller = sellerDao.getSellerDetail(id);
		return seller;
	}
		
	/**
	 * 通过id删除上商家信息
	 */
	@Override
	public boolean deleteSeller(String[] sellerId) {
		boolean deleteSeller = sellerDao.deleteSeller(sellerId);
		return deleteSeller;
	}

	
	@Override
	public boolean updateSeller(String role,Seller seller) {//更新商家信息
		boolean updateSeller = false;
		if("admin".equals(role)){
			updateSeller = sellerDao.updateByAdmin(seller);
		}else if("seller".equals(role)){
			updateSeller = sellerDao.updateBySeller(seller);
		}
		return updateSeller;
	}
	
	@Override
	public ResultPage getSellersForUser(String key, String sort, Page page){//为用户查询
		return sellerDao.getSellersForUser(key, sort, page);
	}
	
    //用于管理员查询商家，提供了关键字查询，销量、信用等排序及区间筛选
	@Override
  	public ResultPage getSellersForAdmin(String key, String sort, String region, 
  			                               String left, String right, Page page){
  		return sellerDao.getSellersForAdmin(key, sort, region, left, right, page);
  	}
}
