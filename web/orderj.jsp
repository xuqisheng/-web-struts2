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

	<script src="../jquery/custom/jquery.min.js"></script>
	<script src="../jquery/custom/jquery.table2excel.min.js"></script>
<title>采购单</title>
<script>
    var supplier_name = window.parent.supplier_name;
    var MultiRows = window.parent.MultiRows;
    function toExcelData() {
        $("#printContent").table2excel({
            exclude: ".excludeThisClass",
            name: "Worksheet Name",
            exclude_inputs: false,
            fileext: ".xls",
            filename: supplier_name+"采购单"+".xls"
        });
        alert("导出成功！");
    }

	//获得当前时间
	var myDate = new Date();
	var time = myDate.toLocaleString(); //获取日期与时间

	//先获取到相关变量值
	var batNo = window.parent.batNo;
	var total = window.parent.total;
	var req_date = window.parent.req_date;
	var url = document.URL;
	var uarr = url.split('/');

	function orderbuy(printContent, tojsons) {
		var tojsons = eval(tojsons);
		var v_amount = 0;
		$.each(tojsons, function(i) {
			var cols = tojsons[i];
			var arr_details = cols.details;
			$.each(arr_details, function(i) {
				var purchase_num = arr_details[i].purchase_num;
				v_amount = v_amount + parseInt(arr_details[i].purchase_num);
			});
		});
		var myDate = new Date();
		var time = myDate.toLocaleString(); //获取日期与时间
		var url = document.URL;
		var uarr = url.split('/');
		var html = "";
		html += ('<div class="printdiv"id = "printdiv"> ');
		html += '<br>';
		//条形码
		html += ('<thead   border="0" align="center" cellpadding="0" cellspacing="0">');
		html += ('<tr align="center" class="TitleStyle">');
		html += ('<td align="center" style="border:0px;" colspan="7"><h3>采购单位:' + supplier_name + '</h3></td>');
		html += ('</tr>');
		html += ('</thead>');
		html += ('<br>');
		html += ('<thead   border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 20px;border:1px #999999 solid;line-height: 22px">');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td colspan="7" style="border:0px;">&nbsp;</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td style="border-bottom:0px;border-right:0px;border-left:0px" colspan="7"  align="left" class="title">&nbsp;打印时间：'
				+ time + '</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td colspan="7" style="border:0px;" >&nbsp;</td>');
		html += ('</tr>');
		html += ('</thead>');
		html += ('<tbody class="print_table"  border="1" cellpadding="0" cellspacing="0">');

		$.each(tojsons,
						function(i) {
							var cols = tojsons[i];
							var no = cols.id;// 编号
							if (cols.class_id.length == 0) {
								cols.class_id = "中心仓库";
							}
							var class_name = cols.class_id;
							var arr_details = cols.details;
							html += '<tr class=text align="center" >';
							html += '<td style="border:none" width="3%">&nbsp;</td>';
							html += '<td align="center" colspan="2"  style="border:none"><h4 >';
							html += class_name;
							html += '</4></td>';
							html += '</tr>'
							html += '<tr class=text align=center >';
							html += '<td nowrap class="title" >序号</td>';
							html += '<td nowrap class="title" >商品名称&nbsp;</td>';
							html += '<td nowrap class="title" >采购单编号&nbsp;</td>';
							html += '<td nowrap class="title" >采购规格&nbsp;</td>';
							html += '<td nowrap class="title" >包装单位&nbsp;</td>';
							html += '<td nowrap class="title" >备注&nbsp;</td>';
							html += '<td nowrap class="title" >采购数量&nbsp;</td>';
							//html += '<td nowrap class="title" >采购金额&nbsp;</td>'; 
							html += '</tr>';
							$.each(arr_details,
									function(i) {
									var id = arr_details[i].id;
									var package_unit = arr_details[i].package_unit;
									var product_id = arr_details[i].product_id;
									var purchase_id = arr_details[i].purchase_id;
									var purchase_num = arr_details[i].purchase_num;
									var specifications = arr_details[i].specifications;
									var remarks = arr_details[i].remarks;
									html += '<tr class=text align=center width="100%" >';
									html += '<td >' + (i + 1)+ '</td>';
									html += '<td >' + product_id+ '</td>';
									html += '<td >' + purchase_id+ '</td>';
									html += '<td>' + specifications+ '</td>';
									html += '<td align=center>'+ package_unit+ '</td>';
									html += '<td >' + remarks+ '</td>';
									html += '<td >' + purchase_num+ '</td>';
									html += '</tr>'
											});
						});
        html += '</tbody>';

        html += ('<tfoot  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 22px;border:1px #999999 solid;line-height: 18px">');
        html += ('<tr align="left" class="text" style="line-height:28px;">');
        html += ('<td colspan="7" style="border: 0px;"  align="left" class="title">&nbsp;</td>');
        html += ('</tr>');
        html += ('<tr align="left" style="line-height:28px;">');
        html += ('<td style="border: 0px;" colspan="2" align="left" >经办人（签名）：</td>');
        html += ('<td style="border: 0px;" colspan="2" align="left" >证明人（签名）：</td>');
        html += ('<td style="border: 0px;" colspan="3" align="left" >审批人（签名）：</td>');
        html += ('</tr>');
        html += '</tfoot>';

		html += ('</div>');

		return html;
	}
	$(function() {
        //MultiRows ="Z20180504D1";
		//var MultiRows = "Z20180502D1";
		$.ajax({
			type : "POST",
			url : 'OrderPrintAction_printDoOrderF.action',
			dataType : "json",
			data : {
				"MultiRows" : MultiRows
			},
			success : function(data, textStatus) {
				var printContent = $('#printContent');
				printContent.empty();
				var html = "";
				if (data) {
					html = orderbuy(printContent, data.json);
				}
				// console.log(html);
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
				value="打印订购单" onClick="doprint();"></td>
		</tr>
		<tr align="center">
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td><input name="BtnPrint" type="button"  value="导出表格" onClick="toExcelData();"></td>
		</tr>
	</table>
	<table id="printContent" width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" >

	</table>
</body>

</html>