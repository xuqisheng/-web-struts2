<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>结算单打印</title>
    <script src="../js/vue.js"></script>
    <script src="../js/vue-resource.js"></script>
    <script language="JavaScript">
        // var startTime = window.parent.startTime;
        // var endTime = window.parent.endTime;
        // var class_id = window.parent.class_id;
    </script>
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

        table.inTable tr {
            border-style: solid;
            border-color: #666666;
            border-width: 0px;
            border-style: solid;
        }

        table.inTable td {
            text-align: center;
            border-width: 1px;
            border-style: solid;
            border-color: #666666;
            width: 15%;
            height: 10%;
        }
    </style>
</head>
<body>

<div id="tableContent">
    <table class="inTable">
        <tr>{{checkList.apply_no}}号结算单</tr>
        <tr>
            <td>结算单号</td>
            <td>结算申请用户</td>
            <td>结算申请总金额</td>
            <td>已上传发票金额</td>
            <td>结算申请日期</td>
        </tr>
        <tr>
            <td>{{checkList.apply_no}}</td>
            <td>{{checkList.username}}</td>
            <td>{{checkList.samt}}</td>
            <td>{{uploadedVoiceMount}}</td>
            <td>{{checkList.indate}}</td>
        </tr>
        <tr><td>订单明细</td></tr>
        <tr>
            <td>订单号</td>
            <td>订单总金额</td>
            <td>结算申请项目</td>
            <td>结算申请费用项</td>
        </tr>
        <tbody v-for="det in orderDetailsF">
        <tr >
            <td>{{det.ordercode}}</td>
            <td>{{det.amt}}</td>
            <td>{{det.uni_prj_code}}</td>
            <td>{{det.b_code}}</td>
            <%--<td>{{det.detailList}}</td>--%>
        </tr>
        <tr >
            <td></td>
            <td>曾用名</td>
            <td>化学名</td>
            <td>英文名</td>
            <td>CAS号</td>
            <td>规格型号</td>
            <td>单位</td>
            <td>购买数量</td>
            <td>单价/元</td>
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
            <td>{{details.price}}</td>
        </tr>
        <tr>
            <td></td>
            <td>审核人姓名</td>
            <td>审核结果</td>
            <td>审核意见</td>
        </tr>
        <tr v-for="(obj,index) in det.detailList.commentsList">
            <td>{{index+1}}</td>
            <td>{{obj.check_name}}</td>
            <td>{{obj.check_result}}</td>
            <td>{{obj.check_msg}}</td>
        </tr>
        </tbody>
        <tr></tr>
    </table>

</div>
</body>
<script src="./js/printOrder.js"></script>
</html>
