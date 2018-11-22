<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String items = request.getParameter("items");
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta charset="utf-8">
<title>订单管理</title>
<link rel="stylesheet" href="css/style.css">
	<script src="../mainjs/jquery-1.11.3.min.js" ></script>
	<script src="../mainjs/order.js" ></script>

</head>
<body>
		<%@include file="/shopping/bar/head.jsp"%>
		<%@include file="/shopping/bar/left.jsp"%>
		<div class="h-logo"><a href="#"><img src="./images/logo.png"/></a>
			
			<span class="w-title">我的订单</span>
			
		</div>
	<div class="container" id="orders">
	    <div class="mt">
	    	<ul class="extra-l">
	    		<li><a href="#" class="curr">全部订单</a></li>
	    		<li><a href="#">待付款</a></li>
	    		<li><a href="#">待收货</a></li>
                                        <div class="extra-r">
                                            <div class="search">
                                    <input id="ip_keyword" type="text" class="itxt" value="商品名称/商品编号/订单号" style="color: rgb(204, 204, 204);">
                                  <a href="javascript:;" class="search-btn" clstag="click|keycount|orderinfo|search">搜索<b></b></a>
                                   
                                </div>
                                        </div>
	    		<div class="clear"></div>
	    	</ul>
	    </div>
        <div class="div-top"><div class="time-txt">近三个月订单<b></b><span class="blank"></span></div>
        <div class="order-detail-txt ac">订单详情</div>
        <div class="order-detail-txt as">收货人</div>
        <div class="order-detail-txt as">订单金额</div>
        <div class="order-detail-txt zt">状态</div>
        <div class="order-detail-txt zt">操作</div>
        <div class="clear"></div>
    </div>
                <!-- <div class="order-info">
                 	<div class="info-div"><span class="dealtime">2017-03-14 10:00:36</span><span class="number">订单号： </span><a href="#" class="num">51086951433</a><span class="operator">运营商:京东</span><span class="pay">支付方式:在线支付</span><span class="order-details"><a href="#">订单详情></a></span></div>
                 	<ul class="details">
                 		<li class="details-list">
                 	                 <div class="d-left"><img src="./images/5.jpg"/></div>
                 	                 <div class="d-right">
                 	                 	<span class="list-column">卡西欧（CASIO）手表EDIFICE系列男士石英表EFR-553L-7BVUPR</span>
                 	                 	<span class="list-column list-align">x1</span><span class="list-column">收货人:吴斌 <span class="order-amount">金额:<span class="tot-color">¥114.80</span></span></span><span class="list-column text-align">申请售后<span class="order-amount">已完成</span></span>

                                          </div>
                                          <div class="evaluate"><span class="evaluate-txt"><a href="#">评价</a></span><br><span class="buy-now"><a href="#">立即购买</a></span></div>
                 	                 <div class="clear"></div>
                 		</li>
                 		<li class="details-list" style="border-bottom:0;" >
                                     <div class="d-left"><img src="./images/5.jpg"/></div>
                                     <div class="d-right">
                                        <span class="list-column">卡西欧（CASIO）手表EDIFICE系列男士石英表EFR-553L-7BVUPR</span>
                                        <span class="list-column list-align">x1</span><span class="list-column">收货人:吴斌 <span class="order-amount">金额:<span class="tot-color">¥114.80</span></span></span><span class="list-column text-align">申请售后<span class="order-amount">已完成</span></span>

                                          </div>
                                          <div class="evaluate"><span class="evaluate-txt"><a href="#">评价</a></span><br><span class="buy-now"><a href="#">立即购买</a></span></div>
                                     <div class="clear"></div>
                        </li>
                 	</ul>
                 </div>
              <div class="order-info">
                    <div class="info-div"><span class="dealtime">2017-03-14 10:00:36</span><span class="number">订单号： </span><a href="#" class="num">51086951433</a><span class="operator">运营商:京东</span><span class="pay">支付方式:在线支付</span><span class="order-details"><a href="#">订单详情></a></span></div>
                    <ul class="details">
                        <li class="details-list">
                                     <div class="d-left"><img src="./images/5.jpg"/></div>
                                     <div class="d-right">
                                        <span class="list-column">卡西欧（CASIO）手表EDIFICE系列男士石英表EFR-553L-7BVUPR</span>
                                        <span class="list-column list-align">x1</span><span class="list-column">收货人:吴斌 <span class="order-amount">金额:<span class="tot-color">¥114.80</span></span></span><span class="list-column text-align">申请售后<span class="order-amount">已完成</span></span>

                                          </div>
                                          <div class="evaluate"><span class="evaluate-txt"><a href="#">评价</a></span><br><span class="buy-now"><a href="#">立即购买</a></span></div>
                                     <div class="clear"></div>
                        </li>
                        <li class="details-list" style="border-bottom:0;">
                                     <div class="d-left"><img src="./images/5.jpg"/></div>
                                     <div class="d-right">
                                        <span class="list-column">卡西欧（CASIO）手表EDIFICE系列男士石英表EFR-553L-7BVUPR</span>
                                        <span class="list-column list-align">x1</span><span class="list-column">收货人:吴斌 <span class="order-amount">金额:<span class="tot-color">¥114.80</span></span></span><span class="list-column text-align">申请售后<span class="order-amount">已完成</span></span>

                                          </div>
                                          <div class="evaluate"><span class="evaluate-txt"><a href="#">评价</a></span><br><span class="buy-now"><a href="#">立即购买</a></span></div>
                                     <div class="clear"></div>
                        </li>
                    </ul>
                 </div>-->
	

	</div>
</body>
</html>