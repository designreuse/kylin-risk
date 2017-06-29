<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子模板添加</title>
</head>
<body>
<form id="addGroupform" method="post" data-parsley-validate class="form-horizontal">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
        <div class="ibox-content   float-e-margins">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">规则组名称:</label>
                    <div class="col-sm-3 m-b">
                        <input class="form-control " type="text" id="groupname" name="groupname"
                               required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label">规则组类型:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="category" id="grouptype" name="grouptype"
                                     nullLabel="请选择" nullOption="true" cssClass="form-control"
                                      required="true"></dict:select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label">业务类型:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="servicetype" id="servicetype" name="servicetype"
                                     nullLabel="请选择" nullOption="true" cssClass="form-control"
                                      required="true"></dict:select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label">生效时间:</label>
                    <div class="col-sm-3 m-b">
                        <input type="text" name="effdate"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               class="form-control Wdate" id="effdate" required/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label">失效时间:</label>
                    <div class="col-sm-3 m-b">
                        <input type="text" name="expdate"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               class="form-control Wdate" id="expdate" required/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="status">状态:</label>
                    <div class="col-sm-3">
                        <dict:select dictcode="status" id="status" name="status"
                                     escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true"
                                     cssClass="form-control"
                                     required="true"></dict:select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label" for="channel">渠道产品:</label>
                    <div class="col-sm-7">
                        <div class="panel panel-default panel-body" id="channel"
                             style="margin-bottom:0px;">
                            <c:forEach items="${list}" var="channel" varStatus="all">
                                <input type="hidden" name="channels[${all.index}].channelCode"
                                       value="${channel.dictionaryCode.code}">
                                <a data-toggle="collapse" href="#faq${channel.dictionaryCode.code}"
                                   aria-expanded="true">
                                        ${channel.dictionaryCode.name}
                                </a>
                                <div id="faq${channel.dictionaryCode.code}"
                                     class="panel-collapse collapse">
                                    <div class="faq-answer">
                                        <table>
                                            <tbody>
                                            <c:forEach items="${channel.dictionaryCodes}" var="dic"
                                                       varStatus="i">
                                                <c:if test="${i.count%8==0}">
                                                    <tr>
                                                </c:if>
                                                <td>
                                                    <input type="checkbox"
                                                           name="channels[${all.index}].mercCode"
                                                           value="${dic.code}"
                                                           thirdFlag="${channel.dictionaryCode.code}">${dic.name}
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
            $("#submitAdd").click(function () {
                subRuleAdd();
            });
            $("#back").backup();
        });
        function subRuleAdd() {
            var valid = $("#addGroupform").parsley().validate();
            if ($("input[name$='mercCode']:checked").length < 1) {
                bootbox.alert("请选择渠道产品！");
                return ;
            }            if (valid) {
                $("#addGroupform").ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    url: '${ctxPath}/api/1/group/addGroup',
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

        $("input[thirdFlag]").click(function () {
            if ($(this).is(":checked")) {
                var checkid = $(this).attr("thirdFlag");
                $("input[thirdFlag]").each(function () {
                    if ($(this).attr("thirdFlag") != checkid) {
                        $(this).attr("checked", false);
                    }
                });
            }
        });
    </script>
</div>
</html>
