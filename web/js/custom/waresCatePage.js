var tbc = new Vue({
    el: '#tableContent',
    data: {
        typeJsonArray: '',
        typeList:'',
    },
    methods:{
        parseData:function(data){
            console.log(data.jsonArray);
            var rsData = data.jsonArray;
            this.typeJsonArray = data.typeJsonArray;
            //数据待解析
            // this.typeJsonArray.forEach(item =>{
            //     console.log(item.name);
            //     rsData.forEach(rs=>{
            //         if(rs.cate==item.id){
            //             rs.list.forEach(){
            //
            //             }
            //         }
            //     });
            // });
        },

    },
    mounted:function(){
        this.$http.post('WaresCateAction_waresCateData.action').then(response =>{
            this.parseData(response.data);
            },failResponse=>{
            console.error("some error");
        });
    }
})