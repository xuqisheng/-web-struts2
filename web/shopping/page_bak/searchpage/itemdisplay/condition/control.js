//@ sourceURL=condition-control.js

// 初始化
$(function() {
	var cate = getQueryString("category");
	var keyword = decodeURI(decodeURI(getQueryString("keyword")));
	var parameter = getQueryString("parameter");
	var value = decodeURI(decodeURI(getQueryString("value")));
	if (parameter!=null&&parameter!=undefined) {
		parameter = parameter.split(",");
	}
	if (value!=null&&value!=undefined) {
		value = value.split(",");
	}
	function logic(){
		$(".parameter").live("click",function(e) {
			var flag = -1;
			var ps = "";
			var vs = "";
			var url = "search.jsp?category="+cate+"&keyword="+keyword+"&projid="+projID;
			
			if (parameter!=null&&parameter!=undefined) {
				for (var k=0;k<parameter.length;k++) {
					if (this.getAttribute("parameter")==parameter[k]&&this.innerHTML==value[k]) {
						flag = k;
						break;
					}
				}
				for (var k=0;k<parameter.length;k++) {
					if (k==flag) {
						continue;
					}
					ps+=parameter[k]+",";
					vs+=value[k]+",";
				}
				
			}
			
			if (flag==-1) {
				ps+=this.getAttribute("parameter");
				vs+=this.innerHTML;
			} else {
				ps = ps.substring(0, ps.length-1);
				vs = vs.substring(0, vs.length-1);
			}
			
			if (ps!=""&&vs!="") {
				url+="&parameter="+ps+"&value="+vs;
			}
			window.location.href = encodeURI(encodeURI(url));
		});
	}
	
	var options = {
		dataurl:"getCondition.action",
		layer:"page/searchpage/itemdisplay/condition",
		parent:$("#J_selector")[0],
		afterPage:logic,
		afterCss:null,
		clear:false,
		paramData:{
			category:cate,
			keyword:keyword
		}
	};
	control(options);
});