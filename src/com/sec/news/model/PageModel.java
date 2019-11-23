package com.sec.news.model;

import java.util.*;

import com.sec.news.dao.TypeDao;


public class PageModel<T> {
	
	/**
	 * 当前页码
	 */
	private int pageNo=1;
	/**
	 * 每页数据条数
	 */
	private int pageSize=5;
	
	/**
	 * 查询所有数据总共行数（所有数据条数）
	 */
	private int countData;
	
	/**
	 * 存放请求的页码数据集合
	 */
	private List<T> page = new ArrayList<T>();
	

	//根据索引获取页码信息
	public T get(int index)
	{
		return page.get(index);
	}
	
	/**
	 * 获取所有数据条数
	 * @return
	 */
	public int getCountData() {
		return countData;
	}
	
	public void setCountData(int countData){
		this.countData = countData;
	}
	/**
	 * 获取分页后有几页
	 * @return=
	 */
	public int getTotalPages()
	{				
		int temp = (countData+pageSize-1)/pageSize;
		return temp <= 0?1:temp;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 返回页码数据
	 * @return
	 */
	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> page) {
		this.page = page;
	}
}
