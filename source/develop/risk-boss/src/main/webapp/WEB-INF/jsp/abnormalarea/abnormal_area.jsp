<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>异常地区代码管理</title>
</head>
<body>
<div id="wrapper" >
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="form" action="${ctxPath}/abnormalarea/queryAbnormalArea"  method="get" data-table="queryTable">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id="id" name="id">
        <div class="row" >
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="code">地区代码:</label>
              <input type="text" class="form-control input-sm" id="code" name="code" value="${abnormalArea.code}" placeholder="" class="form-control">
            </div>
          </div>

          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="name">地区名称:</label>
              <input type="text" class="form-control input-sm" id="name" name="name" value="${abnormalArea.name}" placeholder="" class="form-control">
            </div>
          </div>

        </div>
        <div align="center">
          <button  id="querysubmit"type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
          <a href="${ctxPath}/abnormalarea/_toImportAbnormalArea" data-toggle="modal" data-target="#myModal">
            <button class="btn btn-w-m btn-primary " type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">导入异常地区代码</span></button>
          </a>
          <button id="export" type="button" class="btn btn-w-m btn-info"><strong>&nbsp;&nbsp;导出</strong></button>
        </div>
     </form>
      </div>
        <div class="row" style="margin-top: 50px">
          <div class="col-lg-12">
            <div class="ibox">
              <div class="ibox-content">
                <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                  <thead>
                  <tr>
                    <th>ID</th>
                    <th>地区代码</th>
                    <th>所在省</th>
                    <th>所在市</th>
                    <th>所在县区</th>
                    <th>地区名称</th>
                    <th>修改时间</th>
                    <th>所属反洗钱组织</th>
                    <th>操作</th>
                  </tr>
                  </thead>
                </table>
              </div>
              <br><br>
            </div>>
          </div>
        </div>
    </div>

  </div>
</div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content1">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">操作</h4>
      </div>
      <div class="modal-content">
        加载中...
      </div>

    </div>
  </div>
</div>
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
    var operTpl = $("#oper-tpl").html();
    //预编译模板
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);
    //绑定form的查询到tables
    $(function(){
      $('form#form').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "code"},
          {"data": "provnm"},
          {"data": "citynm"},
          {"data": "countynm"},
          {"data": "name"},
          {"data": "updatetime"},
          {"data": "type"},
          {"data": null}
        ],
        columnDefs:[
          {
            targets:7,
            render:function(data, type, row, meta){
              var dataMap={'1':'某组织1','2':'某组织2'};
              return dataMap[data]||data;
            }
          },

          {
            targets: -1,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "编辑", "fn": "modify("+row.id+")", "cssClass": "fa-pencil"}
                ]
              };
              var html = operTemplate(context);
              return html;
            }
          }
        ]
      });
      $("#export").click(function () {
        location.href = "${ctxPath}/abnormalarea/exportAbnormalArea?" + $("form").serialize()
      });
    });

    function modify(id){
      var code=$("#code").val();
      var name=$("#name").val();
      window.location.href="${ctxPath}/abnormalarea/toModifyAbnormalArea?id="+id+"&code="+code+"&name="+name;
    }
  </script>
</div>
</html>