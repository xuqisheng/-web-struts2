<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>上海交通大学研究生科研助教付款凭证</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="../jquery/jquery-1.3.2.js"></script>
    <script type="text/javascript" src="../core/userContext.js"></script>
    <script type="text/javascript" src="../core/customWin.js"></script>
    <script src="js/common.js"></script>
    <style media='print'> 
    .printdiv{
		margin: 0px 0px;
		padding-left:0px;
		padding-right:0px;
		padding-top:0px;
		padding-bottom:0px;
		height: 600px;
		width:  900px;
		position: relative;
	}

    .Noprint {display:none;} 

    .PageBreak {page-break-after: always;} 

    </style> 
	
	<style type="text/css" >
	.TitleStyle {
	font-size: 24px;
	font-family: "锟斤拷锟斤拷";
	test-align:center;
	font-weight: bold;
	color:#000000;
    }
    
    .title {
	font-size: 20px;
	font-family: "锟斤拷锟斤拷";
	font-weight: bold;
	color: #000000;
	margin-bottom:20px;
    }

	 .printtitle {
		
	font-size: 14px;
	font-family: "锟斤拷锟斤拷";
	font-weight: bold;
	color: #000000;
    }
	
	.table_border{  
    border: solid 1px #000000;  
    border-collapse: collapse;  
    }  
    .table_border tr th{  
    padding-left:4px;  
    height:24px;  
    border: solid 1px #000000;  
    }  
    .table_border tr td{  
    height:24px; 
    text-align:center; 
    padding:2px;
    border: solid 1px #000000;  
    } 
	</style>
	
	<script type="text/javascript">	
	//获得当前时间
	var myDate = new Date();
	var time = myDate.toLocaleString( ); //获取日期与时间
	
	//先获取到相关变量值
	var batNo = window.parent.batNo;
	var total = window.parent.total;
	var code = window.parent.code;
	var username = window.parent.username;
	var req_date = window.parent.req_date;
	var url = document.URL;  
	var uarr = url.split('/');

	//*生成上海交通大学研究生科研助教付款凭证打印页面*/
function createTechPay(printContent,tojsons){
	var html = "";
	
	html += ('<div class="printdiv">');
            html += '<table   cellspacing="10" width="100%" border="0" algin="center" cellpadding="8" cellspacing="0">';
            html += '<tr><td align="left" class="TitleStyle" colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;<img src="/'+uarr[3]+'/barcode?msg='+batNo+'" height="50px" width=130px/>&nbsp;&nbsp;&nbsp;&nbsp;上海交通大学研究生科研助教付款凭证</td></tr>';
            html += '<tr><td></td><td></td><td width="16%" align="center" class="printtitle">打印日期:  '+time+'</td><td></td></tr><br/>';
            html += '<tr><td></td>';
            html += '<td width="50%" align="left" class="title" style="padding-left:40px;">金 额 总 计：'+'<font style="border-bottom:1px solid #000000;">   '+Number(total).toFixed(2)+'  元          </font></td>';
            html += '<td width="50%" align="left" class="title" style="padding-left:40px;">聘用单位审核盖章：____________</td>';
            html += '<td></td></tr><br/><tr><tr/>';
            html += '<tr><td></td>';
            html += '<td width="30%" align="left" class="title" style="padding-left:40px;">科研经费账号：'+'<font style="border-bottom:1px solid #000000;">' +code+'</td>';
            html += '<td width="30%" align="left" class="title" style="padding-left:40px;">经费账号章：_______________</td>';
            html += '<td></td></tr><br/><tr><tr/>';
            html += '<tr><td></td>';
            html += '<td width="30%" align="left" class="title" style="padding-left:40px;">聘用教师签字：____________</td>';
            html += '<td width="30%" align="left" class="title"style="padding-left:40px;" >经费负责人签字：____________</td>';
            html += '<td></td></tr><br/><tr><tr/>';
            html += '</table>';
            
            html += '<table><br><br></table>';
            
            html += '<table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0"  class="table_border">';
			html += '<tr><caption align = "left" class="printtitle">申请日期：'+req_date+'</caption></tr>';
	   	    html += '<tr><caption align = "left" class="printtitle">操作人员：'+username+'</caption></tr>';
            html += '<tr><caption align = "left" class="printtitle">发放序列：'+batNo+'</caption><caption class="title">付款清单<br/></caption><tr/>';
            html += '<tr><tr/><tr><tr/><tr><tr/><tr><tr/><tr><th scope="col">学号</th>';
            html += '<th scope="col">姓名</th>';
            html += '<th scope="col">班级</th>';
            html += '<th scope="col">导师工号</th>';
            html += '<th scope="col">导师姓名</th>';
            html += '<th scope="col">起始日期</th>';
            html += '<th scope="col">结束日期</th>';
            html += '<th scope="col">金额</th>';
            html += '<th scope="col">培养方式</th></tr>';

	$.each(tojsons,function(i){
			var cols = tojsons[i];
			var stuno = cols.SNO;
			var stuName = cols.SNAME;
			var stuClass = cols.DCODE;
			var techNo = cols.TCH_NO;
			var techName = cols.TEACHER;
			var startDate = cols.START_DATE;
			var endDate = cols.END_DATE;
			var amt = cols.AMT;
			var pyName = cols.PNAME;
			
            html += '<tr><td>'+stuno+'</td>';
            html += '<td>'+stuName+'</td>';
            html += '<td>'+stuClass+'</td>';
            html += '<td>'+techNo+'</td>';
            html += '<td>'+techName+'</td>';
            html += '<td>'+startDate+'</td>';
            html += '<td>'+endDate+'</td>';
            html += '<td>'+Number(amt).toFixed(2)+'</td>';
            html += '<td>'+pyName+'</td></tr>';
		});
	        html += '<tr><td colspan="9" class="title">金额总计：  '+Number(total).toFixed(2)+'  元</td></tr> ';
            html += '</table>';
			html += ('</div>')
	return html;
}
	 
	  $(function(){
	  	$.ajax({
			type: "POST",
			url:  'gfBatDtl_getGFBatDtlList.action?batNo='+batNo,
			dataType: "json",
			success: function( data,textStatus ){	  			
	  			var printContent = $('#printContent');
	  			printContent.empty();
	  			var html  ="";
				if(data){
					html = createTechPay(printContent,E);
				}
				printContent.html(html);			
			},
			error:function(e){
				alert(e);
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
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="Noprint">
	<br>
	  <tr align="center">
		<td><input name="BtnPrint" type="button" id="BtnPrint" value="打印付款凭证" onClick="doprint();"></td>
	  </tr>
	</table>
    <div id="printContent" width="100%" align="center" style="overflow:auto;"></div>	

  	
 
  	
  </body>
</html>
