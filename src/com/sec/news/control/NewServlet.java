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
		
		//请求和响应编码为utf-8
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//得到op参数的值
		String op = request.getParameter("op");
		//判断op的值，并调用相应的方法
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
//			System.out.println("修改");
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
	 * 添加业务逻辑处理
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取表单数据
		String title = request.getParameter("title");
		int recommended = 0;
		int typeId = 0;
		String content = request.getParameter("content");
		String keywords = request.getParameter("keywords");
		try {
			typeId = Integer.parseInt(request.getParameter("typeId"));
			recommended = Integer.parseInt(request.getParameter("recommended"));
			//获得Session中保存的用户信息
			User user = (User)request.getSession().getAttribute("user");
			Type type = new Type(typeId);
			//设置新闻信息
			News news = new News();
			news.setType(type);
			news.setUser(user);
			news.setTitle(title);
			
			//替换新闻内容中的转义字符，方便格式的保持
			news.setContent(content.replace("\r\n", "<p/>"));
			news.setRecommended(recommended);
			news.setKeywords(keywords);
			//调用数据访问类的方法插入数据
			if(this.dao.insert(news) > 0 )
			{
				//插入成功，查询最新数据
				this.query(request, response);
			}else{
				request.setAttribute("msg", "发布新闻失败，可能是数据库错误！");
				this.error(request, response);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			//异常处理
			request.setAttribute("msg", e.getMessage()+"出现了不可描述的异常");
			this.error(request, response);
		}
	}
	
	/**
	 * 删除业务
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int newsId = 0;
		//获取要删除的新闻编号
		try{newsId = Integer.parseInt(request.getParameter("delete"));}
		catch(Exception e){};
		//调用数据访问类的方法插入数据
		if(this.dao.delete(newsId) > 0 )
		{
			//插入成功，查询最新数据
			this.query(request, response);
		}else{
			request.setAttribute("msg", "删除失败，可能是数据库错误！");
			this.error(request, response);
		}
	}
	
	/**
	 * 更新业务
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
//	private void update(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//	 	//获取值
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
//	 	//设置值
//	 	news.setNewsId(newsId);
//	 	news.setTitle(title);
//	 	news.setType(type);
//	 	news.setRecommended(recommended);
//	 	news.setContent(content);
//	 	news.setKeywords(keywords);
//	 	
//	 	//调用数据访问类的方法插入数据
//		if(this.dao.update(news) > 0)
//		{
//			System.out.println("修改成功");
//			//插入成功，查询最新数据
//			this.query(request, response);
//		}else{
//			request.setAttribute("msg", "删除失败，可能是数据库错误！");
//			this.error(request, response);
//		}
//	}
	
	/**
	 * 预览业务(类似修改)
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
			//修改新闻时预览newId不为空
			if(request.getParameter("newsId") != null)
			{
				newsId = Integer.parseInt(request.getParameter("newsId"));
				news = new News(newsId);
				this.dao.SelectNewsId(news);
			}
			else
			{
				//发布新闻时的预览
				User user =(User) request.getSession().getAttribute("user");
				news.setUser(user);
			}
			//设置新闻类型
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

			//将新闻信息设置到request对象中
			request.setAttribute("data", news);
			//转发到预览页面
			request.getRequestDispatcher("../manage/preview.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("msg", e.getMessage());
			this.error(request, response);
		}
	}
	
	/**
	 * 查询新闻业务
	 */
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNo = 1;//页面，初始化为1
		int pageSize = 6;//每页数据条数，初始化为6
		int typeId = 0;//类型编号
		//类型转换
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
			//判断是否根据新闻类型查询
			if(typeId == 0)
			{
				int countAllData = this.dao.select(pm);//查询所有新闻
				pm.setCountData(countAllData);
			}else {
				int countAllData = this.dao.select(pm,typeId);//查询指定类型的新闻
				pm.setCountData(countAllData);
			}
			//将PageModel对象存入request对象中
			request.setAttribute("data", pm);
			//要转发的jsp页面
			String jsp = request.getParameter("jsp");
			//转发到指定页面
			request.getRequestDispatcher(jsp).forward(request, response);
		} catch (Exception e) {
			//设置异常信息到request中
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			
			//转发到错误页面
			this.error(request, response);
		}
	}
	
	/**
	 * 查询（预览）业务
	 */
	private void show_update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int newsId = 0;
		//类型转换
		try {
			//得到被修改新闻的编号
			newsId = Integer.parseInt(request.getParameter("newsId"));
			News news = new News(newsId);
			//调用业务访问类的方法，查询新闻信息
			this.dao.SelectNewsId(news);
			
			//将段落标签<p>转换为\r\n方便在<textarea>中显示是保持格式
			news.setContent(news.getContent().replaceAll("<p>", "\r\n"));
			//将查询出的新闻信息设置到request属性中
			request.setAttribute("data", news);
			//转发到修改新闻页面
			request.getRequestDispatcher("../manage/updateNews.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			request.setAttribute("msg", e.getMessage());
			this.error(request, response);
		}
	}
	
	//错误页面
	public void error(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//转发到错误提示页面
		request.getRequestDispatcher("../manage/error.jsp").forward(request, response);
	}
}
