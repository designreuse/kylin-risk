<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
	<title>操作员查询</title>
	<style>
		.dataTables_wrapper{padding-bottom: 0px}
	</style>
</head>
<body>
<div id="wrapper">
	<div class="wrapper wrapper-content animated fadeInRight ecommerce">
		<div class="ibox-content m-b-sm border-bottom">
			<form role="operform" id="operform" action="${ctxPath}/operator/queryOperator" method="post" data-table="queryTable" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="username">登录名称：</label>
							<input type="text" class="form-control input-sm" id="username" name="username"
								    placeholder="登录名称" class="form-control">
						</div>
					</div>

					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="realname">真实姓名：</label>
							<input type="text" class="form-control input-sm" id="realname" name="realname"
								  placeholder="真实姓名" class="form-control">
						</div>
					</div>

					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="createoper">创建人:(登录名)</label>
							<input type="text" class="form-control input-sm" id="createoper" name="createoper"
								    placeholder="创建人" class="form-control">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="opertype">操作员类型</label>
							<select id="opertype" name="opertype" class="form-control">
								<option value="">---- 请选择 ----</option>
								<option value="admin" <c:if test="${operator.opertype=='admin'}">selected</c:if>>管理员
								</option>
								<option value="oper" <c:if test="${operator.opertype=='oper'}">selected</c:if>>操作员
								</option>
							</select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="status">用户状态</label>
							<dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label" for="status">产品</label>
							<select name="products" id="product" class="form-control">
								<option value="">请选择</option>
							</select>
						</div>
					</div>
				</div>
				<div align="center">
					<button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
					<a href="toAddOperator">
						<button type="button" id="toAddOperator" class="btn btn-w-m btn-primary">
							<strong>添加</strong>
						</button>
					</a>
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
								<th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选</th>
								<th >登录名</th>
								<th >真实姓名</th>
								<th >电话</th>
								<th >操作员类型</th>
								<th >生效时间</th>
								<th >失效时间</th>
								<th >创建人</th>
								<th >产品</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
							</thead>
						</table>
						<a href="#">
							<button type="button" id ="delete" class="btn btn-white"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除</button>
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
<script id="product-tpl" type="text/x-handlebars-template">
	<span class="label">
		{{convertPro code}}
	</span>
</script>
</body>

<div id="siteMeshJavaScript">
	<script type="text/javascript">
		var csrfToken = "${_csrf.token}";
		var csrfHeaderName = "${_csrf.headerName}";
		var checkboxSource   = $("#checkbox-tpl").html();
		var checkBoxTemplate = Handlebars.compile(checkboxSource);
		var operTpl = $("#oper-tpl").html();
		var operTemplate = Handlebars.compile(operTpl);
		var statusSource   = $("#status-tpl").html();
		var statusTemplate = Handlebars.compile(statusSource);
		var productSource   = $("#product-tpl").html();
		var productTemplate = Handlebars.compile(productSource);
		var products;

		Handlebars.registerHelper("convertPro",function(value){
			if(value=="-1"){
				return "全部";
			}else if(value!=null){
				var proArr = value.split(",");
				var proNam = $.map(proArr,function(v){
					return convertProduct(v);
				}).join(",");
				return proNam;
			}

		});
		function convertProduct(incode){
			return incode&&products[incode];
		}
		$(document).ready(function(){
			products= queryProducts();
			$.each(products, function(key,value){
				$("#product").append('<option value="' + key + '">' + value + '</option>');
			});
		});
		function queryProducts(){
			$.ajaxSettings.async = false;
			var productsTem=null;
			$.getJSON("${ctxPath}/api/1/dictionary/queryProducts",function(json){
				productsTem = json.data;
			});
			return productsTem;
		}

		$(function () {
			$("#delete").click(function () {
				var deleteIds = "";
				var result = $("input[name='choose']:checked").length;
				if (result == 0) {
					bootbox.alert("请选择数据");

				} else {
					bootbox.confirm("确定删除？", function(result) {
						if(result==true){
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
								url: "${ctxPath}/api/1/operator/deleteOper",
								data: "deleteIds=" + deleteIds,
								dataType: "text",
								success: function (data, textStatus, jqXHR) {
									if (textStatus == "success") {
										bootbox.alert("删除成功！", function() {
											window.location.href="${ctxPath}/operator/toQueryOperator?_initQuery=true"
										});
									} else {
										bootbox.alert("删除失败！"+data.message, function() {
											window.location.href="${ctxPath}/operator/toQueryOperator?_initQuery=true"
										});
									}
								}
							});
						}else{

						}
					});

				}
			});
			/*
			 $("input[name='inputTest']")*/

		});

		function reSet(id) {
			$.ajax({
				type: "POST",
				beforeSend: function (xhr) {
					xhr.setRequestHeader(csrfHeaderName, csrfToken);
				},
				url: "${ctxPath}/api/1/operator/reSetPWD",
				data: "id=" + id,
				dataType: "text",
				success: function (data, textStatus, jqXHR) {
					if (data.code = 200) {
						bootbox.alert("重置成功！");
					} else {
						bootbox.alert("重置失败！");
					}

				}
			});

		}
		function modify(id) {
			window.location.href="${ctxPath}/operator/toOperatorDetail?operid="+id;
		}

		//全选
		function setChecked() {
			var choosetotal = $("input[name='choosetotal']");
			if (choosetotal[0].checked == true) {
				$("input[name='choose']").attr("checked", true);
			} else {
				$("input[name='choose']").attr("checked", false);
			}
		}

		//绑定form的查询到tables
		$(function(){
			$('form#operform').queryTables({
				"pageLength":10,
				"autoWidth": false,
				columns: [
					{"data": "id"},
					{"data": "username"},
					{"data": "realname"},
					{"data": "phone"},
					{"data": "opertype"},
					{"data": "passwdeffdate"},
					{"data": "passwdexpdate"},
					{"data": "createoper"},
					{"data": "products"},
					{"data": "status"},
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
						targets:4,
						render:function(data, type, row, meta){
							var dataMap={'admin':'管理员','oper':'操作员'};
							return dataMap[data]||data;
						}
					},
					{
						targets:8,
						render:function(data, type, row, meta){
							return productTemplate({code:data});
						}
					},
					{
						targets:9,
						render:function(data, type, row, meta){
							var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"}};
							var map=dataMap[data];
							return map?statusTemplate(map):'';
						}
					},

					{
						targets: 10,
						render: function (data, type, row, meta) {
							var context =
							{
								func: [
									{"text": "重置密码", "fn": "reSet("+row.id+")", "cssClass": "fa-wrench"},
									{"text": "编辑", "fn": "modify("+row.id+")", "cssClass": "fa-pencil"}
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