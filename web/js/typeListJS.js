var typeListArr = ["AAA","BBB","CCC","DDD"];
var lis = new Vue({
    el: '#typeList',
    data: {
        selectedData: '',
        typeList : typeListArr
    },
    methods:{
        parseData:function(data){
            alert(this.name);
            alert(data);
        }
    }
})
var data = {'aaa':'aaa'}
alert(typeList.parseData(data))


$(function(){
    $.ajax({
        type: "POST",
        url:  'TList_theData.action',
        dataType: "json",
        data: {},
        success: function(data){
            //alert("success");
            //typeList.parseData(data);
        },
        error:function(e){
            alert('error:'+e);
        }
    });
});
