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
		<link href="css/index.css" type="text/css" rel="stylesheet">
		<style type="text/css"> 
			#demo { 
			background: #FFF; 
			overflow:hidden; 
			width: 100%; 
			} 
			#demo img { 
			border: 3px solid #F2F2F2; 
			} 
			#indemo { 
			float: left; 
			width: 800%; 
			} 
			#demo1 { 
			float: left; 
			} 
			#demo2 { 
			float: left; 
			} 
			</style>
	<script src="js/jquery-1.11.3.min.js" ></script>
	<script src="js/index.js" ></script>
	<script src="../mainjs/sysconfig.js" ></script>
	<script src="../mainjs/index_dt.js" ></script>

	<script>
		window.onload=function(){
			createMenu();
			mainitems();
		GetRECOMMEND();
		GetTGROUP();
GetHotSearch();
getmsg();

		}
		</script>
	</head>
	<body id="top">
		
		<!-----------------------------------------顶部-------------->
		
		<%@include file="/shopping/bar/left.jsp"%>
		<%@include file="/shopping/bar/head.jsp"%>
		
		<div class="w">
			<div class="logo"><a href=""><img src="./images/logo.png"></a></div>
			<div class="search">
				<input type="text" value="请输入关键字进行搜索,品名/品牌/CAS号/货号等" class="text" id="textt"   onfocus='if(this.value=="请输入关键字进行搜索,品名/品牌/CAS号/货号等"){this.value="";}; '   
            onblur='if(this.value==""){this.value="请输入关键字进行搜索,品名/品牌/CAS号/货号等";};'>
				<!--<button class="button" onClick="serch($(this).parent().find(\"[id='textt']\").val())">搜索</button>-->
				<button class="button" onClick="serchm()">搜索</button>
			</div>
			
			<div class="right">
				<i class="gw-left" style="background:url(./images/cg2017img.png)0 -58px no-repeat;width:24px;height:24px;"></i>
				<i class="gw-right">></i>
				<i class="gw-count" id="itemsnum">0</i>
				<a id="carts" target="_blank">我的购物车</a>
			
				<div class="dorpdown-layer" id = "maincarts">
				


				</div>
			</div>
			<div class="hotwords" id="hotwords">
				<a  >热门搜索:</a>
				<!--<a href="#" class="red">热门搜索2</a>
				<a href="#" class="red">热门搜索3</a>-->
				
			</div>
		</div>
		
		<div class="clear"></div>
		<!--轮播图上方导航栏  一栏-->
		<div id="navv">
			<div class="nav-img" style="height:51px;background: #005BAC;"></div>
			<!-- <div class="nav-imgs" style="background:url(./images/568a0a8eNe8f4df82.jpg) no-repeat center top"></div> -->	
		</div>
		<div class="focus">
			<div class="focus-a">
				<div class="fouc-img1"><img src="./images/5689d4ebN19f155a6.jpg" class="nav-img2"></div>
				<div class="fouc-font"><a href="../search.jsp?category=0&projid=SHOP">全部商品分类</a> </div> 
			</div>
			
		<%@include file="/shopping/bar/menu.jsp"%>
			<!--<div class="focus-c"><a href=""><img src="./images/hhh.jpg"></a></div>
			<div class="focus-d"><a href=""><img src="./images/nianhuo.jpg"></a></div>-->
			<!--轮播图左边当行蓝-->
			<div class="bb"></div>
			
			<div class="dd-inner" id="mainmenu">

			<!--<div class="font-item">
					<div class="item fore1">
						<h3><a href="../search.jsp?category=0&projid=WF_NYD">普通材料</a></h3>
						<i>></i>
					</div>
					<div class="font-item1">
						
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">常用耗材<i>></i></a></dt>							
								<dd>
									<a href="../search.jsp?category=111&projid=SHOP">镊子</a>
									<a href="../search.jsp?category=112&projid=SHOP">手套</a>
									<a href="../search.jsp?category=113&projid=SHOP">刷子</a>
									<a href="../search.jsp?category=114&projid=SHOP">胶带</a>
									<a href="../search.jsp?category=115&projid=SHOP">笔</a>
									<a href="../search.jsp?category=116&projid=SHOP">插座</a>
								</dd>

							</dl>
							<dl class="fore1">
								<dt><a href="">常用耗材<i>></i></a></dt>							
								<dd>
									<a href="../search.jsp?category=111&projid=SHOP">镊子</a>
									<a href="../search.jsp?category=112&projid=SHOP">手套</a>
									<a href="../search.jsp?category=113&projid=SHOP">刷子</a>
									<a href="../search.jsp?category=114&projid=SHOP">胶带</a>
									<a href="../search.jsp?category=115&projid=SHOP">笔</a>
									<a href="../search.jsp?category=116&projid=SHOP">插座</a>
								</dd>

							</dl>
						</div>
					
					</div>
				</div>
				<div class="font-item">
					<div class="item fore2">
						<h3>
							<a href="../search.jsp?category=2&projid=WF_NYD">化学用品</a>
						</h3>
						<i>></i>
					</div>
					<div class="font-item1">
						<div class="font-lefty">
							<dl class="fore1">
								<dt><a href="">管制品<i>></i></a></dt>
								<dd>
									<a href="../search.jsp?category=221&projid=SHOP">易制毒</a>
									<a href="../search.jsp?category=222&projid=SHOP">易制爆</a>
									<a href="../search.jsp?category=223&projid=SHOP">剧毒</a>					
								</dd>
							</dl>				
						</div>
						
						
					</div>
				</div>-->

		


				</div>
			<!------------------------------------轮播图------------------------------------>
			<div id="lunbo">
				<ul id="one">
					<li><a href=""><img src="./images/fyproduct.jpg"></a></li>
					<li><a href=""><img src="./images/fyup.jpg"></a></li>
				</ul>
				<ul id="two">
					<li class="on">1</li>
					<li>2</li>
				</ul>
				<!------------------------------------轮播图左右箭头------------------------>
				<div class="slider-page">
					<a href="javascript:void(0)" id="left"><</a>
					<a href="javascript:void(0)" id="right">></a>
				</div>
			</div>
			
			<!------------------------------------轮播图右侧一栏------------------------>
			<div class="m" id="showmessage">
				<div class="mt">
					<h3 href="#" onclick="checkmsg('gg',this)">公告</h3>
					<h3 >&nbsp;|&nbsp;</h3>
					<h3 href="#" onclick="checkmsg('xggd',this)">相关规定</h3> 
					<div class="extra"><a href="">更多 ></a></div>
				</div>
				<div class="mc" id="gg" name="msg">
					<!--<ul>
						<li><a href=""><span>[特惠]</span>集中采购即将上线,优惠多多</a></li>
					</ul>
					<ul>
						<li><a href=""><span>[公告]</span>集中采购即将上线，敬请期待！</a></li>
					</ul>-->
				</div>
				<div class="mc" style="display:none;" id="xggd" name="msg">
					 
				</div>
				
				
			</div>
			<div class="m m-c">
				<div class="mt">
					<h3>促销</h3>
					<div class="extra"><a href="">更多 ></a></div>
				</div>
				<div class="mc" id="cx">
					<!--<ul>
						<li><a href=""><span>[特惠]</span>集中采购即将上线,优惠多多</a></li>
					</ul>
					<ul>
						<li><a href=""><span>[公告]</span>集中采购即将上线，敬请期待！</a></li>
					</ul>-->
				</div> 
				
			</div>
			<div class="ms">
				<div class="mm">
					<h3>订购</h3>
					
				</div>
				<div class="mmm">
					<ul>
						
						<li>
							<a href="">
								<i class="ci-left" style="background:url(./images/568dfdbdN4f7d7ca3.png)no-repeat 0 -25px;"></i>
								<span class="ci-bottom">机票</span>
							</a>
						</li>
						
						
						
						<li style="border-right:0;">
							<a href="">
								<i class="ci-left" style="background:url(./images/568dfdbdN4f7d7ca3.png)no-repeat -25px -150px;"></i>
								<span class="ci-bottom">酒店</span>
							</a>
						</li>
						
						
						
					</ul>
					
				</div>
			</div>
		
		<!--*****************轮播下方*****************-->
		<div class="home-promo-wrap">
          
		   <ul class="home-promo-list">
		   	<li class="list-margin"><a href="#" class="box-shadow-outset"><img src="./images/promo1.jpg"></a></li>
		   	<li><a href="#" class="box-shadow-outset"><img src="./images/promo2.jpg"></a></li>
		   	<li><a href="#" class="box-shadow-outset"><img src="./images/promo3.jpg"></a></li>
		   	<li><a href="#" class="box-shadow-outset"><img src="./images/promo4.jpg"></a></li>
		   	
		   </ul>
		
		</div>
		<div id="footer-20171" >
		<div class="links">
			<a rel="nofollow" target="_blank" href="#">关于我们</a>|<a  href="#">联系我们</a>|<a rel="nofollow" target="_blank" href="#">商家入驻</a>|<a target="_blank" href="#">友情链接</a>
			<p>Copyright © 2017-2018 复翼 版权所有</p>

		</div>
		
	  </div>
	</div>
	</body>
<script> 
	var speed=50; 
	var tab=document.getElementById("demo"); 
	var tab1=document.getElementById("demo1"); 
	var tab2=document.getElementById("demo2"); 
	tab2.innerHTML=tab1.innerHTML; 
	function Marquee(){ 
	if(tab2.offsetWidth-tab.scrollLeft<=0) 
	tab.scrollLeft-=tab1.offsetWidth 
	else{ 
	tab.scrollLeft++; 
	} 
	} 
	var MyMar=setInterval(Marquee,speed); 

	tab.onmouseover=function() {
		clearInterval(MyMar)
		}; 
	tab.onmouseout=function() 
	{
		MyMar=setInterval(Marquee,speed)
	}; 
function checkmsg(divname,obj){
	$('#showmessage div[name=msg]').hide();
	$('#'+divname).show();
	$('#showmessage h3').css("color","#666");
	$(obj).css("color","#c81623"); 
}
</script>
</html>