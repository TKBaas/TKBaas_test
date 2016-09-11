package com.tk.baas.controller.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.plexus.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.User;
import com.tk.baas.service.UserService;
import com.tk.baas.util.IOUtil;
import com.tk.baas.util.IPUtil;

@Controller
@RequestMapping(value="/user/app")
public class UserController {

	@Resource(name="UserService")
	private UserService userService;
	
	
	/*
	 * phone_number：手机号码
	 * user_password：密码
	 * 用户注册
	 * 返回：
	 * result:注册结果成功或者失败（boolean)
	 * user_id:返回用户注册生成的的用户id
	 */
	@RequestMapping(value="/regist" ,method=RequestMethod.POST)
	public void UserRegist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);

		String phone_number = (String) js.get("phone_number");
		String user_password = (String) js.get("user_password");
		
		boolean result = userService.userRegist(phone_number, user_password);
		String user_id = "";
		if(result) {
			user_id = userService.getUserByPhone(phone_number).getId();
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		jsonObject.put("user_id", user_id);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
				
	}
	
	
	/*
	 * user_phone:登录手机号码
	 * user_password:登录密码
	 * 用户登录
	 * user_id:用户id(登录不成功，返回"")
	 * result:登录结果（boolean）
	 * 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);

		String user_phone = (String) js.get("user_phone");
		String user_password = (String) js.get("user_password");
		
		//System.out.println(user_phone + ":" + user_password);
		String user_id = "";
		
		boolean result = userService.userLogin(user_phone, user_password);
		if(result) {
			user_id = userService.getUserByPhone(user_phone).getId();
		}
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("result", result);
		jsonObject.put("user_id", user_id);
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * user_id:用户的id
	 * 用户退出登录
	 * result:返回结果
	 * 结果永远为true
	 */
	@RequestMapping(value="/loginout",method=RequestMethod.POST)
	public void userLoginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		
		String user_id = (String) js.get("user_id");
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("result", true);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * user_id:用户id
	 * 获取用户信息
	 * user_name:用户昵称
	 * user_phone：手机号码//不能修改
	 * user_sex：性别
	 * user_picture_url：用户图片
	 * user_money：用户财产（double)
	 */
	@RequestMapping(value="/getInfo",method=RequestMethod.POST)
	public void getUserInfo(HttpServletRequest  request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");

		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		
		String user_id = (String) js.get("user_id");
		
		User user = userService.getUserById(user_id);
		
		String user_name = user.getUsername();
		String user_phone = user.getPhone();
		String user_sex = user.getSex();
		String user_picture_url = IPUtil.IMGSURL+ user.getDisplayPicture();//得知道文件的存放位置，然后把该文件的URL路径字符串传过去给客户端加载
		double user_money = user.getMoney();
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("user_name", user_name);
		jsonObject.put("user_phone", user_phone);
		jsonObject.put("user_sex", user_sex);
		jsonObject.put("user_picture_url", user_picture_url);
		jsonObject.put("user_money", user_money);
		/*System.out.println(user_sex);
		System.out.println(user_picture_url);
		System.out.println(jsonObject);*/
		
		OutputStream os = response.getOutputStream();
		
		String reslut = jsonObject.toString();
		os.write(jsonObject.toString().getBytes("utf-8"));
	}

	/*
	 * user_id:用户id
	 * user_password:用户密码
	 * charge_money:充值金额（double)
	 * 用户充钱
	 * charge_state:充值结果（boolean)
	 */
	@RequestMapping(value="/changeMoney",method=RequestMethod.POST)
	public void changeUserMoney(HttpServletRequest  request,
			                    HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8");

		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		String user_id = (String) js.get("user_id");
		String user_password = (String) js.get("user_password");
		double charge_money = Double.valueOf(js.get("charge_money").toString());
		User user = userService.getUserById(user_id);
		String user_phone = user.getPhone();
		boolean checkout_password = userService.userLogin(user_phone, user_password);
		
		boolean charge_state = false;
		
		if(checkout_password) {
			charge_state = userService.changeMoney(user_id, charge_money);
		}
		//System.out.println(charge_state);
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("charge_state", charge_state);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));

	}
	
	
	/*
	 * user_id：用户id
	 * old_password:旧密码
	 * new_password:新密码
	 * 修改密码
	 * result:返回结果（booelan)
	 */
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public void changeUserPassword(HttpServletRequest  request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");

		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		
		String user_id = (String) js.get("user_id");
		String old_password = (String) js.get("old_password");
		String new_password = (String) js.get("new_password");
		boolean result = userService.changePassword(user_id, old_password, new_password);
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("result", result);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));

	}
	
	/*
	 * user_id:用户id
	 * picture_name:头像图片名
	 * picture_str:头像的输出流字符串
	 * user_sex：用户性别
	 * user_name：用户名
	 * 修改用户信息
	 * result：修改结果（boolean)
	 */
	@RequestMapping(value="/changeInfo",method=RequestMethod.POST)
	public void changeUSerInfo(HttpServletRequest  request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8");
		//System.out.println("++++++++++++++++++++++++++++++");

		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		System.out.println(js.toString());
		String user_id = (String) js.get("user_id");
		String picture_name = (String) js.get("picture_name");
		String picture_str = (String) js.get("picture_str");
		String user_sex = (String) js.get("user_sex");
		String user_name = (String) js.get("user_name");
		User user = userService.getUserById(user_id);
		if(null != picture_name && !picture_str.equals("0")) {
			
			picture_name = UUID.randomUUID().toString() + picture_name.substring(picture_name.lastIndexOf("."));
			
			byte[] b = Base64.decodeBase64(picture_str.getBytes("utf-8"));
			ByteArrayInputStream byteZip = new ByteArrayInputStream(b);
			GZIPInputStream zip = new GZIPInputStream(byteZip);
			
			FileOutputStream fos = new FileOutputStream(request.getSession().getServletContext().getRealPath("/imgs") + "/"+ picture_name);
			System.out.println(request.getSession().getServletContext().getRealPath("/imgs") + picture_name);
			byte[] bb = new byte[1024];
			int len = 0;
			while((len = zip.read(bb)) != -1) {
				fos.write(bb, 0, len);
			}
			user.setDisplayPicture(picture_name);
		}
		
		if(null != user_sex) {
			user.setSex(user_sex);
		}
		
		if(null != user_name){
			user.setUsername(user_name);
		}
		
		
		boolean result = userService.updateUser(user);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));

	}
	
	/*
	 * search_condition携带json数据
	 * key:关键字
	 * currentPage：当前页数（int)
	 * pageSize：一页的用户条数（int)
	 * 根据关键字key获取用户列表
	 * currentPage：当前页数(int)
	 * pageSize：一页的用户条数(int)
	 * totalPage：总的页数(int)
	 * totalNum：总的返回总的用户数(int)
	 * user_list:用户列表（jsonArray)包含json:
	 * user_id:用户Id
	 * user_name:用户名
	 * user_sex：用户性别
	 * user_phone：用户手机
	 * user_money：用户钱包
	 * user_picture：用户头像名
	 * user_isBlack：用户是否为黑名单
	 */
	@RequestMapping(value="/getUserList",method=RequestMethod.POST)
	public void getUserList(HttpServletRequest  request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");

		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		String key = (String) js.get("key");
		int currentPage =  (Integer) js.get("currentPage");
		int pageSize = (Integer) js.get("pageSize");
		System.out.println(key + currentPage + pageSize);
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
	
		ResultPage resultPage = userService.getUserList(key, page);
		List<User> userList = (List<User>) resultPage.getResult();
		
		JSONObject jsonObject = new JSONObject();
		JSONObject userInfo = new JSONObject();
		JSONArray userArray = new JSONArray();
		
		jsonObject.put("currentPage", resultPage.getCurrentPage());
		jsonObject.put("pageSize", resultPage.getPageSize());
		jsonObject.put("totalPage", resultPage.getTotalPage());
		jsonObject.put("totalNum", resultPage.getTotalNum());
		
		for(User user : userList) {
			userInfo.put("user_id", user.getId());
			userInfo.put("user_name", user.getUsername());
			userInfo.put("user_sex", user.getSex());
			userInfo.put("user_phone", user.getPhone());
			userInfo.put("user_money", user.getMoney());
			userInfo.put("user_picture", IPUtil.IMGSURL + user.getDisplayPicture());
			userInfo.put("user_isBlack", user.isBlackList());
			
			userArray.add(userInfo);
		}
		
		jsonObject.put("user_list", userArray);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * user_id:JsonArray数据存放用户id
	 * flag:将用户拉黑是true ,将用户解封是false
	 * 将用户加入黑名单或者解封被冻结用户
	 * result:结果（boolean)
	 */
	@RequestMapping(value="/blackUser",method=RequestMethod.POST)
	public void blackUser(HttpServletRequest  request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		
		JSONArray jsArray = JSONArray.fromObject(js.get("user_id"));
		String[] user_id = new String[jsArray.size()];
		for(int i=0; i<jsArray.size(); i++) {
			user_id[i] = jsArray.getString(i);
		}
		
		boolean black_flag = (Boolean) js.get("black_flag");
		
		boolean result = userService.updateBlackList(user_id, black_flag);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
}
