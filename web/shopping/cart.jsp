<%@ page language="java" import="wingsoft.shopping.dao.UsersDAO" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/*if (request.getSession().getAttribute("userId")==null) {
		response.sendRedirect("index.jsp");
	}*/
	String projid = request.getParameter("projid");
	if (projid == null)
		projid = "";
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="core/jquery-1.4.4.js"></script>
    <script type="text/javascript" src="core/control.js"></script>
    <script type="text/javascript" src="mainjs/cart.js"></script>
    <script type="text/javascript" src="page/cartpage/control.js"></script>
        <script type="text/javascript">
projID = '<%=projid%>';
</script>

    <title>购物车
    </title>
    <style></style>
  </head>
  <body>
<%@include file="/shopping/bar/head.jsp"%>
<%@include file="/shopping/bar/left.jsp"%>

  </body>
</html>