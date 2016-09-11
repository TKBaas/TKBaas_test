package com.tk.baas.controller.pc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tk.baas.model.Admin;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.service.SellerService;

/**
 * 商家相关的控制器，前端版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/seller/pc")
public class SellerControllerP {
	
	@Resource(name="SellerService")
	private SellerService sellerService;
	
	/**
	 * 获得注册的界面
	 */
	@RequestMapping("/registForm")
	public String registForm(Model model){
		Seller seller = new Seller();
	    model.addAttribute("seller" , seller);      
		return "seller/sellerRegist";//跳转到登录页面
	}
	
	/**
	 * seller注册
	 */
	@RequestMapping(value="/regist" ,method=RequestMethod.POST)
	public String registSeller(@ModelAttribute("seller") Seller seller) throws IOException {
		boolean result = sellerService.addSeller(seller);
		if(result){//注册成功
			return "redirect:/seller/pc/loginForm";	
		}else{//注册失败
			return "redirect:/seller/pc/registForm";	
		}
	}

	
	@RequestMapping(value="/loginForm")
	public String loginForm(Model model, HttpServletRequest request){
		Seller seller = new Seller();
		
		//处理cookie,用户记住密码
		Cookie[] cs = request.getCookies();
		String password =null,phone=null;
		if(cs != null){
			for(Cookie tem:cs){
				if("sellerPhone".equals(tem.getName())){
					phone = tem.getValue();
					seller.setPhone(phone);
				}
				if("sellerPassword".equals(tem.getName())){
					password=tem.getValue();
					seller.setPassword(password);
				}
			}
		}
		
	    model.addAttribute("seller" , seller);      
		return "seller/sellerLogin";//跳转到登录页面
	}

	
	@RequestMapping(value="/login")
	public String login(@ModelAttribute("seller") Seller seller,
			HttpServletRequest request , HttpServletResponse response,
			Model model) throws Exception{
		Seller result = sellerService.login(seller);
		if(result != null){//登录成功
			
			//记住密码
			String remember = request.getParameter("autoLogin");
			if("remember".equals(remember)){//用户选择cookie记住密码
				Cookie c1 = new Cookie("sellerPhone",seller.getPhone());
				Cookie c2 = new Cookie("sellerPassword",seller.getPassword());
				c1.setMaxAge(60);//设置寿命一周
				c2.setMaxAge(60);
				response.addCookie(c1);
				response.addCookie(c2);
			}
			
			HttpSession session = request.getSession();//将用户放到session
			session.setAttribute("seller", result);
			return "redirect:/product/pc/home";
		}else{//登录失败
			return "redirect:/seller/pc/loginForm";
		}
	}

	
	@RequestMapping(value="/exit")
	public String exit(HttpServletRequest request ){
		HttpSession session = request.getSession();
		Seller seller = (Seller) session.getAttribute("seller");
		if(seller != null){			//用户在线
			session.invalidate();	//销毁
		}
		return "redirect:/seller/pc/loginForm";
	}

	
	/**
	 * 获得seller update seller信息的form
	 */
	@RequestMapping("/updateFormSeller")
	public String updateFormSeller(Model model, HttpServletRequest request){
		//判断商家是否已经登录
		Seller currentSeller = (Seller) request.getSession().getAttribute("seller");
		if(currentSeller == null){
			return "redirect:/seller/pc/loginForm";//跳转到商家登录页面
		}
	    model.addAttribute("seller" , currentSeller);	//该类用于表单封装数据
		return "updateFormSeller";	//跳转到更新页面
	}
		
	/**
	 * 商家更新商家信息
	 */
	@RequestMapping("/updateBySeller")
	public String updateBySeller(@RequestParam("file") MultipartFile file , Model model,
			@ModelAttribute Seller seller, HttpServletRequest request )throws Exception{
		//判断商家是否已经登录
		Seller currentSeller = (Seller) request.getSession().getAttribute("seller");
		String fileName = null;
		String oldFileName = null;
		if(currentSeller == null){
			return "redirect:/seller/pc/loginForm";
		}
		currentSeller.setName(seller.getName());
		currentSeller.setPassword(seller.getPassword());
		currentSeller.setShopName(seller.getShopName());
		currentSeller.setShopDescription(seller.getShopDescription());
		
		oldFileName = currentSeller.getShopPicture();
		if(!file.isEmpty()){//用户更新图片（第一次插入或更新图片）
			//获取上传文件的名称
			fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			if(index == -1){//上传的文件名字有误
				model.addAttribute("error", "上传的文件有误！");
				return "redirect:/seller/pc/updateForm";
			}
			fileName = UUID.randomUUID() + fileName.substring(index);//新起图片名
			
			//获取文件将要保存的位置
			File imageFile = new File(request.getSession().getServletContext().getRealPath("/imgs"), fileName);
			//文件上传到指定位置
			file.transferTo(imageFile);
			
			//商品保存图片的名称
			currentSeller.setShopPicture(fileName);
			
			if(oldFileName != null){//需要删除旧图片
				File oldImageFile = 
						new File(request.getSession().getServletContext().getRealPath("/imgs"), oldFileName);
				if(oldImageFile.exists() && oldImageFile.isFile()){
					oldImageFile.delete();
				}
			}
		}
		
		boolean result = sellerService.updateSeller("seller", currentSeller);
		if(result){//修改成功
			return "redirect:/seller/pc/index";
		}else{//修改失败
			return "redirect:/seller/pc/updateForm";
		}
	}
	
	/**
	 * 获得管理员修改商家信息的form
	 */
	@RequestMapping("/updateFormAdmin")
	public String updateFormAdmin(@RequestParam("sellerId") String sellerId, Model model, HttpServletRequest request){
		//判断管理员是否已经登录
		Admin currentAdmin = (Admin) request.getSession().getAttribute("admin");
		if(currentAdmin == null){
			return "redirect:/admin/pc/loginForm";
		}
		Seller seller = sellerService.getSellerOne(sellerId);
	    model.addAttribute("seller" , seller);
		return "updateFormAdmin";//跳转到管理员页面
	}
	
	/**
	 * 管理员 update seller信息
	 */
	@RequestMapping(value="/updateByAdmin" ,method=RequestMethod.POST)
	public String updateByAdmin(@ModelAttribute Seller seller, Model model,
			@RequestParam("file") MultipartFile file ,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		//判断管理员是否已经登录
		Admin currentAdmin = (Admin) request.getSession().getAttribute("admin");
		if(currentAdmin == null){
			return "redirect:/seller/pc/loginForm";
		}
		String fileName = null;
		String oldFileName = null;
		oldFileName = seller.getShopPicture();
		
		if(!file.isEmpty()){//用户更新图片（第一次插入或更新图片）
			//获取上传文件的名称
			fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			if(index == -1){//上传的文件名字有误
				model.addAttribute("error", "上传的文件有误！");
				return "seller/pc/updateForm";
			}
			fileName = UUID.randomUUID() + fileName.substring(index);//新起图片名
			
			//获取文件将要保存的位置
			File imageFile = new File(request.getSession().getServletContext().getRealPath("/imgs"), fileName);
			//文件上传到指定位置
			file.transferTo(imageFile);
			
			//商品保存图片的名称
			seller.setShopPicture(fileName);
			
			if(oldFileName != null){//需要删除旧图片
				File oldImageFile = 
						new File(request.getSession().getServletContext().getRealPath("/imgs"), oldFileName);
				if(oldImageFile.exists() && oldImageFile.isFile()){
					oldImageFile.delete();
				}
			}
		}
		
		boolean result = sellerService.updateSeller("seller", seller);
		if(result){//修改成功
			return "redirect:/seller/pc/updateFormAdmin?sellerId=1";
		}else{//修改失败
			return "redirect:/seller/pc/updateFormAdmin";
		}
	}
	
	/**
	 * 管理员删除用户
	 */
	@RequestMapping("/delete")
	public String deleteSeller(@RequestParam("sellerId") String sellerId ,HttpServletRequest request)throws Exception{
		/*Admin currentAdmin = (Admin) request.getSession().getAttribute("admin");
		if(currentAdmin == null){
			return "redirect:/admin/pc/loginForm";	//跳回管理员登录页面
		}*/
		String[] sellerIds = {sellerId};
		boolean result = sellerService.deleteSeller(sellerIds);
		if(result){	//删成功
			return "index";
		}else{	//删失败
			return "";
		}
	}
	
	/**
	 * 管理员通过 商家id 查找一个商家的详细信息
	 */
	@RequestMapping("/queryOneByAdmin")
	public String queryOneByAdmin(@RequestParam("sellerId") String sellerId, Model model)throws Exception{
		Seller seller = sellerService.getSellerOne(sellerId);
		model.addAttribute("seller", seller);
		return "admin/Detail";
	}
	
	/**
	 * 用户通过 商家id 查找一个商家的详细信息
	 */
	@RequestMapping("/queryOneByUser")
	public String querySellerByUser(@RequestParam("sellerId") String sellerId, Model model)throws Exception{
		Seller seller = sellerService.getSellerOne(sellerId);
		model.addAttribute("seller", seller);
		return "user/sellerDetail";
	}
	
	/**
	 * 管理员查询多个商家
	 */
	@RequestMapping("/queryListByAdmin")
	public String queryListByAdmin(HttpServletRequest request,
			                      HttpServletResponse response){
		//先判断管理员是否登录，没有登录
	    Admin currentAdmin = (Admin) request.getSession().getAttribute("admin");
	    if(currentAdmin == null){
			return "redirect:/admin/pc/loginForm";	//跳回管理员登录页面
		}
	    return "user/sellerDetail";
	}
	
	@RequestMapping("/showQueryListByAdmin")
	public void showListByAdmin(HttpServletRequest request,
			                    HttpServletResponse response)
			                    throws Exception{
		//先判断管理员是否登录，没有登录
		Admin currentAdmin = (Admin) request.getSession().getAttribute("admin");
		if(currentAdmin == null){
			return ;	//跳回管理员登录页面
		}
        request.setCharacterEncoding("utf-8");
		
		String search_condition = request.getParameter("json");
		JSONObject js = JSONObject.fromObject(search_condition);
		String key    = (String) js.get("key");
		String sort   = (String) js.get("sort");
		String region = (String) js.get("region");
		String left   = (String) js.get("left");
		String right  = (String) js.get("right");
		String jumpPage = (String) js.get("jumpPage");
		
		String temp1 = (String)js.get("currentPage");
	    String temp2 = (String)js.get("pageSize");
	    if(temp1.equals(""))temp1 = "1";
	    if(temp2.equals(""))temp2 = "30";
		int currentPage = Integer.parseInt(temp1);
		int pageSize    = Integer.parseInt(temp2);
		
		Page page = new Page();
		page.setCurrentPage(new Integer(currentPage));
		page.setPageSize(new Integer(pageSize));
		
		ResultPage resultPage = sellerService.getSellersForAdmin(key, sort, region, left, right, page);
        List<Seller> list = (List<Seller>) resultPage.getResult();
		
		JSONArray jsonArray = new JSONArray();
		for(Seller tem : list){
			JSONObject obj = new JSONObject();//新建seller的json对象
			obj.put("chinaId", tem.getChinaID());
			obj.put("grade", tem.getGrade());
			obj.put("id", tem.getId());
			obj.put("name", tem.getName());
			obj.put("password", tem.getPassword());
			obj.put("phone", tem.getPhone());
			obj.put("sales", tem.getSales());
			obj.put("shopDescription", tem.getShopDescription());
			obj.put("shopMoney", tem.getShopMoney());
			obj.put("shopName", tem.getShopName());
			obj.put("shopPicture", tem.getShopPicture());
		    
			jsonArray.add(obj);//加入数组中
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("currentPage", resultPage.getCurrentPage());
		jsonObject.put("pageSize", resultPage.getPageSize());
		jsonObject.put("totalPage", resultPage.getTotalPage());
		jsonObject.put("totalNum", resultPage.getTotalNum());
		jsonObject.put("key", key);
		jsonObject.put("sort", sort);
		jsonObject.put("region", region);
		jsonObject.put("left", left);
		jsonObject.put("right", right);
		jsonObject.put("result", jsonArray);
		if(jumpPage.equals("1"))
			jsonObject.put("jumpPage", resultPage.getCurrentPage());
		else jsonObject.put("jumpPage", "");
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
		
	}
	
	/**
	 * 用户查询多个商家
	 */
	@RequestMapping("/queryListByUser")
	public String queryListByUser(){
		
	    return "";
	}
	@RequestMapping("/getListByUser")
	public void getQuerySellerByUser(HttpServletRequest request,
			                        HttpServletResponse response)
			                        throws Exception{
        request.setCharacterEncoding("utf-8");
		
		String search_condition = request.getParameter("json");
		JSONObject js = JSONObject.fromObject(search_condition);
		String key    = (String) js.get("key");
		String sort   = (String) js.get("sort");
		String jumpPage = (String) js.get("jumpPage");
		
		String temp1 = (String)js.get("currentPage");
	    String temp2 = (String)js.get("pageSize");
	    if(temp1.equals(""))temp1 = "1";
	    if(temp2.equals(""))temp2 = "30";
		int currentPage = Integer.parseInt(temp1);
		int pageSize    = Integer.parseInt(temp2);
		
		Page page = new Page();
		page.setCurrentPage(new Integer(currentPage));
		page.setPageSize(new Integer(pageSize));
		
		ResultPage resultPage = sellerService.getSellersForUser(key, sort, page);
        List<Seller> list = (List<Seller>) resultPage.getResult();
		
		JSONArray jsonArray = new JSONArray();
		for(Seller tem : list){
			JSONObject obj = new JSONObject();//新建seller的json对象
			obj.put("chinaId", tem.getChinaID());
			obj.put("grade", tem.getGrade());
			obj.put("id", tem.getId());
			obj.put("name", tem.getName());
			obj.put("password", tem.getPassword());
			obj.put("phone", tem.getPhone());
			obj.put("sales", tem.getSales());
			obj.put("shopDescription", tem.getShopDescription());
			obj.put("shopMoney", tem.getShopMoney());
			obj.put("shopName", tem.getShopName());
			obj.put("shopPicture", tem.getShopPicture());
		    
			jsonArray.add(obj);//加入数组中
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("currentPage", resultPage.getCurrentPage());
		jsonObject.put("pageSize", resultPage.getPageSize());
		jsonObject.put("totalPage", resultPage.getTotalPage());
		jsonObject.put("totalNum", resultPage.getTotalNum());
		jsonObject.put("key", key);
		jsonObject.put("sort", sort);
		jsonObject.put("result", jsonArray);
		if(jumpPage.equals("1"))
			jsonObject.put("jumpPage", resultPage.getCurrentPage());
		else jsonObject.put("jumpPage", "");
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}
}
