<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="wingsoft.core.start.WebInitServlet"%>
<%if(session.getAttribute("userContextStr") == null){response.sendRedirect("login.html");return;}
String debugWFServerName = request.getParameter("debug");
if (!"json".equals(WebInitServlet.startMode) && debugWFServerName!=null) {
	WebInitServlet.wfServerName = debugWFServerName;
	WebInitServlet.prjId = debugWFServerName;
}
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/all.css"/> 
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/tree.css"/> 
<link rel="stylesheet" type="text/css" href="<%=basePath%>style/home.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>Customize/css/style.css" />

<script type="text/javascript" src="<%=basePath%>core/all.js"></script>
<script type="text/javascript">
loadAppJS('<%=WebInitServlet.appWinTypes%>');//加载js
</script>
<script type="text/javascript" src="<%=basePath%>img/new-img/lo.js"></script>

<title>复旦天翼</title>

</head>
<!-- 主菜单 -->
<div id="menuTree" style="width:100%;background:url('img/menu_back.png');display:none"></div>

</div>

    <td valign="top" >
	<div id="main" style="float:left;margin:4px 5px 0 0; ">
	   <ul id="pageBar">
       </ul>
    </div></td>
  </tr>

</div>

</body>

</html>