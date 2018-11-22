<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
		<meta charset="utf-8">
		<!--<link href="../index/css/index.css" type="text/css" rel="stylesheet">-->
		<link rel="stylesheet" href="<%=basePath%>shopping/bar/css/bar.css">
		<script src="<%=basePath%>shopping/mainjs/sysconfig.js" ></script>
		<script language="JavaScript" type="text/JavaScript">
		 
		</script>
	</head>
<div class="jdm-toolbar-wrap J-wrap">                
			<div class="jdm-toolbar J-toolbar">                    
				<div class="jdm-toolbar-panels J-panel">                                             
				</div>                                       
				<div class="jdm-toolbar-tabs J-tab">                         
					<div data-type="bar" class="J-trigger jdm-toolbar-tab jdm-tbar-tab-cart"   onclick="linkto('cart')">                                
						<i class="tab-ico" ></i>                                <em class="tab-text" id="icon-cart">购物车                                
						</em>                                                  
					</div>                          
					<div data-type="bar" clstag="h|keycount|cebianlan_h_follow|btn" onclick="linkto('collection')" class="J-trigger jdm-toolbar-tab jdm-tbar-tab-follow" data-name="follow" data-login="true">                                
						<i class="tab-ico"></i>                                <em class="tab-text">                                    我的收藏                                
						</em>                                                 
					</div>                          
					<div data-type="bar" clstag="h|keycount|cebianlan_h_history|btn" class="J-trigger jdm-toolbar-tab jdm-tbar-tab-history" data-name="history">                                
						<i class="tab-ico"></i>                                <em class="tab-text">                                    我的足迹                                
					</em>                                                   
				</div>                          
				<div class="J-trigger jdm-toolbar-tab jdm-tbar-tab-message" data-name="message"><a target="_blank" href="#">
					<i class="tab-ico"></i>                                
					<em class="tab-text">                                    我的消息                                
					</em></a>                                                
				</div>                          
				<div data-type="bar" clstag="h|keycount|cebianlan_h_jimi|btn" class="J-trigger jdm-toolbar-tab jdm-tbar-tab-jimi" data-name="jimi" data-login="true" onclick="linkto('qq')">                                
					<i class="tab-ico"></i>                                
					<em class="tab-text">                                    咨询我们                               
					</em>                                                   
				</div>                     
			</div>                    
			<div class="jdm-toolbar-footer">                        
				<div data-type="link" id="#top" class="J-trigger jdm-toolbar-tab jdm-tbar-tab-top">                                
					<a href="#" clstag="h|keycount|cebianlan_h|top">                                
						<i class="tab-ico"></i>                                <em class="tab-text">顶部</em>                                
					</a>                         
				</div>                        
				                  
			</div>                    
			<div class="jdm-toolbar-mini">                    
			</div>                
		</div>              
		<div id="J-toolbar-load-hook" clstag="h|keycount|cebianlan_h|load"></div>            
	</div>