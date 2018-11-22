//@ sourceURL=pages-control.js


// 初始化
$(function() {
	var cate = getQueryString("category");
	var parameter = getQueryString("parameter");
	var value = decodeURI(decodeURI(getQueryString("value")));
	var keyword = decodeURI(decodeURI(getQueryString("keyword")));
	var page = getQueryString("page");

	var baseurl = "search.jsp"+window.location.search;
	if (window.location.search=="") {
		baseurl+="?";
	}
	baseurl = decodeURI(decodeURI(baseurl));

	function logic() {
		if (window.location.search!="") {
			var url = "search.jsp"+window.location.search;
			if (url.indexOf("page=")>=0) {
				url = url.substring(0,url.indexOf("page="));
			}
			url+="&page=\"+$('#page_jump_num')[0].value&projid="+projID;
			$("#pn-sub")[0].setAttribute("onclick","window.location.href=\""+url+"");
		} else {
			$("#pn-sub")[0].setAttribute("onclick","window.location.href=\"search.jsp?page=\"+$('#page_jump_num')[0].value&projid="+projID);
		}
	}
	
	
	var options = {
		dataurl:"getItemPage.action",
		layer:"page/searchpage/itemdisplay/page",
		parent:$("#pages")[0],
		afterPage:logic,
		afterCss:null,
		clear:false,
		paramData:{
			category:cate,
			parameter:parameter,
			value:value,
			page:page,
			keyword:keyword,
			baseurl:baseurl
		}
	};
	control(options);
});