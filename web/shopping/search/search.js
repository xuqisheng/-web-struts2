//@ sourceURL=search.js
// 初始化
$(function() {
/*系统参数定义表*/
	var projID ="SHOP";
	var itemid = getQueryString("itemid");
	function getRealPath(){  
		var curWwwPath=window.document.location.href; 
		//获取主机地址之后的目录，如： myproj/view/my.jsp   
		var pathName=window.document.location.pathname;  
		var pos=curWwwPath.indexOf(pathName);  
		//获取主机地址，如： http://localhost:8083   
		var localhostPaht=curWwwPath.substring(0,pos);  
		//获取带"/"的项目名，如：/myproj 
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);   
		//得到了 http://localhost:8083/myproj  
		var realPath=localhostPaht+projectName;  
		return  realPath;
		} 
	/**获取项目路径**/
	var syspath = getRealPath();

	/**获取地址参数**/
	function GetUrlString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 

	function repstr(str){
	if(str==''||str==null){
		return '&nbsp;';
	}else{
		return str;
		}
	}
	
	/**获取url上的搜索关键字等信息**/
	var out = "";
	var cate = getQueryString("category");
	var keyword = decodeURI(decodeURI(getQueryString("keyword")));
	var parameter = getQueryString("parameter");
	var parameter_url = getQueryString("parameter");
	var value = decodeURI(decodeURI(getQueryString("value")));
	var value_url = decodeURI(decodeURI(getQueryString("value")));
	var page = GetUrlString('page');
	var collection = getQueryString("collection");
	var baseurl = "search.jsp"+window.location.search;

	if (parameter!=null&&parameter!=undefined) {
		parameter = parameter.split(",");
	}
	if (value!=null&&value!=undefined) {
		value = value.split(",");
	}


/** 获取搜索条件信息  **/
	$.ajax({
		url:"getCondition.action",
		type:'get',
		data:{category:cate,
			 keyword:keyword,
			value:value},
		dataType:'json',
		success:function(data){
			// alert(data);
			if (data!=null&&data!=undefined){
				for (var i=0;i<data.length;i++){
					var span = "<span>"+data[i].display+"</span>";
					
					var a = "";
					for (var j=0;j<data[i].options.length;j++) {
						var flag = false;
						if (parameter!=null&&parameter!=undefined) {
							for (var k=0;k<parameter.length;k++) {
								if (data[i].id==parameter[k]&&data[i].options[j]==value[k]) {
									flag = true;
									break;
								}
							}
						}
						
						if (flag) {
							a = a+'<a class="parameter" style="font-weight:800" parameter='+data[i].id+'>'+data[i].options[j]+'</a>';
						} else {
							a = a+ '<a class="parameter" style = "" parameter='+data[i].id+'>'+data[i].options[j]+'</a>';
						}
						
					}
					var li = "<li style='display:block;'>"+a+"</li>";
					var div = 
						'<div class="J_selectorLine">'
							+'<div class="sl-wrap">'
							+	'<div class="sl-key">'
							+		span
							+	'</div>'
							+	'<div class="sl-value">'
							+		'<div class="sl-v-list">'
							+			'<ul class="J_valueList">'
							+				li
							+			'</ul>'
							+		'</div>'
							+	'</div>'
							+'</div>'
						+'</div>';
					
					$("#J_selector").append(div);
				}
			}
		},
		error:function(){
			console.log("加载搜索条件信息出错.");
		}
	});
	search();
	
/**查询的搜索信息总体显示**/
	var value_search ="";
	var key_search = keyword;
	if (keyword==null||keyword == "null"){
		key_search = "全部结果";
	}
	if (value==null||value == "null"){
		value_search = "全部结果";
	}
	
	
	//获取的 点击搜索条件
	var i_del = "<i class='crumbs-first-del'>x</i>";
	var temp = "";
	if(value_search != "全部结果"){
		
		if(value.length>1){
			//包含“，”
			for(var i = 0; i<value.length;i++){
				var span = "<span class='crumbs-first-span'>"+value[i]+i_del+"</span>";
				temp += span;
			}
			$("#J_crumbsBar .crumbs-first").append(temp);
		}else{
			var span = "<span class='crumbs-first-span'>"+value[0]+i_del+"</span>";
			temp += span;
			$("#J_crumbsBar .crumbs-first").append(temp);
		
		}
	
	}else{
			
	}
	//获取的url上的keyword搜索信息
	if(keyword!=null&&keyword != "null"){
		$("#J_crumbsBar .crumbs-first").prepend("<span class='crumbs-first-span crumbs-keyword-span'>"+key_search+i_del+"</span>");
	}

	

/** 获取搜索的各个内容项 **/

	$.ajax({
		url:"getItem.action",
		type:"get",
		data:{
			"category": cate,
			"parameter": parameter_url,
			"keyword": keyword,
			"value": value_url,
			"page":page,
			"collection":collection	
		},
		dataType:'json',
		success:function(data){
			if (data!=null&&data!=undefined) {
				var tmp = "";
				for (var i=0;i<data.length;i++) {
					//商品图片
					var itemImgUpload = data[i].itemimgupload;
					var itemcate = data[i].itemcate;//找分类号最后一个数
					var num_split = itemcate.split(".")
					var final_num = num_split[num_split.length-2]
					if (itemImgUpload) {
						var seq = itemImgUpload.split(/&/)[0]; // itemImgUplad的格式形如: seq&xxx.jpg&.jpg
						var img1="<img width='220px' height='220px' src='fileSystem_getImgStreamAction.action?seq="+seq+"&projid="+projID+"' onerror=\"this.src='img/default.jpg'\"></img>"; //projID是index.jsp加载时设置的全局js变量 
					}
					else {
						var img1="<img width='220px' height='220px' src='img/default.jpg'></img>"; 
					}

					/**危化品标识**/
					/*if(a1.children.length>1){
						a1.removeChild(img2);
					}*/
					var img2 = "";
					var itemcate = data[i].itemcate;//找分类号最后一个数
					var num_split = itemcate.split(".")
					var final_num = num_split[num_split.length-2]
					if (final_num == 221){//如果最后的数为危化品，寻找他在shp_category中imgroot的图片路径
						//if (data[i].imgroot){//如果为真，就把图片标识加在商品图片上
						//	img2.setAttribute("src",data[i].imgroot)
						//}
						img2 = "<img src='img/toxic.jpg'></img>";					
					}else if (final_num == 222){//易爆
						img2 = "<img src='img/yzb.jpg'></img>";						
					}else if (final_num == 223){//剧毒
						img2 = "<img src='img/judu.jpg'></img>";
					}
					
					//商品图片超链接到详情页面
					var parents = data[i].parents;
					if (data[i].parents==undefined||data[i].parents==null||data[i].parents=="null") {
						parents = data[i].itemid;
					}
					var a1 = "<a href='item.jsp?itemid="+parents+"&subid="+data[i].itemid + "&projid="+projID+"'>"+img1+img2+"</a>";
					
					//价格
					var price =  (data[i].prize-0).toFixed(2);
					
					//商品名
					itemName = data[i].itemname;

					//点击商品名称进入详情页面
					var a2 = "<a href='item.jsp?itemid="+parents+"&subid="+data[i].itemid+ "&projid="+projID+"'><em>"+itemName+"</em></a>";
					
					/* 购物车 */
					//添加购物车前，先验证是否登录
					//a3.setAttribute("href","addCart.action?subid="+data[i].itemid+"&num=1");
					//a3.setAttribute("onclick","addcard(\""+data[i].itemid+"\",1,this)");
					
					
					//评论
					var commentNum = "已有"+data[i].comment+"人评价";
					//strong3.innerHTML = "    <div id='supplier' style='float: right'><a   href='#'>"+data[i].itempara["0"]+"</a>
					// <a onclick='supplier(\""+data[i].itempara["15"]+"\")'><img width='16' height='16' src='./img/qq.png'/></a></div>";
					
					
					//供货商
					ageny = data[i].itempara["0"];
					//供货商qq链接
					var a_qq = "<a onclick='supplier(\""+data[i].itempara["15"]+"\")'><img width='16' height='16' src='./img/qq.png'/></a>";
					//var cart = '<a class="p-o-btn addcart"  onClick="addcard(\""+data[i].itemid+"\",1)"><i></i>加入购物车</a>';
					//var collection = '<a class="p-o-btn  addcart"  onClick="addColletion(\""+data[i].itemid+"\",1)" style="background-position: 0 -414px;"><i style="background-position: 0 -414px;"></i>添加收藏</a>';
					var cart = '<a class="p-o-btn addcart"  onClick="addcard('+data[i].itemid+',1)"><i></i>加入购物车</a>';
					var col = '<a class="p-o-btn  addcart"  onClick="addColletion('+data[i].itemid+',1);return false;"  style="background-position: 0 -414px;">' +
						'<i style="background-position: 0 -414px;"></i>添加收藏</a>';
					/****/
					var li = 
					'<li class="gl-item">'
					+	'<div class="gl-i-wrap">'
					+		'<div class="p-img">'
					+			a1
					+		'</div>'
					+		'<div class="p-price">'
					+			'<strong><em>￥</em><i>'+price+'</i></strong>'
					+		'</div>'
					+		'<div class="p-name p-name-type-2">'
					+			a2
					+		'</div>'
					+		'<div style="float:right" id="sin">'
					+			'<strong style="color:blue;">'+ageny+'</strong>'
					+			a_qq
					+		'</div>'
					+		'<div class="p-commit">'
					+			'<strong>'+commentNum+'</strong>'
					+		'</div>'
					+		'<div class="p-operate">'
					+			col
					+			cart
					+		'<div style="display:none;" id="addsuccess_"+data[i].itemid></div>'
					+		'</div>'
					+	'</div>'
					+'</li>';
					tmp += li;
				}
				$("#J_goodsList ul").append(tmp);
			}
		},
		error:function(){
			console.log("加载搜索结果信息出错");
		}
	});

	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	}

	function iFrameHeight(idx) {   
		var frameName = idx?("infoframe"+idx):"infoframe";
		var ifm = document.getElementById(frameName);
		var subWeb = document.frames ? document.frames[frameName].document : ifm.contentDocument;   
		if(ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight
		}   
	}   
	//点击搜索条件

	function search(){
		var value = decodeURI(decodeURI(getQueryString("value")));
		if (value!=null&&value!=undefined) {
		value = value.split(",");
		}
		$(".parameter").live("click",function(e) {
				var flag = -1;
				var ps = "";
				var vs = "";
				var url = "search.jsp?category="+cate+"&keyword="+keyword+"&projid="+projID;
				if (parameter!=null&&parameter!=undefined) {
					for (var k=0;k<parameter.length;k++) {
						if (this.getAttribute("parameter")==parameter[k]&&this.innerHTML==value[k]) {
							flag = k;
							break;
						}
					}
					for (var k=0;k<parameter.length;k++) {
						if (k==flag) {
							continue;
						}
						ps+=parameter[k]+",";
						vs+=value[k]+",";
					}
					
				}
				
				if (flag==-1) {
					ps+=this.getAttribute("parameter");
					vs+=this.innerHTML;
				} else {
					ps = ps.substring(0, ps.length-1);
					vs = vs.substring(0, vs.length-1);
				}
				
				if (ps!=""&&vs!="") {
					url+="&parameter="+ps+"&value="+vs;
				}
				window.location.href = encodeURI(encodeURI(url));
			});
			


			/*白雪 2017-8-2 增加功能：点击头部筛选条件，删除筛选条件*/
			$(".crumbs-first-span").live("click",function(e){
				//查看当前点击删除的对象，是否为搜索关键字条件
				var this_class =$(this).attr("class");
				var del_key = false;
				if(this_class.indexOf("crumbs-keyword-span")>0){
					del_key = true;;
				}
				
				//
				var flag = -1;
				var ps = "";
				var vs = "";
				var url = "search.jsp?category="+cate+"&keyword="+keyword+"&projid="+projID;
				var del =$(this).html();
				var del = del.substring(0,del.indexOf("<"));
				if(del_key == false){
					
					if(value!=null && value!=undefined){
						//记录下要删除的项
						for(var k = 0;k<value.length;k++){
							if(del == value[k]){
								flag = k;
								break;
							}
						}
						//除要删除项外的其他条件存入
						for(var k = 0;k<value.length;k++){
							if(flag == k){
								continue;
							}
							ps+=parameter[k]+",";
							vs+=value[k]+",";
						}
						ps = ps.substring(0, ps.length-1);//去掉最后一个“，”
						vs = vs.substring(0, vs.length-1);
					}
					//存入url
					if (ps!=""&&vs!="") {
						url+="&parameter="+ps+"&value="+vs;
					}
					window.location.href = encodeURI(encodeURI(url));
				}else{
					//点击删除搜索关键字
					url = "search.jsp?category="+cate+"&projid="+projID;
					if (parameter_url!=""&&value_url!="") {
						url+="&parameter="+parameter_url+"&value="+value_url;
					}
					window.location.href = encodeURI(encodeURI(url));
				}
				
			});
			/*  结束  */
	};
			

	//验证用户登录信息
	function checklogin(type){
		if(type=='1'){
			 $.ajax({  
			  type: "POST",
			  url: 'SHOP_IsLogin.action',
			  dataType: "json",
			  success: function( data ){
				if(data.json!='ok'){
					alert('请先登录');
				}else{
					window.open('cart/cart.jsp?projid='+projID);
					}
			  }
			 });
			// href="cart.jsp?projid='+projID+'"
		}
	}

	//加入购物车
	addcard = function(subid,num){
		$.ajax({  
			  type: "POST",
			  url: 'addCart.action',
			  data:{"subid":subid,"num":num},
			  dataType: "json",
			  success: function( data ){
				
				if(data.json!='ok'){
					alert('请先登录');
				}else{
				show('cart');
					}
			  }
			 });
	}
	


	//加入收藏
	addColletion = function(itemid,num){
		$.ajax({  
			  type: "POST",
			  url: 'SHOP_Collection.action',
			  data:{"itemid":itemid},
			  dataType: "json",
			  success: function( data ){
				
				if(data.json!='ok'){
					alert('请先登录');
				}else{
					show("collection");
				}
			  }
		});
	};

	 //显示加入购物车/收藏 成功
	 function show(type)
	  {
		  //添加收藏夹/购物车返回消息
		if(type == "cart"){
			$("#Idiv").html("<span style='text-align:center;color:#ffffff';line-height: 40px;>添加购物车成功</span>");
		}else if(type == "collection"){
			$("#Idiv").html("<span style='text-align:center;color:#ffffff';line-height: 40px;>添加收藏夹成功</span>");
		}

		 var Idiv=document.getElementById("Idiv"); 
		 Idiv.style.display="block";
		 //以下部分要将弹出层居中显示
		 Idiv.style.left=(document.documentElement.clientWidth-Idiv.clientWidth)/2+document.documentElement.scrollLeft+"px";
			 //alert(document.body.scrollTop)
			 var aa_scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
		 Idiv.style.top=(document.documentElement.clientHeight-Idiv.clientHeight)/2+aa_scrollTop+"px";
			 //此处出现问题，弹出层左右居中，但是高度却不居中，显示在上部分，导致一                      //部分不可见,于是暂时在下面添加margin-top


		 //以下部分使整个页面至灰不可点击
			 var procbg = document.createElement("div");  //首先创建一个div
		 procbg.setAttribute("id","mybg");            //定义该div的id
		// procbg.style.background ="#000000";
		 procbg.style.width ="100%";
		 procbg.style.height ="100%";
		 procbg.style.position ="fixed";
		 procbg.style.top ="0";
		 procbg.style.left ="0";
		 procbg.style.zIndex ="500";
		 procbg.style.opacity ="0.6";
		 procbg.style.filter ="Alpha(opacity=70)";
			 //取消滚动条
		 document.body.appendChild(procbg);
		 document.body.style.overflow ="auto";    
		 setTimeout( closeDiv, 400 );  
	   
	 } 

	  function closeDiv()   //关闭弹出层
	 {
			 
		 var Idiv=document.getElementById("Idiv"); 
			 var mybg = document.getElementById("mybg");
		 document.body.removeChild(mybg);
		 Idiv.style.display="none";
		 document.body.style.overflow ="auto";//恢复页面滚动条
		 //document.getElementById("mybg").style.display="none"; 
	 }

	//经销商qq
	supplier = function(sid){
	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_supplier.action',
		  data:{"supplierid":sid},
		  dataType: "json",
		  success: function( data ){
			myjson = eval(data.json);
			if(myjson.length>0){
				if(myjson[0].qq!=''&&myjson[0].qq!=null&&myjson[0].qq!='undefined'){ 
				window.location.href='tencent://message/?uin='+myjson[0].qq;
				}else{
				alert('该供应商未填写QQ,请用其他方式联系');
				}
			}
		  }
		 });
	}

/** 页码 **/	
	var pagetotal = "";
	$.ajax({
		url:'getItemPage.action',
		type:'get',
		data:{
			category:cate,
			parameter:parameter,
			value:value,
			page:page,
			keyword:keyword,
			baseurl:baseurl
		},
		dataType:'json',
		success:function(data){
			if (data!=null&&data!=undefined) {
				if (data.total=="0") {
					$(".p-wrap").html("");
				} else {
					var curr = data.currentpage-0;
					var total = data.totalpage-0;
					pagetotal = total;
					var index = data.baseurl.indexOf("&page=");
					var baseurl = data.baseurl;
					if (index>=0) {
						baseurl = baseurl.substring(0,index)+"&page=";
					} else {
						baseurl+="&page=";
					}
					//baseurl = encodeURI(encodeURI(baseurl));
					//共x页
					$(".p-skip b").html(total);
					var last = "";
					var next = "";
					if (curr==total) {
						$("#page_jump_num").val(curr);
						next = '<a class="pn-next" href="'+baseurl+total+'">下一页<i>&gt;</i></a>';
					} else {
						$("#page_jump_num").val(curr+1);//跳转一个页码
						next = '<a class="pn-next" href="'+baseurl+(curr+1)+'">下一页<i>&gt;</i></a>';
					//下一页
					}
					//1...(curr-2,curr-1,curr,curr+1,curr+2)...total
					var tmp = "";
					var pageHtml="";
					if (curr==1) {
						pageHtml = "<a class='curr' href="+baseurl+"1>1</a>";
						last = '<a class="pn-prev" href="'+ baseurl+"1"+'"><i>&lt;</i><em>上一页</em></a>'
					} else {
						pageHtml = "<a class='' href="+baseurl+"1>1</a>";
						last = '<a class="pn-prev" href="'+ baseurl+(curr-1)+'"><i>&lt;</i><em>上一页</em></a>'
					}
					tmp+=pageHtml;
					
					var b1 = "<b class='pn-break'>...</b>";
					var begin = 2;
					if (curr-2>1) {
						tmp+=b1;
						begin = curr-2;
					} 
					
					for (var i=begin;i<=Math.min(total,curr+2);i++) {
						pageHtml = "<a href="+baseurl+i+">"+i+"</a>";
						if (i==curr) {
							pageHtml = "<a class='curr' href="+baseurl+i+">"+i+"</a>";
						} else {
							pageHtml = "<a class='' href="+baseurl+i+">"+i+"</a>";
						}
						tmp+=pageHtml;
					}
					
					if (curr+2<total) {
						tmp+=b1;
						var pageHtml = "<a href="+baseurl+total+">"+total+"</a>"
						tmp+=pageHtml;
					} 
					$("#J_bottomPage .p-num").html (last + tmp +next);
				}
			}
		
			
			pageClick();
		},
		error:function(){
			console.log("加载页码信息出错");
		}
		

	});
	
	//修改跳转页面
	$("#page_jump_num").live("change",function(){pageClick();});
	
	//点击跳转页面
	function pageClick() {
		var page_jump = $("#page_jump_num").val();
		if(page_jump>pagetotal) page_jump = pagetotal;
		if(page_jump<1) page_jump = 1;
		$("#page_jump_num").val(page_jump);
		if (window.location.search!="") {
			var url = "search.jsp"+window.location.search;
			if (url.indexOf("page=")>=0) {
				url = url.substring(0,url.indexOf("&page="));
			}

			var page_jump = $("#page_jump_num").val();
			url+= "&page="+page_jump+"&projid="+projID;
			$("#pn-sub").attr("onClick","window.location.href='"+url+"'");
		} else {
			var page_jump = $("#page_jump_num").val();
			url+= "&page="+page_jump+"&projid="+projID;
			$("#pn-sub").attr("onClick","window.location.href='search.jsp?page="+page_jump+"&projid="+projId+"'");
		}
	}
	

/**加载css**/
	$.ajax({  
    	url:"search/css/search_style.css",  
    	type:'get',  
    	success:function(css) {
    		$("style").append(css);
    		
    	},
    	error:function() {
			console.log("加载层样式出错.");
    	}
    });





});