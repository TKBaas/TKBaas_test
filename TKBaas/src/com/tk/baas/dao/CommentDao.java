package com.tk.baas.dao;

import java.text.ParseException;
import java.util.List;

import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;

public interface CommentDao {
	
	public boolean addComment(String userId, String productId) throws ParseException;
	
	
	public boolean deleteComment(String commentId);
	
	public boolean updateComment(String commentId, Comment comment)throws Exception;
	
	public ResultPage getUserComment(String userId, Page page);
	public ResultPage getProductComment(String productId, Page page);
	//用于差评(bad)， 好评(good)， 中评(normal)商品
	public ResultPage getCommentDetailList(String productId, String grade, Page page);
}
