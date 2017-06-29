<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>查询角色</title>
</head>
<body>
  <div class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
      <div class="ibox-content m-b-sm border-bottom">
        <form action="${ctxPath}/role/queryRole" id="form" role="form" data-table="queryTable">
        <div class="row">
          <div class="col-sm-6">
            <div class="form-group">
              <label class="control-label" for="rolename">角色名称</label>
              <input type="text" id="rolename" name="rolename" value="${roles.rolename}" placeholder="角色名称" class="form-control">
            </div>
          </div>
          <div class="col-sm-6">
            <div class="form-group">
              <label class="control-label" for="status">状态</label>
              <dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
        <div class="row query-div">
          <a href="toAjaxAddRole" class="ajaxback-requst">
            <button type="button" id="toAddRiskrule" class="btn btn-w-m btn-primary">添加</button>
          </a>
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
                  <th><input type="checkbox" name='choosetotal' id="checkAll">全选</th>
                  <th>角色名称</th>
                  <th>说明</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
                </thead>
              </table>
              <a href="#">
                <button type="button" id ="delete" class="btn-white btn btn-xs">
                  <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
              </a>&nbsp;&nbsp;&nbsp;&nbsp;
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
      $.ajaxBack();
      $("#delete").click(function () {
        var deleteIds = [];
        var result = $("input[name='choose']:checked").length;
        if (result == 0) {
          bootbox.alert("请选择数据");

        } else {
          if (confirm("确定删除？")) {
            var str = document.getElementsByName("choose");
            for (var i = 0; i < str.length; i++) {
              if (str[i].checked == true) {
                deleteIds[i]=str[i].value;
              }
            }
            $.ajax({
              type: "POST",
              beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeaderName, csrfToken);
              },
              url: "${ctxPath}/api/1/role/deleteRole",
              data: "ids=" + deleteIds,
              dataType: "json",
              success: function (data, textStatus, jqXHR) {
                if (data.code==200) {
                  bootbox.alert("删除成功！");
                  $('#form').submit();
                } else {
                  bootbox.alert("删除失败！");
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
      var rolename=$("#rolename").val();
      var status=$("#status").val();
      $.ajaxParamBack({"url":"${ctxPath}/role/toAjaxGetRoleDetail","data":"ids="+id+"&dealType="+type+"&rolename="+rolename+"&status="+status});
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
      $('form#form').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "rolename"},
          {"data": "remark"},
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
            targets:3,
            render:function(data, type, row, meta){
              var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 4,
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
