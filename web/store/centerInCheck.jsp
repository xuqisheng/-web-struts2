<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script src="../jquery/custom/jquery.min.js"></script>
    <script src="../jquery/custom/jquery.table2excel.min.js"></script>
    <title>入库验收单打印</title>
    <script>/*获得当前时间*/
    var myDate = new Date();
    var time = myDate.toLocaleString();
    /*获取日期与时间*/
    var supplier_name = window.parent.supplier_name;
    var MultiRows = window.parent.MultiRows;
    /*先获取到相关变量值*/
    var batNo = window.parent.batNo;
    var total = window.parent.total;
    var req_date = window.parent.req_date;
    var url = document.URL;
    var uarr = url.split('/');
    var userName = window.parent.$.UC.bindData("#0-userinfo.username#");

    function toExcelData() {
        $("#printContent").table2excel({name: "Worksheet Name", fileext: ".xls", filename: "入库验收单打印" + ".xls"});
        alert("导出成功！");
    }

    function orderbuy(printContent, tojsons) {
        var unitName = "入库验收单打印";
        var tojsons = eval(tojsons);
        var v_amount = 0;
        $.each(tojsons, function (i) {
            $.each(tojsons[i].list, function (j, apt) {
                $.each(apt.list, function (k, arr) {
                    v_amount = v_amount + arr.total * 1;
                });
            });
        });
        var myDate = new Date();
        var time = myDate.toLocaleString();
        /*获取日期与时间*/
        var url = document.URL;
        var uarr = url.split('/');
        var html = "";
        html += ('<div class="printdiv" id = "printdiv"> ');
        html += '<br>';
        html += ('<thead   border="0" align="center" cellpadding="0" cellspacing="0">');
        html += ('<tr align="center" class="TitleStyle">');
        html += ('<td style="border:0px;" colspan="12" align="center"  width="40%"><h1>' + unitName + '</h1></td>');
        html += ('</tr>');
        html += ('</thead>');
        html += ('<br>');
        html += ('<tbody   border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 20px;border:1px #999999 solid;line-height: 22px">');
        html += ('<tr align="left" class="text" style="line-height:28px;">');
        html += ('<td  style="border:0px;" colspan="12" align="left" class="title">&nbsp;</td>');
        html += ('</tr>');
        // html += ('<tr align="left" class="text" style="line-height:28px;">');
        // html += ('<td  style="border-left:0px;border-right:0px;border-top: 0px;" ' + 'colspan="12" align="left" class="title">&nbsp;打印时间：' + time + '</td>');
        // html += ('</tr>');
        html += ('<tr align="left" class="text" style="line-height:28px;">');
        html += ('<td style="border:0px;" colspan="12" align="right" class="title">总金额：' + Number(v_amount).toFixed(2) + '</td>');
        html += ('</tr>');
        html += ('</tbody>');
        html += ('<tbody class="print_table"  border="1" cellpadding="0" cellspacing="0">');

        $.each(tojsons, function (i) {
            var cols = tojsons[i];
            var supplier_name = cols.supplier_name;// 批次
            var listp = cols.list;
            html += '<tr class=text align="center" >';
            html += '<td align="center" colspan="12"  style="border:none"><h2>';
            html += supplier_name;
            html += '</2></td>';
            html += '</tr>';
//遍历批次
                $.each(listp, function (li) {
                    var pici = listp[li].pici;
                    var arr_details = listp[li].list;
                    html += '<tr class=text align=center >';
                    html += '<td  class="title" style="border:none"><strong>批次</strong></td>';
                    html += '<td style="border:none"  class="title" ><strong>' + pici + '</strong></td>';
                    html += '</tr>';
                    html += '<tr class=text align=center >';
                    html += '<td >序号</td>';
                    html += '<td >商品名称&nbsp;</td>';
                    html += '<td >部门&nbsp;</td>';//1
                    html += '<td >生产日期批号&nbsp;</td>';
                    html += '<td >保质期&nbsp;</td>';
                    html += '<td >生产厂家&nbsp;</td>';
                    html += '<td >供应商联系人&nbsp;</td>';
                    html += '<td >进货数量&nbsp;</td>';
                    html += '<td >包装单位&nbsp;</td>';
                    html += '<td >商品规格&nbsp;</td>';
                    html += '<td >单价&nbsp;</td>';
                    html += '<td >小计&nbsp;</td>';
                    html += '</tr>';

                    $.each(arr_details, function (k) {
                        var id = arr_details[k].id;
                        var product_name = arr_details[k].product_name;
                        var in_num = arr_details[k].in_num;

                        var dep = arr_details[k].dep;
                        var birth_num = arr_details[k].birth_num;
                        var save_time = arr_details[k].save_time;
                        var produce_nuame = arr_details[k].produce_nuame;
                        var supplier_person = arr_details[k].supplier_person;

                        var package_unit = arr_details[k].package_unit;
                        var specifications = arr_details[k].specifications;
                        var in_price = arr_details[k].in_price;
                        var total = arr_details[k].total;
                        html += '<tr class=text align=center width="100%" >';
                        html += '<td >' + (k + 1) + '</td>';                          //序号
                        html += '<td >' + product_name + '</td>';               //商品名称
                        html += '<td >' + dep + '</td>';
                        html += '<td >' + birth_num + '</td>';          //生产日期批号
                        html += '<td >' + save_time + '天</td>';
                        html += '<td >' + produce_nuame + '</td>';
                        html += '<td >' + supplier_person + '</td>';
                        html += '<td >' + in_num + '</td>';         //数量
                        html += '<td>'  + package_unit + '</td>';
                        html += '<td >' + specifications + '</td>';
                        html += '<td align="right">' + Number(in_price).toFixed(2) + '</td>';
                        html += '<td align="right">' + Number(total).toFixed(2) + '</td>';
                        html += '</tr>';
                    });
                    html += '<tr class=text align=center >';
                    html += '<td class="title" style="border:none">&nbsp;</td>';
                    html += '<td style="border:none"  class="title" >&nbsp;</td>';
                    html += '</tr>';

                });
            });
        html += '</tbody>';

        html += ('<tfoot  border="0" align="center" cellpadding="0" cellspacing="0" style="line-height: 22px;border:1px #999999 solid;line-height: 18px">');
        html += ('<tr align="left" class="text" style="line-height:28px;">');
        html += ('<td colspan="12" style="border: 0px;"  align="left" class="title">&nbsp;</td>');
        html += ('</tr>');
        html += ('<tr align="left" style="line-height:28px;">');
        html += ('<td style="border: 0px;" colspan="4" align="left" >制表人：'+userName+'</td>');
        html += ('<td style="border: 0px;text-align: left" colspan="8" align="left" >打印时间：'+time+'</td>');
        html += ('</tr>');
        html += '</tfoot>';
        html += ('</div>');

        return html;
    }

    $(function () {
        $.ajax({
            type: "POST",
            url: 'CenterInCheckAction_centerIn.action',
            dataType: "json",
            data: {
                "multiParams": MultiRows,
            },
            success: function (data, textStatus) {
                var printContent = $('#printContent');
                printContent.empty();
                var html = "";
                if (data) {
                    html = orderbuy(printContent, data.json);
                }
                printContent.html(html);
            },
            error: function (e) {
                alert('error:' + e);
            }
        });

    });

    function doprint() {
            window.print();
    }

    </script>
</head>
<body>
<table width="100%" border="0" align="center" cellpadding="0"
       cellspacing="0">
    <tr align="center">
        <td><input name="BtnPrint" type="button" id="BtnPrint"
                   value="打印结算" onClick="doprint();"></td>
    </tr>
    <tr align="center">
        <td>&nbsp;</td>
    </tr>
    <tr align="center">
        <td><input name="BtnPrint" type="button" value="导出表格" onClick="toExcelData();"></td>
    </tr>
</table>
<table id="printContent" width="100%" align="center" cellpadding="0" cellspacing="0" border="1">

</table>
</body>

</html>