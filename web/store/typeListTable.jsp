<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="../js/vue.js"> </script>
    <script src="../jquery/custom/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <script src="../js/vue-resource.js"></script>

    <title>中心仓库存类别明细表</title>
    <style>
        .main{
            padding: 0;
            position:relative;
        }
        #left select{
            margin-top: 100px;
        }
        #left .selectClass{
            margin-left: 20px;
        }
        #left{
            float: left;
            width: 330px;
            height:320px;

        }
        #right{
            margin: auto;
            padding: auto;
            text-align: center;
            width: 960px;
            border-left: 1px solid #BDBDBD;
        }
        .remove{
            width: 16px;
            height: 16px;
            margin: 0px;
            padding: 0px;
            text-align: center;
        }
        div.typeList{
         align-content: center;
         text-align: center;
         margin: 0 auto;
        }
        input{
            text-align: center;
            align-content: center;
            margin: 0 auto;
            align-self: center;
            border: 1px;
            color: black;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 12px;
        }
        typeList
    </style>
</head>
<body>
<div id="typeList" class="main">
    <div id="left">
        <select  @change="changeTypeLi();" v-model="typeData">
            <option disabled="disabled" value="">请选择</option>
            <option v-for="type in typeList">{{type.name}}</option>
        </select>
        <select v-model="liData">
            <option disabled="disabled"  value="">请选择</option>
            <option v-bind:value="lis.name" v-for="lis in liDataList">{{lis.name}}</option>
        </select>
        <input type="button" value="添加" @click="addSelectLi();"/>
        <br/>
        <p v-if="selectList.length!=0">需要查询的类别如下</p>
        <div class="selectClass" v-for="x in selectList">
            <span style="width: 160px">{{x}}</span>
            <button style="padding: auto" @click="remove(x)" class="remove">x</button>
        </div>

        <p style="margin-left: 80px"><button @click="selectData()">查询</button></p>
    </div>


<div  v-if="jsonData!=''" class="typeList" id="right">
    <center>
        <h2>中心仓库存类别明细表</h2>
    </center>
    <br>
    <div   id="showTable">
        <table class="inTable"  id="inTable">
            <tr>
                <td>商品类别</td>
                <td>商品名称</td>
                <td>商品属性</td>
                <td>规格</td>
                <td>单位</td>
                <td>库存数量</td>
            </tr>
            <tr v-for="info in jsonData">
                <td>{{info.pcname}}</td>
                <td>{{info.name}}</td>
                <td v-if="info.type==1">
                非生鲜
                </td>
                <td v-if="info.type==0">
                    生鲜
                </td>
                <td>{{info.specifications}}</td>
                <td>{{info.base_unit}}</td>
                <td>{{info.out_amt}}</td>
            </tr>
        </table>
        <br/>
    </div>
    <input @click="printPage()" type="button" value="打印" style="width: 60px;height: 30px;text-align: center;-webkit-text-size-adjust: auto"/>
</div>
</div>
</body>
<script>
    var typeList = [];
    var rsList = [];

    var lis = new Vue({
        el: '#typeList',
        data: {
            typeData: '',
            liData:'',
            typeList : typeList,
            liDataList:[],
            selectList:[],
            jsonData:'',
            usStoreId:window.parent.usStoreId
        },
        //var class_id =window.parent.class_id;
        methods:{
            parseData:function(data){
                var obj= JSON.parse(data);
                for (var index in obj){
                    var detail={};
                    detail.name = obj[index].name;
                    detail.list = [];
                    for(var indx in obj[index].list){
                        var objD = {};
                        objD.id = obj[index].list[indx].id;
                        objD.name = obj[index].list[indx].name
                        detail.list.push(objD);
                    }
                    typeList.push(detail);
                }
                // console.log("typeList:"+typeList);
            },
            changeTypeLi:function () {
                if(this.liDataList.length!=0){
                    this.liDataList = [];
                }
                for(var index in typeList){
                    if(this.typeData==typeList[index].name){
                        for(var li in typeList[index].list){
                            this.liDataList.push(typeList[index].list[li]);
                        }
                    }
                }
            },
            addSelectLi:function () {
                if(this.selectList.indexOf(this.liData)==-1&&this.liData!=""){
                    this.selectList.push(this.liData);
                }
            },
            selectData:function () {
                if(this.selectList.length==0){
                    alert("请首先选择类别！");
                    return;
                }else{
                    this.$http.post('TList_selectType.action',{selectList:this.selectList,usStoreId:this.usStoreId}).then(rss=>{
                        this.showTableData(rss.data.json);
                },rsf=>{
                        console.log("fail");
                    });
                }

            },
            showTableData:function (data) {
                var obj= JSON.parse(data);
                // console.log(obj);
                this.jsonData = obj;
                // var obj= JSON.parse(data);
                // jsonData = obj;
            },
            remove:function(data){
                this.selectList.splice(this.selectList.indexOf(data),1)
            },
            printPage:function(){
                document.body.innerHTML=document.getElementById('showTable').innerHTML;
                window.print();
            }
        },

        mounted:function(){
            this.$http.post('TList_theData.action').then(function(response){
                this.parseData(response.data.json);
            },function(response){
                console.error(response);
            });
        }
    })


</script>

</html>

