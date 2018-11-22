// 初始化
$(function() {
	var options = {
		dataurl:"getItem.action",
		layer:"page/homepage/itemdisplay/items",
		parent:$("#J_goodsList")[0],
		afterPage:null,
		afterCss:null,
		clear:false,
		paramData:{}
	}
	control(options);
});