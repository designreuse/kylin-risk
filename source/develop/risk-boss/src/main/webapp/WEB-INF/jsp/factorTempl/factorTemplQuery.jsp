<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子模板查询</title>
</head>
<body >
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom">
    <form id="ruleForm" action="${ctxPath}/factorTempl/queryFactorTempl"   data-table="queryTable">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="rolename" id="rolename" value="${rolename}"/>
      <div class="row">
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-2 control-label" >因子名称</label>
            <div class="col-sm-10">
              <input type="text" id="factorname" name="factorname"  placeholder="因子名称" class="form-control">
            </div>
          </div>
        </div>
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-2 control-label" >归属规则组</label>
            <div class="col-sm-10">
              <select name="groupid" id="rulegroup" class="form-control"></select>
            </div>
          </div>
        </div>
      </div>
    <div class="row query-div">
      <a href="toAddFactorTempl">
        <button type="button" id="toAddFactorTempl" class="btn btn-w-m btn-primary">添加</button>
      </a>
      <button type="submit" class="btn btn-w-m btn-success">查询</button>
      <button type="reset" class="btn btn-w-m btn-info">重置</button>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <!--<a href="#">
              <button type="button"  id ="deleteSelected" class="btn-white btn btn-xs">
                <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
            </a>-->
            <br>
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th></th>
                <th>因子名称</th>
                <th>规则组</th>
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
<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="choose" value="{{id}}"/>
</script>
<script id="sta-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
    {{/each}}

  </div>
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
    var statusSource   = $("#sta-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    function addRuleCategory(factorSelect){
      factorSelect.append("<option value=''>---请选择---</option>");
      $.getJSON("${ctxPath}/api/1/group/queryAllGroup",function(json){
        $.each(json.data, function(index,factors){
          factorSelect.append('<option value="' + factors['id'] + '" id="' + factors['id'] + '">' + factors['groupname'] + '</option>');
        });

      });
    }

    $(function(){
      addRuleCategory($("#rulegroup"));
      $("#deleteSelected").click(function(){
        var selectIds = "";
        var result = $("input[name='choose']:checked");
        if (result.length == 0) {
          bootbox.alert("请选择数据");

        } else {
          for(var i=0;i<result.length;i++){
            selectIds=selectIds+result[i].value+",";
          }
          selectIds = selectIds.substr(0,selectIds.length-1);
          deleteTempl(selectIds);
        }
      });
    });

    function getDetail(id){
      window.location.href="${ctxPath}/factorTempl/queryFactorTemplDetail?id="+id;
    }
    function deleteTempl(ids){
      if (confirm("确定删除？")) {
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/factorTempl/deleteFactorTempl",
        data: "id=" + ids,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          if (data.code==200) {
            bootbox.alert("删除成功！");
            $('#ruleForm').submit();
          } else {
            bootbox.alert("删除失败！");
            $('#ruleForm').submit();
          }
        }
      });
      }
    }

    $(function(){
      $('form#ruleForm').queryTables({
        "pageLength":10,
        "columns": [
          {"data": "id"},
          {"data": "name"},
          {"data": "group"},
          {"data": null}
        ],columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({"id":data});
            }
          },
          {
            targets:2,
            render:function(data, type, row, meta){
              return data&&data.groupname||"";
            }
          },
          {
            targets: 3,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "编辑", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                  //{"text": "删除", "fn": "deleteTempl("+row.id+")", "cssClass": "fa-pencil"}
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
