<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>案例查询</title>
</head>
<body>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="form" action="${ctxPath}/case/queryCase" data-table="queryTable" >
      <div class="row">
        <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label" for="casename">案例名称</label>
            <input type="text" id="casename" name="casename"placeholder="案例名称" class="form-control">
          </div>
        </div>
        <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label">案例类型</label>
            <dict:select dictcode="casetype" id="casetype" name="casetype" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
          </div>
        </div>
        <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label">状态</label>
            <dict:select dictcode="status" id="status" name="status" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label">生成时间(开始)</label>
            <input type="text" class="form-control input-sm Wdate" name="createtimebeg" id="createtimebeg" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" >
          </div>
        </div>
        <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label">生成时间(截止)</label>
            <input type="text" class="form-control input-sm Wdate" name="createtimeend" id="createtimeend" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" >
          </div>
        </div>
      </div>
      <div class="row query-div">
        <button type="submit" class="btn btn-w-m btn-success">查询</button>
        <button type="reset" class="btn btn-w-m btn-info">重置</button>
        <button id="export" type="button" class="btn btn-w-m btn-info"><strong>&nbsp;&nbsp;导出</strong></button>
      </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th>案例名称</th>
                <th>案例类型</th>
                <th>案例描述</th>
                <th>生成时间</th>
                <th>创建人姓名</th>
                <th>状态</th>
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

    function getDetail(id,casetype) {
        window.location.href="${ctxPath}/case/getCaseDetail?id="+id+"&casetype="+casetype;
    }

    //绑定form的查询到tables
    $(function(){
      $('form#form').queryTables({
        "pageLength":10,
        columns: [
          {"data": "casename"},
          {"data": "casetype"},
          {"data": "casedesc"},
          {"data": "createtime"},
          {"data": "operatorname"},
          {"data": "status"},
          {"data": "id"}
        ],
        columnDefs:[
          {
            targets:1,
            render:function(data, type, row, meta){
              var dataMap={"0":{text:"可疑交易"},"1":{text:"风险事件"} };
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 3,
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
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
            targets: 6,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "查看", "fn": "getDetail("+row.id+","+row.casetype+")", "cssClass": "fa-wrench"}
                ]
              };
              var html = operTemplate(context);
              return html;
            }
          }
        ]
      });
      $("#export").click(function () {
        location.href = "${ctxPath}/case/exportCaseExcel?" + $("form").serialize();
      });
    });
  </script>
</div>
</html>