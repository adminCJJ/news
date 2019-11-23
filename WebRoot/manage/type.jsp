<%@page import="com.sec.news.model.PageModel"%>
<%@ page language="java" import="java.util.*,com.sec.news.model.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新闻类型管理</title>
     <script type="text/javascript">
    	function returnMenu()
    	{
    		window.location.href = "../manage/welcome.jsp";
    	}	
    	function addType()
    	{
    		window.location.href = "../manage/addType.jsp";
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
	<table width="600px" border="1px">
		<tr>
			<th colspan="3" style="font-size: 18px; text-align: left">新增新闻类型</th>
			<td class="right">
				<input type="button" value="新增类型" onclick="addType()" />
				<input type="button" value="返回" onclick="returnMenu()" />
			</td>
		</tr>
		<tr>
			<td width="40px">编号</td>
			<td width="80px">新闻类型</td>
			<td width="200px">备注</td>
			<td width="280px">操作</td>
		</tr>
		<%!
			private String QueryURL(int pageNo)
			{
				String basePath = "../servlet/TypeServlet?op=query&pageNo=";
				
				return basePath+pageNo;
			}
			private String QueryURL(int pageNo,int total)
			{
				String basePath = "../servlet/TypeServlet?op=query&pageNo=";
				if(pageNo<=1)
				{
					pageNo = 1;
				}
				else if(pageNo >= total)
				{
					pageNo = total;
				}
				return basePath+pageNo;
			}
			private String deleteURL(int pageNo)
			{
				String basePath = "../servlet/TypeServlet?op=delete&typeId=";
				
				return basePath+pageNo;
			}
			private String updateURL(int typeId)
			{
				String basePath = "../manage/updateType.jsp?op=update&typeId=";
				
				return basePath+typeId;
			}
		 %>
		<%
			 PageModel<Type> pm = (PageModel<Type>)request.getAttribute("data");
			 for(int i = 0;i< pm.getPage().size();i++)
			 {%>
				<tr>
				<%--编号--%>
				<td><%=pm.get(i).getTypeId() %></td>
				<%--新闻类型 --%>
				<td><%=pm.get(i).getTypeName() %></td>
				<td><%=pm.get(i).getRemark() %></td>
				<td>
					<%--管理新闻：查询出该类型对应的新闻 --%> 
					<a href="#">[管 理 新 闻]</a> 
					<%--删除类型 --%> 
					<a href="<%=deleteURL(pm.get(i).getTypeId())%>">[删 除]</a> 
					<%--修改类型 --%> 
					<a href="<%=updateURL(pm.get(i).getTypeId()) %>">[修 改]</a>
				</td>
				</tr>
			<%}%>
		<tr>
			<td colspan="4" align="center">
				共有 <%=pm.getCountData()%>条数据，
				每页<%=pm.getPageSize() %>条，
				共<%=pm.getTotalPages() %>页 ,
				当前第<%=pm.getPageNo() %>页 &nbsp; 
				[<a href="<%=QueryURL(1)%>">首页</a>
				 <a href="<%=QueryURL(pm.getPageNo()+1,pm.getTotalPages())%>">下一页</a> 
				 <a href="<%=QueryURL(pm.getPageNo()-1,pm.getTotalPages())%>">上一页</a> 
				 <a href="<%=QueryURL(pm.getTotalPages())%>">尾页</a>]

			</td>
		</tr>
	</table>
</body>
</html>
