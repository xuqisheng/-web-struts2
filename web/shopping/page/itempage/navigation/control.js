//@ sourceURL=navigation-control.js
// 初始化
$(function() {
    function logic() {
        //类别菜单交互实现
        $(".item").live("mouseover",
        function(e) {
            $(this).addClass('hover').siblings().removeClass('hover').end();
            $("#category-item-" + $(this).attr("data-index")).addClass('hover').siblings().removeClass('hover').end();
            e.stopPropagation();
        });
        $(".item-sub").live("mouseover",
        function(e) {
            e.stopPropagation();
        });
        $("#mainnav").live("mouseover",
        function(e) {
            $("#categorys-2014").css("z-index","20");
            $("#items").css("display", "block");
            e.stopPropagation();
        });
        $("#categorys-2014").live("mouseover",
        function(e) {
            e.stopPropagation();
        });
        $("#wrapper").live("mouseover",
        function(e) {
            $(".item").removeClass('hover').end();
            $(".item-sub").removeClass('hover').end();
            $("#items").css("display", "none");
            $("#categorys-2014").css("z-index","1");
            e.stopPropagation();
        });
        $("body").live("mouseover",
        function() {
            $(".item").removeClass('hover').end();
            $(".item-sub").removeClass('hover').end();
            $("#categorys-2014").css("z-index","1");
            $("#items").css("display", "none");
        });

        //调整css
        $("#categorys-2014").css("height", "48px");
        $("#categorys-2014 .dd").css("height", (1 + $("#mainitem").children().length * 31) + "px");
        $("#categorys-2014 .subitems").css("min-height", ($("#mainitem").children().length * 31 - 11) + "px");
    }
    
    var options = {
        dataurl:"getCategory.action",
        layer:"page/itempage/navigation",
        parent:$("#nav-2014")[0],
        afterPage:logic,
        afterCss:null,
        clear:false,
        paramData:{}
    }
    control(options);
});