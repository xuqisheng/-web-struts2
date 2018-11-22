$(function () {
        getcarts();


    }
);

function getcarts() {
    var cart = "";
    var supplier = "";
    var tamt = 0;
    var tprice = 0;
    var small = 0;
    var postage = 0;
    /*获取订单明细*/
    $.ajax({
        type: "POST",
        url: 'SHOP_GetCart.action',
        dataType: "json",
        success: function (data) {
            myjson = eval(data.json);

            for (var i = 0; i < myjson.length; i++) {

                supplier = "<div class=\"title-txt\"><input type=\"checkbox\"  onchange=\"ck_supplier(this)\" id=\"supp_check\" value=\"" + myjson[i].sid + "\" style=\"vertical-align: middle;\">&nbsp;&nbsp;" +
                    "<span class=\"title-name\">" + myjson[i].sname + "</span>";
                small = Number(myjson[i].small);
                postage = Number(myjson[i].postage);
                if (postage == 0) {
                    supplier = supplier + "<span  class=\"title-postage\">全场免邮</span>";
                } else {
                    supplier = supplier + "<span class=\"title-postage\">(单笔订单不满" + small + "元，将收取" + postage + "邮费)</span>";
                }
                supplier = supplier + "</div>";
                $("#item-list").append(supplier);
                myjsondtl = eval(myjson[i].dtl);
                for (var ii = 0; ii < myjsondtl.length; ii++) {
                    cart = "<div class=\"item-item item-selected\"><div class=\"item-form\" >" +
                        "<div class=\"cell p-goods\"><div class=\"goods-item\"><input id=\"chkb\" onchange=\"chck()\" type=\"checkbox\" svalue=\"" + myjson[i].sid + "\" value=\"" + myjsondtl[ii].cartid +
                        "\" itemcate=\"" + myjsondtl[ii].itemcate + "\"><div class=\"p-img\">" +
                        "<a  target=\"_blank\" href=\"../item.jsp?itemid=" + myjsondtl[ii].itemid + "&subid=" + myjsondtl[ii].itemid + "&projid=" + projID + "\">" +
                        "<img width=\"80\" height=\"80\" onerror=\"this.src='../img/default.jpg'\" " +
                        "src=\"fileSystem_getImgStreamAction.action?seq=" + myjsondtl[ii].image.split(/&/)[0] + "&projid=" + projID + "\">" +
                        "</a></div><div class=\"item-msg\"><div class=\"p-name\"><a target=\"_blank\"  href=\"../item.jsp?itemid=" + myjsondtl[ii].itemid + "&subid=" + myjsondtl[ii].itemid + "&projid=" + projID + "\">" +
                        myjsondtl[ii].itemname + "</a></div></div></div></div><div class=\"cell p-props p-props-new\"></div><div class=\"cell p-price\">" +
                        "<strong id=\"prize-" + myjsondtl[ii].cartid + "\">" + myjsondtl[ii].prize + "</strong></div><div class=\"cell p-quantity\">" +
                        "<div class=\"quantity-form\"><a id=\"minus\" data-num=\"-1\" data-tpa=\"DECREMENT\" onclick=\"edit(this,'minus')\" href=\"javascript:;\" class=\"minus\">-</a>" +
                        "<input id=\"vals\" type=\"text\" class=\"itxt\" onblur=\"edit(this,'edit')\" value=\"" + myjsondtl[ii].numbers + "\" cart=\"" + myjsondtl[ii].cartid + "\">" +
                        "<a id=\"add\" data-num=\"1\" data-tpa=\"INCREMENT\" href=\"javascript:;\" onclick=\"edit(this,'add')\" class=\"add\">+</a></div></div>" +
                        "<div class=\"cell p-sum\"><strong id=\"sum-" + myjsondtl[ii].cartid + "\" name=\"p_sum\">" + (Number(myjsondtl[ii].prize) * Number(myjsondtl[ii].numbers)).toFixed(2) +
                        "</strong></div>" +
                        "<div class=\"cell p-ops\"><a class=\"cart-remove\" href=\"deleteCart.action?cartid=" + myjsondtl[ii].cartid +
                        "\">删除</a> <a class=\"cart-remove\" name=\"COLLECTION\" itemid=\"" + myjsondtl[ii].itemid + "\">点击收藏</a></div></div></div>";
                    tamt = Number(myjsondtl[ii].prize) * Number(myjsondtl[ii].numbers);
                    tprice = tprice + tamt;
                    $("#item-list").append(cart);

                }
                $("[name='COLLECTION']").click(function () {
                    $.ajax({
                        type: "POST",
                        url: 'SHOP_Collection.action',
                        data: {'itemid': $(this).attr('itemid')},
                        dataType: "json",
                        success: function (data) {
                        }
                    });

                });
            }

            $("#totalcart").html(tprice.toFixed(2));
        }
    });

    $("#total").html('0.00');


}


/*跳转结算页面*/
function nexttosettle() {
    var groupCheckbox = $("input[id='chkb']");
    var item = "";
    var items = "";
    var itemcates = "";
    var a = 0;
    var c = 0;
    var category = '';
    for (i = 0; i < groupCheckbox.length; i++) {

        if (groupCheckbox[i].checked) {
            item = groupCheckbox[i].value;
            itemcates = groupCheckbox[i].getAttribute('itemcate').split('.');

            if (itemcates[3] == '22') {
                //alert('结算材料中存在管制品,无法统一结算');
                c += 1;
                category = itemcates[4]
                //return;
            }

            if (a == 0) {
                items = item;
            } else {
                items = items + "," + item;
            }
            a = a + 1;
        }
    }
    if (a > 1) {
        if (c > 1) {
            alert('管制材料无法统一结算');
            return;
        } else if (c == 1) {
            alert('普通材料跟管制材料无法统一结算');
            return;

        }
    }
    items = "'" + items + "'";
    /*获取选择结算订单*/
//	items = "'368','366'";
    if (items == "''") {
        alert('请选择需要结算的商品');
    } else {
        window.open("../settle/settle.jsp?cartids=" + items + "&category=" + category);
    }

}

function ck_supplier(obj) {
    if (obj.checked) {
        $("input[id='chkb'][svalue='" + obj.value + "']").attr('checked', true);
    } else {
        $("input[id='chkb'][svalue='" + obj.value + "']").attr('checked', false);
    }
    chck();
}

function ck_all(obj) {
    if (obj.checked) {
        $("input[id='chkb']").attr('checked', true);
    } else {
        $("input[id='chkb']").attr('checked', false);
    }
    chck();
}

function chck() {
    var groupCheckbox = $("input[id='chkb']");
    var cartid = "";
    var select_amt = 0;
    var pamt = 0;
    for (i = 0; i < groupCheckbox.length; i++) {

        if (groupCheckbox[i].checked) {
            cartid = groupCheckbox[i].value;
            pamt = $("#sum-" + cartid).html();
            select_amt = Number(select_amt) + Number(pamt);
        }
    }

    $("#total").html(select_amt.toFixed(2));
}

function edit(obj, etype) {
    var t = $(obj).parent().find('input[class*=itxt]');
    if (etype == 'add') {
        t.val(parseInt(t.val()) + 1);
    } else if (etype == 'minus') {
        if (parseInt(t.val()) > 1) {
            t.val(parseInt(t.val()) - 1);
        } else {
            $(".minus").attr("disabled", "disabled");
            alert("no more goods");
        }
    }

    $(".minus").removeAttr("disabled");
    $.ajax({
        url: "editCart.action?cartid=" + obj.parentNode.childNodes[1].getAttribute("cart") + "&number=" + obj.parentNode.childNodes[1].value,
        type: 'get',
        dataType: 'json',
        success: function (data) {
            //更新数值$(".itxt")[0].value
            //var sum = $("#totalcart")[0].innerHTML;;
            var old = $("#sum-" + data[0])[0].innerHTML - 0;
            var n = $("#prize-" + data[0])[0].innerHTML * data[1];
            //sum = sum-old+n;
            //$("#totalcart")[0].innerHTML =  sum.toFixed(2);
            $("#sum-" + data[0])[0].innerHTML = n.toFixed(2);

            chck();
        }
    });
}
// function deleteAll(){
//     $.ajax({
//         url: "deleteCart_deleteAll.action?cartid=" + obj.parentNode.childNodes[1].getAttribute("cart") ,
//         type: 'get',
//         dataType: 'json',
//         success: function (data) {
//             chck();
//         }
//     });
// }



