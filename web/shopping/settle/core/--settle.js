//var projID = "WF_NYD";
var category = '';
window.onload=function(){
	category = GetUrlString('category'); 
	/**如果为管制品  22   课题组勾选、课题组下拉显示、管制品申请单，**/
	/*if(category.substr(0,2)=='22'){
	firstread();
	$("#topic-choice").attr('checked',true);
	$("#topic-choice").attr("disabled",true)
	$("#ktz").show();
	$('#whprinfo').show();
    
	}*/
	
	$("#topic-choice").attr('checked',true);
	$("#topic-choice").attr("disabled",true)

	//window.showModalDialog('../firstread.html',window, 'height=100, width=400, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
	//window.document.location.href="../firstread.html";
	/*隐藏项目信息*/
	$("#proinfo").hide();
	var myjson = "";
	
	/*获取收货地址*/
	GetAddress();
	SetTime();
	getOtPay();
	/*获取课题组*/
	GetTGROUP_Settle();


	var carts = "";
	var suppliers = "";
	var supplier = "";
	var tamt = 0;
	var tnum = 0;
	var tprice = 0;
	var tsprice = 0;
	var postage = 0;
	var postage_price = 0;
	var cartids = GetQueryString("cartids");
		/*获取订单明细*/
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetSettleCart.action',
		  dataType: "json",
		  data: {"cartids":cartids},
		  success: function( data ){
			myjson = eval(data.json);
			tamt = 0;
			for(var i=0;i<myjson.length;i++){ 
			tnum = 0;
			tsprice = 0;
				/*cart = "<li  class=\"wrap wrap-detailed\">"+
							"<div class=\"w-left\">"+
								"<img width=\"100\" height=\"100\" src=\"../fileSystem_getImgStreamAction.action?seq="+myjson[i].image.split(/&/)[0]+"&projid="+projID+"\"></div>"+
							"<div class=\"w-right\">"+
								"<div class=\"right-list\">"+
									"<span class=\"list-column list-column-color\">"+myjson[i].name+"</span>"+
									"<span class=\"list-column list-align list-color\">￥"+
								myjson[i].price+"<br><span class=\"list-color-k\">"+myjson[i].brand+"</span></span><span class=\"list-column list-align\">x"+
								myjson[i].number+"</span></div>"+
								"<div class=\"clear\"></div>"+
							"</div>"+
	 						"<div class=\"clear\"></div>"+
						"</li>";*/

		myjsondtl = eval(myjson[i].dtl);
			carts = "";
			
		 /**设置时间**/
  /**修改时间   by  吴斌**/
  var myDate = new Date();
  myDate.setTime(myDate.getTime()+24*60*60*1000);
  var Week = ""; 
  if (myDate.getDay()==0)
  {
	  Week = "周日";
  }else if (myDate.getDay()==1)
  {
	  Week = "周一";
  }else if (myDate.getDay()==2)
  {
	  Week = "周二";
  }else if (myDate.getDay()==3)
  {
	  Week = "周三";
  }else if (myDate.getDay()==4)
  {
	  Week = "周四";
  }else if (myDate.getDay()==5)
  {
	  Week = "周五";
  }else if (myDate.getDay()==6)
  {
	  Week = "周六";
  } 
 /**设置时间结束**/
			for(var ii=0;ii<myjsondtl.length;ii++){ 
				
				tprice = Number(myjsondtl[ii].price)*Number(myjsondtl[ii].number);
				tamt =  Number(tamt)+Number(tprice);
				tsprice = Number(tsprice)+Number(tprice);
				tnum = Number(tnum)+Number(myjsondtl[ii].number);
			    carts += "<div>"+
						   "<div class=\"p-img\"><img src=\"../fileSystem_getImgStreamAction.action?seq="+myjsondtl[ii].image.split(/&/)[0]+"&projid="+projID+"\"></div>"+
						   "<div class=\"goods-msg\">"+myjsondtl[ii].name+"</div>"+
						   "<div class=\"p-price\">"+
							   "<span class=\"title-attribute\">"+repstr(myjsondtl[ii].specifications)+"</span>"+
							   "<span class=\"title-brand\">"+myjsondtl[ii].brand+"</span>"+
							   "<strong class=\"price-tot\">￥"+myjsondtl[ii].price+"</strong>"+
							  " <span class=\"p-num\">x"+myjsondtl[ii].number+"</span>"+
							  " <span class=\"price-tot\">"+tprice.toFixed(2)+"</span>"+
						  " </div>"+
						  " <div class=\"clear\"></div>"+
						"</div>";
			}
		
		
			    if(Number(myjson[i].postage)==0||Number(myjson[i].small)==0){
					postage=0.00;
				}else{
					if(Number(myjson[i].small)>tsprice){
						postage = Number(myjson[i].postage);
					}else{
						postage=0.00;
					}
				}
				postage_price = Number(tsprice)+Number(postage);
				
				tamt =  Number(tamt)+Number(postage);
		supplier = "<div class=\"\" name=\"suppliers\" sid=\""+myjson[i].sid+"\" store=\""+myjson[i].store+"\" tamt=\""+tsprice.toFixed(2)+"\" postage=\""+postage.toFixed(2)+"\">"+
				"<div class=\"detailed-newlist\">"+
				 " <div class=\"price-tit tit-height\"><span class=\"price-color\">费用项：</span><select  id=\"b_code\" name=\"b_code\" style=\"width: 220px;\">";
				 
					myjsonbcode = eval(myjson[i].bcodes);
						for(var iii=0;iii<myjsonbcode.length;iii++){ 
							supplier = supplier + "<option value=\""+myjsonbcode[iii].b_code+"\">"+myjsonbcode[iii].b_code+'-'+myjsonbcode[iii].usage+"</option>";
						}
					supplier = supplier + "</select>"+
					"</div>"+ 		 		
				 " <div class=\"price-tit tit-height\"><span class=\"price-color\">金额：</span><span class=\"price-num\" style=\"font-size:14px;\">￥"+postage_price.toFixed(2)+"（其中包含邮费【"+postage+"元】）</span></div>"+
				 " <div class=\"price-tit tit-height\"><span class=\"price-color\">数量：</span><span>"+tnum+"</span></div>"+
				 " <div class=\"price-tit tit-height\"><span class=\"price-color\">配送时间：</span><div class=\"mode-infor\" >&nbsp;"+
					 "<a id=\"gettimey\" id=\"gettimey\" name=\"gettimey\" >"+myDate.getFullYear()+"</a>年"+
					 "<a id=\"gettimem\" id=\"gettimem\" name=\"gettimem\">"+(myDate.getMonth()+1)+"</a>月"+
					 "<a id=\"gettimed\" id=\"gettimed\" name=\"gettimed\">"+myDate.getDate()+"</a>日&nbsp;&nbsp;"+
					 "<a id=\"gettimew\" id=\"gettimew\" name=\"gettimew\">"+Week+"</a>&nbsp;&nbsp;送达 "+
					 " <span style=\"color:red\" onClick=\"HS_setDate(this)\">修改</span></div>"+
				 " </div>"+
				 " <div class=\"price-tit\"><span class=\"price-color\">配送方式:</span>"+
				 " <div class=\"clear\"></div>"+
				 " <div class=\"express-checkbox2\" style=\" height:30px;\">"+
				 " <input name=\"\" type=\"checkbox\" value=\"\" id=\"checkboxOneInputg\"/>"+
				 " <label for=\"checkboxOneInputg\">供应商配送</label> "+
				 " </div>"+
				 " <div class=\"express-checkbox3\" style=\" height:30px;\">"+
				// " <input name=\"\" type=\"checkbox\" value=\"\" id=\"checkboxOneInputz\"/> "+
				// " <label for=\"checkboxOneInputz\">自提</label>  "+
				 " </div>"+
				 " </div>"+
			// " <div class=\"order-details-botton\">订单详情</div>"+
			 " <div class=\"order-details-botton\"></div>"+
				 " <div> "+
				 " </div>"+
				"</div>"+
			"<div class=\"order-details-div\">"+
			"<div class=\"goods-tit\"><h4 class=\"vendor_name_h\">经销商："+
				 "<span style='color:#005BAC'>"+myjson[i].sname+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>";
			    if(Number(myjson[i].postage)==0||Number(myjson[i].small)==0){
				 supplier += "<span style='color:red'>(全场免邮)</span>";
					 }
				 else{
				 supplier += "<span style='color:red'>(单笔订单不满"+myjson[i].small+"元，将收取"+myjson[i].postage+"邮费)</span>";
				  
				 }
	  supplier += "</h4>"+
			"</div>"+
			 " <div class=\"goods-item\">"+
				/*材料明细*/
				carts+
				"<div class=\"return\">"+
	 			"<span class=\"total-amount\" id=\"total-amount\">"+tsprice.toFixed(2)+"</span>"+
	 		"</div>"+
			  "</div>"+
			"</div>"+
			"<div class=\"clear\"></div>"+
			"</div>";

			suppliers += supplier;



			/*	cart = "<div class=\"p-img\"><img src=\"../fileSystem_getImgStreamAction.action?seq="+myjson[i].image.split(/&/)[0]+"&projid="+projID+"\"></div>"+
				   "<div class=\"goods-msg\">"+myjson[i].name+"("+myjson[i].brand+")   "+myjson[i].specifications+"</div>"+
				   "<div class=\"p-price\">"+
				      " <strong class=\"price-tot\">￥"+myjson[i].price+"</strong>"+
					  " <span class=\"p-num\">x"+myjson[i].number+"</span>"+
					  " <span class=\"p-state\"></span>"+
				   "</div>"+
				   "<div class=\"clear\"></div>"+
				"</div>";*/


				



			}
				$("#suppliers").append(suppliers);
			//	$("#carts").append("<div class=\"return\"><span class=\"total\" >总金额</span><span class=\"total-amount\" id=\"total-amount\">"+tamt.toFixed(2)+"</span>");
				$("#payamt").html(tamt.toFixed(2));
				
		  }
		 });

	/*获取项目信息*/
	var pros = "";
	var pro = "";
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetProInfo.action',
		  dataType: "json",
		  success: function( data ){
			myjson = eval(data.json);
			for(var i=0;i<myjson.length;i++){ 
				pro = "<option value=\""+myjson[i].name+"|,|"+myjson[i].procode+"|,|"+myjson[i].proname+"|,|"+myjson[i].haveprice+"\">"+myjson[i].name+"("+myjson[i].procode+")</option>";
				pros += pro;
			}
				$("#pro").append(pros);
		  }
		 });



		 /**设置时间**/
  /**修改时间   by  吴斌**/
 /* var myDate = new Date();
  var Week = "";
  if (myDate.getDay()==0)
  {
	  Week = "周日";
  }else if (myDate.getDay()==1)
  {
	  Week = "周一";
  }else if (myDate.getDay()==2)
  {
	  Week = "周二";
  }else if (myDate.getDay()==3)
  {
	  Week = "周三";
  }else if (myDate.getDay()==4)
  {
	  Week = "周四";
  }else if (myDate.getDay()==5)
  {
	  Week = "周五";
  }else if (myDate.getDay()==6)
  {
	  Week = "周六";
  }
  $("#gettime").html("&nbsp;"+myDate.getFullYear()+"年"+ (myDate.getMonth()+1)+"月"+myDate.getDate()+"日&nbsp;&nbsp;["+Week+"]&nbsp;&nbsp;送达");
  $("#hidetime").html(myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate());*/
 /**设置时间结束**/


}



function GetAddress(){
	var myjson = "";
	var address = "";
	var address_s = "";
	var groupcode = "";
	/*如果未勾选课题组，不提取课题组信息*/
	if($("#topic-choice").attr('checked')){
	 	 groupcode =  $('#ktz').val();
	}
	address_h = "";
	var def = "";
	$("#address").children().remove();
	/*获取收货地址*/
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetAddres.action',
		  dataType: "json",
		    data: {"groupcode":groupcode},
		  success: function( data ){
			myjson = eval(data.json);
			for(var i=0;i<myjson.length;i++){ 
				  
					if(myjson[i].isdef=='T'){
						def = i;
					address	= "<li class=\"wrap\">"+
						"<input id=\"selectaddress_"+i+"\" name=\"selectaddress\" style=\"display :none\" type=\"radio\" value=\""+i+"\" locationid=\""+myjson[i].locationid+"\" />"+
								"<div class=\"checkbox-one\"><div class=\"express-checkbox express-checkbox\""+i+">"+
								"<label name=\"addresslabel\"  for=\"selectaddress_"+i+"\" onclick=\"SetColor(this,"+i+")\">"+myjson[i].name+"</label> "+
					     "</div></div>"+
					"<div class=\"addr-detail\">"+
						"<span id=\"name_"+i+"\" class=\"addr-name\">"+myjson[i].name+"</span>"+
						"<span id=\"address_"+i+"\" class=\"addr-info\">"+myjson[i].address+"</span>"+
						"<span id=\"phone_"+i+"\" class=\"addr-tel\">"+myjson[i].phone+"</span>";
					if(myjson[i].locationid=='0'){
					address = address + "<span class=\"addr-default\">默认地址</span>";
					}
					address = address + "</div>"+
					"<div class=\"clear\"></div>"+
				 "</li>";
				address_s += address;
				 }else{
					address	= "<li class=\"wrap\">"+
						"<input id=\"selectaddress_"+i+"\" name=\"selectaddress\" style=\"display :none\"  type=\"radio\" value=\""+i+"\"  groupcode=\""+myjson[i].locationid+"\"/>"+
								"<div class=\"checkbox-one\"><div class=\"express-checkbox express-checkbox\""+i+">"+
								"<label  name=\"addresslabel\"  for=\"selectaddress_"+i+"\" onclick=\"SetColor(this,"+i+")\">"+myjson[i].name+"</label> "+
					     "</div></div>"+
					"<div class=\"addr-detail\">"+
						"<span id=\"name_"+i+"\" class=\"addr-name\">"+myjson[i].name+"</span>"+
						"<span id=\"address_"+i+"\" class=\"addr-info\">"+myjson[i].address+"</span>"+
						"<span id=\"phone_"+i+"\" class=\"addr-tel\">"+myjson[i].phone+"</span>"+
					"</div>";
						if(myjson[i].locationid=='0'){
						//address = address + "<span class=\"addr-default\">默认地址</span>";
						}
					address = address + "<div class=\"default-add\">设置为默认地址</div>"+
					"<div class=\"clear\"></div>"+
				 "</li>";
				address_h += address;
				 }

			}
				$("#address").append(address_s);
				$("#address").append("<div style=\"display:none;\" id=\"addshow\">");
				$("#addshow").append(address_h);
				$("label[for='selectaddress_"+def+"']").click();
				//$("#address").append("<div class=\"addr-switch switch-on\" id=\"add\"><span>更多地址</span><b></b></div><div class=\"addr-switch switch-off\" style=\"display:none;\"><span>收起地址</span><b></b></div>");
		  }
		 });

}

function getOtPay(){
	var OtPay = "";
 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GET_OT_PAY.action',
		  dataType: "json",
		  success: function( data ){
			myjson = eval(data.json);
			for(var i=0;i<myjson.length;i++){ 
				OtPay = "<input  name=\"otpro\" type=\"radio\" unino=\""+myjson[i].ot_uni_no+"\" ot_name=\""+myjson[i].ot_name+"\"  />"+myjson[i].ot_uni_no+"-"+myjson[i].ot_name+"&nbsp;&nbsp;&nbsp;&nbsp;";
				$("#OtPay").append(OtPay);
			}
			 $("[name='otpro']").click(function(){
			   $('#othercode').val($(this).attr('unino'));
			   $('#othername').val($(this).attr('ot_name'));
			  });
		  }
		  });


}



function projchange(data) {
var strs= new Array(); 
if(data.value==0){
	$("#proinfo").hide();

}else{
	strs=data.value.split("|,|");
	$("#proinfo").html("项目负责人："+strs[2]+"；项目余额："+strs[3]);//项目编号："+strs[1]+"；项目名称："+strs[0]+"；
	$("#paytype").html("项目支付");
	$("#chargename").html(strs[2]);
	$("#proid").html(strs[1]);

   /* alert(data.value);*/
		
	$("#proinfo").show();
}

  }

function groupchange() {
GetAddress();
var   groupcode =  $('#ktz').val();
/*Get_REQUISITION(groupcode);*/
var gro = $("#ktz").find("option:selected");
if(gro.val()==0||!$("#topic-choice").attr('checked')){
$("#sgroup").html("");
}else{
$("#sgroup").html(gro.val()+"-"+gro.text());
}
}


/*提交订单*/
function submitorder(cartids){
	var checkedaddress = $("[name=selectaddress]:checked");
	var saddress = checkedaddress.val();
	if(!$('[name=selectaddress]').is(':checked')) {
		alert('请选择收货地址');
		return;
	}
	/*支付方式：0：项目支付，OT：他人代付*/
	var paytype= $("#ul_list li[class='current']").attr('id');
	var othercode = $("#othercode").val();
	var othername = $("#othername").val();

	var Tamt = $("#total-amount").html();
	var pro_code =$("#proid").html();
	var b_code= "";
	var b_namearr= "";//$("#b_code").find("option:selected").html();
	//var strs= new Array(); 
	//	strs = b_namearr.split('-');
	var b_name = "";//strs[1];
	
	if(paytype=='XM'){
		if(pro_code==''||pro_code==null){
		alert('请选择支付项目');
		return;
		}
		//if(b_code==''||b_code==null){
		//alert('请选择费用项');
		//return;
		//}
		//if(b_name==''||b_name==null){
		//alert('请选择费用项');
		//return;
		//}
	}else if(paytype=='OT'){
		if(othercode==''||othercode==null){
		alert('请填写代付人账户');
		return;
		}
		if(othername==''||othername==null){
		alert('请填写代付人姓名');
		return;
		}
	}else{
		alert('请选择支付方式');
		return;
	}
	/*[{sid:'123',tamt:'100',Getdate:'yyyy-mm-dd'}]*/
	var s_json = "[";//供应商信息
	var sobj = $("[name='suppliers']");
	var sobjl = $("[name='suppliers']").length;
	
	for(var i = 0;i < sobjl; i++) { 
		b_name = "";
		b_code = "";
		b_namearr= sobj.eq(i).find("[name='b_code']").find("option:selected").html();
		var strs= new Array(); 
			strs = b_namearr.split('-');
		var b_code = strs[0];
		var b_name = strs[1];
		if(paytype=='XM'){ 
			if(b_code==''||b_code==null){
			alert('请选择费用项');
			return;
			}
			if(b_name==''||b_name==null){
			alert('请选择费用项');
			return;
			}
		}
		if(i==0){
		s_json += "{sid:'"+sobj.eq(i).attr('sid')+"'"+
					",store:'"+sobj.eq(i).attr('store')+
					"',tamt:'"+sobj.eq(i).attr('tamt')+
					"',postage:'"+sobj.eq(i).attr('postage')+
					"',b_code:'"+b_code+ 
					"',b_name:'"+b_name+ 
				"',Getdate:'"+
					$("[name='suppliers']").eq(i).find("[name='gettimey']").html()+"-"+
					$("[name='suppliers']").eq(i).find("[name='gettimem']").html()+"-"+
					$("[name='suppliers']").eq(i).find("[name='gettimed']").html()+"'}";
		}else{
		s_json += ",{sid:'"+sobj.eq(i).attr('sid')+"'"+
					",store:'"+sobj.eq(i).attr('store')+
					"',tamt:'"+sobj.eq(i).attr('tamt')+
					"',postage:'"+sobj.eq(i).attr('postage')+
					"',b_code:'"+b_code+ 
					"',b_name:'"+b_name+ 
					"',Getdate:'"+
					$("[name='suppliers']").eq(i).find("[name='gettimey']").html()+"-"+
					$("[name='suppliers']").eq(i).find("[name='gettimem']").html()+"-"+
					$("[name='suppliers']").eq(i).find("[name='gettimed']").html()+"'}";
		}
	} 
	s_json+=']';
	var groupcode = "";
	var locationid = "";
	/*if(category.substr(0,2)=='22'){
		if (!$('#topic-choice').attr('checked')||$('#ktz').val()=='0')
		{ 
			alert('管制品商品,请选择课题组');
			return;
		}
		if(checkedaddress.attr('locationid')=='0'){
			alert('管制品商品,送货地址必须为课题组注册地址');
			return;
		}
		
	}*/
	if ($('#ktz').val()=='0')
		{ 
			alert('请选择课题组');
			return;
		}
		locationid = checkedaddress.attr('locationid');
		groupcode = $('#ktz').val();
	
	var GetName = $("#name_"+saddress).html();
	var GetPhone = $("#phone_"+saddress).html();
	var GetAddress = $("#address_"+saddress).html();
	//var Getyyyy = $("#gettimey").text();
	//var Getmm = $("#gettimem").html();
	//var Getdd = $("#gettimed").html();
	var projacc =  $("#chargecode").html();
	var projNAME = $("#chargename").html();
	var appmsg = $("[name='message']").html();//订单备注
	var files = $("#whpr").find("option:selected").attr('files');
	/*如果是他人代付，pro_code为代付人账户*/
	$.ajax({  
	      type: "POST",
		  timeout: 60000, 
		  url: 'SHOP_SaveOrder.action',
		  data: {"paytype":paytype,
			     "Tamt":Tamt,
				 "pro_code":pro_code,
			    "othercode":othercode,
			    "othername":othername,
				"GetName":GetName,
				"GetPhone":GetPhone,
				"GetAddress":GetAddress,
				"s_json":s_json,
				"projacc":projacc,
				"projNAME":projNAME,
				"groupcode":groupcode,
				"locationid":locationid,
			    "appmsg":appmsg,
			    "files":files,
				"cartids":cartids},
		  dataType: "json",
		  success: function( data ){
			if (data.json.split(':')[0]=='ok')
			{
			//	alert('订单提交成功');
			window.location.href='cashier.jsp?orderid='+data.json.split(':')[1];
			}else{
				alert(data.json);
			}
		  }
		 });


}


/**保存收获地址信息**/
function saveaddress(){

	var newuser = $('#newuser').val();
	var newaddress = $('#newaddress').val();
	var newphone = $('#newphone').val();
	if(newuser==''){
		alert('收货人未填写');
	}
	if(newaddress==''){
		alert('收货地址未填写');
	}
	if(newphone==''){
		alert('收货人联系方式未填写')
	}
	$.ajax({  
	      type: "POST",
		  url: 'SHOP_SaveAddress.action',
		  data: {"newuser":newuser,
				 "newaddress":newaddress,
				 "newphone":newphone},
		  dataType: "json",
		  success: function( data ){
			if (data.json=='ok')
			{
				alert('地址保存成功');
				/*保存成功后重新刷新收获地址*/
				 GetAddress();
			}else{
				alert(data.json);
			}
		  }
		 });

	closeDiv();
}




function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}


function checkaddress(i){
$("#selectaddress[value="+i+"]").attr('checked','true');
}



function SetColor(obj,i){
$("[name=addresslabel]").css("color","#005BAC");
$("[name=addresslabel]").css("backgroundColor","#fff");
obj.style.backgroundColor="#005BAC";
obj.style.color="#fff";
$("#selectaddress_"+i).attr('checked','true');
//var saddress = $("[name=selectaddress]:checked").val();

	var GetName = $("#name_"+i).html();
	var GetPhone = $("#phone_"+i).html();
	var GetAddress = $("#address_"+i).html();
$("#finalgetaddress").html("寄送至   "+GetAddress+"   收货人："+GetName+"("+GetPhone+")")

}


function SetTime(){
 /**修改时间   by  吴斌**/
  var myDate = new Date();
myDate.setDate(myDate.getDate()+1);
  var Week = "";
  if (myDate.getDay()==0)
  {
	  Week = "周日";
  }else if (myDate.getDay()==1)
  {
	  Week = "周一";
  }else if (myDate.getDay()==2)
  {
	  Week = "周二";
  }else if (myDate.getDay()==3)
  {
	  Week = "周三";
  }else if (myDate.getDay()==4)
  {
	  Week = "周四";
  }else if (myDate.getDay()==5)
  {
	  Week = "周五";
  }else if (myDate.getDay()==6)
  {
	  Week = "周六";
  }
 // $("#gettime").html("预计&nbsp;"+myDate.getFullYear()+"年"+(myDate.getMonth()+1)+"月"+myDate.getDate()+"日&nbsp;&nbsp;["+Week+"]&nbsp;&nbsp;送达");
 
  $("#gettimey").html(myDate.getFullYear());
  $("#gettimem").html(myDate.getMonth()+1);
  $("#gettimed").html(myDate.getDate());

  $("#gettimew").html("["+Week+"]");
}



function GetTGROUP_Settle(){
	var groups = "";
	var group = "";
	var isshow = "F";
		/*获取所属课题组*/
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetTGROUP.action',
		  dataType: "json",
		  success: function( data ){
			myjson = eval(data.json);
			for(var i=0;i<myjson.length;i++){ 
					group =   "<option value=\""+myjson[i].groupcode+"\"  show=\""+myjson[i].show+"\" >"+myjson[i].groupname+"</option>";
			$("#ktz").append(group);
			if(myjson[i].show=='T'){
				isshow = "T";
			}
			}
			if(isshow=='F'){
			$("#ktz option").eq(1).attr('selected',true);
			}else{
			$("#ktz option[show='T']").attr('selected',true);
			}
		
		  }
		 });

}



/**获取管制品申请单**/
function Get_REQUISITION(groupcode){
	var groups = "";
	var group = "";
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetREQUISITION.action',
		  dataType: "json",
		  data: {"groupcode":groupcode},
		  success: function( data ){
			myjson = eval(data.json);
			for(var i=0;i<myjson.length;i++){ 
					group =   "<option value=\""+myjson[i].rcode+"\" files=\""+myjson[i].files+"\" >"+myjson[i].rname+"</option>";
			}
		
			$("#whpr").append(group);
		  }
		 });

}


function whpRchange(){
	$("#gzpfiles").children().remove()
	var showfiles  = "";
	var fileNames = $("#whpr").find("option:selected").attr('files');
			if(fileNames == "" || typeof(fileNames) == "undefined" || fileNames == "undefined") {
				return;
			}
			var fObjs = fileNames.split("|");
			for(var i=0; i<fObjs.length; i++){
				if (fObjs[i] == "") {
					break;
				}
				var info = fObjs[i].split("&");//seq & name & type;
				var path = "fileSystem_getAction.action?seq="+info[0]+"&minFile=0&type="+info[2]+"&projid="+projID;
				if (info.length < 2)
					continue;
				var fName = info[1];
				fName = fName.length>12?fName.substring(0,10)+"...":fName;
				showfiles = showfiles + '<a href='+path+'>'+fName+'</a>';
				
			}
				$("#gzpfiles").append(showfiles);

}


function te(){
		//	window.location.href='cashier.jsp';
			
				$.ajax({  
	      type: "POST",
		  url: 'SHOP_test1.action',
		  dataType: "json", 
		  success: function( data ){
			alert(data.json);
		  }
		 });
}