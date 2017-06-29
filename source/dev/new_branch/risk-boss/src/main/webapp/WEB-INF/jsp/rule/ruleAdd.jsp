
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>规则管理添加</title>
</head>
<body>
<form id="addRuleform" method="post" data-parsley-validate class="form-horizontal">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="rulecategory" id="rulecategory"/>
    <input type="hidden" name="fields" id="fields"/>
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
                <input class="form-control " type="text" id="rulename" name="rulename" required>
              </div>
            </div>
        </div>

        <c:if test="${rolename=='SUPERMODIFIER'}">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-1 control-label" >类别:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="ruleclass" id="ruleclass" name="ruleclass" cssClass="form-control" required="true"></dict:select>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${rolename=='MODIFIER'}">
            <input type="hidden" name="ruleclass" id="ruleclass" value="01">
        </c:if>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="priority">优先级:</label>
                <div class="col-sm-3 tooltip-demo">
                    <input class="form-control" name="priority" id="priority" required number data-toggle="tooltip" data-parsley-range="[0,99999999]" data-placement="right" title="仅支持数字，数值越大优先级越高"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="result">触发结果(得分):</label>
                <div class="col-sm-3 tooltip-demo">
                    <select class="form-control" id="result" name="result" data-toggle="tooltip" data-placement="right"  required title="评分规则仅支持数字和“禁入”，逻辑规则支持自定义和“禁入”">
                        <option></option>
                        <option myvalue="forbid">禁入</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="status">状态:</label>
                <div class="col-sm-3">
                    <dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true" cssClass="form-control" require="true"></dict:select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-1 control-label" for="remark">描述:</label>
                <div class="col-sm-7">
                    <textarea class="form-control" name="remark" id="remark"></textarea>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-offset-1 col-sm-10">
                <p class="text-danger">注意：因子条件为">" 或 ">=" 或 "<" 或 "<="时，结果仅支持数字，其他情况结果仅支持数字和下拉选项</p>
            </div>
        </div>
        <div class="row" id="addRow0">
            <div class="form-group">
                <label class="col-sm-1 control-label" >规则逻辑:</label>
                <div class="col-sm-11 form-inline">
                    <select class="form-control span1"  name="leftbrac" id="leftbrac0">
                        <option value=""></option>
                        <option value="(">(</option>
                    </select>
                    <select class="form-control" SIGN="factor"  name="factor" id="factor0">
                        <option value="">---请选择---</option>
                    </select>
                    <select class="form-control span1" name="conditions" id="conditions0">
                        <option value=""></option>
                        <option value="==">=</option>
                        <option value="!=">!=</option>
                        <option value=">">&gt;</option>
                        <option value=">=">&gt;=</option>
                        <option value="<">&lt;</option>
                        <option value="<=">&lt;=</option>
                    </select>
                    <select class="form-control" id="conditionvals0" name="conditionvals" ></select>

                    <select class="form-control span1"  name="rightbrac" id="rightbrac0">
                        <option value=""></option>
                        <option value=")">)</option>
                    </select>
                    <a href="javascript:;" id="adddiv0" onclick="addDiv('0')"><li class="fa fa-plus"></li></a>
                </div>
            </div>
        </div>
        <center>
          <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
        </center>
      </div>
    </div>
  </div>
</form>
<script id="adddiv-tpl" type="text/x-handlebars-template">
    <div class="row" id="addRow{{idIndex}}">
        <div class="form-group">
            <div class="col-sm-offset-1 col-sm-11 form-inline">
                <select class="form-control span1"  name="logicsym" id="logicsym{{idIndex}}">
                    <option value=""></option>
                    <option value="&&">且</option>
                    <option value="||">或</option>
                </select>
                <br>
                <select class="form-control span1"  name="leftbrac" id="leftbrac{{idIndex}}">
                    <option value=""></option>
                    <option value="(">(</option>
                </select>
                <select class="form-control" SIGN="factor"  name="factor" id="factor{{idIndex}}">

                </select>
                <select class="form-control span1"  name="conditions" id="conditions{{idIndex}}">
                    <option value=""></option>
                    <option value="==">=</option>
                    <option value="!=">!=</option>
                    <option value=">">&gt;</option>
                    <option value=">=">&gt;=</option>
                    <option value="<">&lt;</option>
                    <option value="<=">&lt;=</option>
                </select>
                <select class="form-control" id="conditionvals{{idIndex}}" name="conditionvals" ></select>

                <select class="form-control span1"  name="rightbrac" id="rightbrac{{idIndex}}">
                    <option value=""></option>
                    <option value=")">)</option>
                </select>
                <a href="javascript:;" id="adddiv{{idIndex}}" onclick="addDiv('{{idIndex}}')"><li class="fa fa-plus"></li></a>
                <a href="javascript:;" id="deldiv{{idIndex}}" onclick="delDiv('{{idIndex}}')"><li class="fa fa-minus"></li></a>
            </div>
        </div>
    </div>
</script>
<script id="option-tpl" type="text/x-handlebars-template">
    <option></option>
    {{#each params}}
        <option myvalue="{{code}}">{{name}}</option>
    {{/each}}
</script>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    var idIndex = 0;
    var csrfToken="${_csrf.token}";
    var csrfHeaderName="${_csrf.headerName}";
    var addDivSource= $("#adddiv-tpl").html();
    var addDivTemplate = Handlebars.compile(addDivSource);
    var optionSource= $("#option-tpl").html();
    var optionTemplate = Handlebars.compile(optionSource);
    var params;
    var ruleTempls;
    $(document).ready(function() {
        params = queryRuleParam();
        $("select[id^='conditionval']").each(function () {
            selectEditable($(this));
        });
        $("#result").editableSelect({
            effects: 'slide'
        });
        addRuleCategory($("#grouprule"));
        $("#back").backup();
    });

    function addDiv(curIdIndex){
        var addIndex = Number(idIndex)+1;
        $("#addRow"+curIdIndex).after(addDivTemplate({"idIndex":addIndex}));
        //$("#adddiv"+curIdIndex).hide();
        addQueryFactor($("#factor"+addIndex),ruleTempls);
        selectEditable($("#conditionvals"+addIndex));
        idIndex++;
    }

    function selectEditable(obj){
        obj.append(optionTemplate({"params":params}));
        obj.editableSelect({
            effects: 'slide'
        });
    }

    function delDiv(delIdIndex){
        /*if($("#adddiv"+delIdIndex+":visible").length!=0){
            var preIndex = $("#addRow"+delIdIndex).prev().attr("id").substr(6,1);
            $("#adddiv"+preIndex).show();
        }*/
        $("#addRow"+delIdIndex).remove();
    }
    $(function(){
        $("#grouprule").change(function(){
            var groupid = this.value;
            ruleTempls = getTemplsAjax(groupid);
            $("select[SIGN='factor']").each(function(){
                var factorSelect = $(this);
                addQueryFactor(factorSelect,ruleTempls);
            });

        });
    });

    function getTemplsAjax(groupid){
        $.ajaxSettings.async = false;
        var templs;
        $.getJSON("${ctxPath}/api/1/factorTempl/queryByGroup",{groupid:groupid},function(json){
            templs = json.data;
        });
        return templs;
    }
    function addQueryFactor(factorSelect,templs){
        factorSelect.empty();
        factorSelect.append("<option value=''>---请选择---</option>");
        if(templs!=undefined){
            $.each(templs, function(index,factors){
                factorSelect.append('<option value="' + factors['code'] + '">' + factors['name'] + '</option>');
            });
        }
    }
    function addRuleCategory(factorSelect){
        factorSelect.append("<option value=''>---请选择---</option>");
        $.getJSON("${ctxPath}/api/1/group/queryAllGroup",function(json){
            $.each(json.data, function(index,groups){
                factorSelect.append('<option value="' + groups['id']  + '">' + groups['groupname'] + '</option>');
            });

        });
    }
    function queryRuleParam(){
        $.ajaxSettings.async = false;
        var params;
        $.getJSON("${ctxPath}/api/1/rule/queryDicByDictcode",function(json){
            params = json.data;
        });
        return params;
    }

    function changeResult(categorySelect){
        var typeflag = $(categorySelect).find("option:selected").attr("id");
        $("#rulecategory").val(typeflag);
    }

    $(function(){
      $("#submitAdd").click(function(){
          jointFieldsParam();
          subRuleAdd();
      });
    });

    function subRuleAdd(){
        var valid=$("#addRuleform").parsley().validate();
        if(valid ){
            $("#addRuleform").ajaxSubmit({
                type:'post',
                dataType: 'json',
                url: '${ctxPath}/api/1/rule/addRule',
                success: function(data,textStatus,jqXHR){
                    if(data.code==200){
                        bootbox.alert("添加成功！", function () {
                            $.backquery();
                        });
                    }else{
                        bootbox.alert("添加失败！", function () {
                        });
                    }
                 }
            });
        }
    }

    function jointFieldsParam(){
        var fields="";
        $("select[id^='factor']").each(function(){
            var index = $(this).attr("id").substr(6);
            var fieldTem = $("#leftbrac"+index).val()+$("#factor"+index).val()+$("#rightbrac"+index).val()+",";
            fields +=fieldTem;
        });
        var fieldsVal = fields.substring(0,fields.length-1);
        $("#fields").val(fieldsVal);
    }


  </script>
</div>
</html>
