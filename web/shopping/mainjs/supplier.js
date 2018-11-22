/**动态修改html页面**/

window.onload=function(){
suppliers();
}

/*主页菜单*/
function suppliers(){
	$("#supplier").children().remove();
	var skey = $("#supplierkey").val();
	var supplier = "";
	var suppliers = "";
      	 $.ajax({  
	      type: "POST",
		  url: 'SHOP_GetSuppliers.action',
		  data: {"key":skey},
		  dataType: "json",
		  success: function( data ){
			myjson = eval(data.json);
			for(var i=0;i<myjson.length;i++){ 
			supplier = " <tr>"+
							"<td>"+myjson[i].id+"</td>"+
							"<td>"+myjson[i].name+"</td>"+
							"<td>"+myjson[i].contact+"</td>"+
							"<td>"+myjson[i].telephone+"</td>"+
							"<td>"+myjson[i].qq+"</td>"+
							"<td>"+myjson[i].email+"</td>"+
							"<td>"+myjson[i].url+"</td>"+
							"<td>"+myjson[i].address+"</td>"+
							"<td>"+myjson[i].businesslicense+"</td>"+
							"<td>"+myjson[i].taxlicense+"</td>"+
						 "</tr>";
			suppliers = suppliers + supplier;

		  }
		$("#supplier").append(suppliers);
		  }
		 });
}

