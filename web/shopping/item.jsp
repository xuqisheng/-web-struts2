<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//if (request.getSession().getAttribute("userId")==null) {
	//	response.sendRedirect("index.jsp");
	//}
	String projid = request.getParameter("projid");
	if (projid == null)
		projid = "";
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- saved from url=(0107)http://192.168.3.123:8080/SHOPJSON/shopping/item.jsp?itemid=551_2101014504&subid=551_2101014504&projid=SHOP -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="mainjs/jquery-1.4.4.js"></script>
<script type="text/javascript" src="item/item.js"></script>
<script type="text/javascript" src="mainjs/navigation.js"></script>
    <style>
.Bomb-box{width:240px;height:40px;position:absolute;z-index:1000;background:red;border-radius: 4px; font-size:14px;}

	</style>
<title>商品详情</title>
<link rel="stylesheet" href="item/css/item_style.css">
<link rel="stylesheet" href="item/css/item_style_agency.css">
</head>
<body>
<div id="Idiv" style="display:none;text-align:center;" class="Bomb-box">
		   <span style=" text-align:center;color:#ffffff ;  line-height: 40px;">添加购物车成功</span>
		 </div>

	<%@include file="/shopping/bar/head.jsp"%>
	<%@include file="/shopping/bar/left.jsp"%>
	<%@include file="/shopping/bar/searchbar.jsp"%>

<!--	<div id="wrapper">
		<div class="w">
			<div id="img"></div>
			<div id="search-2014">
				<div class="form">
					<input type="text" id="key" accesskey="s" value="请输入关键字进行搜索"
						onFocus="if(this.value=='请输入关键字进行搜索'){this.value='';};"
						onBlur="if(this.value==''){this.value='请输入关键字进行搜索';};"
						class="text">
					<button id="searchbtn" class="button cw-icon">
						<i></i>搜索
					</button>
				</div>
			</div>
			<div id="settleup-2014" class="dorpdown">
				<div class="cw-icon">
					<i class="ci-left"></i><a
						href="./cart/cart.jsp?projid=SHOP">我的购物车</a>
				</div>
			</div>
			<span class="clr"></span>
		</div>
-->		

		<!--<div id = "nav-2014">
			<div class = "w">
				<div id="categorys-2014" class="dorpdown">
					<div class="dt" id="mainnav">
						<a target="_blank" href="#">全部商品分类</a>
					</div>
					<div class="dd" id="items" style="display:none;height: 342px;">
						
						<div class="dd-inner" id="mainitem">
					
						</div>
						
						<div class="dorpdown-layer"style="display: block;">
							
						</div>
					</div>
				</div>	
			</div>  
		</div> -->
		<div id="nav-2014">
			<div class="w">
				<div class="dorpdown" id="categorys-2014" style="height: 48px; z-index: 1;">
					<div class="dt" id="mainnav">
						<a
							href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=0&projid=SHOP">全部商品分类</a>
					</div>
					<div class="dd" id="items" style="display: none; height: 342px;">
						<div class="dd-inner" id="mainitem">
						</div>
						<div class="dorpdown-layer" style="display:block;">
							
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="product-intro" class="m-item-grid clearfix">
		<div id="suppier-info">  
		<h3><strong >阿拉丁试剂（上海）有限公司</strong><span> <a onclick='OpenQQ(123415)'>QQ交谈</a></span></h3> 
		</div>
		
			<div id="preview">
				<div id="spec-n1" class="jqzoom">
					<img width="350" height="350"
						onerror="this.src='img/default.jpg'"
						>
				</div>
			</div>
			<div class="m-item-inner">
				<div id="itemInfo">
					<div id="name">
						<h1></h1>
					</div>
					<div id="summary">
						<div class="summary-info J-summary-info clearfix">
							<div id="comment-count" class="comment-count item fl">
								<p class="comment">累计评价</p>
								<a class="count"
									href=""></a>
							</div>
						</div>
						<div id="summary-price">
							<div class="dt">价格：</div>
							<div class="dd">
								<strong class="p-price" id="jd-price" sub=""></strong>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix p-choose-wrap">
					<div class="li choose-color-shouji p-choose">
						<div class="dt">选择系列：</div>
						<div class="dd">
							<div class="item selected">
								<b></b><a
									class="changeprize" sub=""><img width="25"
									height="25" onerror="this.src='img/default.jpg'"
									
									style="margin-right: 5px;"><i></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="choose-btns" class="li">
				<div class="choose-amount fl">
					<div class="wrap-input">
						<input class="text" id="buy-num" value="1">
					</div>
				</div>
				<div class="btn">
					<a class="btn-append" id="addcard" 
						href="#">加入购物车<b></b></a>
				</div>
			</div>
		</div>
		<!-- 规格参数/商品介绍/商品评价切换 -->
		<div class="w">
			<div id="itemdetail" class="right">
				<div class="mt J-detail-tab" id="pro-detail-hd"
					data-fixed="pro-detail-hd-fixed">
					<div class="mt-inner m-tab-trigger-wrap clearfix">
						<ul class="m-tab-trigger">
							<li id="detail-tab-agency"
								class="ui-agency trig-item curr"><a
								href="#agency">经销商信息</a></li>
							<li id="detail-tab-param"
								class="ui-switchable-item trig-item "><a
								href="#parameter">规格参数</a></li>
							<li id="detail-tab-intro" class="ui-switchable-item trig-item"><a
								href="#infopage">商品介绍</a></li>
							<li id="detail-tab-comm" class="ui-switchable-item trig-item"><a
								href="#comments">商品评价</a></li>
						</ul>
					</div>
				</div>
				<!--经销商信息-->
				<div id= "same_sale">
				<!--<div id = "agency" class="p-parameter">
					<h3><strong >北京华威瑞克有限公司</strong><span>QQ交谈</span></h3>
					<ul  class="agency_info">
						<li>联系电话：<i>139XXXXXXXX</i></li>
						<li>供货周期：<i>100</i></li>
						<li>邮费：<i>￥50</i></li>
					</ul>
					<ul >
						<li>动态评价：</li>
						
					</ul>
					<ul class="agency_comment">
						<li>服务态度：<i>5</i>分</li>
						<li>商品质量：<i>5</i>分</li>
						<li>发货速度：<i>5</i>分</li>
					</ul>
					<div class="agency_price">
						<p>商家优惠价：<span>143元<span></p>
						<div class="btn">
							<a class="btn-append addCart" 
		
						href="http://192.168.3.123:8080/SHOPJSON/shopping/addCart.action?subid=551_2101014504&num=1"><strong>加入购物车</strong></a>
						</div>
						<div class="btn ">
							<a class="btn-append addCollection" href="http://192.168.3.123:8080/SHOPJSON/shopping/addCart.action?subid=551_2101014504&num=1"><strong>收藏</strong></a>
						</div>
					</div>
					<p></p>
				</div>
				<div id = "agency" class="p-parameter">
					<h3><strong >北京华威瑞克有限公司</strong><span>QQ交谈</span></h3>
					<ul  class="agency_info">
						<li>联系电话：<i>139XXXXXXXX</i></li>
						<li>供货周期：<i>100</i></li>
						<li>邮费：<i>￥50</i></li>
					</ul>
					<ul >
						<li>动态评价：</li>
						
					</ul>
					<ul class="agency_comment">
						<li>服务态度：<i>5</i>分</li>
						<li>商品质量：<i>5</i>分</li>
						<li>发货速度：<i>5</i>分</li>
					</ul>
					<div class="agency_price">
						<p>商家优惠价：<span>143元<span></p>
						<div class="btn">
							<a class="btn-append addCart" 
		
						href="http://192.168.3.123:8080/SHOPJSON/shopping/addCart.action?subid=551_2101014504&num=1"><strong>加入购物车</strong></a>
						</div>
						<div class="btn ">
							<a class="btn-append addCollection" href="http://192.168.3.123:8080/SHOPJSON/shopping/addCart.action?subid=551_2101014504&num=1"><strong>收藏</strong></a>
						</div>
					</div>
					<p></p>
				</div>-->
				</div>
				<div id="parameter" class="p-parameter">
					<ul id="p-parameter2" class="p-parameter-list">
						<li></li>
						<li></li>
						<li></li>
						<li></li>
						<li></li>
						<li></li>
					</ul>
					<p></p>
				</div>
				<div id="infopage">
					<iframe id="infoframe" frameborder="0" scrolling="no"
						src="" iframeheight()"=""></iframe>
				</div>
				<div id="comments" class="m">
					<div id="comment-0" class="mc ui-switchable-panel comments-table ui-switchable-panel-selected" style="display: block;">
						<div class="com-table-main"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>