package com.sec.news.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import sun.awt.RepaintArea;

import com.sec.news.dao.NewDao;
import com.sec.news.dao.TypeDao;
import com.sec.news.dao.UserDao;
import com.sec.news.model.*;

public class NewServlet extends HttpServlet {

	private NewDao dao = new NewDao();
	
	@Override
	public void init() throws ServletException {
		// Put your code here
		super.init();
	}

	/**
	 * Constructor of the object.
	 */
	public NewServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//�������Ӧ����Ϊutf-8
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//�õ�op������ֵ
		String op = request.getParameter("op");
		//�ж�op��ֵ����������Ӧ�ķ���
		if(op.equals("add"))
		{
			add(request, response);
		}
		else if(op.equals("delete"))
		{
			delete(request, response);
		}
//		else if(op.equals("update"))
//		{
//			System.out.println("�޸�");
//			update(request, response);
//		}
		else if(op.equals("preview"))
		{
			preview(request, response);
		}else if(op.equals("update"))
		{
			show_update(request, response);
		}
		else if(op.equals("query"))
		{
			query(request, response);
		}
	}
	
	/**
	 * ���ҵ���߼�����
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ������
		String title = request.getParameter("title");
		int recommended = 0;
		int typeId = 0;
		String content = request.getParameter("content");
		String keywords = request.getParameter("keywords");
		try {
			typeId = Integer.parseInt(request.getParameter("typeId"));
			recommended = Integer.parseInt(request.getParameter("recommended"));
			//���Session�б�����û���Ϣ
			User user = (User)request.getSession().getAttribute("user");
			Type type = new Type(typeId);
			//����������Ϣ
			News news = new News();
			news.setType(type);
			news.setUser(user);
			news.setTitle(title);
			
			//�滻���������е�ת���ַ��������ʽ�ı���
			news.setContent(content.replace("\r\n", "<p/>"));
			news.setRecommended(recommended);
			news.setKeywords(keywords);
			//�������ݷ�����ķ�����������
			if(this.dao.insert(news) > 0 )
			{
				//����ɹ�����ѯ��������
				this.query(request, response);
			}else{
				request.setAttribute("msg", "��������ʧ�ܣ����������ݿ����");
				this.error(request, response);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			//�쳣����
			request.setAttribute("msg", e.getMessage()+"�����˲����������쳣");
			this.error(request, response);
		}
	}
	
	/**
	 * ɾ��ҵ��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int newsId = 0;
		//��ȡҪɾ�������ű��
		try{newsId = Integer.parseInt(request.getParameter("delete"));}
		catch(Exception e){};
		//�������ݷ�����ķ�����������
		if(this.dao.delete(newsId) > 0 )
		{
			//����ɹ�����ѯ��������
			this.query(request, response);
		}else{
			request.setAttribute("msg", "ɾ��ʧ�ܣ����������ݿ����");
			this.error(request, response);
		}
	}
	
	/**
	 * ����ҵ��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
//	private void update(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//	 	//��ȡֵ
//		int newsId = 0;
//	 	
//	 	News news = new News();
//	 	Type type = new Type();
//	 	int typeId = 0;
//	 	int recommended = 0;
//	 	
//	 	String title = request.getParameter("title");
//	 	try{
//	 		typeId = Integer.parseInt(request.getParameter("typeId"));
//	 		type.setTypeId(typeId);}
//		catch (Exception e) {}
//	 	try{
//	 		recommended = Integer.parseInt(request.getParameter("recommended"));}
//		catch (Exception e) {}
//	 	try{
//	 		newsId = Integer.parseInt(request.getParameter("newsId"));}
//		catch (Exception e) {}
//	 	String content = request.getParameter("content");
//	 	String keywords = request.getParameter("keywords");
//	 	
//	 	//����ֵ
//	 	news.setNewsId(newsId);
//	 	news.setTitle(title);
//	 	news.setType(type);
//	 	news.setRecommended(recommended);
//	 	news.setContent(content);
//	 	news.setKeywords(keywords);
//	 	
//	 	//�������ݷ�����ķ�����������
//		if(this.dao.update(news) > 0)
//		{
//			System.out.println("�޸ĳɹ�");
//			//����ɹ�����ѯ��������
//			this.query(request, response);
//		}else{
//			request.setAttribute("msg", "ɾ��ʧ�ܣ����������ݿ����");
//			this.error(request, response);
//		}
//	}
	
	/**
	 * Ԥ��ҵ��(�����޸�)
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void preview(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int typeId = 0;
		int newsId = 0;
		try {
			typeId = Integer.parseInt(request.getParameter("typeId"));
			News news = new News();
			//�޸�����ʱԤ��newId��Ϊ��
			if(request.getParameter("newsId") != null)
			{
				newsId = Integer.parseInt(request.getParameter("newsId"));
				news = new News(newsId);
				this.dao.SelectNewsId(news);
			}
			else
			{
				//��������ʱ��Ԥ��
				User user =(User) request.getSession().getAttribute("user");
				news.setUser(user);
			}
			//������������
			if(typeId != 0)
			{
				typeId = Integer.parseInt(request.getParameter("typeId"));
				Type type = new Type(typeId);
				TypeDao typeDao = new TypeDao();
				typeDao.select(type);
				news.setType(type);
			}
			if(title != null&&content!=null)
			{
				news.setTitle(title);
				news.setContent(content.replace("\r\n", "<p>"));
			}

			//��������Ϣ���õ�request������
			request.setAttribute("data", news);
			//ת����Ԥ��ҳ��
			request.getRequestDispatcher("../manage/preview.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("msg", e.getMessage());
			this.error(request, response);
		}
	}
	
	/**
	 * ��ѯ����ҵ��
	 */
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = 1;//ҳ�棬��ʼ��Ϊ1
		int pageSize = 6;//ÿҳ������������ʼ��Ϊ6
		int typeId = 0;//���ͱ��
		//����ת��
		try {pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}catch (Exception e) {}
		
		try {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}catch (Exception e) {}
		
		try {
			typeId = Integer.parseInt(request.getParameter("IfselectAll"));
		}catch (Exception e) {
			typeId = 0;
		}
		
		try {
			PageModel<News> pm = new PageModel<News>();
			pm.setPageNo(pageNo);
			pm.setPageSize(pageSize);
			//�ж��Ƿ�����������Ͳ�ѯ
			if(typeId == 0)
			{
				int countAllData = this.dao.select(pm);//��ѯ��������
				pm.setCountData(countAllData);
			}else {
				int countAllData = this.dao.select(pm,typeId);//��ѯָ�����͵�����
				pm.setCountData(countAllData);
			}
			//��PageModel�������request������
			request.setAttribute("data", pm);
			//Ҫת����jspҳ��
			String jsp = request.getParameter("jsp");
			//ת����ָ��ҳ��
			request.getRequestDispatcher(jsp).forward(request, response);
		} catch (Exception e) {
			//�����쳣��Ϣ��request��
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			
			//ת��������ҳ��
			this.error(request, response);
		}
	}
	
	/**
	 * ��ѯ��Ԥ����ҵ��
	 */
	private void show_update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int newsId = 0;
		//����ת��
		try {
			//�õ����޸����ŵı��
			newsId = Integer.parseInt(request.getParameter("newsId"));
			News news = new News(newsId);
			//����ҵ�������ķ�������ѯ������Ϣ
			this.dao.SelectNewsId(news);
			
			//�������ǩ<p>ת��Ϊ\r\n������<textarea>����ʾ�Ǳ��ָ�ʽ
			news.setContent(news.getContent().replaceAll("<p>", "\r\n"));
			//����ѯ����������Ϣ���õ�request������
			request.setAttribute("data", news);
			//ת�����޸�����ҳ��
			request.getRequestDispatcher("../manage/updateNews.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("msg", e.getMessage());
			this.error(request, response);
		}
	}
	
	//����ҳ��
	public void error(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ת����������ʾҳ��
		request.getRequestDispatcher("../manage/error.jsp").forward(request, response);
	}
}
