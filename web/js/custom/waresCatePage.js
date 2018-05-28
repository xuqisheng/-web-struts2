var date = new Date().toLocaleString( );
var tbc = new Vue({
    el: '#tableContent',
    data: {
        typeJsonArray: '',
        typeList:'',
        tableDate:date,
        startTime:startTime,
        endTime:endTime,
        storeName:'',
    },
    methods:{
        parseData:function(data){
            this.storeName = data.name;
            var rsData = data.jsonArray;
            var keyRs = {};
            this.typeJsonArray = data.typeJsonArray;
            //数据待解析
            this.typeJsonArray.forEach(item =>{
                var listF = [];
                listF.push(item.name);
                listF.push(0);
                listF.push(0);
                keyRs[item.id] = listF;
                rsData.forEach(rs=>{
                    if(rs.cate===item.id){
                        var number =0;
                        var price = 0;
                        var list =[];
                        rs.list.forEach(li=>{
                            number = number + li.in_num*1;
                            price = li.in_num*1*li.in_price*1 + price;
                        });
                        list.push(item.name);
                        list.push(number);
                        list.push(price);
                        keyRs[item.id] = list;
                    }
                });
            });
            this.typeList = keyRs;
        },
        countNumber:function(){
            var number = 0;
            for(var index in this.typeList){
                number = this.typeList[index][1] +number;
            }
            return number;
        },countPrice:function(){
            var price=0;
            for (var index in this.typeList){
                price = this.typeList[index][2] +price;
            }
            return price;
        },printPage:function(){
            var nal = $("button").remove();
            document.body.innerHTML=document.getElementById('tableContent').innerHTML;
            window.print();
        }
},
    mounted:function(){
        this.startTime = startTime;
        this.endTime = endTime;
        this.class_id = class_id;
        //  this.startTime = "2017-01-01";
        //  this.endTime = "2019-01-01";
        //  this.class_id = "81";
        this.$http.post('WaresCateAction_waresCateData.action',{startTime:this.startTime,endTime:this.endTime,class_id:this.class_id}).
        then(response =>{
            if (response.data.jsonArray!=null){
                this.parseData(response.data);
            }
            },failResponse=>{
            console.error("some error");
        });
    },
    filters: {
        currency: function (value) {
            if (!value) return '';
            return Number(value).toFixed(2);
        },trim:function(str){
            if(!str) return '';
            return str.replace(/(^\s*)|(\s*$)/g, "");
        }
    }
});

$(function() {
    $("#excelButton").click(function () {
        $("#tableContent").table2excel({
            name: $("#tableName").text().replace(/\s+/g,"")+".xls",
            exclude: ".noExl",
            fileext: ".xls",
            filename: $("#tableName").text() + new Date().toISOString().replace(/[\-\:\.]/g, "") + ".xls",
        });
        alert("导出成功！");
    });
});
