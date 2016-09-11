package com.tk.baas.controller.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
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

import org.codehaus.plexus.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.service.SellerService;
import com.tk.baas.util.IPUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商家相关的控制器，安卓版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/seller/app")
public class SellerControllerA {

	@Resource(name="SellerService")
	private SellerService sellerService;
	
	/**
	 * 商家注册ok
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/regist" ,method=RequestMethod.POST)
	public void registSeller(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		String seller_info = request.getParameter("seller_info");//获得json的toString
		
		JSONObject js = JSONObject.fromObject(seller_info);

		String name = (String) js.get("name");						//商家姓名
		String password = (String) js.get("password"); 				//商家密码
		String shopName = (String) js.get("shopName");				//店铺名
		String shopDescription = (String) js.get("shopDescription");//商店简介
		String chinaID = (String) js.get("chinaID");				//身份证号
		String phone = (String) js.get("phone");					//手机号
		
		//商家注册时信誉进行初始化
		int grade = 1;				//信誉
		
		//封装seller对象
		Seller seller = new Seller();
		seller.setName(name);
		seller.setPassword(password);
		seller.setShopName(shopName);
		seller.setShopDescription(shopDescription);
		seller.setChinaID(chinaID);
		seller.setPhone(phone);
		seller.setGrade(grade);
		
		//执行函数得到结果
		boolean result = sellerService.addSeller(seller);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		OutputStream os = response.getOutputStream();
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	/**
	 * 商家登录（getSeller）ok
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("utf-8");
		String seller_info = request.getParameter("seller_info");//获得json的toString
		
		JSONObject js = JSONObject.fromObject(seller_info);

		String password = (String) js.get("password"); 				//商家密码
		String phone = (String) js.get("phone");					//手机号
		
		//封装seller对象
		Seller seller = new Seller();
		seller.setPassword(password);
		seller.setPhone(phone);
		
		//执行函数得到结果
		Seller result = sellerService.login(seller);
		JSONObject json = new JSONObject();
		if(result == null){//取的id为空说明登录失败
			json.put("id", null);
		}else{
			json.put("id", result.getId());
		}
		
		OutputStream os = response.getOutputStream();
		os.write(json.toString().getBytes("utf-8"));
		return ;
	}
	
	/**
	 * 商家 修改店铺信息
	 * 
	 */
	@RequestMapping("/update")
	public boolean update(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("utf-8");
		//【【【此处不能用getParament获取】】】
		BufferedReader ois =new BufferedReader( new InputStreamReader(request.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String str = null;
		while((str = ois.readLine()) != null){
			sb.append(str);
		}
		JSONObject js = JSONObject.fromObject(sb.toString());
		
		String id = (String) js.get("id");							//商家id
		String name = (String) js.get("name");						//商家姓名
		String phone = (String) js.get("phone");						//商家电话
		String password = (String) js.get("password"); 				//商家密码
		String shopName = (String) js.get("shopName");				//店铺名
		String shopDescription = (String) js.get("shopDescription");//商店简介
		String shopPicture = (String) js.get("shopPicture");		//商店图片的名字
		String shopPictureStr = (String) js.get("shopPictureStr");		//商店图片的字符串
		
		//上传图片1:用uuid生成图片的名字
		int index = shopPicture.lastIndexOf(".");
		if(index == -1){//图片名字出错
			return false;
		}
		shopPicture = UUID.randomUUID().toString()+shopPicture.substring(index);
		
		//需要找到原先的图片名字然后将图片的名字删掉
		String path = request.getSession().getServletContext().getRealPath("/imgs");
		String oldPicture = sellerService.getSellerOne(phone).getShopPicture();
		File file = new File(path + "/"+oldPicture);
		if(file.exists() && file.isFile()){//如果存在就删除
			file.delete();
		}
		
		//解压缩图片
		byte[] b = Base64.decodeBase64(shopPictureStr.getBytes());
		ByteArrayInputStream byteZip = new ByteArrayInputStream(b);
		GZIPInputStream zip = new GZIPInputStream(byteZip);
		
		//图片内容存放在服务器的位置
		FileOutputStream fos = new FileOutputStream(path + "/" + shopPicture);
		byte[] bb = new byte[1024];
		int len = 0;
		while((len = zip.read(bb)) != -1){
			fos.write(bb, 0, len);
		}
		
		//封装seller对象
		Seller seller = new Seller();
		seller.setId(id);
		seller.setName(name);
		seller.setPassword(password);
		seller.setShopName(shopName);
		seller.setShopDescription(shopDescription);
		seller.setShopPicture(shopPicture);//此处放置图片 名字
		
		//执行函数得到结果："seller"说明是商家的修改权限
		boolean result = sellerService.updateSeller("seller",seller);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		OutputStream os = response.getOutputStream();
		os.write(jsonObject.toString().getBytes("utf-8"));
		fos.close();
		return true;
	}
	
	/**
	 * 通过商家id查找一个商家的详细信息
	 */
	@RequestMapping("/queryOne")
	public void querySellerByPhone(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("utf-8");
		String seller_phone = request.getParameter("seller_phone");//获得json的toString
		
		Seller seller = sellerService.getSellerOne(seller_phone);
		
		JSONObject json = null;
		if(seller != null){
			json = new JSONObject();
			json.put("id", seller.getId());
			json.put("password", seller.getPassword());
			json.put("name", seller.getName());
			json.put("shopName", seller.getShopName());
			json.put("shopPicture", IPUtil.IMGSURL + seller.getShopPicture());
			json.put("shopDescription", seller.getShopDescription());
			json.put("shopMoney", seller.getShopMoney());
			json.put("grade", seller.getGrade());
			json.put("chinaID", seller.getChinaID());
			json.put("phone", seller.getPhone());
			json.put("sales", seller.getSales());
		}
		OutputStream os = response.getOutputStream();
		os.write(json.toString().getBytes("utf-8"));
		return;
	}
	
	/**
	 * 查找符合条件的商家，返回商家列表
	 */
	@RequestMapping("/queryList")
	public void getSellers(HttpServletRequest request, HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("utf-8");
		
		BufferedReader ois =new BufferedReader( new InputStreamReader(request.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String str = null;
		while((str = ois.readLine()) != null){
			sb.append(str);
		}
		JSONObject js = JSONObject.fromObject(sb.toString());
		String key  = (String) js.get("key");
		String sort = (String) js.get("sort");
		//String jumpPage = (String) js.get("jumpPage");
		JSONObject pageJson = (JSONObject) js.get("page");
		
		Page page  = new Page();
		int currentPage = new Integer((String)pageJson.get("currentPage"));
		int pageSize = new Integer((String)pageJson.get("pageSize"));
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		ResultPage resultPage = sellerService.getSellersForUser(key, sort, page);//获得list列表
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
			obj.put("shopPicture", IPUtil.IMGSURL + tem.getShopPicture());
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
//		if(jumpPage.equals("1"))
//			jsonObject.put("jumpPage", resultPage.getCurrentPage());
//		else jsonObject.put("jumpPage", "");
		
		OutputStream os = response.getOutputStream();
		os.write(jsonObject.toString().getBytes("utf-8"));
		return;
	}
}
