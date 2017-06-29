<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>


<html>
<head>
	<title>菜单操作信息</title>
</head>
<body>	
	<div style="position:relative;width:600px;height:500px;top:15px;left:30px;">
		<div style="position:relative;height:5px;clear:both;"></div>
		<div id="addResult" class="success-font" >操作成功，信息如下：</div>
		<form id="menuAddForm" action="" method="post">
			<div style="height:200px;" >
					<table class="global_table" cellpadding="0" cellspacing="0">
						<tr>
							<th style="width:35%;">名称：</th>
							<td style="width:65%;">${menu.menuname}</td>
						</tr>
						<tr>
							<th>菜单级别：</th>
							<c:if test="${menu.menulevel==1}">
								<td>一级菜单</td>
							</c:if>
							<c:if test="${menu.menulevel==2}">
								<td>二级菜单</td>
							</c:if>
							<c:if test="${menu.menulevel==3}">
								<td>三级菜单</td>
							</c:if>
						</tr>
						<tr>
							<th>状态：</th>
							<c:if test="${menu.status.toString()=='01'}">
								<td>可用</td>
							</c:if>
							<c:if test="${menu.status.toString()=='00'}">
								<td>禁用</td>
							</c:if>
						</tr>
						<tr>
							<th>菜单链接地址：</th>
							<td>${menu.menuurl}</td>
						</tr>
						<tr>
							<th>displayorder：</th>
							<td>${menu.displayorder}</td>
						</tr>
					</table>
			</div>
			
			<div style="position:relative;left:160px;top:90px;">
				<input id="return" type="button" class="global_btn" value="返回" />
			</div>
			
		</form>	

	</div>
</body>

<div id="siteMeshJavaScript">
	<script type="text/javascript">
		$(function(){
			window.parent.formSubmit();
		});

		function changeTheFrameSrcValue(frameId,questURL){
			window.parent.refresh(frameId, questURL);
		}
	</script>


</div>
</html>