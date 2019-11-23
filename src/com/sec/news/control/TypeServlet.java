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
		//设置编码
		request.setCharacterEncoding("utf-8");
		
		//获取op的值
		String op = request.getParameter("op");
		
		//数据
		
		if(op.equals("add"))
		{
			//唯一，先查询所有类型名称！！！
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
	 * 添加新闻类型
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取typeName
		String typeName = request.getParameter("typeName");
		String remark = request.getParameter("remark");
		Type type = new Type(typeName,remark);
		
		//判断新闻类型是否存在
		if(this.dao.exists(type))
		{
			//设置提示信息
			request.setAttribute("msg", "新增失败，新闻类型已经存在。");
			//转发到addType.jsp页面
			request.getRequestDispatcher("../manage/addType.jsp").forward(request, response);
			return;
		}
		//调用数据访问类的方法执行插入操作
		if(this.dao.insert(type)>0)
		{
			this.query(request, response);
		}
		else
		{
			request.setAttribute("msg", "新增失败，可能是数据库错误。");
			//转发到addType.jsp页面
			request.getRequestDispatcher("../manage/addType.jsp").forward(request, response);
		}
	}
	
	
	/**
	 * 删除新闻类型
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//新闻类型编号
		int typeId = 0;
		
		try {
			//得到要删除的新闻类型编号
			typeId = Integer.parseInt(request.getParameter("typeId"));
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		
		//调用数据库访问类的方法删除新闻类型
		if(this.dao.delete(typeId) > 0)
		{
			//重新查询数据
			this.query(request, response);
		}
		else
		{
			request.setAttribute("msg", "可能是数据库错误。");
			request.getRequestDispatcher("../manage/error.jsp").forward(request, response);
		}
	}
	
	/**
	 * 修改新闻类型
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
			//获取类型编号
			typeId = Integer.parseInt(request.getParameter("typeId"));
		}catch (Exception e){typeId = 0;}
		//获取传入参数中的类型名称
		String typeName = request.getParameter("typeName");
		//获取传入参数中的类型备注
		String remark = request.getParameter("remark");
		//将新闻类型封装好
		Type type = new Type(typeName,remark,typeId);
		//判断新闻类型是否存在(类型重复)
		if(this.dao.exists(type))
		{
			//设置提示信息
			request.setAttribute("msg", "修改失败，新闻类型名称已经存在。");
			//转发到updateType.jsp页面(修改页面)
			request.getRequestDispatcher("../manage/updateType.jsp").forward(request, response);
			//重新返回
			return;
		}
		//调用数据访问类的方法执行插入操作
		if(this.dao.update(type)>0)
		{
			this.query(request, response);
		}
		else
		{
			request.setAttribute("msg", "修改失败，可能是数据库错误。");
			//转发到addType.jsp页面
			request.getRequestDispatcher("../manage/updateType.jsp").forward(request, response);
		}
	}
	
	/**
	 * 分页查询新闻类型
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = 1;//页数
		int pageSize = 5;//每个页面显示的页数
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
		//转到type中显示所有数据
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
