<%@page import="com.sec.news.dao.TypeDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加新闻类型</title>
    <%@include file="Msg.jsp"%>
    <script type="text/javascript" src="js/ajax.js" charset="utf-8"></script>
     <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "type.jsp";
    	}	
    	
    	/**
    	*提交时的验证
    	*/
    	function Submit()
    	{
    		var tip = document.getElementById("tips");
    		var typeName = document.getElementsByName("typeName")[0];
    		var remark = document.getElementsByName("remark")[0];
    		if(typeName.value == null || typeName.value.length == 0)
    		{
    			tip.innerHTML = "类型名称不能为空！！！";
    			typeName.focus();
    			return false;
    		}else if(remark.value == null || remark.value.length == 0)
    		{
    			tip.innerHTML = "备注不能为空！！！";
    			typeName.focus();
    			return false;
    		}
    		return true;
    	}
    </script>
  </head>
  
  <body>
    <form action="../servlet/TypeServlet" method="post" onsubmit="return Submit()">
    	<table width="100%">
    		<tr>
    			<th colspan="2" style="font-size: 18px; text-align: left">新增新闻类型</th>
    			<td class="right"><input type="button" value="返回" onclick="returnMenu()"></input></td>
    		</tr>
    		<tr>
    			<td width="100px">类型名称：</td>
    			<td><input type="text" name = "typeName"/></td>
    		</tr>
    		<tr>
    			<td>类型备注：</td>
    			<td>
    				<textarea rows="5px" cols="60px" name="remark"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2" id = "tips"><%= msg%></td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center">
    				<input type="submit" value="提交"/>&nbsp;&nbsp;
    				<input type="reset" value="重置"/>
    				<input type="hidden" value="add" name="op">
    			</td>
    		</tr>
    	</table>
    </form>

  </body>
</html>
