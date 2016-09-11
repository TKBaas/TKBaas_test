package com.tk.baas.service;

import java.text.ParseException;
import java.util.List;

import com.tk.baas.model.Address;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.ResultPage;

public interface CommentService {
	/**
	 * 用户下订单后添加评论
	 * @param userId
	 * @param productId
	 * @return
	 * @throws ParseException 
	 */
	public boolean addComment(String userId, String productId) throws ParseException;
	
	/**
	 * 管理员删除
	 * @param commentId
	 * @return
	 */
	public boolean deleteComment(String commentId);
	
	/**
	 * 更新评论（即用户添加商品评论）
	 * @param commentId
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public boolean updateComment(String commentId, Comment comment)throws Exception;
	
	/**
	 * 用户查看商品评论记录
	 * @param userId
	 * @param page
	 * @return
	 */
	public ResultPage getUserComment(String userId, Page page);
	
	/**
	 * 用户查看商品（所有）留言
	 * @param productId
	 * @param page
	 * @return
	 */
	public ResultPage getProductComment(String productId, Page page);
	
	/**
	 * 用于差评(bad)， 好评(good)， 中评(normal)商品的评论
	 * @param productId
	 * @param grade
	 * @param page
	 * @return
	 */
	public ResultPage getCommentDetailList(String productId, String grade, Page page);
}
