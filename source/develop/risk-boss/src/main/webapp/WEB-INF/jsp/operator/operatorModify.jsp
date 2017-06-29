<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title>修改操作员</title>
</head>
<body>
<div id="wrapper">
	<div  class="gray-bg">
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
				<div class="col-lg-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>修改操作员信息<small>.</small></h5>

						</div>
						<div class="ibox-content">
							<form id="updateOperForm" method="post" action="${ctxPath}/operator/operModify" class="form-inline" data-parsley-validate>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<input type="hidden" name="id" value="${operator.id}">
								<div align="center">
								<table  >
									<tr style=" height:50px;">
										<th><label>登录名:</label></th>
									<td>
										<input type="text" readonly="readonly" name="username" class="form-control" value="${operator.username}" style="width:250px">
									</td>
										<th><label>真实姓名:</label></th>
									<td>
										<input type="text"  name="realname" class="form-control" value="${operator.realname}" style="width:250px">
									</td>
									</tr>
									<tr style=" height:50px;">
										<th><label>电话号码:</label></th>
										<td>
											<input type="text"  name="phone" class="form-control" value="${operator.phone}" style="width:250px" required>
										</td>
										<th><label>Email:</label></th>
										<td>
											<input type="text"  name="email" class="form-control" value="${operator.email}" style="width:250px" required>
										</td>
									</tr>

									<tr style=" height:50px;">
										<th><label>生效日期:</label></th>
										<td>
											<input name="passwdeffdate"class="form-control" style="width:250px" value="${operator.passwdeffdate}"readonly="readonly"/>
										</td>

										<th><label>失效日期:</label></th>
										<td>
											<input name="passwdexpdate" type="text" name="passwdexpdate"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control Wdate" value="${operator.passwdexpdate}" style="width:250px" required />
										</td>
									</tr>

									<tr  style=" height:50px;">
										<th><label>操作员类型:</label></th>
										<td>
											<select  style="width:250px" class="form-control m-b" id="opertype" name="opertype" value="${operator.opertype}" required>
												<option value="admin"<c:if test="${operator.opertype=='admin'}">selected</c:if>>管理员</option>
												<option value="oper" <c:if test="${operator.opertype=='oper'}">selected</c:if>>操作员</option>
											</select>
										</td>
										<th><label>状态:</label></th>
										<td>
											<div class="onoffswitch">
												<input type="checkbox"  <c:if test="${operator.status=='00'}">checked="checked"</c:if> id="status" name="status" class="onoffswitch-checkbox" >
												<label class="onoffswitch-label" for="status">
													<span class="onoffswitch-inner"></span>
													<span class="onoffswitch-switch"></span>
												</label>
											</div>
										</td>
									</tr>

									<tr >
										<th >渠道产品:</th>
										<td colspan="3">
											<div class="panel-heading-hide" id="headingmodify">
												<input type="radio" name="productId" <c:if test="${productId=='-1'}">checked="checked"</c:if> value="-1"  id="allproductid" firstFlag>
												全部
												<input type="radio" name="productId" <c:if test="${productId !='-1'}">checked="checked"</c:if> value="" id="productnull" firstFlag>
												<a data-toggle="collapse" id="menumodify" aria-expanded="false">
													部分
												</a>
											</div>
											<div class="panel panel-default panel-body" id="channel">
												<c:forEach items="${list}" var="channel" varStatus="all" >
													<input type="radio" name="channels.channelCode" id="${channel.dictionaryCode.code}" value="${channel.dictionaryCode.code}" <c:if test="${channel.dictionaryCode.dictname=='1'}">checked</c:if> secondFlag>
													<a data-toggle="collapse" href="#faq${channel.dictionaryCode.code}" aria-expanded="true">${channel.dictionaryCode.name}</a>
													<div id="faq${channel.dictionaryCode.code}" class="panel-collapse collapse">
														<div class="faq-answer">
															<table>
																<tbody>
																<c:forEach items="${channel.dictionaryCodes}" var="dic" varStatus="i">
																	<c:if test="${i.count%8==0}">
																		<tr>
																	</c:if>
																	<td>
																		<input type="checkbox"  name="productId" value="${dic.code}"  <c:if test="${dic.dictname=='1'}">checked</c:if> thirdFlag="${channel.dictionaryCode.code}" >${dic.name}
																	</td>
																	<c:if test="${i.count%8==0}">
																		</tr>
																	</c:if>
																</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
									<tr>
										<th>角色信息</th>
										<td colspan="3" style="padding-right:10px">
											<table class="table table-bordered table-striped" >
												<tr>
													<%int i = 0;%>
													<c:forEach items="${newList}" var="role">
														<td width="25%">
																<c:if test='${role.remark=="checked"}'>
																	<input type="checkbox" checked="checked" name="roleIds" value="${role.id}">
																</c:if>
																<c:if test='${role.remark!="checked"}'>
																	<input type="checkbox" name="roleIds" value="${role.id}">
																</c:if>
															&nbsp;${role.rolename}
														</td>
														<%
															if( ++i%4 == 0){
																i=0;
																out.println("</tr><tr>");
															}
														%>
													</c:forEach>
												</tr>
											</table>
										</td>
									</tr>
								</table>
									</div>
								<div align="center">
									<button id="updateButton" type="button" class="btn btn-w-m btn-success"><strong>确认修改</strong></button>
									<button type="button" onclick="back()" class="btn btn-w-m btn-info"><strong>取消</strong></button>
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
		function back(){
			window.location.href="${ctxPath}/operator/toQueryOperator?_initQuery=true"
		}
		$(function(){
			$("#updateButton").click(function(){
				if(validateChannel()){
					modifyOperator();
				}else{
					bootbox.alert("请选择渠道产品！");
				}

			});
		});

		function validateChannel(){
			if(($("input:radio[name='productId']:checked").val()=="")&&($("input:checkbox[name='productId']:checked").length==0)){
				return false;
			}else{
				return true;
			}
		}
		function modifyOperator(){
			var initparsley=$("#updateOperForm").parsley();
			$("#updateOperForm").ajaxSubmit({
				type:'post',
				dataType: 'json',
				beforeSubmit:function(){
					return initparsley.validate();
				},
				url: '${ctxPath}/api/1/operator/operModify',
				success: function(data,textStatus,jqXHR){
					if(data.code==200){
						bootbox.alert("修改操作员成功！", function () {
							window.location.href="${ctxPath}/operator/toQueryOperator?_initQuery=true"
						});
					}else{
						bootbox.alert("修改操作员失败！", function () {
							window.location.href="${ctxPath}/operator/toQueryOperator?_initQuery=true"
						});
					}
				}
			});
		}

		$("input[firstFlag]").click(function(){
			if($(this).attr("id")=='allproductid'){
				$("input[secondFlag],input[thirdFlag]").each(function(){
					$(this).attr("checked",false);
					$(this).attr("disabled","disabled");
				});
			}else{
				$("input[secondFlag],input[thirdFlag]").each(function(){
					$(this).attr("disabled",false);
				});
			}
		});

		$("input[secondFlag]").click(function(){
			var checkid = $(this).attr("id");
			$("input[thirdFlag]").each(function (){
				var secondid = $(this).attr("thirdFlag");
				if(secondid==checkid){
					$(this).attr("checked","checked");
				}else{
					$(this).attr("checked",false);
				}
			});
		});

		$("input[thirdFlag]").click(function(){
			if($(this).is(":checked")){
				var checkid = $(this).attr("thirdFlag");
				$("#"+checkid).attr("checked","checked");
				$("input[thirdFlag]").each(function (){
					if($(this).attr("thirdFlag")!=checkid){
						$(this).attr("checked",false);
					}
				});
				$("#productnull").attr("checked","checked");
			}
		});
		$(document).ready(function(){
			var proval = $("input[name='productId']:checked").attr("id");
			if(proval=="allproductid"){
				$("input[secondFlag],input[thirdFlag]").each(function(){
					$(this).attr("disabled","disabled");
				});
			}
		});
		$(function(){
			$bodyshow=$('#channel');
			if($bodyshow.is(':visible')){
				$bodyshow.hide();
			}
			$('#menumodify').click(function(){
				if($bodyshow.is(':hidden')){
					$bodyshow.show();
					$("#productnull").val("");
				}else if($bodyshow.is(':visible')){
					$bodyshow.hide();
				}
			});
		});
	</script>
</div>
</html>
