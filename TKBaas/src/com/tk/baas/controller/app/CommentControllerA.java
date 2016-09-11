package com.tk.baas.controller.app;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.service.CommentService;
import com.tk.baas.util.IOUtil;
import com.tk.baas.util.IPUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 评论的控制器，安卓版
 * @author Administrator
 */
@Controller
@RequestMapping(value="/comment/app")
public class CommentControllerA {
	
	@Resource(name="CommentService")
	private CommentService commentService;//调用dao
	
	//安卓端用户评论
	@RequestMapping(value="/update" ,method=RequestMethod.POST)
	public void update(HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception{
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		String id = jsonObj.getString("id");
		String grade = jsonObj.getString("grade");
		String commentStr = jsonObj.getString("comment");
		
		Comment comment = new Comment();
		comment.setGrade(grade);
		comment.setId(id);
		comment.setComment(commentStr);
		
		boolean result = commentService.updateComment(id, comment);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("result", result);
		
		//将json发送至安卓端
		IOUtil.outPut(response, resultJson.toString());//将结果发送给安卓端
		return;
	}
	
	/**
	 * 用户查看用户评论记录（用户id）
	 */
	@RequestMapping(value="/getUserComment" ,method=RequestMethod.POST)
	public void getUserComment(HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception{
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		String userId = jsonObj.getString("userId");
		String currentPage = jsonObj.getString("currentPage");
		String pageSize = jsonObj.getString("pageSize");
		if(currentPage == null || currentPage.equals("")){
			currentPage = "1";
		}
		if(pageSize == null || pageSize.equals("")){
			pageSize = "9";
		}
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
			json.put("userPicture", IPUtil.IMGSURL + comment.getUserPicture() );
			json.put("userGrade", comment.getUserGrade());
			json.put("date", new SimpleDateFormat("yyyy-MM-dd").format(comment.getDate()));
			
			commentArr.add(json);
		}
		
		resultJson.put("result", commentArr);//放入多个评论
		
		//将json发送至安卓端
		IOUtil.outPut(response, resultJson.toString());
	}
	
	/**
	 * 用于查找某个商品的【所有】评论（productId ， page）
	 * @param request
	 * @param model
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/getProductComment" ,method=RequestMethod.POST)
	public void getProductComment(HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception{
		String jsonStr = IOUtil.readString(request.getInputStream());
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		String currentPage = jsonObj.getString("currentPage");
		String pageSize = jsonObj.getString("pageSize");
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
			json.put("userPicture", IPUtil.IMGSURL + comment.getUserPicture() );
			json.put("userGrade", comment.getUserGrade());
			json.put("date", new SimpleDateFormat("yyyy-MM-dd").format(comment.getDate()));
			
			commentArr.add(json);
		}
		
		resultJson.put("result", commentArr);//放入多个评论
		
		//将json发送至安卓端
		IOUtil.outPut(response, resultJson.toString());
		return ;
	}
	
	/**
	 * 用于商品的好，中，坏评（productId ，grade， page）
	 */
	@RequestMapping(value="/getCommentDetailList" ,method=RequestMethod.POST)
	public void getCommentDetailList(HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception{
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
			json.put("userPicture", IPUtil.IMGSURL + comment.getUserPicture() );
			json.put("userGrade", comment.getUserGrade());
			json.put("date", new SimpleDateFormat("yyyy-MM-dd").format(comment.getDate()));
			
			commentArr.add(json);
		}
		
		resultJson.put("result", commentArr);//放入多个评论
		
		//将json发送至安卓端
		IOUtil.outPut(response, resultJson.toString());
		return ;
	}
}
