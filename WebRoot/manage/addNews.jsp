<%@ page import="com.sec.news.dao.TypeDao"%>
<%@ page language="java" import="java.util.*,com.sec.news.model.*" pageEncoding="utf-8" errorPage="error.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>发布新闻</title>
    <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "welcome.jsp";
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
    		if(Vailean(contenet))
    		{
    			alert("新闻内容不能为空");
    			return false;
    		}
    		else {
    			falg =  true;
    		}
    		return false;
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
    			<th colspan="2" style="font-size: 18px; text-align: left">发布新闻</th>
    			<td class="right"><input type="button" value="返回" onclick="returnMenu()"></input></td>
    		</tr>
    		<tr>
    			<td width="150px">新闻标题:</td>
    			<td><input type="text" name = "title" size="50"/></td>
    		</tr>
    		<tr>
    			<td width="150px">是否推荐该新闻:</td>
    			<td>
    				<input type="radio" name = "recommended" value = "1" checked="checked"/>推荐
    				<input type="radio" name = "recommended" value = "0"/>不推荐
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">新闻类型:</td>
    			<td>
    				<select name="typeId">
    					<%
    					TypeDao typeDao = new TypeDao();
    					ArrayList<Type> lst = typeDao.select();
    					for(Type list:lst)
    					{%>
		    				<option value="<%=list.getTypeId()%>"><%=list.getTypeName() %></option>
    				  <%}%>
    				</select>
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">新闻正文:</td>
    			<td>
    				<textarea rows="10px" cols="50px" name="content">
    				
    				</textarea>
    			</td>
    		</tr>
    		<tr>
    			<td width="150px">关键字:</td>
    			<td>
    				<input type="text" size="50" name="keywords"/>关键字格式如：(软件、教育，财经)
    			</td>
    		</tr>
    		<tr  style="text-align: center;">
    			<td colspan="3">
    				<input type="submit" value="提交"/> 
    				<input type="reset" value="重置"/> 
    				<input type="button" value="预览"/>
    				<input type="hidden" value="add" name="op"> 
    				<input type="hidden" value="../manage/news.jsp" name="jsp">
    				<input type="hidden" value="0" name="IfselectAll"/>
    			</td>
    		</tr>
    	</table>
    </form>

  </body>
</html>
