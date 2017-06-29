<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
    <title>风险事件管理</title>
</head>
<body>
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="eventForm" id="eventForm" action="${ctxPath}/riskEvent/queryRiskEvent" method="get" data-table="queryTable" >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="id">用户事件编号：</label>
              <input type="text" class="form-control input-sm" id="id" name="id"
                     placeholder="用户事件编号：" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="customerid">用户ID：</label>
              <input type="text" class="form-control input-sm" id="customerid" name="customerid"
                     placeholder="用户ID：" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="operatorname">用户名称：</label>
              <input type="text" class="form-control input-sm" id="operatorname" name="operatorname"
                     placeholder="用户名称" class="form-control">
            </div>
          </div>
        </div>
        <div class="row query-div">
          <%--<a href="toQueryRiskEventAdd??_initQuery=true">
            <button type="button" id="toQueryRiskEventAdd" class="btn btn-w-m btn-primary">
              <strong>新增风险事件</strong>
            </button>
          </a>--%>
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
          <button id="export" type="button" class="btn btn-w-m btn-info"><strong>&nbsp;&nbsp;导出</strong></button>
        </div>
      </form>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选</th>
                <th >客户编号</th>
                <th >事件名称</th>
                <th >事件类型</th>
                <th >处理结果</th>
                <th >操作人</th>
                <th >操作时间</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
            <a href="#">
              <button id ="delete" class="btn-white btn btn-xs"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
            </a>

            <a id="addCase" href="#"  data-toggle="modal" data-target="#myModal">
              <button type="button" id="add" class="btn btn-white  btn-xs">
                <strong>添加案例</strong>
              </button>
            </a>

          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content1">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增案例</h4>
      </div>
      <div class="modal-content">
        加载中...
      </div>

    </div>
  </div>
</div>

<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="choose" value="{{id}}"/>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    <button class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
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
      $("#delete").click(function () {
        var deleteIds = [];
        var result = $("input[name='choose']:checked").length;
        if (result == 0) {
          alert("请选择数据");

        } else {
          if (confirm("确定删除？")) {
            var str = document.getElementsByName("choose");
            for (var i = 0; i < str.length; i++) {
              if (str[i].checked == true) {
                  deleteIds[i]= str[i].value;
              }
            }
            $.ajax({
              type: "POST",
              beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeaderName, csrfToken);
              },
              url: "${ctxPath}/api/1/riskEvent/deleteEvent",
              data: "deleteIds=" + deleteIds,
              dataType: "json",
              success: function (data, textStatus, jqXHR) {
                if (data.code==200) {
                  bootbox.alert("删除成功！", function() {
                    $("#eventForm").submit();
                  });
                } else {
                  bootbox.alert("删除失败！");
                }
              }
            });

          } else {

          }
        }
      });
      /*
       $("input[name='inputTest']")*/

    });



    function getDetail(id,type) {
      $.ajaxParamBack({"url":"${ctxPath}/riskEvent/toAjaxRiskEventDetail","data":"ids="+id+"&dealType="+type});
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

    $("#addCase").click(function(){
      var addIds = ids();
      var len=$("input[name='choose']:checked").length;
      if(len==0){
        bootbox.alert("请选择数据",function(){
          $('#myModal').modal('hide');
        });
      }else{
        $("#addCase").attr("href","${ctxPath}/api/1/riskEvent/_toAddCase?ids="+addIds);
      }
    });

    //获取所选择的id
    function ids(){
      var Ids="";
      var str=document.getElementsByName("choose");
      for (var i=0;i<str.length;i++){
        if(str[i].checked == true){
          if(Ids==""){
            Ids+=str[i].value;
          }else {
            Ids+=","+str[i].value;
          }
        }
      }
      return Ids;
    }

    //绑定form的查询到tables
    $(function(){

      $("#myModal").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
      });

      $('form#eventForm').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "customerid"},
          {"data": "eventname"},
          {"data": "eventtype"},
          {"data": "eventresult"},
          {"data": "operatorname"},
          {"data": "createtime"},
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
            targets:4,
            render:function(data, type, row, meta){
              var dataMap={"0":{"cssClass":"label-danger",text:"未处理"},"1":{"cssClass":"label-primary",text:"已处理"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 6,
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets: 7,
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

      $("#export").click(function () {
        location.href = "${ctxPath}/riskEvent/exportRiskEventExcel?" + $("form").serialize();
      });
    });
  </script>
</div>
</html>
