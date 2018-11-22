
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String projid = request.getParameter("projid");
	if (projid == null) projid = "";
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getRequestURI()+"?"+request.getQueryString();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="mainjs/jquery-1.4.4.js"></script>
<script type="text/javascript" src="search/search.js"></script>
<script type="text/javascript" src="mainjs/navigation.js"></script>
<title>复翼集中采购平台</title>
<link rel="stylesheet" href="search/css/search_style.css">
<style></style>
</head>
<body>
	<%@include file="bar/head.jsp"%>
	<%@include file="bar/left.jsp"%>
	<!-- 搜索框 -->
    <%@include file="/shopping/bar/searchbar.jsp"%>
	


	
	<!--新增收货地址先隐藏-->
	<div id="Idiv" style="display:none;" class="Bomb-box">
		<span style=" text-align:center;color:#ffffff ">添加购物车成功</span>
	</div>
	<!--新增收货地址先隐藏-->
		
		<!-- 菜单 -->
		<div id="nav-2014">
			<div class="w">
				<div class="dorpdown" id="categorys-2014" style="height: 48px; z-index: 1;">
					<div class="dt" id="mainnav">
						<a href="">全部商品分类</a>
					</div>
					<div class="dd" id="items" style="display: none; height: 342px;">
						<div class="dd-inner" id="mainitem" style="background-color:#005BAC;"></div>
						<div class="dorpdown-layer" style="display:block;"></div>
					</div>
				</div>
			</div>
		</div>
		
		<!--搜索条件-->
		<div id="J_searchWrap" class="w">
			<!-- 已选择的搜索条件 -->
			<div class="crumbs-bar" id="J_crumbsBar">
				<div class="crumbs-nav">
					<div class="crumbs-nav-main clearfix">
						<div class="crumbs-nav-item">
							<div class="crumbs-first" style="margin-left:10px;"></div>
						</div>
						<i class="crumbs-arrow">&gt;</i>
						<div class="crumbs-nav-item">
							<strong class="search-key">全部结果</strong>
						</div>
					</div>
				</div>
			</div>

			<!--搜索条件-->
			<div id="J_selector" class="selector">
				<!--<div class="J_selectorLine">
					
					<div class="sl-wrap">
						<div class="sl-key">
							<span>供应商</span>
						</div>
						<div class="sl-value">
							<div class="sl-v-list">
								<ul class="J_valueList">
									<li style="display:block;"><a class="parameter"
										href=""
										parameter="0" style=""></a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				-->
			</div>

			<!--商品缩略图-->
			<div id="J_goodsList" class="goods-list-v1" >
				<ul class="gl-warp clearfix">
					<!--<li class="gl-item"><div class="gl-i-wrap">
							<div class="p-img">
								<a target="_blank"
									href="http://192.168.3.123:8080/SHOPJSON/shopping/item.jsp?itemid=551_2101014576&subid=551_2101014576&projid=SHOP"><img
									width="220" height="220" class="err-product"
									onerror="this.src=&#39;img/default.jpg&#39;"
									src="./search_files/fileSystem_getImgStreamAction.action"></a>
							</div>
							<div class="p-price">
								<strong><em>￥</em><i>15.00</i></strong>
							</div>
							<div class="p-name p-name-type-2">
								<a target="_blank"
									href="http://192.168.3.123:8080/SHOPJSON/shopping/item.jsp?itemid=551_2101014576&subid=551_2101014576&projid=SHOP"><em>欣维尔T型三通连接头
										- 欣维尔T型三通连接头</em></a>
							</div>
							<div style="float: right" id="sin">
								<strong style="color:blue">复旦科教器材有限公司</strong><a
									onClick="supplier(&quot;551&quot;)"><img width="16"
									height="16" src="./search_files/qq.png"></a>
							</div>
							<div class="p-commit">
								<strong>已有0人评价</strong>
							</div>
							<div class="p-operate">
								<a class="p-o-btn addcart"
									onClick="addcard(&quot;551_2101014576&quot;,1,this,event)"><i></i>加入购物车</a>
								<div style="display:none;" id="addsuccess_551_2101014576"></div>
							</div>
						</div>
					</li>-->
				</ul>
			</div>

			<!--页码-->
			<div id="pages" class="page clearfix">
				<div class="p-wrap" id="J_bottomPage">
					<span class="p-num">
						<!--<a class="pn-prev" href=""><i>&lt;</i><em>上一页</em></a>
						<a href="" class="curr"></a>
						<a class="pn-next" href="">下一页<i>&gt;</i></a>-->
					</span>
					<span class="p-skip">
						<em>共<b></b>页&nbsp;&nbsp;到第</em>
						<input class="input-txt" id="page_jump_num" onKeyUp="this.value=this.value.replace(/[^0-9]/g,'');" >
						<em>页</em>
						<a class="btn btn-default" id="pn-sub">确定</a>
					</span>
				</div>
			</div>




		</div>
	</div>
</body>
</html>