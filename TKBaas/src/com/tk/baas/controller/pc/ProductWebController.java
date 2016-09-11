package com.tk.baas.controller.pc;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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

import com.tk.baas.model.OrderSearchForm;
import com.tk.baas.model.Page;
import com.tk.baas.model.Picture;
import com.tk.baas.model.ProDetailPicture;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;
import com.tk.baas.service.CommentService;
import com.tk.baas.service.ProductService;
import com.tk.baas.util.ImageUtil;

@Controller
@RequestMapping(value="/product/pc")
public class ProductWebController {
	
	private static final Log logger = LogFactory.getLog(ProductWebController.class);
	
	@Autowired
	private ProductService productService;
	
	private ImageUtil imageutil = new ImageUtil();
	
	@Resource(name="CommentService")
	private CommentService commentService;
	
	/*
	 * 跳转到添加商品表单
	 */
	@RequestMapping(value="/addProductForm")
	public String addProductForm(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Seller seller = (Seller) session.getAttribute("seller");
		if(null == seller) {
			return "redirect:/seller/pc/loginForm";
		}
		Product product = new Product();
		model.addAttribute("product", product);
		
		return "seller/addProduct";
	}
	
	/*
	 * 添加商品
	 * 可同时添加5张图片
	 * 还有5张详情图片
	 */
	@RequestMapping(value="/addProduct")
	public String addProduct(@RequestParam("pictures") MultipartFile[] files ,@ModelAttribute Product product,
			HttpServletRequest request,Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Seller seller = (Seller) session.getAttribute("seller");
		
		//System.out.println(product.getCity() + product.getName() + "+++++++++++");
		
		Set<Picture> pictureSet = null;
		Set<ProDetailPicture> detailPictureSet= null;
		Picture picture = null;
		ProDetailPicture detailPicture = null;
		String fileName = null;
		String filePath = null;
		boolean result = false;
		
		if(null == seller) {
			return "redirect:seller/pc/login";
		}
		
		if(null != files && files.length == 10) {
			int i = 0;
			pictureSet = new HashSet<Picture>() ;
			detailPictureSet = new HashSet<ProDetailPicture>();
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
					
					fileName = file.getOriginalFilename();
					int index = fileName.lastIndexOf(".");
					if(index == -1) {
						model.addAttribute("error", "图片上传错误");
						return "redirect:/product/pc/addProductForm";
					}
					fileName = UUID.randomUUID() + fileName.substring(index);
					filePath = request.getSession().getServletContext().getRealPath("/imgs");
					File imageFile = new File(filePath, fileName);
					file.transferTo(imageFile);
					if(i < 5) {
						ImageUtil imageUtil = new ImageUtil();
						String tinyPicture = imageUtil.alterPictureSize(350, 350, filePath, fileName, filePath);
						picture = new Picture();
						picture.setPictureUrl(fileName);
						picture.setTinyPictureUrl(tinyPicture);
						pictureSet.add(picture);
					} else {
						detailPicture = new ProDetailPicture();
						detailPicture.setDetailPicUrl(fileName);
						detailPictureSet.add(detailPicture);
					}
				}
				i++;
			}
			//System.out.println(pictureSet.toString());
			product.setPicture(pictureSet);
			product.setDetailPicture(detailPictureSet);
			result = productService.addProduct(seller.getId(), product);
		}
		
		if(result)
			return "redirect:/product/pc/home";
		
		return "redirect:/product/pc/addProduct";
	}

	
	@RequestMapping(value="/getInfo")
	public String getInfo(@RequestParam("productId")String productId, HttpServletRequest request, 
			Model model) throws UnsupportedEncodingException {
		//获取详情商品
		Product product = productService.getProduct(productId);
		
		String type = product.getType();
		Page popularPage = new Page();
		popularPage.setCurrentPage(1);
		popularPage.setPageSize(6);
		
		//获得人气单品：同类别人气高的商品
		List<Product> popularList = (List<Product>)
				productService.getProductListByAll(type, "sales desc", "", "", "", popularPage).getResult();
		
		//获得用户评论列表
		Page commentPage = new Page();
		commentPage.setCurrentPage(1);
		commentPage.setPageSize(8);
		ResultPage commenResultPage = commentService.getProductComment(productId, commentPage);
		
		model.addAttribute("product",product);
		model.addAttribute("popularList",popularList);
		model.addAttribute("commenResultPage",commenResultPage);
		return "user/productDetail";
	}
	
	/*
	 * 修改商品详情
	 */
	@RequestMapping(value="changeInfo")
	public String changeInfo(@RequestParam("pictures") MultipartFile[] pictureFile , @ModelAttribute Product product, 
			HttpServletRequest request, Model model) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		String pictureName = null;
		String picturePath = null;
		boolean result = false;
		Seller seller = (Seller) session.getAttribute("seller");
		String[] pictureNames = request.getParameterValues("pictureName");
		String[] tinyPictureName = request.getParameterValues("tinyPictureName");
		if(null == seller) {
			return "redirect:ller/pc/login";
		}
		
		if(null != pictureFile && pictureFile.length == 10) {
			int i = 0;
			
			for(MultipartFile file : pictureFile) {
				if(!file.isEmpty()) {
					pictureName = file.getOriginalFilename();
					int index = pictureName.lastIndexOf(".");
					if(index == -1) {
						model.addAttribute("error", "上传的文件有误");
						return "redirect:/product/pcangeInfo";
					}
					pictureName = UUID.randomUUID() + pictureName.substring(index);
					picturePath = request.getSession().getServletContext().getRealPath("/imgs");
					File imageFile = new File(picturePath, pictureName);
					file.transferTo(imageFile);
					
					if(i < 5) {
						String newTinyPiName = ImageUtil.alterPictureSize(350, 350, picturePath, pictureName, picturePath);
						productService.updatePicture(product.getId(), pictureNames[i], pictureName,newTinyPiName);
					} else {
						productService.updateDetailPicture(product.getId(), pictureNames[i], pictureName);
					}
					
					
					if(null != pictureNames[i] ) {
						if(i < 5) {
							File tinyImageFile = new File(picturePath, tinyPictureName[i]);
							if(tinyImageFile.exists() && tinyImageFile.isFile()) {
								tinyImageFile.delete();
							}
						}
						File oldImageFile = new File(picturePath, pictureNames[i]);
						if(oldImageFile.exists() && oldImageFile.isFile()) {
							oldImageFile.delete();
						}
					}
				}
				i++;
			}
		}
		
		result = productService.updateProduct(product);
		
		if(!result) {
			model.addAttribute("error","更新失败");
			return "redirect:/product/pcllerIndex";
		}
		return "redirect:/product/pc/getInfo?productId=" + product.getId();
	}

	
	/*
	 * 将商品下架
	 * 
	 * ****************页面跳转最后在弄
	 */
	@RequestMapping(value="/deleteProduct")
	public String deleteProduct(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		

		HttpSession session = request.getSession();
		
		boolean result = false;
		Seller seller = (Seller) session.getAttribute("seller");
		
		
		if(null == seller) {
			return "redirect:/seller/pc/login";
		}
		
		String productId = request.getParameter("productId");
		
		result = productService.deleteProduct(productId);
		
		if(result)
			return "redirect:/product/pc/sellerIndex";
		return "";
	}
	
	
	/*
	 * 获取商店商品列表
	 * 将获取的ProductList放到session中
	 */
	@RequestMapping(value="/getSellerProductList")
	public String getSellerProductList(HttpServletRequest request,Model model) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		
		Seller seller = (Seller) session.getAttribute("seller");
		List<Product> productList = (List<Product>) session.getAttribute("productList");
		
		
		if(null == seller) {
			return "redirect:/seller/pc/login";
		}
		
		if(null != productList) {
			session.removeAttribute("productList");
		}
		
		//productList = productService.getSellerProduct(seller.getId());
		session.setAttribute("productList", productList);
		
		return "sellerIndex";//跳转到商品店铺界面
	}
	
	
	/*
	 * 根据条件获取商品列表
	 * key:模糊搜索关键字
	 * sort:按照什么排序
	 * region:地区
	 * left-right:区间
	 * 不需要其中参数时前端这只为""(空字符)
	 * 页面跳转没完成+++++++++++++++++++++++
	 */
	@RequestMapping(value="getListByAll")
	public String show(Model model,HttpServletRequest request){
		
		String key = request.getParameter("key");
		model.addAttribute("key", key);
		return "user/searchProduct";
	}
	
	@RequestMapping(value="/showListByAll")
	public void getListByAll(HttpServletRequest request,
	                         HttpServletResponse response)
	                         throws IOException {

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
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		
		ResultPage resultPage =  productService.getProductListByAll(key, sort, region, left, right, page);
		
		List<Product> productList = (List<Product>) resultPage.getResult(); 
		
		JSONObject jsonObject = new JSONObject();
		JSONObject productInfo = new JSONObject();
		JSONArray productArray = new JSONArray();
		
		jsonObject.put("listNum", productList.size());
		
		for(Product product : productList) {
			Set<Picture> pictureSet = product.getPicture();
			String[] pictures = new String[pictureSet.size()];//每张图片的JSonObject
			JSONArray pictures_name = new JSONArray();//每张图片的JSonObject都添加到JSONArray
			int i = 0;
			for(Picture picture : pictureSet) {
				JSONObject picture_name = new JSONObject();
				pictures[i] = picture.getPictureUrl();
				picture_name.put("pictureUrl", pictures[i]);
				pictures_name.add(picture_name);
				i ++;
			}
			
			productInfo.put("id", product.getId());
			productInfo.put("name", product.getName());
			productInfo.put("city", product.getCity());
			productInfo.put("type", product.getType());
			productInfo.put("description", product.getDescription());
			productInfo.put("price", product.getPrice());
			productInfo.put("sales", product.getSales());
			productInfo.put("store", product.getStore());
			productInfo.put("picture", pictures_name);
			
			productArray.add(productInfo);
		}
		
		jsonObject.put("currentPage", resultPage.getCurrentPage());
		jsonObject.put("pageSize", resultPage.getPageSize());
		jsonObject.put("totalPage", resultPage.getTotalPage());
		jsonObject.put("totalNum", resultPage.getTotalNum());
		jsonObject.put("key", key);
		jsonObject.put("sort", sort);
		jsonObject.put("region", region);
		jsonObject.put("left", left);
		jsonObject.put("right", right);
		if(jumpPage.equals("1"))jsonObject.put("jumpPage", resultPage.getCurrentPage());
		else jsonObject.put("jumpPage", "");
		jsonObject.put("result", productArray);
		
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}
	//}

	
	
	@RequestMapping(value="/home")
	public String getProductListtoHome(HttpServletRequest request,Model model) throws IOException {
		request.setCharacterEncoding("utf-8");
		
		List<Product> hotSalesList = null;
		List<Product> recommendList = null;
		List<Product> southFruitList = null;
		List<Product> westFruitList = null;
		List<Product> northFruitList = null;
		
		ResultPage resultPage = null;
		
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(8);
		
		resultPage = productService.getProductListByAll("", "sales desc", "", "", "", page);
		hotSalesList = (List<Product>) resultPage.getResult();
		
		resultPage = productService.getProductListByAll("南果缤纷", "sales desc", "", "", "", page);
		southFruitList = (List<Product>) resultPage.getResult();
		
		resultPage = productService.getProductListByAll("西域果情", "sales desc", "", "", "", page);
		westFruitList = (List<Product>) resultPage.getResult();
		
		resultPage = productService.getProductListByAll("北果风光", "sales desc", "", "", "", page);
		northFruitList = (List<Product>) resultPage.getResult();
		
		recommendList = new ArrayList<Product>();
		Product product1 = productService.getProduct("402891815678675c0156786de2200000");
		Product product2 = productService.getProduct("402891815678675c0156786f3a51006e");
		Product product3 = productService.getProduct("402891815678675c01567870bacb00dc");
		recommendList.add(product1);
		recommendList.add(product2);
		recommendList.add(product3);
		
		model.addAttribute("hotSalesList", hotSalesList);
		model.addAttribute("recommendList", recommendList);
		model.addAttribute("southFruitList", southFruitList);
		model.addAttribute("westFruitList", westFruitList);
		model.addAttribute("northFruitList", northFruitList);
		OrderSearchForm productSearchForm = new OrderSearchForm();
		model.addAttribute("productSearchForm", productSearchForm);

		return "user/home";
	}
	
}