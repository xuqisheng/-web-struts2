<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/test_vue.js"> </script>
    <script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
    <title>中心仓库存类别明细表</title>
</head>
<body>
<h2>中心仓库存类别明细表</h2>
<div id="typeTable">
<div id="typeList">
    <select  v-model="selectedData">
        <option value="">请选择</option>
        <option v-for="type in typeList">{{type}}</option>
    </select>
    <span>Selected: {{ selectedData }}</span>
</div>


</div>
</body>
<script src="js/typeListJS.js">
</script>
</html>

