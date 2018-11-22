// var conte = ;
var cwd = new Vue({
    el: '#tableContent',
    data: {
        jsonArray:[],
        supplier_name:'',
        startTime:startTime,
        endTime:endTime,
        time: time,
        userName:userName,
        MultiRows:MultiRows,
        collect:flag==0?'(未汇总)':'(汇总)',
        unitSend:'',
        typeArray:''
    },
    methods:{
        parseData:function(data){
            this.jsonArray = data.jsonObject.jsonArray;
            this.typeArray = data.jsonObject.typeArray;
        },getTypeList:function(typeList,index){
            return typeList[index];
        },getPrice:function(a,b){
            return a*b;
        },testLen:function(list,index){
            if(list.length>index+1)
                return true;
            else
                return false;
        },countNumber:function(list) {
            count = 0;
            list.forEach(it=>{
                count +=it.out_num*1;
            });
            return count;
        }, countPrice:function(list){
            var price = 0;
            list.forEach(it=>{
                price += it.in_num*1*it.in_price*1;
                if(this.supplier_name==""){
                    this.supplier_name = it.name;
                }
            });
            return price;
        },getAllNumber:function(){
            var number = 0;
            this.jsonArray.forEach(its=>{
                its.list.forEach(it=>{
                    number +=it.in_num*1;
                });
            });
            return number;
        },getAllPrice:function(){
            var price = 0;
            this.jsonArray.forEach(its=>{
                its.list.forEach(it=>{
                    price +=it.in_num*1*it.in_price*1;
                });
            });
            return price;
        }, printPage:function(){
            document.getElementById("buttons").remove();
            document.body.innerHTML=document.getElementById('tableContent').innerHTML;
            window.print();
        }
    },
    mounted:function(){
        var toUrl = "";
        // flag = 0;
        if (flag == 0){
            toUrl = "CenterWaresDetails_outNoCollect.action";
        }else {
            toUrl = "CenterWaresDetails_outCollect.action"
        }
        // MultiRows ="O20181113D6T2;O20181113D5T1;O20181113D5T2;O20181113D5T3;O20181113D2T1;O20181113D11T1;O20181113D11T2;O20180911D1T1";
            this.$http.post(toUrl, {MultiRows:MultiRows}).
        then(response =>{
            if (response.data.jsonObject!=null){
                this.parseData(response.data);
            }
        },failResponse=>{
            console.error("some error");
        });
    },filters: {
        currency: function (value) {
            if (!value) return '';
            return Number(value).toFixed(2);
        }
    }
});