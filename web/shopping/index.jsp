<%@ page language="java" import="wingsoft.shopping.dao.UsersDAO,wingsoft.shopping.util.DBManager, wingsoft.tool.common.MyDes" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (request.getParameter("token") != null) {
		MyDes des = new MyDes();
		String token = des.decrypt(request.getParameter("token").toString());
		String [] user_info = token.split("\\|");
		DBManager.setPid(user_info[0]);
		String userid = user_info[1];
		String username = user_info[2];
		UsersDAO ud = new UsersDAO();
		if (ud.selectUser(userid)=="") {
			ud.saveUser(userid, username);
		}
		request.getSession().setAttribute("userId", userid);
		request.getSession().setAttribute("userName", username);
	}
	
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
 %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="core/jquery-1.4.4.js"></script>
    <script type="text/javascript" src="core/control.js"></script>
    <script type="text/javascript" src="page/homepage/control.js"></script>
<script type="text/javascript">
projID = "SHOP";//'<%=DBManager.getPid()%>';
</script>
    <title>主页
    </title>
    <style></style>
  </head>
  <body>

<%@include file="/shopping/bar/head.jsp"%>
<%@include file="/shopping/bar/left.jsp"%>
  </body>
</html>