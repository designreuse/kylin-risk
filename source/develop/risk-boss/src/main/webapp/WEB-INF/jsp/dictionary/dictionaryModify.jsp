<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title>编辑数据字典</title>
</head>
<body>
<div id="wrapper">
	<div  class="gray-bg">
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
				<div class="col-lg-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>编辑数据字典信息<small>.</small></h5>

						</div>
						<div class="ibox-content">
							<form id="addForm" method="post"  class="form-inline" data-parsley-validate>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="id" value="${dictionaryCode.id}"/>
								<div align="center">
									<table>
										<tr style=" height:50px;">
											<th><label>字典编号:</label></th>
											<td>
												<input type="text" id="dictcode" name="dictcode"  value="${dictionaryCode.dictcode}" class="form-control"  style="width:250px" onkeyup="checkdictcode()" required>
											</td>
											<th><label>字典名称:</label></th>
											<td>
												<input type="text"   name="dictname" value="${dictionaryCode.dictname}" class="form-control"  style="width:250px" required>
											</td>
										</tr>

										<tr style=" height:50px;">
											<th><label>数值:</label></th>
											<td>
												<input type="text"   value="${dictionaryCode.code}" name="code" class="form-control"  style="width:250px" required data-parsley-maxlength="30">
											</td>
											<th><label>解释:</label></th>
											<td>
												<input id="name"  value="${dictionaryCode.name}" name = "name" class="form-control"  style="width:250px" required data-parsley-maxlength="50" />
											</td>
										</tr>
									</table>
								</div>
								<div align="center">
									<button type="button" id="submitButton"  class="btn btn-w-m btn-success"><strong>确定</strong></button>
									<button type="button"  onclick="back()" class="btn btn-w-m btn-info"><strong>取消</strong></button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<div id="siteMeshJavaScript">
	<script type="text/javascript" >

		var csrfToken="${_csrf.token}";
		var csrfHeaderName="${_csrf.headerName}";
		function checkdictcode(){
			var dictcode=$("#dictcode").val();
			$.ajax({
				type : "POST",
				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrfHeaderName, csrfToken);
				},
				url : "${ctxPath}/api/1/dictionary/checkDictCode",
				data : "dictcode="+dictcode,
				dataType: "text",
				success : function(data, textStatus, jqXHR) {
					if("false" == $.trim(data)){
						$("#userMsg").html("字典编号["+dictcode+"]存在！");
						$("#dictcode").val("");
					}else{
						$("#userMsg").html("");
					}
				}
			});
		}


		function back(){
			window.location.href="${ctxPath}/dictionary/toQueryDictionary?_initQuery=true";
		}

		$(function(){
			var initparsley=$("#addForm").parsley();
			$("#submitButton").click(function(){
				$("#addForm").ajaxSubmit({
					type:'post',
					dataType: 'json',
					beforeSubmit:function(){
						return initparsley.validate();
					},
					url: '${ctxPath}/api/1/dictionary/dictionaryModify',
					success: function(data,textStatus,jqXHR){
						if(data.code==200){
							bootbox.alert("修改成功！", function () {
								window.location.href="${ctxPath}/dictionary/toQueryDictionary";
							});
						}else{
							bootbox.alert("修改失败！", function () {
								window.location.href="${ctxPath}/dictionary/toQueryDictionary";
							});
						}
					}
				});
			});

		});
	</script>
</div>
</html>

