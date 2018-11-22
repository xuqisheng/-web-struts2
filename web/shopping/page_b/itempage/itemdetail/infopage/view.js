//@ sourceURL=infopage-view.js

//子组件赋值，无子组件留空
include = [];
//商品信息
var url = "";
if (data!=null&&data!=undefined) {
	if (data.itempage=="null") {
		url='<iframe id="infoframe" frameborder="0" scrolling="no" src="img/item.jsp?itemid='+data.itemid+'&projid='+projID+' onload="iFrameHeight()"></iframe>';
	} else {
		var imageStrings = data.itempage.split(/\|/);
		for (var i = 0; i < imageStrings.length; i ++) {
			var seq = imageStrings[i].split(/&/)[0];
			url+='<iframe id="infoframe'+(i+1)+'" frameborder="0" scrolling="no" src="../fileSystem_getImgStreamAction.action?seq='+seq+'&projid='+projID+'" onload="iFrameHeight('+(i+1)+')"></iframe>';
		}
	}
}
url;