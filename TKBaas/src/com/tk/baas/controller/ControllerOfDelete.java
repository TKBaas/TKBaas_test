//package com.tk.baas.controller;
//
//import java.io.ObjectOutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tk.baas.service.OrderService;
//import com.tk.baas.service.ProductService;
//import com.tk.baas.service.SellerService;
//import com.tk.baas.service.UserService;
//
//@Controller
//@RequestMapping(value="/Delete")
//public class ControllerOfDelete {
//	
//	@Autowired
//	UserService userService ;
//	
//	@Autowired
//	ProductService productService;
//	
//	@Autowired
//	SellerService sellerService;
//	
//	@Autowired
//	OrderService orderService;
//	
//	@RequestMapping(value="/delete/user")
//	public String deleteUser(HttpServletRequest request) throws Exception {
//		
//		boolean deleteUser = userService.deleteUser(request.getParameter("id"));
//		
//		//deleteUtils(request, deleteUser);
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/delete/seller")
//	public String deleteSeller(HttpServletRequest request) throws Exception {
//		
//		boolean deleteSeller = sellerService.deleteSeller(request.getParameter("id"));
//		
//		//deleteUtils(request, deleteSeller);
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/delete/product")
//	public String deleteProduct(HttpServletRequest request) throws Exception {
//		
//		boolean deleteProduct = productService.deleteProduct(request.getParameter("id"));
//		
//		//deleteUtils(request, deleteProduct);
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/delete/order")
//	public String deleteOrder(HttpServletRequest request) throws Exception {
//		
//		boolean deleteOrder = orderService.deleteOrder(request.getParameter("id"));
//		
//		//deleteUtils(request, deleteOrder);
//		
//		return "";
//	}
//	
//	
//	
//}
