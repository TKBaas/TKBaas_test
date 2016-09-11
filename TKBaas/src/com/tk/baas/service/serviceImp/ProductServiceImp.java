package com.tk.baas.service.serviceImp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tk.baas.dao.ProductDao;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.service.ProductService;

@Component("ProductService")
public class ProductServiceImp implements ProductService{

	@Resource(name="ProductDao")
	private ProductDao productDao;
	
	@Override
	public boolean addProduct(String sellerId, Product product) {
		
		return productDao.addProduct(sellerId, product);
	}

	@Override
	public boolean deleteProduct(String productId) {
		boolean deleteProduct = productDao.deleteProduct(productId);
		return deleteProduct;
	}

	@Override
	public boolean deleteProduct(String[] productId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean updateProduct(Product product) {
		boolean updateProduct =productDao.updateBySeller(product);
		return updateProduct;
	}

	@Override
	public Product getProduct(String productId) {
		Product product = productDao.getProduct(productId);
		return product;
	}

	@Override
	public ResultPage getSellerProduct(String sellerId, 
			                              String currentPage,
			                              String pageSize) {
		String tc = "1";
		String ts = "20";
		if(currentPage != null || !currentPage.equals(""))tc = currentPage;
		if(pageSize != null || !pageSize.equals(""))ts = pageSize;
		
		Page page = new Page();
		page.setCurrentPage(Integer.parseInt(tc));
		page.setPageSize(Integer.parseInt(ts));
		ResultPage resultPage= productDao.getSellerProduct(sellerId, page);
		return resultPage;
	}


	@Override
	public ResultPage getProductListByAll(String key, String sort,
			String region, String left, String right, Page page) {
		if(page.getCurrentPage() <= 0) {
			page.setCurrentPage(1);
		}
		if(page.getPageSize() <= 0) {
			page.setPageSize(20);
		}

		ResultPage resultPage = productDao.getProductListByAll(key, sort, region, left, right, page);
		return resultPage;
	}

	@Override
	public boolean updatePicture(String productId, String oldPicName,
			String newPicName, String newTinyPicName) {
		boolean result = productDao.updatePicture(productId, oldPicName, newPicName, newTinyPicName);
		return result;
	}

	@Override
	public boolean updateDetailPicture(String prooductId, String oldPicture,
			String newPicture) {
		boolean result = productDao.updateDetailPicture(prooductId, oldPicture, newPicture);
		return result;
	}


}
