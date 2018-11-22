 //表单项目是否通过检查的标识
		    var checkFlag = {
		    "email":false,//非空和格式检查标识
		    "emailajax":false,//ajax检查标识
		    "nickname":false,
		    "password":false,
		    "repassword":false,
		    "code":false};
		    
			$(function(){
				$("#txtEmail").blur(function(){
				    var email = $("#txtEmail").val();
				    checkFlag.email = false;//未通过检查
				    checkFlag.emailajax = false;//未通过检查
				    //非空检查
				    if(email == ""){
				    	$("#email\\.info").html("Email不能为空!");
				    	checkFlag.email = false;//设置成未通过检查
				    	return;
				    }
				    //格式检查
				    var pattern=/\b(^['_A-Za-z0-9-]+(\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\.[A-Za-z0-9-]+)*((\.[A-Za-z0-9]{2,})|(\.[A-Za-z0-9]{2,}\.[A-Za-z0-9]{2,}))$)\b/;
				    if(!pattern.test(email)){
				    	$("#email\\.info").html("Email格式不正确!");
				    	checkFlag.email = false;//设置成未通过检查
				    	return;
				    }
				    checkFlag.email = true;//通过非空格式,设置为true
					
					$.post(
						"/dang/user/validEmail.action",
						{"email":email},
						function(ok){
							
						     if(ok){
						     	$("#email\\.info").html("Email可用!");
						     	checkFlag.emailajax = true;//设置成通过检查
						     }else{
						     	$("#email\\.info").html("Email已被占用!");
						     	checkFlag.emailajax = false;//设置成未通过检查
						     }
						}
					);
				});
				
				//页面提交
				$("#f").submit(function(){
					if(checkFlag.nickname
					 &&checkFlag.emailajax){
					    return true;//允许提交
					}else{
						alert("表单数据输入有误,请检查...");
						return false;//阻止提交
					}
				});
				
			});
			//名字验证
			$(function(){
				$("#txtNickName").blur(function(){
					var length = $("#txtNickName").val().length;
					 //非空检查
				    if($("#txtNickName").val() == ""){
				    	$("#name\\.info").html("姓名不能为空!");
				    	checkFlag.email = false;//设置成未通过检查
				    	return;
				    }
					if (length>=4&&length<=20) {
						$("#name\\.info").html("用户名正确");
						checkFlag.nickname = true;
					}else {
						$("#name\\.info").html("用户名格式错误");
					}
				});
				$("#f").submit(function(){
					if(checkFlag.nickname
					){
					    return true;//允许提交
					}else{
						alert("表单数据输入有误,请检查...");
						return false;//阻止提交
					}
				});
				
				});
			
			//密码验证
			$(function(){
				$("#txtPassword").blur(function(){
					var checkpass = /[a-zA-Z0-9]{6,20}/;
					var password = $("#txtPassword").val();
					if(password == ""){
				    	$("#password\\.info").html("密码不能为空!");
				    	checkFlag.password = false;//设置成未通过检查
				    	return;
				    }
					if (checkpass.test(password)) {
				    	checkFlag.password = true;
						$("#password\\.info").html("密码格式正确");
					} else {
						$("#password\\.info").html("密码格式错误");
					}
				});
				
				
			});
			//重新输入的密码与第一次的对比
			$(function(){
				$("#txtRepeatPass").blur(function(){
				var password = $("#txtPassword").val();
				var password1 = $("#txtRepeatPass").val();
				if(password1 == ""){
			    	$("#password1\\.info").html("密码不能为空!");
			    	checkFlag.repassword = false;//设置成未通过检查
			    	return;
			    }
				if (password==password1) {
					$("#password1\\.info").html("密码正确");
			    	checkFlag.repassword = true;

				} else {
					$("#password1\\.info").html("两次输入的密码不匹配，请重新输入");

				}
			});
			});
			
			
			$(function(){
				$("#txtVerifyCode").blur(function(){
				var val = $("#txtVerifyCode").val();
					$.post(
						"checkcode.action",
						{"code":val},
						function(checkok){
							if (checkok) {
								$("#number\\.info").html("验证码正确！");
								checkFlag.code = true;
							}else{
								$("#number\\.info").html("验证码错误！");
								
							}
							});
				});
				$("#f").submit(function(){
					if(checkFlag.code
					){
					    return true;//允许提交
					}else{
						alert("表单数据输入有误,请检查...");
						return false;//阻止提交
					}
				});
			
			});	
//			$(function(){
//				//电话验证
//				$("#txtphone").blur(function(){
//					var phone = $("#phone").val();
//					//非空检查
//					if (phone == "") {
//						$("#phone\\.info").html("电话不能为空！");
//						return;
//					}
//				//	var pattern = /^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/;
//					var pattern = /^\d{11}$/;
//					if (!pattern.test(phone)) {
//						$("#phone\\.info").html("电话格式不对！");
//						return;
//					}else {
//						$("#phone\\.info").html("");
//						check.phone = true;
//						return;
//					}
//					
//				});
//			});
			$(function(){
				//手机验证
				$("#mobile").blur(function(){
					var mobile = $("#mobile").val();
					//非空检查
					if (mobile == "") {
						$("#mobile\\.info").html("手机不能为空！");
						return;
					}
					var pattern = /^\d{11}$/;
					if (!pattern.test(mobile)) {
						$("#mobile\\.info").html("手机格式不对！");
						return;
					}else {
						$("#mobile\\.info").html("");
						check.mobile = true;
						return;
					}
					
				});
				
				
			});
			$(function(){
				//地址检查
				$("#fullAddress").blur(function(){
					var address = $("#fullAddress").val();
					//非空检查
					if (address == "") {
						$("#fullAddress\\.info").html("地址不能为空！");
						return;
					}
					$("#fullAddress\\.info").html("");
					check.fullAddress = true;
				});
			});