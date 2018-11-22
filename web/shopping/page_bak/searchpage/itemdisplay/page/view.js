//@ sourceURL=pages-view.js

//子组件赋值，无子组件留空
include = [];

/*
<div class="p-wrap" id="J_bottomPage">
    <span class="p-num">
      <a class="pn-prev" href="/list.html?cat=9987,653,655&amp;page=1&amp;go=0&amp;JL=6_0_0">
        <i>
          &lt;
        </i>
        <em>
          上一页
        </em>
      </a>
      
      1...(curr-2,curr-1,curr,curr+1,curr+2)...total 形式
      
      
      <a href="/list.html?cat=9987,653,655&amp;page=1&amp;go=0&amp;JL=6_0_0"
      class="">
        1
      </a>
      
      
      <b class="pn-break ">
        …
      </b>
      
      
      <a href="/list.html?cat=9987,653,655&amp;page=2&amp;go=0&amp;JL=6_0_0"
      class=" curr">
        2
      </a>
      <a href="/list.html?cat=9987,653,655&amp;page=3&amp;go=0&amp;JL=6_0_0"
      class=" ">
        3
      </a>
      <a href="/list.html?cat=9987,653,655&amp;page=4&amp;go=0&amp;JL=6_0_0"
      class=" ">
        4
      </a>
      
      
      
      <b class="pn-break ">
        …
      </b>
      
      
      <a href="/list.html?cat=9987,653,655&amp;page=47&amp;go=0&amp;JL=6_0_0"
      class="">
        47
      </a>
      <a class="pn-next" href="/list.html?cat=9987,653,655&amp;page=3&amp;go=0&amp;JL=6_0_0">
        下一页
        <i>
          &gt;
        </i>
      </a>
    </span>
    
    
    <span class="p-skip">
      <em>
        共
        <b>
          47 页码数
        </b>
        页&nbsp;&nbsp;到第
      </em>
      <input class="input-txt" id="page_jump_num" maxlength="4" value="2" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
      <em>
        页
      </em>
      <a class="btn btn-default" href="">
        确定
      </a>
    </span>
  </div>
*/

var div1 = document.createElement("div");
var span1 = document.createElement("span");
var span2 = document.createElement("span");
var last = document.createElement("a");
var next = document.createElement("a");
var i1 = document.createElement("i");
var i2 = document.createElement("i");
var em1 = document.createElement("em");
var em2 = document.createElement("em");
var em3 = document.createElement("em");
var b1 = document.createElement("b");
var b2 = document.createElement("b");
var input1 = document.createElement("input");
var submit = document.createElement("a");

var page = document.createElement("a");

div1.setAttribute("class", "p-wrap");
div1.setAttribute("id", "J_bottomPage");
span1.setAttribute("class","p-num");
last.setAttribute("class", "pn-prev");
i1.innerHTML = "&lt;";
em1.innerHTML = "上一页";
next.setAttribute("class", "pn-next");
i2.innerHTML = "&gt;";
span2.setAttribute("class", "p-skip");
input1.setAttribute("class","input-txt");
input1.setAttribute("id","page_jump_num");
input1.setAttribute("onkeyup","this.value=this.value.replace(/[^0-9]/g,'');");
em3.innerHTML = "页";
submit.setAttribute("class","btn btn-default");
submit.setAttribute("id","pn-sub");
submit.innerHTML = "确定";
b1.setAttribute("class","pn-break");
b1.innerHTML = "...";

last.appendChild(i1);
last.appendChild(em1);
next.innerHTML = "下一页"+i2.outerHTML;
span2.appendChild(em2);
span2.appendChild(input1);
span2.appendChild(em3);
span2.appendChild(submit);
div1.appendChild(span1);
div1.appendChild(span2);

if (data!=null&&data!=undefined) {
	if (data.total=="0") {
		div1.innerHTML ="";
	} else {
		var curr = data.currentpage-0;
		var total = data.totalpage-0;
		var index = data.baseurl.indexOf("&page=");
		var baseurl = data.baseurl;
		if (index>=0) {
			baseurl = baseurl.substring(0,index)+"&page=";
		} else {
			baseurl+="&page=";
		}
		baseurl = encodeURI(encodeURI(baseurl));
		b2.innerHTML = total;
		em2.innerHTML = "共"+b2.outerHTML+"页&nbsp;&nbsp;到第";
		if (curr==total) {
			input1.setAttribute("value", curr);
			next.setAttribute("href", baseurl+total);
		} else {
			input1.setAttribute("value", curr+1);
			next.setAttribute("href", baseurl+(curr+1));
		}
		//1...(curr-2,curr-1,curr,curr+1,curr+2)...total
		var tmp = "";
		page.innerHTML = "1";
		page.setAttribute("href",baseurl+"1");
		if (curr==1) {
			page.setAttribute("class","curr");
			last.setAttribute("href", baseurl+"1");
		} else {
			page.setAttribute("class","");
			last.setAttribute("href", baseurl+(curr-1));
		}
		tmp+=page.outerHTML;
		
		var begin = 2;
		if (curr-2>1) {
			tmp+=b1.outerHTML;
			begin = curr-2;
		} 
		
		for (var i=begin;i<=Math.min(total,curr+2);i++) {
			page.innerHTML = i;
			page.setAttribute("href",baseurl+i);
			if (i==curr) {
				page.setAttribute("class","curr");
			} else {
				page.setAttribute("class","");
			}
			tmp+=page.outerHTML;
		}
		
		if (curr+2<total) {
			tmp+=b1.outerHTML;
			page.innerHTML = total;
			page.setAttribute("href",baseurl+total);
			tmp+=page.outerHTML;
		} 
		span1.innerHTML = last.outerHTML+tmp+next.outerHTML;		
	}
}

div1.outerHTML;




 