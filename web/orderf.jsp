<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="../jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="../jquery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="../core/common.js"></script>
    <script type="text/javascript" src="../core/customWin.js"></script>
    <script type="text/javascript" src="../core/userContext.js"></script>
    <script type="text/javascript" src="../jquery/printf.js"></script>
<title>采购单</title>
<script>
//*生成打印页面*/
function atoc(numberValue){
	var numberValue=new String(Math.round(numberValue*100)); // 数字金额
	var chineseValue=""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len=numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero=0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if(len>15){
	alert("超出计算范围");
	return "";
	}
	if (numberValue==0){
	chineseValue = "零元整";
	return chineseValue;
	}
	String2 = String2.substr(String2.length-len, len); // 取出对应位数的STRING2的值
	for(var i=0; i<len; i++){
	String3 = parseInt(numberValue.substr(i, 1),10); // 取出需转换的某一位的值
	if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){
	if ( String3 == 0 ){
	Ch1 = "";
	Ch2 = "";
	nZero = nZero + 1;
	}
	else if ( String3 != 0 && nZero != 0 ){
	Ch1 = "零" + String1.substr(String3, 1);
	Ch2 = String2.substr(i, 1);
	nZero = 0;
	}
	else{
	Ch1 = String1.substr(String3, 1);
	Ch2 = String2.substr(i, 1);
	nZero = 0;
	}
	}
	else{ // 该位是万亿，亿，万，元位等关键位
	if( String3 != 0 && nZero != 0 ){
	Ch1 = "零" + String1.substr(String3, 1);
	Ch2 = String2.substr(i, 1);
	nZero = 0;
	}
	else if ( String3 != 0 && nZero == 0 ){
	Ch1 = String1.substr(String3, 1);
	Ch2 = String2.substr(i, 1);
	nZero = 0;
	}
	else if( String3 == 0 && nZero >= 3 ){
	Ch1 = "";
	Ch2 = "";
	nZero = nZero + 1;
	}
	else{
	Ch1 = "";
	Ch2 = String2.substr(i, 1);
	nZero = nZero + 1;
	}
	if( i == (len - 11) || i == (len - 3)){ // 如果该位是亿位或元位，则必须写上
	Ch2 = String2.substr(i, 1);
	}
	}
	chineseValue = chineseValue + Ch1 + Ch2;
	}
	if ( String3 == 0 ){ // 最后一位（分）为0时，加上“整”
	chineseValue = chineseValue + "整";
	}
	return chineseValue;
	}

//获得当前时间
var myDate = new Date();
var time = myDate.toLocaleString( ); //获取日期与时间
var supplier_name = window.parent.supplier_name; 

//先获取到相关变量值
var batNo = window.parent.batNo;
var total = window.parent.total;
var req_date = window.parent.req_date;
var url = document.URL;  
var uarr = url.split('/');

function orderbuy(printContent,tojsons){
	var tojsons = eval(tojsons);
	var v_mount = 0;
	$.each(tojsons,function(i){
		var cols = tojsons[i];
		var arr_details = cols.provide_list;
		if (arr_details.length!=0){
			$.each(arr_details,function(i){
				var clazz = arr_details[i];
				clazz_list = clazz.clazz_name;
				if (clazz_list.length!=0){
					$.each(clazz_list,function(i){
						var purchase_num = clazz_list[i].purchase_num;
						v_mount= v_mount + parseInt(purchase_num);
					});
				}
			});
		}
	});
	
	
	
	var myDate = new Date();
	var time = myDate.toLocaleString( ); //获取日期与时间
var url = document.URL;  
var uarr = url.split('/');
var html = "";
html += ('<div class="printdiv"id = "printdiv"> ');
			html += '<br>';
			//条形码
			html += ('<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">');
			html += ('<tr align="center" class="TitleStyle">');
			html += ('<td align="center"  width="40%"><h1>采购单</h1></td>');
			html += ('</tr>');
			html += ('</table>');
			html += ('<br>');
			html += ('<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 20px;border:1px #999999 solid;line-height: 22px">');
			html += ('<tr align="left" class="text" style="line-height:28px;">');
			html += ('<td width="30%" align="left" class="title">&nbsp;</td>');
     		html += ('<td width="30%" align="right" class="title">&nbsp;</td>');
			html += ('</tr>');
			html += ('<tr align="left" class="text" style="line-height:28px;">');
			html += ('<td width="30%" align="left" class="title">&nbsp;打印时间：'+time+'</td>');
     		html += ('<td width="30%" align="right" class="title">采购总金额：'+Number(v_mount).toFixed(2)+'&nbsp;</td>');
			html += ('</tr>');
			html += ('<tr align="left" class="text" style="line-height:28px;">');
			html += ('<td width="30%" align="left" class="title">&nbsp;</td>');
     		html += ('<td width="30%" align="right" class="title"></td>');
     		html += ('</tr>');
			html += ('</table>');
			html += ('<table class="print_table" width="90%" border="1" cellpadding="0" cellspacing="0">');
$.each(tojsons,function(i){
		var cols = tojsons[i];
    	var no = cols.id;// 编号
    	var provide_name = cols.provide_name;
    	var arr_details = cols.provide_list;
		html += '<tr class=text align="center" >';
		html += '<td  colspan="7" align="center" style="border:none;font-weight:bold;font-size:26px;" >&nbsp;';
		html += provide_name;
		html += "</td>";
	   	html += '<td align="left" colspan="2"  style="border:none">';
		html += '</td>';
		html += '</tr>'
		
    	$.each(arr_details,function(i){
    	var clazz = arr_details[i];
    	var clazz_id = clazz.clazz_id;
    	var clazz_list = clazz.clazz_name;
    	if (clazz_id.length==0){
    		clazz_id ="中心仓库";
    	}
		if(clazz.clazz_name.length!=0){
			html += '<tr>';
	    	html += '<td width=20% style="border:none;font-weight:bold;" class=text align=center >'+clazz_id+'</td>'; 
			html += '</tr>';
			html += '<tr class=text align=center >';
			html += '<td nowrap class="title" >序号</td>';
			html += '<td nowrap class="title" >商品名称&nbsp;</td>';
			html += '<td nowrap class="title" >采购单编号&nbsp;</td>';
			html += '<td nowrap class="title" >采购规格&nbsp;</td>'; 
			html += '<td nowrap class="title" >包装单位&nbsp;</td>';
			html += '<td nowrap class="title" >备注&nbsp;</td>';
			html += '<td nowrap class="title" >采购金额&nbsp;</td>'; 
			html += '</tr>';
			
			$.each(clazz_list,function(i){
				var claz_li = clazz_list[i];
				var ids = claz_li.id;
				var purchase_id = claz_li.purchase_id;
				var product_name = claz_li.product_name;
				var remarks = claz_li.remarks;
				var specifications = claz_li.specifications;
				var package_unit = claz_li.package_unit;
				var purchase_num = claz_li.purchase_num;
				html += '<tr class=text align=center width="100%" >';
				html += '<td >'+(i+1)+'</td>';
				html += '<td >'+product_name+'</td>';
				html += '<td >'+purchase_id+'</td>';
		        html += '<td >'+specifications+'</td>';
		        html += '<td>'+package_unit+'</td>';
		        html += '<td align=center>'+remarks+'</td>';
				html += '<td >'+Number(purchase_num).toFixed(2)+'</td>';
				html += '</tr>';
				
			});
		}    	
		    });
	   });
	
        html += '</table>';
        
		html += ('<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 22px;border:1px #999999 solid;line-height: 18px">');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td width="12%" align="left" class="title">&nbsp;</td>');
		html += ('<td width="12%" align="left" class="title">&nbsp;</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td width="33%" align="left" class="title">经办人（签名）：</td>');
		html += ('<td width="33%" align="left" class="title">证明人（签名）：</td>');
		html += ('<td width="33%" align="left" class="title">审批人（签名）：</td>');
		html += ('</tr>');
		html += '</table>';	
		
		html += ('</div>');
return html;
}
  $(function(){
	  var MultiRows = window.parent.MultiRows;
	  //MultiRows = "F20180315D4;F20180315D2;F20180315D5;P20180315D4;P20180316D2";
  	$.ajax({
		type: "POST",
		url:  'OrderPrintAction_printAllOrder.action',
		dataType: "json",
		data: {"MultiRows":MultiRows},
		success: function(data,textStatus){
			var printContent = $('#printContent');
  			printContent.empty();
  			var html  ="";
			if(data){
				html = orderbuy(printContent,data.json);
			}
			printContent.html(html);			
		},
		error:function(e){
			alert('error:'+e);
		}
	});

	});
	function doprint(){
  if($.browser.msie){
			document.execCommand('print', false, null);
		}else{
			window.print();
		}
	}
	
</script>
</head>
<body>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr align="center">
		<td><input name="BtnPrint" type="button" id="BtnPrint" value="打印订购单" onClick="doprint();"></td>
	  </tr>
	</table>
	 <div id="printContent" width="100%" align="center" style="overflow:auto;">
	 </div>	
</body>
<script>
</script>

</html>