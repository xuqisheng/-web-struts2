//@ sourceURL=cartdisplay-control.js

// 初始化
function test(objid,intx){
			var input = document.getElementById(objid);
			if (parseInt(input.value)==0 && intx == -1)
				{ alert("no more goods")	}
			else{
				input.value = parseInt(input.value) + intx;
				}
			
		
	
	}
$(function() {


	function logic() {
	$(".add").click(function(){
		var t = $(this).parent().find('input[class*=itxt]');
		t.val(parseInt(t.val())+1);
		$(".minus").removeAttr("disabled");
	 			$.ajax({  
		    	url:"editCart.action?cartid="+this.parentNode.childNodes[1].getAttribute("cart")+"&number="+this.parentNode.childNodes[1].value,  
		    	type:'get',  
		    	dataType: 'json',
		    	success:function(data) {
		    		//更新数值$(".itxt")[0].value
		    		var sum = $("#total")[0].innerHTML.substr(1)-0;;
		    		var old = $("#sum-"+data[0])[0].innerHTML-0;
		    		var n = $("#prize-"+data[0])[0].innerHTML*data[1];
		    		sum = sum-old+n;
		    		$("#total")[0].innerHTML = "￥"+sum.toFixed(2);
		    		$("#sum-"+data[0])[0].innerHTML = n.toFixed(2);
		    	}
		     });
	});
	$(".minus").click(function(){
		var t = $(this).parent().find('input[class*=itxt]');
		if (parseInt(t.val())>1) {
		t.val(parseInt(t.val())-1);
		}else{
		$(".minus").attr("disabled","disabled")
		alert("no more goods");}
	 			$.ajax({  
		    	url:"editCart.action?cartid="+this.parentNode.childNodes[1].getAttribute("cart")+"&number="+this.parentNode.childNodes[1].value,  
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
	  //afterPage:newlogic,
		afterCss:null,
		clear:false,
		paramData:{
			userid:1
		}
	};
	control(options);
});