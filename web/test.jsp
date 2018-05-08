<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>jQuery Boilerplate</title>
    <script src="/jquery/custom/jquery.min.js"></script>
    <script src="/jquery/custom/jquery.table2excel.min.js"></script>
</head>
<body>
<table class="table2excel" data-tableName="Test Table 1">
    <tr>
        <td>品名</td>
        <td colspan='4'>
            <input ng-model="Cname" name="Cname" value="圆白菜粉条" class="ng-pristine ng-untouched ng-valid ng-not-empty">
        </td>
    </tr>
    <tr>
        <td rowspan='6'>主辅料</td>
        <td>原料名称</td>
        <td>数量</td>
        <td>单价</td>
        <td>金额</td>
    </tr>
    <tr>
        <td>
            <input ng-model="x.costName" name="costName" value="圆白菜"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty">
        </td>
        <td><input ng-model="x.costNumber" type="number" name="costNumber" value="1"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td><input ng-model="x.unitPrice" type="number" name="costUnitPrice" value="0.8"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td>
            0.80
        </td>
    </tr>
    <tr>
        <td>
            <input ng-model="x.costName" name="costName" value="粉条"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty">
        </td>
        <td><input ng-model="x.costNumber" type="number" name="costNumber" value="3"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td><input ng-model="x.unitPrice" type="number" name="costUnitPrice" value="3"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td>
            9.00
        </td>
    </tr>
    <tr>
        <td>
            <input ng-model="x.costName" name="costName" value="细粉条"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty">
        </td>
        <td><input ng-model="x.costNumber" type="number" name="costNumber" value="2"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td><input ng-model="x.unitPrice" type="number" name="costUnitPrice" value="2.5"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td>
            5.00
        </td>
    </tr>
    <tr>
        <td>
            <input ng-model="x.costName" name="costName" value="分分分"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty">
        </td>
        <td><input ng-model="x.costNumber" type="number" name="costNumber" value="65"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td><input ng-model="x.unitPrice" type="number" name="costUnitPrice" value="23.2"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td>
            1,508.00
        </td>
    </tr>
    <tr>
        <td>
            <input ng-model="x.costName" name="costName" value="" class="ng-pristine ng-untouched ng-valid ng-empty">
        </td>
        <td><input ng-model="x.costNumber" type="number" name="costNumber" value="NaN"
                   class="ng-pristine ng-untouched ng-valid"></td>
        <td><input ng-model="x.unitPrice" type="number" name="costUnitPrice" value="NaN"
                   class="ng-pristine ng-untouched ng-valid"></td>
        <td>

        </td>
    </tr>
    <tr>
        <td rowspan='2'>其他</td>
        <td>油</td>
        <td><input name="costNumber6" ng-model="costNumber6" type="number" value="1"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td><input name="unitPrice6" ng-model="unitPrice6" type="number" value="3.8"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td>
            3.80
        </td>
    </tr>
    <tr>
        <td>调料</td>
        <td colspan='2'></td>
        <td>
            <input name="price7" type="number" ng-model="price7" value="3"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty">
        </td>
    </tr>
    <tr>
        <td>原材料合计</td>
        <td colspan='3'></td>
        <td>1,529.60</td>
    </tr>
    <tr>
        <td>水电气</td>
        <td colspan='3'>总成本*10%</td>
        <td>254.90</td>
    </tr>
    <tr>
        <td>直接成本</td>
        <td colspan='3'></td>
        <td>1,784.50</td>
    </tr>
    <tr>
        <td>间接成本</td>
        <td colspan='3'></td>
        <td>764.80</td>
    </tr>
    <tr>
        <td>总成本</td>
        <td colspan='3'></td>
        <td> 2,549.30</td>
    </tr>
    <tr>
        <td rowspan='2'>回收</td>
        <td>份数<br>元
        </td>
        <td>单价<br>元
        </td>
        <td>合计</td>
        <td>盈亏<br>元
        </td>
    </tr>
    <tr>
        <td><input ng-model="re_number" type="number" name="re_number" value="20"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td><input ng-model="re_price" type="number" name="re_unitPrice" value="2"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
        <td>40.00</td>
        <td>-2,509.30</td>
    </tr>
    <tr>
        <td colspan='2'>毛利率</td>
        <td colspan='2'></td>
        <td><input name="interest_rate" ng-model="interest_rate" value="35%"
                   class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
    </tr>
    <tr>
        <td colspan='2'>成品重量（斤）/份</td>
        <td colspan='3'><input ng-model="height" type="number" name="height" value="0.6"
                               class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
    </tr>
    <tr>
        <td colspan='2'>成品口味</td>
        <td colspan='3'><input ng-model="taste" type="text" name="taste" value="糖醋"
                               class="ng-pristine ng-untouched ng-valid ng-not-empty"></td>
    </tr>
</table>

<script>


    $(function () {
        $(".table2excel").table2excel({
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "myFileName" + new Date().toISOString().replace(/[\-\:\.]/g, ""),
            fileext: ".xls",
            exclude_img: true,
            exclude_links: true,
            exclude_inputs: true
        });
    });
</script>


</body>
</html>