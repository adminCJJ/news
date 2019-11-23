<%@page import="org.omg.CORBA.Request"%>
<%@page import="com.sec.news.dao.TypeDao"%>
<%@ page language="java" import="java.util.*,com.sec.news.model.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新闻管理</title>
    <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "../manage/manage.jsp";
    	}	
    	function VailLen(obj)
    	{
    		if(obj.value.length >= 6)
    		{
    			obj.focus();
    			return true;
    		}
    		return false;
    	}
    	function change(obj)
    	{
    		window.location = "/news/servlet/NewServlet?op=query&jsp=../manage/news.jsp&pageNo=1&IfselectAll="+obj.value;
    	}
    	
    	function click1()
    	{
    		window.location= "../manage/addNews.jsp";
    	}
    </script>
    <style type="text/css">
    	.right{
    		text-align: right;
    		margin-right: 5px;
    	}
    	td
    	{
    		text-align: center;
    	}
    </style>
    <%
    	//获取查询好的集合信息
    	PageModel<News> pm = (PageModel<News>)request.getAttribute("data");
    	if(pm == null)
    	{
    		pm = new PageModel<News>();
    	}
     %>
     <%!
     	public String queryURL(int pageNo,int Allpages)
     	{
     		String base = "../servlet/NewServlet?op=query&jsp=../manage/news.jsp&pageNo=";
     		if(pageNo <= 1)
     		{
     			pageNo = 1;
     		}
     		else if(pageNo >= Allpages)
     		{
     			pageNo = Allpages;
     		}
     		return base+pageNo;
      	}
      	
      	public String priviousPages()
     	{
     		String base = "../servlet/NewServlet?op=query&jsp=../manage/news.jsp&pageNo="; 
     		return base + 1;
      	}
      	
      	public String lastPage(int last)
      	{
      		String base = "NewServlet?op=query&jsp=../manage/news.jsp&pageNo="; 
     		return base + last;
      	}
      	public String update(int newsId)
      	{
      		return "NewServlet?op=update&newsId="+newsId;
      	}
      	
		public String delete(int newsId)
		{
			return "../servlet/NewServlet?op=delete&jsp=../manage/news.jsp&delete="+newsId;
		}      	
      %>
  </head>
  <body>
    	<table width="800px" border="1px">
    		<tr>
    			<th style="font-size: 18px; text-align: left">新闻管理</th>
    			<td colspan="9" class="right">
    				新闻类型：&nbsp;&nbsp;
    				<select onchange="change(this)">
    					<option value="0">所有新闻</option>
    					<%
    					TypeDao typeDao = new TypeDao();
    					ArrayList<Type> lst = typeDao.select();
    					for(Type list:lst)
    					{%>
		    				<option value="<%=list.getTypeId()%>" ><%=list.getTypeName() %></option>
    				  <%}%>
    				</select>
    				&nbsp;&nbsp;
    				<input type="button" value="发布新闻" onclick="click1()"></input>&nbsp;&nbsp;
    				<input type="button" value="返回" onclick="returnMenu()"></input>
    			</td>
    		</tr>
    		<tr>
    			<td>编号</td>
    			<td>标题</td>
    			<td>类型</td>
    			<td>点击次数</td>
    			<td>正文大小</td>
    			<td>发布时间</td>
    			<td>推荐</td>
    			<td>关键字</td>
    			<td>发布者</td>
    			<td>操作</td>
    		</tr>
    		<%
    			for(News news: pm.getPage())
    			{%>
    				<tr>
    				<td><%=news.getNewsId() %></td>
    				<td><%=news.getTitle() %></td>
    				<td><%=news.getType().getTypeName() %></td>
    				<td><%=news.getClick() %></td>
    				<td><%=news.getContent().getBytes().length %></td>
    				<td><%=news.getReleaseTime() %></td>
    				<td><%=(news.getRecommended() == 1 ?"是":"否")%></td>
    				<td><%=news.getKeywords() %></td>
    				<td><%=news.getUser().getUserName() %></td>
    				<td>
    					<a href="<%=delete(news.getNewsId())%>">[删除]</a>
    					<a href="<%=update(news.getNewsId())%>">[修改]</a>
    				</td>
    				</tr>	
    		  <%}	
    		 %>
    		<tr>
    			<td colspan="10">
    				共<%=pm.getCountData() %>条数据，
    				每页<%=pm.getPageSize() %>条，
    				共<%=pm.getTotalPages() %>页，
    				当前<%=pm.getPageNo() %>页 [
    				<a href="<%=priviousPages()%>">首 页 &nbsp; </a>
    				<a href="<%=queryURL(pm.getPageNo()-1,pm.getTotalPages())%>">上 一 页&nbsp;  </a>
    				<a href="<%=queryURL(pm.getPageNo()+1,pm.getTotalPages())%>">下 一 页 &nbsp;  </a>
    				<a href="<%=lastPage(pm.getTotalPages())%>">尾 页</a>]
    			</td>
    		</tr>
    	</table>
  </body>
</html>
