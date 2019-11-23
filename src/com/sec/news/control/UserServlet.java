package com.sec.news.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sec.news.dao.UserDao;
import com.sec.news.model.News;
import com.sec.news.model.PageModel;
import com.sec.news.model.Type;
import com.sec.news.model.User;

/**
 * 处理用户登录、注销、修改密码的业务逻辑
 * @author 郑
 *
 */
public class UserServlet extends HttpServlet {

	//创建全局的数据访问类对象
	private UserDao dao = new UserDao();
	
	/**
	 * Constructor of the object.
	 */
	public UserServlet() {
		super();
	}

	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		super.init();
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
	@Override
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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 请求和响应编码为utf-8
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// 得到参数op的值
		String op = request.getParameter("op");
		
		//out.println("<script>alert(\""+op+"\");</script>");
		// 根据op的值执行不同的方法
		if ("login".equals(op)) {
			// 调用登录方法
			this.login(request, response);
		}
		else if("logout".equals(op))
		{
			this.logout(request, response);
		}
		else if("update".equals(op))
		{
			this.update(request, response);
		}
	}
	
	/**
	 * 登录的方法
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//得到表单数据
		String userName = request.getParameter("userName").trim();
		String password = request.getParameter("password").trim();
		User user = new User(userName,password);
		//调用数据访问类的方法判断登录是否成功
		boolean flag = dao.select(user);
		//登录成功，将用户信息存入Session中，并跳转到manage.jsp
		if(flag)
		{
			//将所有字赋值个User对象
			dao.selectUser(user);
			//用户信息存入Session
			request.getSession().setAttribute("user", user);
			
			//跳转到manage.jsp
			response.sendRedirect("../manage/manage.jsp");
		}
		//登录失败，转发到登录页面，提示用户输入错误
		else
		{
			response.sendRedirect("../manage/login.jsp?user="+userName);
		}
	}
	
	/**
	 * 修改用户密码的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取表单提交的数据
		//新密码
		String newPwd =request.getParameterValues("newPwd")[0];
		//获取Session值，根据Session值和用户旧密码进行验证，如果不同，提示用户
		User user = (User)request.getSession().getAttribute("user");
		user.setPassword(newPwd);
		//如果成功,操作数据库和Session进行修改，后转到主页
		if(dao.update(user)) 
		{
			//跳转到主界面
			response.sendRedirect("../manage/welcome.jsp");
		}
		else
		{
			error(request, response);
		}
	}
	
	/**
	 * 注销用户的方法 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//移除Session中保存的用户数据
		request.getSession().removeAttribute("user");
		//跳转到登录界面
		response.sendRedirect("../manage/login.jsp");
	}
	
	/**
	 * 错误界面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void error(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//转发到错误提示页面
		request.getRequestDispatcher("../manage/error.jsp").forward(request, response);
	}
}
