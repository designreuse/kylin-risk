<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>规则管理查询</title>
</head>
<body >
<div class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
    <div class="ibox-content m-b-sm border-bottom" id="divContent">
    <form id="ruleForm" action="${ctxPath}/rule/queryRule"  data-table="queryTable">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="rolename" id="rolename" value="${rolename}"/>
    <div class="row">
      <div class="col-sm-6">
        <div class="form-group">
          <label class="col-sm-2 control-label" >规则名称</label>
          <div class="col-sm-10">
            <input type="text" id="rulename" name="rulename"  placeholder="规则名称" class=" form-control">
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <div class="form-group">
          <label class="col-sm-2 control-label" >规则编号</label>
          <div class="col-sm-10">
            <input type="text" id="rulecode" name="rulecode"  placeholder="规则编号" class=" form-control">
          </div>
        </div>
      </div>
    </div>
      <br/>
    <div class="row">
      <div class="col-sm-6">
        <div class="form-group">
          <label class="col-sm-2 control-label" >归属规则组</label>
          <div class="col-sm-10">
            <select name="groupid" id="rulegroup" class="form-control"></select>
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <div class="form-group">
          <label class="col-sm-2 control-label" >状态</label>
          <div class="col-sm-10">
            <dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true"  cssClass="form-control"></dict:select>
          </div>
        </div>
      </div>
    </div>
      <br>
    <div class="row">
      <div class="col-sm-6">
        <div class="form-group">
          <label class="col-sm-2 control-label" >类别</label>
          <div class="col-sm-10">
            <dict:select dictcode="ruleclass" id="ruleclass" name="ruleclass" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
          </div>
        </div>
      </div>
    </div>
    <div class="row query-div">
      <c:if test="${rolename=='SUPERMODIFIER'}">
        <a href="toAjaxAddRule" class="ajaxback-requst">
          <button type="button" id="toAddRule" class="btn btn-w-m btn-primary">添加</button>
        </a>
      </c:if>
      <button type="submit" class="btn btn-w-m btn-success">查询</button>
      <button type="reset" class="btn btn-w-m btn-info">重置</button>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <c:if test="${rolename=='MODIFIER'||rolename=='SUPERMODIFIER'}">
              <a href="#">
                <button type="button"  id ="deleteSelected" class="btn-white btn btn-xs">
                  <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
              </a>
              <a id="active" href="#"  data-toggle="modal" data-target="#myModal">
                <button type="button" id="statusActive" class="btn btn-white  btn-xs">
                  <i class="fa fa-play"></i>
                  <strong>开启</strong>
                </button>
              </a>
              <a id="inactive" href="#"  data-toggle="modal" data-target="#myModal">
                <button type="button" id="statusInactive" class="btn btn-white  btn-xs">
                  <i class="fa fa-pause"></i>
                  <strong>关闭</strong>
                </button>
              </a>
            </c:if>
            <br>
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选</th>
                <th>规则组</th>
                <th>规则名称</th>
                <th>规则编号</th>
                <th>优先级</th>
                <th>状态</th>
                <th>类别</th>
                <th>创建人</th>
                <th>最后修改时间</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
    </form>
      </div>
  </div>
</div>

<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    {{#isRight this.text}}
      <input type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}" value="{{this.text}}"/>
    {{else}}
    {{/isRight}}
    {{/each}}
  </div>
</script>
<script id="status-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>

<script id="checkbox-tpl" type="text/x-handlebars-template">
  {{#checkManager ruleclass}}
  <input type="checkbox" name="choose" value="{{id}}"/>
  {{else}}
  {{/checkManager}}
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

    Handlebars.registerHelper("isRight",function(v1,options){
      var rolename = $("#rolename").val();
      if(v1=="删除"&&(rolename!="MODIFIER"&&rolename!="SUPERMODIFIER")){
        return options.inverse(this);
      }else{
        return options.fn(this);
      }
    });

    Handlebars.registerHelper("checkManager",function(v1,options){
      var rolename = $("#rolename").val();
      if(rolename=="SUPERMODIFIER"||(rolename=="MODIFIER"&&v1=="01")){
        return options.fn(this);
      }else{
        return options.inverse(this);
      }
    });

    $(document).ready(function(){
      $.ajaxBack();
      addRuleCategory($("#rulegroup"));
    });

    $("#deleteSelected").click(function(){
      var selectIds = getSelectedId();
      if (selectIds!="") {
          deleteRule(selectIds);
      }
    });
    $("#statusInactive").click(function(){
      var selectIds = getSelectedId();
      if (selectIds!=""&&confirm("确定关闭规则？")) {
        updateRuleStatus(selectIds,"false");
      }
    });
    $("#statusActive").click(function(){
      var selectIds = getSelectedId();
      if (selectIds!=""&&confirm("确定开启规则？")) {
        updateRuleStatus(selectIds,"true");
      }
    });

    function addRuleCategory(factorSelect){
      factorSelect.append("<option value=''>---请选择---</option>");
      $.getJSON("${ctxPath}/api/1/group/queryAllGroup",function(json){
        $.each(json.data, function(index,groups){
          factorSelect.append('<option value="' + groups['id']  + '">' + groups['groupname'] + '</option>');
        });

      });
    }

    function getSelectedId(){
      var selectIds = "";
      var result = $("input[name='choose']:checked");
      if (result.length == 0) {
        alert("请选择数据");
      } else {
        for (var i = 0; i < result.length; i++) {
          selectIds = selectIds + result[i].value + ",";
        }
        selectIds = selectIds.substr(0, selectIds.length - 1);

      }
      return selectIds;
    }

    function updateRuleStatus(ids,status){
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/rule/modifyRuleStatus",
        data: "id=" + ids+"&status="+status,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          if (data.code==200) {
            bootbox.alert("修改成功，重新发布规则后生效！",function(){
              $('#ruleForm').submit();
            });
          } else {
            bootbox.alert("修改失败！");
          }
        }
      });
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

    function getDetail(id){
      $.ajaxParamBack({"url":"${ctxPath}/rule/toAjaxQueryRuleDetail","data":"id="+id});
    }

    function getIsEditable(length){
      var rolename = $("#rolename").val();
      if(length>1 && rolename=="ONE"||rolename==""){
        return false;
      }else{
        return true;
      }
    }
    function deleteRule(id){
      if (confirm("确定删除？")) {
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/rule/deleteRule",
        data: "id=" + id,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          if (data.code==200) {
            bootbox.alert("删除成功！",function(){
              $('#ruleForm').submit();
            });
          } else {
            bootbox.alert("删除失败！");
          }
        }
      });
      }
    }
    //绑定form的查询到tables
    $(function(){
      $('form#ruleForm').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "group"},
          {"data": "rulename"},
          {"data": "rulecode"},
          {"data": "priority"},
          {"data": "status"},
          {"data": "ruleclass"},
          {"data": "createopername"},
          {"data": "updatetime"},
          {"data": null}
        ],
        columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({"id":data,"ruleclass":row.ruleclass});
            }
          },
          {
            targets:1,
            render:function(data, type, row, meta){
              return data&&data.groupname||"";
            }
          },
          {
            targets:5,
            render:function(data, type, row, meta){
              var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets:6,
            render:function(data, type, row, meta){
              var dataMap={"00":{"cssClass":"label-primary",text:"预置规则"},"01":{"cssClass":"label-success",text:"自定义规则"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },

          {
            targets:[8],
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets: 9,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "编辑", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                  {"text": "删除", "fn": "deleteRule("+row.id+")", "cssClass": "fa-pencil"}

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
