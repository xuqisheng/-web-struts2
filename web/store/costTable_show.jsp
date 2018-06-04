<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<script src="/jquery/angular.min.js"></script>
	<script src="/jquery/custom/jquery.min.js"></script>
	<script src="/jquery/custom/jquery.table2excel.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>北工大成本核算表</title>
<style>
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
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
input[type="number"] {
	-moz-appearance: textfield;
}

input {
	font-size:14px;
	text-align:center;
	width: 100%;
	height: 100%;
	border-style: none;
}

table.costTable {
	width:794px;
	margin-left: auto;
	margin-right: auto;
	align-content: center;
	border-width: 1px;
	border-style: solid;
	border-color: #666666;
	border-collapse: collapse;
}

table.costTable tr {
	border-width: 1px;
	border-style: solid;
	border-color: #666666;
	margin: 0px;
	border-style: solid;
}

table.costTable td {
	text-align: center;
	border-width: 1px;
	margin: 0px;
	height: 26px;
	border-style: solid;
	border-color: #666666;
}

.noExl{
	display:none;
}
</style>

<script>
    var class_id =window.parent.class_id;
    var tableID = window.parent.tableID;
var app = angular.module('costTable', []);
app.filter('customCurrency', ["$filter", function ($filter) {
    return function(amount, customCurrencySymbol){
        var customCurrency = $filter('currency');
        if(amount == 0){
            return "";
        }
        return customCurrency(amount, customCurrencySymbol);
    };
}]);
app.controller('costTableControl', function($scope,$http) {
	$scope.printPage=(function(){
		document.body.innerHTML=document.getElementById('print').innerHTML; 
		window.print();
	});
	$scope.typeList=
		[{costName:'',costNumber:NaN,unitPrice:NaN,others:0,order:-1},
		{costName:'',costNumber:NaN,unitPrice:NaN,others:0,order:-1},
		{costName:'',costNumber:NaN,unitPrice:NaN,others:0,order:-1},
		{costName:'',costNumber:NaN,unitPrice:NaN,others:0,order:-1},
		{costName:'',costNumber:NaN,unitPrice:NaN,others:0,order:-1}
		];
	$scope.countPrice=(function(){
		var list =$scope.typeList;
		var number =0;
		angular.forEach(list,function(data,index,list){
			number = number+checkZero(data.costNumber*data.unitPrice);
		});
		number = number + checkZero($scope.costNumber6*$scope.unitPrice6);
		number = number + checkZero($scope.price7);
		return number;
	});
	$scope.utilities=(function(){//水电气
		return ($scope.totalCost()*0.1).toFixed(1)*1;
	});
	$scope.totalCost= (function(){
		return ( $scope.countPrice() / 0.6 ).toFixed(1)*1;//小数
	});
	$scope.direct_cost=(function(){//直接成本
		return ($scope.countPrice()+$scope.utilities()).toFixed(1)*1;
	});
	$scope.joint_cost=(function(){//间接成本
		return ($scope.totalCost()-$scope.direct_cost()).toFixed(1)*1;
	});
	$scope.re_total = (function(){
		return ($scope.re_number*$scope.re_price).toFixed(1)*1;
	});
	$scope.profit = (function(){
		return ($scope.re_total()-$scope.totalCost()).toFixed(1)*1;
	});
	$scope.interest_rate='30%';
	$scope.parseJsonObj=(function(data){
		var data=angular.fromJson(data);
		$scope.class_id = data.class_id;
		$scope.tableID = data.tableID;
		$scope.Cname = data.name;
		$scope.height= data.height*1;
		$scope.taste = data.taste;
		$scope.re_number = data.re_number*1;
		$scope.re_price =data.re_price*1.0;
		$scope.typeList = $scope.getMainOther(data.typeList);
		$scope.interest_rate = data.interest_rate*1*100+'%';
	});
	
	$scope.getMainOther = (function(list){
		var result_list = [];
		angular.forEach(list,function(data,index,list){
			if(angular.equals('主辅料',data.type)){
				result_list.push({costName:data.name,costNumber:data.num*1,unitPrice:data.price*1,others:0,order:data.order});
			}else if (angular.equals('其他',data.type)){
				if(angular.equals('油',data.name)){
					$scope.costNumber6=data.num*1;
					$scope.unitPrice6=data.price*1;
					$scope.oil_order = data.order;
				}
				if(angular.equals('调料',data.name)){
					$scope.price7 = data.totalprice*1;
					$scope.o_order = data.order;
				}
			}
		});
		while (result_list.length<5){
			result_list.push({costName:'',costNumber:NaN,unitPrice:NaN,other:0,order:'-1'})
		}
		return result_list;
	});
	
	$scope.$watch('$viewContentLoaded', function() {  
		$scope.getData();
	}); 
	//查询
	$scope.getData = (function(){
		// var tableID ='1';//参数名
		// var class_id = '13';//参数名
		$http({
		    method: 'post',
		    url: 'costTable_fromDBData.action',
		    params:{
		    	"tableID":tableID
				}
		}).then(function successCallback(response) {
			$scope.parseJsonObj(response.data.json);
		}, function errorCallback(response) {
				alert("fails:错误信息"+response);
		});
	});
	//修改
	$scope.update= (function(){
		var tableID = $scope.tableID;
		var class_id = $scope.class_id;
		var list = $scope.typeList;
		$http({
		    method: 'post',
		    url: 'costTable_update.action',
		    data:{
				"oil_order":$scope.oil_order,
				"o_order":$scope.o_order,
		    	"tableID":tableID,
		    	"class_id":class_id,
		    	"name":$scope.Cname,
		    	"countPrice":$scope.countPrice(),
				"totalCost":$scope.totalCost(),
				"utilities":$scope.utilities(),
				"direct_cost":$scope.direct_cost(),
				"joint_cost":$scope.joint_cost(),
				"re_total":$scope.re_total(),
				"profit":$scope.profit(),
				"height":$scope.height,
				"taste":$scope.taste,
				"re_number":$scope.re_number,
				"re_price":$scope.re_price,
				"interest_rate":$scope.interest_rate,
				"typeList":$scope.typeList,
				"oil_price":$scope.unitPrice6.toFixed(1)*1,
				"oil_numb":$scope.costNumber6.toFixed(1)*1,
				"other":$scope.price7*1
				}
		}).then(function successCallback(response) {
				alert("操作成功");
		}, function errorCallback(response) {
				alert("操作失败");
		});
	});	
	
	$scope.confirm = (function(){
		var con;
		con=confirm("确定修改？"); //在页面上弹出对话框
		if(con==true)
			$scope.update();
		else return;
	});
});
$(function() {
    $("#excelButton").click(function () {
        var nal = $("input[value='NaN']").remove();
        console.log(nal);

        $("#printContent").table2excel({
            name: $("#tableName").text() + ".xls",
            exclude: ".noExl",
            fileext: ".xls",
            filename: $("#tableName").text() + new Date().toISOString().replace(/[\-\:\.]/g, "") + ".xls",
        });
        alert("导出成功！");
    });
});

function checkZero(obj){
	if (angular.equals(obj,NaN)||!angular.isDefined(obj)){
		return 0;
	}
	else {
		return obj;
	}
}

</script>
</head>
<body ng-app="costTable" ng-controller="costTableControl">
<div id="print">
	<h1 id="tableName" align="center">北工大成本核算表</h1>
	<table id="printContent" class="costTable" title="costTable" >
		<tr align="center" class="text">
			<td width="30%" align="center" class="title">品名</td>
			<td width="70%" align="center" class="title" colspan="4">
				<input  ng-model="Cname" name="Cname" value="{{Cname}}"/>
			</td>
			<td class="noExl">
				<input  name="class_id" ng-model="class_id" class="ng-hide" value ="{{class_id}}">
				<input  name="tableID" ng-model="tableID"  class="ng-hide" value = "{{tableID}}">
			</td>
		</tr>
		
		<tr align="center" class="text">
			<td rowspan="6" align="center">主辅料</td>
			<td>原料名称</td>
			<td>数量</td>
			<td>单价</td>
			<td>金额</td>
		</tr>
		<tr ng-repeat="x in typeList" >
			<td>
				<input  ng-model="x.costName" name="costName" value = "{{x.costName}}"/>
			</td>
			<td><input  ng-model="x.costNumber" type="number" name="costNumber" value= "{{x.costNumber}}"/></td>
			<td><input  ng-model="x.unitPrice" type="number" name="costUnitPrice" value="{{x.unitPrice}}"/></td>
			<td width="20%">
				{{x.costNumber*x.unitPrice|customCurrency:"":1}}
			</td>
			<td class="noExl">
				<input  name="order" class="ng-hide"  ng-model="x.order"  value = "{{x.order}}"/>
				<input  name="others" class="ng-hide"  ng-init="x.others=0" ng-model="x.others"  value = "{{x.others}}"/>
			</td>
		</tr>
		
		<tr>

			<td rowspan="2">其他</td>
			<td>油</td>
			<td><input  name="costNumber6" ng-model="costNumber6" type="number" name="costNumber" value= "{{costNumber6}}"/></td>
			<td><input  name="unitPrice6" ng-model="unitPrice6" type="number" name="costUnitPrice" value="{{unitPrice6}}"/></td>
			<td>
				{{costNumber6*unitPrice6|customCurrency:"":1}}
			</td>
			<td class="noExl">
				<input  name="oil_order" ng-model="oil_order" class="ng-hide" value="{{oil_order}}"/>
			</td>
		</tr>
		<tr>
			<td>调料</td>
			<td colspan="2"></td>
			<td>
				<input  name="price7" type="number" name="price7" ng-model="price7" value="{{price7}}"/>
			</td>
			<td class="noExl">
				<input  name="o_order" ng-model="o_order" class="ng-hide" value="{{o_order}}"/>
			</td>
		</tr>
		<tr>
			<td>原材料合计</td>
			<td colspan="3"></td>
			<td>{{countPrice()|customCurrency:"":1}}</td>
		</tr>
		<tr>
			<td>水电气</td>
			<td colspan="3">总成本*10%</td>
			<td>{{utilities()|customCurrency:"":1}}</td>
		</tr>
		<tr>
			<td>直接成本</td>
			<td colspan="3"></td>
			<td>{{direct_cost()|customCurrency:"":1}}</td>
		</tr>
		<tr>
			<td>间接成本</td>
			<td colspan="3"></td>
			<td>{{joint_cost()|customCurrency:"":1}}</td>
		</tr>
		<tr>
			<td>总成本</td>
			<td colspan="3"></td>
			<td> {{totalCost()|customCurrency:"":1}}</td>
		</tr>
		<tr>
			<td rowspan="2">回收</td>
			<td>份数<br />元
			</td>
			<td>单价<br />元
			</td>
			<td>合计</td>
			<td>盈亏<br />元
			</td>
		</tr>
		<tr>
			<td><input  ng-model="re_number" type="number" name="re_number" value="{{re_number}}"/></td>
			<td><input  ng-model="re_price" type="number" name="re_unitPrice" value="{{re_price}}" /></td>
			<td>{{re_number*re_price|customCurrency:"":1}}</td>
			<td>{{profit()|customCurrency:"":1}}</td>
		</tr>
		
		<tr>
			<td colspan="2">毛利率</td>
			<td colspan="2"></td>
			<td><input  name="interest_rate" ng-model="interest_rate" value="{{interest_rate}}"/></td>
		</tr>
		<tr>
			<td colspan="2">成品重量（斤）/份</td>
			<td colspan="3"><input  ng-model="height" type="number" name="height" value="{{height}}"/></td>
		</tr>
		<tr>
			<td colspan="2">成品口味</td>
			<td colspan="3"><input  ng-model="taste" type="text" name="taste" value="{{taste}}"/></td>
		</tr>
	</table>
	</div>
	<div align="center">
	<br/>
	<button type="button"   ng-click="confirm()" >修改</button>
	<button type="button"   ng-click="printPage()" >打印界面</button>
	<button id="excelButton" type="button" >导出Excel</button>
	</div>
</body>
</html>