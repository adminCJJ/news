package com.sec.news.dao;

import java.sql.*;

import com.sec.news.model.User;

public class UserDao {
	/**
	 * ��ѯ�Ƿ���ڸ��û�
	 * @param user
	 * @return �Ƿ���ڣ�ͳ������
	 */
	public boolean select(User user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where userName = ? and password = ?");
			//���ò����б�
			pstmt.setString(1,user.getUserName());
			pstmt.setString(2,user.getPassword());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����������
			flag = rs.next();
			//�������ֵ
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
	 * ��ѯ�Ƿ���ڸ��û�(˳�㽫�����ʼ��)
	 * @param userId �ṩ����Id����
	 * @return ����User����
	 */
	public User selectUser(int userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = new User();
		try {
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select *  from userInfo where userId = ?");
			//���ò����б�
			pstmt.setInt(1,userId);
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
	 * ��ѯ�Ƿ���ڸ��û�(˳�㽫�����ʼ��)
	 * @param user �����Ѿ���ʼ��Id��User����
	 * @return ����User����
	 */
	public User selectUser(User user)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select *  from userInfo  where userId = ?");
			//���ò����б�
			pstmt.setInt(1, user.getUserId());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("update userInfo set password = ? where userName = ?");
			//���ò����б�
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
