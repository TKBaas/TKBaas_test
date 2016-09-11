package com.tk.baas.dao;

import java.util.List;

import com.tk.baas.model.Cart;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;

public interface ProductDao {

	//商家添加商品
	public boolean addProduct(String sellerId, Product product);
	//增加商品销量
	public boolean addSales(String productId, int sale);
	
	
	//商家更新商品
	public boolean updateBySeller(Product product);
	//管理员更新商品
	public boolean updateByAdmin(Product product);
	//更新商品图片,!!传入大图片的旧URL即可，以及大图片的新URL，小图片的新URL！！图片要service自己保存和删除
	public boolean updatePicture(String productId, String oldPicName,
			                     String newPicName,String newTinyPicName);
	//更新商品详情图片,!!传入大图片的旧URL即可，以及大图片的新URL，小图片的新URL！！图片要service自己保存和删除
	public boolean updateDetailPicture(String productId, String oldPicName, 
			                           String newPicName);

	
	
	//获得商品详情
	public Product getProduct(String productId);
	//商家获得自己的商品
	public ResultPage getSellerProduct(String sellerId, Page page);
	//商品排序，区间筛选，分页
	public ResultPage getProductListByAll(String key, String sort, String region, 
				                             String left, String right, Page page);
	
	
	//商品下架，可由商家，管理员调用
	public boolean deleteProduct(String productId);
	//批量下架
	public boolean deleteProducts(String[] productsId);
}
