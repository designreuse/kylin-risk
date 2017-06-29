<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>业务流程添加</title>
</head>
<body>
<form id="addChannelFlowform" method="post" data-parsley-validate class="form-horizontal">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
        <div class="ibox-content   float-e-margins">
            <div class="row">
                <div class="form-channelFlow">
                    <label class="col-sm-2 control-label">渠道产品:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="channel" id="channelcode" name="channelcode"
                                     nullLabel="请选择" nullOption="true"
                                     cssClass="form-control" onchange="checkChannel(this.value)"
                                     required="true"></dict:select>
                    </div>
                    <div class="col-sm-4 m-b">
                        <select id="productcode" name="productcode" class="form-control"
                        data-parsley-remote
                        data-parsley-remote-validator="checkproductcode"
                        data-parsley-remote-message="该渠道产品业务流程信息已存在"
                        required>
                        <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-channelFlow">
                    <label class="col-sm-2 control-label">个人信息流程:</label>
                    <div class="col-sm-7">
                        <div class="panel panel-default panel-body" id="channel"
                             style="margin-bottom:0px;">
                            <input id="crawler" name="crawler" type="checkbox">爬虫
                            <input id="idcardvalidate" name="idcardvalidate" type="checkbox">身份证验证
                            <input id="bankcardvalidate" name="bankcardvalidate" type="checkbox">银行卡验证
                            <input id="idcardhandvalidate" name="idcardhandvalidate"
                                   type="checkbox">手持身份证验证
                            <input id="mitou" name="mitou" type="checkbox">米投
                            <input id="bairongeducational" name="bairongeducational"
                                   type="checkbox">百融学历
                            <input id="bairongspecial" name="bairongspecial" type="checkbox">百融特殊名单
                            <input id="bairongquery" name="bairongquery" type="checkbox">百融多次查询
                            <input id="unionpayadvisors" name="unionpayadvisors" type="checkbox">银联智策
                        </div>
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="form-channelFlow">
                    <div class="row query-div">
                        <input type="button" id="submitAdd" class="btn btn-w-m btn-success"
                               value="添加">&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        var csrfToken = "${_csrf.token}";
        var csrfHeaderName = "${_csrf.headerName}";
        function checkChannel(channelvalue) {
            $("#productcode").empty();
            if (channelvalue != "" && channelvalue != undefined && channelvalue != null) {
                $(document).ready(function () {
                    $.getJSON("${ctxPath}/api/1/dictionary/queryProduct", {channelcode: channelvalue}, function (json) {
                        var data = json.data;
                        data && $.each(data, function (key, value) {
                            $("#productcode").append('<option value="'
                                    + key
                                    + '">'
                                    + value
                                    + '</option>');
                        });
                        allProducts = data;
                    });
                });
            } else {
                $("#productcode").append('<option value="">' + "请选择" + '</option>');
            }
        }
        $(function () {
            window.Parsley.addAsyncValidator('checkproductcode', function (xhr) {
                return xhr.responseJSON.data;
            }, '${ctxPath}/api/1/channelFlow/checkExistProduct');

            $("#submitAdd").click(function () {
                subChannelFlowAdd();
            });
            $("#back").backup();
        });
        function subChannelFlowAdd() {
            var valid = $("#addChannelFlowform").parsley().validate();
            if (valid) {
                if ($("#channel input[type='checkbox']:checked").length < 1) {
                    bootbox.alert("请选择个人信息流程！");
                    return;
                }
                $("#channel :checkbox").each(function (i) {
                    if ($(this).attr("checked")) {
                        $(this).val("00");
                    } else {
                        $(this).attr("checked", "checked");
                        $(this).val("01");
                    }
                });
                $("#addChannelFlowform").ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    url: '${ctxPath}/api/1/channelFlow/addChannelFlow',
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("添加成功！", function () {
                                $.backquery();
                            });
                        } else {
                            bootbox.alert("添加失败！");
                        }
                    }
                });
            }
        }
    </script>
</div>
</html>
