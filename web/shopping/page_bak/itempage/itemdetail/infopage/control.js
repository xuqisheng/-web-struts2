//@ sourceURL=infopage-control.js
$(function() {
	var itemid = getQueryString("itemid");

    var options = {
        dataurl:"getItemInfomation.action",
        layer:"page/itempage/itemdetail/infopage",
        parent:$("#infopage")[0],
        afterPage:null,
        afterCss:null,
        clear:false,
        paramData:{
        	itemid:itemid
        }
    };
    control(options);
});