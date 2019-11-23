package com.sec.news.dao;

import java.sql.*;

import com.sec.news.model.User;

public class UserDao {
	/**
	 * 查询是否存在个用户
	 * @param user
	 * @return 是否存在，统计行数
	 */
	public boolean select(User user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where userName = ? and password = ?");
			//设置参数列表
			pstmt.setString(1,user.getUserName());
			pstmt.setString(2,user.getPassword());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数，存在
			flag = rs.next();
			//结果集赋值
			user.setUserId(rs.getInt("userId"));
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
	 * 查询是否存在个用户(顺便将对象初始化)
	 * @param userId 提供整型Id即可
	 * @return 返回User对象
	 */
	public User selectUser(int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = new User();
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where userId = ?");
			//设置参数列表
			pstmt.setInt(1,userId);
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			rs.next();
			user.setUserId(rs.getInt("userId"));
			user.setUserName(rs.getString("userName"));
			user.setPassword(rs.getString("password"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return user;
	}
	
	/**
	 * 查询是否存在个用户(顺便将对象初始化)
	 * @param user 传入已经初始化Id的User对象
	 * @return 返回User对象
	 */
	public User selectUser(User user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select *  from userInfo  where userId = ?");
			//设置参数列表
			pstmt.setInt(1, user.getUserId());
			//获得结果集
			rs = pstmt.executeQuery();
			//获取查询行数
			rs.next();
			user.setUserId(rs.getInt("userId"));
			user.setUserName(rs.getString("userName"));
			user.setPassword(rs.getString("password"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			DaoUtil.closeDB(rs, pstmt, con);
		}
		return user;
	}
	
	public boolean update(User user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//打开连接
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("update userInfo set password = ? where userName = ?");
			//设置参数列表
			pstmt.setString(2,user.getUserName());
			pstmt.setString(1,user.getPassword());
			int i = pstmt.executeUpdate();
			if(i == 0)
			{
				return false;
			}
			else if(i == 1)
			{
				return true;
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
}
