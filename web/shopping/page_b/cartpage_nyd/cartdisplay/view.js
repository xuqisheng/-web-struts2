//@ sourceURL=cartdisplay-view.js

//子组件赋值，无子组件留空
include = [];

/*
<div class="cart-main"><div class="cart-thead"><div class="column t-checkbox"></div><div class="column t-goods">商品</div><div class="column t-props"></div><div class="column t-price">单价(元)</div><div class="column t-quantity">数量</div><div class="column t-sum">小计(元)</div><div class="column t-action">操作</div></div></div>


1<div class="cart-tbody">
2    <div class="item-list">
3      <div class="item-give item-full ">

        <!-- 单品-->
4        <div class="item-item">
5          <div class="item-form">
6            <div class="cell p-goods">
7              <div class="goods-item">
8                <div class="p-img">
1                 <a href="" target="_blank">
1                   <img />
                  </a>
                </div>
9                <div class="item-msg">
10                 <div class="p-name">
2                    <a href="" target="_blank">
                      英雄（HERO）1063纯风铱金钢笔（暗尖） 黑色
                    </a>
                  </div>
                </div>
              </div>
            
11          <div class="cell p-props p-props-new">
12           <div class="props-txt">
                颜色：黑色
              </div>
            </div>
13          <div class="cell p-price">
1              <strong>
                45.00
              </strong>
            </div>
14         <div class="cell p-quantity">
17<div class="quantity-form" promoid="188185441">
            
            
            

1                <input type="text" class="itxt">
 </div>
            </div>
15          <div class="cell p-sum">
2              <strong>
                45.00
              </strong>
            </div>
16            <div class="cell p-ops">

 3             <a class="cart-remove" href="#">
                删除
              </a>
            </div>
            </div>
          </div>
        </div>


      </div>
    </div>
  </div>



<div id="cart-floatbar"><div class="cart-toolbar" style="width: 988px; height: 50px;"><div class="toolbar-wrap"><div class="options-box"><div class="toolbar-right"><div class="normal"><div class="comm-right"><div class="btn-area"> <a href="#"class="submit-btn">去结算<b></b></a></div><div class="price-sum"><div><span class="txt">总价：</span><span class="price sumPrice"><em>

</em></span></div></div></div></div></div></div></div></div></div>
*/
var head = '<div class="cart-main"><div class="cart-thead"><div class="column t-checkbox"></div><div class="column t-goods">商品</div><div class="column t-props"></div><div class="column t-price">单价(元)</div><div class="column t-quantity">数量</div><div class="column t-sum">小计(元)</div><div class="column t-action">操作</div></div></div>';
var tail1 = '<div id="cart-floatbar"><div class="cart-toolbar" style="width: 988px; height: 50px;"><div class="toolbar-wrap"><div class="options-box"><div class="toolbar-right"><div class="normal"><div class="comm-right"><div class="btn-area"> <a href="addOrder.action"class="submit-btn">去结算<b></b></a></div><div class="price-sum"><div><span class="txt">总价：</span><span class="price sumPrice"><em id="total">';
var total = "￥0.00";
var tail2 = '</em></span></div></div></div></div></div></div></div></div></div>';

var div1 = document.createElement('div');
var div2 = document.createElement('div');
var div3 = document.createElement('div');
var div4 = document.createElement('div');
var div5 = document.createElement('div');
var div6 = document.createElement('div');
var div7 = document.createElement('div');
var div8 = document.createElement('div');
var div9 = document.createElement('div');
var div10 = document.createElement('div');
var div11 = document.createElement('div');
var div12 = document.createElement('div');
var div13 = document.createElement('div');
var div14 = document.createElement('div');
var div15 = document.createElement('div');
var div16 = document.createElement('div');
var div17 = document.createElement('div');
var a1 = document.createElement('a');
var a2 = document.createElement('a');
var a3 = document.createElement('a');
var img1 = document.createElement('img');
var strong1 = document.createElement('strong');
var strong2 = document.createElement('strong');
var input1 = document.createElement('input');


div1.setAttribute("class","cart-tbody");
div2.setAttribute("class","item-list");
div3.setAttribute("class","item-give item-full");
div4.setAttribute("class","item-item item-selected");
div5.setAttribute("class","item-form");
div6.setAttribute("class","cell p-goods");
div7.setAttribute("class","goods-item");
div8.setAttribute("class","p-img");
div9.setAttribute("class","item-msg");
div10.setAttribute("class","p-name");
div11.setAttribute("class","cell p-props p-props-new");
div12.setAttribute("class","props-txt");
div13.setAttribute("class","cell p-price");
div14.setAttribute("class","cell p-quantity");
div15.setAttribute("class","cell p-sum");
div16.setAttribute("class","cell p-ops");
div17.setAttribute("class","quantity-form");
a1.setAttribute("target","_blank");
a2.setAttribute("target","_blank");
a3.setAttribute("class","cart-remove");
a3.setAttribute("href","#");
a3.innerHTML="删除";
input1.setAttribute("type","text");
input1.setAttribute("class","itxt");
img1.setAttribute("width","80");
img1.setAttribute("height","80");
img1.setAttribute("onerror", "this.src='img/default.jpg'");


a1.appendChild(img1);
div8.appendChild(a1);
div7.appendChild(div8);
div10.appendChild(a2);
div9.appendChild(div10);
div7.appendChild(div9);
div6.appendChild(div7);
div5.appendChild(div6);

div11.appendChild(div12);
div13.appendChild(strong1);
div17.appendChild(input1);
div14.appendChild(div17);
div15.appendChild(strong2);
div16.appendChild(a3);
div5.appendChild(div11);
div5.appendChild(div13);
div5.appendChild(div14);
div5.appendChild(div15);
div5.appendChild(div16);
div4.appendChild(div5);


div2.appendChild(div3);
div1.appendChild(div2);

var out = "";
if (data!=null&&data!=undefined) {
	var list = "";
	var sum = 0;
	for (var i=0;i<data.length;i++) {
		a1.setAttribute("href","item.jsp?itemid="+data[i].itemid+"&projid="+projID);
		if (data[i].itemimg==null||data[i].itemimg==""||data[i].itemimg=="null"){
			img1.setAttribute("src","img/default.jpg");
		} else {
			img1.setAttribute("src","img/"+data[i].itemid+"/"+data[i].itemimg);
		}
		a2.setAttribute("href","item.jsp?itemid="+data[i].itemid+"&projid="+projID);
		a2.innerHTML = data[i].itemname;
		div12.innerHTML = data[i].subitemname;
		strong1.innerHTML = (data[i].subitemprize-0).toFixed(2);
		strong1.setAttribute("id","prize-"+data[i].cartid);
		input1.setAttribute("value",data[i].number);
		input1.setAttribute("cart",data[i].cartid);
		strong2.innerHTML = ((data[i].subitemprize-0)*data[i].number).toFixed(2);
		strong2.setAttribute("id","sum-"+data[i].cartid);
		sum = sum+(data[i].subitemprize-0)*data[i].number;
		a3.setAttribute("href","deleteCart.action?cartid="+data[i].cartid);
		list+=div4.outerHTML;
	}
	div3.innerHTML = list;
	out = div1.outerHTML;
	total = "￥"+sum.toFixed(2);
}
head+out+tail1+total+tail2;