<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Logo页面</title>
    <style type="text/css">
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
    		width:60;
    	}
    </style>
  </head>
  	
  <body>
  	<img src="logo.png" width="400" height="75"/>
  	<div id="logo_right">
	   	<a style="cursor: pointer;" href="manage/login.jsp">[首 页]</a>
	   	<a style="cursor: pointer;">[前 台]</a>
	   	<a style="cursor: pointer;">[退 出]</a>
   	</div>
  </body>
</html>
