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
		<title>废弃物申报</title>
		<link href="css/index.css" type="text/css" rel="stylesheet">
		<link href="css/index-dealerinfor.css" type="text/css" rel="stylesheet">
	<script src="js/jquery-1.11.3.min.js" ></script>
	<script src="js/index.js" ></script>
	<script src="../mainjs/index_dt.js" ></script>
	<script src="js/jquery.page.js"></script>
		
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
				<!--	<div style="display:none;"><img src="./images/settleup-nogoods.png">
					购物车中还没有商品，赶紧选购吧！</div>
					


				<div class="settleup-content">
						<div class="smt"><h4 class="f1">最新加入的商品</h4></div>
						<div class="smc">
						    <ul class="smc-y">
						          <li>
						          	   <div class="p-img">
						          	   	<a href="#">
						          	   		<img src="./images/563308aaN6198f580.jpg"/>
						          	   	</a>
						          	   </div>
						          	   <div class="p-name">
						          	   	<a href="#">Apple iPad mini 2 平板电脑 7.9英寸（32G WLAN版/A7芯片/Retina显示屏 ME280CH）银色</a>
						          	   </div>
						          	   <div class="p-detail">
						          	   	<span>
						          	   		<strong>￥1988.00</strong>
						          	   		x1
						          	   	</span></br>
						          	   	<a class="" href="#">删除</a>
						          	   </div>
						          </li>
						           <li>
						          	   <div class="p-img">
						          	   	<a href="#">
						          	   		<img src="./images/563308aaN6198f580.jpg"/>
						          	   	</a>
						          	   </div>
						          	   <div class="p-name">
						          	   	<a href="#">Apple iPad mini 2 平板电脑 7.9英寸（32G WLAN版/A7芯片/Retina显示屏 ME280CH）银色</a>
						          	   </div>
						          	   <div class="p-detail">
						          	   	<span>
						          	   		<strong>￥1988.00</strong>
						          	   		x1
						          	   	</span></br>
						          	   	<a class="" href="#">删除</a>
						          	   </div>
						          </li>
						    </ul>
						</div>
						<div class="smb">
						      <div class="p-total">共<b>26</b>件商品　共计<strong>￥ 399.00</strong></div>
						      <a href="#" title="去购物车">去购物车</a>
						</div>
					</div>

					-->


				</div>
			</div>
			
		</div>
		
		<div class="clear"></div>
		<!--轮播图上方导航栏  一栏-->
		<div id="navv">
			<div class="nav-img" style="background: #005BAC;height: 50px;"></div>
			<!--<div class="nav-imgs" style="background:url(./images/568a0a8eNe8f4df82.jpg) no-repeat center top"></div>-->	
		</div>
		<div class="focus">
			<div class="focus-a">
				<div class="fouc-img1"><img src="./images/5689d4ebN19f155a6.jpg" class="nav-img2"></div>
				<div class="fouc-font"><a href="">全部商品分类</a> </div>
			</div>
			
		<%@include file="/shopping/bar/menu.jsp"%>
			<!--<div class="focus-c"><a href=""><img src="./images/hhh.jpg"></a></div>
			<div class="focus-d"><a href=""><img src="./images/nianhuo.jpg"></a></div>-->
			<!--轮播图左边当行蓝-->
			<div class="bb"></div>
			<div class="dd-inner" style="display:none;">
				<div class="font-item">
					<div class="item fore1">
						<h3><a href="../search.jsp?category=0&projid=WF_NYD">普通材料</a></h3>
						<i>></i>
					</div>
					<div class="font-item1">
						
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">生活日用<i>></i></a></dt>							
								<dd>
									<a href="">收纳用品</a><a href="">雨伞雨具</a><a href="">净化除味</a><a href="">浴室用品</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">家具<i>></i></a></dt>							
								<dd>
									<a href="">卧室家具</a><a href="">客厅家具</a><a href="">儿童家具</a><a href="">办公家具</a><a href="">沙发</a><a href="">电脑椅</a><a href="">衣柜</a>
									<a href="">冷风扇</a><a href="">插座</a><a href="">电话机</a><a href="">净水器</a><a href="">饮水机</a>
									<a href="">茶几</a><a href="">电视柜</a><a href="">电脑桌</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>							
								<dd>
									<a href="">电压力锅</a><a href="">豆浆机</a><a href="">面包机</a><a href="">咖啡机</a><a href="">微波炉料理/榨汁机</a><a href="">电烤箱</a><a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a><a href="">煮蛋器酸奶机</a><a href="">电水壶/热水瓶</a><a href="">电炖锅</a><a href="">多用途锅</a><a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>							
								<dd>
									<a href="">剃须刀剃/脱毛器</a><a href="">口腔护理</a><a href="">电吹风</a><a href="">美容器</a><a href="">理发器卷/直发器</a><a href="">按摩椅</a><a href="">按摩器</a>
									<a href="">足浴盆</a><a href="">血压计</a><a href="">健康秤/厨房秤</a><a href="">血糖仪</a><a href="">体温计</a><a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>						
								<dd>
									<a href="">平板电视</a><a href="">空调</a><a href="">冰箱</a><a href="">洗衣机</a><a href="">家庭影院</a><a href="">DVD</a><a href="">迷你音响</a><a href="">烟机/灶具</a>
									<a href="">热水器</a><a href="">消毒具/洗碗柜</a><a href="">冰柜/冰吧</a><a href="">酒柜</a><a href="">家电配件</a>
								</dd>
							</dl>
						</div>
					
					</div>
				</div>
				<div class="fore-2">
					<div class="item fore2">
						<h3>
							<a href="">管制品</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<!--<div class="font-left">
							<div class="one"><a href="">手机频道<i>></i></a></div>
							<div class="two"><a href="">网上营业厅<i>></i></a></div>
							<div class="three"><a href="">配件城<i>></i></a></div>
							<div class="four"><a href="">影响Club<i>></i></a></div>
							<div class="five"><a href="">手机社区<i>></i></a></div>
							<div class="sex"><a href="">以旧换新<i>></i></a></div>
						</div>-->
						<div class="font-lefty">
							<dl class="fore1">
								<!--<dt><a href="">手机通讯<i>></i></a></dt>-->
								<dd>
									<a href="">易制毒</a><a href="">剧毒</a><a href="">易燃</a><a href="">手机维修</a>					
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">京东通讯<i>></i></a></dt>
								<dd>
									<a href="">选号中心</a><a href="">自助服务</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">运营商<i>></i></a></dt>
								<dd>
									<a href="">合约机</a><a href="">办套餐</a><a href="">选号码</a><a href="">装宽带</a><a href="">中国移动</a><a href="">中国联通</a><a href="">中国电信</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">手机配件<i>></i></a></dt>
								<dd>
									<a href="">电池/移动电源</a><a href="">蓝牙耳机</a><a href="">充电器/数据线</a><a href="">手机耳机</a><a href="">手机支架</a><a href="">贴膜</a><a href="">存储卡</a>
									<a href="">保护套</a><a href="">车载iPhone配件</a><a href="">创意配件</a><a href="">便携/无线音箱</a><a href="">手机饰品</a><a href="">拍照配件</a>	
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">摄影摄像<i>></i></a></dt>							
								<dd>
									<a href="">数码相机</a><a href="">单电/微单相机</a><a href="">单反相机</a><a href="">拍立得</a><a href="">运动相机</a><a href="">摄像机</a>
									<a href="">镜头户外器材</a><a href="">影棚器材</a><a href="">冲印服务</a><a href="">数码相框</a>
								</dd>
							</dl>							
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/55f7e052N91fe7821.gif"></a><a href="#"><img src="./images/566b9856N9be09c56.png"></a><a href="#"><img src="./images/56302f25N84147dce.jpg"></a><a href="#"><img src="./images/54dac932Nb84e058e.jpg"></a>
								<a href="#"><img src="./images/54d9d1e9Nfb4684e5.jpg"></a><a href="#"><img src="./images/566e5771N0e6950ee.jpg"></a><a href="#"><img src="./images/55b1d930Nf0bfccbb.jpg"></a><a href="#"><img src="./images/554a11bbN7bb7f655.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5693228dN146c521c.jpg"></a><a href="#"><img src="./images/56985dc0Nda320512.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-3">
					<div class="item fore3">
						<h3>
							<a href="">电脑、</a>
							<a href="">办公</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
								<dd>
									<a href="#">平板电视</a>
									<a href="#">空调</a>
									<a href="#">冰箱</a>
									<a href="#">洗衣机</a>
									<a href="#">家庭影院</a>
									<a href="#">DVD</a>
									<a href="#">迷你音响</a>
									<a href="#">烟机/灶具</a>
									<a href="#">热水器</a>
									<a href="#">消毒具/洗碗柜</a>
									<a href="#">冰柜/冰吧</a>
									<a href="#">酒柜</a>
									<a href="#">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
								<dd>
									<a href="#">取暖电器</a>
									<a href="#">净化器</a>
									<a href="#">扫地机器人</a>
									<a href="#">吸尘器</a>
									<a href="#">加湿器</a>
									<a href="#">挂烫机/熨斗</a>
									<a href="#">电风扇</a>
									<a href="#">冷风扇</a>
									<a href="#">插座</a>
									<a href="#">电话机</a>
									<a href="#">净水器</a>
									<a href="#">饮水机</a>
									<a href="#">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="###">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="#">电压力锅</a>
									<a href="#">豆浆机</a>
									<a href="#">面包机</a>
									<a href="#">咖啡机</a>
									<a href="#">微波炉料理/榨汁机</a>
									<a href="#">电烤箱</a>
									<a href="#">电磁炉</a>
									<a href="#">电饼铛/烧烤盘</a>
									<a href="#">煮蛋器酸奶机</a>
									<a href="#">电水壶/热水瓶</a>
									<a href="#">电炖锅</a>
									<a href="#">多用途锅</a>
									<a href="##">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="#">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="#">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="#">剃须刀剃/脱毛器</a>
									<a href="#">口腔护理</a>
									<a href="#">电吹风</a>
									<a href="#">美容器</a>
									<a href="#">理发器卷/直发器</a>
									<a href="#">按摩椅</a>
									<a href="#">按摩器</a>
									<a href="#">足浴盆</a>
									<a href="#">血压计</a>
									<a href="#">健康秤/厨房秤</a>
									<a href="#">血糖仪</a>
									<a href="#">体温计</a>
									<a href="#">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="#">平板电视</a>
									<a href="#">空调</a>
									<a href="#">冰箱</a>
									<a href="#">洗衣机</a>
									<a href="#">家庭影院</a>
									<a href="#">DVD</a>
									<a href="#">迷你音响</a>
									<a href="#">烟机/灶具</a>
									<a href="#">热水器</a>
									<a href="#">消毒具/洗碗柜</a>
									<a href="#">冰柜/冰吧</a>
									<a href="#">酒柜</a>
									<a href="#">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-4">
					<div class="item fore4">
						<h3>
							<a href="##">家居、</a>
							<a href="##">家具、</a>
							<a href="##">家装、</a>
							<a href="##">厨具</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="#">平板电视</a>
									<a href="#">空调</a>
									<a href="#">冰箱</a>
									<a href="#">洗衣机</a>
									<a href="#">家庭影院</a>
									<a href="#">DVD</a>
									<a href="#">迷你音响</a>
									<a href="#">烟机/灶具</a>
									<a href="#">热水器</a>
									<a href="#">消毒具/洗碗柜</a>
									<a href="#">冰柜/冰吧</a>
									<a href="#">酒柜</a>
									<a href="#">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="#">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="#">取暖电器</a>
									<a href="#">净化器</a>
									<a href="#">扫地机器人</a>
									<a href="#">吸尘器</a>
									<a href="#">加湿器</a>
									<a href="#">挂烫机/熨斗</a>
									<a href="#">电风扇</a>
									<a href="#">冷风扇</a>
									<a href="#">插座</a>
									<a href="#">电话机</a>#
									<a href="#">净水器</a>
									<a href="#">饮水机</a>
									<a href="#">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="#">电压力锅</a>
									<a href="#">豆浆机</a>
									<a href="#">面包机</a>
									<a href="#">咖啡机</a>
									<a href="#">微波炉料理/榨汁机</a>
									<a href="#">电烤箱</a>
									<a href="#">电磁炉</a>
									<a href="#">电饼铛/烧烤盘</a>
									<a href="#">煮蛋器酸奶机</a>
									<a href="#">电水壶/热水瓶</a>
									<a href="#">电炖锅</a>
									<a href="#">多用途锅</a>
									<a href="#">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="##">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="#">剃须刀剃/脱毛器</a>
									<a href="#">口腔护理</a>
									<a href="#">电吹风</a>
									<a href="#">美容器</a>
									<a href="#">理发器卷/直发器</a>
									<a href="#">按摩椅</a>
									<a href="#">按摩器</a>
									<a href="#">足浴盆</a>
									<a href="#">血压计</a>
									<a href="#">健康秤/厨房秤</a>
									<a href="#">血糖仪</a>
									<a href="#">体温计</a>
									<a href="#">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="#">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="#">平板电视</a>
									<a href="#">空调</a>
									<a href="#">冰箱</a>
									<a href="#">洗衣机</a>
									<a href="#">家庭影院</a>
									<a href="#">DVD</a>
									<a href="#">迷你音响</a>
									<a href="#">烟机/灶具</a>
									<a href="#">热水器</a>
									<a href="#">消毒具/洗碗柜</a>
									<a href="#">冰柜/冰吧</a>
									<a href="#">酒柜</a>
									<a href="#">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-5">
					<div class="item fore5">
						<h3>
							<a href="##">男装、</a>
							<a href="##">女装、</a>
							<a href="##">内衣、</a>
							<a href="##">珠宝</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="#">平板电视</a>
									<a href="#">空调</a>
									<a href="#">冰箱</a>
									<a href="#">洗衣机</a>
									<a href="#">家庭影院</a>
									<a href="#">DVD</a>
									<a href="#">迷你音响</a>
									<a href="#">烟机/灶具</a>
									<a href="#">热水器</a>
									<a href="#">消毒具/洗碗柜</a>
									<a href="#">冰柜/冰吧</a>
									<a href="#">酒柜</a>
									<a href="#">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="##">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="#">取暖电器</a>
									<a href="#">净化器</a>
									<a href="#">扫地机器人</a>
									<a href="#">吸尘器</a>
									<a href="#">加湿器</a>
									<a href="#">挂烫机/熨斗</a>
									<a href="#">电风扇</a>
									<a href="#">冷风扇</a>
									<a href="#">插座</a>
									<a href="#">电话机</a>
									<a href="#">净水器</a>
									<a href="#">饮水机</a>
									<a href="#">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="##">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="#">电压力锅</a>
									<a href="#">豆浆机</a>
									<a href="#">面包机</a>
									<a href="#">咖啡机</a>
									<a href="#">微波炉料理/榨汁机</a>
									<a href="#">电烤箱</a>#
									<a href="#">电磁炉</a>
									<a href="#">电饼铛/烧烤盘</a>
									<a href="#">煮蛋器酸奶机</a>
									<a href="#">电水壶/热水瓶</a>
									<a href="#">电炖锅</a>
									<a href="#">多用途锅</a>
									<a href="#">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="#">剃须刀剃/脱毛器</a>
									<a href="#">口腔护理</a>
									<a href="#">电吹风</a>
									<a href="#">美容器</a>
									<a href="#">理发器卷/直发器</a>
									<a href="#">按摩椅</a>
									<a href="#">按摩器</a>
									<a href="#">足浴盆</a>
									<a href="#">血压计</a>
									<a href="#">健康秤/厨房秤</a>
									<a href="#">血糖仪</a>
									<a href="#">体温计</a>
									<a href="#">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="#">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="#">平板电视</a>
									<a href="#">空调</a>
									<a href="#">冰箱</a>
									<a href="#">洗衣机</a>
									<a href="#">家庭影院</a>
									<a href="#">DVD</a>
									<a href="#">迷你音响</a>
									<a href="#">烟机/灶具</a>
									<a href="#">热水器</a>
									<a href="#">消毒具/洗碗柜</a>
									<a href="#">冰柜/冰吧</a>
									<a href="#">酒柜</a>
									<a href="#">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-6">
					<div class="item fore6">
						<h3>
							<a href="">个人化妆、</a>
							<a href="">清洁用品</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-7">
					<div class="item fore7">
						<h3>
							<a href="">鞋靴、</a>
							<a href="">箱包、</a>
							<a href="">钟表、</a>
							<a href="">奢饰品</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-8">
					<div class="item fore8">
						<h3><a href="">运动户外</a></h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-9">
					<div class="item fore9">
						<h3>
							<a href="">汽车、</a>
							<a href="">汽车用品</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>							
								<dd>
									<a href="">平板电视</a><a href="">空调</a><a href="">冰箱</a><a href="">洗衣机</a><a href="">家庭影院</a><a href="">DVD</a><a href="">烟机/灶具</a>
									<a href="">热水器</a><a href="">消毒具/洗碗柜</a><a href="">冰柜/冰吧</a><a href="">酒柜</a><a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>							
								<dd>
									<a href="">取暖电器</a><a href="">净化器</a><a href="">扫地机器人</a><a href="">吸尘器</a><a href="">加湿器</a><a href="">挂烫机/熨斗</a><a href="">电风扇</a>
									<a href="">冷风扇</a><a href="">插座</a><a href="">电话机</a><a href="">净水器</a><a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>							
								<dd>
									<a href="">电压力锅</a><a href="">豆浆机</a><a href="">面包机</a><a href="">咖啡机</a><a href="">微波炉料理/榨汁机</a><a href="">电烤箱</a><a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a><a href="">煮蛋器酸奶机</a><a href="">电水壶/热水瓶</a><a href="">电炖锅</a><a href="">多用途锅</a><a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
								<dd>
									<a href="">剃须刀剃/脱毛器</a><a href="">口腔护理</a><a href="">电吹风</a><a href="">美容器</a><a href="">理发器卷/直发器</a><a href="">按摩椅</a><a href="">按摩器</a>
									<a href="">足浴盆</a><a href="">血压计</a><a href="">健康秤/厨房秤</a><a href="">血糖仪</a><a href="">体温计</a><a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
								<dd>
									<a href="">平板电视</a><a href="">空调</a><a href="">冰箱</a><a href="">洗衣机</a><a href="">家庭影院</a><a href="">DVD</a><a href="">迷你音响</a>
									<a href="">烟机/灶具</a><a href="">热水器</a><a href="">消毒具/洗碗柜</a><a href="">冰柜/冰吧</a><a href="">酒柜</a><a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef28N00328d44.jpg"></a>
								<a href="#"><img src="./images/54d9ef34N7cc61f4c.jpg"></a>
								<a href="#"><img src="./images/54d9ef3eN99aef1f0.jpg"></a>
								<a href="#"><img src="./images/54d9ef4cN4fe57f9b.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-10">
					<div class="item fore10">
						<h3>
							<a href="">母婴、</a>
							<a href="">玩具乐器</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-12">
					<div class="item fore11">
						<h3>
							<a href="">食品、</a>
							<a href="">酒类、</a>
							<a href="">生鲜、</a>
							<a href="">特产</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-12">
					<div class="item fore12">
						<h3><a href="">营养保健</a></h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-13">
					<div class="item fore13">
						<h3>
							<a href="">图书、</a>
							<a href="">音像、</a>
							<a href="">电子书</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-14">
					<div class="item fore14">
						<h3>
							<a href="">彩票、</a>
							<a href="">旅行、</a>
							<a href="">充值、</a>
							<a href="">票务</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
				<div class="fore-15">
					<div class="item fore15">
						<h3>
							<a href="">理财、</a>
							<a href="">众筹、</a>
							<a href="">白条、</a>
							<a href="">保险</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-left">
							<div class="one"><a href="">品牌日<i>></i></a></div>
							<div class="two"><a href="">家电城<i>></i></a></div>
							<div class="three"><a href="">智能生活馆<i>></i></a></div>
							<div class="four"><a href="">京东净化馆<i>></i></a></div>
							<div class="five"><a href="">京东帮服务店<i>></i></a></div>
							<div class="sex"><a href="">值得买精选<i>></i></a></div>
						</div>
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">大家电<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">生活电器<i>></i></a></dt>
							
								<dd>
									<a href="">取暖电器</a>
									<a href="">净化器</a>
									<a href="">扫地机器人</a>
									<a href="">吸尘器</a>
									<a href="">加湿器</a>
									<a href="">挂烫机/熨斗</a>
									<a href="">电风扇</a>
									<a href="">冷风扇</a>
									<a href="">插座</a>
									<a href="">电话机</a>
									<a href="">净水器</a>
									<a href="">饮水机</a>
									<a href="">除湿机</a><a href="">干衣机清洁机</a><a href="">收录/音机</a><a href="">生活电器配件</a><a href="">其它生活电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">厨房电器<i>></i></a></dt>
							
								<dd>
									<a href="">电压力锅</a>
									<a href="">豆浆机</a>
									<a href="">面包机</a>
									<a href="">咖啡机</a>
									<a href="">微波炉料理/榨汁机</a>
									<a href="">电烤箱</a>
									<a href="">电磁炉</a>
									<a href="">电饼铛/烧烤盘</a>
									<a href="">煮蛋器酸奶机</a>
									<a href="">电水壶/热水瓶</a>
									<a href="">电炖锅</a>
									<a href="">多用途锅</a>
									<a href="">果蔬解毒机</a><a href="">养生壶/煎药壶</a><a href="">其它厨房电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">个护健康<i>></i></a></dt>
							
								<dd>
									<a href="">剃须刀剃/脱毛器</a>
									<a href="">口腔护理</a>
									<a href="">电吹风</a>
									<a href="">美容器</a>
									<a href="">理发器卷/直发器</a>
									<a href="">按摩椅</a>
									<a href="">按摩器</a>
									<a href="">足浴盆</a>
									<a href="">血压计</a>
									<a href="">健康秤/厨房秤</a>
									<a href="">血糖仪</a>
									<a href="">体温计</a>
									<a href="">计步器/脂肪检测仪</a><a href="">脂肪检测仪其它健康电器</a>
								</dd>
							</dl>
							<dl class="fore1">
								<dt><a href="">五金家装<i>></i></a></dt>
							
								<dd>
									<a href="">平板电视</a>
									<a href="">空调</a>
									<a href="">冰箱</a>
									<a href="">洗衣机</a>
									<a href="">家庭影院</a>
									<a href="">DVD</a>
									<a href="">迷你音响</a>
									<a href="">烟机/灶具</a>
									<a href="">热水器</a>
									<a href="">消毒具/洗碗柜</a>
									<a href="">冰柜/冰吧</a>
									<a href="">酒柜</a>
									<a href="">家电配件</a>
								</dd>
							</dl>
						</div>
						<div class="font-right">
							<div>
								<a href="#"><img src="./images/562f4971Na5379aba.jpg"></a>
								<a href="#"><img src="./images/54d9eef9N5bb8d27f.jpg"></a>
								<a href="#"><img src="./images/54d9ef02N99d26435.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
								<a href="#"><img src="./images/54d9ef10Nd206a236.jpg"></a>
							</div>
						</div>
						<div class="font-righty">
							<a href="#"><img src="./images/5673a854N10856704.jpg"></a>
							<a href="#"><img src="./images/56a0376aN7de1bdcf.jpg"></a>
						</div>
					</div>
				</div>
			</div>
		    <div class="dealer-infor">
		    	<div class="crumb">
		    		<a href="#"><span>首页</span></a>
		    		>
		    		<span>废弃物申报</span>
		    	</div>
		    	<div class="dealer-d-list">
		    		<span class="dealer-left">
		    			<form action="#">
                                                           
		    			<select name="c" class="c-style c-laboratory">
					<option value="choose">请选择实验室</option>
					<option value="">1</option>
					<option value="">1</option>
					</select>
		    		             订单号：
		    		             <input type="text" name="no" class="o-num">
					  开始时间
					  <input type="date" class="b-date">
					  －结束时间
					    <input type="date" class="b-date">
					     <input type="submit" value="搜　索" class="search-botton">
					    
					</form>
		    		</span>
		    		<span class="dealer-right">
		    			<form action="#">
		    			<input type="submit" value="提交申报"class="search-botton">
		    			 <input type="submit" value="导　出"class="search-botton empty-botton">
					   
					     </form>
		    		</span>
					<div class="clear"></div>
		    		
		    	</div>
		    	<div class="dealer-s-table">
		    		<table id="table">
				     <tr >
				        <td class="table-tr">&nbsp;&nbsp;&nbsp;&nbsp;</td>
				        <td class="table-tr">订单号</td>
				        <td class="table-tr">所在学院</td>
				        <td class="table-tr">申报人</td>
				        <td class="table-tr">申报时间</td>
				        <td class="table-tr">订单状态</td>
				        <td class="table-tr">操作</td>
				     </tr>
				     <tr>
				        <td colspan="7">暂无数据</td>
				       
				        
				     </tr>
				
					 <tr>
				        <td colspan="7" style="height:38px;line-height:38px;">
				        	<div class="tcdPageCode">
				    
					</div>
					<span class="k-title">没有可显示的记录</span>
				        </td>
				       
				       
				     </tr>
				 
				      
				      
				  </table>
		    	</div>
		    </div>
			
			
		
		<!--*****************轮播下方*****************-->
		
		<div id="footer-20171" >
		<div class="links">
			<a rel="nofollow" target="_blank" href="#">关于我们</a>|<a  href="#">联系我们</a>|<a rel="nofollow" target="_blank" href="#">商家入驻</a>|<a rel="nofollow" target="_blank" href="#">营销中心</a>|<a target="_blank" href="#">友情链接</a>|<a target="_blank" href="#">销售联盟</a></div>
		
	  </div>
	</div>
	</body>
	 <script>
         $(".tcdPageCode").createPage({
	        pageCount:6,
	        current:1,
	        backFn:function(p){
	            console.log(p);
	        }
	    });
    </script>
</html>