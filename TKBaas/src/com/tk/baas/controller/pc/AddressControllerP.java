package com.tk.baas.controller.pc;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.fabric.Response;
import com.tk.baas.model.Address;
import com.tk.baas.model.Admin;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;
import com.tk.baas.service.AddressService;
import com.tk.baas.service.SellerService;

/**
 * 商家相关的控制器，前端版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/address/pc")
public class AddressControllerP {
	
	@Resource(name="AddressService")
	private AddressService addressService;
	
	/**
	 * 添加地址的表单
	 */
	@RequestMapping("/addForm")
	public String addForm(HttpServletRequest request, Model model)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return "redirect:/user/pc/loginForm";
		}
		Address address = new Address();
	    model.addAttribute("address" , address);      
		return "user/userAddress";//跳转到添加地址页面
	}
	
	/**
	 * 添加地址
	 * @param address
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(Model model, 
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return "redirect:/user/pc/loginForm";//跳转回登录界面
		}
		Address address = new Address();
		//address.setId(request.getParameter("id"));
		address.setReceiver(request.getParameter("receiver"));
		address.setPhone(request.getParameter("phone"));
		address.setProvince( request.getParameter("province"));
		address.setCity( request.getParameter("city"));
		address.setCountyTown( request.getParameter("countyTown"));
		address.setStreet( request.getParameter("street"));
		address.setDetailsAddress( request.getParameter("detailsAddress"));
		
		//添加地址信息
		boolean result = addressService.addAddress(currentUser.getId(), address);
		if(result){
			//return "";跳转
		}else{
			//return "";
		}
		return "redirect:/address/pc/getList";//跳转到显示地址的页面
	}
	
	
	/**
	 * 删除地址(userId addressId)
	 * @param addressId
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(@RequestParam("addressId") String addressId, Model model, 
			HttpServletRequest request)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			return "redirect:/user/pc/loginForm";//跳转回登录界面
		}
		//添加地址信息
		boolean result = addressService.deleteAddress(currentUser.getId(), addressId);
		if(result){
			System.out.println("删除成功");
			//return "";跳转到getaddrssList页面
		}else{
			System.out.println("删除失败");
			//return "";跳到失败页面
		}
		return "redirect:/address/pc/getList";//跳转到添加地址页面
	}
	
	/**
	 * 请求更新地址的表格（需要：address  id）
	 */
	@RequestMapping("/updateForm")
	public String updateForm(@RequestParam("addressId") String addressId, HttpServletRequest request, 
			Model model)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return "redirect:/address/pc/userLogin";
		}
		//从session中取得对象
		ResultPage resultPage = (ResultPage) request.getSession().getAttribute("addrssResultPage");
		if(resultPage == null){
			return "redirect:/address/pc/getList";
		}
		Address address = null;
		for(int i=0; i<resultPage.getResult().size(); i++){
			address = (Address) resultPage.getResult().get(i);
			if(address.getId() != null && address.getId().equals(addressId)){
				break;
			}
		}
		if(address == null){//session找不到该地址
			return "redirect:/address/pc/getList";
		}else{
			model.addAttribute("address" , address);      
			return "addressUpdate";//跳转到添加地址页面
		}
	}
	
	/**
	 * 更新除默认地址之外的字段
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update")
	public String update( Model model, 
			HttpServletRequest request)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return "redirect:/address/pc/userLogin";
		}
		Address address = new Address();
		address.setId(request.getParameter("addressId"));
		address.setReceiver(request.getParameter("receiver"));
		address.setPhone(request.getParameter("phone"));
		address.setProvince( request.getParameter("province"));
		address.setCity( request.getParameter("city"));
		address.setCountyTown( request.getParameter("countyTown"));
		address.setStreet( request.getParameter("street"));
		address.setDetailsAddress( request.getParameter("detailsAddress"));
		
		//添加地址信息
		boolean result = addressService.updateAddress(address);
		if(result){
			System.out.println("更新成功");
			//return "";跳转到getaddrssList页面
		}else{
			System.out.println("更新失败！");
			//return "";跳到失败页面
		}
		return "redirect:/address/pc/getList";//跳转到添加地址页面
	}
	
	//更新默认地址（只更新默认地址）
	@RequestMapping(value="/updateDefAdd")
	public String updateDefAdd(@RequestParam("addressId") String addressId, Model model, 
			HttpServletRequest request)throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return "redirect:/address/pc/userLogin";
		}
		//添加地址信息
		boolean result = addressService.updateDefaultAddress(currentUser.getId(), addressId);
		if(result){
			System.out.println("修改默认地址成功");
			//return "";跳转到getaddrssList页面
		}else{
			System.out.println("修改默认地址失败");
			//return "";跳到失败页面
		}
		return "redirect:/address/pc/getList";//跳转到添加地址页面
	}
	
	/**
	 * 获取地址列表userid key page
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList")
	public String getList(Model model, HttpServletRequest request )throws Exception{
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return "redirect:/user/pc/loginForm";
		}
		String key = request.getParameter("key");
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage == null){
			currentPage = "1";
		}
		if(pageSize == null){
			pageSize = "10";
		}
		if(key == null){
			key="";
		}
		Page page = new Page();
		page.setCurrentPage(new Integer(currentPage));
		page.setPageSize(new Integer(pageSize));
		
		System.out.println(currentUser.getId());
		
		ResultPage resultPage = addressService.getAddress(currentUser.getId(), key, page);
		
		System.out.println(resultPage.getResult().size());
		
		//将数据放至session中，用于用户修改地址
		request.getSession().setAttribute("addrssResultPage", resultPage);
		//将结果集返回值给页面显示
		model.addAttribute("addressResultPage",resultPage);
		model.addAttribute("user",currentUser);//
		return "user/userAddress";//跳转 地址 列表 页面
	}
}
