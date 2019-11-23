<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*,java.lang.*,com.sec.news.model.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 5 Transitional//EN">
<html>
<head>
<title>欢迎界面</title>
<%
	 User user = (User)request.getSession().getAttribute("user");
	 
	 if(user == null)
	 {
	 	response.sendRedirect("/manage/login.jsp");
	 	return;
	 }
	 
	 String userName = user.getUserName();
	 
	 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
	 String time = sd.format(new Date(request.getSession().getCreationTime()));
	 
	 String IP = request.getRemoteAddr();
 %>
<style type="text/css">
		body
		{
			width:1300px;
		}
    	a{
    		margin-top:4px;
    		float:right;
    		display: block;
    		text-decoration: none;
			
    	}
    	#logo_right
    	{
    		margin-right:5px;
    		float:right;
    		width:60px;
			height:75px;
    	}
		
		#main
		{
			float:left;
			height:600px;
			width:1300px;
		}
		#main_left
		{
			width:220px;
			float:left;
			height:600px;
		}
		#main_right
		{
			width:1000px;
			height:600px;
			float:left;	
		}
		p{
			padding-top:0px;
    		margin-top:0px;
    		font-size: 12px;
			
		}
		header
		{
			width:1300px;
			height:80px;
		}
		header img
		{
			float:left;
		}
		iframe
		{
			float:left;
			width:1000px;
			height:100%;
			
		}
		footer
		{
			float:left;
			text-align:center;
			background-image:url("img/middle.png");
			width:1300px;
			height:25px;
		}
		#usersInfo
		{
			width:100%;
			height:150px;
		}
		#newsInfo
		{
			width:100%;
			height:200px;
		}
		#frontInfo
		{
			width:100%;
			height:250px;
		}
		#title{
			text-align:center;
			font-size:20px;
			background-color:#FF9;
		}
		#context
		{
			margin-top:2px;
			background-image: url("img/macback.png");
		}
		.div_right{
    		float: left;
    	}
    	
		li
		{
			display:block;
			list-style:none;
		}
		li a
		{
			float:none;
			font-size:16px;
		}
    </style>
</head>
<body>
	<!--页头-->
    <header>
        <img src="/news/logo.png" width="400" height="75"/>
        <div id="logo_right">
            <a style="cursor: pointer;" href="manage/login.jsp">[首 页]</a>
            <a style="cursor: pointer;" href="#">[前 台]</a>
            <a style="cursor: pointer;" href="../servlet/UserServlet?op=logout">[退 出]</a>
        </div>
     </header>
     <!--版心-->
     <div id="main">
         <div id="main_left">
            <div id="usersInfo">
                <div id="title">用户信息中心</div>
                <div id="context">
                	<p>用户名:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=userName %></p>
                	<p>登录时间:&nbsp;&nbsp;<%=time %></p>
                	<p>登录IP:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=IP %></p>
                    <div id="div_right">
                        <a href="../servlet/UserServlet?op=logout">[注 销]</a>
                        <a href="/news/manage/updateUser.jsp" target="iframe">[修改密码]</a>
                    </div>	
                </div>
            </div>
            <div id="newsInfo">
                <div id="title">新闻管理中心</div>
                <div id="context">
                <ul>
                    <li><a href="addNews.jsp" target="iframe">发布新闻</a><br/></li>
                    <li><a href="../servlet/NewServlet?op=query&jsp=../manage/news.jsp" target="iframe">新闻管理</a><br/></li>
                    <li><a href="addType.jsp" target="iframe">添加新闻类型</a><br/></li>
                    <li><a href="../servlet/TypeServlet?op=query" target="iframe">新闻类型管理</a><br/></li>
                </ul>
                </div>
            </div>
            <div id="frontInfo">
                <div id="title">后台管理中心</div>
                <div id="context">
                <ul>
                    <li><a href="addNews.jsp" target="iframe">关于系统</a><br/></li>
                    <li><a href="news.jsp" target="iframe">关于用户</a><br/></li>
                    <li><a href="addType.jsp" target="iframe">关于新闻类型管理</a><br/></li>
                    <li><a href="type.jsp" target="iframe">关于新闻管理</a><br/></li>
                    <li><a href="addType.jsp" target="iframe">关于浏览新闻</a><br/></li>
                </ul>
                </div>
            </div>
         </div>
         <div id="main_right">
         	<iframe src="../manage/welcome.jsp" scrolling="no" name="iframe"></iframe>
         </div>
     </div>
    <!--尾眉-->
    <footer>
    	<p>校园新闻网&copy;copy 2008-2009</p>
    </footer>
</body>
</html>
