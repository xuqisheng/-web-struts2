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
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="../core/jquery-1.4.4.js"></script>
<!--<script type="text/javascript" src="../core/My97DatePicker/WdatePicker.js"></script>-->
<script type="text/javascript" src="./core/settle.js"></script>
<script type="text/javascript" src="./core/time.js"></script>
<script type="text/javascript" src="../address/address.js"></script>
<script language="JavaScript" type="text/JavaScript">
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
   #ul_list li{height:30px; line-height: 30px; padding: 0 16px; border: 1px solid #005BAC;float: left; margin-right: 3px; cursor: pointer;}
   .current{ background: #005BAC; color:#fff;}
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

				<div id="firstread" style="display:none;" class="Bomb-boxfirst">
					<%@include file="../firstread.jsp"%>
 				 </div>

			<span class="w-title">结算页</span>
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
		<div class="pay-tit">
		  <h3>支付方式</h3>
		  <div class="pay-div">
			   <ul id="ul_list">
				   <li class="current" id='XM'>本人项目</li>
				   <li id='OT'>他人项目</li>
				  
			   </ul>
			   <div id="content_list">
				  <div class="showlist" id="two">
				     <table class="table_style" border="0" id="table_test">
				            <tr class="addp ">
				             <th class="table_th" style="font-weight:normal;text-align: left;">支付项目<select id="pro" name="pro">
				                     <option value="0">==请选择支付项目==</option>
				                </select>
								<span>项目金额<input type="text" style="width:64px;" name = "pro_money"></span>
								
				                        <span id="proinfo" style="display: inline;" class="proinfo">项目负责人 </span>
				                        <span id="proid" class="proid" style="display: none;"></span><!--项目-->
				                        <span id="chargecode" class="chargecode" style="display: none;"></span><!--项目负责人编号-->           
				                        <span id="chargename" class="chargename" style="display: none;"></span><!--项目负责人名字-->                            
				                    <!-- <span id="add">+</span> -->
				                    <span id="add" class ="add_pro"><img src="./images/add.png"/ style="vertical-align:middle; margin-bottom:5px;cursor: pointer;width:28px; height:28px;"></span>
				            </th>
				                
				            </tr>
				            <tr id="list1">
				            
				            </tr>
           
            
                    </table>







					<!-- <span id="add"><img src="./images/add.png"/ style="vertical-align:middle; margin-bottom:5px;cursor: pointer;width:28px; height:28px;"></span>	 -->				
				  </div>
				  <div id="two">
				  <div  id = "OtPay"></div>
				  负责人工号：&nbsp;<input id="othercode" type="text" value=""  />&nbsp;&nbsp;&nbsp;&nbsp;负责人姓名：&nbsp;<input id="othername" type="text" value=""  />
				  
				  </div>
			   
			   </div>
   </div>
		</div>
			<!--<div class="danger-right"><img src="images/danger.jpg"/></div>-->
			<div class="clear"></div>

	 	<div class="step-tit"><h3>收货人信息</h3>
		<!-- <span class="topic-choice" ><input type="checkbox" name="" value="" id="topic-choice">课题组&nbsp;&nbsp;
		       <select id="ktz" name="ktz"   class="s-choice" onChange="groupchange()">
										<option value="0">==请选择课题组==</option>
					<option value="ke">课题组</option>
					<option value="xk">XXXX课题组</option>
					<option value="fiat">XXXXX课题组2</option>
					<option value="audi">XXXXXX课题组3</option>
				</select>

				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a id="whprinfo" style="display:none;color:red" >管制品申请单&nbsp;&nbsp;<select id="whpr" name="whpr" onChange="whpRchange()">
										<option value="0">==请选择管制品申请单==</option>

				</select>
				<span id="gzpfiles"></a>
				</span>
	   </span> -->
	 		<div class="extra-r">
	 			<a href="#" class="r-color" onClick="show()">新增收货地址</a>
	 		</div>
			<div>
	 	
		
	              </div>
		<!--新增收货地址先隐藏-->
		<div id="Idiv" style="display:none;" class="Bomb-box">
		    <div class="add-div"><span class="add-title">新增收货人信息</span><a href="javascript:void(0)" onClick="closeDiv()" title="关闭"><span class="ui-dialog-close"></span></a></div>
			<div class="add-input">
				<div class="add-input-list">收货人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="newuser" type="text" class="input-style"></input></div>
				<div class="add-input-list">收货地址&nbsp;&nbsp;<input id="newaddress" type="text" class="input-style"></input></div>
				<div class="add-input-list">手机号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="newphone" type="text" class="input-style"></input></div>
				<div class="add-input-list">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="saveaddress()" class="save-botton">保存</a></div>
			</div>
		 </div>
		<!--新增收货地址先隐藏-->



	              </div>
		<div class="mes-add" id="address">
		<!--<li class="wrap">
			<div class="checkbox-one"><div class="express-checkbox express-checkbox1">
			  <input name="" type="checkbox" value="" id="checkboxOneInput1"/>
			   
			  <label for="checkboxOneInput1">吴斌</label> 
			   
			  </div></div>
	 		<div class="addr-detail">
	 			<span class="addr-name">吴斌</span>
	 			<span class="addr-info">上海 杨浦区 城区 上海市邯郸路159号10楼A座</span>
	 			<span class="addr-tel">156****5895</span>
	 			<span class="addr-default">默认地址</span>
	 		</div>
			<div class="default-add">设置为默认地址</div>
	 		<div class="clear"></div>
	 		
	 	</li>-->
		<div style="display:none;" id="addshow">
		<!--<li class="wrap">
			<div class="checkbox-one"><div class="express-checkbox express-checkbox1">
			  <input name="" type="checkbox" value="" id="checkboxOneInput2"/>
			   
			  <label for="checkboxOneInput2">吴斌</label> 
			   
			  </div></div>
	 		<div class="addr-detail">
	 			<span class="addr-name">吴斌</span>
	 			<span class="addr-info">上海 杨浦区 城区 上海市邯郸路159号10楼A座</span>
	 			<span class="addr-tel">156****5895</span>
	 			
	 		</div>
			<div class="default-add">设置为默认地址</div>
	 		<div class="clear"></div>
	 		
	 	</li>
		<li class="wrap">
			<div class="checkbox-one"><div class="express-checkbox express-checkbox1">
			  <input name="" type="checkbox" value="" id="checkboxOneInput3"/>
			   
			  <label for="checkboxOneInput3">吴斌</label> 
			   
			  </div></div>
	 		<div class="addr-detail">
	 			<span class="addr-name">吴斌</span>
	 			<span class="addr-info">上海 杨浦区 城区 上海市邯郸路159号10楼A座</span>
	 			<span class="addr-tel">156****5895</span>
	 			
	 		</div>
			<div class="default-add">设置为默认地址</div>
	 		<div class="clear"></div>
	 		
	 	</li>-->
		</div>
		<!--<div class="addr-switch switch-on" id="add"><span>更多地址</span>
	 			<b></b>
	 	</div>
		<div class="addr-switch switch-off" style="display:none;"><span>收起地址</span>
	 			<b></b>
	 	</div>-->
        </div>
	 	<div class="addr-switch switch-on" id="add"><span>更多地址</span>
	 			<b></b>
	 	</div>
		<div class="addr-switch switch-off" style="display:none;"><span>收起地址</span>
	 			<b></b>
	 	</div>
	      <!--<div id="carts"> 
			   <span id = "supplier"></span>
			   <span id = "supplierid" style="display:none;">供应商ID</span>
	
		</div>-->

	 		<div class="clear">
			</div>
			<div class="step-tit">
			<h3>送货清单</h3>
				<div class="extra-r">
					<a href="../cart/cart.jsp" class="r-color">返回修改购物车</a>
				</div>
			</div>
			


<div id="suppliers">
<!--
			<div class="">
	 		<div class="detailed-newlist">
			  <div class="price-tit tit-height"><span class="price-color">金额：</span><span class="price-num" style="font-size:14px;">￥329.00</span></div>
			  <div class="price-tit tit-height"><span class="price-color">数量：</span><span>1111</span></div>
			  <div class="price-tit tit-height"><span class="price-color">配送时间：</span><div class="mode-infor" >&nbsp;<a id="gettimey">2017</a>年<a id="gettimem">6</a>月<a id="gettimed">6</a>日&nbsp;&nbsp;<a id="gettimew">[周二]</a>&nbsp;&nbsp;送达  <span style="color:red" onClick="HS_setDate(this)">修改</span></div>
			  </div>
			  <div class="price-tit"><span class="price-color">配送方式:</span>
			  <div class="clear"></div>
			  <div class="express-checkbox2" style=" height:30px;">
			  <input name="" type="checkbox" value="" id="checkboxOneInputg"/>
			   
			  <label for="checkboxOneInputg">供应商配送</label> 
			   
			  </div>
			  <div class="express-checkbox3" style=" height:30px;">
			  <input name="" type="checkbox" value="" id="checkboxOneInputz"/>
			   
			  <label for="checkboxOneInputz">自提</label> 
			   
			  </div>
			  </div>
			  <div class="order-details-botton">订单详情</div>
			  <div>
			  
			  </div>
			  
			</div>
			
			<div class="order-details-div">
			<div class="goods-tit"><h4 class="vendor_name_h">商家：京东自营</h4></div>
			
			  <div class="goods-item">
			    <div>
				   <div class="p-img"><img src="../../../settle/settle/images/5.jpg"></div>
				   <div class="goods-msg">手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表</div>
				   <div class="p-price">
				       <span class="title-attribute">材料属性材料属性材料属性材料属性材料属性料属性</span>
					   <span class="title-brand">品牌品牌</span>
				       <strong class="price-tot">￥329.00</strong>
					   <span class="p-num">x2</span>
					   <span class="price-tot">小计</span>
					   
				   </div>

				   <div class="clear"></div>
				</div>
				<div>
				   <div class="p-img"><img src="../../../settle/settle/images/5.jpg"></div>
				   <div class="goods-msg">手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表</div>
				   <div class="p-price">
				       <span class="title-attribute">材料属性材料属性材料属性材料属性材料属性料属性</span>
					   <span class="title-brand">品牌品牌</span>
				       <strong class="price-tot">￥329.00</strong>
					   <span class="p-num">x2</span>
					   <span class="price-tot">小计</span>
					   
				   </div>

				   <div class="clear"></div>
				</div>
				<div class="return">
	 			<span class="total-amount" id="total-amount">￥0</span>
	 		</div>
			  </div>
			</div>
			<div class="clear"></div>
			</div>


	<div class="">
	 		<div class="detailed-newlist">
			  <div class="price-tit tit-height"><span class="price-color">金额：</span><span class="price-num" style="font-size:14px;">￥329.00</span></div>
			  <div class="price-tit tit-height"><span class="price-color">数量：</span><span>1111</span></div>
			  <div class="price-tit tit-height"><span class="price-color">配送时间：</span><div class="mode-infor" >&nbsp;<a id="gettimey">2017</a>年<a id="gettimem">6</a>月<a id="gettimed">6</a>日&nbsp;&nbsp;<a id="gettimew">[周二]</a>&nbsp;&nbsp;送达  <span style="color:red" onClick="HS_setDate(this)">修改</span></div>
			  </div>
			  <div class="price-tit"><span class="price-color">配送方式:</span>
			  <div class="clear"></div>
			  <div class="express-checkbox2" style=" height:30px;">
			  <input name="" type="checkbox" value="" id="checkboxOneInputg"/>
			   
			  <label for="checkboxOneInputg">供应商配送</label> 
			   
			  </div>
			  <div class="express-checkbox3" style=" height:30px;">
			  <input name="" type="checkbox" value="" id="checkboxOneInputz"/>
			   
			  <label for="checkboxOneInputz">自提</label> 
			   
			  </div>
			  </div>
			  <div class="order-details-botton">订单详情</div>
			  <div>
			  
			  </div>
			  
			</div>
			
			<div class="order-details-div">
			<div class="goods-tit"><h4 class="vendor_name_h">商家：京东自营</h4></div>
			  <div class="goods-item">
			    <div>
				   <div class="p-img"><img src="../../../settle/settle/images/5.jpg"></div>
				   <div class="goods-msg">手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表手表</div>
				   <div class="p-price">
				       <span class="title-attribute">材料属性材料属性材料属性材料属性材料属性料属性</span>
					   <span class="title-brand">品牌品牌</span>
				       <strong class="price-tot">￥329.00</strong>
					   <span class="p-num">x2</span>
					   <span class="price-tot">小计</span>
					   
				   </div>
				   <div class="clear"></div>
				</div>
				<div class="return">
	 			<span class="total-amount" id="total-amount">￥0</span>
	 		</div>
			  </div>
			</div>
			<div class="clear"></div>
			</div>
			-->
			
			</div>





			<div class="step-tit"><h3>老师留言</h3></div>
			<textarea name="message" rows="4" cols="148" style="width:100%"></textarea>
			  <div class="clear"></div>
			</div>
	 		
			<!--<a onclick="submitorder()">提交订单</a>-->
	<div style="padding:120px 10px 15px 0;"></div>
	<div class="trade-foot-detail-com">

	 <div class="fc-price-info">
	   <span class="price-tit">课题组：</span>
	   <span class="price-num" id="sgroup"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   <span class="price-tit">支付方式：</span>
	   <span class="price-num" id="paytype">项目支付,请选择支付项目</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   <span class="price-tit">应付总额：</span>
	    <span class="price-num"  >￥</span><span class="price-num" id="payamt">￥329.00</span>
	 </div>
	 <div class="fc-consignee-info"  class="mr20" >
	   <span class="mr20" id="finalgetaddress"></span>
	 <!--  <span id="finalgetname">收货人:吴斌 156****5895</span>-->
<div class="botton-layer">
	 	<button type="submit" class="checkout-submit" onClick="submitorder(<%=cartids%>)">提交订单
	 		 <b></b>
	 	</button>
	 	
	 </div>
	 </div>  
	 	
	 </div> 
	 
	</div>
 
	
</body>
<script type="text/javascript">
$(document).ready(function(){
  $("#add").click(function(){
  $("#addshow").toggle();
  });
  
  $("#topic-choice").click(function(){
  $(".s-choice").toggle();
	groupchange();
  });
});
      $('#ul_list li').click(function(){
          
          //1.点击li的时候要切换样式
          $(this).addClass('current').siblings().removeClass('current');
		  var k = $(this).attr("id");
		  if(k=="XM"){
				$("#paytype").html("本人项目");
				
		  }else if(k=="OT"){
				$("#paytype").html("他人项目");
		  }
          //2.根据li的索引值,来确定哪个div显示.其它div隐藏
          $('#content_list>#two').eq($(this).index()).show().siblings().hide();

      });

	  
    </script>
    <script>
$(function(){
	var i=0;
	$("#add").click(function(){
		
		
	    var select_html = $("#pro").html();
		
		
		 var select_html = $("#pro").html();
		$("#list1").append('<tr class="addp"><td>支付项目<select  name="pro" >'+select_html+'</select><span> 项目金额<input type="text" style="width:64px;" name = "pro_money"/></span> <span  style="display: inline;" class="proinfo"></span> <span   style="display:none;  class="proid" "></span><span  style="display:none; class="chargecode"></span><span     class="chargename" style="display:none;></span><span class="add_myclass"    onclick="deleteTr(this)"><img src="./images/close.png"/style="vertical-align:middle; margin-bottom:5px;cursor: pointer;width:22px; height:22px;"/></span>'+'</td></tr>');
		i++;
		
	});

})

function deleteTr(obj){
	$(obj).parent().parent().remove();
}
</script>

</html>