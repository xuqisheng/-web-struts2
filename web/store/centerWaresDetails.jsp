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
        var flag =  window.parent.flag;
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
                        <h3>入库明细查询表{{collect}}</h3>
                    </td>
                </tr>

            </thead>

            <tbody class="inTable">
            <tr>
                <td colspan="4" style="border: 0px;text-align: left">&nbsp;&nbsp;供应商: {{supplier_name}}</td>
                <td colspan="8" style="border: 0px;text-align: left">日期:  {{startTime}}  至 {{endTime}} </td>
            </tr>
            <tr>
                <td>编号</td>
                <td>商品名称</td>
                <td>单位</td>
                <td>数量</td>
                <td>价格</td>
                <td>金额(元)</td>
                <td>编号</td>
                <td>商品名称</td>
                <td>单位</td>
                <td>数量</td>
                <td>价格</td>
                <td>金额(元)</td>
            </tr>
            <template  v-for="type in jsonArray">
                <tr >
                    <td  style="text-align:left;border: 0px;" colspan="12"><b>&nbsp;&nbsp;商品类别:{{type.pcname}}</b></td>
                </tr>
                <tr v-for="(item, index) in type.list" v-if="index%2==0" >
                    <td>{{getTypeList(type.list, index).product_id}}</td>
                    <td>{{getTypeList(type.list, index).product_name}}</td>
                    <td>{{getTypeList(type.list, index).package_unit}}</td>
                    <td>{{getTypeList(type.list, index).in_num}}</td>
                    <td>{{getTypeList(type.list, index).in_price|currency}}</td>
                    <td>{{getPrice(getTypeList(type.list, index).in_price,
                        getTypeList(type.list, index).in_num)|currency}}</td>
                    <template v-if="testLen(type.list,index)">
                        <td>{{getTypeList(type.list, index+1).product_id}}</td>
                        <td>{{getTypeList(type.list, index+1).product_name}}</td>
                        <td>{{getTypeList(type.list, index+1).package_unit}}</td>
                        <td>{{getTypeList(type.list, index+1).in_num}}</td>
                        <td>{{getTypeList(type.list, index+1).in_price|currency}}</td>
                        <td>{{getPrice(getTypeList(type.list, index+1).in_price,
                                getTypeList(type.list, index+1).in_num)|currency}}</td>
                    </template>
                    <template v-else>
                        <td></td>  <td></td>  <td></td>  <td></td>  <td></td>  <td></td>
                    </template>
                </tr>
                <tr>
                    <td colspan="2" style="border-left: 0px;border-right: 0px;border-left: 0px;">小计</td>
                    <td colspan="3" style="border-left: 0px;border-right: 0px;border-left: 0px;">
                        <strong>数量:{{countNumber(type.list)}}</strong>
                    </td>
                    <td colspan="7" style="border-left: 0px;border-right: 0px;border-left: 0px;">
                        <strong>金额:{{countPrice(type.list)|currency}}</strong>
                    </td>
                </tr>
            </template>
            <tr>
                <td style="border: 0px" colspan="2">总计</td>
                <td style="border: 0px" colspan="4">数量:{{getAllNumber()}}</td>
                <td style="border: 0px" colspan="6">金额:{{getAllPrice()|currency}}</td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="12" style="border: 0px;">&nbsp;</td>
            </tr>
                <tr>
                    <td style="border: 0px;" colspan="4" align="left" >制表人：{{userName}}</td>
                    <td style="border: 0px;" colspan="4" align="left" >制表时间：{{time}}</td>
                </tr>
            </tfoot>
        </table>
        <center>
            <input @click="printPage()" id="buttons" type="button" value="打印" style="width: 60px;height: 30px;text-align: center;-webkit-text-size-adjust: auto"/>
        </center>
    </div>

</body>
<script src="../js/custom/centerWaresDetails.js"></script>
</html>
