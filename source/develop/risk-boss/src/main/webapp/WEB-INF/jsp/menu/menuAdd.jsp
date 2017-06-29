<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title></title>
</head>
<body>
<div class="ibox float-e-margins">
	<div class="ibox-title">
		<h5>菜单信息</h5>

	</div>
	<div class="ibox-content">
		<form id="menuAddForm" action="${ctxPath}/menu/menuAdd" method="post" data-parsley-validate>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<c:if test="${targetParentLevel=='0'}">
			<input type="hidden" type="text" name="menulevel" value="1"/>
			</c:if>
			<c:if test="${targetParentLevel=='1'}">
			<input type="hidden" type="text" name="menulevel" value="2"/>
			</c:if>
			<c:if test="${targetParentLevel=='2'}">
				<input type="hidden" type="text" name="menulevel" value="3"/>
			</c:if>
			<input type="hidden" type="text" name="parentid" value="${targetParentId}"/>
				<div class="form-group"><label class="col-lg-2 control-label">菜单名称:</label>
					<div class="col-lg-10">
						<input type="text" id="menuname" name="menuname"  class="form-control" required>
					</div>
				</div>
			<div class="form-group"><label class="col-lg-2 control-label">上级菜单名称:</label>
				<div class="col-lg-10">
					<input type="text" id="parentName" readonly="readonly" name="parentName" value="${parentNodeName}" class="form-control" >
				</div>
			</div>

				<div class="form-group"><label class="col-lg-2 control-label">菜单链接地址:</label>
					<div class="col-lg-10">
						<input type="text" id="menuurl" name="menuurl"  class="form-control" required>
					</div>
				</div>

				<div class="form-group"><label class="col-lg-2 control-label">菜单顺序:</label>
					<div class="col-lg-10">
						<input type="text" id="displayorder" name="displayorder"  class="form-control" required data-parsley-type="number">
					</div>
				</div>

<%--			<div class="form-group"><label class="col-lg-2 control-label">状态:</label>
				<div class="onoffswitch">
					<input type="checkbox"   id="status" name="status" class="onoffswitch-checkbox" >
					<label class="onoffswitch-label" for="status">
						<span class="onoffswitch-inner"></span>
						<span class="onoffswitch-switch"></span>
					</label>
				</div>
			</div>--%>
				<div class="form-group"><label class="col-lg-2 control-label">状态:</label>
					<div class="col-lg-10">
						<dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
					</div>
				</div>

				<div class="form-group"><label class="col-lg-2 control-label">菜单级别:</label>
					<div class="col-lg-10">
						<select  id="level" class="form-control input-sm"  name="level" style="height: 35px">
							<c:if test="${targetParentLevel=='0'}">
								<option value="1">一级菜单</option>								</c:if>
							<c:if test="${targetParentLevel=='1'}">
								<option value="2">二级菜单</option>
							</c:if>
							<c:if test="${targetParentLevel=='2'}">
								<option value="2">三级菜单</option>
							</c:if>
						</select>
					</div>
				</div>


			<div style="position:relative;left:60px;top:10px;">
				<button type="button" id="addbutton" class="btn btn-w-m btn-primary"><strong>提交</strong></button>
				<button type="reset" class="btn btn-w-m btn-primary"><strong>取消</strong></button>
			</div>
			
		</form>	
    </div>
</div>
</body>
<div id="siteMeshJavaScript">
	<script type="text/javascript" >
		$(function(){
			var initparsley=$("#menuAddForm").parsley();
			$("#addbutton").click(function(){
				$("#menuAddForm").ajaxSubmit({
					type:'post',
					dataType: 'json',
					beforeSubmit:function(){
						return initparsley.validate();
					},
					url: '${ctxPath}/api/1/menu/menuAdd',
					success: function(data,textStatus,jqXHR){
						if(data.code==200){
							bootbox.alert("添加菜单成功！", function () {
								window.parent.formSubmit();
								window.parent.refresh("mennudisplay","")
							});
						}else{
							bootbox.alert("添加菜单失败！", function () {
								window.parent.formSubmit();

							});
						}
					}
				});
			});

		});
    </script>
</div>
</html>