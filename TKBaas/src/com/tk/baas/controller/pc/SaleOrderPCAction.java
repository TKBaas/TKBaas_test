package com.tk.baas.controller.pc;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.tk.baas.model.Address;
import com.tk.baas.model.Admin;
import com.tk.baas.model.Cart;
import com.tk.baas.model.CartSellerItem;
import com.tk.baas.model.CartSellerProItem;
import com.tk.baas.model.OrderSearchForm;
import com.tk.baas.model.OrderSubmitForm;
import com.tk.baas.model.Page;
import com.tk.baas.model.Picture;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;
import com.tk.baas.service.AddressService;
import com.tk.baas.service.CartService;
import com.tk.baas.service.OrderService;
import com.tk.baas.util.IPUtil;

@Controller
@RequestMapping("/order/pc")
public class SaleOrderPCAction {
    //dfsdf
	@Resource(name="CartService")
	private CartService cartService;
	
	@Resource(name="OrderService")
	private OrderService orderService;
	
	@Resource(name="AddressService")
	private AddressService addressService;
	
	@RequestMapping("/fillOrder")
	public String add(HttpServletRequest request, Model model){
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return "redirect:/login/login";
		
		String[] proItemIds = request.getParameterValues("proItemIds");
		
		for(String proItemId : proItemIds)
		System.out.println(proItemId);
		
		Cart cart = orderService.getBuyList(proItemIds, user.getCart());
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(30);
		List<Address> set = (List<Address>)addressService.getAddress(user.getId(), "", page).getResult();
		
		model.addAttribute("listAddress", set);
		model.addAttribute("listProduct", cart);
		model.addAttribute("orderForm", new OrderSubmitForm());
		return "user/submitOrder";
	}
	
	@RequestMapping("/submitOrder")
	public String submit(HttpServletRequest request,
			            @ModelAttribute("OrderForm") OrderSubmitForm orderSubmitForm){
		
		String[] proIds = orderSubmitForm.getProIds();
		String[] proItemIds = orderSubmitForm.getProItemIds();
		for(int i = 0; i < proIds.length; i ++){
			System.out.println(proIds[i]);
			System.out.println(proItemIds[i]);
		}
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return "redirect:/login/login";
		
		String addressId = request.getParameter("addressId");
		orderSubmitForm.setAddressId(addressId);
		boolean flag = orderService.submitOrder(user.getId(), orderSubmitForm);
		return flag == true ? "/user/submitOrderSuccess" : "redirect:/product/pc/home";

	}
	
	@RequestMapping("/userOrder")
	public String userOrder(HttpServletRequest request, Model model){
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return "user/pc/userLogin";
		
		model.addAttribute("userId", user.getId());
		
		String key = request.getParameter("key");
		if(key == null)key = "";
		model.addAttribute("key", key);
		
		return "user/userOrder";
	}
	
	@RequestMapping("/getUserOrder")
	public void getUserOrder(HttpServletRequest request,
			                  HttpServletResponse response){
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return ;
		try{
        request.setCharacterEncoding("utf-8");
		String search_condition = request.getParameter("json");
		JSONObject jsonObject = JSONObject.fromObject(search_condition);
		
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
		resultJson.put("totalPage", resultPage.getTotalPage());
		
		JSONArray orderArray = new JSONArray();
		List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
		for(SaleOrder saleOrder : list){
			JSONObject orderJson = new JSONObject();
			orderJson.put("boughtDate", saleOrder.getBoughtDate());
			orderJson.put("id", saleOrder.getId());
			orderJson.put("money", saleOrder.getMoney());
			orderJson.put("sellerId", saleOrder.getSellerId());
			orderJson.put("state", saleOrder.getState());
			orderJson.put("shopName", saleOrder.getShopName());
			orderJson.put("userId", saleOrder.getUserId());
			
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
				    proJson.put("picture", pic.getPictureUrl());
				    break;
				}
				
				proItemJson.put("product", proJson);
				proItemsJson.add(proItemJson);
			}
			orderJson.put("proItems", proItemsJson);
			orderArray.add(orderJson);
		}
		resultJson.put("result", orderArray);
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(resultJson.toString());
		out.flush();
		out.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/searchUserOrder")
	public String searchUserOrder(HttpServletRequest request,Model model){
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return "redirect:/login/login";
		
		String key = (String)request.getParameter("key");
		model.addAttribute("userId", user.getId());
		model.addAttribute("key", key);
		return "user/userOrder";
	}
	
	@RequestMapping("/getSearchUserOrder")
	public void getSearchUserOrder(HttpServletRequest request,
			                      HttpServletResponse response){
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return ;
		
		try{
	        request.setCharacterEncoding("utf-8");
			String search_condition = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(search_condition);
			String userId = jsonObject.getString("userId");
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			String key    = jsonObject.getString("key");
			String region = jsonObject.getString("region");
			String left   = jsonObject.getString("left");
			String right  = jsonObject.getString("right");
			
			System.out.println("searchOrder-->" + jsonObject.toString());
			
			OrderSearchForm orderSearchForm = new OrderSearchForm();
			orderSearchForm.setKey(key);
			orderSearchForm.setRegion(region);
			orderSearchForm.setLeft(left);
			orderSearchForm.setRight(right);
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
			resultJson.put("totalPage", resultPage.getTotalPage());
			
			JSONArray orderArray = new JSONArray();
			List<SaleOrder> list = (List<SaleOrder>)resultPage.getResult();
			for(SaleOrder saleOrder : list){
				JSONObject orderJson = new JSONObject();
				orderJson.put("boughtDate", saleOrder.getBoughtDate());
				orderJson.put("id", saleOrder.getId());
				orderJson.put("money", saleOrder.getMoney());
				orderJson.put("sellerId", saleOrder.getSellerId());
				orderJson.put("state", saleOrder.getState());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				
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
					    proJson.put("picture", pic.getPictureUrl());
					    break;
					}
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			System.out.println("searchOrder--->" + resultJson.toString());
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.println(resultJson.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/sellerOrder")
	public String sellerOrder(HttpServletRequest request){
		
		Seller seller = (Seller)request.getSession().getAttribute("seller");
		if(seller == null)return "redirect:/login/login";
		return "seller/pc/sellerOrder";
	}
	
	@RequestMapping("/getSellerOrder")
	public void getSellerOrder(HttpServletRequest request,
			                     HttpServletResponse response){
		
		Seller seller = (Seller)request.getSession().getAttribute("seller");
		if(seller == null)return ;
		
		try{
	        request.setCharacterEncoding("utf-8");
			String search_condition = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(search_condition);
			
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
				orderJson.put("state", saleOrder.getState());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				
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
					proJson.put("picture", proItem.getProduct().getPicture());
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.println(resultJson.toString());
			out.flush();
			out.close();

			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/searchSellerOrder")
	public String searchSellerOrder(HttpServletRequest request){
		
		Seller seller = (Seller)request.getSession().getAttribute("seller");
		if(seller == null)return "redirect:/login/login";
	    return "";
	}
	
	@RequestMapping("/getSearchSellerOrder")
	public void getSearchSellerOrder(HttpServletRequest request,
			                        HttpServletResponse response){
		
		Seller seller = (Seller)request.getSession().getAttribute("seller");
		if(seller == null)return ;
		
		try{
	        request.setCharacterEncoding("utf-8");
			String search_condition = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(search_condition);
		
			String sellerId = jsonObject.getString("sellerId");
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			String key    = jsonObject.getString("key");
			String region = jsonObject.getString("region");
			String left   = jsonObject.getString("left");
			String right  = jsonObject.getString("right");
			
			OrderSearchForm orderSearchForm = new OrderSearchForm();
			orderSearchForm.setKey(key);
			orderSearchForm.setRegion(region);
			orderSearchForm.setLeft(left);
			orderSearchForm.setRight(right);
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
				orderJson.put("state", saleOrder.getState());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				
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
					proJson.put("picture", proItem.getProduct().getPicture());
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.println(jsonObject.toString());
			out.flush();
			out.close();
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/searchAllOrder")
	public String searchAllOrder(HttpServletRequest request){
		
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null)return "redirect:/login/login";
		return "";
	}
	@RequestMapping("/getSearchAllOrder")
	public void getSearchAllOrder(HttpServletRequest request,
                                    HttpServletResponse response){
		
		Admin admin = (Admin)request.getSession().getAttribute("admin");
		if(admin == null)return ;
		
		try{
	        request.setCharacterEncoding("utf-8");
			String search_condition = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(search_condition);
		
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			String key    = jsonObject.getString("key");
			String region = jsonObject.getString("region");
			String left   = jsonObject.getString("left");
			String right  = jsonObject.getString("right");
			
			OrderSearchForm orderSearchForm = new OrderSearchForm();
			orderSearchForm.setKey(key);
			orderSearchForm.setRegion(region);
			orderSearchForm.setLeft(left);
			orderSearchForm.setRight(right);
			if(currentPage!= null && !currentPage.equals(""))
				orderSearchForm.setCurrentPage(Integer.parseInt(currentPage));
		    if(pageSize != null && !pageSize.equals(""))
				orderSearchForm.setPageSize(Integer.parseInt(pageSize));
			
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
				orderJson.put("state", saleOrder.getState());
				orderJson.put("shopName", saleOrder.getShopName());
				orderJson.put("userId", saleOrder.getUserId());
				
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
					proJson.put("picture", proItem.getProduct().getPicture());
					
					proItemJson.put("product", proJson);
					proItemsJson.add(proItemJson);
				}
				orderJson.put("proItems", proItemsJson);
				orderArray.add(orderJson);
			}
			resultJson.put("result", orderArray);
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.println(jsonObject.toString());
			out.flush();
			out.close();
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/deleteUserOrder")
	public String deleteUserOrders(HttpServletRequest request){
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null)return "redirect:/login/login";
		
		String[] orderIds = request.getParameterValues("orderIds");
		boolean flag = orderService.deleteUserOrders(user.getId(), orderIds);
		return flag == true ? "/userOrder" : "/falilure";
	}
	
	@RequestMapping("/deleteSellerOrder")
	public String deleteSellerOrders(HttpServletRequest request){
		
		Seller seller = (Seller)request.getSession().getAttribute("seller");
		if(seller == null)return "redirect:/login/login";
		
		String[] orderIds = request.getParameterValues("orderIds");
		boolean flag = orderService.deleteSellerOrders(seller.getId(), orderIds);
		return flag == true ? "/userOrder" : "/falilure";
	}
	
	@RequestMapping("/updateOrderAdderss")
	public String updateOrderAddress(HttpServletRequest request,
			                         @ModelAttribute("Address") Address address){
	     
		Seller seller = (Seller)request.getSession().getAttribute("seller");
		if(seller == null)return "redirect:/login/login";
		
		String orderId = (String)request.getParameter("orderId");
		boolean flag = orderService.updateOrderAddress(orderId, address);
		return flag == true ? "/sellerOrder" : "/falilure";
	}
	
	@RequestMapping("/updateOrderParam")
	public String updateOrderParam(HttpServletRequest request,
			                         @ModelAttribute("saleOrder") SaleOrder saleOrder){
		
		boolean flag = orderService.updateOrderParam(saleOrder);
		return flag == true ? "/sellerOrder" : "/falilure";
	}
	
	
	@RequestMapping("/testSelectBox")
	public String test(Model model){
		
		Product product = new Product();
		product.setId("1");
		product.setName("shoe");
		Product product1 = new Product();
		product1.setId("2");
		product1.setName("pc");
		
		CartSellerProItem proItem = new CartSellerProItem();
		proItem.setId("1");
		proItem.setProduct(product);
		proItem.setNum(0);
		CartSellerProItem proItem1 = new CartSellerProItem();
		proItem1.setId("2");
		proItem1.setProduct(product1);
		proItem1.setNum(1);
		
		CartSellerItem sellerItem = new CartSellerItem();
		sellerItem.setProItem(new HashSet<CartSellerProItem>());
		sellerItem.getProItem().add(proItem);
		sellerItem.getProItem().add(proItem1);
		CartSellerItem sellerItem1 = new CartSellerItem();
		sellerItem1.setProItem(new HashSet<CartSellerProItem>());
		sellerItem1.getProItem().add(proItem);
		sellerItem1.getProItem().add(proItem1);
		
		Cart cart = new Cart();
		cart.setSellerItem(new HashSet<CartSellerItem>());
		cart.getSellerItem().add(sellerItem);
		cart.getSellerItem().add(sellerItem1);
		
		List<Product> list = new ArrayList<Product>();
		list.add(product);
		list.add(product1);
		
		//model.addAttribute("cart", cart);
		//model.addAttribute("list", list);
		//List<param> list = new ArrayList<param>();
		//model.addAttribute("list", list);
		return "/home";
	}
	
//	@RequestMapping("/recieveSelectBox")
//	public String showTest(@ModelAttribute("productForm") productForm productForm,
//			               HttpServletRequest request){
//		
////		String[] id= (String[])request.getParameterValues("product");//Attribute("list");
////		for(String temp : id)
////		System.out.println(temp);
//		for(Product product : productForm.getProduct()){
//			System.out.println(product.getId());
//			System.out.println(product.getName());
//		}
//		return "";
//	}
}
