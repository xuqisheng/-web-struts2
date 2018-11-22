//@ sourceURL=iteminfo-control.js
// 初始化
$(function() {
	var itemid = getQueryString("itemid");

    function logic() {
        $(".p-choose-wrap .item").live("click",
            function(e) {
                $(this).addClass('selected').siblings().removeClass('selected').end();
            }
        );
        
        $(".changeprize").live("click",
        	function(e){
        		var id = $(this)[0].getAttribute('sub');
        		$("#spec-n1")[0].children[0].src = this.children[0].src;
        		var prize = $(".p-price");
        		for (var i=0;i<prize.length;i++) {
        			if (prize[i].getAttribute('sub')==id) {
        				prize[i].setAttribute("style","display:display");
        			} else {
        				prize[i].setAttribute("style","display:none");
        			}
        		}
        		$(".btn-append")[0].setAttribute("href","addCart.action?subid="+id+"&num="+$("#buy-num")[0].value);
        	}
        );
        
        $("#buy-num").live("blur",
        	function(e) {
        		var href = $(".btn-append")[0].getAttribute("href");
        		if (href.indexOf("&num=")>=0) {
        			href = href.substring(0,href.indexOf("&num="))+"&num="+$("#buy-num")[0].value;
        		} else {
        			href += "&num="+$("#buy-num")[0].value;
        		}
        		$(".btn-append")[0].setAttribute("href",href);
        	}
        );
        
    }

    var options = {
        dataurl:"getItemInfomation.action",
        layer:"page/itempage/iteminfo",
        parent:$("#product-intro")[0],
        afterPage:logic,
        afterCss:null,
        clear:false,
        paramData:{
        	itemid:itemid
        }
    };
    control(options);
});