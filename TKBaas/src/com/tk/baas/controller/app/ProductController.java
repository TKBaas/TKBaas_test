package com.tk.baas.controller.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.plexus.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tk.baas.dao.ProductDao;
import com.tk.baas.model.Page;
import com.tk.baas.model.Picture;
import com.tk.baas.model.ProDetailPicture;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;
import com.tk.baas.service.ProductService;
import com.tk.baas.service.SellerService;
import com.tk.baas.util.IOUtil;
import com.tk.baas.util.IPUtil;
import com.tk.baas.util.ImageUtil;
import com.tk.baas.util.JSONUtil;

@Controller
@RequestMapping(value="/product/app")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Resource(name="SellerService")
	private SellerService sellerservice;
	
	/*
	 * seller_id:商家id
	 * product_name:商品名
	 * prooduct_type：商品类型
	 * product_city：商品产地
	 * product_store：商品库存（int）
	 * product_price：商品价格（double）
	 * product_description：商品描述
	 * 
	 * show_picture_list：上传商品详情最先展示的图片的Jsonarray:包含的json：
	 * show_picture_name：图片名
	 * show_picture_str：图片的二进制字符串
	 * 
	 * detail_picture_list：上传商品详情下面展示的图片的Jsonarray:包含的json：
	 * detail_picture_name：详情图片名
	 * detail_picture_str：图片的二进制字符串
	 * 
	 * 添加商品
	 * 返回结果result(boolean）
	 */
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String sb = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(sb.toString());
		
		String seller_id = (String) js.get("seller_id");
		String product_name = (String) js.get("product_name");
		String prooduct_type = (String) js.get("product_type");
		String product_city = (String) js.get("product_city");
		int product_store = (Integer) js.get("product_store");
		double product_price = (Double) js.get("product_price");
		String product_description = (String) js.get("product_description");
		Set<Picture> pictureSet = new HashSet<Picture>();
		Set<ProDetailPicture> detailPictureSet = new HashSet<ProDetailPicture>();
		Picture picture = null;
		ProDetailPicture detailPicture = null;
		String showPictureName = null;
		String showPictureStr = null;
		String detailPictureName = null;
		String detailPictureStr = null;
		JSONArray show_picture_list = js.getJSONArray("show_picture_list") ;
		JSONArray detail_picture_list = js.getJSONArray("detail_picture_list") ;
		String filePath = request.getSession().getServletContext().getRealPath("/imgs");
		
		for(int i=0; i<show_picture_list.size();i++) {
			JSONObject pictureJson = show_picture_list.getJSONObject(i);
			picture = new Picture();
			showPictureName = (String) pictureJson.get("show_picture_name");
			showPictureStr = (String) pictureJson.get("show_picture_str");
			
			String pictureName = IOUtil.writeFile(filePath, showPictureStr, showPictureName);
			String tinyPictureName = ImageUtil.alterPictureSize(350, 350, filePath, showPictureName, filePath);
			picture.setPictureUrl(pictureName);
			picture.setTinyPictureUrl(tinyPictureName);
			pictureSet.add(picture);
		}
		for(int i=0; i<detail_picture_list.size();i++) {
			JSONObject detailJson = show_picture_list.getJSONObject(i);
			detailPicture = new ProDetailPicture();
			detailPictureName = (String) detailJson.get("detail_picture_name");
			detailPictureStr = (String) detailJson.get("detail_picture_str");
			
			String pictureName = IOUtil.writeFile(filePath, detailPictureStr, detailPictureName);
			
			detailPicture.setDetailPicUrl(pictureName);
			detailPictureSet.add(detailPicture);
		}
		
		Product product = new Product();
		
		product.setName(product_name);
		product.setType(prooduct_type);
		product.setCity(product_city);
		product.setDescription(product_description);
		product.setPrice(product_price);
		product.setStore(product_store);
		product.setPicture(pictureSet);
		product.setDetailPicture(detailPictureSet);
		
		boolean result = productService.addProduct(seller_id, product);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	/*
	 * products_id:商品ID的JSONArray
	 * product_id:每个JSONObject包含的
	 * 删除商品
	 * 返回result（boolean)
	 */
	@RequestMapping(value="/deleteProduct",method=RequestMethod.POST)
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");

		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		
		JSONArray idArray = JSONArray.fromObject(js.get("products_id")) ;
		String[] products_id = new String[idArray.size()];
		for(int i=0; i<idArray.size(); i++) {
			JSONObject productId = idArray.getJSONObject(i);
			products_id[i] = (String) productId.get("product_id");
		}
		boolean result = productService.deleteProduct(products_id);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * +++++++++++++++暂时不支持app端修改商品信息++++++++++++++++++++++
	 * 包含10张图片的和其他商品数据的流然后转换成json
	 * product_id:商家id
	 * product_name:商品名
	 * prooduct_type：商品类型
	 * product_city：商品产地
	 * product_store：商品库存（int）
	 * product_price：商品价格（double）
	 * product_description：商品描述
	 * product_picture_list：上传图片的Jsonarray:包含的json：
	 * old_picture_name：旧图片名
	 * new_picture_name：新图片名
	 * picture_str：新图片的二进制字符串
	 * 更新商品
	 * 返回结果result(boolean）
	 */
	@RequestMapping(value="/updateProduct",method=RequestMethod.POST)
	public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		String sb = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(sb.toString());
		
		String product_id = (String) js.get("product_id");
		String product_name = (String) js.get("product_name");
		String prooduct_type = (String) js.get("product_type");
		String product_city = (String) js.get("product_city");
		int product_store = (Integer) js.get("product_store");
		double product_price = (Double) js.get("product_price");
		String product_description = (String) js.get("product_description");
		Picture picture = null;
		String old_picture_name = null;
		String new_picture_name = null;
		String new_picture_str = null;
		JSONArray product_picture_list = js.getJSONArray("product_picture_list") ;
		
		for(int i=0; i<product_picture_list.size();i++) {
			old_picture_name = (String) js.get("old_picture_name");
			new_picture_name = (String) js.get("new_picture_name");
			new_picture_str = (String) js.get("new_picture_str");
			
			new_picture_name = UUID.randomUUID().toString() + new_picture_name.substring(new_picture_name.lastIndexOf("."));
			
			byte[] b = Base64.decodeBase64(new_picture_str.getBytes());
			ByteArrayInputStream byteZip = new ByteArrayInputStream(b);
			GZIPInputStream zip = new GZIPInputStream(byteZip);
			
			FileOutputStream fos = new FileOutputStream(request.getSession().getServletContext().getRealPath("/imgs") + new_picture_name);
			byte[] bb = new byte[1024];
			int len = 0;
			while((len = zip.read(bb)) != -1) {
				fos.write(bb, 0, len);
			}
			
			boolean updatePicture = true;//productService.updateProduct(product_id, old_picture_name, new_picture_name);
			
			if(updatePicture) {
				File oldImageFile = new File(request.getSession().getServletContext().getRealPath("/imgs"), old_picture_name);
				if(oldImageFile.exists() && oldImageFile.isFile()) {
					oldImageFile.delete();
				}
			}
				
		}
		
		Product product = new Product();
		
		product.setId(product_id);
		product.setName(product_name);
		product.setType(prooduct_type);
		product.setCity(product_city);
		product.setDescription(product_description);
		product.setPrice(product_price);
		product.setStore(product_store);
		
		boolean result = productService.updateProduct(product);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * product_id:商品id
	 * 获取商品信息
	 * product_id
	 * product_name:商品名
	 * prooduct_type：商品类型
	 * product_city：商品产地
	 * product_store：商品库存（int）
	 * product_sales：销量（int）
	 * product_price：商品价格（double）
	 * product_description：商品描述
	 * 
	 * pictures_url:商品图片数组JSONArray:包含商品图片名json:
	 * picture_url:商品图片名   and  tiny_picture_url:商品小图片名
	 * 
	 * detail_pictures_url:商品图片数组JSONArray:包含商品图片名json:
	 * detail_picture_url:商品详情图片名  
	 * 
	 * product_seller:商家信息json，包含下列信息
	 * id:商家seller的id
	 * name:商家姓名
	 * shopName:店铺名
	 * shopPicture:店铺头像
	 * shopDescription:店铺描述
	 * grade:等级
	 * phone:手机
	 * sales:销量
	 * 
	 * product_comment:商品第一条评价
	 */
	@RequestMapping(value="/getProduct",method=RequestMethod.POST) 
	public void getProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		
		String product_id = (String) js.get("product_id");
		
		Product product = productService.getProduct(product_id); 
		
		String path = IPUtil.IMGSURL;
		
		JSONObject jsonObject = JSONUtil.getProductJSON(product,path);
		System.out.println(jsonObject);
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * seller_id:店铺id	 
	 * currentPage:当前页（int）
	 * pageSize：每页的商品条数（int）
	 * +++++++++++++++++++++++++++++++++++
	 * 获取商店所有商品信息
	 * +++++++++++++++++++++++++++++++++++
	 * currentPage:当前页（int）
	 * pageSize:每页的商品条数（int）
	 * totalNum:总的商品数（int）
	 * totalPage:总的页数（int）
	 * product_list：返回的商品列表（JsonArray)
	 * 			product_id
	 *			product_name:商品名
	 * 			prooduct_type：商品类型
	 * 			product_city：商品产地
	 * 			product_store：商品库存（int）
	 * 			product_sales：销量（int）
	 * 			product_price：商品价格（double）
	 * 			product_description：商品描述
	 * 			picture_url:商品图片名
	 * seller_id  
	 * seller_name
	 * seller_shopName
	 * seller_shopPicture
	 * seller_shopDescription
	 * seller_grade
	 * seller_phone
	 * seller_sales
	 */
	@RequestMapping(value="/getSellerProductList",method=RequestMethod.POST) 
	public void getSellerProductList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String path = IPUtil.IMGSURL;
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		String seller_id = (String) js.get("seller_id");
		String currentPage = (String)js.get("currentPage");
		String pageSize = (String)js.get("pageSize");
		
		if(pageSize == null || pageSize.equals(""))pageSize = "10";
		ResultPage resultPage = (ResultPage) productService
			   .getSellerProduct(seller_id, currentPage, pageSize);
		
		List<Product> productList = (List<Product>) resultPage.getResult();
		Seller seller = sellerservice.getSellerOne(seller_id);
		
		JSONObject jsonObject = new JSONObject();
		JSONArray productArray = JSONUtil.getProductJSONArray(productList, path);
		
		jsonObject.put("currentPage", resultPage.getCurrentPage());
		jsonObject.put("pageSize", resultPage.getPageSize());
		jsonObject.put("totalNum", resultPage.getTotalNum());
		jsonObject.put("totalPage", resultPage.getTotalPage());
		jsonObject.put("product_list", productArray);
		
		jsonObject.put("seller_id", seller.getId());
		jsonObject.put("seller_name", seller.getName());
		jsonObject.put("seller_shopName", seller.getShopName());
		jsonObject.put("seller_shopPicture", IPUtil.IMGSURL+seller.getShopPicture());
		jsonObject.put("seller_shopDescription", seller.getShopDescription());
		jsonObject.put("seller_grade", seller.getGrade());
		jsonObject.put("seller_phone", seller.getPhone());
		jsonObject.put("seller_sales", seller.getSales());
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * key:搜索关键字(为空字符串""默认搜索所有商品)
	 * sort:排序方式 （"sales":销量     "price"：价格由低到高      "price desc":价格由高到低    ""：为空字符串默认不排序 ）
	 * left，right:价格区间范围
	 * currentPage:当前页（即第几次请求获取的商品）int
	 * pageSize:每页的商品数（每次请求获取的商品数）int
	 * +++++++++++++++++++++++++++++++++++++++++++++++
	 * 获取符合商品列表信息
	 * +++++++++++++++++++++++++++++++++++++++++++++++
	 * currentPage：当前页数(int)
	 * pageSize：一页的用户条数(int)
	 * totalPage：总的页数(int)
	 * totalNum：总的返回总的商品数(int)
	 * product_list：返回的商品列表（JsonArray)其中每个json包含下列属性
	 * product_id
	 * product_name:商品名
	 * prooduct_type：商品类型
	 * product_city：商品产地
	 * product_store：商品库存（int）
	 * product_sales：销量（int）
	 * product_price：商品价格（double）
	 * product_description：商品描述
	 * picture_url:商品图片名
	 */
	@RequestMapping(value="/getProductListByAll",method=RequestMethod.POST) 
	public void getProductListByAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String path = IPUtil.IMGSURL;
		String str = IOUtil.readString(request.getInputStream());
		
		JSONObject js = JSONObject.fromObject(str);
		System.out.println(js);
		String key = (String) js.get("key");
		String sort = (String) js.get("sort");
		String region = "price";
		String left = (String) js.get("left");
		String right = (String) js.get("right");
		int currentPage = (Integer) js.get("currentPage");
		int pageSize = (Integer) js.get("pageSize");
		
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		ResultPage resultPage =  productService.getProductListByAll(key, sort, region, left, right, page);
		
		List<Product> productList = (List<Product>) resultPage.getResult(); 
		
		JSONObject jsonObject = new JSONObject();
		JSONArray productArray = JSONUtil.getProductJSONArray(productList,path);
		
		
		jsonObject.put("listNum", productList.size());
		jsonObject.put("currentPage", resultPage.getCurrentPage());
		jsonObject.put("pageSize", resultPage.getPageSize());
		jsonObject.put("totalPage", resultPage.getTotalPage());
		jsonObject.put("totalNum", resultPage.getTotalNum());
		jsonObject.put("product_list", productArray);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
	
	
	/*
	 * 
	 * +++++++++++获取首页所需商品信息++++++++++++
	 * hot_sales_list：返回的热销商品列表（JsonArray)长度为8
	 * recommend_list：返回的推荐商品列表（JsonArray)长度为3
	 * west_fruit_list：返回的西域商品列表（JsonArray)长度为8
	 * north_fruit_list：返回的北果商品列表（JsonArray)长度为8
	 * south_sales_list：返回的南果商品列表（JsonArray)长度为8
	 * ++++++++++++++每个Array包含多个JSONObject（封装下列数据）
	 * product_id：商品id
	 * product_name:商品名
	 * prooduct_type：商品类型
	 * product_city：商品产地
	 * product_store：商品库存（int）
	 * product_sales：销量（int）
	 * product_price：商品价格（double）
	 * product_description：商品描述
	 * 
	 * pictures_url:商品图片数组JSONArray:包含商品图片名json:
	 * picture_url:商品图片名
	 * 
	 * detail_pictures_url:商品图片数组JSONArray:包含商品图片名json:
	 * detail_picture_url:商品详情图片名  
	 
	@RequestMapping(value="/home",method=RequestMethod.POST) 
	public void getProductListToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String path = "imgs/";
		List<Product> hotSalesList = null;
		List<Product> recommendList = null;
		List<Product> southFruitList = null;
		List<Product> westFruitList = null;
		List<Product> northFruitList = null;
		
		ResultPage resultPage = null;
		
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(8);
		
		resultPage = productService.getProductListByAll("", "sales desc", "sales", "", "", page);
		hotSalesList = (List<Product>) resultPage.getResult();
		
		resultPage = productService.getProductListByAll("south", "sales desc", "price", "", "", page);
		southFruitList = (List<Product>) resultPage.getResult();
		
		resultPage = productService.getProductListByAll("west", "sales desc", "price", "", "", page);
		westFruitList = (List<Product>) resultPage.getResult();
		
		resultPage = productService.getProductListByAll("north", "sales desc", "price", "", "", page);
		northFruitList = (List<Product>) resultPage.getResult();
		
		recommendList = new ArrayList<Product>();
		recommendList.add(southFruitList.get(0));
		recommendList.add(westFruitList.get(0));
		recommendList.add(northFruitList.get(0));
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray hotArray = JSONUtil.getProductJSONArray(hotSalesList,path);
		JSONArray recommendArray = JSONUtil.getProductJSONArray(recommendList,path);
		JSONArray westArray = JSONUtil.getProductJSONArray(westFruitList,path);
		JSONArray northArray = JSONUtil.getProductJSONArray(northFruitList,path);
		JSONArray southArray = JSONUtil.getProductJSONArray(southFruitList,path);
		
		jsonObject.put("hot_sales_list", hotArray);
		jsonObject.put("recommend_list", recommendArray);
		jsonObject.put("west_fruit_list", westArray);
		jsonObject.put("north_fruit_list", northArray);
		jsonObject.put("south_fruit_list", southArray);
		
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes());
	}*/
	
	
	/* 
	 * type:(sales代表获取热销列表，recommend代表获取推荐列表，west代表获取西果列表，north代表获取北果列表，south代表获取南果)
	 * +++++++++++获取首页所需商品信息++++++++++++
	 * product_list：返回的热销商品列表（JsonArray)长度为8
	 * product_list：返回的推荐商品列表（JsonArray)长度为3
	 * product_list：返回的西域商品列表（JsonArray)长度为8
	 * product_list：返回的北果商品列表（JsonArray)长度为8
	 * product_list：返回的南果商品列表（JsonArray)长度为8
	 * ++++++++++++++每个Array包含多个JSONObject（封装下列数据）
	 * product_id：商品id
	 * product_name:商品名
	 * product_type：商品类型
	 * product_city：商品产地
	 * product_store：商品库存（int）
	 * product_sales：销量（int）
	 * product_price：商品价格（double）
	 * product_description：商品描述
	 * picture_url:商品图片url
	 */
	@RequestMapping(value="/home",method=RequestMethod.POST) 
	public void getProductListToHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		String path = IPUtil.IMGSURL;
		String str = IOUtil.readString(request.getInputStream());
		JSONObject js = JSONObject.fromObject(str);
		String type = (String) js.get("type");
		//int num = (Integer) js.get("num");
		System.out.println(type);
		List<Product> product_list = null;
		
		ResultPage resultPage = null;
		
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(8);
		
		if(type.equals("sales")) {
			resultPage = productService.getProductListByAll("", "sales desc", "", "", "", page);
			product_list = (List<Product>) resultPage.getResult();
		} else if(type.equals("recommend")) {
			product_list = new ArrayList<Product>();
			Product product1 = productService.getProduct("402891815678675c0156786de2200000");
			Product product2 = productService.getProduct("402891815678675c0156786f3a51006e");
			Product product3 = productService.getProduct("402891815678675c01567870bacb00dc");
			product_list.add(product1);
			product_list.add(product2);
			product_list.add(product3);
		} else if(type.equals("west")) {
			resultPage = productService.getProductListByAll("西域果情", "sales desc", "", "", "", page);
			product_list = (List<Product>) resultPage.getResult();
		} else if(type.equals("north")) {
			resultPage = productService.getProductListByAll("北果风光", "sales desc", "", "", "", page);
			product_list = (List<Product>) resultPage.getResult();
		} else if(type.equals("south")) {
			resultPage = productService.getProductListByAll("南果缤纷", "sales desc", "", "", "", page);
			product_list = (List<Product>) resultPage.getResult();
		}
		
		JSONObject jsonObject = new JSONObject();
		JSONArray productArray = JSONUtil.getProductJSONArray(product_list,path);
		
		jsonObject.put("product_list", productArray);
		System.out.println(jsonObject);
		OutputStream os = response.getOutputStream();
		
		os.write(jsonObject.toString().getBytes("utf-8"));
	}
}
