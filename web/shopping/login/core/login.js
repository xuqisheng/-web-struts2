
 $(function(){
	 $("#pwd").hide();
	 function GetRequest() { 
		var url = location.search; //获取url中"?"符后的字串 

		var theRequest = url.substring(url.indexOf('=')+1);
			return theRequest; 
			} 
					 

		function reloadCheckcode(){
			$("#checkcodeImg").attr("src","loginAction_getCheckCodeImg.action?s="+new Date());
		};
		
		$("#loginbtn1").click(function(){
				$("#loginbtn1").hide("drop",{},300,function(){
					$("#loginWindow").show("drop",{},300);
					$("#loginbtn2").show("drop",{},300);
				});
				
		});
		
		$("#btnview img")
		.css({border:"solid 1px gray"})
		.hover(function(){
					$(this).css({border:"solid 1px #ADFC49"});
		},function(){
					$(this).css({border:"solid 1px gray"});
		});
		
		$("#checkcodeImg").click(function(){
					reloadCheckcode();
		});
		
		$("#loginbtn2").click(function(){
			var returnurl = GetRequest() ;
			var uid = $("#uid").val();
			var pwd = $("#pwd").val();
			//alert(uid);
			//alert(pwd);
			var chkcode = $("#chkcode").val();
			
			if( (uid != "请输入用户名") && (uid != "") && (pwd != "") ){
				
				
				$.ajax({
						type:"POST",
						url:"loginAction_doLogin.action",
						data:{uid:uid,pwd:pwd},
						dataType:"text",
						success:function(data,textStatus){
							if(data=="ok"){
								$("#loading").hide();
								$("#loginbtn2").show();
								$("#msg").css({fontSize:"12px",color:"#0000FF"}).html("登录成功!");
								if(returnurl!="" && returnurl!=null){
								window.location.href=returnurl;
								}else{
								window.location.href="./index/index.jsp";
								}
							}else{
								reloadCheckcode();
								if(data == "checkerror"){
									$("#msg").css({fontSize:"12px",color:"#FF0000"}).html("<br/>验证码不正确");
								}else{
									$("#msg").css({fontSize:"12px",color:"#FF0000"}).html("<br/>用户名或密码不正确");
									
								}
							}
						}
				});
			}
		});
		
		$("#uid").addClass("embed").val("请输入用户名")
		.focus(function(){
					if($("#uid").val() == "请输入用户名"){
				 		$("#uid").val("").removeClass("embed");
				 	}
		}) .blur(function(){
				 	if($("#uid").val() == ""){
				 		$("#uid").removeClass("normal").val("请输入用户名");
				 	}
		});
		

		$("#pwd1").addClass("embed").val("请输入密码")
		.focus(function(){
				 	$("#pwd1").hide();
					
				 	$("#pwd").show().focus();
		});
		$("#pwd").blur(function(){
				  	if($("#pwd").val()==""){
					  	$("#pwd1").show();
					 	$("#pwd").hide();
				 	}
		});
		$(document).keypress(function(e){
			if(e.which == 13){
				$("#loginbtn2").click();
			}
		});
	});



