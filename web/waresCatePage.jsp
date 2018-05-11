<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>类别汇总结账表</title>
    <script src="../js/vue.js"></script>
    <script src="../js/vue-resource.js"></script>
    <script src="../jquery/custom/jquery.min.js"></script>
    <script src="../jquery/custom/jquery.table2excel.min.js"></script>
<style>
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
    <tr><td style="border: 0px" colspan="3"><h1>采购部 类别汇总结账表</h1></td></tr>
<tr>
    <td><b>类别名称</b></td>
    <td><b>数量</b></td>
    <td><b>金额（元）</b></td>
</tr>
    <tr v-for="type in typeList">
        <td>{{type.name}}</td>
        <td></td>
        <td></td>
    </tr>

</table>
</div>
</body>
<script src="js/custom/waresCatePage.js"></script>
</html>
