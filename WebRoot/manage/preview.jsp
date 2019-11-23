<%@page import="com.sec.news.model.News"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新闻预览</title>
    <%
    	News news = (News)request.getAttribute("data");
    	news = (news == null)?new News():(News)request.getAttribute("data");
    	int newsId = news.getNewsId();
     %>
    <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "/news/manage/updateNews.jsp";
    	}	
    	function font1()
    	{
    		document.getElementById("content").className = "font1";
    	}
    	function font2()
    	{
    		document.getElementById("content").className = "font2";
    	}
    	function font3()
    	{
    		document.getElementById("content").className = "font3";
    	}
    </script>
    <style type="text/css">
    	.right{
    		text-align: right;
    		margin-right: 5px;
    	}
    	.font1
    	{
    		font-size: 90px;
    		color: green;
    	}
    	.font2
    	{
    		font-size:40px;
    		color: red;
    	}
    	.font3
    	{
    		font-size:15px;
    		color: blue;
    	}
    </style>
  </head>
  
  <body>
    <table height="5px" border="0px" width="800px">
    	<tr>
    		<th style="font-size: 18px; text-align: left">新闻预览</th>
    		<td class="right"><input type="button" onclick="returnMenu()" value="返回"></td>
    	</tr>
    	<tr>
    		<td colspan="2" width="150px"><hr/></td>
    	</tr>
    	<tr style="text-align: center;">
    		<td colspan="2"><h1><%=news.getTitle() %></h1></td>
    	</tr>
    	<tr style="text-align: center;">
    		<td colspan="2">
    			类型:<%=news.getType().getTypeName()%>
    			作者：<%=news.getUser().getUserName()%>
    			发布时间：<%=news.getReleaseTime()%>
    			点击次数：<%=news.getClick()%>
    			[字体: <a href="javascript:font1()">大</a>  <a href="javascript:font2()">中</a>    <a href="javascript:font3()">小</a>]
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2" width="150px"><hr/></td>
    	</tr>
    	<tr>
    		<td colspan="2" width="150px" >
    			<div  id="content" class="font3"><%=news.getContent() %></div>
    		</td>
    	</tr>
    </table>
  </body>
</html>
