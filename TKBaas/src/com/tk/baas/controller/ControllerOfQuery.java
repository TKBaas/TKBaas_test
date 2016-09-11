//package com.tk.baas.controller;
//
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tk.baas.model.Address;
//import com.tk.baas.model.Comment;
//import com.tk.baas.model.Order;
//import com.tk.baas.model.Product;
//import com.tk.baas.model.Seller;
//import com.tk.baas.model.User;
//import com.tk.baas.service.AddressService;
//import com.tk.baas.service.CommentService;
//import com.tk.baas.service.OrderService;
//import com.tk.baas.service.ProductService;
//import com.tk.baas.service.SellerService;
//import com.tk.baas.service.UserService;
//
//
//@Controller
//@RequestMapping(value="/Query")
//public class ControllerOfQuery {
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
//	@Autowired
//	AddressService addressService;
//	
//	@Autowired
//	CommentService commentService;
//	
//	//ByKey
//	@RequestMapping(value="/key/user")
//	public String queryUserByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<User> userList = userService.searchUser(request.getParameter("key"),request.getParameter("number"),request.getParameter("table"),request.getParameter("page"));
//		
//		OutputStream os = response.getOutputStream();
//		
//		
//		
//		return "";
//	}
//	
//	@RequestMapping(value="/key/product")
//	public String queryProductByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Product> productList = productService.searchProduct(request.getParameter("key"),request.getParameter("number"),request.getParameter("table"),request.getParameter("page"));
//		
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(productList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/key/order")
//	public String queryOrderByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Order> orderList = orderService.searchOrder(request.getParameter("key"),request.getParameter("number"),request.getParameter("table"),request.getParameter("page"));
//		
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(orderList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/key/seller")
//	public String querySellerByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Seller> sellerList = sellerService.searchSeller(request.getParameter("key"),request.getParameter("number"),request.getParameter("table"),request.getParameter("page"));
//		
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(sellerList);
//		
//		oos.close();
//
//		return "";
//	}
//
//	@RequestMapping(value="/key/address")
//	public String queryAddressByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Address> addressList = addressService.searchAddress(request.getParameter("key"),request.getParameter("number"),request.getParameter("table"),request.getParameter("page"));
//		
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(addressList);
//		
//		oos.close();
//
//		return "";
//	}
//
//	@RequestMapping(value="/key/comment")
//	public String queryCommentByKey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Comment> commentList = commentService.searchComment(request.getParameter("key"),request.getParameter("number"),request.getParameter("table"),request.getParameter("page"));
//		
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(commentList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	
//	
//	
//	//BySort
//	@RequestMapping(value="/sort/user")
//	public String queryUserBySort(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<User> userList = userService.getSortedUserlist(request.getParameter("table"), request.getParameter("number"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("page"));
//		
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(userList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/sort/product")
//	public String queryProductBySort(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<Product> productList = productService.getSortedProductlist(request.getParameter("table"), request.getParameter("number"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(productList);
//		
//		oos.close();
//
//		return "";
//		
//	}
//	
//	@RequestMapping(value="/sort/order")
//	public String queryOrderBySort(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<Order> orderList = orderService.getSortedOrderlist(request.getParameter("table"), request.getParameter("number"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(orderList);
//		
//		oos.close();
//
//		return "";
//		
//	}
//	
//	@RequestMapping(value="/sort/seller")
//	public String querySellerBySort(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Seller> sellerList = sellerService.getSortedSellerlist(request.getParameter("table"), request.getParameter("number"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(sellerList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//
//	//ByRange
//	@RequestMapping(value="/range/user")
//	public String queryUserByRange(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<User> userList = userService.getRangeUserlist(request.getParameter("table"), request.getParameter("left"), request.getParameter("right"), request.getParameter("number"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(userList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/range/product")
//	public String queryProductByRangey(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<Product> productList = productService.getRangeProductlist(request.getParameter("table"), request.getParameter("left"), request.getParameter("right"), request.getParameter("number"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(productList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/range/order")
//	public String queryOrderByRange(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<Order> orderList = orderService.getRangeOrderlist(request.getParameter("table"), request.getParameter("left"), request.getParameter("right"), request.getParameter("number"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(orderList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/range/seller")
//	public String querySellerByRange(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		List<Seller> sellerList = sellerService.getRangeSellerlist(request.getParameter("table"), request.getParameter("left"), request.getParameter("right"), request.getParameter("number"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(sellerList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/all/user")
//	public String queryUserByAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<User> userList = userService.getUserListByAll(request.getParameter("table"), request.getParameter("key"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("left"), request.getParameter("left"), request.getParameter("right"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(userList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/all/seller")
//	public String querySellerByAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Seller> sellerList = sellerService.getSellerListByAll(request.getParameter("table"), request.getParameter("key"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("left"), request.getParameter("left"), request.getParameter("right"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(sellerList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/all/product")
//	public String queryProductByAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Product> productList = productService.getProductListByAll(request.getParameter("table"), request.getParameter("key"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("left"), request.getParameter("left"), request.getParameter("right"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(productList);
//		
//		oos.close();
//
//		return "";
//	}
//	
//	@RequestMapping(value="/all/order")
//	public String queryOrderByAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		List<Order> orderList = orderService.getOrderListByAll(request.getParameter("table"), request.getParameter("key"), request.getParameter("sort"), request.getParameter("desc"), request.getParameter("left"), request.getParameter("left"), request.getParameter("right"), request.getParameter("page"));
//
//		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
//		
//		oos.writeObject(orderList);
//		
//		oos.close();
//
//		return "";
//	}
///*	
//	
//	private void queryUtils(HttpServletRequest request,Object o) throws Exception {
//		
//		
//		String ip = request.getRemoteAddr();
//		int port = request.getRemotePort();
//		String encoding="UTF-8";
//		String path ="http://" + ip +":" + port + "/TKBaas/query";
//	    URL url =new URL(path);
//	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//	    
//	    
//	    conn.setRequestMethod("POST");
//	    conn.setDoOutput(true);
//	    conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);
//	    conn.setConnectTimeout(5*1000);
//	    
//	    
//	    ObjectOutputStream oos = new ObjectOutputStream(conn.getOutputStream());
//	    
//	    oos.writeObject(o);
//	    oos.close();
//	}
//	*/
//}
