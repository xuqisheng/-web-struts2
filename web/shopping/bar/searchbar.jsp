<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	
		$("#searchbtn").live("click",function(e) {
			var txt = $("#key")[0].value;
			window.location.href = encodeURI(encodeURI("search.jsp?category=0&keyword="+txt+"&projid="+projID));
		});
	


</script>


	<div id="wrapper">
		<div class="w">
			<div id="img"></div>
			<div id="search-2014">
				<div class="form">
					<input type="text" id="key" accesskey="s" value="请输入关键字进行搜索"
						onFocus="if(this.value=='请输入关键字进行搜索'){this.value='';};"
						onBlur="if(this.value==''){this.value='请输入关键字进行搜索';};"
						class="text">
					<button id="searchbtn" class="button cw-icon">
						<i></i>搜索
					</button>
				</div>
			</div>
			<div id="settleup-2014" class="dorpdown">
				<div class="cw-icon">
					<i class="ci-left"></i><a
						href="./cart/cart.jsp?projid=SHOP">我的购物车</a>
				</div>
			</div>
			<span class="clr"></span>
		</div>

