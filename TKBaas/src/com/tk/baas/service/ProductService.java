package com.tk.baas.service;

import java.util.List;

import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;

public interface ProductService {
	
	public Product getProduct(String productId) ;
	
	public boolean addProduct(String sellerId,Product product) ;
	
	public boolean deleteProduct(String productId) ;
	
	public boolean deleteProduct(String[] productId) ;
	
	public boolean updateProduct(Product product) ;
	
	public boolean updatePicture(String productId, String oldPicName,
            String newPicName, String newTinyPicName);
	
	public boolean updateDetailPicture(String prooductId, String oldPicture, String newPicture);
	
	public ResultPage getSellerProduct(String sellerId, String currentPage, String pageSize) ;
	
	public ResultPage getProductListByAll(String key, String sort, String region, String left, String right, Page page);
	
}
