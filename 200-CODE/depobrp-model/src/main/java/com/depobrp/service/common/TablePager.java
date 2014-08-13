package com.depobrp.service.common;

import java.io.Serializable;
import java.util.Collection;

public class TablePager<T> implements Serializable{

	
	private static final long serialVersionUID = 6372031521467408871L;
	
	private int pageNum = 1;
	private int totalPage = 0;
	private int totalRow = 0;
	private int rowPerPage = 0;
	private int firstIndexOfPage = 0;
	private int lastIndexOfPage = 0;

	private Collection<T> contents;

	public TablePager(int pageNum, int totalRow, int rowPerPage, Collection<T> contents) {
		this.pageNum = pageNum <= 0 ? 1 : pageNum;
		this.rowPerPage = rowPerPage;
		this.totalRow = totalRow;
		this.totalPage = PaginationUtils.getTotalPage(totalRow, rowPerPage);
		this.firstIndexOfPage = PaginationUtils.getFirstIndex(pageNum, rowPerPage) + 1;
		
		if(pageNum < totalPage)
			this.lastIndexOfPage = pageNum * rowPerPage;
		else
			this.lastIndexOfPage = ((pageNum-1) * rowPerPage) + contents.size();
		
		this.contents = contents;
		
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public int getFirstIndexOfPage() {
		return firstIndexOfPage;
	}

	public void setFirstIndexOfPage(int firstIndexOfPage) {
		this.firstIndexOfPage = firstIndexOfPage;
	}

	public int getLastIndexOfPage() {
		return lastIndexOfPage;
	}

	public void setLastIndexOfPage(int lastIndexOfPage) {
		this.lastIndexOfPage = lastIndexOfPage;
	}

	public Collection<T> getContents() {
		return contents;
	}

	public void setContents(Collection<T> contents) {
		this.contents = contents;
	}
	
	
}
