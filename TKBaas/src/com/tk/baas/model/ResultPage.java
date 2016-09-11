package com.tk.baas.model;

import java.util.List;

public class ResultPage {
	
	private int currentPage;
    private int pageSize;
    private int totalPage;
    private int totalNum;
    
    private List<?> result;
    
	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalNum() {
		return totalNum;
	}
	
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
		this.totalPage = totalNum % this.pageSize == 0 ? 
				(totalNum / this.pageSize) : (totalNum / this.pageSize + 1);
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}
}
