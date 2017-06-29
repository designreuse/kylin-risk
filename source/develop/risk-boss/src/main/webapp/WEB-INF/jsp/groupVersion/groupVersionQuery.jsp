<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>规则组版本查询</title>
</head>
<body >
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom">
    <form id="ruleForm" action="${ctxPath}/groupVersion/queryGroupVersion"   data-table="queryTable">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="rolename" id="rolename" value="${rolename}"/>
      <div class="row">
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-3 control-label" >归属规则组</label>
            <div class="col-sm-8">
              <select name="groupid" id="rulegroup" class="form-control"></select>
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-3 control-label" >规则组类型</label>
            <div class="col-sm-8">
              <dict:select dictcode="category" id="grouptype" name="grouptype"  nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
      </div>
      <br>
      <div class="row">
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-3 control-label" >版本号</label>
            <div class="col-sm-8">
              <input type="text" class="form-control" name="version" id="version"/>
            </div>
          </div>
        </div>
      </div>
    <div class="row query-div">
      <button type="submit" class="btn btn-w-m btn-success">查询</button>
      <button type="reset" class="btn btn-w-m btn-info">重置</button>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th></th>
                <th>规则组名称</th>
                <th>规则组类型</th>
                <th>版本号</th>
                <th>版本创建人</th>
                <th>版本发布人</th>
                <th>创建时间</th>
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
<script id="sta-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    {{#isRight this.text  this.isissue}}
    <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
    {{else}}
    {{/isRight}}
    {{/each}}
  </div>
</script>
</body>
<div id="siteMeshJavaScript">
  <script src="${libPath}/layer/layer.min.js"></script>
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var operTpl = $("#oper-tpl").html();
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#sta-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    $(document).ready(function(){
      addRuleCategory($("#rulegroup"));
    });
    Handlebars.registerHelper("isRight",function(v1,v2,options){
       if(v1=="发布规则"&&v2!="00"){
        return options.inverse(this);
      }else{
        return options.fn(this);
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

    function getDetail(id){
      window.location.href="${ctxPath}/ruleHis/toQueryRuleHis?groupVersionId="+id+"&restr=groupVersion/toQueryGroupVersion";
    }
    function generatingRule(id,isexecute){
      if(isexecute!="00"){
        bootbox.alert("规则状态不符合");
      }else{
        bootbox.confirm("确认发布规则吗？",function(res){
                  if(res==true){
                    execuGeneratingRule(id);
                  }
                }
        );

      }
    }

    function execuGeneratingRule(id){
      var ii = layer.load();
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/group/generatingRule",
        data: "id=" + id,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          layer.close(ii);
          if (data.code==200) {
            bootbox.alert("规则执行成功！",function(){
              $('#ruleForm').submit();
            });
          } else {
            bootbox.alert("规则执行失败，请检查子规则！");
          }
        }
      });
    }
    $(function(){
      $('form#ruleForm').queryTables({
        "pageLength":10,
        "columns": [
          {"data": "id"},
          {"data": "groupname"},
          {"data": "grouptype"},
          {"data": "version"},
          {"data": "createopername"},
          {"data": "issueopername"},
          {"data": "createtime"},
          {"data": "updatetime"},
          {"data": null}
        ],columnDefs:[
          {
            targets: 2,
            render:function(data, type, row, meta){
              var dataMap={"scorerule":{"cssClass":"label-primary",text:"评分规则"},"logicrule":{"cssClass":"label-danger",text:"逻辑规则"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets:[6,7],
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets:8,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "查看规则", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                  {"text": "发布规则", "fn": "generatingRule('"+row.id+"','"+row.isissue+"')",
                    "cssClass": "fa-tasks","isissue":row.isissue}
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
