//@ sourceURL=navigation-view.js

//子组件赋值，无子组件留空

//页面生成
//最上层模板
/*
    <div class = "w">
        <div id="categorys-2014" class="dorpdown">
          <div class="dt" id="mainnav">
            <a target="_blank" href="#">
              全部商品分类
            </a>
          </div>
          <div class="dd" id="items" style="display:none">
            <div class="dd-inner" id="mainitem">
             
            </div>
            <div class="dorpdown-layer"style="display: block;">
              
            </div>
          </div>
        </div>
      </div>    
    */
var topdiv = document.createElement('div');
var topdiv2 = document.createElement('div');
var topdiv3 = document.createElement('div');
var topa = document.createElement('a');
var topdiv4 = document.createElement('div');
var topdiv5 = document.createElement('div');
var topdiv6 = document.createElement('div');
topdiv6.setAttribute("class", "dorpdown-layer");
topdiv6.setAttribute("style", "display:block;");
topdiv5.setAttribute("class", "dd-inner");
topdiv5.setAttribute("id","mainitem");
topdiv4.setAttribute("class", "dd");
topdiv4.setAttribute("id", "items");
topdiv4.setAttribute("style", "display:none;");
topa.setAttribute("href", "search.jsp?category=0&projid="+projID);
topa.innerHTML = "全部商品分类";
topdiv3.setAttribute("class", "dt");
topdiv3.setAttribute("id", "mainnav");
topdiv2.setAttribute("class", "dorpdown");
topdiv2.setAttribute("id", "categorys-2014");
topdiv.setAttribute("class", "w");
topdiv4.appendChild(topdiv5);
topdiv4.appendChild(topdiv6);
topdiv3.appendChild(topa);
topdiv2.appendChild(topdiv3);
topdiv2.appendChild(topdiv4);
topdiv.appendChild(topdiv2);

//一级菜单模板
/*第一级
            <div class="item fore1" data-index="1">
                <h3>
                  <a target="_blank" href="http://channel.jd.com/electronic.html">
                    家用电器
                  </a>
                </h3>
                <i>
                  &gt;
                </i>
            </div>*/
var maindiv = document.createElement('div');
var mainh = document.createElement('h3');
var maini = document.createElement('i');
var maina = document.createElement('a');
maini.innerHTML = "&gt;";
maindiv.appendChild(mainh);
maindiv.appendChild(maini);
mainh.appendChild(maina);

//二三级菜单模板
/*<div class="item-sub" id="category-item-1">             
            <div class="subitems">
              <dl class="fore1">
                <dt>
                  <a href="http://channel.jd.com/737-794.html" target="_blank">
                    大家电
                    <i>
                      &gt;
                    </i>
                  </a>
                </dt>
                <dd>
                  <a href="http://list.jd.com/list.html?cat=737,794,798" target="_blank">
                    平板电视
                  </a>
                </dd>
              </dl>
            </div>
          </div>*/
var suba = document.createElement("a");
var subi = document.createElement("i");
var subdd = document.createElement("dd");
var suba2 = document.createElement("a");
var subdiv = document.createElement("div");
var subdiv2 = document.createElement("div");
var subdl = document.createElement("dl");
var subdt = document.createElement("dt");
subdiv.setAttribute("class", "item-sub");
subdiv2.setAttribute("class", "subitems");
subi.innerHTML = "&gt;";
subdiv.appendChild(subdiv2);

var firstlv = data[0];
var secondlv = data[1];
var thirdlv = data[2];

var firstinfo = new Array();
var secondinfo = new Array();
var thirdinfo = new Array();

//生成第一级菜单，生成顺序即读入顺序
if (firstlv != undefined && firstlv != null) {
    for (var i = 0; i < firstlv.length; i++) {
        maindiv.setAttribute("class", "item fore" + (i + 1));
        maindiv.setAttribute("data-index", (i + 1));
        maina.setAttribute("href", "search.jsp?category="+firstlv[i].categoryid+"&projid="+projID);
        maina.innerHTML = firstlv[i].categoryname;
        var html = maindiv.outerHTML;
        topdiv5.innerHTML += html;

        firstinfo[i] = firstlv[i].categoryid;
    }
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
            suba.setAttribute("href", "search.jsp?category="+secondlv[i].categoryid+"&projid="+projID);
            suba.innerHTML = secondlv[i].categoryname;
            suba.appendChild(subi);
            var tmp = new Object();
            tmp.id = secondlv[i].categoryid;
            tmp.html = suba.outerHTML;
            secondinfo[index].push(tmp);
        }
    }
}

//根据parent，提取三级数据及结构并保存
if (thirdlv != undefined && thirdlv != null) {
    for (var i = 0; i < thirdlv.length; i++) {
        var indexfirst = -1;
        var indexsecond;
        for (var j = 0; j < secondinfo.length; j++) {
            if (secondinfo[j] == undefined || secondinfo[j] == null) {
                continue;
            }
            for (var k = 0; k < secondinfo[j].length; k++) {
                if (secondinfo[j][k].id == thirdlv[i].parents) {
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
            suba2.setAttribute("href", "search.jsp?category="+thirdlv[i].categoryid+"&projid="+projID);
            suba2.innerHTML = thirdlv[i].categoryname;
            var tmp = new Object();
            tmp.id = thirdlv[i].categoryid;
            tmp.html = suba2.outerHTML;
            thirdinfo[indexfirst][indexsecond].push(tmp);
        }
    }
}

//根据数据生成二三级菜单，同一分类下同一级按照读入顺序生成
for (var i = 0; i < secondinfo.length; i++) {
    subdiv.setAttribute("id", "category-item-" + (i + 1));
    subdiv2.innerHTML = "";
    if (secondinfo[i]) {
    	for (var j = 0; j < secondinfo[i].length; j++) {
    		if (thirdinfo[i] && thirdinfo[i][j] ) {
    			subdl.innerHTML = "";
    			subdl.setAttribute("class", "fore" + (j + 1));
    			subdt.innerHTML = "";
    			$(subdt).append(secondinfo[i][j].html);
    			subdl.appendChild(subdt);
    			subdd.innerHTML = "";
    			for (var k = 0; k < thirdinfo[i][j].length; k++) {
    				$(subdd).append(thirdinfo[i][j][k].html);
    			}
    			subdl.appendChild(subdd);
    			$(subdiv2).append(subdl.outerHTML);
    		}
    	}
    	topdiv6.innerHTML += subdiv.outerHTML;
    }
}

//页面生成
topdiv.outerHTML;