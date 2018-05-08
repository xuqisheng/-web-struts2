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
<title>验收单打印</title>
<style type="text/css">
	thead{
		border: 0px;
		border-style:none
	}
	thead tr{
		border: 0px;
		border-style:none
	}
	thead td{
		border: 0px;
		border-style:none
	}
	tfoot{
		border: 0px;
		border-style:none
	}
</style>
<script>
//*生成打印页面*/
function toExcelData() {
    $("#printContent").table2excel({
        exclude: ".excludeThisClass",
        name: "Worksheet Name",
        exclude_inputs: true,
        fileext: ".xls",
        filename: $("#tableName").text()+".xls"
    });
    alert("导出成功！");
}

//获得当前时间
var myDate = new Date();
var time = myDate.toLocaleString( ); //获取日期与时间
var MultiRows = window.parent.MultiRows;
//先获取到相关变量值
var batNo = window.parent.batNo;
var total = window.parent.total;
var username = window.parent.username;
var req_date = window.parent.req_date;
var url = document.URL;  
var uarr = url.split('/');

function checkList(printContent,tojsons){
	var tojsons = eval(tojsons);
	var v_amount = 0;
	//alert(tojsons);<list>
	$.each(tojsons,function(i){
		var cols = tojsons[i];
		var arr_details = cols.details
		$.each(arr_details,function(i){
			var out_price = arr_details[i].out_price;
			var out_num = arr_details[i].out_num;
			v_amount=v_amount+parseInt(out_price*out_num);
		});
	});
	var myDate = new Date();
	var time = myDate.toLocaleString( ); //获取日期与时间
var url = document.URL;  
var uarr = url.split('/');
var html = "";
html += ('<div id = "printdiv"> ');
			html += '<br>';
			//条形码
			html += ('<thead  width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">');
			html += ('<tr align="center" >');
			html += ('<td colspan="7" align="center"  style="border: 0px;" width="40%"><h3 id="tableName">入库验收单</h3></td>');
			html += ('</tr>');
			html += ('</thead>');
			html += ('<tbody  border="0" align="center"  style="line-height: 20px;border:0px;line-height: 22px">');
			html += ('<tr style="border: 0px;" align="left"  style="line-height:28px;">');
			html += ('<td style="border: 0px;"  align="left" >&nbsp;</td>');
     		html += ('<td style="border: 0px;"  align="right" >&nbsp;</td>');
			html += ('</tr>');
			html += ('<tr style="border: 0px;" align="left"  style="line-height:28px;border: 0px;">');
			html += ('<td style="border: 0px;"  colspan="6" align="left" >&nbsp;打印时间：'+time+'</td>');
     		html += ('<td style="border: 0px;"  align="right">采购总金额：'+Number(v_amount).toFixed(2)+'&nbsp;</td>');
			html += ('</tr>');
			html += ('<tr  align="left"  style="line-height:28px;">');
			html += ('<td style="border:0px;border-bottom:solid 1px;"   colspan="7" >&nbsp;</td>');
     		html += ('</tr>');
			html += ('</tbody>');
			html += ('<fbody class="print_table"  border="0" >');
			
    $.each(tojsons,function(i){
    	var cols = tojsons[i];
    	var no = cols.id;// 编号
    	var arr_details = cols.details;//list
    	var class_name = cols.class_id;
    	var class_depart = cols.class_name;
		html += '<tr  align="center" >';
		html += '<td  style="border:none"><h4 >验收单:&nbsp;'+class_name+'</td>';
	   	html += '<td align="left" colspan="2"  style="border:none"><h4 >&nbsp;';
		html += '</td>';
		html += '<td  align="right" colspan="4" style="border:none"><h4 >客户部门：'+class_depart+'&nbsp;&nbsp;</h4></td>';
		html += '</tr>';
		html += '<tr  align=center >';
		html += '<td>序号</td>';
		html += '<td nowrap  >商品名称&nbsp;</td>';
		html += '<td nowrap  >包装规格&nbsp;</td>';
		html += '<td nowrap  >包装单位&nbsp;</td>';
		html += '<td nowrap  >入库数量&nbsp;</td>';
		html += '<td nowrap  >入库单价&nbsp;</td>';
		html += '<td nowrap  >小计&nbsp;</td>';
	    html += '</tr>';
		$.each(arr_details,function(i){
			var id = arr_details[i].id;
			var package_unit = arr_details[i].package_unit;
			var product_id = arr_details[i].product_id;
			var specifications = arr_details[i].specifications;
			var out_price = arr_details[i].out_price;
			var out_num =arr_details[i].out_num;
			
			html += '<tr  align=center width="100%" >';
			html += '<td >'+(i+1)+'</td>';
			html += '<td >'+product_id+'</td>';//名称
	        html += '<td>'+specifications+'</td>';
	        html += '<td align=center>'+package_unit+'</td>';
			html += '<td >'+out_num+'</td>';//库存
			html += '<td >'+out_price+'</td>';//入库价格
			html += '<td >'+Number(out_num*out_price).toFixed(2)+'</td>';//入库价格
			html += '</tr>'
		});
	});
        html += '</fbody>';
		html += ('<tfoot >');
		html += ('<tr align="left" style="line-height:28px;">');
		html += ('<td style="border: 0px;" colspan="7" align="left" >&nbsp;</td>');
		html += ('</tr>');
		html += ('<tr align="left" style="line-height:28px;">');
		html += ('<td style="border: 0px;" colspan="2" align="left" >经办人（签名）：</td>');
		html += ('<td style="border: 0px;" colspan="2" align="left" >证明人（签名）：</td>');
		html += ('<td style="border: 0px;" colspan="3" align="left" >审批人（签名）：</td>');
		html += ('</tr>');
		html += '</tfoot>';
    	html += '<br/>';
		html += ('</div>');

		
		
return html;
}

  $(function(){

	 // var MultiRows = "O20180503D4";
  	$.ajax({
		type: "POST",
		url:  'OrderPrintAction_checkList.action',
		dataType: "json",
		data: {"MultiRows":MultiRows},
		success: function(data,textStatus){
			var printContent = $('#printContent');
  			printContent.empty();
  			var html  = "";
			if(data){
				html = checkList(printContent,data.json);
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
	<br/><br/>
	<tr align="center">
		<td ><input name="BtnPrint" type="button" id="BtnPrint" value="打印验收单" onClick="doprint();"></td>
		  <td><input name="bExcel" type="button" value="导出表格" onclick="toExcelData();"/></td>
	  </tr>
	</table>
	<br/><br/>
	 <table id="printContent" width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" >

	 </table>
</body>
<script>
</script>

</html>