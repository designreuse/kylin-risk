<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>课程类型查询</title>
</head>
<body >
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
    <form id="courseTypeForm" action="${ctxPath}/courseType/queryCourseType"   data-table="queryTable">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="rolename" id="rolename" value="${rolename}"/>
      <div class="row">
        <div class="col-sm-6">
          <div class="form-group">
            <label class="col-sm-2 control-label" >课程类型名称</label>
            <div class="col-sm-10">
              <input type="text" id="coursetypename" name="coursetypename" placeholder="课程类型名称" class="form-control">
            </div>
          </div>
        </div>
      </div>
    <div class="row query-div">
      <a href="toAjaxAddCourseType" class="ajaxback-requst">
        <button type="button" id="toAddCourseType" class="btn btn-w-m btn-primary">添加</button>
      </a>
      <button type="submit" class="btn btn-w-m btn-success">查询</button>
      <button type="reset" class="btn btn-w-m btn-info">重置</button>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <a href="#">
              <button type="button"  id ="deleteSelected" class="btn-white btn btn-xs">
                <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
            </a>
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选<span class="footable-sort-indicator"></span></th>
                <th>课程类型id</th>
                <th>课程类型名称</th>
                <th>创建时间</th>
                <th>修改时间</th>
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

    //全选
    function setChecked(){
      var choosetotal=$("input[name='choosetotal']");
      if(choosetotal[0].checked==true){
        $("input[name='choose']").attr("checked",true);
      }else{
        $("input[name='choose']").attr("checked",false);
      }
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

    $(function(){
      $.ajaxBack();
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
          deleteCourseType(selectIds);
        }
      });
    });

    function deleteCourseType(ids){
      if (confirm("确定删除？")) {
        $.ajax({
          type: "POST",
          beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfToken);
          },
          url: "${ctxPath}/api/1/courseType/deleteCourseType",
          data: "id=" + ids,
          dataType: "json",
          success: function (data, textStatus, jqXHR) {
            if (data.code==200) {
              bootbox.alert("删除成功！");
              $('#courseTypeForm').submit();
            } else {
              bootbox.alert("删除失败！");
              $('#courseTypeForm').submit();
            }
          }
        });
      }
    }

    function getDetail(id){
      $.ajaxParamBack({"url":"${ctxPath}/courseType/toAjaxQueryCourseTypeDetail","data":"id="+id});
    }

    $(function(){
      $('form#courseTypeForm').queryTables({
        "pageLength":10,
        "columns": [
          {"data": "id"},
          {"data": "id"},
          {"data": "coursetypename"},
          {"data": "createtime"},
          {"data": "updatetime"},
          {"data": null}
        ],columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({"id":data});
            }
          },
          {
            targets:3,
            render:function(data, type, row, meta){
              return data.substring(0,19);
            }
          },
          {
            targets:4,
            render:function(data, type, row, meta){
              return data.substring(0,19);
            }
          },
          {
            targets: 5,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "编辑", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                  {"text": "删除", "fn": "deleteCourseType("+row.id+")", "cssClass": "fa-pencil"}

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
