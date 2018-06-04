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

	//获得当前时间
	var myDate = new Date();
	var time = myDate.toLocaleString(); //获取日期与时间
	var supplier_name = window.parent.supplier_name;
	//先获取到相关变量值
	var batNo = window.parent.batNo;
	var total = window.parent.total;
    var MultiRows = window.parent.MultiRows;
	var unitName = window.parent.supplier;
	var req_date = window.parent.req_date;
	var url = document.URL;
	var uarr = url.split('/');
    function toExcelData() {
        // var table = $('#printContent').DataTable();
        $("#printContent").table2excel({
            exclude: ".excludeThisClass",
            name: "Worksheet Name",
            fileext: ".xls",
            filename: unitName+"结算单"+".xls"
        });
        alert("导出成功！");
    }
	function orderbuy(printContent, tojsons) {
		//var unitName =  "结算单位XXXXX";
		var tojsons = eval(tojsons);
		var v_amount = 0;
		//v_amount = v_amount + parseInt(arr_details[i].purchase_num);
		$.each(tojsons, function(i) {
			$.each(tojsons[i].list, function(i,arr){
				v_amount = v_amount + arr.total*1;
			});
		});
		var myDate = new Date();
		var time = myDate.toLocaleString(); //获取日期与时间
		var url = document.URL;
		var html = "";
		html += ('<div class="printdiv" id = "printdiv"> ');
		html += '<br>';
		html += ('<thead  border="0" align="center" cellpadding="0" cellspacing="0">');
		html += ('<tr align="center">');
		html += ('<td style="border: 0px;" align="center" colspan="7" ><h3>结算单位:' + unitName + '</h3></td>');
		html += ('</tr>');
		html += ('</thead>');
		html += ('<br>');
		html += ('<tbody   border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 20px;border:1px #999999 solid;line-height: 22px">');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td style="border:0px" border="0" align="left" colspan="7">&nbsp;</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td style="border-bottom:0px;border-right:0px;border-left:0px" align="left" colspan="7">&nbsp;打印时间：'	+ time + '</td>');
		html += ('</tr>');
		html += ('<tr align="left" class="text" style="line-height:28px;">');
		html += ('<td style="border:0px" colspan="7" align="right">总金额：'+Number(v_amount).toFixed(2) +'</td>');
		html += ('</tr>');
		html += ('</tbody>');
		html += ('<tbody class="print_table" width="90%" border="1" cellpadding="0" cellspacing="0">');

		$.each(tojsons, function(i) {
							var cols = tojsons[i];
							var pici = cols.pici;// 批次
							var arr_details = cols.list;
							html += '<tr class=text align="center" >';
							html += '<td style="border:none" width="3%">批次&nbsp;</td>';
							html += '<td align="left" colspan="2" style="border:none"><h4 >';
							html += pici;
							html += '</4></td>';
							html += '</tr>'
							html += '<tr class=text align=center >';
							html += '<td >序号</td>';
							html += '<td >商品名称&nbsp;</td>';
							html += '<td >入库数量&nbsp;</td>';
							html += '<td >包装单位&nbsp;</td>';
							html += '<td >商品规格&nbsp;</td>';
							html += '<td >单价&nbsp;</td>';
							html += '<td >小计&nbsp;</td>';
							//html += '<td >采购金额&nbsp;</td>';
							html += '</tr>';
							$.each(arr_details, function(i) {
									var id = arr_details[i].id;
									var product_name = arr_details[i].product_name;
									var product_id = arr_details[i].product_id;
									var in_num = arr_details[i].in_num;
									var package_unit = arr_details[i].package_unit;
									var specifications = arr_details[i].specifications;
									var in_price = arr_details[i].in_price;
									var total = arr_details[i].total;
									html += '<tr class=text align=center width="100%" >';
									html += '<td >' + (i + 1)+ '</td>';
									html += '<td >' + product_name + '</td>';
									html += '<td>' +in_num + '</td>';
									html += '<td >' + package_unit+ '</td>';
									html += '<td >' +specifications+ '</td>';
									html += '<td align="right">' +Number(in_price).toFixed(2)+ '</td>';
									html += '<td align="right">' +Number(total).toFixed(2)+ '</td>';
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
		//var MultiRows = window.parent.MultiRows;
		//MultiRows = "I20180503D2";
		$.ajax({
			type : "POST",
			url : 'settlePrint_dataFromDB.action',
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
		<tr align="center">
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td><input name="BtnPrint" type="button"  value="导出表格" onClick="toExcelData();"></td>
		</tr>
	</table>
	<table id="printContent" width="100%" align="center" cellpadding="0" cellspacing="0"  border="1">

	</table>
</body>

</html>