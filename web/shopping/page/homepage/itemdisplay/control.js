//@ sourceURL=itemdisplay-control.js

// 初始化
$(function() {
	var options = {
	    dataurl:null,
		layer:"page/homepage/itemdisplay",
		parent:$("#J_searchWrap")[0],
		afterPage:null,
		afterCss:null,
		clear:false,
		paramData:{}
	}
	control(options);
});