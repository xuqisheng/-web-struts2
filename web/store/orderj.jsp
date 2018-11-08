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
	<script src="../jquery/custom/jquery.min.js"></script>
	<script src="../jquery/custom/jquery.table2excel.min.js"></script>
<title>采购单</title>
<script>
	// var order_flag = -1;
	var collect = window.parent.collect;
    var startTime = window.parent.startTime;
    var endTime = window.parent.endTime;
    var userName = window.parent.userName;
    // var startTime= "2010-01-01";
    // var endTime = "2019-01-01";
    // var supplier_name = window.parent.supplier_name;
    var MultiRows = window.parent.MultiRows;
    var order_flag = window.parent.order_flag;
    // console.log(supplier_name);
    // console.log(MultiRows);
    // console.log(order_flag);
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
	var supplier_name ='';
	function orderbuy(printContent, tojsons) {
		var tojsons = eval(tojsons);

		var v_amount = 0;
		$.each(tojsons, function(i) {
			var cols = tojsons[i];
			var arr_details = cols.list;
			$.each(arr_details, function(i) {
                supplier_name = arr_details[i].provide_name;
                var purchase_num = arr_details[i].purchase_num;
				v_amount = v_amount + parseInt(arr_details[i].purchase_num);
			});
		});
		var myDate = new Date();
		var time = myDate.toLocaleString(); //获取日期与时间
		var url = document.URL;
		var uarr = url.split('/');
		var html = "";
		html += ('<div class="printdiv" id = "printdiv"> ');
		html += '<br>';
		//条形码
		html += ('<thead width="80%"   border="0" align="center" cellpadding="0" cellspacing="0">');
		html += ('<tr align="center" class="TitleStyle">');
		html += ('<td align="center" style="border:0px;" colspan="8">');
        collect ==1?html += ('<h3>采购部 送货单(汇总)</h3>'):html += ('<h3>采购部 送货单(不汇总)</h3>');
		html += ('</td></tr>');
		html += ('</thead>');
		html += ('<br>');
		html += ('<thead   border="0" align="center" cellpadding="0" cellspacing="0" ' +
			'style="border:none;">');
		html += ('<tr align="left" class="text" style="border:none;">');
            html += ('<td style="border:none;" colspan="8" style="border:0px;">&nbsp;</td>');
		html += ('</tr>');

		html += ('<tr align="left" class="text" style="border:none;">');
        html += ('<tr align="left" class="text" style="line-height:28px;">');
        html += ('<td colspan="8" style="border:0px;" >&nbsp;</td>');
        html += ('</tr>');
		if(order_flag*1==-1){
            html += ('<td style="border-bottom:0px;border-right:0px;border-left:0px" colspan="8"  align="left" class="title">&nbsp;打印时间：'
                + time + '</td>');
		}
        else {
            // html += ('<td style="border:none;" colspan="1"  align="left" class="title">&nbsp;</td>');
            html += ('<td style="border:none;" colspan="4"  align="left" class="title">&nbsp;&nbsp;&nbsp;&nbsp;送货时间：'
					//time
                + startTime  + '至'+ endTime +'</td>');
            html += ('<td style="border:none;" colspan="4"  align="left" class="title">&nbsp;送货单位：'
                + supplier_name + '</td>');
		}
		html += ('</tr>');
        // html +=('<tr>' +
        //     '<td style="border: 0px;text-align: right;" colspan="8" align="right">采购日期:'+startTime+'至'+endTime+'</td>\n' +
        //     '</tr>');

		html += ('</thead>');
		html += ('<tbody width="70%" class="print_table"  border="1" cellpadding="0" cellspacing="0">');

		$.each(tojsons, function(i) {
							var cols = tojsons[i];
							var no = cols.id;// 编号
						// console.log(cols);
							if (cols.custom_name.length == 0) {
								cols.custom_name = "中心仓库";
							}
							// var class_name = cols.class_id;
							var class_name = cols.custom_name;
							var arr_details = cols.list;

							// var arr_details = cols.details;
							html += '<tr class=text align="center" >';
							// html += '<td style="border:none" width="3%">&nbsp;</td>';
							html += '<td align="center" colspan="3"  style="border:none"><h4 >';
							html += class_name;
							html += '</4></td>';
							html += '</tr>'
							html += '<tr class=text align=center >';
							// html += '<td nowrap class="title" >序号</td>';
							html += '<td width="15%" nowrap class="title" >商品名称&nbsp;</td>';
							// html += '<td nowrap class="title" >采购单编号&nbsp;</td>';
							// html += '<td nowrap class="title" >采购规格&nbsp;</td>';
							html += '<td nowrap class="title" >单位&nbsp;</td>';
            				html += '<td nowrap class="title" >计划数量&nbsp;</td>';
							html += '<td nowrap class="title" >实际数量&nbsp;</td>';
							if(order_flag*1==-1)
								html += '<td nowrap class="title" >采购单价&nbsp;</td>';
//并排的
            html += '<td width="15%" nowrap class="title" >商品名称&nbsp;</td>';
            // html += '<td nowrap class="title" >采购单编号&nbsp;</td>';
            // html += '<td nowrap class="title" >采购规格&nbsp;</td>';
            html += '<td nowrap class="title" >单位&nbsp;</td>';
            html += '<td nowrap class="title" >计划数量&nbsp;</td>';
            html += '<td nowrap class="title" >实际数量&nbsp;</td>';
            if(order_flag*1==-1)
                html += '<td nowrap class="title" >采购单价&nbsp;</td>';
							html += '</tr>';
							// $.each(arr_details, function(i) {
				for(var i=0;i<arr_details.length;i+=2){
									var id = arr_details[i].id;
									var package_unit = arr_details[i].package_unit;
									var product_id = arr_details[i].product_name;
									// var purchase_id = arr_details[i].purchase_id;
									var purchase_num = arr_details[i].purchase_num;
									// var specifications = arr_details[i].specifications;
									var remarks = arr_details[i].remarks;
									var price = arr_details[i].price;
									html += '<tr class=text align=center width="100%" >';
									// html += '<td >' + (i + 1)+ '</td>';
									html += '<td >' + product_id+ '</td>';
									// html += '<td >' + purchase_id+ '</td>';
									// html += '<td>' + specifications+ '</td>';
									html += '<td align=center>'+ package_unit+ '</td>';
									html += '<td >' + purchase_num+ '</td>';
									html += '<td >' + remarks+ '</td>';
									if(order_flag*1==-1)
										html += '<td >'+Number(price).toFixed(2)+'</td>';
					if(i+1<arr_details.length){
                        var package_unit2 = arr_details[i+1].package_unit;
                        var product_id2 = arr_details[i+1].product_name;
                        // var purchase_id = arr_details[i].purchase_id;
                        var purchase_num2 = arr_details[i+1].purchase_num;
                        // var specifications = arr_details[i].specifications;
                        var remarks2 = arr_details[i+1].remarks;
                        var price2 = arr_details[i+1].price;

                        // html += '<td >' + (i + 1)+ '</td>';
                        html += '<td >' + product_id2+ '</td>';
                        // html += '<td >' + purchase_id+ '</td>';
                        // html += '<td>' + specifications+ '</td>';
                        html += '<td align=center>'+ package_unit2+ '</td>';
                        html += '<td >' + purchase_num2+ '</td>';
                        html += '<td >' + remarks2+ '</td>';
                        if(order_flag*1==-1)
                            html += '<td >'+Number(price).toFixed(2)+'</td>';
                        html += '</tr>';
					}else {
                        html += '<td > </td>';
                        html += '<td align=center> </td>';
                        html += '<td > </td>';
                        html += '<td > </td>';
                        if(order_flag*1==-1)
                            html += '<td > </td>';
                        html += '</tr>';
					}
                    }
						});

        html += '</tbody>';

        html += ('<tfoot  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 22px;border:1px #999999 solid;line-height: 18px">');
        html += ('<tr align="left" class="text" style="line-height:28px;">');
        html += ('<td colspan="8" style="border: 0px;"  align="left" class="title">&nbsp;</td>');
        html += ('</tr>');
        html += ('<tr align="left" style="line-height:28px;">');
        html += ('<td style="border: 0px;" colspan="2" align="left" >制表人：'+userName+'</td>');
        html += ('<td style="border: 0px;" colspan="6" align="left" >制表时间：'+time+'</td>');
        // html += ('<td style="border: 0px;" colspan="3" align="left" >审批人（签名）：</td>');
        html += ('</tr>');
        html += '</tfoot>';
		html += ('</div>');

		return html;
	}
	$(function() {
		// var MultiRows = "P20180911D5;P20180911D2";
        // collect = '0';
		var url = collect=='1'?"OrderPrintAction_printDoOrderF.action":"OrderPrintAction_printDoOrderFNoCollect.action";
		$.ajax({
			type : "POST",
			url : 'OrderPrintAction_printDoOrderF.action',
			dataType : "json",

			data : {
				"MultiRows" : MultiRows,
				"startTime":startTime,
				"endTime":endTime
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
			window.print();
	}
	

	
</script>
</head>
<body>
	<table width="80%" border="0" align="center" cellpadding="0"
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
	<table id="printContent" width="80%"  border="1" align="center" cellpadding="0" cellspacing="0" >

	</table>
</body>

</html>