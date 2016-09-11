package com.tk.baas.controller.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerItem;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.Picture;
import com.tk.baas.service.CartService;
import com.tk.baas.util.IOUtil;
import com.tk.baas.util.IPUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 购物车的controller，安卓版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/cart/app")
public class CartControllerA {
	
	@Resource(name="CartService")
	private CartService cartService;
	
	/**
	 * 用户将商品加入购物车（用户id，商品id，数量）
	 **************服务器接接收：JSONObject****************************
	 * userId		用户id（string）
	 * productId	商品id（string）
	 * num			商品数量
	 **************服务器返回：JSONObject**************************** 
	 * result		加入商品是否成功（boolean）
	 */
	@RequestMapping("/addInCart")
	public void addInCart(HttpServletRequest request, HttpServletResponse response)throws Exception {
		//获取数据
		String str = IOUtil.readString(request.getInputStream());
		JSONObject json = JSONObject.fromObject(str);
		String usreId = (String) json.get("userId");
		String productId = (String) json.get("productId");
		String num = (String) json.get("num");
		
		//加入购物车
		boolean result = cartService.addIncart(usreId, productId, new Integer(num));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", result);
		response.getOutputStream().write(jsonObj.toString().getBytes());;
		return ;
	}
	
	/**
	 * user 查看购物车(用户id)
	 */
	@RequestMapping("/getUserProduct")
	public void getUserProduct(HttpServletRequest request, HttpServletResponse response)throws IOException{
		String str = IOUtil.readString(request.getInputStream());
		JSONObject json = JSONObject.fromObject(str);
		String userId = (String) json.get("userId");
		
		Cart cart = cartService.getUserProduct(userId);
		
		//将购物车封装进jsonObj
		JSONObject cartJson = new JSONObject();
		JSONArray sellerItem = new JSONArray();
		for(CartSellerItem cartItem : cart.getSellerItem()){
			JSONObject cartSellerJson = new JSONObject();
			cartSellerJson.put("id", cartItem.getId());
			cartSellerJson.put("sellerId", cartItem.getSellerId());
			cartSellerJson.put("shopName", cartItem.getShopName());
			JSONArray proItemArray = new JSONArray();
			for(CartSellerProItem proItem : cartItem.getProItem()){
				JSONObject proItemJson = new JSONObject();
				proItemJson.put("id", proItem.getId());
				proItemJson.put("num", proItem.getNum());
				JSONObject proJson = new JSONObject();
				proJson.put("id", proItem.getProduct().getId());
				proJson.put("name", proItem.getProduct().getName());
				proJson.put("type", proItem.getProduct().getType());
				proJson.put("city", proItem.getProduct().getCity());
				proJson.put("store", proItem.getProduct().getStore());
				proJson.put("price", proItem.getProduct().getPrice());
				proJson.put("description", proItem.getProduct().getDescription());
				proJson.put("sales", proItem.getProduct().getSales());
				proJson.put("ifDeleted", proItem.getProduct().isIfDeleted());
				
				//设置图片url
				Set<Picture> pictureSet = proItem.getProduct().getPicture();
				String pictureUrl = null;
				for(Picture picture : pictureSet){
					pictureUrl = picture.getPictureUrl();
					break;
				}
				proJson.put("picture", IPUtil.IMGSURL+ pictureUrl);
				
				proItemJson.put("product", proJson);
				proItemArray.add(proItemJson);
			}
			cartSellerJson.put("proItem", proItemArray);
			
			sellerItem.add(cartSellerJson);
		}
		
		cartJson.put("id", cart.getId());
		cartJson.put("sellerItem", sellerItem);
		
		response.getOutputStream().write(cartJson.toString().getBytes());
		return ;
	}

	
	/**
	 * 删除购物车delIncart(需要商品id和userId)
	 * @param model
	 * @param request
	 * @param productId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delIncart")
	public void delIncart(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String str = IOUtil.readString(request.getInputStream());
		JSONObject json = JSONObject.fromObject(str);
		String userId = (String) json.get("userId");
		JSONArray productIdArr = json.getJSONArray("productId");
		String[] productId = new String[productIdArr.size()];
		//将jsonarray的数据封装到String[]
		for(int i=0; i<productIdArr.size(); i++){
			productId[i] = productIdArr.getString(i);
		}
		boolean result = cartService.delIncart(userId, productId);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", result);
		response.getOutputStream().write(jsonObj.toString().getBytes());;
		return ;
	}
	
	/**
	 * 修改购物车数量(需要productId[] and num[] and userId)
	 * @param request
	 * @param productId
	 * @param num
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateCart")
	public void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String str = IOUtil.readString(request.getInputStream());
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json.toString());
		String userId = json.getString("userId");
		JSONArray productIdArr =json.getJSONArray("productId");
		JSONArray numArr = json.getJSONArray("num");
		
		//将productId的jsonarray的数据封装到String[]
		String[] productId = new String[productIdArr.size()];
		for(int i=0; i<productIdArr.size(); i++){
			productId[i] = productIdArr.getString(i);
		}
		
		//将num的jsonarray的数据封装到String[]
		int[] num = new int[numArr.size()];
		for(int i=0; i<numArr.size(); i++){
			num[i] = numArr.getInt(i);
		}
		
		boolean result = cartService.updateCart(userId, productId, num);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", result);
		response.getOutputStream().write(jsonObj.toString().getBytes());;
		return ;
	}
}
