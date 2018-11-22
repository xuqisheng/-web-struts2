//@ sourceURL=iteminfo-view.js

//子组件赋值，无子组件留空
include = [];
//商品信息
/*
  <div id="preview">
    <div id="spec-n1" class="jqzoom">
      <img width="350" height="350" /> 商品图片
    </div>
  </div>

  <div class="m-item-inner">
    <div id="itemInfo">
      <div id="name">
        <h1>
          商品名称
        </h1>
      </div>
      <div id="summary">
        <div class="summary-info J-summary-info clearfix">
          <div id="comment-count" class="comment-count item fl">
            <p class="comment">
              累计评价
            </p>
            <a class="count" href="#comment">
              评价数量
            </a>
          </div>
        </div>
        <div id="summary-price">
          <div class="dt">
            价格：
          </div>
          <div class="dd">
            //每个价格一个
            <strong class="p-price">
              ￥价格
            </strong>
        </div>
      </div>
      
      <div id="choose" class="clearfix p-choose-wrap">
        <div id="choose-color" class="li choose-color-shouji p-choose">
          <div class="dt">
            选择XX：
          </div>
          <div class="dd">

            //每一个div表示一个选项，selected默认
            <div class="item  selected">
              <b>
              </b>
              <a href="#">
                <img data-img="1" width="25" height="25" /> 缩略图  
                <i>
                  类别名
                </i>
              </a>
            </div>

          </div>
        </div> 

        //购买
        <div id="choose-btns" class="li">
          <div class="choose-amount fl ">
            <div class="wrap-input">
              <a class="btn-reduce" href="javascript:;">
                -
              </a>
              <a class="btn-add" href="javascript:;">
                +
              </a>
              <input class="text" id="buy-num" value="1">
            </div>
          </div>
          <div class="btn">
            <a class="btn-append" href="#">
              加入购物车
              <b>
              </b>
            </a>
          </div>
        </div> 


      </div>
    </div>
  </div>


    */
var div1 = document.createElement('div');
var div2 = document.createElement('div');
var img1 = document.createElement('img');
var div3 = document.createElement('div');
var div4 = document.createElement('div');
var div5 = document.createElement('div');
var h1 = document.createElement('h1');
var div6 = document.createElement('div');
var div7 = document.createElement('div');
var div8 = document.createElement('div');
var p1 = document.createElement('p');
var a1 = document.createElement('a');
var div9 = document.createElement('div');
var div10 = document.createElement('div');
var div11 = document.createElement('div');
var strong1 = document.createElement('strong');
var div12 = document.createElement('div');
var div13 = document.createElement('div');
var div14 = document.createElement('div');
var div15 = document.createElement('div');
var div16 = document.createElement('div');
var b1 = document.createElement('b');
var a2 = document.createElement('a');
var img2 = document.createElement('img');
var i1 = document.createElement('i');

var div17 = document.createElement('div');
var div18 = document.createElement('div');
var div19 = document.createElement('div');
var input1 = document.createElement('input');
var div20 = document.createElement('div');
var a5 = document.createElement('a');
var b2 = document.createElement('b');

div1.setAttribute("id","preview");
div2.setAttribute("id","spec-n1");
div2.setAttribute("class","jqzoom");
img1.setAttribute("width","350");
img1.setAttribute("height","350");
img1.setAttribute("onerror", "this.src='img/default.jpg'");
div3.setAttribute("class","m-item-inner");
div4.setAttribute("id","itemInfo");
div5.setAttribute("id","name");
div6.setAttribute("id","summary");
div7.setAttribute("class","summary-info J-summary-info clearfix");
div8.setAttribute("id","comment-count");
div8.setAttribute("class","comment-count item fl");
p1.setAttribute("class","comment");
p1.innerHTML = "累计评价";
a1.setAttribute("class","count");
a1.setAttribute("href","#comments");
div9.setAttribute("id","summary-price");
div10.setAttribute("class","dt");
div10.innerHTML = "价格：";
div11.setAttribute("class","dd");
strong1.setAttribute("class","p-price");
strong1.setAttribute("id","jd-price");

div12.setAttribute("class","clearfix p-choose-wrap");
div13.setAttribute("class","li choose-color-shouji p-choose");
div14.setAttribute("class","dt");
div14.innerHTML = "选择系列：";
div15.setAttribute("class","dd");
div16.setAttribute("class","item selected");
a2.setAttribute("href","#");
a2.setAttribute("class","changeprize");
img2.setAttribute("width","25");
img2.setAttribute("height","25");
img2.style.marginRight="5px";
img2.setAttribute("onerror", "this.src='img/default.jpg'");

div17.setAttribute("id","choose-btns");
div17.setAttribute("class","li");
div18.setAttribute("class","choose-amount fl");
div19.setAttribute("class","wrap-input");
input1.setAttribute("class","text");
input1.setAttribute("id","buy-num");
input1.setAttribute("value","1");
div20.setAttribute("class","btn");
a5.setAttribute("class","btn-append");
a5.setAttribute("href","#");
a5.innerHTML="加入购物车";

div2.appendChild(img1);
div1.appendChild(div2);
div5.appendChild(h1);
div8.appendChild(p1);
div8.appendChild(a1);
div7.appendChild(div8);
div9.appendChild(div10);
div11.appendChild(strong1);
div9.appendChild(div11);
div6.appendChild(div7);
div6.appendChild(div9);
div3.appendChild(div4);
a2.appendChild(img2);
a2.appendChild(i1);
div16.appendChild(b1);
div16.appendChild(a2);
div13.appendChild(div14);
div13.appendChild(div15);
div12.appendChild(div13);
div3.appendChild(div12);

div19.appendChild(input1);
div18.appendChild(div19);
a5.appendChild(b2);
div20.appendChild(a5);
div17.appendChild(div18);
div17.appendChild(div20);

if(data!=null&&data!=undefined) {
  var subid = getQueryString("subid");
  if (subid==null) {
	 subid="1";
  }

  //处理选项
  var tmp1 = "";
  var tmp2 = "";
  //div16.setAttribute("class","item selected");
  for (var i=0;i<data.choose.length;i++) {
    i1.innerHTML = data.choose[i].itemname;
    a2.setAttribute("sub",data.choose[i].itemid);
    
	var itemImgUpload = data.choose[i].itemimgupload;
    if (data.choose[i].itemid==subid) {
    	if (itemImgUpload) {
    		var seq = itemImgUpload.split(/&/)[0]; // itemImgUplad的格式形如: seq&xxx.jpg&.jpg
    		img1.setAttribute("src", "../fileSystem_getImgStreamAction.action?seq="+seq+"&projid="+projID);  // projID是index.jsp加载时设置的全局js变量 // by wyj 2016-8-23
    	}
    	else {
    		img1.setAttribute("src","img/default.jpg");
    	}
    }
    h1.innerHTML = data.itemname;
    a1.innerHTML = data.comment;
    
    if (data.choose[i].itemid==subid) {
    	a5.setAttribute("href","addCart.action?subid="+data.choose[i].itemid+"&num=1");
        div16.setAttribute("class","item selected");
        strong1.style.display = "";
    } else {
        div16.setAttribute("class","item");
    	strong1.style.display = "none";
    }
    
	if (itemImgUpload) {
		var seq = itemImgUpload.split(/&/)[0]; // itemImgUplad的格式形如: seq&xxx.jpg&.jpg
		img2.setAttribute("src", "../fileSystem_getImgStreamAction.action?seq="+seq+"&projid="+projID);  // projID是index.jsp加载时设置的全局js变量 // by wyj 2016-8-23
	}
	else {
		img2.setAttribute("src","img/default.jpg");
	}
    
    tmp1+=div16.outerHTML;
    
    strong1.innerHTML = "￥"+(data.choose[i].prize-0).toFixed(2);
    strong1.setAttribute("sub",data.choose[i].itemid);

    tmp2+=strong1.outerHTML;
  }
  div15.innerHTML = tmp1;
  div11.innerHTML = tmp2; 
  div4.innerHTML = div5.outerHTML+div6.outerHTML;
}

div1.outerHTML+div3.outerHTML+div17.outerHTML;