<%@ page language="java" isErrorPage="true" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发现错误了！！！</title>
  </head>
  <%
  		String msg = (String)request.getAttribute("msg");
   %>
  <body>
  	<p>错误提示：</p>
    <h1><%=msg%></h1><hr/>
  </body>
</html>
