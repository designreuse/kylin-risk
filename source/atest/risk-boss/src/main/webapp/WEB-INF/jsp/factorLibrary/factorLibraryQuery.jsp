<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子库查询</title>
</head>
<body >
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight backPageDiv" >
    <div class="ibox-content m-b-sm border-bottom">
    <form id="ruleForm" action="${ctxPath}/factorLibrary/queryFactorLibrary"   data-table="queryTable">
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
            <label class="col-sm-2 control-label" >因子类型</label>
            <div class="col-sm-10">
              <dict:select dictcode="factortype" id="factortype" name="factortype" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
      </div>
    <div class="row query-div">
      <a href="toAjaxAddFactorLibrary" class="ajaxback-requst">
        <button type="button"  class="btn btn-w-m btn-primary ">添加</button>
      </a>
      <button type="submit" class="btn btn-w-m btn-success">查询</button>
      <button type="reset" class="btn btn-w-m btn-info">重置</button>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <a id="active" href="#"  data-toggle="modal" data-target="#myModal">
              <button type="button" id="statusActive" class="btn btn-white  btn-xs">
                <i class="fa fa-play"></i>
                <strong>开启</strong>
              </button>
            </a>
            <a id="inactive" href="#"  data-toggle="modal" data-target="#myModal">
              <button type="button" id="statusInactive" class="btn btn-white  btn-xs">
                <i class="fa fa-pause"></i>
                <strong>关闭</strong>
              </button>
            </a>
            <br>
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th></th>
                <th>因子名称</th>
                <th>因子类型</th>
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

    $("#statusInactive").click(function(){
      var selectIds = getSelectedId();
      if (selectIds!=""&&confirm("确定关闭因子？")) {
        updateFactorLibraryStatus(selectIds,"false");
      }
    });
    $("#statusActive").click(function(){
      var selectIds = getSelectedId();
      if (selectIds!=""&&confirm("确定开启因子？")) {
        updateFactorLibraryStatus(selectIds,"true");
      }
    });

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

    function updateFactorLibraryStatus(ids,status){
      $.ajax({
        type: "POST",
        beforeSend: function (xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/factorLibrary/modifyFactorStatus",
        data: "id=" + ids+"&status="+status,
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
          if (data.code==200) {
            bootbox.alert("修改成功！",function(){
              $('#ruleForm').submit();
            });
          } else {
            bootbox.alert("修改失败！");
          }
        }
      });
    }
    function getDetail(id){
      $.ajaxParamBack({"url":"${ctxPath}/factorLibrary/toAjaxQueryFactorLibraryDetail","data":"id="+id});
    }

    $(function(){
      $.ajaxBack();
      $('form#ruleForm').queryTables({
        "pageLength":10,
        "columns": [
          {"data": "id"},
          {"data": "name"},
          {"data": "type"},
          {"data": "status"},
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
              var dataMap={"00":{"cssClass":"label-primary",text:"个人信息"},"01":{"cssClass":"label-danger",text:"家庭成员信息"},
                "02":{"cssClass":"label-primary",text:"合作平台收集信息"},"03":{"cssClass":"label-danger",text:"通讯录信息"},
                "04":{"cssClass":"label-primary",text:"平台订单记录"},"05":{"cssClass":"label-danger",text:"培训机构信息"},
                "06":{"cssClass":"label-danger",text:"金融产品申请信息"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
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
                  {"text": "编辑", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"}
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
