var datas = new Vue({
    el: '#tableContent',
    data: {
        checkList: {},
        orderDetailsF:[],
        orderInvoice: [],
        orderArray: [],
        apply_no: '201',
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
            });
            return count;
        },
        parseData:function(data){
            this.checkList = data.checkListF;
            this.orderDetailsF = data.orderDetailsF;
            this.uploadedVoiceMount = this.uploadedVoiceMounts(data.orerInvoice);
            this.parseOrderArray(this.orderDetailsF,data.orderArray);//处理订单明细
            // console.log(this.orderDetailsF);
        },
        parseOrderArray:function (orderDetails,orderArray) {
            orderDetails.forEach(item=>{
/////////
                orderArray.forEach(detail=>{
                    var details = detail[item.ordercode];
                    if(typeof (details)=="undefined"){
                        item.detailList = {};
                        item.detailList.detailsList=[];
                        item.detailList.commentsList=[];
                    }else {
                        item.detailList = details;
                    }
                });

            });
        }

    }
});