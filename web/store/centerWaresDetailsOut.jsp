<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>中心仓库入库查询</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script src="../js/vue.js"></script>
    <script src="../js/vue-resource.js"></script>
    <script src="../jquery/custom/jquery.min.js"></script>
    <script src="../jquery/custom/jquery.table2excel.min.js"></script>
    <script >
        var startTime = window.parent.startTime;
        var endTime = window.parent.endTime;
        var myDate = new Date();
        var userName = window.parent.userName;
        var time = myDate.toLocaleString(); //获取日期与时间
        var MultiRows = window.parent.MultiRows;
        var unitSend = window.parent.unitName;
        var supplier_name = window.parent.supplier_name;
        var flag =  window.parent.flag;// 汇总是1
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
        table {
            width:1000px;
            margin-left: auto;
            margin-right: auto;
            align-content: center;
            border-width: 0px;
            border-style: solid;
            border-color: #666666;
            border-collapse: collapse;
        }

        tbody.inTable tr {
            border-style: solid;
            border-color: #666666;
            border-width: 0px;
            border-style: solid;
        }

        tbody.inTable td {
            text-align: center;
            border-width: 1px;
            border-style: solid;
            border-color: #666666;
            height: 10%;
        }

    </style>
</head>

<body>
    <div id="tableContent">
        <table >
            <!--表头-->
            <thead>
                <tr>
                    <td align="center" colspan="12" style="font-size: 26px;padding: auto" >
                        <h3 id="tableName">出库明细查询表{{collect}}</h3>
                    </td>
                </tr>

            </thead>

            <tbody class="inTable">
            <tr>
                <td colspan="2" style="border: 0px;text-align: left">&nbsp;&nbsp;供应商: {{supplier_name}}</td>
                <td colspan="4" style="border: 0px;text-align: left">日期:  {{startTime}}  &nbsp;至 {{endTime}} </td>
                <td colspan="2" style="border: 0px;text-align: left">送货单位: {{unitSend}}</td>
            </tr>

            <template  v-for="type in jsonArray">

                <tr >
                    <td  style="text-align: left" colspan="8"><b>&nbsp;&nbsp;客户名称:&nbsp;&nbsp;{{type.custom_name}}</b></td>
                </tr>
                <tr>
                    <%--<td>编号</td>--%>
                    <td><b>商品名称</b></td>
                    <td><b>单位</b></td>
                    <td><b>计划数量</b></td>
                    <%--<td>价格</td>--%>
                    <td><b>实际数量</b></td>
                    <%--<td>编号</td>--%>
                    <td><b>商品名称</b></td>
                    <td><b>单位</b></td>
                    <td><b>计划数量</b></td>
                    <td><b>实际数量</b></td>
                    <%--<td>金额(元)</td>--%>
                </tr>
                <tr v-for="(item, index) in type.list" v-if="index%2==0" >
                    <%--<td>{{getTypeList(type.list, index).product_id}}</td>--%>
                    <td>{{getTypeList(type.list, index).product_name}}</td>
                    <td>{{getTypeList(type.list, index).package_unit}}</td>
                    <td>{{getTypeList(type.list, index).out_num}}</td>
                    <%--<td>{{getTypeList(type.list, index).in_price|currency}}</td>--%>
                    <td> &nbsp;</td>
                    <template v-if="testLen(type.list,index)">
                        <%--<td>{{getTypeList(type.list, index+1).product_id}}</td>--%>
                        <td>{{getTypeList(type.list, index+1).product_name}}</td>
                        <td>{{getTypeList(type.list, index+1).package_unit}}</td>
                        <td>{{getTypeList(type.list, index+1).out_num}}</td>
                        <%--<td>{{getTypeList(type.list, index+1).in_price|currency}}</td>--%>
                            <td> &nbsp;</td>
                    </template>
                    <template v-else>
                        <td></td>  <td></td>  <td></td>  <td></td>
                    </template>
                </tr>
                <tr>
                    <td colspan="8" style="text-align: left;"><b>&nbsp;&nbsp;小计 &nbsp;:{{countNumber(type.list)|currency}}</b></td>
                </tr>
            </template>

            </tbody>
            <tfoot>

                <tr >
                    <td style="border: 0px;" colspan="8">
                        (<template  v-for="type in typeArray">
                            {{type.name}}、
                        </template>)
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="border: 0px;">&nbsp;</td>
                </tr>
                <tr>
                    <td style="border: 0px;" colspan="4" align="left" >制表人：{{userName}}</td>
                    <td style="border: 0px;" colspan="4" align="left" >制表时间：{{time}}</td>
                </tr>
            </tfoot>
        </table>

        <button type="button" v-on:click="printPage()" >打印界面</button>
        <button id="excelButton" type="button" >导出Excel</button>
    </div>

</body>
<script src="../js/custom/centerWaresDetailsOut.js"></script>
</html>
