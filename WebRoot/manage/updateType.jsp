<%@page import="com.sec.news.dao.TypeDao"%>
<%@ page language="java" import="java.util.*,com.sec.news.model.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改新闻类型</title>
     <%--引入提示用户的信息页s --%>
     <%@include file="Msg.jsp"%>
     <%
    	int typeId = 0;
   		
   		try{
   			//获取类型编号
    		typeId =Integer.parseInt(request.getParameter("typeId"));
    	}catch(Exception e){typeId = 0;}
    	
    	TypeDao typedao = new TypeDao();
    	//查询该编号的信息
    	Type type = typedao.select(typeId);
     %>
     <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "welcome.jsp";
    	}	
    	
    	/**
    	*提交时的验证
    	*/
    	function Submit()
    	{
    		var tip = document.getElementById("tips");
    		var typeName = document.getElementsByName("typeName")[0];
    		if(typeName.value == null)
    		{
    			tip.innerHTML = "类型名称不能为空！！！";
    			typeName.focus();
    			return false;
    		}
    		return true;
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
    <form action="../servlet/TypeServlet" method="post" onsubmit="return Submit()">
    	<table width="100%">
    		<tr>
    			<th colspan="2" style="font-size: 18px; text-align: left">修改新闻类型</th>
    			<td class="right">
    				<input type="button" value="返回" onclick="returnMenu()"/>
   				</td>
    		</tr>
    		<tr>
    			<td width="100px">类型编号：</td>
    			<td>
    				<input type="text" name="typeId" readonly="readonly" value="<%=type.getTypeId()%>"/>
    			</td>
    		</tr>
    		<tr>
    			<td width="100px">类型名称：</td>
    			<td><input type="text" name = "typeName" value="<%=type.getTypeName()%>"/></td>
    		</tr>
    		<tr>
    			<td>类型备注：</td>
    			<td>
    				<textarea rows="5px" cols="80px" name="remark"><%=type.getRemark()%></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2" id = "tips"><%=msg%></td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center">
    				<input type="submit" value="提交"/>&nbsp;&nbsp;
    				<input type="reset" value="重置"/>
    				<input type="hidden" value="update" name="op">
    			</td>
    		</tr>
    	</table>
    </form>

  </body>
</html>
