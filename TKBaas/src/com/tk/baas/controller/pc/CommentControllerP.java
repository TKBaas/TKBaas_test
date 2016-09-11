package com.tk.baas.controller.pc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
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

import com.mysql.fabric.Response;
import com.tk.baas.model.Address;
import com.tk.baas.model.Admin;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.Seller;
import com.tk.baas.model.User;
import com.tk.baas.service.AddressService;
import com.tk.baas.service.CommentService;
import com.tk.baas.service.SellerService;
import com.tk.baas.util.IOUtil;

/**
 * 留言控制器    ，前端版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/comment/pc")
public class CommentControllerP {

	@Resource(name="CommentService")
	private CommentService commentService;//调用dao
	
	//更新评论（用户添加评论）
	@RequestMapping(value="/update" ,method=RequestMethod.POST)
	public String update(@ModelAttribute() Comment comment, HttpServletRequest request)throws Exception{
		//先判断用户是否登录
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return "redirect:/address/pc/userLogin";
		}
		/*ResultPage resultPage = (ResultPage) request.getSession().getAttribute("commentResultPage");
		if(resultPage == null){
			return "返回用户登录界面";
		}
		List<Comment> list = (List<Comment>) resultPage.getResult();
		Comment commentTem = null;
		for(int i=0; i<list.size(); i++){
			commentTem = list.get(i);
			if(commentTem.getId().equals(comment.getId())){
				commentTem.setComment(comment.getComment());
				commentTem.setGrade(comment.getGrade());
				break;//更新后退出
			}
		}*/
		boolean result = commentService.updateComment(comment.getId(), comment);
		if(result){
			return "";
		}else{
			return "";
		}
	}
	
	/**
	 * 管理员删除评论[ok]
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value="/delete" ,method=RequestMethod.POST)
	public String deleteComment(HttpServletRequest request, @RequestParam() String commentId){
		//先判断管理员是否登录
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return "redirect:/address/pc/userLogin";
		}
		boolean result = commentService.deleteComment(commentId);
		if(result){
			return "";
		}else{
			return "";
		}
	}
	
	/**
	 * 用户查看用户评论记录[ok]
	 * @param userId
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getUserComment" ,method=RequestMethod.POST)
	public String getUserComment(HttpServletRequest request){
		
		User currentUser = (User) request.getSession().
				                  getAttribute("user");
		if(currentUser == null){
			return "redirect:/address/pc/userLogin";
		}
		return "";
	}
	
	@RequestMapping(value="/showUserComment")
	public void showUserComment(HttpServletRequest request,
			                   HttpServletResponse response){
		
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//return "redirect:/user/pc/loginForm";//跳转回登录界面
			return ;
		}
		
		try{
	        request.setCharacterEncoding("utf-8");
			String search_condition = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(search_condition);
			String userId = jsonObject.getString("userId");
			
			String currentPage  = jsonObject.getString("currentPage");
			String pageSize     = jsonObject.getString("pageSize");
			if(currentPage == null || currentPage.equals(""))currentPage = "1";
			if(pageSize == null || pageSize.equals(""))pageSize = "9";
			
			Page page = new Page();
			page.setCurrentPage(new Integer(currentPage));
			page.setPageSize(new Integer(pageSize));
			
			ResultPage result = commentService.getUserComment(userId, page);
			
			//封装resultPage成jsonobject
			JSONObject resultJson = new JSONObject();
			resultJson.put("currentPage", result.getCurrentPage());
			resultJson.put("pageSize", result.getPageSize());
			resultJson.put("totalPage", result.getTotalPage());
			resultJson.put("totalNum", result.getTotalNum());
			
			JSONArray commentArr = new JSONArray();
			List<Comment> commentList = (List<Comment>) result.getResult();
			for(int i=0; i<commentList.size(); i++){
				Comment comment = commentList.get(i);
				JSONObject json = new JSONObject();
				json.put("id", comment.getId());
				json.put("grade", comment.getGrade());
				json.put("comment", comment.getComment());
				json.put("username", comment.getUsername());
				json.put("proName", comment.getProName());
				json.put("userPicture", comment.getUserPicture() );
				json.put("userGrade", comment.getUserGrade());
				json.put("date", comment.getDate());
				
				commentArr.add(json);
			}
			
			resultJson.put("result", commentArr);//放入多个评论
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.println(jsonObject.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * 用户或游客：查看商品所有评论
	 * @param productId
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getProductComment")
	public String getProdcutComment(){
		return "";
	}
	
	@RequestMapping(value="/showProductComment")
	public void showProductComment(HttpServletRequest request,
			                         HttpServletResponse response){
		
		try{
	        request.setCharacterEncoding("utf-8");
			String search_condition = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(search_condition);
		    
			String currentPage = jsonObject.getString("currentPage");
			String pageSize = jsonObject.getString("pageSize");
			String productId = jsonObject.getString("productId");
			
			if(currentPage == null || currentPage.equals("")){
				currentPage = "1";
			}
			if(pageSize == null || pageSize.equals("")){
				pageSize = "9";
			}
			Page page = new Page();
			page.setCurrentPage(new Integer(currentPage));
			page.setPageSize(new Integer(pageSize));
			
			ResultPage result = commentService.getProductComment(productId, page);
			
			//封装resultPage成jsonobject
			JSONObject resultJson = new JSONObject();
			resultJson.put("currentPage", result.getCurrentPage());
			resultJson.put("pageSize", result.getPageSize());
			resultJson.put("totalPage", result.getTotalPage());
			resultJson.put("totalNum", result.getTotalNum());
			
			JSONArray commentArr = new JSONArray();
			List<Comment> commentList = (List<Comment>) result.getResult();
			for(int i=0; i<commentList.size(); i++){
				Comment comment = commentList.get(i);
				JSONObject json = new JSONObject();
				json.put("id", comment.getId());
				json.put("grade", comment.getGrade());
				json.put("comment", comment.getComment());
				json.put("username", comment.getUsername());
				json.put("proName", comment.getProName());
				json.put("userPicture", comment.getUserPicture() );
				json.put("userGrade", comment.getUserGrade());
				json.put("date", comment.getDate());
				
				commentArr.add(json);
			}
			
			resultJson.put("result", commentArr);//放入多个评论
			
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.println(jsonObject.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户或游客：查看用于差评(bad)， 好评(good)， 中评(normal)商品
	 * @param productId
	 * @param grade
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getCommentDetailList")
	public String getCommentDetailList(){
		return "";
	}
	
	@RequestMapping(value="/showCommentDetailList")
	public void getCommentDetailList(HttpServletRequest request,
			                         HttpServletResponse response)
			                         throws Exception{
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		String currentPage = jsonObj.getString("currentPage");
		String pageSize = jsonObj.getString("pageSize");
		String grade = jsonObj.getString("grade");
		String productId = jsonObj.getString("productId");
		
		if(currentPage == null || currentPage.equals("")){
			currentPage = "1";
		}
		if(pageSize == null || pageSize.equals("")){
			pageSize = "9";
		}
		Page page = new Page();
		page.setCurrentPage(new Integer(currentPage));
		page.setPageSize(new Integer(pageSize));
		
		ResultPage result = commentService.getCommentDetailList(productId, grade, page);
		
		//封装resultPage成jsonobject
		JSONObject resultJson = new JSONObject();
		resultJson.put("currentPage", result.getCurrentPage());
		resultJson.put("pageSize", result.getPageSize());
		resultJson.put("totalPage", result.getTotalPage());
		resultJson.put("totalNum", result.getTotalNum());
		
		JSONArray commentArr = new JSONArray();
		List<Comment> commentList = (List<Comment>) result.getResult();
		for(int i=0; i<commentList.size(); i++){
			Comment comment = commentList.get(i);
			JSONObject json = new JSONObject();
			json.put("id", comment.getId());
			json.put("grade", comment.getGrade());
			json.put("comment", comment.getComment());
			json.put("username", comment.getUsername());
			json.put("proName", comment.getProName());
			json.put("userPicture", comment.getUserPicture() );
			json.put("userGrade", comment.getUserGrade());
			json.put("date", comment.getDate());
			
			commentArr.add(json);
		}
		
		resultJson.put("result", commentArr);//放入多个评论
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		out.println(resultJson.toString());
		out.flush();
		out.close();
	}
}
