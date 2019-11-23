package com.sec.news.control;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.sec.news.dao.TypeDao;
import com.sec.news.model.PageModel;
import com.sec.news.model.Type;

public class TypeServlet extends HttpServlet {

	TypeDao dao = new TypeDao();
	
	/**
	 * Constructor of the object.
	 */
	public TypeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���ñ���
		request.setCharacterEncoding("utf-8");
		
		//��ȡop��ֵ
		String op = request.getParameter("op");
		
		//����
		
		if(op.equals("add"))
		{
			//Ψһ���Ȳ�ѯ�����������ƣ�����
			add(request,response);
			
		}else if(op.equals("delete"))
		{
			delete(request,response);
		}else if(op.equals("update"))
		{
			update(request,response);
		}else if(op.equals("query"))
		{
			query(request, response);
		}
	}
	
	/**
	 * �����������
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡtypeName
		String typeName = request.getParameter("typeName");
		String remark = request.getParameter("remark");
		Type type = new Type(typeName,remark);
		
		//�ж����������Ƿ����
		if(this.dao.exists(type))
		{
			//������ʾ��Ϣ
			request.setAttribute("msg", "����ʧ�ܣ����������Ѿ����ڡ�");
			//ת����addType.jspҳ��
			request.getRequestDispatcher("../manage/addType.jsp").forward(request, response);
			return;
		}
		//�������ݷ�����ķ���ִ�в������
		if(this.dao.insert(type)>0)
		{
			this.query(request, response);
		}
		else
		{
			request.setAttribute("msg", "����ʧ�ܣ����������ݿ����");
			//ת����addType.jspҳ��
			request.getRequestDispatcher("../manage/addType.jsp").forward(request, response);
		}
	}
	
	
	/**
	 * ɾ����������
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�������ͱ��
		int typeId = 0;
		
		try {
			//�õ�Ҫɾ�����������ͱ��
			typeId = Integer.parseInt(request.getParameter("typeId"));
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		
		//�������ݿ������ķ���ɾ����������
		if(this.dao.delete(typeId) > 0)
		{
			//���²�ѯ����
			this.query(request, response);
		}
		else
		{
			request.setAttribute("msg", "���������ݿ����");
			request.getRequestDispatcher("../manage/error.jsp").forward(request, response);
		}
	}
	
	/**
	 * �޸���������
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		int typeId=0;
		try{
			//��ȡ���ͱ��
			typeId = Integer.parseInt(request.getParameter("typeId"));
		}catch (Exception e){typeId = 0;}
		//��ȡ��������е���������
		String typeName = request.getParameter("typeName");
		//��ȡ��������е����ͱ�ע
		String remark = request.getParameter("remark");
		//���������ͷ�װ��
		Type type = new Type(typeName,remark,typeId);
		//�ж����������Ƿ����(�����ظ�)
		if(this.dao.exists(type))
		{
			//������ʾ��Ϣ
			request.setAttribute("msg", "�޸�ʧ�ܣ��������������Ѿ����ڡ�");
			//ת����updateType.jspҳ��(�޸�ҳ��)
			request.getRequestDispatcher("../manage/updateType.jsp").forward(request, response);
			//���·���
			return;
		}
		//�������ݷ�����ķ���ִ�в������
		if(this.dao.update(type)>0)
		{
			this.query(request, response);
		}
		else
		{
			request.setAttribute("msg", "�޸�ʧ�ܣ����������ݿ����");
			//ת����addType.jspҳ��
			request.getRequestDispatcher("../manage/updateType.jsp").forward(request, response);
		}
	}
	
	/**
	 * ��ҳ��ѯ��������
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = 1;//ҳ��
		int pageSize = 5;//ÿ��ҳ����ʾ��ҳ��
		try{
			pageNo =Integer.parseInt(request.getParameter("pageNo"));
		}catch(Exception e){
			pageNo = 1;
		}
		try{
			pageSize =Integer.parseInt(request.getParameter("pageSize"));
		}catch(Exception e){
			pageSize = 5;
		}
		
		PageModel<Type> pm = new PageModel<Type>();
		pm.setPageNo(pageNo);
		pm.setPageSize(pageSize);
		pm.setCountData(dao.selectCount());
		this.dao.select(pm);
		request.setAttribute("data", pm);
		//ת��type����ʾ��������
		request.getRequestDispatcher("../manage/type.jsp").forward(request, response);
	}
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
