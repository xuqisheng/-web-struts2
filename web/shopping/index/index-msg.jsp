<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>消息察看</title>
		<link href="css/index.css" type="text/css" rel="stylesheet">
		<link href="css/reveal.css" type="text/css" rel="stylesheet">
		<link href="css/index-dealerinfor.css" type="text/css" rel="stylesheet">
	<script src="js/jquery-1.11.3.min.js" ></script>
		 <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script> 
	<script type="text/javascript" src="js/jquery.reveal.js"></script> 
 
	</script>  
	  
  <script>
		$(function() { 
		var mid = GetUrlString("mid"); 
		var ind = 0;
 $.ajax({  
	      type: "POST",
		  url: 'MSG_GetMsg.action',
		  dataType: "json",
		  data:{mid:mid},
		  success: function( data ){
			  ind = data.json.indexOf("#@#@");
		 		$("#msgtitle").append(data.json.substring(0,ind));
		 		$("#msginfo").append(data.json.substring(ind+4));
		  }
		 });
		 
		}
		);
		</script>
  </SCRIPT>
		
	</head>
	<body id="top">
		
		<!-----------------------------------------顶部-------------->
		
		<%@include file="/shopping/bar/left.jsp"%>
		<%@include file="/shopping/bar/head.jsp"%>
		
		<div class="w">
			<div class="logo"><a href=""><img src="./images/logo.png"></a></div>
			<div class="search">
				<input type="text" value="搜索" class="text" id="textt">
				<button class="button">搜索</button>
			</div>
			<div class="right">
				<i class="gw-left" style="background:url(./images/jd2015img.png)0 -58px no-repeat;width:24px;height:24px;"></i>
				<i class="gw-right">></i>
				<i class="gw-count" id="itemsnum">0</i>
				<a id="carts">我的购物车</a>
			
				<div class="dorpdown-layer" id = "maincarts">
				


				</div>
			</div>
			
		</div>
		
		<div class="clear"></div>
		<!--轮播图上方导航栏  一栏-->
		<div id="navv">
			<div class="nav-img" style="border-bottom: 2px solid #C81623;"></div>
			<!--<div class="nav-imgs" style="background:url(./images/568a0a8eNe8f4df82.jpg) no-repeat center top"></div>-->	
		</div>
		<div class="focus">
			<div class="focus-a">
				<div class="fouc-img1"><img src="./images/5689d4ebN19f155a6.jpg" class="nav-img2"></div>
				<div class="fouc-font"><a href="">全部商品分类</a> </div>
			</div>
			<a href="#" class="big-link" data-reveal-id="myModal" data-animation="fade">
 
		</a>

		<%@include file="/shopping/bar/menu.jsp"%>
			<!--<div class="focus-c"><a href=""><img src="./images/hhh.jpg"></a></div>
			<div class="focus-d"><a href=""><img src="./images/nianhuo.jpg"></a></div>-->
			<!--轮播图左边当行蓝-->
			<div class="bb"></div>
			<div class="dd-inner" style="display:none;">
				
				
			</div>
		    <div class="dealer-infor">
		    	<div class="crumb" style="text-align:center"> 
		    		<h3 id="msgtitle"></h3>
		    	</div>
		    	<div id="msginfo"></div>

		    	
		    </div>
			
			
		
		<!--*****************轮播下方*****************-->
		
		<div id="footer-2014" >
		<div class="links">
			<a rel="nofollow" target="_blank" href="#">关于我们</a>|<a  href="#">联系我们</a>|<a rel="nofollow" target="_blank" href="#">商家入驻</a>|<a target="_blank" href="#">友情链接</a></div>
		
	  </div>
	</div>


		<div id="myModal" class="reveal-modal">
		       <h3 id="want_product">反馈</h3>
              
			<div class="table-style">
			<table border="0" cellspacing="0" cellpadding="0">
			    <thead>
				     <tr style="background:#f5f5dd;">
				       <!-- <td class="table-tr">原请购单编号</td>-->
				      <!--  <td class="table-tr">供应商反馈编号，主键</td>-->
				        <td class="table-tr">反馈信息</td>
				        <td class="table-tr">反馈单价</td>
				        <!--<td class="table-tr">反馈状态 A:正常 D：撤销</td>-->
				        <td class="table-tr">反馈人员</td>
				        <td class="table-tr">反馈供应商</td>
				        <td class="table-tr">联系方式</td>
				        <td class="table-tr">操作</td>
				     </tr>
			    </thead>
				<tbody id="reqbody">
				     <tr> 
				        <td>ddddddd</td>
						<td>aaaaaaa</td>
				        <td>bbbbbbb</td>
				        <td>ccccccc</td>
				        <td>ddddddd</td>
				     </tr> 
					
				</tbody>
				      
				  </table>
               </div>
			<a class="close-reveal-modal">&#215;</a>

		</div>

	</body>
	<!-- <script>
         $(".tcdPageCode").createPage({
	        pageCount:6,
	        current:1,
	        backFn:function(p){
	            console.log(p);
	        }
	    });
    </script>-->
</html>