package com.tk.baas.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import com.tk.baas.model.Comment;
import com.tk.baas.model.Picture;
import com.tk.baas.model.ProDetailPicture;
import com.tk.baas.model.Product;
import com.tk.baas.model.Seller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtil {
	public static JSONArray getProductJSONArray(List<Product> productList, String path) {
		JSONArray jsonArray = new JSONArray();
		JSONObject productInfo = null;
		
		for(Product product : productList) {
			
			productInfo = JSONUtil.getSimpleJSON(product,path);
			
			jsonArray.add(productInfo);
		}
		return jsonArray;
	}
	
	public static JSONObject getSimpleJSON(Product product, String path) {
		
		JSONObject productInfo = new JSONObject();
		Set<Picture> pictureSet = product.getPicture();
		String picture_url = path;
		int i = 0;
		for(Picture picture : pictureSet) {
			if(i==0) {
				picture_url += picture.getPictureUrl();
			}
			i++;
		}
		
		productInfo.put("product_id", product.getId());
		productInfo.put("product_name", product.getName());
		productInfo.put("product_city", product.getCity());
		productInfo.put("product_type", product.getType());
		productInfo.put("product_description", product.getDescription());
		productInfo.put("product_price", product.getPrice());
		productInfo.put("product_sales", product.getSales());
		productInfo.put("product_store", product.getStore());
		productInfo.put("picture_url", picture_url);
		
		
		return productInfo;
	}
	
	
	/**
	 * 商品详情（评论，商家）
	 * @param product
	 * @param path
	 * @return
	 */
	public static JSONObject getProductJSON(Product product, String path) {
		
		JSONObject productInfo = new JSONObject();
		Set<Picture> pictureSet = product.getPicture();
		Set<ProDetailPicture> detailPictureSet = product.getDetailPicture();
		String[] pictures = new String[pictureSet.size()];
		String[] tinyPictures = new String[pictureSet.size()];
		String[] detailPictures = new String[detailPictureSet.size()];
		JSONObject picture_url = null;//每个picture的JSonObject
		JSONObject detail_picture_url = null;//每个proDetailPicture的JSonObject
		JSONArray pictures_url = new JSONArray();//每张图片的JSonObject都添加到JSONArray
		JSONArray detail_pictures_url = new JSONArray();
		int i = 0;
		for(Picture picture : pictureSet) {
			picture_url = new JSONObject();
			pictures[i] = path + picture.getPictureUrl();
			tinyPictures[i] = path + picture.getTinyPictureUrl();
			picture_url.put("picture_url", pictures[i]);
			picture_url.put("tiny_picture_url", tinyPictures[i]);
			pictures_url.add(picture_url);
			i ++;
		}
		int j = 0;
		for(ProDetailPicture detailPicture : detailPictureSet) {
			detail_picture_url = new JSONObject();
			
			detailPictures[j] = path + detailPicture.getDetailPicUrl();
				
			detail_picture_url.put("detail_picture_url", detailPictures[j]);
			detail_pictures_url.add(detail_picture_url);
			
			j ++;
		}
		
		JSONObject sellerJsonObj = new JSONObject();//商家的json对象
		Seller seller = product.getSeller();
		sellerJsonObj.put("id", seller.getId());
		sellerJsonObj.put("name", seller.getName());
		sellerJsonObj.put("shopName", seller.getShopName());
		sellerJsonObj.put("shopPicture", path + seller.getShopPicture());
		sellerJsonObj.put("shopDescription", seller.getShopDescription());
		sellerJsonObj.put("grade", seller.getGrade());
		sellerJsonObj.put("phone", seller.getPhone());
		sellerJsonObj.put("sales", seller.getSales());
		
		
		Set<Comment> commentSet = product.getComment();//获得1条评论
		JSONObject commentJsonObj = new JSONObject();
		if(commentSet.size() < 1){//
			commentJsonObj.put("id", "");
		}else{
			//Comment comment = commentSet.
			for(Comment tem : commentSet){
				commentJsonObj.put("id", tem.getId());
				commentJsonObj.put("grade", tem.getGrade());
				commentJsonObj.put("comment", tem.getComment());
				commentJsonObj.put("username", tem.getUsername());
				commentJsonObj.put("proName", tem.getProName());
				commentJsonObj.put("userPicture", path + tem.getUserPicture());
				commentJsonObj.put("userGrade", tem.getUserGrade());
				
				commentJsonObj.put("date", new SimpleDateFormat("yyyy-MM-dd").format(tem.getDate()));
				commentJsonObj.put("access", tem.isAccess());
				break;
			}
		}
		
		productInfo.put("product_id", product.getId());
		productInfo.put("product_name", product.getName());
		productInfo.put("product_city", product.getCity());
		productInfo.put("product_type", product.getType());
		productInfo.put("product_description", product.getDescription());
		productInfo.put("product_price", product.getPrice());
		productInfo.put("product_sales", product.getSales());
		productInfo.put("product_store", product.getStore());
		productInfo.put("pictures_url", pictures_url);
		productInfo.put("detail_pictures_url", detail_pictures_url);
		productInfo.put("product_seller", sellerJsonObj);
		productInfo.put("product_comment", commentJsonObj);
		
		return productInfo;
	}
}
