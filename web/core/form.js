;(function($){
	
	$.F = $.form =  {
		options:{
			formParamURL:$.global.functionDefinitionUrl+"?type=F",
			dataBindingURL:"common_getBindingData.action",
			inputDataURL:"common_bindInputData.action"
		},
		
		list:{},
		validators:{},
		
		runInstance: function( funcNo,options ){
			var op = $.extend({
						autoOpen:	 true,
						formComplete:function(){},
						target:		 "main"
						},options);
			if(this.list[funcNo] == null){
				$.ajax({
					type: "POST",
					url:  $.form.options.formParamURL,
					data: {funcNo:funcNo},
					dataType: "json",
					success: function( data,textStatus ){
						var formDef = data[0];
						$.form.list[funcNo] = formDef;
						$.userContext.appendDataType(formDef.typeMap);
						$.form.createNew( formDef,op.target );
						op.formComplete();
						$("#form" + funcNo).show("drop",{},500);
					},
					error:function(e){
						$.msgbox.show("err","配置错误：可能某个非String类型字段取了空值，导致格式json错误：<br><br>"+e.responseText);
					}
				});
			}else{
				var formDef = this.list[funcNo];
				this.createNew( formDef,  op.target );
				op.formComplete();
				$("#form" + funcNo).show("drop",{},500);
			}
			return this;
		},
		
		createNew: function( formDef, target ){
			
			var formid = "form" + formDef.funcno;

			var formHtml = this.drawNew(formDef);
			$( "#"+target ).append( $(formHtml) );

			var formFields = formDef.formFields;
			this.raplaceFieldMarks( formDef.funcno, formFields );

			var rules =  this.getValidateRules(formid, formFields);
			this.validators[formDef.funcno] 
			     = $( "#" + formid ).validate({rules: rules});
			
			this.initFormData(formDef.funcno);
			this.getDataToContext(formDef.funcno);
		},
		
		drawNew:function( formDef ){
			var formTable = "";
		
			var body = formDef.formhtml;
			if(body == ""){	
				body = "<table cellspacing='0'>";
				var fields = formDef.formFields;
				for( var i = 0; i < fields.length; i++ ){
						body+="<tr><td style='width:100px'>"+fields[i].label+"：</td><td style='width:250px'>#"+fields[i].fieldname+"#</td></tr>";
				}
				body +="</table>";
			}
			body = '<div id="b_form'+formDef.funcno+'" class="form-body">' + body + '</div>';
			
			var formHtml = "<form id='form" + formDef.funcno + "'>"
						 + "<div style='overflow:auto;min-width:350px;width:"+formDef.width+"px;height:"+formDef.height+"px' class='form'>"
						 + body + "</div></form>";
			return formHtml;
		},
		
		/*按照表单内的标号替换相关内容
		 * 标号的形式为 #number#,含有标号的地方将被替换成相应的 input
		 * @param funcno:待替换的表单功能号
		 * @param formFields：将替换成的各个字段的描述，格式为json数组
		 */ 
		raplaceFieldMarks: function(funcno,formFields){
			var formid = "form" + funcno;
			var funcMap = $.form.list[funcno].funcMap;
			//逐一替换标记字段
			for( var i = 0; i < formFields.length; i++ ){
				//获得字段定义细节
				var f = formFields[i];
				//该input的id号，生成格式为：‘表单号_字段名’
				var fid = formid + "_" + f.id;
				//获得替换位置的jquey对象
				var $markedField = this.findMark(formid, f.fieldname);	
				//清空内部标记
				$markedField.empty();										
				//绑定input类型
				this.bindingInput(f.inputtype, f.binding_data, fid, f.align,$markedField,funcno);
				this.bindingTrigger(funcno, fid, f.inputtype, f.trig, funcMap);
			};
		},
		
		findMark: function(formid,postion){
			return $("#" + formid + " table td:contains(#" + postion + "#):first");
		},
		
		/*绑定输入类型
		 *@param inputtype: 要绑定的输入类型
		 *@param bindData: 要绑定在输入控件中的数据
		 *@param id: 赋给该输入控件的 id 号
		 *@param align: 编辑位置
		 *@param $target:输入控件要绑定到的目标
		 **/
		bindingInput:function ( inputtype, bindData, id , align, $target, funcno){
			if( typeof($.form.input[inputtype]) == 'function')
				return $.form.input[inputtype](id, bindData, $target, align,funcno); 
			else 
				return "<a style='color:red'> error type:"+inputtype+"</a>";
		},
		
		
		/*输入类型*/
		input:{
			//文本框
			text:function(id,bindData,$target,align,funcno){
				var $input = $("<input id='"+id+"'  name='"+id+"' type='text' style='text-align:"+align+";border:none;width:99%;height:"+$target.height()+"px;'/>");
				
				$input.keyup(function(){
					$.form.getDataToContext(funcno);
					$.page.triggerBy(funcno);
				});
				$target.append($input);
			},
			//小图片
			img :function(id, bindData,$target,funcno){
				var $imgUploader = $("<input id='"+id+"' name='"+id+"' type='file'/>")
				$target.append($imgUploader);
				$imgUploader.uploader({fileDesc : '支持格式:jpg/gif/jpeg/png/bmp.',
					fileExt	 : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
					multi:true});
			},
			//日期 
			date:function(id, bindData,$target,funcno){
				var $dateInput = $("<input id='"+id+"' name='"+id+"' type='text' dateInput='true'  style='border:none;width:"+$target.width()+"px;height:"+$target.height()+"px;' class='dateInput' readonly='readonly'/>");
				$target.append($dateInput);
				$dateInput.datepicker({changeYear:true,changeMonth:true,duration:"fast"});
			},
			//编辑框
			textarea:function(id, bindData,$target,funcno){
				$target.append("<textarea id='"+id+"' name='"+id+"'  style='border:none;width:"+$target.width()+"px;;height:"+$target.height()+"px;'/>");
			},
			//勾选框
			checkbox:function(id, bindData,$target,funcno){
				var $checkbox = $("<input id='"+id+"' name='"+id+"' value='F' type='checkbox'/>");
				$target.append($checkbox)
			},
			//密码框
			password:function(id,  bindData,$target,funcno){
				$target.append("<input id='"+id+"' name='"+id+"' type='password' style='width:"+$target.width()+"px;height:"+$target.height()+"px;'/>");
			},
			//图片捕获
			capturer:function(id, bindData,$target,funcno){
				$target.append("<div id="+id+"></div>");
				$("#"+id).capturer({});
			},
			//大文档
			bigdoc:function(id, bindData,$target,funcno){
				$("<div id="+id+"></div>")
				.appendTo($target)
				.ftpFileUploader({}); 
			},
			//下拉框 
			select:function(id, bindData,$target,align,funcno){
				//如果绑定的数据是以‘@’开头表示是一条 SQL 语句，需要请求服务器查询得到键值对。
				var $select = $("<select id='"+id+"' name='"+id+"' style='float:"+align+"'></select>");
				$target.append($select);
				var getOptions = function(bindData){
					var options="";
					var kvs=bindData.split(";");
					for(var i=0;i<kvs.length;i++){
						var kv=kvs[i].split(":");
						options+="<option value='"+kv[0]+"'>"+kv[1]+"</option>";
					}
					return options;
				}
				if(bindData.substring(0,1) == "@")
					$.ajax({
						type:"POST",
						url:$.form.options.dataBindingURL,
						data:{sql:$.userContext.parser(bindData.substring(1))},
						dataType:"text",
						success:function(data,textStatus){
							$select.append(getOptions(data));
						}	
					});
					
				else//否则直接绑定
					$select.append(getOptions(bindData));
				
			}
			//序列
			/*sequence:function(id,bindData,$target){
				$target.append("<input readonly='readonly' id='"+id+"' name='"+id+"' type='text' style=\"border:none;color:red;font-weight:bold;width:"+$target.css("width")+";height:"+$target.css("height")+"\" value='自动生成'/>");
			},*/
			/*
			"function":function(id,bindData,$target,align,funcno){
				$target.append("<input id='"+id+"'  name='"+id+"' type='text' style='text-align:"+align+";border:none;width:"+$target.width()+"px;height:"+$target.height()+"px;'/>");
			}*/
			
		},
		
		bindingTrigger:function(funcno, id, type, trigStr, funcMap){
			if( trigStr == "" )
				return;
			var formid = "form"+funcno;
			var $input = $( "#" + id );
			var trigList = trigStr.split(',');
			var trigCompute = function(){
				//若表单有错误 则不触发function
				if(!$.form.validators[funcno].element($input)){
					return;
				}
				
				$.form.getDataToContext( funcno );
				for( var i=0; i<trigList.length; i++){
					var fname = trigList[i];
					var fid = formid + "_" + fname.replace('.','-')
					var func = funcMap[ fname ];
					$.form.bindInputData($("#"+fid) , func );
				}
			}
			if( type.match(/select|checkbox/)){
				$input.change(trigCompute);
			}else
				$input.blur(trigCompute);
			
		},
	
		getValidateRules: function(formid,formFields){
			var rules = {};
			var c = formFields.length;
			for(var i = 0;i<c; i++){
				rules[formid + "_" + formFields[i].id] =
				this.getValidateMode(formFields[i].nullable,  //允许为空
										  formFields[i].maxlen, 			//最大长度
										  formFields[i].minlen, 			//最小长度
										  formFields[i].maxval, 			//最大值
										  formFields[i].minval, 			//最小值
										  formFields[i].format				//格式
							)
			}
			
			return rules;
		},
		
		getValidateMode: function(nullable,maxlen,minlen,maxval,minval,format){
			var validateMode = {}; 
			if( (nullable == false) ){
				validateMode.required = true ;
			}
			
			if( format && (format!="")  )
				if(format.charAt(0) == "@")
					validateMode["remote"] = "common_validateData.action?funcName="+format.substring(1);
				else 
					validateMode[format] = true;
			
			if( maxlen && (maxlen != "") )
				validateMode["maxlength"] = parseFloat(maxlen);
			else if( maxval && (maxval!="") )
				validateMode["max"] = parseFloat(maxval);
			
			
			if( minlen && (minlen!="") )
				validateMode["minlength"] = parseInt(minlen);
			else if( (minval!=null) && (minval!="") )
				validateMode["min"] = parseInt(minval);
			
			return validateMode;
		},
		
		getFormData:function( funcno ){
			var formDef = $.form.list[funcno];
			var fields = formDef.formFields;
			var formid = "form" + formDef.funcno;
			var len = fields.length;
			var data = {};
			for(var i=0;i<len;i++){
				var f = fields[i];
				var val = "";
				val = this.getInputData(formid+"_"+f.id, f.inputtype ,f.binding_data)
				var varname = funcno+"-"+f.fieldname;
				data[varname.toUpperCase()] = val;
			}
			return data;
		},
		
		getInputData:function( id, type,bindData){
			if(type == "img" || type == "file"){
				return $("#"+id).getFileNames();
				
			}else if(type == "capturer"){
				return $("#"+id).getCaputuredImgs();
				
			}else if(type == "bigdoc"){
				return $("#"+id).getUploadedFile();
				
			}else if(type == "checkbox"){
				
				var k = bindData.split(":");
				return $("#"+id).attr("checked")?k[0]:k[1];
				
			}else{
				return $("#"+id).val();
			}
		},
		
		setInputData:function( id, type, val, bindData){
			
			if(type == "img" || type == "file")
				$("#"+id).readFiles(val);
				
			else if(type == "capturer")
				$("#"+id).setCaputuredImgs(val);
				
			else if(type == "checkbox")
				if(val == bindData.split(":")[0]){
					
					$("#"+id).attr("checked",true);
				}else{
					$("#"+id).attr("checked",false);
				}
			
			else if(type == "bigdoc")
				$("#"+id).setUploadedFile( val );
			
			else
				$("#"+id).val( val );
			
		},
		initFormData:function(funcno){ 
			var formDef = $.form.list[funcno];
			var flds = formDef.formFields;
			var afterBindSQLData = function(oneData){
				//第一步 绑定sql数据
				for(var i=0;i<flds.length;i++){
					var f = flds[i];
					var fname = f.fieldname.split(".")[1].toUpperCase();
					var val = oneData[fname];
					$.form.setInputData("form"+funcno+"_"+f.id,f.inputtype,val?val:"",f.binding_data);
				}
				//第二步绑定变量数据 
				$.each(flds,function(i){
					var f = flds[i];
					if(f.initdata != "") {
						var val = $.userContext.parser(f.initdata);
						
						if( val || (val==0) ){
							if((val.length==1)&&(escape(val).match("%A0")))
								val = "";//空字符
							$.form.setInputData("form"+funcno+"_"+f.id,f.inputtype,val,f.binding_data);
						}
					}else{
						//$.form.setInputData("form"+funcno+"_"+f.id,f.inputtype,"",f.binding_data);
					}	
				});
				
				$.form.setReadOnlyFields(funcno);
				
				//第三步计算各字段值
				$.form.getDataToContext( funcno );
				var funcMap = $.form.list[funcno].funcMap;
				for(var i=0;i<flds.length;i++){
					var f = flds[i];
					$.form.bindInputData($("#form"+funcno+"_"+f.id),funcMap[f.fieldname]);
				};
			}
			var sql = formDef.bindSql;
			if(sql && (sql != "")){
				$.getDataBySQL($.userContext.parser( sql ),afterBindSQLData);
			}else{ 
				afterBindSQLData({});
			}
			
		},
		
		getDataToContext:function( funcno ){
			var formData = this.getFormData(funcno);
			$.each(formData,function(i){
				$.userContext.userData[i]=formData[i];
			});
		},
		
		setReadOnlyFields:function(funcno){
			var formDef = this.list[funcno];
			var flds = formDef.formFields;
			$.each(flds,function(i){
				var f = flds[i];
				if(f.readonly){
					$.form.setInputReadOnly("form"+funcno+"_"+f.id,f.inputtype);
				}
			});
		},
		
		setInputReadOnly:function(id,type){
			if(type == "img" || type == "file"){
				var $p = $("#"+id).parent();
				$p.find(">object").remove();
				$p.find("ul>li>a").remove();
				
			}else if(type == "capturer")
				$("#"+id+">button").remove();
			
			else if(type == "bigdoc"){
				$("#"+id).find(">input").remove();
				var $span = $("#"+id).find(">span");
				var link = $span.attr("link");
				var name = $span.text();
				if(name && (name != "")){
					$span.html("<a href='"+link+"' style='color:#336699'><span class='ui-icon ui-icon-tag' style='float:left'></span>"+name+"</a>");
				}
			}else 
				$("#"+id).attr("readonly",true).css("color","#E82030");
			
		},
		
		refresh:function(funcno, filter){
			$.form.setReadOnlyFields(funcno);
			$.form.initFormData(funcno);
			
		},
		
		bindInputData:function($input, func){
			if(func == "")
				return; 
			//可编辑框 有值不送
			if(!$input.attr("readonly") && ($input.val()!="") )
				return;
			
			//@ 表示变量表达式  只需要本地计算
			if(func.charAt(0) == '@'){
				$input.val(caculator.caculate($.userContext.parser(func.substring(1)),true));
				return;
				
			}else{
				var funcName = func.split(":")[0];
				var paramsStr = func.split(":")[1];
				var params = this.parseParamsToData( paramsStr );
				var funcStr = '{name:"'+funcName+'",params:'+params+'}';
				$.ajax({
					type:"POST",
					data:{funcStr:funcStr},
					datatype:"text",
					url:$.form.options.inputDataURL,
					success:function( data ){
						if(data == 'error'){
							$input.val( "" );
						}else{
							$input.val( data );
						}
					},
					error:function( e ){
						$input.val( "" )//$.msgbox.show("err",$input.attr("id")+"在绑定值时发生错误:"+func);
					}
				});
			}
		},
		
		/*
		 * 将调用参数字符串转解析成命令字符串
		 * 参数字符串：#param1#;#param2#;#param3#...
		 * 转换后 [{name:n1,type:t1,value:val1},{...},...]
		 * * */
		parseParamsToData:function(paramsStr){ 
			var params = (paramsStr+"").split(";");
			
			var len = params.length;
			
			var dataList="[";
			
			for(var i=0;i<len;i++)
				dataList += $.userContext.getData(params[i])+(i<len-1?",":"");
			
			dataList += "]";
			return dataList;
		}
	};
	
})(jQuery);