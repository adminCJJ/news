<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新闻管理中心</title>
    <style type="text/css">
    	.title{
    		text-align: center;
    	}
    	.div_right{
    		float: right;
    	}
    	a{
    		margin-top:4px;
    		float:right;
    		display: block;
    		text-decoration: none;
    	}
    </style>
  </head>
  
  <body>
    <div>新闻管理中心</div>
    <div>
    	<a href="addNews.jsp" target="iframe">发布新闻</a>
    	<a href="news.jsp" target="iframe">新闻管理</a>
    	<a href="addType.jsp" target="iframe">添加新闻类型</a>
    	<a href="type.jsp" target="iframe">新闻类型管理</a>
    </div>
  </body>
</html>
