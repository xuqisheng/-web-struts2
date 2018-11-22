//@ sourceURL=cartdisplay-control.js

// 初始化
$(function() {
	function logic() {
		$(".itxt").live("blur",function() {
			$.ajax({  
		    	url:"editCart.action?cartid="+$(this)[0].getAttribute("cart")+"&number="+$(this)[0].value,  
		    	type:'get',  
		    	dataType: 'json',
		    	success:function(data) {
		    		//更新数值
		    		var sum = $("#total")[0].innerHTML.substr(1)-0;;
		    		var old = $("#sum-"+data[0])[0].innerHTML-0;
		    		var n = $("#prize-"+data[0])[0].innerHTML*data[1];
		    		sum = sum-old+n;
		    		$("#total")[0].innerHTML = "￥"+sum.toFixed(2);
		    		$("#sum-"+data[0])[0].innerHTML = n.toFixed(2);
		    	}
		     });
		});
	}

	var options = {
		dataurl:"getCart.action",
		layer:"page/cartpage/cartdisplay",
		parent:$("#cart")[0],
		afterPage:logic,
		afterCss:null,
		clear:false,
		paramData:{
			userid:1
		}
	};
	control(options);
});





/*跳转结算页面*/
function nexttosettle(){
var groupCheckbox=$("input[id='chkb']");
var item = "";
var items = "";
    for(i=0;i<groupCheckbox.length;i++){
        if(groupCheckbox[i].checked){
             item =groupCheckbox[i].value;
			 if(i==0){
			 vals = "'"+item+"'";
			 }else{
			   items = items+",'"+item+"'";
			}
        }
    }
/*获取选择结算订单*/
	items = "'368','366'";
	alert(items);
   window.open("./settle/settle.jsp?items="+items);

}