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
    <script type="text/javascript" src="core/jquery.fly.min.js"></script> 
    <script type="text/javascript" src="core/control.js"></script>
    <script type="text/javascript" src="page/searchpage/control.js"></script>
        <script type="text/javascript">
projID = '<%=projid%>';
</script> 
    <title>复翼集中采购平台
    </title>
    <style>
	
/*弹框*/
/*弹框*/
.Bomb-box{width:240px;height:40px;position:absolute;z-index:1000;background:red;border-radius: 4px; font-size:14px;}
 
	</style>
  </head>
  <body>
<%@include file="/shopping/bar/head.jsp"%>
<%@include file="/shopping/bar/left.jsp"%>
		<!--新增收货地址先隐藏-->
		<div id="Idiv" style="display:none;text-align:center;" class="Bomb-box">
		   <span style=" text-align:center;color:#ffffff ;  line-height: 40px;">添加购物车成功</span>
		 </div>
		<!--新增收货地址先隐藏-->
  </body>
</html>