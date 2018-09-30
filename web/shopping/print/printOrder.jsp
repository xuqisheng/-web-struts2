<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>结算单打印</title>
    <script src="../js/vue.js"></script>
    <script src="../js/vue-resource.js"></script>
    <script type="text/javascript" src="../mainjs/jquery-1.4.4.js"></script>
    <style>

        #tableContent{
            text-align: center;
        }
        button{
            border: 1px;
            color: black;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
        }
        table.inTable {
            width:1000px;
            margin-left: auto;
            margin-right: auto;
            align-content: center;
            border-width: 0px;
            border-style: solid;
            border-color: #666666;
            border-collapse: collapse;
        }
        table.inTable tr{
            border:0px solid #666666;
        }
        table.inTable td {
            text-align: center;
            border-style: solid;
            border-color: #666666;
            width: 10%;
            height: 10%;
        }
        .noBorder{
            border-width: 0px;
            border-style: hidden;
        }
        h3{
            margin: auto;
            align-content: center;
        }
        .currency{
            text-align: right;
        }

    </style>
</head>
<body>

<div id="tableContent">
    <table class="inTable">
        <tr><td  class="noBorder" colspan="9"><h2 id="tableName">{{checkList.apply_no}}号结算单</h2></td></tr>
        <tr>
            <td ><b>结算单号</b></td>
            <td colspan="2"><b>结算申请用户</b></td>
            <td colspan="2"><b>结算申请总金额</b></td>
            <td><b>已上传发票金额</b></td>
            <td colspan="3"><b>结算申请日期</b></td>
        </tr>
        <tr>
            <td >{{checkList.apply_no}}</td>
            <td colspan="2">{{checkList.username}}</td>
            <td style="text-align: right" colspan="2">{{checkList.samt|currency}}</td>
            <td style="text-align: right" >{{uploadedVoiceMount|currency}}</td>
            <td colspan="3">{{checkList.indate}}</td>
        </tr>
        <tr >
            <td class="noBorder"><h3 align="center">订单明细</h3></td>
            <%--<td class="noBorder" colspan="8">&nbsp;</td>--%>
        </tr>
        <tr>
            <td ><b>订单号</b></td>
            <td><b>订单总金额</b></td>
            <td><b>结算申请项目</b></td>
            <td colspan="2"><b>结算申请费用项</b></td>
            <td class="noBorder" colspan="5" ></td>
        </tr>
        <tbody v-for="det in orderDetailsF">
        <tr >
            <td>{{det.ordercode}}</td>
            <td style="text-align: right">{{det.amt|currency}}</td>
            <td>{{det.uni_prj_code}}</td>
            <td colspan="2">{{det.b_code}}</td>
            <td class="noBorder" colspan="5" ></td>
        </tr>

        <tr >
            <td ><b>订单明细</b></td>
            <td><b>曾用名</b></td>
            <td><b>化学名</b></td>
            <td><b>英文名</b></td>
            <td><b>CAS号</b></td>
            <td><b>规格型号</b></td>
            <td><b>单位</b></td>
            <td><b>购买数量</b></td>
            <td><b>单价/元</b></td>
        </tr>
        <tr v-for="(details,index) in det.detailList.detailsList">
            <td>{{index+1}}</td>
            <td>{{details.common_name}}</td>
            <td>{{details.chemical_name}}</td>
            <td>{{details.english_name}}</td>
            <td>{{details.cas_name}}</td>
            <td>{{details.specifications}}</td>
            <td>{{details.measurement_unit}}</td>
            <td>{{details.by_number}}</td>
            <td style="text-align: right">{{details.price|currency}}</td>
        </tr>
        <tr class="noBorder">
            <td class="noBorder"><h3>审核信息</h3></td>
            <%--<td class="noBorder" colspan="8"></td>--%>
        </tr>
        <tr class="noBorder">
            <%-- akEPxkPXXuLz --%>
            <td></td>
            <td><b>审核人姓名</b></td>
            <td><b>审核结果</b></td>
            <td><b>审核意见</b></td>
            <td colspan="5" class="noBorder"></td>
        </tr>
        <tr class="noBorder" v-for="(obj,index) in det.detailList.commentsList">
            <td>{{index+1}}</td>
            <td>{{obj.check_name}}</td>
            <td>{{obj.check_result|booleanStr}}</td>
            <td>{{obj.check_msg}}</td>
            <td colspan="5" class="noBorder"></td>
        </tr>
        </tbody>
        <tr >
            <td class="noBorder"><h3>发票信息</h3></td>
        </tr>
        <tr class="noBorder">
            <td><b>发票号</b></td>
            <td><b>发票金额</b></td>
            <td><b>附件</b></td>
            <td style="border: 0px;" colspan="6" class="noBorder"></td>
        </tr>
        <tr v-for="invoice in orderInvoice" class="noBorder">
            <td>{{invoice.invoice_no}}</td>
            <td style="text-align: right">{{invoice.invoice_amt|currency}}</td>
            <td><img width="200" height="200" v-bind:src="invoice.invoice_doc"></td>
            <td colspan="6" class="noBorder"></td>
        </tr>

    </table>
    <button type="button" v-on:click="printPage()" >打印界面</button>
    <%--<button id="excelButton" type="button" >导出Excel</button>--%>
</div>
</body>
<script src="./js/printOrder.js"></script>
</html>
