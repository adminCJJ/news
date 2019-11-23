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
 * �����û���¼��ע�����޸������ҵ���߼�
 * @author ֣
 *
 */
public class UserServlet extends HttpServlet {

	//����ȫ�ֵ����ݷ��������
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
		
		// �������Ӧ����Ϊutf-8
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		// �õ�����op��ֵ
		String op = request.getParameter("op");
		
		//out.println("<script>alert(\""+op+"\");</script>");
		// ����op��ִֵ�в�ͬ�ķ���
		if ("login".equals(op)) {
			// ���õ�¼����
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
	 * ��¼�ķ���
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//�õ�������
		String userName = request.getParameter("userName").trim();
		String password = request.getParameter("password").trim();
		User user = new User(userName,password);
		//�������ݷ�����ķ����жϵ�¼�Ƿ�ɹ�
		boolean flag = dao.select(user);
		//��¼�ɹ������û���Ϣ����Session�У�����ת��manage.jsp
		if(flag)
		{
			//�������ָ�ֵ��User����
			dao.selectUser(user);
			//�û���Ϣ����Session
			request.getSession().setAttribute("user", user);
			
			//��ת��manage.jsp
			response.sendRedirect("../manage/manage.jsp");
		}
		//��¼ʧ�ܣ�ת������¼ҳ�棬��ʾ�û��������
		else
		{
			response.sendRedirect("../manage/login.jsp?user="+userName);
		}
	}
	
	/**
	 * �޸��û�����ķ���
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ���ύ������
		//������
		String newPwd =request.getParameterValues("newPwd")[0];
		//��ȡSessionֵ������Sessionֵ���û������������֤�������ͬ����ʾ�û�
		User user = (User)request.getSession().getAttribute("user");
		user.setPassword(newPwd);
		//����ɹ�,�������ݿ��Session�����޸ģ���ת����ҳ
		if(dao.update(user)) 
		{
			//��ת��������
			response.sendRedirect("../manage/welcome.jsp");
		}
		else
		{
			error(request, response);
		}
	}
	
	/**
	 * ע���û��ķ��� 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�Ƴ�Session�б�����û�����
		request.getSession().removeAttribute("user");
		//��ת����¼����
		response.sendRedirect("../manage/login.jsp");
	}
	
	/**
	 * �������
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void error(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ת����������ʾҳ��
		request.getRequestDispatcher("../manage/error.jsp").forward(request, response);
	}
}
