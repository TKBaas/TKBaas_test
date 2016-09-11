package com.tk.baas.controller.pc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tk.baas.dao.UserDao;
import com.tk.baas.model.Cart;
import com.tk.baas.model.User;
import com.tk.baas.service.CartService;

/**
 * 购物车的controller，前端版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/cart/pc")
public class CartControllerP {
	
	@Resource(name="CartService")
	private CartService cartService;
	
	@Resource(name="UserDao")
	private UserDao userDao;
	
	/**
	 * 用户将商品加入购物车
	 */
	@RequestMapping("/addInCart")
	public String addInCart(/*@RequestParam("num") String num, */Model model, 
			@RequestParam("productId") String productId, HttpServletRequest request)
			throws IOException{
		//先判断使用是否登录
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return "redirect:/user/pc/loginForm";
		}
		String numStr = request.getParameter("num");
		int num = 0;
		if(numStr == null && numStr.equals("")){
			num =1;
		}else{
			num = new Integer(numStr);
		}
		//加入购物车
		boolean result = cartService.addIncart(currentUser.getId(), productId, new Integer(num));
		if(result){//加入成功
			return "user/addInCartSuccess";
		}else{
			return "redirect:/product/pc/home";
		}
	}
	
	/**
	 * user 查看购物车
	 * @param model
	 * @param request
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getUserProduct")
	public String getUserProduct(Model model, HttpServletRequest request)throws IOException{
		//先判断使用是否登录
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return "redirect:/user/pc/loginForm";
		}
		Cart cart = cartService.getUserProduct(currentUser.getId());
		
		currentUser.setCart(cart);
		request.getSession().setAttribute("user", currentUser);
		model.addAttribute("cart",cart);
		return "user/userCart";
	}
	
	/**
	 * 删除购物车delIncart
	 * @param model
	 * @param request
	 * @param productId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delIncart")
	public void delIncart(HttpServletRequest request,
			                HttpServletResponse response)
			                throws IOException{
		
		//先判断使用是否登录
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return ;
		}
        
		request.setCharacterEncoding("utf-8");
		String search_condition = request.getParameter("json");
		JSONObject js = JSONObject.fromObject(search_condition);
		String[] proId = {(String) js.get("proId")};
		
		boolean result = cartService.delIncart(currentUser.getId(), proId);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 修改购物车数量
	 * @param request
	 * @param productId
	 * @param num
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateCart")
	public void updateCart(HttpServletRequest request,
			                 HttpServletResponse response) 
			                 throws IOException{
		//先判断使用是否登录
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return ;
		}
		
		request.setCharacterEncoding("utf-8");
		String search_condition = request.getParameter("json");
		JSONObject js = JSONObject.fromObject(search_condition);
		String proId    = (String) js.get("proId");
		String num   = (String) js.get("num");
		
		String[] productIdArr = {proId};
		int[] numArr = {new Integer(num)};
		boolean result = cartService.updateCart(currentUser.getId(), productIdArr, numArr);
	    
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}
}
