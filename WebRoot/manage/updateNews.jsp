<%@page import="com.sec.news.dao.NewDao"%>
<%@ page import="com.sec.news.dao.TypeDao"%>
<%@ page language="java" import="java.util.*,com.sec.news.model.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改新闻</title>
    <%
    	News news = (News)request.getAttribute("data");
    	news = (news == null)?new News():(News)request.getAttribute("data");
    	int newsId = news.getNewsId();
    	request.setAttribute("data", news);
     %>
    <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "/news/servlet/NewServlet?op=query&jsp=../manage/news.jsp";
    	}	
    	
    	function VailLen(obj)
    	{
    		if(obj.value.length <= 0)
    		{
    			obj.focus();
    			return true;
    		}
    		return false;
    	}
    	
    	function Submt()
    	{
    		var title = document.getElementByName("title")[0];
    		var contenet = document.getElementsByName("content")[0];

    		if(Vailen(title))
    		{
    			alert("标题不能为空");
    			return false;
    		}
    		else if(Vailean(contenet))
    		{
    			alert("新闻内容不能为空");
    			return false;
    		}
    		else {
    			return  true;
    		}
    	}
    	
    	function fun1()
    	{
    		window.location = "../servlet/NewServlet?op=preview&newsId="+<%=newsId%>;
    	}
    	
    	function preview()
    	{
    		var obj = document.getElementsByName("op")[0];
    		obj.value = "preview";
    	}
    </script>
    
    <style type="text/css">
    	.right{
    		text-align: right;
    		margin-right: 5px;
    	}
    </style>
  </head>
  
  <body>
    <form action="../servlet/NewServlet" method="post" onsubmit="return Submt()">
    	<table height="5px" border="0px">
    		<tr>
    			<th style="font-size: 18px; text-align: left">修改新闻</th>
    			<td class="right"><input type="button" value="返回" onclick="returnMenu()"></input></td>
    		</tr>
    		<tr>
    			<td colspan="2">
    			新闻编号：<%=news.getNewsId() %> 
    			发布时间：<%=news.getReleaseTime() %> 
    			点击次数:<%=news.getClick() %> 
    			创建者:<%=news.getUser().getUserName() %>
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">新闻标题:</td>
    			<td><input type="text" name = "title" size="50" value="<%=news.getTitle() %>"/></td>
    		</tr>
    		<tr>
    			<td>
    				新闻类型：
    			</td>
    			<td>
	    			<select name="typeId">
	    				<%
						TypeDao typeDao = new TypeDao();    					
	    				ArrayList<Type> lst = typeDao.select();
	    				for(Type list:lst)
	    				{
	    					if(list.getTypeId() == news.getNewsId())
	    					{%>
	    						<option value="<%=list.getTypeId()%>" selected="selected"><%=list.getTypeName() %></option>
	    					<%}else%>
	    				   <%{%>
	    						<option value="<%=list.getTypeId()%>"><%=list.getTypeName() %></option>
	    				   <%}%>
	    					
	    			  <%}%>
	    			</select>
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">是否推荐该新闻:</td>
    			<td>
    				<%
    					if(news.getRecommended() == 1)
    					{%>
    						 <input type="radio" name = "recommended" value = "1" checked="checked"/>推荐
    				 		<input type="radio" name = "recommended" value = "0"/>不推荐
    				           <%}
    					else
    					{%>
    						<input type="radio" name = "recommended" value = "1"/>推荐
    				 		<input type="radio" name = "recommended" value = "0"  checked="checked"/>不推荐
    				           <%}
    				 %>
    				
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">新闻正文:</td>
    			<td>
    				<textarea rows="10px" cols="50px" name="content"><%=news.getContent() %></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">关键字:</td>
    			<td>
    				<input type="text" name="keywords" value="<%=news.getKeywords()%>"/>
    			</td>
    		</tr>
    		<tr  style="text-align: center;">
    			<td colspan="3">
    				<input type="submit" value="提交"/> 
    				<input type="reset" value="重置"/> 
    				<input type="submit" value="预览" onclick="preview()"/>
    				<input type="hidden" value="update" name="op"> 
    				<input type="hidden" value="<%=newsId %>" name="newsId"/>
    				<input type="hidden" value="../manage/news.jsp" name="jsp"> 
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
