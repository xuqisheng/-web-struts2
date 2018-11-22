 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cartids = request.getParameter("cartids");
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <!DOCTYPE>
<html>
<head>
<meta charset="utf-8">
<title>结算页</title>
<link rel="stylesheet" href="css/cashier.css">
<script type="text/javascript" src="../core/jquery-1.4.4.js"></script> 
<script type="text/javascript" src="./core/cashier.js"></script> 
<script language="JavaScript" type="text/JavaScript">
$(function() { 
GetSuccessOrder(); 
}
);
</script>
<style type="text/css">
  *
  {
      padding:0px;
      margin:0px;
  }
  #Idiv
  {
      /*width:500px;
      height:auto;
      position:absolute;
      z-index:1000;
      border:2px solid #ffffff;
      background:#ffffff;*/
  }
   ul{list-style-type: none; }
   .pay-div{ margin-left:32px; font-size:12px;}
   #ul_list{height: 30px; margin-bottom: 22px;}
   #ul_list li{height:30px; line-height: 30px; padding: 0 16px; border: 1px solid #CC162C;float: left; margin-right: 3px; cursor: pointer;}
   .current{ background: #CC162C; color:#fff;}
   #two{display: none; margin:10px 0;}
   #two.showlist{ display: block;}
   .default-add{ float:right; margin-top:20px; height:30px; line-height:30px; cursor:pointer;}
  </style>

</head>
<body>
<%@include file="/shopping/bar/head.jsp"%>
<%@include file="/shopping/bar/left.jsp"%>



<div class="header-top">
		<div class="h-logo"><a href="#"><img src="./images/logo.png"/></a>
			<!-- <span class="h-title">复翼集中采购平台</span> -->
			<span class="w-title">收银台</span>
			<div class="stepflex">
				<dl class="first done">
			                      <dt class="s-num">1</dt>
			                      <dd class="s-text">1.我的购物车
			                      	<s></s>
			                      	<b></b>
			                      </dd>
				</dl>
				<dl class="normal doing">
				            <dt class="s-num">2</dt>
				            <dd class="s-text">2.填写核对订单信息
				            	<s></s>
				            	<b></b>
			                         </dd>

			             </dl>
			                <dl class="normal last">
				            <dt class="s-num">3</dt>
				            <dd class="s-text">3.成功提交订单
				            	<s></s>
				            	<b></b>
				            </dd>
			        </dl>
			</div>
		</div>
           </div>
	
	 <div class="container">
		 <div class="check-wrap">
	     <span class="check-icon"><img src="./images/check-alt.png"/></span>
		 <span class="check-title"><span class="check-tilte-color" id="paytitle">订单提交成功！我们将尽快安排为您发货！</span>
		 </br> </br> 
		 <div id="payinfo">
			 <!--<div>
				 <span>支付方式：微信&nbsp;&nbsp;&nbsp;&nbsp;支付订单金额：￥164.00</span>
				 &nbsp;&nbsp;&nbsp;&nbsp;<span><a href="#">查看订单</a></span>
			 </div> -->
		</div>
 
	     </div>
	</div>
 
	
</body>
<script type="text/javascript">
$(document).ready(function(){
  $("#add").click(function(){
  $("#addshow").toggle();
  });
  
});
</script>
<script>
      $('#ul_list li').click(function(){
          
          //1.点击li的时候要切换样式
          $(this).addClass('current').siblings().removeClass('current');
          //2.根据li的索引值,来确定哪个div显示.其它div隐藏
          $('#content_list>#two').eq($(this).index()).show().siblings().hide();

      });
    </script>

</html>