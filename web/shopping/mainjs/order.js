/**动态修改html页面**/
var projID ="WF_NYD";

window.onload=function(){
mainorders();
}

/*获取所有订单信息*/
function mainorders(){
	var orderinfo = "";
	var myjson = "";
	var myjsondtl = "";
	var order = "";
	var orders = "";
	var tamt = 0;
	var tprice = 0;
		/*获取所有订单信息*/
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetOrders.action',
		  dataType: "json",
		  success: function( data ){
			if(data.json==''){
				alert("当前用户未登录,请先登录");
			}else{
			myjson = eval(data.json);
			
			for(var i=0;i<myjson.length;i++){ 
				
			order = "<div class=\"order-info\">"+
                 	"<div class=\"info-div\"><span class=\"dealtime\">2017-03-14 10:00:36</span><span class=\"number\">订单号： </span>"+
							"<a href=\"#\" class=\"num\">"+myjson[i].ordercode+"</a><span class=\"operator\">运营商:"+myjson[i].sname+
							"</span><span class=\"pay\">支付方式:"+myjson[i].payinfo+"</span>"+
							"<span class=\"order-amount\">订单金额:<span class=\"tot-color\">¥"+myjson[i].totalprice +"</span></span>"+
								"<span class=\"order-details\"><a href=\"#\" onclick=\"showproducts(this)\">订单详情></a></span>"+
					"</div>"+
                 	"<ul class=\"details\" style=\"display:none;\">";
					myjsondtl = eval(myjson[i].dtl);
					orderinfo = "";
			for(var ii=0;ii<myjsondtl.length;ii++){ 
					orderinfo = orderinfo+"	<li class=\"details-list\"  >"+
										"                 <div class=\"d-left\"><img src=\"../fileSystem_getImgStreamAction.action?seq="+myjsondtl[ii].image.split(/&/)[0]+"&projid="+projID+"\"></div>"+
										"                 <div class=\"d-right\">"+
										"                 	<span class=\"list-column\">"+myjsondtl[ii].common_name+" "+myjsondtl[ii].specifications+"</span>"+
										"                 	<span class=\"list-column list-align\">x"+myjsondtl[ii].by_number+"</span><span class=\"list-column\">收货人:"+myjson[i].getname+
												" <span class=\"order-amount\">金额:<span class=\"tot-color\">¥"+myjsondtl[ii].tprice+
												"</span></span></span><span class=\"list-column text-align\">申请售后<span class=\"order-amount\">"+myjson[ii].ostatus+"</span></span>"+
										"                      </div>"+
										"                      <div class=\"evaluate\"><span class=\"evaluate-txt\"><a href=\"#\">评价</a></span><br><span class=\"buy-now\"><a href=\"#\">立即购买</a></span></div>"+
										"                 <div class=\"clear\"></div>"+
										"	</li>";
			}
			order = order + orderinfo +
						"</ul>"+
                 "</div>";
				tprice = parseInt(myjson[i].price)*parseInt(myjson[i].number);
				tamt =   parseInt(tamt) + parseInt(tprice);

				orders += order;
			}

				$("#orders").append(orders);
		  }
		  }
		 });
}



function showproducts(obj){
	obj.parentNode.parentNode.nextSibling.style.display="block"; 
	if(obj.text=='订单详情>'){
		obj.text='隐藏订单<'
	}else{
	obj.parentNode.parentNode.nextSibling.style.display="none"; 
		obj.text='订单详情>';
	}
}