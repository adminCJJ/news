package com.sec.news.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sec.news.model.*;


public class TypeDao {
	
	/**
	 * 查询所有类型
	 * @return 类型集合
	 */
	public ArrayList<Type> select()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Type> lst = new ArrayList<Type>();
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsTypeInfo");
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			while(rs.next())
			{
				Type type = new Type();
				type.setTypeId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("typeName"));
				type.setRemark(rs.getString("remark"));
				lst.add(type);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return lst;
	}
	
	/**
	 * 查询类型编号下的ID(根据编号查询)
	 * @param typeId 
	 * @return
	 */
	public Type select(int typeId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Type type = new Type();
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsTypeInfo where typeId = ?");
			pstmt.setInt(1, typeId);
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			if(rs.next())
			{
				type.setTypeId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("typeName"));
				type.setRemark(rs.getString("remark"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return type;
	}
	
	/**
	 * 查询类型编号下的ID(根据Type对象中的编号查询)
	 * @param Type 新闻类
	 * @return 
	 */
	public Type select(Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsTypeInfo where typeId = ?");
			pstmt.setInt(1, type.getTypeId());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			if(rs.next())
			{
				type.setTypeId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("typeName"));
				type.setRemark(rs.getString("remark"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return type;
	}
	
	/**
	 * 返回使用页数
	 * @return 
	 */
	public int selectCount()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select count(*) as 'num' from newsTypeInfo ");
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			while(rs.next())
			{
				i = rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return i;
	}
	
	
	/**
	 * 返回页面
	 * @return 分页模型（封装了页数【pageNo】、一页显示多少张【pageSize】）
	 */
	public ArrayList<Type> select(PageModel<Type> pm)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Type> lst = new ArrayList<Type>();
		try {
			int no = pm.getPageNo();
			int size = pm.getPageSize();
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select top "+size+" * from newsTypeInfo where typeId not in" +
					"(select top (?*(?-1)) typeId from newsTypeInfo)");
			pstmt.setInt(1, size);
			pstmt.setInt(2, no);
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			while(rs.next())
			{
				Type type = new Type();
				type.setTypeId(rs.getInt("typeId"));
				type.setTypeName(rs.getString("typeName"));
				type.setRemark(rs.getString("remark"));
				pm.getPage().add(type);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return lst;
	}

	/**
	 * 查询是否存在该类型
	 * @param type
	 * @return
	 */
	public boolean exists(Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DaoUtil.getConn();
			//查询不为该编号是否有该名称
			pstmt = con.prepareStatement("select * from newsTypeInfo where typeName = ? and typeId != ?");
			pstmt.setString(1, type.getTypeName());
			pstmt.setInt(2, type.getTypeId());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			while(rs.next())
			{
				//查询到说明已经重复了
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return flag;
	}
	
	/**
	 * 将对象直接添加到数据库中
	 * @param type
	 * @return
	 */
	public int insert (Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("insert into newsTypeInfo values(?,?)");
			pstmt.setString(1, type.getTypeName());
			pstmt.setString(2, type.getRemark());
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(null, pstmt, con);
		}
		return i;
	}
	
	/**
	 * 根据类型编号删除类型
	 * @param typeId
	 * @return
	 */
	public int delete (int typeId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("delete from newsTypeInfo where typeId = ?");
			pstmt.setInt(1, typeId);
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(null, pstmt, con);
		}
		return i;
	}
	
	/**
	 * 修改
	 * @param type
	 * @return
	 */
	public int update(Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("update newsTypeInfo set typeName = ? ,remark = ? where typeId = ?");
			pstmt.setString(1, type.getTypeName());
			pstmt.setString(2, type.getRemark());
			pstmt.setInt(3, type.getTypeId());
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(null, pstmt, con);
		}
		return i;
	}
}
