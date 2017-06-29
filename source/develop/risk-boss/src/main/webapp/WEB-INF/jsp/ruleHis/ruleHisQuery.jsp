<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>规则历史查询</title>
</head>
<body >
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom" id="divContent">
    <form id="ruleForm" action=""  data-table="queryTable">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" id="groupVersionId" name="groupVersionId" value="${groupVersionId}"/>
      <input type="hidden" id="ruleids" name="ruleids" value="${ruleids}"/>
      <input type="hidden" id="restr" name="restr" value="${restr}"/>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <br>
            <table id="tabId" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th></th>
                <th>规则组</th>
                <th>规则名称</th>
                <th>规则编号</th>
                <th>优先级</th>
                <th>状态</th>
                <th>类别</th>
                <th>修改时间</th>
                <th>修改人</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
      <div class="row query-div">
        <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
      </div>
    </form>
      </div>
  </div>
</div>

<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
      <input type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}" value="{{this.text}}"/>
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
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    function getDetail(id){
      window.location.href="${ctxPath}/ruleHis/toQueryRuleHisDetail?id="+id+"&ruleids="+$("#ruleids").val()+"&restr="+$("#restr").val();
    }
    $(function(){
      $("#back").click(function(){
        window.location.href="${ctxPath}/"+$("#restr").val();
      });
    });
    $(document).ready(function(){
      queryGrouVersionTable();
    });
    //绑定form的查询到tables
    function queryGrouVersionTable(){

      $('table#tabId').DataTable({
        "ajax":"${ctxPath}/api/1/ruleHis/queryRuleHis?groupid="+$("#groupVersionId").val()+"&ruleids="+$("#ruleids").val()+"&restr="+$("#restr").val(),
        "paging": false,
        "deferRender": true,
        "info":false,
        columns: [
          {"data": "id"},
          {"data": "groupVersion"},
          {"data": "rulename"},
          {"data": "rulecode"},
          {"data": "priority"},
          {"data": "status"},
          {"data": "ruleclass"},
          {"data": "createtime"},
          {"data": "updateopername"},
          {"data": null}
        ],
        columnDefs:[
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
            targets:[7],
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
                  {"text": "查看", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                 ]
              };
              var html = operTemplate(context);
              return html;
            }
          }
        ]
      });
    };


  </script>

</div>
</html>
