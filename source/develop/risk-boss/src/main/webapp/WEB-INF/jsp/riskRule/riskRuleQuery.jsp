<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title>规则查询</title>
</head>
<body>
<div id="wrapper">
	<div class="wrapper wrapper-content animated fadeInRight ecommerce">
		<div class="ibox-content m-b-sm border-bottom">
			<form role="ruleForm" id="ruleForm" action="${ctxPath}/riskrule/queryRiskrule" method="get" data-table="queryTable" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="rulename">规则名称：</label>
							<input type="text" class="form-control input-sm" id="rulename" name="rulename"
								   value="${riskrule.rulename}" placeholder="规则名称" class="form-control">
						</div>
					</div>

					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="status">规则状态：</label>
							<dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
						</div>
					</div>

					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="category">规则类别：</label>
							<dict:select dictcode="category" id="category" name="category" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
						</div>
					</div>
				</div>
				<div class="row query-div">
					<a href="toAddRiskrule">
						<button type="button" id="toAddRiskrule" class="btn btn-w-m btn-primary">添加</button>
					</a>
					<button type="submit" class="btn btn-w-m btn-success">查询</button>
					<button type="reset" class="btn btn-w-m btn-info">重置</button>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox">
					<div class="ibox-content">
						<table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
							<thead>
							<tr>
								<th><input type="checkbox" name='choosetotal' id="checkAll">全选</th>
								<th >规则名称</th>
								<th >规则类别</th>
								<th >规则状态</th>
								<th >规则描述</th>
								<th >更新时间</th>
								<th >操作员</th>
								<th>操作</th>
							</tr>
							</thead>
						</table>
						<a href="#">
							<button type="button" id ="delete" class="btn-white btn btn-xs">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
						</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#">
							<button type="button" id ="setActive" class="btn-white btn btn-xs">
								<i class="fa fa-play"></i>&nbsp;&nbsp;<strong>启用</strong></button>
						</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#">
							<button type="button" id ="setInAactive" class="btn-white btn btn-xs">
								<i class="fa fa-pause"></i>&nbsp;&nbsp;<strong>停用</strong></button>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script id="checkbox-tpl" type="text/x-handlebars-template">
	<input type="checkbox" name="choose" value="{{id}}"/>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
	<div class="btn-group">
		{{#each func}}
		<button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
		{{/each}}
	</div>
</script>
<script id="status-tpl" type="text/x-handlebars-template">
	<span class="label {{cssClass}}">{{text}}</span>
</script>
</body>

<div id="siteMeshJavaScript">
	<script type="text/javascript">
		var csrfToken = "${_csrf.token}";
		var csrfHeaderName = "${_csrf.headerName}";
		var checkboxSource   = $("#checkbox-tpl").html();
		var checkBoxTemplate = Handlebars.compile(checkboxSource);

		var operTpl = $("#oper-tpl").html();
		//预编译模板
		var operTemplate = Handlebars.compile(operTpl);
		var statusSource   = $("#status-tpl").html();
		var statusTemplate = Handlebars.compile(statusSource);

		$(function () {
			$("#delete").click(function () {
				var deleteIds = "";
				var result = $("input[name='choose']:checked").length;
				if (result == 0) {
					bootbox.alert("请选择数据");

				} else {
					if (confirm("确定删除？")) {
						var str = document.getElementsByName("choose");
						for (var i = 0; i < str.length; i++) {
							if (str[i].checked == true) {
								if (deleteIds == "") {
									deleteIds += str[i].value;
								} else {
									deleteIds += "," + str[i].value;
								}
							}
						}
						$.ajax({
							type: "POST",
							beforeSend: function (xhr) {
								xhr.setRequestHeader(csrfHeaderName, csrfToken);
							},
							url: "${ctxPath}/api/1/riskrule/deleteRiskrule",
							data: "ids=" + deleteIds,
							dataType: "json",
							success: function (data, textStatus, jqXHR) {
								if (data.code==200) {
									bootbox.alert("删除成功！");
									$('#ruleForm').submit();
								} else {
									bootbox.alert("删除失败！");
									$('#ruleForm').submit();
								}
								$("#checkAll").attr("checked",false);
							}
						});
					} else {

					}
				}
			});
		});

		$(function () {
			$("#setActive").click(function () {
				var activeIds = "";
				var result = $("input[name='choose']:checked").length;
				if (result == 0) {
					bootbox.alert("请选择数据");

				} else {
					if (confirm("确定启用？")) {
						var str = document.getElementsByName("choose");
						for (var i = 0; i < str.length; i++) {
							if (str[i].checked == true) {
								if (activeIds == "") {
									activeIds += str[i].value;
								} else {
									activeIds += "," + str[i].value;
								}
							}
						}
						$.ajax({
							type: "POST",
							beforeSend: function (xhr) {
								xhr.setRequestHeader(csrfHeaderName, csrfToken);
							},
							url: "${ctxPath}/api/1/riskrule/modifyRiskruleStatus",
							data: "ids=" + activeIds+"&setStatus=Active",
							dataType: "json",
							success: function (data, textStatus, jqXHR) {
								console.log(typeof data.code);
								if (data.code==200) {
									bootbox.alert("启用成功！");
									$('#ruleForm').submit();
								} else {
									bootbox.alert("启用失败！");
									$('#ruleForm').submit();
								}
								$("#checkAll").attr("checked",false);
							}
						});
					} else {

					}
				}
			});
		});

		$(function () {
			$("#setInAactive").click(function () {
				var inactiveIds = "";
				var result = $("input[name='choose']:checked").length;
				if (result == 0) {
					bootbox.alert("请选择数据");

				} else {
					if (confirm("确定停用？")) {
						var str = document.getElementsByName("choose");
						for (var i = 0; i < str.length; i++) {
							if (str[i].checked == true) {
								if (inactiveIds == "") {
									inactiveIds += str[i].value;
								} else {
									inactiveIds += "," + str[i].value;
								}
							}
						}
						$.ajax({
							type: "POST",
							beforeSend: function (xhr) {
								xhr.setRequestHeader(csrfHeaderName, csrfToken);
							},
							url: "${ctxPath}/api/1/riskrule/modifyRiskruleStatus",
							data: "ids=" + inactiveIds+"&setStatus=Inactive",
							dataType: "json",
							success: function (data, textStatus, jqXHR) {
								if (data.code==200) {
									bootbox.alert("停用成功！");
									$('#ruleForm').submit();
								} else {
									bootbox.alert("停用失败！");
									$('#ruleForm').submit();
								}
								$("#checkAll").attr("checked",false);
							}
						});
					} else {

					}
				}
			});
		});

		function getDetail(id,type) {
			var rulename=$("#rulename").val();
			var status=$("#status").val();
			var category=$("#category").val();
			window.location.href="${ctxPath}/riskrule/getRiskruleDetail?ids="+id+"&dealType="+type+"&rulename="+rulename+"&status="+status+"&category="+category;
		}

		//全选
		$(function () {
			$("#checkAll").click(function () {
				var choosetotal = $("input[name='choosetotal']");
				if (choosetotal[0].checked == true) {
					$("input[name='choose']").attr("checked", true);
				} else {
					$("input[name='choose']").attr("checked", false);
				}
			});
		});

		//绑定form的查询到tables
		$(function(){
			$('form#ruleForm').queryTables({
				"pageLength":10,
				columns: [
					{"data": "id"},
					{"data": "rulename"},
					{"data": "category"},
					{"data": "status"},
					{"data": "discribe"},
					{"data": "updatetime"},
					{"data": "username"},
					{"data": null}
				],
				columnDefs:[
					{
						targets:0,
						render:function(data, type, row, meta){
							return checkBoxTemplate({id:data});
						}
					},
					{
						targets:2,
						render:function(data, type, row, meta){
							var dataMap={"00":{text:"风控规则"},"01":{text:"反洗钱规则"},"02":{text:"登录异常规则"},"03":{text:"防钓鱼规则设置"},"04":{text:"注册异常规则"}};
							var map=dataMap[data];
							return map?statusTemplate(map):'';
						}
					},
					{
						targets:3,
						render:function(data, type, row, meta){
							var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"}};
							var map=dataMap[data];
							return map?statusTemplate(map):'';
						}
					},
					{
						targets: 5,
						render:function(data, type, row, meta){
							return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
						}
					},
					{
						targets: 7,
						render: function (data, type, row, meta) {
							var context =
							{
								func: [
									{"text": "查看", "fn": "getDetail("+row.id+",'query')", "cssClass": "fa-wrench"},
									{"text": "编辑", "fn": "getDetail("+row.id+",'modify')", "cssClass": "fa-pencil"}
								]
							};
							var html = operTemplate(context);
							return html;
						}
					}
				]
			});
		});
	</script>
</div>
</html>