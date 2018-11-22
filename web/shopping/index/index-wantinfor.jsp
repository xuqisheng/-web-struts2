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
		<title>求购信息</title>
		<link href="css/index.css" type="text/css" rel="stylesheet">
		<link href="css/reveal.css" type="text/css" rel="stylesheet">
		<link href="css/index-dealerinfor.css" type="text/css" rel="stylesheet">
	<script src="js/jquery-1.11.3.min.js" ></script>
		 <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script> 
	<script type="text/javascript" src="js/jquery.reveal.js"></script>
	<script src="js/index.js" ></script>
	<script src="../mainjs/index_dt.js" ></script>
	<script src="../mainjs/wantinfor.js" ></script>
	<script src="js/jquery.page.js">
 
	</script>
<style type="text/css">
		   .table-style { margin:0 auto;}
           .table-style table{border-right:1px solid #ccc;border-bottom:1px solid #ccc;width:890px; font-size:14px;}
		   .table-style table td{border-left:1px solid #ccc;border-top:1px solid #ccc;padding:5px; text-align:center;}
		</style>
	<script>
		$(function() { 
		getWantFor();    
		}
		);
		</script>
	 <SCRIPT LANGUAGE="JavaScript">  
//contetn为要显示的内容  
//height为离窗口顶部的距离  
//time为多少秒后关闭的时间，单位为秒  
function showTips( content, height, time ){  
    //窗口的宽度  
  var windowWidth  = $(window).width();  
  var windowHigh  = $(window).height();  
  var tipsDiv = '<div class="tipsClass">' + content + '</div>';  
   
  $( 'body' ).append( tipsDiv );  
  $( 'div.tipsClass' ).css({  
      'top'       : windowHigh/2 + 'px',  
      'left'      : ( windowWidth / 2 ) - 350/2 + 'px',  
      'position'  : 'absolute',  
      'padding'   : '3px 5px',  
      'background': '#8FBC8F',  
      'font-size' : 12 + 'px',  
      'margin'    : '0 auto',  
      'text-align': 'center',  
      'width'     : '350px',  
      'height'    : 'auto',  
      'color'     : '#fff',  
      'opacity'   : '0.8'  
  }).show();  
  setTimeout( function(){$( 'div.tipsClass' ).fadeOut();}, ( time * 1000 ) );  
}  
  
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
			<div class="nav-img" style="background: #005BAC;height: 50px;"></div>
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
		    	<div class="crumb">
		    		<a href="#"><span>首页</span></a>
		    		>
		    		<span>求购信息</span>
		    	</div>
		    	<div class="dealer-s-list dealer-b-list">
		    		<span>
		    			<form action="#">
					  开始时间
					  <input type="date" class="b-date">
					  －结束时间
					    <input type="date" class="b-date">
					     <input type="submit" value="搜　索" class="search-botton">
					     <input type="submit" value="清　空"class="search-botton empty-botton">
					     <input type="submit" onclick="show()" value="发布求购"class="search-botton">
					</form>
		    		</span>

<!--发布求购信息-->
		             <div id="Idiv" style="display:none;" class="Bomb-box">
		    <div class="add-div"><span class="add-title">发布求购信息</span><a href="javascript:void(0)" onClick="closeDiv()" title="关闭"><span class="ui-dialog-close"></span></a></div>
			<div class="add-input">
				<div class="add-input-list"><label>商品名称</label><input id="w_pname" type="text" class="input-style"></input></div>
				<div class="add-input-list"><label>品牌</label><input id="w_brand" type="text" class="input-style"></input></div>
				<div class="add-input-list"><label>规格</label><input id="w_model" type="text" class="input-style"></input></div>
				<div class="add-input-list"><label>数量</label><input id="w_num" type="text" class="input-style"></input></div>
				<div class="add-input-list"><label>备注</label><input id="w_remark" type="text" class="input-style"></input></div>
				<div class="add-input-list"><label>联系方式</label><input id="w_tel" type="text" class="input-style"></input></div>
				<div class="add-input-list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="savewantfor()" class="save-botton">发布</a></div>
			</div>
		 </div>
				<!--新增收货地址先隐藏 end-->

		    		
		    	</div>

		    	<div class="dealer-s-table">
		    		<table id="table"> 
					
			    <thead>
					  <tr style="  background: beige;">
				        <td class="table-tr" style="width:20%;text-align:center">商品名称</td>
				        <td class="table-tr" style="width:10%;text-align:center">品牌</td>
				        <td class="table-tr" style="width:15%;text-align:center">规格</td>
				        <td class="table-tr" style="width:10%;text-align:center">数量</td>
				        <td class="table-tr" style="width:25%;text-align:center">备注</td>
				        <td class="table-tr" style="width:10%;text-align:center">发布时间</td>
				        <td class="table-tr" style="width:10%;text-align:center">操作</td>
					</tr>
			    </thead>
				<tbody id="wbody">
				</tbody >
				    <!-- <tr>
				        <td colspan="7">暂无数据</td>
				       
				        
				     </tr>
				
					 <tr>
				        <td colspan="7" style="height:38px;line-height:38px;">
				        	<div class="tcdPageCode">
				    
					</div>
					<span class="k-title">没有可显示的记录</span>
				        </td>
				       
				       
				     </tr>-->
				 
				      
				      
				  </table>
		    	</div>
		    </div>
			
			
		
		<!--*****************轮播下方*****************-->
		
		<div id="footer-20171" >
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