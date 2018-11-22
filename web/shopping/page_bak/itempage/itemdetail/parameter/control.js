//@ sourceURL=parameter-control.js
// 初始化
$(function() {
    var itemid = getQueryString("itemid");
    var options = {
        dataurl:"getItemParameter.action",
        layer:"page/itempage/itemdetail/parameter",
        parent:$("#parameter")[0],
        afterPage:null,
        afterCss:null,
        clear:false,
        paramData:{
        	itemid:itemid
        }
    }
    control(options);
});