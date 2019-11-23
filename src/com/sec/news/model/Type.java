package com.sec.news.model;

import java.util.*;

import com.sec.news.dao.TypeDao;

public class Type {
	List<Type> types = new ArrayList<Type>();
	
	public Type(){}
	
	/**
	 * 
	 * @param typeId 根据编号返回集合元素
	 */
	public Type(int typeId) {
		// TODO Auto-generated constructor stub
		TypeDao typeDao = new TypeDao();
		Type type = typeDao.select(typeId);
		this.typeId = typeId;
		this.typeName = type.getTypeName();
		this.remark = type.getRemark();
	}
	
	/**
	 * 创建Type实体
	 * @param typeName
	 * @param remark
	 */
	public Type(String typeName,String remark) {
		// TODO Auto-generated constructor stub
		this.typeName = typeName;
		this.remark = remark;
	}
	
	public Type(String typeName,String remark,int typeId) {
		// TODO Auto-generated constructor stub
		this.typeName = typeName;
		this.remark = remark;
		this.typeId = typeId;
	}
	private int typeId;
	private String typeName;
	private String remark;

	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
