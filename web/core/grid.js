/**文件名：jquery.grid.js
 *描述：该文件包含对jqgrid相关的按钮标签、消息参数、事件响应函数等的定义实现。
 *依赖:jquery-1.3.2.js;ui.core.js;ui.dialog.js;userContext.js;
 *@author:zyz
 *-----------------------------------------------------------------**/
;(function($){
	$.Q = $.grid = {
			opitions:{
				gridParamURL:  $.global.functionDefinitionUrl+"?type=Q",
				doQueryURL:	 "commonQuery_doQuery.action",
				updateURL:"commonUpdate_responseButtonEvent.action"
			},
			
			list:{/*{funcno:gridDef}*/},
			lastRows:{/*{funcno:lastRowId}*/},
			
			/**
			 * gird运行实例函数
			 * @funcNo 要创建的grid功能编号
			 * @model	grid的生成模式：可以为'indep','grud','step'
			 * @autoOpen grid创建完毕后是否自动打开
			 * @formComplete 创建完毕后回调函数
			 * **/
			runInstance: function( funcNo,options ){
				var op = $.extend({
					autoOpen:	 true,			//创建表单完毕后是否自动打开
					gridComplete:function(){},			//创建完毕后要回调处理的函数
					target:		 "main"			//表单所属的区域
					},options);
				
				if($.grid.list[funcNo] == null){
					$.ajax({
						type: "POST",
						url:  $.grid.opitions.gridParamURL,
						data: {funcNo: funcNo},
						dataType: "json",
						success:  function( data,textStatus ){
							var gridDef = data[0];
							$.grid.list[funcNo] = gridDef
							$.userContext.appendDataType(gridDef.typeMap);
							$.grid.createNew( gridDef,op.target);
							op.gridComplete();
						},
						error:function(e){
							//$.msgbox.show("err","请求显示的功能"+funcNo+"不存在或存在定义错误：<br>"+e.responseText);
						}
					});
				}else{
					var gridDef = $.grid.list[funcNo];
					$.grid.createNew(gridDef,op.target);
					op.gridComplete();
				}
				$.grid.lastRows[funcNo] = null;
				return $("#div_grid"+funcNo);
			},
			
			createNew: function (gridDef,target){
				var grid_id = "grid" + gridDef.funcno;
				var grid_divId = "div_" + grid_id;
				var pager_id = "pager" + gridDef.funcno;
				
				//添加一个新的grid
				$("#"+target).append($("<div id=\""+grid_divId+"\" style='float:left'><table  id=\""+grid_id+"\" ></table><div id=\""+pager_id+"\"></div></div>"));
				
				//配置grid各基本属性
				$.grid.setProperties(grid_id,pager_id,gridDef);
				
				//调用jqGrid生成
				$("#"+grid_id).jqGrid(gridDef);
				
				//配置grid工具栏
				$.grid.setToolbar(gridDef.funcno,gridDef);
				
				//添加搜索面板
				if(gridDef.panelSearch == 'show'){
					var $sp = $.grid.createSearchPanel(gridDef.funcno,gridDef.colModel,gridDef.colNames);
					var $btn = $($.button.create(
						{id: grid_id+"_query",
	 					caption:"查询", 
	 					icon:"ui-icon-search", 
	 					pos:"left",
	 					script:"$.grid.doQuery("+gridDef.funcno+")"
					}));
					$sp.find(">table>tbody>tr:last>td").prepend($btn);
					$sp.show();
				};
				
				//隐藏列
				$.grid.setHiddenCols(grid_id,gridDef.colModel);
			},
			
			setHiddenCols:function(grid_id,colModel){
				var cols = [];
				$.each(colModel,function(i){
					if(!colModel[i].isshow){
						cols.push(colModel[i].name);
					}
				})
				$("#"+grid_id).jqGrid('hideCol',cols); 
			},
			
			setProperties:function(grid_id,pager_id,gridDef){
				
				gridDef.url = $.grid.opitions.doQueryURL+"?funcno=" + gridDef.funcno ;
				
				
				gridDef.loadBeforeSend = function(xhr){
					
				}
				gridDef.pager = $("#"+pager_id);
				
				gridDef.gridComplete =function(){
					$.grid.lastRows[gridDef.funcno]=null;
					if(gridDef.stripe){
						$("#"+grid_id+">tbody>tr:odd>td").addClass("stripe-grid");
					}
				};
				
				gridDef.loadError = function(xhr,st,err) {
			    	//$.msgbox.show("err","grid 在加载数据时发生错误，可能是sql拼写有错误");
			    };
			    
				gridDef.onSelectRow = function(rowid){
					$.grid.fn.getDataToContext(gridDef.funcno,rowid);
					
					if(gridDef.editable){
						$.grid.fn.editRow(gridDef.funcno,rowid,gridDef.editCond);
					}else{
						$.page.triggerBy(gridDef.funcno);
						if(gridDef.multiselect){
							$.grid.fn.getMultiRowsToContext(gridDef.funcno,gridDef.multifields);
						}
					}
					$.grid.lastRows[gridDef.funcno] = rowid;
				};
				
				gridDef.onSelectAll = function(rowids,status){
					if(gridDef.multiselect){
						$.grid.fn.getMultiRowsToContext(gridDef.funcno,gridDef.multifields);
					}
				};
				
				gridDef.beforeRequest = function(){
					
					$("#"+grid_id).jqGrid("appendPostData",{
						prjfields:$.userContext.parser(gridDef.prjfields),
						tablenames:$.userContext.parser(gridDef.tablenames),
						joinconditions:$.userContext.parser(gridDef.joinconditions),
						sql:$.userContext.parser(gridDef.sql)
					});
				};
				
				gridDef.onSortCol = function(){
					$("#"+grid_id).jqGrid("removePostDataItem","ordStr");
				};
				
				$("#"+grid_id).jqGrid("sortableRows");
			},
			
			setToolbar:function(funcno,toolbar){
				if(!toolbar.toolbar[0]){
					$("#t_"+grid_id).hide();
					return;
				}
				var grid_id = "grid"+funcno
				var layout = toolbar.toolbarlayout;
				$("#t_"+grid_id).empty()	
			 	var btnOptions;
			 	//添加选择列按钮
			 	if(toolbar.selcol){
			 		btnOptions={id:grid_id + "_selcol",
								caption:"选择列", 
								icon:"ui-icon-transferthick-e-w", 
								pos:layout
					}
			 		$("#t_" + grid_id).append( $.button.createIcon(btnOptions));
			 	 	$("#" + grid_id + "_selcol").click(function(){
			 	 		$("#" + grid_id).jqGrid('setColumns');
			 	 		
			 	 	})
			 	 }
			 	//添加排序按钮
			 	 if(toolbar.sortable){
			 		btnOptions={id:grid_id + "_sort",
			 					caption:"排序", 
			 					icon:"ui-icon-arrowthick-2-n-s", 
			 					pos:layout
			 		};
			 		$.grid.createSortDailog(funcno);
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#"+grid_id+"_sort").click(function(){$.grid.fn.openSortDialog(funcno)});
			 	 }
			 	 //添加刷新按钮
			 	if(toolbar.refreshable){
			 		btnOptions={id:grid_id + "_refresh",
		 					caption:"刷新", 
		 					icon:"ui-icon-refresh", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#" + grid_id + "_refresh").click(function(){
			 	 		$("#" + grid_id).trigger('reloadGrid');
			 	 	})
			 	 }
			 	 //添加重载数据按钮
			 	if(toolbar.reloadable){
			 		btnOptions={id:grid_id + "_reload",
		 					caption:"重载", 
		 					icon:"ui-icon-arrowthickstop-1-s", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#" + grid_id + "_reload").click(function(){
			 	 		$("#" + grid_id).jqGrid("setPostData",{});
			 	 		$("#" + grid_id).trigger('reloadGrid');
			 	 	})
			 	 }
			 	
			 	 //添加打印按钮
			 	 if(toolbar.print){
			 		btnOptions={id:grid_id + "_print",
		 					caption:"打印", 
		 					icon:"ui-icon-print", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#"+grid_id+"_print").click(function(){
			 	 			$.grid.printGrid( funcno );
			 	 	});
			 	 }
			 	 
			 	 //添加查询按钮
			 	 if(toolbar.searchable){
			 		btnOptions={id:grid_id + "_search",
		 					caption:"搜索", 
		 					icon:"ui-icon-search", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#"+grid_id+"_search").click(function(){
			 	 		$("#" + grid_id).jqGrid('searchGrid',
			 	 			{sopt:['cn','bw','eq','ne','lt' ,'gt','ew'],multipleSearch:true,closeAfterSearch:true}
			 	 		);
			 	 	});
			 	 }
			 	
			 	//添加查询按钮
			 	 if(toolbar.panelSearch == 'imbed'){
			 		btnOptions={id:grid_id + "_query",
		 					caption:"查询", 
		 					icon:"ui-icon-zoomin", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#"+grid_id + "_query").click(function(){
			 	 		$.grid.openSearchPanel(funcno);
			 	 	});
			 	 }
			 		 
			 	//添加添加按钮
			 	 if(toolbar.addable){
			 		btnOptions={id:grid_id + "_add",
		 					caption:"添加", 
		 					icon:"ui-icon-plus", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#"+grid_id+"_add").click(function(){
			 	 		$.grid.fn.addRow(funcno);
			 	 	});
			 	 }
			 	//添加删除按钮
			 	 if(toolbar.delable){
			 		btnOptions={id:grid_id + "_del",
		 					caption:"删除", 
		 					icon:"ui-icon-trash", 
		 					pos:layout
			 		};
			 	 	$("#t_"+grid_id).append($.button.createIcon(btnOptions));
			 	 	$("#"+grid_id+"_del").click(function(){
			 	 		var rowid = $.grid.lastRows[funcno];
			 	 		if(rowid!=null)
			 	 			$.grid.fn.delRow(funcno,rowid);
			 	 		else
			 	 			$.msgbox.show("msg","请选择一条记录");
			 	 	});
			 	 }
			 	 
			 	 $("#t_"+grid_id).css({height:"26px"});

			}, 
			
			printGrid:function( funcno ){
				var gridDef = $.grid.list[ funcno ];
				var colNames = gridDef.colNames;
				var typeMap = gridDef.typeMap;
				var columns = [];
				var types = [];
				$.each(typeMap,function( i ){
					columns.push(((i+"").split(".")[1]+"").toUpperCase());
					types.push( typeMap[i] );
				});
				//call print function;
				printGrid(colNames,columns,types,funcno);
				
			},
			
			createSortDailog:function( funcno ){
				var gridDef = $.grid.list[funcno];
				var grid_id = "grid"+funcno;
				var colNames = gridDef.colNames;
				var colModel = gridDef.colModel;
				var zIndex =$("#"+grid_id).css("zIndex");
				if(zIndex){
					zIndex = zIndex + 1;
				}else{
					zIndex = 1000;
				}
				var head ='<h3 class="ui-widget-header ui-corner-all">'
						 +'<div class="ui-dialog-title">高级排序</div>'
						 + '</h3>';
				var sortDialog = '<div id="'+grid_id+'_sortDlg" style="left:0px;top:30px;position:absolute;z-index:'+zIndex+';display:none;width: 370px;" class="form ui-widget-content ui-corner-all">'+head
							   + '<div class="form-body" >'
							   + '<div style="float:left" class = "ui-state-default"><span style="padding:4px;">备选字段</span>'
							   + '<ul id="'+grid_id+'sortable1" class="connectedSortable sort-ul">'
				for(var i=0;i<colModel.length;i++){	    
					sortDialog +='<li title="按鼠标左键拖拽至右边" class="ui-state-default">'
								+'<span style="float:left">'+colNames[i]+'</span>'
								+'<div title="点击改变次序" class="order" style="float:right;cursor:pointer" name="'+colModel[i].name+'" order="asc">'
								+'<span style="float:left;margin:-2px" class="ui-icon  ui-icon-arrowthick-1-n"></span><span>升序</span>'
								+'</div></li>';
				}
				sortDialog += '</ul></div>'
							+ '<span style="float:left" class="ui-icon ui-icon-transferthick-e-w"></span>'
							+ '<div style="float:left" class = "ui-state-active"><span style="padding:4px;">排序字段</span>'
					 		+ '<ul  id="'+grid_id+'sortable2" class="connectedSortable sort-ul "></ul>'
					 		+ '</div>'
					 		+'</div>'
					 		+'<div class="form-foot">'
					 		+ $.button.create({
								caption:"排    序 ",
								icon:"ui-icon-arrowthick-2-n-s",
								script:"$.grid.fn.sortGrid("+funcno+")"
					 			})
					 		+ $.button.create({
								caption:"关    闭",
								icon:"ui-icon-close",
								script:"$.grid.fn.closeSortDialog("+funcno+")"
					 			})
					 		+'</div></div>';
				
				$(sortDialog).appendTo($("#div_"+grid_id));
				$("#"+grid_id+"sortable1, #"+grid_id+"sortable2").sortable({
					connectWith: '.connectedSortable',
					placeholder: 'ui-state-highlight'
					
				}).disableSelection();
				
				$("#"+grid_id+"sortable1>li>div").toggle(
						
				function(){
					$(this).attr("order","desc").children()
					.removeClass("ui-icon-arrowthick-1-n")
					.addClass("ui-icon-arrowthick-1-s").text("降序");
				},
				
				function(){
					$(this).attr("order","asc").children()
					.removeClass("ui-icon-arrowthick-1-s")
					.addClass("ui-icon-arrowthick-1-n").text("升序");
					}
				);
				$('#'+grid_id+'_sortDlg').draggable({cancel:".form-body"})
				
			},
			
			openSearchPanel:function(funcno){
				var $sp = $("#grid"+funcno+"_sp");
				if($sp.length>0){
					$sp.dialog("open");
				}else{
					var gridDef = $.grid.list[funcno];
					$.grid.createSearchPanel(funcno,gridDef.colModel,gridDef.colNames);
					$("#grid"+funcno+"_sp").dialog({
						title:"<span style='float:left' class='ui-icon ui-icon-search'></span>查询对话框",
						bgiframe:true,
						autoOpen:true,
						width:540,
						zIndex:100,
						draggable:false,
						modal: true,
						buttons:{
							"查询":function(){
								if($.grid.doQuery(funcno))
									$(this).dialog("close");
								
							},	
							"关闭":function(){
								$(this).dialog("close");
							}
						}
					});
				}
			},
			
			createSearchPanel:function(funcno,colModel,colNames){
				$panel = $("<table cellspacing='0' style='width:100%' class='searchPanel'></table>")
				$sPanel = $("<div style='display:none' id='grid"+funcno+"_sp'></div>").append($panel);
				$("#div_grid"+funcno).prepend($sPanel);
				for(var i=0;i<colModel.length;i++){
					var coldef = colModel[i];
					if(coldef.searchable){
						var inp = "";
						var id = "grid"+funcno+"_s_"+coldef.name.replace(".","-");
						var dt = coldef.datatype;
						if((dt == 'NUMBER') || (dt == 'FLOAT') || (dt == 'INTEGER') || (dt == 'DATE') )
							inp = "<td id='"+id+"'><input style='float:left;' type='text'/><span style='float:left;'>至</span><input style='float:left;' type='text'/></td>";
						else 
							inp = "<td id='"+id+"'><input style='float:left;' type='text'/></td>";
						
						$panel.append("<tr><td style='width:20px;'><input id='"+id.replace("_s_", "_c_")+"' type='checkbox'/></td>" +
								"<td style='width:160px;'>"+colNames[i]+":</td>"+inp+"</tr>");
						if(dt=='DATE'){
							$("#"+id+">input").attr("readonly",true).datepicker({
								changeYear:true,changeMonth:true,duration:"fast",
								beforeShow:function(input,inst){
									inst.dpDiv.css("z-index",1000);
								}
							});
						}
					}
				} 
				
				$panel.append("<tr><td colspan='3'><span class='ui-state-highlight' style='float:left' id='grid"+funcno+"_queryTip'></span></td></tr>");
				
				return $sPanel;
				/*var $btn = $.button.create({id: "grid"+funcno+"_query",
 					caption:"查询", 
 					icon:"ui-icon-search", 
 					pos:"right",
 					script:"$.grid.doQuery("+funcno+")"
				});*/
				
				
			},
			
			doQuery:function(funcno){
				$("#grid"+funcno+"_queryTip").html("");
				var gridDef = $.grid.list[funcno];
				var cond = "1=1";
				var c = gridDef.colModel.length;
				for(var i=0;i<c;i++){
					var coldef = gridDef.colModel[i];
					if(coldef.searchable){
						var id ="grid"+funcno+"_c_"+coldef.name.replace(".","-");
						if($("#"+id).attr("checked")){
							var dt = coldef.datatype;
							if((dt == 'NUMBER') || (dt == 'FLOAT') || (dt == 'INTEGER')){
								var val1 = $("#"+id.replace("_c_", "_s_")+">input:first").val();
								var val2 = $("#"+id.replace("_c_", "_s_")+">input:last").val();
								if(val1 && val2){
									if(/\d+$/.test(val1) && /\d+$/.test(val2)){
										cond += " and ("+coldef.name + " >= " + val1
											  + " and "+coldef.name +" <= " + val2 +")";
									}else{
										$("#grid"+funcno+"_queryTip").html(gridDef.colNames[i]+"只能是数字");
										return false;
									}
								}else if(val1){
									if(/\d+$/.test(val1)){
										cond += " and "+coldef.name + " >= " + val1;
									}else{
										$("#grid"+funcno+"_queryTip").html(gridDef.colNames[i]+"只能是数字");
										return false;
									}
									
								}else if(val2){
									if(/\d+$/.test(val2)){
										cond += " and "+coldef.name + " <= " + val2;
									}else{
										$("#grid"+funcno+"_queryTip").html(gridDef.colNames[i]+"只能是数字");
										return false;
									}
								}else{
									$("#grid"+funcno+"_queryTip").html(gridDef.colNames[i]+" 左右不能都为空值");
									return false;
								}
								
							}else if(dt == 'DATE'){
								var val1 = $("#"+id.replace("_c_", "_s_")+">input:first").val();
								var val2 = $("#"+id.replace("_c_", "_s_")+">input:last").val();
								if(val1 && val2){
									cond += " and ( "+coldef.name + " between to_date('"+val1+"','yyyy-MM-dd') and to_date('"+val2+"','yyyy-MM-dd') )"
								}else if(val1){
									cond += " and "+coldef.name + " >= to_date('" + val1+"','yyyy-MM-dd')";
									
								}else if(val2){
									cond += " and "+coldef.name + " >= to_date('" + val2+"','yyyy-MM-dd')";
									
								}else{
									$("#grid"+funcno+"_queryTip").html(gridDef.colNames[i]+" 左右不能有空值");
									return false;
								}
							}else{
								var op = "=";
								var val = $("#"+id.replace("_c_", "_s_")+">input").val().replace("'","");
								if(val.match("%")||val.match("_"))
									op = "like";
								
								cond += " and " + coldef.name + " "+op+" '"+val+"'";
							}
						}	
					}
				}
				if(cond=='1=1')
					return false;
				$.userContext.userData[funcno+"-COND"] = cond;
				$("#grid"+funcno).trigger('reloadGrid');
				return true;
				//$.grid.refresh(funcno,cond);
			},
			
			refresh:function(funcno,filter){
				var gridDef = $.grid.list[funcno];
				
				if(filter && (filter != '$'))//有刷新过滤条件则带上条件刷新
					$("#grid"+funcno).jqGrid('setPostDataItem','selconditions',filter);
				
				$("#grid"+funcno).trigger('reloadGrid');
			},
			
			fn:{ 
				//将选择的行数据保存到上下文中
				getDataToContext:function(funcno,rowid){
					
					var rowdata = $("#grid"+funcno).jqGrid('getRowData',rowid);
					$.each(rowdata,function(i){
						var val = rowdata[i];
						if(escape(val)=='%A0')
							val ='';
						$.userContext.userData[(funcno + "-" + i).toUpperCase()] = val;
					});
				},
				
				editRow:function(funcno,rowid,editCond){
					var grid_id = "grid"+funcno;
					$("#"+grid_id).jqGrid('restoreRow',$.grid.lastRows[funcno]);
					if(!condTester.ifCondition($.userContext.parser(editCond))){
						return;
					}	
					$("#"+grid_id).jqGrid('editRow',
							rowid,
							true,
							function(rowid){
								//oneEidtfunc;
							},
							function(rowid){
								//succesfunc
							},
							"clientArray",
							{
								//extraparam
							},
							function(rowid){
								var gridDef = $.grid.list[funcno];
								$.grid.fn.getDataToContext(funcno,rowid);
								var cmd = $.page.btn.generateCmd( gridDef.update_check, [gridDef.update_proc] );
								$.page.btn.call_proc(cmd,function(data){
									//if(data =='success')
									//alert(data);
								});
							},
							function(rowid){
								//errorfunc
							},
							function(rowid){
								//afterrestorefunc
							}
					);
				},
				delRow:function(funcno,rowid){
					$.grid.fn.getDataToContext(funcno,rowid);
					$.msgbox.show("conf","确认要删除吗?",function(){
						var gridDef = $.grid.list[funcno];
						var cmd = $.page.btn.generateCmd( gridDef.delete_check, [gridDef.delete_proc] );
						$.page.btn.call_proc( cmd,function( msg ){
							if(msg.match("success")){
								$("#grid"+funcno).jqGrid('delRowData',rowid);
							}
						});
					});
				},
				addRow:function(funcno){
					var gridDef = $.grid.list[funcno];
					var cmd = $.page.btn.generateCmd( gridDef.insert_check, [gridDef.insert_proc] );
					$.page.btn.call_proc( cmd,function( msg ){
						if(msg.match("success")){
							$("#grid"+funcno).trigger("reloadGrid");
						}
					});
				},
				getMultiRowsToContext:function(funcno, fields){
					var rowids = $("#grid"+funcno).jqGrid('getGridParam','selarrrow');
					var multiRows = $.grid.fn.getRowsData(funcno,rowids,fields);
					var multiRowsCount = rowids.length;
					$.userContext.setData(funcno+"-multiRows",multiRows);
					$.userContext.setData(funcno+"-multiCount",multiRowsCount);
				},
				
				getRowsData:function(funcno,rowids,fields){
					var rowsDataStr = "";
					var flds = fields.split(",");
					for(var i=0; i<rowids.length; i++){
						var rowdata = $("#grid"+funcno).jqGrid("getRowData",rowids[i]);
						rowsDataStr += $.grid.fn.getFieldsData(rowdata,flds) + (i<rowids.length-1?";":"");
					}
					return rowsDataStr;
				},
				
				getFieldsData:function(rowdata,fields){
					var rowdataStr = "";
					for(var i=0;i<fields.length;i++){
						rowdataStr += rowdata[fields[i]]+(i<fields.length-1?"&":"");
					}
					return rowdataStr;
				},
				
				openSortDialog:function(funcno){
					$("#grid"+funcno+"_sortDlg").show();
				},
				closeSortDialog:function(funcno){
					$("#grid"+funcno+"_sortDlg").hide();
				},
				sortGrid:function(funcno){
					var ordStr = $.grid.fn.getOrderString(funcno);
					if(ordStr==""){
						$.tip("注意：请从左边框内选择字段拖拽到右边框，然后点击'排序'按钮","grid"+funcno+"_sortDlg");
						return ;
					}
					$("#grid"+funcno).jqGrid("appendPostData",{ordStr:ordStr});
					$("#grid"+funcno).trigger('reloadGrid');
				},
				getOrderString:function(funcno){
					var sortFlds = $("#grid"+funcno+"sortable2>li>div");
					var orderString = "";
					$.each(sortFlds,function(i){
						orderString += $(sortFlds[i]).attr("name")+" "+ $(sortFlds[i]).attr("order")+",";
					});
					
					orderString = orderString.substring(0, orderString.length-1);
					return orderString;
				}	
			}
	}
		
})(jQuery);
	/*------------------end for grid.js--------------------*/
