<%@ page language="java" import="wingsoft.shopping.dao.UsersDAO" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String itemid = request.getParameter("itemid");
 %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  </head>
  <body>
	<div style="width:1000px">
		<img id="pg-img" width="1000" src="<%=itemid %>/post-<%=itemid %>.jpg" onerror="this.src='default.jpg'"/>
	</div>
  </body>
</html>