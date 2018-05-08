<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">

	<script src="../jquery/custom/jquery.min.js"></script>
	<script src="../jquery/custom/jquery.table2excel.min.js"></script>
<title>中心仓库采购验收单</title>
<script>

    function toExcelData() {
       // var table = $('#printContent').DataTable();
        $("#printContent").table2excel({
            exclude: ".excludeThisClass",
            name: "Worksheet Name",
            exclude_inputs: false,
            fileext: ".xls",
            filename: $("#tableName").text()+".xls"
        });
        alert("导出成功！");
    }
//获得当前时间
var myDate = new Date();
var time = myDate.toLocaleString( ); //获取日期与时间
var supplier_name = window.parent.supplier_name;
var MultiRows = window.parent.MultiRows;
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
		// if (arr_details.length!=0){
		// 	$.each(arr_details,function(i){
		// 		var clazz = arr_details[i];
		// 		clazz_list = clazz.clazz_name;
		// 		if (clazz_list.length!=0){
		// 			$.each(clazz_list,function(i){
		// 				var purchase_num = clazz_list[i].purchase_num;
		// 				v_mount= v_mount + parseInt(purchase_num);
		// 			});
		// 		}
		// 	});
		// }
	});
	var myDate = new Date();
	var time = myDate.toLocaleString( ); //获取日期与时间
var url = document.URL;  
var uarr = url.split('/');
var html = "";
html += ('<div >');
			html += '<br>';
			//条形码
			html += ('<thead align="center" >');
			html += ('<tr>');
			html += ('<td align="center" style="border: 0px;text-align: center"  colspan="7"   ><h1 id="tableName">采购验收单</h1></td>');
			html += ('</tr>');
			html += ('</thead>');
			html += ('<tbody border="1"  align="center"  style="line-height: 20px;border:1px #999999 solid;line-height: 22px">');
			html += ('<tr align="left" style="line-height:28px;">');
     		html += ('<td style="border: 0px;"  colspan="7" class="title">&nbsp;</td>');
			html += ('</tr>');
			html += ('<tr align="left" style="line-height:28px;">');
			html += ('<td style="border: 0px;"  colspan="3" align="left" class="title">&nbsp;打印时间：'+time+'</td>');
     		// html += ('<td style="border: 0px;"  colspan="4" align="right" class="title">采购总金额：'+Number(v_mount).toFixed(2)+'&nbsp;</td>');
			html += ('</tr>');
			html += ('<tr>');
			html += ('<td style="border-bottom:0px;border-right:0px;border-left:0px;width: 100%" colspan="7"  >&nbsp;</td>');
     		html += ('</tr>');
			html += ('</tbody>');
			html += ('<tbody  border="1" >');
$.each(tojsons,function(i){
		var cols = tojsons[i];
    	var no = cols.id;// 编号
    	var provide_name = cols.provide_name;
    	var arr_details = cols.provide_list;
		html += '<tr class=text align="center" >';
		html += '<td style="border: 0px;"   colspan="7" align="center" style="font-weight:bold;font-size:26px;" >&nbsp;';
		html += '<h1>'+provide_name+'</h1>';
		html += "</td>";
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
	    	html += '<td style="border: 0px;"  style="font-weight:bold;" class=text align=center >'+clazz_id+'</td>';
			html += '</tr>';
			html += '<tr class=text align=center >';
			html += '<td nowrap class="title" >序号</td>';
			html += '<td nowrap class="title" >商品名称&nbsp;</td>';
			html += '<td nowrap class="title" >采购单编号&nbsp;</td>';
			html += '<td nowrap class="title" >采购规格&nbsp;</td>';
			html += '<td nowrap class="title" >包装单位&nbsp;</td>';
			html += '<td nowrap class="title" >备注&nbsp;</td>';
			html += '<td nowrap class="title" >采购数量&nbsp;</td>';
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
				html += '<td >'+Number(purchase_num).toFixed(0)+'</td>';
				html += '</tr>';
				
			});
		}    	
		    });
	   });
	
        html += '</tbody>';
        
		html += ('<tfoot  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 22px;border:1px #999999 solid;line-height: 18px">');
		html += ('<tr align="left" style="line-height:28px;">');
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
  $(function(){
      //MultiRows ="Z20180504D1;P20180507D2;P20180507D1;P20180504D1";
	  MultiRows = "P20180504D1";
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
<script>
</script>

</html>