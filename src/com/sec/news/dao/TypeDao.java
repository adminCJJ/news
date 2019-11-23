package com.sec.news.dao;

import java.sql.*;
import java.util.ArrayList;

import com.sec.news.model.*;


public class TypeDao {
	
	/**
	 * ��ѯ��������
	 * @return ���ͼ���
	 */
	public ArrayList<Type> select()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Type> lst = new ArrayList<Type>();
		try {
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsTypeInfo");
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
	 * ��ѯ���ͱ���µ�ID(���ݱ�Ų�ѯ)
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
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsTypeInfo where typeId = ?");
			pstmt.setInt(1, typeId);
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
	 * ��ѯ���ͱ���µ�ID(����Type�����еı�Ų�ѯ)
	 * @param Type ������
	 * @return 
	 */
	public Type select(Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select * from newsTypeInfo where typeId = ?");
			pstmt.setInt(1, type.getTypeId());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
	 * ����ʹ��ҳ��
	 * @return 
	 */
	public int selectCount()
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select count(*) as 'num' from newsTypeInfo ");
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
	 * ����ҳ��
	 * @return ��ҳģ�ͣ���װ��ҳ����pageNo����һҳ��ʾ�����š�pageSize����
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
			//������
			con = DaoUtil.getConn();
			pstmt = con.prepareStatement("select top "+size+" * from newsTypeInfo where typeId not in" +
					"(select top (?*(?-1)) typeId from newsTypeInfo)");
			pstmt.setInt(1, size);
			pstmt.setInt(2, no);
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
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
	 * ��ѯ�Ƿ���ڸ�����
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
			//������
			con = DaoUtil.getConn();
			//��ѯ��Ϊ�ñ���Ƿ��и�����
			pstmt = con.prepareStatement("select * from newsTypeInfo where typeName = ? and typeId != ?");
			pstmt.setString(1, type.getTypeName());
			pstmt.setInt(2, type.getTypeId());
			//��ý����
			rs = pstmt.executeQuery();
			//��ȡ��ѯ����
			while(rs.next())
			{
				//��ѯ��˵���Ѿ��ظ���
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
	 * ������ֱ����ӵ����ݿ���
	 * @param type
	 * @return
	 */
	public int insert (Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			//������
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
	 * �������ͱ��ɾ������
	 * @param typeId
	 * @return
	 */
	public int delete (int typeId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			//������
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
	 * �޸�
	 * @param type
	 * @return
	 */
	public int update(Type type)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			//������
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
