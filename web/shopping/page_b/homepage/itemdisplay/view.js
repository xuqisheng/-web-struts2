//@ sourceURL=itemdisplay-view.js

//子组件赋值，无子组件留空
include = ['items'];

/*
<div id="J_goodsList" class="goods-list-v1">...</div>
*/
var div8 = document.createElement('div');

div8.setAttribute("id","J_goodsList");
div8.setAttribute("class","goods-list-v1");

div8.outerHTML;
