
var apply_no = window.parent.apply_no;
var datas = new Vue({
    el: '#tableContent',
    data: {
        checkList: {},
        orderDetailsF:[],
        orderInvoice: [],
        orderArray: [],
        apply_no: '',
        uploadedVoiceMount:0
    },
    mounted:function(){
        this.$http.post('printOrder_mainContent.action',{apply_no:this.apply_no}).
        then(response=>{
            this.parseData(response.data);
        },failRes=>{
            console.error("connection Error")
        });
    },

    methods:{
        uploadedVoiceMounts:function(orderInvoice){
            var count = 0;
            orderInvoice.forEach(item =>{
                if (item.isapp=="Y"){
                    count = count + item.invoice_amt*1;
                }
                item.invoice_doc = "fileSystem_getImgStreamAction.action?seq="+this.imgUrl(item.invoice_doc);
            });
            return count;
        },
        parseData:function(data){
            this.checkList = data.checkListF;
            this.orderDetailsF = data.orderDetailsF;
            this.uploadedVoiceMount = this.uploadedVoiceMounts(data.orerInvoice);
            this.parseOrderArray(this.orderDetailsF,data.orderArray);//处理订单明细
            this.orderInvoice = data.orerInvoice;
        },
        parseOrderArray:function (orderDetailsF,orderArray) {
            orderArray.forEach(ite => {
                orderDetailsF.forEach(item=>{
                    if(typeof (ite[item.ordercode]) !="undefined"){
                        item.detailList = ite[item.ordercode];
                    }
                });
            });
        },imgUrl:function(urls){
            return urls.substr(0,urls.indexOf("&"));
        },printPage:function(){
            var nal = $("button").remove();
            document.body.innerHTML=document.getElementById('tableContent').innerHTML;
            window.print();
        }
    },
    filters: {
        currency: function (value) {
            if (!value) return '';
            return Number(value).toFixed(2);
        },booleanStr:function(boo){
            if(boo=='T'){return "通过";}
            else{return "未通过";}
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