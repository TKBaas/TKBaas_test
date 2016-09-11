package com.tk.baas.service.serviceImp;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tk.baas.dao.CommentDao;
import com.tk.baas.dao.SellerDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.service.CommentService;

@Component("CommentService")
public class CommentServiceImp implements CommentService {
	
	@Resource(name="CommentDao")
	private CommentDao commentDao;//调用dao
	
	public boolean addComment(String userId, String productId) throws ParseException{
		return commentDao.addComment(userId, productId);
	}
	
	
	public boolean deleteComment(String commentId){
		return commentDao.deleteComment(commentId);
	}
	
	
	public ResultPage getUserComment(String userId, Page page){
		return commentDao.getUserComment(userId, page);
	}
	public ResultPage getProductComment(String productId, Page page){
		return commentDao.getProductComment(productId, page);
	}
	//用于差评(bad)， 好评(good)， 中评(normal)商品
	public ResultPage getCommentDetailList(String productId, String grade, Page page){
		return commentDao.getCommentDetailList(productId, grade, page);
	}


	@Override
	public boolean updateComment(String commentId, Comment comment) throws Exception {
		// TODO Auto-generated method stub
		return commentDao.updateComment(commentId, comment);
	}

}
