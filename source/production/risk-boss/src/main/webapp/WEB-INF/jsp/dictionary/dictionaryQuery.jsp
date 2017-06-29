<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>数据字典查询</title>
</head>
<body>
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="dicform" id="dicform" action="${ctxPath}/dictionary/queryDictionary" method="get" data-table="queryTable" >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="dictcode">字典编号：</label>
              <input type="text" class="form-control input-sm" id="dictcode" name="dictcode"
                     placeholder="字典编号" class="form-control">
            </div>
          </div>

          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="dictname">字典名称：</label>
              <input type="text" class="form-control input-sm" id="dictname" name="dictname"
                     placeholder="字典名称" class="form-control">
            </div>
          </div>

          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="code">数值</label>
              <input type="text" class="form-control input-sm" id="code" name="code"
                     placeholder="数值" class="form-control">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="name">解释</label>
              <input type="text" class="form-control input-sm" id="name" name="name"
                     placeholder="解释" class="form-control">
              </select>
            </div>
          </div>
        </div>
        <div class="row query-div">
          <a href="${ctxPath}/dictionary/toAjaxAddDictionary" class="ajaxback-requst">
            <button type="button" id="toAddDictionary" class="btn btn-w-m btn-primary">
              <strong>添加</strong>
            </button>
          </a>
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
          <button type="button" id="freshInfo" class="btn btn-w-m btn-warning"><strong>刷新缓存</strong></button>
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
                <th >字典编号</th>
                <th >字典名称</th>
                <th >数值</th>
                <th >解释</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
            <a href="#">
              <button type="button" id ="delete" class="btn-white btn btn-xs"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
            </a>
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
        var deleteIds = "";
        var result = $("input[name='choose']:checked").length;
        if (result == 0) {
          alert("请选择数据");

        } else {
          if (confirm("确定删除？")) {
            var str = document.getElementsByName("choose");
            for (var i = 0; i < str.length; i++) {
              if (str[i].checked == true) {
                if (deleteIds == "") {
                  deleteIds += str[i].value;
                } else {
                  deleteIds += "," + str[i].value;
                }
              }
            }
            $.ajax({
              type: "POST",
              beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeaderName, csrfToken);
              },
              url: "${ctxPath}/api/1/dictionary/deleteDic",
              data: "deleteIds=" + deleteIds,
              dataType: "text",
              success: function (data, textStatus, jqXHR) {
                if (textStatus == "success") {
                  bootbox.alert("删除成功！",function(){
                    $("#dicform").submit();
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

    /*function reSet(id) {
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/operator/reSetPWD",
        data: "id=" + id,
        dataType: "text",
        success: function (data, textStatus, jqXHR) {
          if (data.code = 200) {
            alert("重置成功！");
          } else {
            alert("重置失败！");
          }

        }
      });

    }*/
    function modify(id) {
      $.ajaxParamBack({"url":"${ctxPath}/dictionary/toAjaxDictionaryDetail","data":"id="+id});
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

    //绑定form的查询到tables
    $(function(){
      $('form#dicform').queryTables({
        "pageLength":10,
        "bAutoWidth": false,
        columns: [
          {"data": "id"},
          {"data": "dictcode"},
          {"data": "dictname"},
          {"data": "code"},
          {"data": "name"},
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
            targets: 5,
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
    });

    $(function(){
      $("#freshInfo").click(function(){
        $("#dicform").attr("action" ,"${ctxPath}/dictionary/queryFreshDic");
        $('#dicform').submit();
        $("#dicform").attr("action" ,"${ctxPath}/dictionary/queryDictionary");
      });
    });
  </script>
</div>
</html>