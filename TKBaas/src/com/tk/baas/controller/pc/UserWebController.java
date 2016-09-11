package com.tk.baas.controller.pc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tk.baas.model.Address;
import com.tk.baas.model.Admin;
import com.tk.baas.model.ChangePasswd;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;
import com.tk.baas.service.AddressService;
import com.tk.baas.service.UserService;


/*
 * 用户相关控制器，前端版
 */
@Controller
@RequestMapping(value="/user/pc")
public class UserWebController {
	
	private static final Log logger = LogFactory.getLog(UserWebController.class);
	
	@Resource(name="UserService")
	private UserService userService ;

	@Resource(name="AddressService")
	private AddressService addressService;
	
	/*
	 * 跳转到注册表单界面
	 */
	@RequestMapping(value="/registForm")
	public String registForm(Model model) {
		
		User user = new User();
		model.addAttribute("user", user);
		
		return "user/userRegist";
	}
	
	/*
	 * 注册user
	 */
	@RequestMapping(value="/regist")
	public String userRegist(@ModelAttribute User user, HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		String password = user.getPassword();
		String phone = user.getPhone();
		//System.out.println("++++++++++++++++++");
		if(userService.userRegist(phone, password)) {
			return "user/registSuccess";
		}
		
		return "redirect:/user/pc/registForm";
	}
	
	/*
	 * 用户登录表单界面
	 */
	@RequestMapping(value="/loginForm")
	public String loginForm(Model model) {
		
		User user = new User();
		model.addAttribute("user", user);
		
		return "user/userLogin";
	}
	
	/*
	 * 用户登录
	 */
	@RequestMapping(value="/login")
	public String login(@ModelAttribute User user, 
			            HttpServletRequest request, 
			            Model model) 
			            throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		User user1 = null;
		
		String phone = user.getPhone();
		String password = user.getPassword();
		
		if(userService.userLogin(phone, password)) {
			user1 = userService.getUserById(userService.getUserByPhone(phone).getId());

			if(null != session.getAttribute("user")) {
				session.removeAttribute("user");
			}
			
			session.setAttribute("user", user1);
			return "redirect:/product/pc/home";
		}
		
		return "redirect:/user/pc/loginForm";
	}
	
	/*
	 * 退出登录
	 */
	@RequestMapping(value="/loginout")
	public String loginout(HttpServletRequest request) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:/product/pc/home";
	}
	
	
//	@RequestMapping(value="/changePasswdForm")
//	public String changePasswdForm(Model model) {
//		
//		ChangePasswd changePasswd = new ChangePasswd();
//		model.addAttribute("changePasswd", changePasswd);
//		return "changePasswd";
//	}
	
	
	@RequestMapping(value="/changePasswd")
	public void changePasswd(HttpServletRequest request,
			                   HttpServletResponse response) 
			                  throws IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		if(null == user) {
			return ;
		}
		
		String search_condition = request.getParameter("json");
		JSONObject js = JSONObject.fromObject(search_condition);
		String oldPassword    = (String) js.get("oldPassword");
		String newPassword   = (String) js.get("newPassword");
		String error = "更新成功";
		
		if(user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			boolean result = userService.updateUser(user);
			if(!result) {
				error = "更新失败";
			} else {
				session.setAttribute("user", user);
			}
		} else {
			error = "密码错误";
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", error);
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}

	
	/*
	 * 修改用户信息
	 */
	@RequestMapping(value="/changeInfo") 
	public String changeInfo(@RequestParam("picture") MultipartFile file ,@ModelAttribute User user, 
			BindingResult bindingResult, HttpServletRequest request, Model model) throws IllegalStateException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		User user1 = (User) session.getAttribute("user");
		
		String fileName = null;
		String oldFileName = null;
		
		if(null == user1) {
			return "redirect:/user/pc/loginForm";
		}
		
		user1.setUsername(user.getUsername());
		user1.setSex(user.getSex());
		
		oldFileName = user1.getDisplayPicture();
		
		if(!file.isEmpty()) {
			fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			if(index == -1) {
				model.addAttribute("error", "上传的文件有误");
				return "redirect:/user/pc/getInfo";
			}
			fileName = UUID.randomUUID() + fileName.substring(index);
			File imageFile = new File(request.getSession().getServletContext().getRealPath("/imgs"), fileName);
			file.transferTo(imageFile);
			
			user1.setDisplayPicture(fileName);
			
			session.setAttribute("user", user1);
			
			if(oldFileName != null) {
				File oldImageFile = new File(request.getSession().getServletContext().getRealPath("/imgs"), oldFileName);
				if(oldImageFile.exists() && oldImageFile.isFile()) {
					oldImageFile.delete();
				}
			}
		}

		boolean result = userService.updateUser(user1);
		
		if(result) {
			return "redirect:/user/pc/getInfo";
		}
		
		return "redirect:/user/pc/getInfo";//携带错误信息
	}
	
	/*
	 * 获取用户信息
	 */
	@RequestMapping(value="/getInfo")
	public String getInfo(HttpServletRequest request, 
			              @ModelAttribute("error") String error,
			              Model model) throws IOException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		List<Address> addressList = null;
		String addressKey =  null;
		String userId = null;
		Page page = new Page();
		
		if(null == user) {
			return "redirect:/user/pc/loginForm";
		}
		
		addressKey = "";
		userId = user.getId();
		page.setCurrentPage(1);
		page.setPageSize(10);
		
		addressList = (List<Address>) addressService.getAddress(userId, addressKey, page).getResult();
		ChangePasswd changePassword = new ChangePasswd();
		
		model.addAttribute("user", user);
		model.addAttribute("addressList", addressList);
		model.addAttribute("changePassword", changePassword);
		model.addAttribute("error", error);
		return "user/userSetting";
	}
	
	/*
	 *  根据key和page获取用户列表
	 *  ==============================跳转页面暂未定+===================
	 */
	@RequestMapping(value="/getUserList") 
	public String getUserList(@RequestParam("key") String key, @RequestParam("currentPage") int currentPage, 
			@RequestParam("pageSize") int pageSize, HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		if(null == session.getAttribute("admin")) {
			return "redirect:/admin/pc/login";
		}
		if(null != session.getAttribute("userList")) {
			session.removeAttribute("userList");
		}
		
		Page page = new Page();
		
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		ResultPage resultPage = userService.getUserList(key, page);
		
		model.addAttribute("resultPage", resultPage);
		
		return "";
	}
	

	
	/*
	 * 管理员将用户冻结
	 */
	@RequestMapping(value="blackUser")
	public String blackUser(@RequestParam("userId")String userId, HttpServletRequest request) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		
		Admin admin = (Admin) session.getAttribute("admin");
		
		if(null == admin) {
			return "redirect:/admin/pc/login";
		}
		
		userService.updateBlackUser(userId, true);
		
		return "adminIndex";
	}
	
	/*
	 * 管理员解封用户
	 * 同上
	 */
	@RequestMapping(value="/whiteUser")
	public String whiteUser(@RequestParam("userId")String userId, HttpServletRequest request) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		
		Admin admin = (Admin) session.getAttribute("admin");
		
		if(null == admin) {
			return "redirect:/admin/pc/login";
		}
		
		userService.updateBlackUser(userId, false);
		
		return "adminIndex";
	}
}
