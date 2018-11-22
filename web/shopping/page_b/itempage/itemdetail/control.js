//@ sourceURL=itemdetial-control.js
// 初始化
$(function() {
    function logic() {
        $(".trig-item").live("click",function(e) {
            $(this).addClass('curr').siblings().removeClass('curr').end();
        });
    }
    var options = {
        dataurl:null,
        layer:"page/itempage/itemdetail",
        parent:$("#itemdetail")[0],
        afterPage:logic,
        afterCss:null,
        clear:false,
        paramData:{}
    }
    control(options);
});