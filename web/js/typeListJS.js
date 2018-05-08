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
        jsonData:''
    },
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
            console.log("typeList:"+typeList);
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

                this.$http.post('TList_selectType.action',{selectList:this.selectList}).then(rss=>{
                    this.showTableData(rss.data.json);
                },rsf=>{
                        console.log("fail");
                });
            }

        },
        showTableData:function (data) {
            var obj= JSON.parse(data);
            console.log(obj);
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
