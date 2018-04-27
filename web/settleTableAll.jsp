<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>采购单</title>

<script>

	//获得当前时间
	var myDate = new Date();
	var time = myDate.toLocaleString(); //获取日期与时间
	var supplier_name = window.parent.supplier_name;
	//先获取到相关变量值
	var batNo = window.parent.batNo;
	var total = window.parent.total;
	var req_date = window.parent.req_date;
	var url = document.URL;
	var uarr = url.split('/');

	function orderbuy(printContent, tojsons) {
		var unitName =  "结算单打印";
		var tojsons = eval(tojsons);
		var v_amount = 0;
		$.each(tojsons, function(i) {
			$.each(tojsons[i].list, function(j,apt){
						$.each(apt.list, function(k,arr){
							$.each(arr.list, function(l,aar){
								v_amount = v_amount + aar.total*1;
						});
					});
				});
		});
		var myDate = new Date();
		var time = myDate.toLocaleString(); //获取日期与时间
		var url = document.URL;
		var uarr = url.split('/');
		var html = "";
		html += ('<div class="printdiv"id = "printdiv"> ');
		html += '<br>';
		html += ('<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">');
		html += ('<tr align="center" class="TitleStyle">');
		html += ('<td align="center"  width="40%"><h1>' + unitName + '</h1></td>');
		html += ('</tr>');
		html += ('</table>');
		html += ('<br>');
		html += ('<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 20px;border:1px #999999 solid;line-height: 22px">');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td width="30%" align="left" class="title">&nbsp;</td>');
		html += ('<td width="30%" align="right" class="title">&nbsp;</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td width="30%" align="left" class="title">&nbsp;打印时间：'
				+ time + '</td>');
		html += ('<td width="30%" align="right" class="title">&nbsp;</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td width="30%" align="left" class="title">&nbsp;</td>');
		html += ('<td width="30%" align="right" class="title">总金额：'+Number(v_amount).toFixed(2) +'</td>');
		html += ('</tr>');
		html += ('</table>');
		html += ('<table class="print_table" width="90%" border="1" cellpadding="0" cellspacing="0">');

		$.each(tojsons, function(i) {
							var cols = tojsons[i];
							var supplier_name = cols.supplier_name;// 批次
							var listp = cols.list;
							html += '<tr class=text align="center" >';
							html += '<td style="border:none" width="7%"><h2>&nbsp;</h2></td>';
							html += '<td align="center" colspan="6"  style="border:none"><h2>';
							html += supplier_name;
							html += '</2></td>';
							html += '</tr>';
		//遍历审核单
		$.each(listp,function(j){
			var cols = listp[j];
			var ar_details = cols.list;
			html += '<tr class=text align="center" >';
			html += '<td style="border:none" width="3%"><h3>审核单号&nbsp;</h3></td>';
			html += '<td align="center"  width="15%" style="border:none"><b>';
			html += cols.apply_ord;
			html += '</b></td>';
			html += '</tr>';
			//遍历批次
			$.each(ar_details, function(l) {
				var cols = ar_details[l];
				var arr_details = cols.list;
							html += '<tr class=text align=center >';
							html += '<td class="title" style="border:none"><strong>批次</strong></td>';
							html += '<td style="border:none"  class="title" ><strong>'+cols.pici+'</strong></td>';
							html += '</tr>';			
							html += '<tr class=text align=center >';
							html += '<td nowrap class="title" >序号</td>';
							html += '<td nowrap class="title" >商品名称&nbsp;</td>';
							html += '<td nowrap class="title" >入库数量&nbsp;</td>';
							html += '<td nowrap class="title" >包装单位&nbsp;</td>';
							html += '<td nowrap class="title" >商品规格&nbsp;</td>';
							html += '<td nowrap class="title" >单价&nbsp;</td>';
							html += '<td nowrap class="title" >小计&nbsp;</td>';
							html += '</tr>';
							
							$.each(arr_details, function(k) {
									var id = arr_details[k].id;
									var product_name = arr_details[k].product_name;
									var in_num = arr_details[k].in_num;
									var package_unit = arr_details[k].package_unit;
									var specifications = arr_details[k].specifications;
									var in_price = arr_details[k].in_price;
									var total = arr_details[k].total;
									html += '<tr class=text align=center width="100%" >';
									html += '<td >' +(k+1)+ '</td>';
									html += '<td >' +product_name+ '</td>';
									html += '<td >' +in_num+ '</td>';
									html += '<td>' +package_unit+ '</td>';
									html += '<td >' +specifications+ '</td>';
									html += '<td align="right">' +Number(in_price).toFixed(2)+ '</td>';
									html += '<td align="right">' +Number(total).toFixed(2)+ '</td>';
									html += '</tr>';
							
									});
							html += '<tr class=text align=center >';
							html += '<td class="title" style="border:none">&nbsp;</td>';
							html += '<td style="border:none"  class="title" >&nbsp;</td>';
							html += '</tr>';
							
					});
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
	$(function() {
		//传入id
		var MultiRows = window.parent.MultiRows;
		//var MultiRows = "126;125;123;122;121;102;101";
		$.ajax({
			type : "POST",
			url : 'settlePrint_allDataFromDB.action',
			dataType : "json",
			data : {
				"multiParams" : MultiRows,
			},
			success : function(data, textStatus) {
				var printContent = $('#printContent');
				printContent.empty();
				var html = "";
				if (data) {
					html = orderbuy(printContent, data.json);
				}
				printContent.html(html);
			},
			error : function(e) {
				alert('error:' + e);
			}
		});

	});
	function doprint() {
		if ($.browser.msie) {
			document.execCommand('print', false, null);
		} else {
			window.print();
		}
	}
	
</script>
</head>
<body>
	<table width="100%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr align="center">
			<td><input name="BtnPrint" type="button" id="BtnPrint"
				value="打印结算" onClick="doprint();"></td>
		</tr>
	</table>
	<div id="printContent" width="100%" align="center"
		style="overflow: auto;">
	</div>
</body>

</html>