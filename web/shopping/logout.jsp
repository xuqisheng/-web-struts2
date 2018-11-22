<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.flushBuffer();
session.removeAttribute("userContextStr");
session.removeAttribute("userId");
session.removeAttribute("roles");
session.removeAttribute("userName");
session.invalidate();
//out.print("<script language='javascript'> window.open('login.html','_top','fullscreen=1,toolbar=0,location=0,directories=0,status=0,menubar=0,resizable=0,top=10000,left=10000')</script>");
out.print("<script language='javascript'>window.location.href='login.html';</script>");

//window.open('login.html','_top','a'); 
//response.sendRedirect("login.html");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'logout.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="jquery/jquery-1.3.2.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
