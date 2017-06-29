
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>规则历史详细</title>
</head>
<body>
<form id="addRuleform" method="post" data-parsley-validate class="form-horizontal">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="initialtype" id="initialtype" value="${rule.groupVersion.groupid}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content   float-e-margins">
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" >归属规则组:</label>
                <div class="col-sm-3 m-b">
                    <select id="grouprule" name="groupid" class="form-control" required></select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
              <label class="col-sm-1 control-label " >规则名称:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="rulename" name="rulename" value="${rule.rulename}" required disabledFlag>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="priority">优先级:</label>
                <div class="col-sm-3">
                    <input class="form-control" name="priority" id="priority" value="${rule.priority}" disabledFlag/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="result">触发结果(得分):</label>
                <div class="col-sm-3">
                    <select class="form-control" id="result" name="result" required  value="${rule.result}" disabledFlag>
                        <option></option>
                        <option myvalue="forbid" <c:if test="${rule.result=='forbid'}">selected</c:if>>禁入</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="status">状态:</label>
                <div class="col-sm-3">
                    <dict:select dictcode="status" id="status" name="status" value="${rule.status}" escapeValue="02,03,99,04" nullLabel="请选择" nullOption="true" cssClass="form-control" ></dict:select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="remark">描述:</label>
                <div class="col-sm-7">
                    <textarea class="form-control" name="remark" id="remark" disabledFlag>${rule.remark}</textarea>
                </div>
            </div>
        </div>
        <c:forEach items="${factors}" var="factor" varStatus="status">
            <div class="row" id="addRow${status.index}">
                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-11 form-inline">
                        <select class="form-control span1" name="logicsym" id="logicsym${status.index}">
                            <option value=""></option>
                            <option value="&&" <c:if test="${factor.logicsym=='&&'}">selected</c:if>>且</option>
                            <option value="||" <c:if test="${factor.logicsym=='||'}">selected</c:if>>或</option>
                        </select>
                        <br>
                        <select class="form-control span1" name="leftbrac" id="leftbrac${status.index}">
                            <option value=""></option>
                            <option value="(" <c:if test="${factor.leftbrac=='('}">selected</c:if>>(</option>
                        </select>
                        <select class="form-control" SIGN="factor" name="factor" id="factor${status.index}">
                            <option value=""></option>
                            <c:forEach items="${templs}" var="templ">
                                <option value="${templ.code}" <c:if test="${templ.code==factor.fields}">selected</c:if>>${templ.name}</option>
                            </c:forEach>
                        </select>
                        <select class="form-control span1" name="conditions" id="conditions${status.index}">
                            <option value=""></option>
                            <option value="==" <c:if test="${factor.conditions=='=='}">selected</c:if>>=</option>
                            <option value="!=" <c:if test="${factor.conditions=='!='}">selected</c:if>>!=</option>
                            <option value=">" <c:if test="${factor.conditions=='>'}">selected</c:if>>&gt;</option>
                            <option value=">=" <c:if test="${factor.conditions=='>='}">selected</c:if>>&gt;=</option>
                            <option value="<" <c:if test="${factor.conditions=='<'}">selected</c:if>>&lt;</option>
                            <option value="<=" <c:if test="${factor.conditions=='<='}">selected</c:if>>&lt;=</option>
                        </select>
                        <select class="form-control span1" name="conditionvals" id="conditionvals${status.index}" value="${factor.conditionvals}"  disabledFlag>
                            <option></option>
                            <c:forEach items="${ruleparams}" var="ruleparam">
                                <option myvalue="${ruleparam.code}" <c:if test="${factor.conditionvals==ruleparam.code}">selected</c:if>>${ruleparam.name}</option>
                            </c:forEach>
                        </select>

                        <select class="form-control span1" name="rightbrac" id="rightbrac${status.index}">
                            <option value=""></option>
                            <option value=")" <c:if test="${factor.rightbrac==')'}">selected</c:if>>)</option>
                        </select>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="row query-div">
          <input type="button" id="back1" class="btn btn-w-m btn-default" value="返回">
        </div>
      </div>
    </div>
  </div>
</form>
<script id="option-tpl" type="text/x-handlebars-template">
    <option></option>
    {{#each params}}
    <option myvalue="{{code}}">{{name}}</option>
    {{/each}}
</script>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    var csrfToken="${_csrf.token}";
    var csrfHeaderName="${_csrf.headerName}";
    var optionSource= $("#option-tpl").html();
    var optionTemplate = Handlebars.compile(optionSource);
    var params;
    $(document).ready(function() {
        $("#back1").backup();
        params = queryRuleParam();
        $("select[id^='conditionval'],select[id^='result']").each(function () {
            $(this).editableSelect({
                effects: 'slide'
            });
        });
    });
    $(document).ready(function() {
        $("[disabledFlag]").each(function (){
            $(this).attr("disabled","disabled");
        });
        $("select").each(function(){
            $(this).attr("disabled","disabled");
        });
        addRuleCategory($("#grouprule"),$("#initialtype").val());
    });

    function queryRuleParam(){
        $.ajaxSettings.async = false;
        var params;
        $.getJSON("${ctxPath}/api/1/rule/queryDicByDictcode",function(json){
            params = json.data;
        });
        return params;
    }
    function addRuleCategory(factorSelect,initype){
        factorSelect.append("<option value=''>---请选择---</option>");
        $.getJSON("${ctxPath}/api/1/group/queryAllGroup",function(json){
            $.each(json.data, function(index,factors){
                if(initype==factors['id']){
                    factorSelect.append('<option value="' + factors['id'] +  '" selected="selected">' + factors['groupname'] + '</option>');
                }else{
                    factorSelect.append('<option value="' + factors['id'] +  '">' + factors['groupname'] + '</option>');
                }
            });

        });
    }
  </script>
</div>
</html>
