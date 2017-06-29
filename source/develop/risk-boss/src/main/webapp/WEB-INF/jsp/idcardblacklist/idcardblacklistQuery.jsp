<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>身份证黑名单</title>
  <style>
    .dataTables_wrapper{padding-bottom: 0px}
  </style>
</head>
<body>
<div id="wrapper" >
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="form" action="${ctxPath}/idcardblack/queryIdCardBlackList"  method="get" data-table="queryTable">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" id="flag" value="${flag}">
        <div class="row">
          <div class="col-sm-4">
            <label class="control-label" for="name">名称</label>
            <input type="text" class="form-control input-sm" id="name" name="name" placeholder="名称" class="form-control">
          </div>
          <div class="col-sm-4">
            <label class="control-label" for="identnum">身份照号码</label>
            <input type="text" class="form-control input-sm" id="identnum" name="identnum" placeholder="身份照号码" class="form-control">
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="status">状态</label>
              <dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
        <div align="center" >
          <button type="submit" class="btn btn-w-m btn-success">查 询</button>
           <c:if  test="${flag=='02'}">
            <a href="${ctxPath}/idcardblack/_toImportBlack" data-toggle="modal" data-target="#myModal">
              <button class="btn btn-w-m btn-primary " type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">导入黑名单</span></button>
            </a>
          </c:if>
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
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选<span class="footable-sort-indicator"></span></th>
                <th>姓名</th>
                <th>证件类型</th>
                <th>身份证号码</th>
                <th>状态</th>
                <th>创建人</th>
                <th>创建时间</th>
              </tr>
              </thead>
            </table>
            <c:if  test="${flag=='01'}">
              <a href="#">
                <button type="button" id ="bathVerify" class="btn btn-white"><i class="fa fa-github-square"></i>&nbsp;&nbsp;审核</button>
              </a>
            </c:if>
            <c:if  test="${flag=='02'}">
              <a href="#">
                <button type="button" id ="delete" class="btn btn-white"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;删除</button>
              </a>
              <a href="#">
                <button type="button" id ="bathRemove" class="btn btn-white"><i class="fa fa-wrench"></i>&nbsp;&nbsp;移除</button>
              </a>
            </c:if>
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
        <h4 class="modal-title" id="myModalLabel">操作</h4>
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
    //绑定form的查询到tables
    $(function(){
      var flag=$("#flag").val();
      $('form#form').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "name"},
          {"data": "identtype"},
          {"data": "identnum"},
          {"data": "status"},
          {"data": "username"},
          {"data": "createtime"},
        ],
        columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              if(flag=="01"){
                if(row["status"]=="02"||row["status"]=="03"){
                  return checkBoxTemplate({id:data});
                }else{
                  return "";
                }
              }else{
                if(row["status"]=="00"||row["status"]=="01"||row["status"]=="04"){
                  return checkBoxTemplate({id:data});
                }else{
                  return "";
                }
              }
            }
          },
          {
            targets:2,
            render:function(data, type, row, meta){
              return "身份证";
            }
          },
          {
            targets:4,
            render:function(data, type, row, meta){
              var dataMap={'00':'有效','01':'无效','02':'待审核','03':'待审核','04':'审核拒绝'};
              return dataMap[data]||data;
            }
          },
          {
            targets:6,
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
          }
          }
        ]
      });

      $("#myModal").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
      });

      $("#delete").click(function(){
        var deleteIds=ids();
        var len=$("input[name='choose']:checked").length;
        if(len==0){
          bootbox.alert("请选择数据");

        }else{
          bootbox.prompt("请输入删除的原因", function(result) {
            if (result === null) {

            } else {
              $.ajax( {
                type : "POST",
                beforeSend: function(xhr) {
                  xhr.setRequestHeader(csrfHeaderName, csrfToken);
                },
                url: "${ctxPath}/api/1/idcardblack/deleteIdCardBlack",
                data : "ids="+deleteIds+"&opertype=delete&reason="+result,
                dataType: "json",
                success : function(data, textStatus, jqXHR) {
                  if(data.code==200){
                    bootbox.alert("删除成功！", function() {
                      window.location.href="${ctxPath}/idcardblack/toQueryIdBlack?_initQuery=true"
                    });
                  }else{
                    bootbox.alert("删除失败！"+data.message, function() {
                      window.location.href="${ctxPath}/idcardblack/toQueryIdBlack?_initQuery=true"
                    });
                  }
                }
              });
            }
          });
        }
      });

      $("#bathRemove").click(function(){
        var removeids=ids();
        var len=$("input[name='choose']:checked").length;
        if(len==0){
          bootbox.alert("请选择数据");

        }else{
          bootbox.prompt("请输入移除的原因", function(result) {
            if (result === null) {

            }else{
              $.ajax({
                type: "POST",
                beforeSend: function (xhr) {
                  xhr.setRequestHeader(csrfHeaderName, csrfToken);
                },
                url: "${ctxPath}/api/1/idcardblack/idcardblackModify",
                data: "ids=" + removeids+"&opertype=remove&reason="+result,
                dataType: "json",
                success: function (data, textStatus, jqXHR) {
                  if (data.code == 200) {
                    bootbox.alert("移除成功！", function() {
                      window.location.href="${ctxPath}/idcardblack/toQueryIdBlack?_initQuery=true"
                    });
                  } else {
                    bootbox.alert("移除失败！"+data.message, function() {
                      window.location.href="${ctxPath}/idcardblack/toQueryIdBlack?_initQuery=true"
                    });}
                }
              });
            }
          });
        }
      });

      $("#bathVerify").click(function(){
        var verifyIds=ids();
        var len=$("input[name='choose']:checked").length;
        if(len==0){
          bootbox.alert("请选择数据");

        }else{
          bootbox.dialog({
            title: "审核.",
            message: '<div class="row">  ' +
            '<div class="col-md-12"> ' +
            '<form class="form-horizontal"> ' +
            '<div class="form-group"> ' +
            '<label class="col-md-4 control-label" for="awesomeness">审核结果</label> ' +
            '<div class="col-md-4"> <div> <label for="awesomeness-0"> ' +
            '<input type="radio" name="awesomeness" id="awesomeness-0" value="00" checked="checked"> ' +
            '通过</label> ' +
            '</div><div > <label for="awesomeness-1"> ' +
            '<input type="radio" name="awesomeness" id="awesomeness-1" value="04">拒绝 </label> ' +
            '</div> ' +
            '</div> </div>' +
            '<div class="form-group"> ' +
            '<label class="col-md-4 control-label" for="reason">备注</label> ' +
            '<div class="col-md-6"> ' +
            '<input id="reason" name="reason" type="text" placeholder="备注" class="form-control input-md"> ' +
            '</div> ' +
            '</div> ' +
            '</form> </div>  </div>',
            buttons: {
              success: {
                label: "提 交",
                className: "btn-primary",
                callback: function () {
                  var reason = $('#reason').val();
                  var result = $("input[name='awesomeness']:checked").val();
                  $.ajax({
                    type: "POST",
                    beforeSend: function (xhr) {
                      xhr.setRequestHeader(csrfHeaderName, csrfToken);
                    },
                    url: "${ctxPath}/api/1/idcardblack/idcardblackVerify",
                    data: "ids=" + verifyIds+"&reason="+reason+"&status="+result,
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                      if (data.code == 200) {
                        bootbox.alert("审核成功！", function() {
                          window.location.href="${ctxPath}/idcardblack/toQueryIdBlack?_initQuery=true"
                        });
                      } else {
                        bootbox.alert("审核失败！"+data.message, function() {
                          window.location.href="${ctxPath}/idcardblack/toQueryIdBlack?_initQuery=true"
                        });
                      }
                    }
                  });

                }
              }
            }
          });
        }
      });
    });

    //全选
    function setChecked(){
      var choosetotal=$("input[name='choosetotal']");
      if(choosetotal[0].checked==true){
        $("input[name='choose']").attr("checked",true);
      }else{
        $("input[name='choose']").attr("checked",false);
      }
    }

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

  </script>
</div>
</html>