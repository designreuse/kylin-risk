<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title>添加操作员</title>
</head>
<body>
<div id="wrapper">
	<div  class="gray-bg">
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
				<div class="col-lg-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>添加操作员信息<small>.</small></h5>

						</div>
						<div class="ibox-content">
							<form id="addForm" method="post"  class="form-inline" data-parsley-validate>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div align="center">
									<table  >
										<tr style=" height:50px;">
											<th><label>登录名:</label></th>
											<td>
												<input type="text" id="username" name="username" placeholder="请输入用户名" class="form-control"  style="width:110px" data-parsley-maxlength="15" onkeyup="checkusername()" required>
											</td>
											<td>
												<input type="text"  value=""  class="form-control"  style="width:130px"readonly="readonly">
												<span id="userMsg" style="color: red"> </span>
											</td>
											<th><label>真实姓名:</label></th>
											<td>
												<input type="text"   name="realname" placeholder="请输入真实名字" class="form-control"  style="width:250px" required>
											</td>
										</tr>

										<tr style=" height:50px;">
											<th><label>电话号码:</label></th>
											<td colspan="2">
												<input type="text"  placeholder="请输入手机号码" name="phone" class="form-control"  style="width:250px" required data-parsley-maxlength="11">
											</td>
											<th><label>生效日期:</label></th>
											<td >
												<input id="passwdeffdate" placeholder="请选择生效日期" type="text" name="passwdeffdate"
													   onClick="WdatePicker()"   class="form-control Wdate" required/>
											</td>
										</tr>
										<tr  style=" height:50px;">
											<th><label>操作员类型:</label></th>
											<td colspan="2">
												<select  style="width:250px" class="form-control m-b" id="opertype" name="opertype"required>
													<option value="">--请选择用户类型--</option>
													<option value="admin"<c:if test="${operator.opertype=='admin'}">selected</c:if>>管理员</option>
													<option value="oper" <c:if test="${operator.opertype=='oper'}">selected</c:if>>操作员</option>
												</select>
											</td>
											<th><label>状态:</label></th>
											<td>
												<dict:select dictcode="status" id="status" name="status"
															 escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true"
															 cssClass="form-control"
															 required="true"></dict:select>

											</td>
										</tr>

										<tr style=" height:50px;">
											<th>渠道产品</th>
											<td colspan="4" style="padding-right:10px">
												<div class="panel-heading-hide" id="headingmodify">
													<input type="radio" name="productId" value="-1" id="allproductid" firstFlag>
													全部
													<input type="radio" name="productId" value=""  id="productnull" firstFlag>
													<a data-toggle="collapse" id="menu2" aria-expanded="false"  >
													部分
													</a>
												</div>
												<div class="panel panel-default panel-body" id="bodyshow">
													<c:forEach items="${list}" var="channel" varStatus="all">
														<input type="radio" name="channels.channelCode"  id="${channel.dictionaryCode.code}" value="${channel.dictionaryCode.code}" secondFlag >
														<a data-toggle="collapse" href="#faq${channel.dictionaryCode.code}" aria-expanded="true">
																${channel.dictionaryCode.name}
														</a>
														<div id="faq${channel.dictionaryCode.code}" class="panel-collapse collapse">
															<div class="faq-answer">
																<table>
																	<tbody>
																	<c:forEach items="${channel.dictionaryCodes}" var="dic" varStatus="i">
																		<c:if test="${i.count%8==0}">
																			<tr>
																		</c:if>
																		<td>
																			<input type="checkbox" name="productId" value="${dic.code}" thirdFlag="${channel.dictionaryCode.code}">${dic.name}
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


										<tr style=" height:50px;">
											<th>角色信息</th>
											<td colspan="4" style="padding-right:10px">
												<table class="table table-bordered table-striped" >
													<tr>
														<%int i = 0;%>
														<c:forEach items="${rolelist}" var="role">
															<td width="25%">
																<input type="checkbox" name="roleIds" value="${role.id}">&nbsp;
																	${role.rolename}

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
									<button type="button" id="submitButton"  class="btn btn-w-m btn-success"><strong>添加</strong></button>
									<button type="button" id="back" class="btn btn-w-m btn-info"><strong>取消</strong></button>
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
		function checkusername(){
			var username=$("#username").val();
			$.ajax({
				type : "POST",
				beforeSend: function(xhr) {
					xhr.setRequestHeader(csrfHeaderName, csrfToken);
				},
				url : "${ctxPath}/api/1/operator/checkUserName",
				data : "username="+username,
				dataType: "text",
				success : function(data, textStatus, jqXHR) {
					if("false" == $.trim(data)){
						$("#userMsg").html("用户名["+username+"]存在！");
						$("#username").val("");
					}else{
						$("#userMsg").html("");
					}
				}
			});
		}

		$(function(){
			$("#back").backup();

			$bodyshow=$('#bodyshow');
			if($bodyshow.is(':visible')){
				$bodyshow.hide();
			}

			$('#menu2').click(function(){
				if($bodyshow.is(':hidden')){
					$bodyshow.show();
				}else if($bodyshow.is(':visible')){
					$bodyshow.hide();
				}
			});
		});

		$(function(){
            $("#submitButton").click(function(){
				if(validateChannel()){
					addOperator();
				}else{
					bootbox.alert("请选择渠道！");
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
		function addOperator(){
			var initparsley=$("#addForm").parsley();
			$("#addForm").ajaxSubmit({
				type:'post',
				dataType: 'json',
				beforeSubmit:function(){
					return initparsley.validate();
				},
				url: '${ctxPath}/api/1/operator/addOperator',
				success: function(data,textStatus,jqXHR){
					if(data.code==200){
						bootbox.alert("添加操作员成功！", function () {
							$.backquery();
						});
					}else{
						bootbox.alert("添加操作员失败！"+data.message);
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
				var thirdid = $(this).attr("thirdFlag");
				if(thirdid==checkid){
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

	</script>
</div>
</html>
