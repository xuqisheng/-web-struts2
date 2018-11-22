//@ sourceURL=condition-view.js

//子组件赋值，无子组件留空
include = [];

//由多个同类型div组成，每个为
// <div class="J_selectorLine">
//   <div class="sl-wrap">
//     <div class="sl-key">
//       <span>
//         类别：
//       </span>
//     </div>
//     <div class="sl-value">
//       <div class="sl-v-list">
//         <ul class="J_valueList">
//           <li>
//             <a href="#">
//               中性笔/签字笔
//             </a>
//           </li>
//           。。。每一个是一个li
//         </ul>
//       </div>
//     </div>
//   </div>
// </div>

var div1 = document.createElement('div');
var div2 = document.createElement('div');
var div3 = document.createElement('div');
var span1 = document.createElement('span');
var div4 = document.createElement('div');
var div5 = document.createElement('div');
var ul1 = document.createElement('ul');
var li1 = document.createElement('li');
var a1 = document.createElement('a');

div1.setAttribute("class","J_selectorLine");
div2.setAttribute("class","sl-wrap");
div3.setAttribute("class","sl-key");
div4.setAttribute("class","sl-value");
div5.setAttribute("class","sl-v-list");
ul1.setAttribute("class","J_valueList");
a1.setAttribute("class","parameter");
a1.setAttribute("href","#");
div5.appendChild(ul1);
div4.appendChild(div5);
div3.appendChild(span1);
div2.appendChild(div3);
div2.appendChild(div4);
div1.appendChild(div2);
li1.appendChild(a1);
li1.setAttribute("style","display:block;");


var out = "";
var cate = getQueryString("category");
var keyword = decodeURI(decodeURI(getQueryString("keyword")));
var parameter = getQueryString("parameter");
var value = decodeURI(decodeURI(getQueryString("value")));
if (parameter!=null&&parameter!=undefined) {
	parameter = parameter.split(",");
}
if (value!=null&&value!=undefined) {
	value = value.split(",");
}

if (data!=null&&data!=undefined) {
	for (var i=0;i<data.length;i++) {
		span1.innerHTML = data[i].display;
		var li = "";
		for (var j=0;j<data[i].options.length;j++) {
			a1.innerHTML = data[i].options[j];
			a1.setAttribute("parameter",data[i].id);
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
				a1.setAttribute("style","font-weight:800");
			} else {
				a1.setAttribute("style","");
			}
			
			
			li+=li1.outerHTML;
		}
		ul1.innerHTML = li;
		out+=div1.outerHTML;
	}
}

out;