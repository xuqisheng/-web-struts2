<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><head>
		<meta charset="utf-8">
		<!--<link href="../index/css/index.css" type="text/css" rel="stylesheet">-->
		<link rel="stylesheet" href="<%=basePath%>shopping/bar/css/bar.css">
		<script src="<%=basePath%>shopping/mainjs/head.js" ></script>
		<script>
		
		</script>
	</head>
<header id="header">
			
<div class="header-box">
				<!--<ul class="header-left" id="group">
					<li class="bj">
						高分子实验 
						<i class="ci-leftll"> 
							<s class="jt">◇</s>
						</i>
						<div class="bj-1"> 
							<a href="">纳米结构研究课题组</a><a href="">半导体课题组</a>
						</div>
					</li>	
				</ul>-->
				<!--<a href="<%=basePath%>index-do.jsp" target="_blank"  class="red">进入管理系统</a>-->
				<a href="<%=basePath%>main.jsp" target="_blank"  class="red">进入管理系统</a>
				<ul class="header-right">
					<c:choose>
				   <c:when test="${userId==''||userId==null}">  
					<li class="denglu"><a href="<%=basePath%>shopping/login.jsp?returnurl=<%=url%>">您好，请登录</a><a href="#"  class="red">免费注册</a></li> 
					  </c:when>
					   <c:otherwise>
						<li class="denglu"><a >欢迎【${userName}】</a><a href="#"  class="red"></a></li> 
						 </c:otherwise>
					</c:choose>
					<li class="shu"></li>
					<li class="denglu"><a  href="<%=basePath%>shopping/index/index.jsp" target="_blank" >首页</a></li>
					<li class="denglu">  
					<a   href="<%=basePath%>main.jsp" target="_blank" >我的订单</a>
					<!--<a   href="<%=basePath%>index-do.jsp?menu1=订单管理&win=6206&projid=NYD" target="_blank" >我的订单</a>--> 
					<!--<a  href="<%=basePath%>shopping/order/order.jsp" target="_blank" >我的订单</a>-->
						</li>
					<li class="shu"></li>
					
					<li class="shu"></li>
					
					<li class="kehu bj">
						<a href="#">客户服务</a>
						<i class="ci-right ">
							<s class="jt">◇</s>
						</i>
						<div class="kehu1">
							<!--<h3 class="neirong2">客户</h3>-->
							<ul class="kehu2">
								<li><a href="">技术支持：400-400-400</a></li>
								<li><a href='tencent://message/?uin=1003695686'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;QQ:&nbsp;&nbsp;<img  src="<%=basePath%>shopping/bar/images/button_11.gif"></a></li>
								<li class="neirong2">国资处材料中心</li>
								<li><a href=""><img src="<%=basePath%>shopping/bar/images/headphones.png">&nbsp;&nbsp;021-55880000</a></li>
								<li><a href="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;021-55880000</a></li>
								 
							</ul>
							
						</div>
					</li>
					
					<li class="denglu"><a href="<%=basePath%>shopping/login_out.jsp"  class="red">退出</a></li>
					
				</ul>
			</div>
		</header>