package com.tk.baas.controller.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
import com.tk.baas.util.IOUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商家相关的控制器，安卓版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/address/app")
public class AddressControllerA {
	
	@Resource(name="AddressService")
	private AddressService addressService;
	//
	/**
	 * 添加地址（需要地址的属性，userId）
	 * @param address
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void add(HttpServletRequest request,
			        HttpServletResponse response)
			        throws Exception{
		
		request.setCharacterEncoding("utf-8");
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject info = JSONObject.fromObject(jsonStr);
		String userId = info.getString("userId");	//取出用户id
		JSONObject json = info.getJSONObject("address");	//取出address Json对象
		
		Address address = new Address();	//新建json对象
		address.setReceiver(json.getString("receiver"));
		address.setPhone(json.getString("phone"));
		address.setProvince(json.getString("province"));
		address.setCity(json.getString("city"));
		address.setCountyTown(json.getString("countyTown"));
		address.setStreet(json.getString("street"));
		address.setDetailsAddress(json.getString("detailsAddress"));
		address.setDefaultAddress(json.getBoolean("defaultAddress"));
		//address.setDeleted(json.getBoolean("deleted"));
        
		System.out.println(info.toString());
		//添加地址信息
		boolean result = addressService.addAddress(userId, address);
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", result);
		response.getOutputStream().write(resultJson.toString().getBytes("utf-8"));
		return ;
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
	public void delete(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String addStr = IOUtil.readString(request.getInputStream());
		JSONObject addJson = JSONObject.fromObject(addStr);
		String userId = addJson.getString("userId");
		String addressId = addJson.getString("addressId");
		
		//添加地址信息
		boolean result = addressService.deleteAddress(userId, addressId);
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", result);
		response.getOutputStream().write(resultJson.toString().getBytes("utf-8"));
		return ;
	}
	
	/**
	 * 更新地址（address）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update")
	public void update(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject info = JSONObject.fromObject(jsonStr);
		String userId = info.getString("userId");	//取出用户id
		JSONObject json = info.getJSONObject("address");	//取出address Json对象
		
		Address address = new Address();	//新建json对象
		address.setId(json.getString("id"));
		address.setReceiver(json.getString("receiver"));
		address.setPhone(json.getString("phone"));
		address.setProvince(json.getString("province"));
		address.setCity(json.getString("city"));
		address.setCountyTown(json.getString("countyTown"));
		address.setStreet(json.getString("street"));
		address.setDetailsAddress(json.getString("detailsAddress"));
		address.setDefaultAddress(json.getBoolean("defaultAddress"));
		//address.setDeleted(json.getBoolean("deleted"));
		
		System.out.println("--->"+json.getBoolean("defaultAddress"));
		
		//添加地址信息
		boolean result = addressService.updateAddressAll(userId, address);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", result);
		response.getOutputStream().write(resultJson.toString().getBytes("utf-8"));
		return ;
	}

	/**
	 * 获取地址列表      userid key page
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList")
	public void getList(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject json = JSONObject.fromObject(jsonStr);
		String userId = json.getString("userId");
		String key = json.getString("key");
		String currentPage = json.getString("currentPage");
		String pageSize = json.getString("pageSize");
	
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
		ResultPage resultPage = addressService.getAddress(userId, key, page);
		
		//封装json，然后发送至安卓
		JSONObject resultJson = new JSONObject();
		resultJson.put("currentPage", resultPage.getCurrentPage());
		resultJson.put("pageSize", resultPage.getPageSize());
		resultJson.put("totalPage", resultPage.getTotalPage());
		resultJson.put("totalNum", resultPage.getTotalNum());
		
		List<Address> addList = (List<Address>) resultPage.getResult();
		JSONArray addressArray = new JSONArray();
		for(int i=0; i<addList.size(); i++){
			Address address = addList.get(i);
			JSONObject jsonAddress = new JSONObject();
			jsonAddress.put("id", address.getId());
			jsonAddress.put("receiver", address.getReceiver());
			jsonAddress.put("phone", address.getPhone());
			jsonAddress.put("province", address.getProvince());
			jsonAddress.put("city", address.getCity());
			jsonAddress.put("countyTown", address.getCountyTown());
			jsonAddress.put("street", address.getStreet());
			jsonAddress.put("detailsAddress", address.getDetailsAddress());
			jsonAddress.put("defaultAddress", address.isDefaultAddress());
			addressArray.add(jsonAddress);
		}
		resultJson.put("result", addressArray);
		response.getOutputStream().write(resultJson.toString().getBytes("utf-8"));
		
		return ;
	}
}
