<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title>菜单修改</title>
</head>
<body>
<div class="ibox float-e-margins" style="margin-top: 50px">
	<div class="ibox-title">
		<h5>菜单信息</h5>

	</div>
   <div class="ibox-content">
	<form id="menuModifyForm" action="${ctxPath}/menu/menuModify" method="post" class="form-horizontal" data-parsley-validate>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input id="id" type="hidden" name="id" value="${menu.id}" />
		<input id="parentid" type="hidden" name="parentid" value="${menu.parentid}" />
		<input id="menulevel" type="hidden" name="menulevel" value="${menu.menulevel}" />
		<div style="position:relative;height:5px;clear:both;"></div>
		<div id="modifyResult" class="success-font"></div>

		<div class="form-group"><label class="col-lg-2 control-label">名称:</label>
			<div class="col-lg-10">
				<input type="text" id="menuname" name="menuname" value="${menu.menuname}" class="form-control" required>
			</div>
		</div>

		<div class="form-group"><label class="col-lg-2 control-label">菜单链接地址:</label>
			<div class="col-lg-10">
				<input type="text" id="menuurl" name="menuurl" value="${menu.menuurl}" class="form-control" required>
			</div>
		</div>

		<div class="form-group"><label class="col-lg-2 control-label">显示顺序:</label>
			<div class="col-lg-10">
				<input type="text" id="displayorder"  name="displayorder" value="${menu.displayorder}" class="form-control" required data-parsley-type="number">
			</div>
		</div>

		<div class="form-group"><label class="col-lg-2 control-label">状态:</label>
			<div class="col-lg-10">
				<select class="form-control" id="status" name="status" value="${menu.status}" required>
					<option value="00"<c:if test="${menu.status=='00'}">selected</c:if>>可用</option>
					<option value="01" <c:if test="${menu.status=='01'}">selected</c:if>>禁用</option>
				</select>
			</div>
		</div>
		<div style="position:relative;left:60px;top:70px;">
			<button type="button" id="modifyButton" class="btn btn-w-m btn-primary"><strong>修改</strong></button>
			<button type="reset" class="btn btn-w-m btn-primary"><strong>取消</strong></button>
		</div>

	</form>
   </div>
</div>
</body>
<div id="siteMeshJavaScript">
	<script type="text/javascript">
		$(function(){
			var initparsley=$("#menuModifyForm").parsley();
			$("#modifyButton").click(function(){
				$("#menuModifyForm").ajaxSubmit({
					type:'post',
					dataType: 'json',
					beforeSubmit:function(){
						return initparsley.validate();
					},
					url: '${ctxPath}/api/1/menu/menuModify',
					success: function(data,textStatus,jqXHR){
						if(data.code==200){
							bootbox.alert("修改菜单成功！", function () {
								window.parent.formSubmit();
								window.parent.refresh("mennudisplay","")
							});
						}else{
							bootbox.alert("修改菜单失败！", function () {
								window.parent.formSubmit();
								window.parent.refresh("mennudisplay","")
							});
						}
					}
				});
			});

			$("#reset").click(function(){
				var id = $("#id").val() ;
				var parentId = $("#parentId").val() ;
				if(""!=id && ""!=parentId ){
					window.location.href="${ctxPath}/menu/toMenuModify.action?id="+id+"&parentId="+parentId ;
				}
			});

		});

	</script>
</div>
</html>