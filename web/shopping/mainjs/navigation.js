//@ sourceURL=navigation.js
/*菜单*/
$(function(){
	$("#mainnav a").attr("href","search.jsp?category=0&projid="+projID);
	$.ajax({
		url:"getCategory.action",
		type:'get',
		data:{},
		dataType:'json', 
		success:function(data){
			var firstlv = data[0];//一级菜单
			var secondlv = data[1];//二级菜单
			var thirdlv = data[2];//三级菜单

			var firstinfo = new Array();
			var secondinfo = new Array();
			var thirdinfo = new Array();
			//一级菜单
			if (firstlv != undefined && firstlv != null) {
				var html = "";
				for (var i = 0; i < firstlv.length; i++) {
					//var html = html+"<div  class='item fore"+(i+1)+"' data-index="+(i+1)+">"
					var html = html+"<div  class='item fore"+(i+1)+"' data-index='"+(i+1)+"'>"
									+"<h3>"
									+"<a href='search.jsp?category="+firstlv[i].categoryid+"&projid="+projID+"'>"+firstlv[i].categoryname+"</a>"
									+"</h3>"
									+"<i>&gt;</i>"
							  +"</div>";
					firstinfo[i] = firstlv[i].categoryid;
					
				}
				$("#categorys-2014 #items .dd-inner").html(html);
			}

			//根据parnet，提取二级数据及结构并保存
			if (secondlv != undefined && secondlv != null) {
				for (var i = 0; i < secondlv.length; i++) {
					var index;
					for (index = 0; index < firstinfo.length; index++) {
						if (secondlv[i].parents == firstinfo[index]) {
							break;
						}
					}
					if (index < firstinfo.length) {
						if (secondinfo[index] == undefined || secondinfo[index] == null) {
							secondinfo[index] = new Array();
						}
						//
						var temp = "<a id='"+secondlv[i].categoryid+"' href= 'search.jsp?category="+secondlv[i].categoryid+"&projid="+projID+"'>"		
                        +secondlv[i].categoryname
						+"<i>&gt;</i></a>";
						secondinfo[index].push(temp);
					}
				}
			}
			
			//根据parents，提取三级数据及结构并保存
			if (thirdlv != undefined && thirdlv != null) {
				for (var i = 0; i < thirdlv.length; i++) {
					var indexfirst = -1;
					var indexsecond;
					for (var j = 0; j < secondinfo.length; j++) {
						if (secondinfo[j] == undefined || secondinfo[j] == null) {
							continue;
						}
						for (var k = 0; k < secondinfo[j].length; k++) {
							if ($(secondinfo[j][k]).attr("id") == thirdlv[i].parents) {
								indexfirst = j;
								indexsecond = k;
								break;
							}
						}
						if (indexfirst != -1) {
							break;
						}
					}
					if (indexfirst != -1) {
						if (thirdinfo[indexfirst] == undefined || thirdinfo[indexfirst] == null) {
							thirdinfo[indexfirst] = new Array();
						}
						if (thirdinfo[indexfirst][indexsecond] == undefined || thirdinfo[indexfirst][indexsecond] == null) {
							thirdinfo[indexfirst][indexsecond] = new Array();
						}

						var tmp = "<a id="+thirdlv[i].categoryid+" href='search.jsp?category="+thirdlv[i].categoryid+"&projid="+projID+"'>"+thirdlv[i].categoryname+"</a>";
						
						thirdinfo[indexfirst][indexsecond].push(tmp);
					
					}
				}
			}

			//根据数据生成二三级菜单，同一分类下同一级按照读入顺序生成
			//二级
			var subdiv = "";
			for (var i = 0; i < secondinfo.length; i++) {
				
				if (secondinfo[i]) {
					//三级
					var menu2 = "";
					var dd ="";
						var dl = "";
					for (var j = 0; j < secondinfo[i].length; j++) {
						if (thirdinfo[i] && thirdinfo[i][j] ) {
							var menu3 = "";
							for (var k = 0; k < thirdinfo[i][j].length; k++) {
								if(thirdinfo[i][j][k]){
									menu3 =menu3 + thirdinfo[i][j][k]; 
								}
							}
							dd = "<dd>"+menu3+"</dd>";
							dl = "<dl class='fore"+(j+1)+"'><dt>"+secondinfo[i][j]+"</dt>"+dd+"</dl>";
						}
						menu2 = menu2+dl;
						
					}
				var subdiv =subdiv+ "<div class='item-sub' id='category-item-"+(i+1)+"'><div class='subitems' style='min-height: 330px;'>"+menu2+"</div></div>";	
				}
			//	var subdiv =subdiv+ "<div class='item-sub' id='category-item-"+(i+1)+"'><div class='subitems' style='min-height: 330px;'>"+menu2+"</div></div>";	
				
			}
			$("#categorys-2014 #items .dorpdown-layer").html(subdiv);
	
		},
		
		error:function() {
			console.log("加载菜单信息数据出错.");
    	}
		
	});


	    function logic() {
        //类别菜单交互实现
        $(".item").live("mouseover",
        function(e) {
            $(this).addClass('hover').siblings().removeClass('hover').end();
            $("#category-item-" + $(this).attr("data-index")).addClass('hover').siblings().removeClass('hover').end();
            e.stopPropagation();
        });
        $(".item-sub").live("mouseover",
        function(e) {
            e.stopPropagation();
        });
        $("#mainnav").live("mouseover",
        function(e) {
            $("#categorys-2014").css("z-index","20");
            $("#items").css("display", "block");
            e.stopPropagation();
        });
        $("#categorys-2014").live("mouseover",
        function(e) {
            e.stopPropagation();
        });
        $("#wrapper").live("mouseover",
        function(e) {
            $(".item").removeClass('hover').end();
            $(".item-sub").removeClass('hover').end();
            $("#items").css("display", "none");
            $("#categorys-2014").css("z-index","1");
            e.stopPropagation();
        });
        $("body").live("mouseover",
        function() {
            $(".item").removeClass('hover').end();
            $(".item-sub").removeClass('hover').end();
            $("#categorys-2014").css("z-index","1");
            $("#items").css("display", "none");
        });

        //调整css
        $("#categorys-2014").css("height", "48px");
        $("#categorys-2014 .dd").css("height", (1 + $("#mainitem").children().length * 31) + "px");
        $("#categorys-2014 .subitems").css("min-height", ($("#mainitem").children().length * 31 - 11) + "px");
    }

	logic();
	
})
