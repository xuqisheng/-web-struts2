//@ sourceURL=items-view.js

//子组件赋值，无子组件留空
include = [];

// 商品列表，每一个li是一个项目
//   <ul class="gl-warp clearfix">
//     <li class="gl-item">
//       <div class="gl-i-wrap">
//         <div class="p-img">
//           <a target="_blank" href="#"> 详情页面url		//a1
//             <img width="220" height="220" class="err-product" src="#">  图片url	
//           </a>
//           </div>
//         </div>
//         <div class="p-price">
//           <strong>
//             <em>
//               ￥
//             </em>
//             <i>
//               价格
//             </i>
//           </strong>
//         </div>
//         <div class="p-name p-name-type-2">
//           <a target="_blank" href="#">  详情页面url			
//             <em>
//               商品标题
//             </em>     
//           </a>
//         </div>
//         <div class="p-commit">
//           <strong>
//             已有
//               评价数量
//             人评价
//           </strong>
//         </div>
//         <div class="p-operate">
//           <a class="p-o-btn addcart" href="#" target="_blank">
//			<i></i>
//             加入购物车
//           </a>
//         </div>
//       </div>
//     </li>
//		...
//   </ul>

var ul1 = document.createElement('ul');
var li1 = document.createElement('li');
var div1 = document.createElement('div');
var div2 = document.createElement('div');
var a1 = document.createElement('a');
var img1 = document.createElement('img');
var img2 = document.createElement('img');//危化品标识
var div3 = document.createElement('div');
var strong1 = document.createElement('strong');
var em1 = document.createElement('em');
var i1 = document.createElement('i');
var div4 = document.createElement('div');
var a2 = document.createElement('a');
var em2 = document.createElement('em');
var div5 = document.createElement('div');
var strong2 = document.createElement('strong');
var strong3 = document.createElement('strong');
var div6 = document.createElement('div');
var a3 = document.createElement('a');
var div7 = document.createElement('div');
var div8 = document.createElement('div');
var a4 = document.createElement('a');

ul1.setAttribute("class","gl-warp clearfix");
li1.setAttribute("class","gl-item");
div1.setAttribute("class","gl-i-wrap");
div2.setAttribute("class","p-img");
a1.setAttribute("target","_blank");
img1.setAttribute("width","220");
img1.setAttribute("height","220");
img1.setAttribute("class","err-product");
img1.setAttribute("onerror", "this.src='img/default.jpg'");
img2.setAttribute("width","40");//危化品标识
img2.setAttribute("id","test");//危化品标识
img2.setAttribute("style","position:absolute;marginRight:0");//危化品标识
div3.setAttribute("class","p-price");
em1.innerHTML = "￥";
div4.setAttribute("class","p-name p-name-type-2");
a2.setAttribute("target","_blank");
div5.setAttribute("class","p-commit");
div6.setAttribute("class","p-operate");
a3.setAttribute("class","p-o-btn addcart");
//a3.setAttribute("href","#");
//a3.setAttribute("target","_blank");
a3.innerHTML = "<i></i>加入购物车";
div8.setAttribute("style","display:none;"); 
div8.innerHTML = "";
div7.setAttribute("style","float: right");
div7.setAttribute("id","sin");
/* 白雪 2017-08-04 功能：加入收藏*/
a4.setAttribute("class","p-o-btn addcart1 addcart");
a4.setAttribute("style","background-position: 0 -414px;");
  
a4.innerHTML = "<i style='background-position: 0 -414px;'></i>添加收藏";
/*  结束  */

a1.appendChild(img1);
div2.appendChild(a1);
strong1.appendChild(em1);
strong1.appendChild(i1);
div3.appendChild(strong1);
//div3.appendChild(div7);
a2.appendChild(em2);
div4.appendChild(a2);
div5.appendChild(strong2);
div6.appendChild(a4);
div6.appendChild(a3);
div6.appendChild(div8);
div1.appendChild(div2);
div1.appendChild(div3);
div1.appendChild(div4);
div1.appendChild(div7);
div1.appendChild(div5);
div1.appendChild(div6);
li1.appendChild(div1);

var out = "";

if (data!=null&&data!=undefined) {
	var tmp = "";
	for (var i=0;i<data.length;i++) {
		var parents = data[i].parents;
		if (data[i].parents==undefined||data[i].parents==null||data[i].parents=="null") {
			parents = data[i].itemid;
		}
		a1.setAttribute("href","item.jsp?itemid="+parents+"&subid="+data[i].itemid + "&projid="+projID);
		var itemImgUpload = data[i].itemimgupload;

		/**/
		var itemcate = data[i].itemcate;//找分类号最后一个数
		var num_split = itemcate.split(".")
		var final_num = num_split[num_split.length-2]


    	if (itemImgUpload) {
    		var seq = itemImgUpload.split(/&/)[0]; // itemImgUplad的格式形如: seq&xxx.jpg&.jpg
    		img1.setAttribute("src", "fileSystem_getImgStreamAction.action?seq="+seq+"&projid="+projID);  // projID是index.jsp加载时设置的全局js变量 // by wyj 2016-8-23
    	}
    	else {
    		img1.setAttribute("src","img/default.jpg");
    	}

		/*危化品标识*/
		if(a1.children.length>1){
			a1.removeChild(img2);
		}
		if (final_num == 221){//如果最后的数为危化品，寻找他在shp_category中imgroot的图片路径
			//if (data[i].imgroot){//如果为真，就把图片标识加在商品图片上
			//	img2.setAttribute("src",data[i].imgroot)
			//}
			img2.setAttribute("src","img/toxic.jpg")//易燃
			a1.appendChild(img2);//危化品标识
		}else if (final_num == 222){//易爆
			img2.setAttribute("src","img/yzb.jpg")
		a1.appendChild(img2);//危化品标识
		}else if (final_num == 223){//剧毒
			img2.setAttribute("src","img/judu.jpg")
			a1.appendChild(img2);//危化品标识
		}
		/**/


		i1.innerHTML = (data[i].prize-0).toFixed(2);
		a2.setAttribute("href","item.jsp?itemid="+parents+"&subid="+data[i].itemid+ "&projid="+projID);
		//添加购物车前，先验证是否登录
		//a3.setAttribute("href","addCart.action?subid="+data[i].itemid+"&num=1");
		//a3.setAttribute("onclick","addcard(\""+data[i].itemid+"\",1,this)");
		a3.setAttribute("onclick","addcard(\""+data[i].itemid+"\",1,this,event)");
		a4.setAttribute("onclick","addColletion(\""+data[i].itemid+"\",1)");
		div8.setAttribute("id","addsuccess_"+data[i].itemid);
		em2.innerHTML = data[i].itemname;
		strong2.innerHTML = "已有"+data[i].comment+"人评价";
		//strong3.innerHTML = "    <div id='supplier' style='float: right'><a   href='#'>"+data[i].itempara["0"]+"</a><a onclick='supplier(\""+data[i].itempara["15"]+"\")'><img width='16' height='16' src='./img/qq.png'/></a></div>";
		div7.innerHTML = " <strong  style='color:blue'>"+data[i].itempara["0"]+"</strong><a onclick='supplier(\""+data[i].itempara["15"]+"\")'><img width='16' height='16' src='./img/qq.png'/></a>";
		tmp+=li1.outerHTML;
	}
	ul1.innerHTML = tmp;
	out = ul1.outerHTML;
}
out;