<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子模板添加</title>
</head>
<body>
<form id="addFactorTemplform" method="post" data-parsley-validate class="form-horizontal">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
        <div class="ibox-content   float-e-margins">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label">归属规则组:</label>
                    <div class="col-sm-3 m-b">
                        <select name="groupid" id="rulegroup1" class="form-control" required>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label">因子类型:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="factortype" id="factortype" name="type"
                                     nullLabel="请选择" nullOption="true" cssClass="form-control"
                                     required="true"></dict:select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">因子:</label>
                    <div class="col-sm-3 m-b">
                        <select name="libraryid" id="factorLibrary" class="form-control" multiple=""
                                required></select>
                    </div>
                </div>
            </div>
            <div class="row query-div">
                <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="添加">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
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

        $(function () {
            $("#back").backup();
            addRuleCategory1($("#rulegroup1"));
            $("#submitAdd").click(function () {
                subRuleAdd();
            });
            $("#factortype").change(function () {
                addFactorLibrary($("#factorLibrary"), $(this).val());
            })
        });
        function addRuleCategory1(factorSelect) {
            factorSelect.append("<option value=''>---请选择---</option>");
            $.getJSON("${ctxPath}/api/1/group/queryAllGroup", function (json) {
                $.each(json.data, function (index, factors) {
                    factorSelect.append('<option value="' + factors['id']  + '">' + factors['groupname'] + '</option>');
                });

            });
        }
        function addFactorLibrary(factorSelect, factortype) {
            factorSelect.empty();
            $.getJSON("${ctxPath}/api/1/factorLibrary/queryFactorByType?type="
                    + factortype, function (json) {
                $.each(json.data, function (index, factors) {
                    factorSelect.append('<option value="'
                            + factors['id']
                            + '">'
                            + factors['name']
                            + '</option>');
                });

            });
        }


        function subRuleAdd() {
            var valid = $("#addFactorTemplform").parsley().validate();
            if (valid ) {
                $("#addFactorTemplform").ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    url: '${ctxPath}/api/1/factorTempl/addFactorTempl',
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
