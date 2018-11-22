//@ sourceURL=parameter-view.js

//子组件赋值，无子组件留空
include = [];
//商品信息
/*
    <ul id="parameter2" class="p-parameter-list">
      //每个属性一个li
      <li>
        商品名称：派克IM
      </li>
    </ul>
    <p>
    </p>
    */
var ul1 = document.createElement('ul');
var li1 = document.createElement('li');
var p1 = document.createElement('p');
ul1.setAttribute("id","p-parameter2");
ul1.setAttribute("class","p-parameter-list");


if (data!=null&&data!=undefined) {
  var tmp = "";
  for (var i=0;i<data.length;i++) {
    li1.innerHTML = data[i].name+"："+data[i].value;
    tmp+=li1.outerHTML;
  }
  ul1.innerHTML = tmp;
}
ul1.outerHTML+p1.outerHTML;