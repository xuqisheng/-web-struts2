//@ sourceURL=comment-control.js
// 初始化
$(function() {
    var itemid = getQueryString("itemid");

    var options = {
        dataurl:"getItemComment.action",
        layer:"page/itempage/itemdetail/comment",
        parent:$("#comments")[0],
        afterPage:null,
        afterCss:null,
        clear:false,
        paramData:{
        	itemid:itemid
        }
    }
    control(options);
});