package com.tk.baas.dao.daoImp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.tk.baas.dao.CommentDao;
import com.tk.baas.model.Address;
import com.tk.baas.model.Comment;
import com.tk.baas.model.Page;
import com.tk.baas.model.Product;
import com.tk.baas.model.ResultPage;
import com.tk.baas.model.SaleOrder;
import com.tk.baas.model.User;

@Component("CommentDao")
public class CommentDaoImp implements CommentDao {
    
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addComment(String userId, String productId) throws ParseException {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		User user = session.get(User.class, userId);
		Product product = session.get(Product.class, productId);
		Comment comment = new Comment();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sf.parse(sf.format(new Date()));
		comment.setAccess(false);
		comment.setComment("");
		comment.setGrade("0");
		comment.setDate(date);
		comment.setProName(product.getName());
		comment.setUsername(user.getUsername());
		comment.setUserPicture(user.getDisplayPicture());
		
		user.getComment().add(comment);
		product.getComment().add(comment);
		session.save(comment);
		session.update(user);
		session.update(product);
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public boolean deleteComment(String commentId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Comment com = new Comment();
		com.setId(commentId);
		session.delete(com);
		
		session.getTransaction().commit();
		return true;
	}

	@Override
	public ResultPage getUserComment(String userId, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		 
	    User user = session.get(User.class, userId);
	    List<Comment> list = new ArrayList<Comment>(user.getComment());
	    int fromIndex = (page.getCurrentPage() - 1) * page.getPageSize();
	    int toIndex = fromIndex+page.getPageSize(); 
	    if(toIndex > list.size())toIndex = list.size();
		 
	    //获得记录数
	    int num = list.size();
		 
        ResultPage resultPage = new ResultPage();
	    resultPage.setCurrentPage(page.getCurrentPage());
		resultPage.setPageSize(page.getPageSize());
	    resultPage.setTotalNum(num);
		resultPage.setResult((List<Comment>)list.subList(fromIndex, toIndex));
	    
	    session.getTransaction().commit();
        return resultPage;
	}

	@Override
	public ResultPage getProductComment(String productId, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		 
	    Product product = session.get(Product.class, productId);
	    List<Comment> list = new ArrayList<Comment>(product.getComment());
	    int fromIndex = (page.getCurrentPage() - 1) * page.getPageSize();
	    int toIndex = fromIndex+page.getPageSize(); 
	    if(toIndex > list.size())toIndex = list.size();
		
	    //获得记录数
	    int num = list.size();
		 
        ResultPage resultPage = new ResultPage();
	    resultPage.setCurrentPage(page.getCurrentPage());
		resultPage.setPageSize(page.getPageSize());
	    resultPage.setTotalNum(num);
		resultPage.setResult((List<Comment>)list.subList(fromIndex, toIndex));
	    
	    session.getTransaction().commit();
        return resultPage;
	}

	@Override
	public ResultPage getCommentDetailList(String productId, 
			                     String grade, Page page) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		 
		String hql = "from Comment c where c.grade = '"+grade+"' and "
	               + "c.pro_id =" + productId;
	    //分页模糊搜索   
	    Query q = session.createQuery(hql).
	       		setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
	       		.setMaxResults(page.getPageSize());
	    List<Comment> list = q.list();
		 
	    //获得记录数
	    q = session.createQuery("select count(*) " + hql);
	    String num = "0";
        if(q.uniqueResult() != null ) num = q.uniqueResult().toString();
		 
        ResultPage resultPage = new ResultPage();
	    resultPage.setCurrentPage(page.getCurrentPage());
		resultPage.setPageSize(page.getPageSize());
	    resultPage.setTotalNum(Integer.parseInt(num));
		resultPage.setResult(list);
	    
	    session.getTransaction().commit();
        return resultPage;
	}
	
	@Override
	public boolean updateComment(String commentId, Comment comment)throws Exception{
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
        Comment newComment = session.get(Comment.class, commentId);
		newComment.setAccess(true);
        newComment.setComment(comment.getComment());
		newComment.setDate(new SimpleDateFormat("yyyy-MM-dd").parse((
  		    		  Calendar.getInstance().getTime().toString())));
        newComment.setGrade(comment.getGrade());
        
        session.update(newComment);
		session.getTransaction().commit();
        return true;
	}
}
