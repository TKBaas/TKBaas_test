//package com.tk.baas.controller;
//
//import java.io.DataOutputStream;
//import java.io.ObjectOutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tk.baas.model.Order;
//import com.tk.baas.model.Product;
//import com.tk.baas.model.Seller;
//import com.tk.baas.model.User;
//import com.tk.baas.service.OrderService;
//import com.tk.baas.service.ProductService;
//import com.tk.baas.service.SellerService;
//import com.tk.baas.service.UserService;
//
//@Controller
//@RequestMapping(value="/Add")
//public class ControllerOfAdd {
//	
//	@Autowired
//	UserService userService ;
//	
//	@Autowired
//	ProductService productService;
//	
//	@Autowired
//	SellerService sellerService;
//	sdfsad
//	@Autowired
//	OrderService orderService;
//	
//	@RequestMapping(value="/save/user")
//	public String saveUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		request.getInputStream();
//		
//		
//		boolean saveUser = userService.addUser((User) request.getAttribute("user"));
//		
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/save/seller")
//	public String saveSeller(HttpServletRequest request) throws Exception {
//		
//		boolean saveSeller = sellerService.addSeller((Seller) request.getAttribute("seller"));
//		
//		//addUtils(request, saveSeller);
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/save/product")
//	public String saveProduct(HttpServletRequest request) throws Exception {
//		
//		boolean saveProduct = productService.addProduct((Product) request.getAttribute("product"));
//		
//		//addUtils(request, saveProduct);
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/save/order")
//	public String saveOrder(HttpServletRequest request) throws Exception {
//		
//		boolean saveOrder = orderService.addOrder((Order) request.getAttribute("order"));
//		
//		//addUtils(request, saveOrder);
//		
//		return "";
//	}
//	
//	
//}
