<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "?" + request.getQueryString();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <!--<link href="../index/css/index.css" type="text/css" rel="stylesheet">-->
    <!--<link rel="stylesheet" href="http://192.168.3.123:8080/SHOPJSON/shopping/bar/css/bar.css"> -->
    <script type="text/javascript" src="../core/jquery-1.4.4.js"></script>
    <script src="../mainjs/sysconfig.js"></script>
    <script src="../mainjs/cart.js"></script>
    <script>

    </script>
    <title>购物车
    </title>
    <link rel="stylesheet" href=" css/gstyle.css">
</head>
<body>


<meta charset="utf-8">


<%@include file="/shopping/bar/left.jsp" %>
<%@include file="/shopping/bar/head.jsp" %>


<div id="wrapper">
    <div class="w">
        <div id="img"><img src="./images/logo.png"><span class="shop-cart">购物车</span></div>
        <div id="search-2014">
            <div class="form"><input type="text" id="key" accesskey="s" class="text">
                <button id="searchbtn" class="button cw-icon"><i></i>搜索</button>
            </div>
        </div>
        <div id="settleup-2014" class="dorpdown">
            <div class="cw-icon"><i class="ci-left"></i><a href="./购物车_files/购物车.html" target="view_window">我的购物车</a>
            </div>
        </div>
        <span class="clr"></span></div>
    <div id="nav-2014">
        <div class="w">
            <div class="dorpdown" id="categorys-2014" style="height: 48px; z-index: 1;">
                <div class="dt" id="mainnav"><a
                        href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=0&projid=SHOP">全部商品10</a>
                </div>
                <div class="dd" id="items" style="display: none; height: 63px;">
                    <div class="dd-inner" id="mainitem">
                        <div class="item fore1" data-index="1"><h3><a
                                href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=1&projid=SHOP">普通材料</a>
                        </h3><i>&gt;</i></div>
                        <div class="item fore2" data-index="2"><h3><a
                                href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=2&projid=SHOP">管制材料</a>
                        </h3><i>&gt;</i></div>
                    </div>
                    <div class="dorpdown-layer" style="display:block;">
                        <div class="item-sub" id="category-item-1">
                            <div class="subitems" style="min-height: 51px;">
                                <dl class="fore1">
                                    <dt>
                                        <a href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=11&projid=SHOP">常用耗材<i>&gt;</i></a>
                                    </dt>
                                    <dd>
                                        <a href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=111&projid=SHOP">镊子</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=112&projid=SHOP">手套</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=113&projid=SHOP">刷子</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=114&projid=SHOP">胶带</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=115&projid=SHOP">笔</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=116&projid=SHOP">插座</a>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                        <div class="item-sub" id="category-item-2">
                            <div class="subitems" style="min-height: 51px;">
                                <dl class="fore1">
                                    <dt>
                                        <a href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=22&projid=SHOP">管制材料<i>&gt;</i></a>
                                    </dt>
                                    <dd>
                                        <a href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=221&projid=SHOP">易制毒</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=222&projid=SHOP">易制爆</a><a
                                            href="http://192.168.3.123:8080/SHOPJSON/shopping/search.jsp?category=223&projid=SHOP">剧毒</a>
                                    </dd>
                                </dl>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="cart" class="w">
        <div class="cart-main">
            <div class="cart-thead">
                <div class="column t-checkbox"></div>
                <div class="column t-goods">商品</div>
                <div class="column t-props"></div>
                <div class="column t-price">单价(元)</div>
                <div class="column t-quantity">数量</div>
                <div class="column t-sum">小计(元)</div>
                <div class="column t-action">操作</div>
            </div>
        </div>
        <div class="cart-tbody">
            <div class="item-list">
                <div class="item-give item-full" id="item-list"></div>
            </div>
        </div>
        <div id="cart-floatbar"
             style="position: fixed;  bottom: 0; left:0; width:100%; box-shadow:0 -1px 8px rgba(0,1,1,.08)" ;>
            <div class="cart-toolbar" style="width: 100%; height: 50px;">
                <div class="toolbar-wrap">
                    <div class="options-box">
                        <input onchange="ck_all(this)" type="checkbox" style="margin-left: 15px; vertical-align:middle;margin-right:15px;"/>全选
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteCart_deleteAll.action">清空购物车</a>

                        <div class="toolbar-right">
                            <div class="normal">
                                <div class="comm-right">
                                    <div class="btn-area">
                                        <a onClick="nexttosettle()" class="submit-btn">去结算<b></b></a></div>
                                    <div class="price-sum">

                                        <div>
                                            <!--<span >购物车总金额：</span><span class="price sumPrice"><em>￥</em><em id="totalcart"></em></span> -->&nbsp;
                                            &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                                            <span>结算总金额：</span><span class="price sumPrice"><em>￥</em><em id="total">1520.88</em></span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>