package com.sec.news.model;

import java.util.*;

import com.sec.news.dao.TypeDao;


public class PageModel<T> {
	
	/**
	 * ��ǰҳ��
	 */
	private int pageNo=1;
	/**
	 * ÿҳ��������
	 */
	private int pageSize=5;
	
	/**
	 * ��ѯ���������ܹ���������������������
	 */
	private int countData;
	
	/**
	 * ��������ҳ�����ݼ���
	 */
	private List<T> page = new ArrayList<T>();
	

	//����������ȡҳ����Ϣ
	public T get(int index)
	{
		return page.get(index);
	}
	
	/**
	 * ��ȡ������������
	 * @return
	 */
	public int getCountData() {
		return countData;
	}
	
	public void setCountData(int countData){
		this.countData = countData;
	}
	/**
	 * ��ȡ��ҳ���м�ҳ
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
	 * ����ҳ������
	 * @return
	 */
	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> page) {
		this.page = page;
	}
}
