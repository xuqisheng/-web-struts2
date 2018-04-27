<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/test_vue.js"></script>
    <title>库存类别明细表</title>
</head>
<body>
<h2>Hello Worlds!</h2>
<div id ="apps">
    {{ message }}
</div>
<div id="app-5">
    <p>{{ message }}</p>
    <button v-on:click="reverseMessage">逆转消息</button>
</div>

<script src="js/typeListJS.js">
</script>
</html>
