<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>预警设置查询</title>
  <style>
    .dataTables_wrapper{padding-bottom: 0px}
  </style>
</head>
<body>
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="warnsetform" id="warnsetform" action="${ctxPath}/warningset/queryWarningset" method="get" data-table="queryTable">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <div align="left" >
            <a href="${ctxPath}/warningset/toAjaxAddWarnSet" class="ajaxback-requst">
              <button type="button" id="toAddWarnSet" class="btn btn-w-m btn-link">
                <i class="fa fa-plus-circle">
                <strong>新增</strong></i>
              </button>
            </a>
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
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
                      <th>ID</th>
                      <th>操作员ID</th>
                      <th>操作员账号</th>
                      <th>生效时间</th>
                      <th>风险等级</th>
                      <th>预警类型</th>
                      <th>状态</th>
                      <th style="text-align:center">操作</th>
                    </tr>
                    </thead>
                  </table>
                  <a href="#">
                    <button type="button" id ="delete" class="btn btn-white"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除</button>
                  </a>
                </div>
              </div>
            </div>
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
    var csrfToken="${_csrf.token}";
    var csrfHeaderName="${_csrf.headerName}";

    var checkboxSource   = $("#checkbox-tpl").html();
    var checkBoxTemplate = Handlebars.compile(checkboxSource);
    var operTpl = $("#oper-tpl").html();
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    $(function(){
      $.ajaxBack();
    //删除开始
      $("#delete").click(function(){
        var deleteIds="";
        var result=$("input[name='choose']:checked").length;
        if(result==0){
          bootbox.alert("请选择数据");

        }else{
          if(confirm("确定删除？")){
            var str=document.getElementsByName("choose");
            for (var i=0;i<str.length;i++){
              if(str[i].checked == true){
                if(deleteIds==""){
                  deleteIds+=str[i].value;
                }else {
                  deleteIds+=","+str[i].value;
                }
              }
            }
            $.ajax( {
              type : "POST",
              beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeaderName, csrfToken);
              },
              url : "${ctxPath}/api/1/warningset/delWarnSet",
              data : "deleteIds="+deleteIds,
              dataType: "text",
              success : function(data, textStatus, jqXHR) {
                if(data.code==200){
                  bootbox.alert("删除成功！");
                  $('#warnsetform').submit();
                }else{
                  bootbox.alert("删除失败！");
                }
              }
            });

          }else{

          }
        }
      });
   //删除结束

      //查询开始
      $('form#warnsetform').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "id"},
          {"data": "operatorid"},
          {"data": "username"},
          {"data": "effdate"},
          {"data": "risklevel"},
          {"data": "warntype"},
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
            targets:5,
            render:function(data, type, row, meta){
              var dataMap={"00":{text:"高"},"01":{text:"中"},"02":{text:"低"}};
              var map=dataMap[data];
              return statusTemplate(map)||data;
            }
          },

          {
            targets:6,
            render:function(data, type, row, meta){
              var dataMap={"1":{text:"短信"},"2":{text:"邮件"}};
              var map=dataMap[data];
              return statusTemplate(map)||data;
            }
          },
          {
            targets:7,
            render:function(data, type, row, meta){
              var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 8,
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

      //查询结束

    });


    function modify(id){
      $.ajaxParamBack({"url":"${ctxPath}/warningset/toAjaxWarningsetModify","data":"wsId="+id});
    }

    //全选
    function setChecked(){
      var choosetotal=$("input[name='choosetotal']");
      if(choosetotal[0].checked==true){
        $("input[name='choose']").attr("checked",true);
      }else{
        $("input[name='choose']").attr("checked",false);
      }
    }

  </script>
</div>
</html>