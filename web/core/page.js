/**************
 * page.js的职责是
 * 1.生成所属页面的所有窗口及其按钮，并进行定义管理
 * 2.接收窗口内部默认事件，并触发执行相关动作
 * 3.维护按钮事件动作机制
 * 
 * v 1.0
 * @author zyz
 **************/
;(function($){
	$.page={
			
			// 常量配置
			options: {
				pageParamURL:$.global.functionDefinitionUrl+"?type=W",
				eventURL:"commonUpdate_responseButtonEvent.action"
			},
			
			//页面定义缓存，存放每次请求后的页面描述参数对象 
			list:{},		
			
			//当前打开页面
			currPage: null,	
			
			// 打开页面函数
			open: function(mainWinno) {
				//若页面定义在缓存中不存在
				if(this.list[mainWinno] == null)
					$.ajax({ 
						type: "POST",
						url:  $.page.options.pageParamURL,
						data: {funcNo:mainWinno},
						dataType: "json",
						
						success: function( pageDef,textStatus ) {
							$.page.currPage = mainWinno
							$.page.list[mainWinno]=pageDef; 
							$.page.createPage(pageDef);
						},
						
						error:function(e){
							$.msgbox.show("err","请求显示的主窗口"+mainWinno+"不存在或存在定义错误：<br>"+e.responseText);
						}
						
					});
				else {
					this.currPage = mainWinno
					var pageDef = $.page.list[mainWinno];
					this.createPage(pageDef);
				}
			},
			
			//根据页面json定义创建该页面
			createPage: function( pageDef ) {
				var $page = 
					$( "<div id='page" + pageDef.mainwinno + "'></div>" );
				
				$("#main")
				.empty()
				.html($page);
				//创建属于该页面的每个窗口
				var winDefs = pageDef.wins;
				for( var i=0; i<winDefs.length; i++ ){
					$.page.createFuncWin( pageDef.mainwinno, winDefs[i] );
				}
			},
			
			// 创建功能窗口
			createFuncWin:function(pageno,winDef){
				
				// 定义窗口 id号
				var winid = "body_win"+winDef.winno;
				
				//画出该窗口
				this.drawWindow(pageno,winDef);
				
				//加载窗口功能, (窗口id，功能类型,		  功能序号）
				this.loadFunc(winid, winDef.func_type, winDef.funcno);
			},
			
			//绘制窗口
			drawWindow:function(pageno,winDef){
				var winid = "win" + winDef.winno;
				
				//绘制窗框
				var winHead = "";
				if(winDef.frame)
					winHead = "<h3 class='ui-widget-header ui-corner-all' style='text-align:center'>" 
								+ "<div  class='ui-dialog-title' style='padding:5px;'>"
								+ "<span style='float:left' class='ui-icon "+$.global.iconSet[winDef.func_type]+"'></span>"
								+ "<span id='"+winid+"_title'>"+$.userContext.parser(winDef.init_title,true)+"&nbsp;</span></div></h3>";
				
				//绘制窗口实体, 并挂在页面上
				var y = winDef.y + 90;
				var $win = $("<div id='"+winid+"' style='padding:1px;width:"+winDef.width+"px;position:absolute;left:"+winDef.x+"px;top:"+y+"px' class='ui-widget-content ui-corner-all'>"
								+	winHead 
								+	"<div id='body_"+winid+"'></div>"
								+	"</div>");
				$( "#page" + pageno ).append( $win );
				
				$( "#body_"+winid ).css({
					width:winDef.width,
					height:winDef.height,
					"overflow-x":"auto",
					"overflow-y":"hidden"
				});
				
				//初始时是否显示
				if( !winDef.isshow ){
					//$win.hide();
					$win.get(0).style.visibility= 'hidden';
					//this.act.hide(winDef.winno)
				}
				
				//是否可拖拽
				if( winDef.draggable ){
					$win.draggable({cancel:"#body_"+winid+",#foot_" + winid});
					$( "#"+winid + " h3" ).css("cursor","move");
				}
				
				//是否可拉伸
				if( winDef.resizable ){
					$( "#body_"+winid ).resizable({
						maxHeight:600,
						minHeight:80,
						maxWidth:1200,
						minWidth:120,
						alsoResize:"#"+winid
					});
				}
				
				//创建窗口按钮栏
				this.createBtnsBar( winDef.winno, winDef.func_btns );
			},
			
			//将某类型功能加载到指定窗口
			loadFunc: function(winid,type,funcno){
				var func = $[type];
				func.runInstance(funcno,{target:winid});
			},
			
			
			//创建功能按钮集，btns为待创建的按钮json对象数组
			createBtnsBar:function(winno,btns){
				
				var $win = $( "#win" + winno );
				
				// 创建盛放按钮的bar
				var barid = "foot_win" +  winno;
				var bar = $( '<div class="ui-widget-header ui-corner-all" id="'+barid+'" style="height:26px;" ></div>' );
				$win.append( bar );
				if( !btns || (btns.length == 0 ))
					return;
				$.each( btns,function(i) {
					//创建按钮 
					var btnDef = btns[i];
					$($.button.create(btnDef))
					.appendTo(bar)
					.css("float","left").css("margin","2px,5px auto")
					
					//绑定按钮事件,以下为按钮点击后要做的动作 核心步骤
					.click( function() {
						var currPage = $.page.currPage;
						var winFunc = $.page.list[currPage].funcMap[winno];
						
						if(winFunc.type == 'F')
							$.form.getDataToContext(winFunc.func);
						// 前置检查
						if(btnDef.pre_check)
							if(	!$.page.btn.pre_check(winFunc.func,winFunc.type) )
								return;
						
						//临时定义跳转动作函数过程,以方便函数内部复用
						var executeActs = function(){
							if(btnDef.acts){
								var toWin = $.page.act.routeActs(btnDef.acts);
								$.page.act.jump(winno,toWin);
							}
							if(btnDef.affects){
								var affects = $.page.act.routeAffects(btnDef.affects);
								$.page.act.affectsWins(affects);
							}
						}
						
						//调用存储过程
						if(btnDef.call_proc){
							$win.block({message:"<p class='ui-state-active'>请稍等...</p>",
								overlayCSS:{backgroundColor: '#0F4569', 
			        			opacity:         0.4 
							}});
							var cmd = $.page.btn.generateCmd(btnDef.check_func, btnDef.procs);
							$.page.btn.call_proc( cmd, function( msg ) {
								$win.unblock();
								var state = $.page.handleMsg( msg );
								if( state || !btnDef.post_check )
									executeActs();
							});
						}else
							executeActs();
					});//end for each
				});
			},
			
			
			handleMsg:function( msgStr ){
				if(msgStr == "success"){
					$.msgbox.show("succ","操作成功！");
					return true;
				}
				msgStr = msgStr.replace(/@/g,"<br><br>");
				msgStr = msgStr.replace(/.*\:/,"");
				$.msgbox.show("err",msgStr);
				return false;
		    },
			
			/*功能事件触发函数，该函数提供给各个功能调用
			* 当任何功能的默认事件发生时，会调用此函数，
			*  srcFuncno为默认事件发生的所属源功能号，
			* 根据该功能号，反向查找到要响应的动作，并执行 */
			triggerBy:function(srcFuncno){
				var actMap = $.page.list[this.currPage].actMap;
				var acts = actMap[srcFuncno];
				if( !acts || (acts == "") )
					return;
				var act = this.act.routeAffects(acts);
				$.page.act.affectsWins(act);
			},
			
			//动作执行函数定义，动作分为跳转和刷新（影响）两种
			act:{
				
				affectsWins:function( affectsStr ) {
					var affects = ( affectsStr + "" ).split(";");
					for( var i=0; i<affects.length; i++)
						this.refresh( affects[i] );
					
				},
				
				jump:function( fromWin, toWin ){
					
					
					var curr = $.page.currPage;
					if(toWin == '#'){
						$.msgbox.show("msg","未满足条件,无法进行下一步操作")
						return;
					}else if( toWin == "-1" ){
						$("main").empty();
						return;
					}
					//若当前页面中有待跳转的目的窗口
					if( $.page.list[curr].funcMap[toWin] ){
						this.hide(fromWin);
						this.show(toWin);
						this.refresh(toWin+"^$");
						
					//否则打开新的页面
					}else
						$.page.open(toWin);
				},
				
				refresh:function( affect ){
					if( affect == '#' ) 
						return;
					var winno = affect.split("^")[0];
					var filter = affect.split("^")[1];
					
					//获得该功能号对应的窗口定义细节
					var winfunc = $.page.list[$.page.currPage].funcMap[winno];
					
					if( !winfunc )
						return $.msgbox.show("err","该页面不包含要刷新的功能窗口:"+winno);
					
					var funcRefresh = $[winfunc.type].refresh;
					if( typeof funcRefresh == "function")
						funcRefresh( winfunc.func, filter );
					else
						return $.msgbox.show("err","未提供 刷新函数，无法刷新窗口:"+winno);
					
					// 刷新该窗口的标题
					$( "#win"+winno+"_title" ).html( $.userContext.parser(winfunc.title,true) );
				},
				
				//显示指定窗口
				show:function( winno ) {
					$("#win"+winno).get(0).style.visibility= 'visible';
				},
				
				//隐藏指定窗口
				hide:function( winno ) {
					$("#win"+winno).get(0).style.visibility= 'hidden';
					//$("#win"+winno).hide();
				},
				
				//路由条件跳转动作 acts 的格式参见数据库文档 功能按钮定义部分
				routeActs:function(acts){
					var actsStr = $.userContext.parser(acts);
					var actArray = actsStr.split("@");
					
					for(var i=0; i<actArray.length; i++){
						var act = actArray[i].split(":");
						if( condTester.ifCondition(act[0]) )
							return act[1];
					}
					
					return "#";
				},
				
				//路由刷新跳转动作 affects 格式参见文档 功能按钮定义部分
				routeAffects:function(affects){
					var affectsStr = $.userContext.parser(affects);
					var affectsArray = affectsStr.split("@");
					var rAffects = "";
					
					for(var i=0; i<affectsArray.length; i++){
						var act = affectsArray[i].split(":");
						if( condTester.ifCondition(act[0]) )
							rAffects += act[1] + ";";
					}
					
					return rAffects+"#";
				}
			},/*end for page.act*/
			
			//按钮后台调用相关定义
			btn:{
				//按钮点击前可能要调用该函数进行前置检验，以决定是否要进行下一步动作
				pre_check:function(funcno,type){
					switch( type ){
						case 'Q':
							if(!$.grid.lastRows[funcno]){
								$.msgbox.show("msg","请先选择记录");
								return false;
							}else return true;
						case 'F':
							if(!$.form.validators[funcno].form()){
								return false;
							}else return true;
						case 'T':
							if(!$.tree.lastLevels[funcno]){
								$.msgbox.show("msg","请先选择菜单项");
								return false;
							}else return true;
						case 'D':
							if(!$.flashchart.lastRows[funcno]){
								$.msgbox.show("msg","请先选择图中坐标");
								return false;
							}
							return true;
						case 'C':
							if(!$.customWin.ready[funcno]){
								$.msgbox.show("msg",$.customWin.tip[funcno]);
								return false
							}
							return true; 
						default:
							return true;
					}
				},
				
				call_proc:function(cmd,callBack){
					$.ajax({
						type:"POST",
						url:$.page.options.eventURL,
						data:{cmd:cmd},
						dataType:"text",
						success:function(msg,textStatus){
							callBack(msg);
						}
					});
				},
				
				/*根据需要调用的function 和procs 生成本次后台要执行的指令
				 *  该指令是与后台UpdateCommand类对应的json对象字符串
				 */
				generateCmd:function(func,procs){
					
					var cmd = "{";
					var i = 0;
					try{
						if((func == "@" )||(func == "")||(func == "escape"))
							cmd += 'check_func:"escape",';
						
						else if(func.charAt(0)=="@")
							cmd += 'check_func:"' + $.userContext.parser(func)+'",';
						
						else{
							var funcCall =  func.split(":");
							cmd += 'check_func:"' + funcCall[0]+'",';
							cmd += 'check_data:' + this.parseParamsToData(funcCall[1])+',';
						}
						
						for(;i<procs.length; i++) {
							if( procs[i].charAt(0) == "@")
								cmd += 'proc'+i+':"'+$.userContext.parser(procs[i])+'"';
							
							else{
								var procCall =  procs[i].split(":");
								cmd += 'proc'+i+':"'+procCall[0]+'",';
								cmd += 'data'+i+':'+this.parseParamsToData(procCall[1]);
							}
							cmd += (i == procs.length-1)?"":",";
						}
					}catch(e){
						$.msgbox.show("err","待执行的存储过程指令中存在错误:<br><br>check_func:"+func+"<br><br>proc:"+procs[i]);
						throw e;
					}
					
					cmd +="}";
					return cmd;
				},
				/*
				 * 将调用参数字符串转解析成命令字符串
				 * 参数字符串：#param1#,#param2#,#param3#...
				 * 转换后 [{name:n1,type:t1,value:val1},{...},...]
				 * * */
				parseParamsToData:function(paramsStr){
					if(paramsStr == "")
						alert("存储过程或函数必须有参数");
					var params = (paramsStr+"").split(";");
					var len = params.length;
					
					var dataList="[";
					
					for(var i=0;i<len;i++)
						dataList += $.userContext.getData(params[i])+(i<len-1?",":"");
					
					dataList += "]";
					return dataList;
				}
			}/*end for page.btn*/
			
	}/*end for page*/
	
})(jQuery);
