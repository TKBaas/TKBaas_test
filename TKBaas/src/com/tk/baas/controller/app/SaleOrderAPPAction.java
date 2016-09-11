package com.tk.baas.controller.app;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.fabric.Response;
import com.tk.baas.model.Address;
import com.tk.baas.model.Admin;
import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerItem;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.OrderSearchForm;
import com.tk.baas.model.OrderSubmitForm;
import com.tk.baas.model.Page;
import com.tk.baas.model.Picture;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;
import com.tk.baas.service.AddressService;
import com.tk.baas.service.CartService;
import com.tk.baas.service.OrderService;
import com.tk.baas.util.IOUtil;
import com.tk.baas.util.IPUtil;

@Controller
@RequestMapping("/order/app")
public class SaleOrderAPPAction {
     
	//wwww
	@Resource(name="CartService")
	private CartService cartService;
	
	@Resource(name="OrderService")
	private OrderService orderService;
	
	@Resource(name="AddressService")
	private AddressService addressService;
	
	private IOUtil jsonUtil = new IOUtil();
	
	@RequestMapping("/fillOrder")
	public void add(HttpServletRequest request, 
     		        HttpServletResponse response, Model model){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			JSONArray jsonArray = jsonObject.getJSONArray("proItems");
			String userId = (String)jsonObject.get("userId");
			Cart userCart = cartService.getUserProduct(userId);
			
			
			String[] proItemIds = new String[jsonArray.size()+2];
			
			for(int i = 0; i < jsonArray.size(); i++){
				JSONObject json = jsonArray.getJSONObject(i);
				proItemIds[i] = json.getString("proItemId");
			}
			Cart cart = orderService.getBuyList(proItemIds, userCart);   		
			
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
					
					//放置图片(将图片转为字符串)
					Set<Picture> pictureSet = proItem.getProduct().getPicture();
					String pictureUrl = null;
					for(Picture picture : pictureSet){
						pictureUrl = picture.getPictureUrl();
						break;
					}
					
					proJson.put("picture", pictureUrl);
					proItemJson.put("product", proJson);
					proItemArray.add(proItemJson);
				}
				cartSellerJson.put("proItem", proItemArray);
				sellerItem.add(cartSellerJson);
			}
			cartJson.put("id", cart.getId());
			cartJson.put("sellerItem", sellerItem);
            
			//addressJson
			Page page = new Page();
			page.setCurrentPage(1);
			page.setPageSize(20);
			ResultPage resultAddress = addressService.getAddress(userId, "", page);
			
			List<Address> addList = (List<Address>) resultAddress.getResult();
			JSONArray addressArray = new JSONArray();
			for(int i=0; i<addList.size(); i++){
				Address address = addList.get(i);
				JSONObject jsonAddress = new JSONObject();
				jsonAddress.put("id", address.getId());
				jsonAddress.put("receiver", address.getReceiver());
				jsonAddress.put("phone", address.getPhone());
				jsonAddress.put("province", address.getProvince());
				jsonAddress.put("city", address.getCity());
				jsonAddress.put("countyTown", address.getCountyTown());
				jsonAddress.put("street", address.getStreet());
				jsonAddress.put("detailsAddress", address.getDetailsAddress());
				jsonAddress.put("defaultAddress", address.isDefaultAddress());
				addressArray.add(jsonAddress);
			}
			
			JSONObject resultJson = new JSONObject();
			resultJson.put("cartJson", cartJson);
			resultJson.put("addressJson", addressArray);
			
			
			System.out.println("--->" + resultJson.toString());
			
			jsonUtil.outPut(response, resultJson.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/submitOrder")
	public void submit(HttpServletRequest request, HttpServletResponse response){
        try{
		JSONObject jsonObject = JSONObject.fromObject(
				jsonUtil.readString(request.getInputStream()));
		String userId = jsonObject.getString("userId");
		String addressId = jsonObject.getString("addressId");
		String money     = jsonObject.getString("totalMoney");
		JSONArray proIds = jsonObject.getJSONArray("proIds");
		JSONArray proItemIds = jsonObject.getJSONArray("proItemIds");
		String[] idPros      = new String[proIds.size()];   
		String[] idProItems  = new String[proIds.size()];
		
		System.out.println(jsonObject.toString());
		for(int i = 0; i < proIds.size(); i++){
			JSONObject proId     = proIds.getJSONObject(i);
			JSONObject proItemId = proItemIds.getJSONObject(i);
			idPros[i]     = proId.getString("proId");
			idProItems[i] = proItemId.getString("proItemId");
		}
		OrderSubmitForm orderSubmitForm = new OrderSubmitForm();
		orderSubmitForm.setAddressId(addressId);
		orderSubmitForm.setTotalMoney(Double.parseDouble(money));
		orderSubmitForm.setProIds(idPros);
		orderSubmitForm.setProItemIds(idProItems);
		
		boolean flag = orderService.submitOrder(userId, orderSubmitForm);
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", flag);
		jsonUtil.outPut(response, resultJson.toString());
        System.out.println(resultJson.toString());
        
        }catch(Exception e){
        	e.printStackTrace();
        }	
    }
	
	@RequestMapping("/userOrder")
	public void getUserOrder(HttpServletRequest request,
			                 HttpServletResponse response){
		
		try{
		JSONObject jsonObject = JSONObject.fromObject(
				jsonUtil.readString(request.getInputStream()));
		String userId = jsonObject.getString("userId");
		String currentPage  = jsonObject.getString("currentPage");
		String pageSize     = jsonObject.getString("pageSize");
		
		ResultPage resultPage = orderService.getUserOrder(
				                userId, currentPage, pageSize);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("currentPage", resultPage.getCurrentPage());
		resultJson.put("pageSize", resultPage.getPageSize());
		resultJson.put("totalNum", resultPage.getTotalNum());
		resultJson.put("totalSize", resultPage.getPageSize());
		
		JSONArray orderArray = new JSONArray();
		List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
		for(SaleOrder saleOrder : list){
			JSONObject orderJson = new JSONObject();
			orderJson.put("boughtDate", saleOrder.getBoughtDate());
			orderJson.put("id", saleOrder.getId());
			orderJson.put("money", saleOrder.getMoney());
			orderJson.put("sellerId", saleOrder.getSellerId());
			orderJson.put("shopName", saleOrder.getShopName());
			orderJson.put("userId", saleOrder.getUserId());
			orderJson.put("state", saleOrder.getState());
			
			JSONObject jsonAddress = new JSONObject();
			Address address = saleOrder.getAddress();
			jsonAddress.put("id", address.getId());
			jsonAddress.put("receiver", address.getReceiver());
			jsonAddress.put("phone", address.getPhone());
			jsonAddress.put("province", address.getProvince());
			jsonAddress.put("city", address.getCity());
			jsonAddress.put("countyTown", address.getCountyTown());
			jsonAddress.put("street", address.getStreet());
			jsonAddress.put("detailsAddress", address.getDetailsAddress());
			jsonAddress.put("defaultAddress", address.isDefaultAddress());
			orderJson.put("address", jsonAddress);
			
			JSONArray proItemsJson = new JSONArray();
			System.out.println("--->size--->" + saleOrder.getProItems().size());
			for(CartSellerProItem proItem : saleOrder.getProItems()){
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
				
				for(Picture pic : proItem.getProduct().getPicture()){
				    proJson.put("picture", IPUtil.IMGSURL + pic.getPictureUrl());
				    break;
				}
				    
				proItemJson.put("product", proJson);
				proItemsJson.add(proItemJson);
			}
			orderJson.put("proItems", proItemsJson);
			orderArray.add(orderJson);
		}
		resultJson.put("result", orderArray);
		System.out.println(orderArray.toString());
		response.getOutputStream().write(resultJson.toString().getBytes("utf-8"));
		
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	@RequestMapping("/searchUserOrder")
	public void getSearchUserOrder(HttpServletRequest request,
                                   HttpServletResponse response){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String userId = jsonObject.getString("userId");
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			String state    = jsonObject.getString("state");
//			String region = jsonObject.getString("region");
//			String left   = jsonObject.getString("left");
//			String right  = jsonObject.getString("right");
			
			OrderSearchForm orderSearchForm = new OrderSearchForm();
			orderSearchForm.setKey(state);
			orderSearchForm.setRegion("");
			orderSearchForm.setLeft("");
			orderSearchForm.setRight("");
			if(currentPage!= null && !currentPage.equals(""))
				orderSearchForm.setCurrentPage(Integer.parseInt(currentPage));
		    if(pageSize != null && !pageSize.equals(""))
				orderSearchForm.setPageSize(Integer.parseInt(pageSize));
			
			ResultPage resultPage = orderService.getSearchUserOrder(
                                    userId, orderSearchForm);
			
			JSONObject resultJson = new JSONObject();
			resultJson.put("currentPage", resultPage.getCurrentPage());
			resultJson.put("pageSize", resultPage.getPageSize());
			resultJson.put("totalNum", resultPage.getTotalNum());
			resultJson.put("totalSize", resultPage.getPageSize());
			
			JSONArray orderArray = new JSONArray();
			List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
			
			System.out.println("-->size" + list.size() + "--->json" + jsonObject.toString());
			
			for(SaleOrder saleOrder : list){
				JSONObject orderJson = new JSONObject();
				orderJson.put("boughtDate", saleOrder.getBoughtDate());
				orderJson.put("id", saleOrder.getId());
				orderJson.put("money", saleOrder.getMoney());
				orderJson.put("sellerId", saleOrder.getSellerId());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				orderJson.put("state", saleOrder.getState());
				
				JSONArray proItemsJson = new JSONArray();
				for(CartSellerProItem proItem : saleOrder.getProItems()){
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
					
					for(Picture pic : proItem.getProduct().getPicture()){
					    proJson.put("picture", IPUtil.IMGSURL + pic.getPictureUrl());
					    break;
					}
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			jsonUtil.outPut(response, resultJson.toString());
			
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/sellerOrder")
	public void getSellerOrder(HttpServletRequest request,
			                    HttpServletResponse response){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String sellerId = jsonObject.getString("sellerId");
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			
			ResultPage resultPage = orderService.getSellerOrder(
                                    sellerId, currentPage, pageSize);
			
			JSONObject resultJson = new JSONObject();
			resultJson.put("currentPage", resultPage.getCurrentPage());
			resultJson.put("pageSize", resultPage.getPageSize());
			resultJson.put("totalNum", resultPage.getTotalNum());
			resultJson.put("totalSize", resultPage.getPageSize());
			
			JSONArray orderArray = new JSONArray();
			List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
			for(SaleOrder saleOrder : list){
				JSONObject orderJson = new JSONObject();
				orderJson.put("boughtDate", saleOrder.getBoughtDate());
				orderJson.put("id", saleOrder.getId());
				orderJson.put("money", saleOrder.getMoney());
				orderJson.put("sellerId", saleOrder.getSellerId());
//				orderJson.put("sellerState", saleOrder.getSellerState());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				orderJson.put("state", saleOrder.getState());
				
				JSONArray proItemsJson = new JSONArray();
				for(CartSellerProItem proItem : saleOrder.getProItems()){
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
					
					for(Picture pic : proItem.getProduct().getPicture()){
					    proJson.put("picture", IPUtil.IMGSURL + pic.getPictureUrl());
					    break;
					}
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			jsonUtil.outPut(response, resultJson.toString());
			
			}catch(Exception e){
				e.printStackTrace();
			}	
	}
	
	@RequestMapping("/searchSellerOrder")
	public void getSearchSellerOrder(HttpServletRequest request,
                                       HttpServletResponse response){
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String sellerId = jsonObject.getString("sellerId");
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			String state    = jsonObject.getString("state");
//			String region = jsonObject.getString("");
//			String left   = jsonObject.getString("left");
//			String right  = jsonObject.getString("right");
			
			OrderSearchForm orderSearchForm = new OrderSearchForm();
			orderSearchForm.setKey(state);
			orderSearchForm.setRegion("");
			orderSearchForm.setLeft("");
			orderSearchForm.setRight("");
			if(currentPage!= null && !currentPage.equals(""))
				orderSearchForm.setCurrentPage(Integer.parseInt(currentPage));
		    if(pageSize != null && !pageSize.equals(""))
				orderSearchForm.setPageSize(Integer.parseInt(pageSize));
			
			ResultPage resultPage = orderService.getSearchUserOrder(
                                    sellerId, orderSearchForm);
			
			JSONObject resultJson = new JSONObject();
			resultJson.put("currentPage", resultPage.getCurrentPage());
			resultJson.put("pageSize", resultPage.getPageSize());
			resultJson.put("totalNum", resultPage.getTotalNum());
			resultJson.put("totalSize", resultPage.getPageSize());
			
			JSONArray orderArray = new JSONArray();
			List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
			for(SaleOrder saleOrder : list){
				JSONObject orderJson = new JSONObject();
				orderJson.put("boughtDate", saleOrder.getBoughtDate());
				orderJson.put("id", saleOrder.getId());
				orderJson.put("money", saleOrder.getMoney());
				orderJson.put("sellerId", saleOrder.getSellerId());
//				orderJson.put("sellerState", saleOrder.getSellerState());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				orderJson.put("state", saleOrder.getState());
				
				JSONArray proItemsJson = new JSONArray();
				for(CartSellerProItem proItem : saleOrder.getProItems()){
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

					for(Picture pic : proItem.getProduct().getPicture()){
					    proJson.put("picture", IPUtil.IMGSURL + pic.getPictureUrl());
					    break;
					}
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			jsonUtil.outPut(response, resultJson.toString());
			
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/searchAllOrder")
	public void getSearchAllOrder(HttpServletRequest request,
			                        HttpServletResponse response){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			String key    = jsonObject.getString("key");
			String region = jsonObject.getString("region");
			String left   = jsonObject.getString("left");
			String right  = jsonObject.getString("right");
			
			OrderSearchForm orderSearchForm = new OrderSearchForm();
			orderSearchForm.setCurrentPage(Integer.parseInt(currentPage));
			orderSearchForm.setPageSize(Integer.parseInt(pageSize));
			orderSearchForm.setKey(key);
			orderSearchForm.setRegion(region);
			orderSearchForm.setLeft(left);
			orderSearchForm.setRight(right);
			
			ResultPage resultPage = orderService.getSearchAllOrder(orderSearchForm);
			
			JSONObject resultJson = new JSONObject();
			resultJson.put("currentPage", resultPage.getCurrentPage());
			resultJson.put("pageSize", resultPage.getPageSize());
			resultJson.put("totalNum", resultPage.getTotalNum());
			resultJson.put("totalSize", resultPage.getPageSize());
			
			JSONArray orderArray = new JSONArray();
			List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
			for(SaleOrder saleOrder : list){
				JSONObject orderJson = new JSONObject();
				orderJson.put("boughtDate", saleOrder.getBoughtDate());
				orderJson.put("id", saleOrder.getId());
				orderJson.put("money", saleOrder.getMoney());
				orderJson.put("sellerId", saleOrder.getSellerId());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				orderJson.put("state", saleOrder.getState());
				
				JSONArray proItemsJson = new JSONArray();
				for(CartSellerProItem proItem : saleOrder.getProItems()){
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

					for(Picture pic : proItem.getProduct().getPicture()){
					    proJson.put("picture", IPUtil.IMGSURL + pic.getPictureUrl());
					    break;
					}
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			jsonUtil.outPut(response, resultJson.toString());
			
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/deleteUserOrder")
	public void deleteUserOrders(HttpServletRequest request,
			                     HttpServletResponse response){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String userId = jsonObject.getString("userId");
			JSONArray orderIds = jsonObject.getJSONArray("orderIds");  
			String[] idOrders  = new String[orderIds.size()];
			
			for(int i = 0; i < orderIds.size(); i++){
				JSONObject orderId = orderIds.getJSONObject(i);
				idOrders[i]        = orderId.getString("orderId");
			}
			
			boolean flag = orderService.deleteUserOrders(userId, idOrders);
			JSONObject resultJson = new JSONObject();
			resultJson.put("reslut", flag);
			jsonUtil.outPut(response, resultJson.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteSellerOrder")
	public void deleteSellerOrders(HttpServletRequest request,
                                   HttpServletResponse response){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String sellerId = jsonObject.getString("sellerId");
			JSONArray orderIds = jsonObject.getJSONArray("orderIds");  
			String[] idOrders  = new String[orderIds.size()];
			
			for(int i = 0; i < orderIds.size(); i++){
				JSONObject orderId = orderIds.getJSONObject(i);
				idOrders[i]        = orderId.getString("orderId");
			}
			
			boolean flag = orderService.deleteSellerOrders(sellerId, idOrders);
			JSONObject resultJson = new JSONObject();
			resultJson.put("reslut", flag);
			jsonUtil.outPut(response, resultJson.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/updateOrderAdderss")
	public void updateOrderAddress(HttpServletRequest request,
                                   HttpServletResponse response){
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
			String sellerId = jsonObject.getString("sellerId");
			String orderId = jsonObject.getString("orderId");
            JSONObject json = jsonObject.getJSONObject("address");
			
			Address address = new Address();	//新建json对象
			address.setCity(json.getString("city"));
			address.setPhone(json.getString("phone"));
			address.setStreet(json.getString("street"));
			address.setReceiver(json.getString("receiver"));
			address.setProvince(json.getString("province"));
			address.setCountyTown(json.getString("countyTown"));
			address.setDetailsAddress(json.getString("detailsAddress"));
			address.setDefaultAddress(json.getBoolean("defaultAddress"));

			
			boolean flag = orderService.updateOrderAddress(orderId, address);
			JSONObject resultJson = new JSONObject();
			resultJson.put("reslut", flag);
			jsonUtil.outPut(response, resultJson.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * /order/app/updateOrderParam
	 * 
	 * request: String id    -订单id
	 *          String state -订单需要修改的状态
	 *          （默认付款了，所以没有unpaid, 只有unsent,unreceived,uncomment）
	 *          
	 * response: boolean result -修改结果        
	 */
	@RequestMapping("/updateOrderParam")
	public void updateOrderParam(HttpServletRequest request,
                                   HttpServletResponse response){
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(
					jsonUtil.readString(request.getInputStream()));
            JSONObject orderJson = jsonObject.getJSONObject("order");

            SaleOrder order = new SaleOrder();
            order.setState(orderJson.getString("state"));
			order.setId(orderJson.getString("id"));
            
            boolean flag = orderService.updateOrderParam(order);
			JSONObject resultJson = new JSONObject();
			resultJson.put("reslut", flag);
			jsonUtil.outPut(response, resultJson.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
