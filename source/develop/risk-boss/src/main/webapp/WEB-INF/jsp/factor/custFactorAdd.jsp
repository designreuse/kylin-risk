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
		<h5>新增风险因子信息</h5>

	</div>
	<div class="ibox-content">
		<form id="factorAddForm" action="" method="post" data-parsley-validate>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="hidden"  name="factorlevel" value="${parent.factorlevel+1}"/>
			<input type="hidden"  name="factorid" value="${parent.id}"/>
			<input type="hidden"  name="addType" value="${addType}"/>
			<c:if test="${addType=='MODEL'}">
				<div class="form-group">
					<label class="col-lg-2 control-label" for="modelname">模型名称:</label>
					<div class="col-lg-10">
						<input type="text" id="modelname" name="name"  class="form-control" required>
						<input type="hidden" id="modelweight" name="weight"  class="form-control" value="${parent.weight}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="modelcode">模型代码:</label>
					<div class="col-lg-10">
						<input type="text" id="modelcode" name="code"  class="form-control" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="modelparentName">上级节点名称:</label>
					<div class="col-lg-10">
						<input type="text" id="modelparentName" readonly="readonly" name="parentName" value="${parent.name}" class="form-control" >
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="channel">渠道产品:</label>
					<div class="col-lg-10">
						<div class="panel panel-default panel-body" id="channel">
							<c:forEach items="${list}" var="channel" varStatus="all" >
								<input type="checkbox" name="channels[${all.index}].channelCode" value="${channel.dictionaryCode.code}" levFlag="firLev${all.count}">
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
													<input type="checkbox" name="channels[${all.index}].mercCode" value="${dic.code}"  onclick="setChecked(this,${all.count})" levFlag="secLev${all.count}">${dic.name}
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
					</div>
				</div>
			</c:if>
			<c:if test="${addType=='FACTOR'}">
				<div class="form-group">
					<label class="col-lg-2 control-label" for="factorname">风险因子名称:</label>
					<div class="col-lg-10">
						<input type="text" id="factorname" name="name"  class="form-control" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="factorcode">因子代码:</label>
					<div class="col-lg-10">
						<input type="text" id="factorcode" name="code"  class="form-control" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="factorparentName">上级节点名称:</label>
					<div class="col-lg-10">
						<input type="text" id="factorparentName" readonly="readonly" name="parentName" value="${parent.name}" class="form-control" >
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="weight">权重:</label>
					<div class="col-lg-10">
						<div class="input-group m-b">
							<input type="text" id="weight"  name="weight" value="" class="form-control" data-parsley-type="integer" required max="100">
							<span class="input-group-addon">%</span>
						</div>
					</div>

				</div>
			</c:if>
			<c:if test="${addType=='DESC'}">
				<div class="form-group">
					<label class="col-lg-2 control-label" for="descname">风险因子描述名称:</label>
					<div class="col-lg-10">
						<input type="text" id="descname" name="name"  class="form-control" required>
						<input type="hidden" id="descweight" name="weight"  class="form-control" value="${parent.weight}" required>
					</div>
				</div>
				<div class="form-group">
					<label class="col-lg-2 control-label" for="desccode">因子描述代码:</label>
					<div class="col-lg-10">
						<input type="text" id="desccode" name="code"  class="form-control" required>
					</div>
				</div>
				<div class="form-group"><label class="col-lg-2 control-label" for="factorparentName">上级节点名称:</label>
					<div class="col-lg-10">
						<input type="text" id="descparentName" readonly="readonly" name="parentName" value="${parent.name}" class="form-control" >
					</div>
				</div>
				<div class="form-group"><label class="col-lg-2 control-label" for="score">分数:</label>
					<div class="col-lg-10">
						<input type="text" id="score"  name="score"  class="form-control" data-parsley-type="integer" required max="100">
					</div>
				</div>
			</c:if>

			<div class="form-group"><label class="col-lg-2 control-label" for="description">详细描述:</label>
				<div class="col-lg-10">
					<input type="text" id="description" name="description"  class="form-control" >
				</div>
			</div>
			<div class="form-group"><label class="col-lg-2 control-label" for="remark">备注:</label>
				<div class="col-lg-10">
					<input type="text" id="remark" name="remark"  class="form-control" >
				</div>
			</div>

			<div class="form-group"><label class="col-lg-2 control-label" for="status">状态:</label>
				<div class="col-lg-10">
					<dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,05,04" nullLabel="请选择" nullOption="false" cssClass="form-control"></dict:select>
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
			var initparsley=$("#factorAddForm").parsley();
			$("#addbutton").click(function(){
				$("#factorAddForm").ajaxSubmit({
					type:'post',
					dataType: 'json',
					beforeSubmit:function(){
						return initparsley.validate();
					},
					url: '${ctxPath}/api/1/factor/addCustFactor',
					success: function(data,textStatus,jqXHR){
						if(data.code==200){
							bootbox.alert("添加成功！", function () {
								window.parent.formSubmit();
								window.parent.refresh("factorudisplay","")
							});
						}else{
							bootbox.alert("添加失败！", function () {
								window.parent.formSubmit();

							});
						}
					}
				});
			});

		});

		function setChecked(obj,num){
				var levFlagres="firLev"+num;
				if(obj.checked==true) {
					$("form input[levFlag="+levFlagres+"]").attr("checked", true);
				}
		}
    </script>
</div>
</html>