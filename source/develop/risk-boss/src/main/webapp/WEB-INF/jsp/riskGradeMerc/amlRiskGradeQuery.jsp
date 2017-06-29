<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>商户反洗钱风险等级管理</title>
  <style>
    .dataTables_wrapper{padding-bottom: 0px}
  </style>
</head>
<body>
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="riskGradeForm" id="riskGradeForm" action="${ctxPath}/amlGradeMerc/queryAmlGradeMerc" method="get" data-table="queryTable" >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="operType" id="operType" value="${operType}"/>
        <input type="hidden" name="risktype" value="01">
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="grade">风险等级：</label>
                     <dict:select dictcode="riskleve" id="grade" name="grade" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="merchantid">商户号：</label>
              <input type="text" class="form-control input-sm" id="merchantid" name="merchantid"
                     value="${merchantid}" placeholder="商户号" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="merchantname">商户名称：</label>
              <input type="text" class="form-control input-sm" id="merchantname" name="merchantname"
                     value="${merchantname}" placeholder="商户名称" class="form-control">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="createtime">创建时间：</label>
              <input type="text" class="form-control input-sm Wdate" id="createtime" name="createtime"
                     value="${createtime}" placeholder="创建时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">

            </div>
          </div>

          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="status">状态：</label>
                   <dict:select dictcode="status" id="status" name="status" escapeValue="99,03,02" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
        <div align="center">
          <button type="submit" class="btn btn-w-m btn-success">查询</button>
          <button type="reset" class="btn btn-w-m btn-info">重置</button>
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
                <th><input type="checkbox" name='choosetotal' id="checkAll">全选</th>
                <th>商户号</th>
                <th>商户名称</th>
                <th>系统等级</th>
                <th>评级得分</th>
                <th>创建时间</th>
                <th>待审核等级</th>
                <th>上次评级等级</th>
                <th>审核状态</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
            <c:if test="${operType=='COMMIT'}">
              <a href="#">
                <button id ="setLow" class="btn-white btn btn-xs">
                  <i class="fa fa-star-o"></i>&nbsp;&nbsp;<strong>设为低风险</strong></button>
              </a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="#">
                <button id ="setMiddle" class="btn-white btn btn-xs">
                  <i class="fa fa-star-half-empty"></i>&nbsp;&nbsp;<strong>设为中风险</strong></button>
              </a>&nbsp;&nbsp;&nbsp;&nbsp;
              <a href="#">
                <button id ="setHigh" class="btn-white btn btn-xs">
                  <i class="fa fa-star"></i>&nbsp;&nbsp;<strong>设为高风险</strong></button>
              </a>
            </c:if>
            <C:if test="${operType=='CHECK'}">
              <a href="#">
                <button id ="setAgree" class="btn-white btn btn-xs">
                  <i class="fa fa-star-o"></i>&nbsp;&nbsp;<strong>审核通过</strong></button>
              </a>
              <a href="#">
                <button id ="setDisagree" class="btn-white btn btn-xs">
                  <i class="fa fa-star"></i>&nbsp;&nbsp;<strong>审核拒绝</strong></button>
              </a>
            </C:if>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="choose" value="{{id}}" grade="{{grade}}"/>
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
    var csrfHeaderName = "${_csrf.headerName}"
    var checkboxSource   = $("#checkbox-tpl").html();
    var checkBoxTemplate = Handlebars.compile(checkboxSource);

    var operTpl = $("#oper-tpl").html();
    //预编译模板
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    function setRiskGrade(updateGrade){
      var selectIds="";
      var len = $("input[name='choose']:checked").length;
      if (len == 0) {
        bootbox.alert("请选择数据");
        return;
      }else {
        bootbox.prompt("请输入更改评级等级原因", function(result) {
          if(result==''){
            return;
          }else{
            var str=document.getElementsByName("choose");
            var grade = "";
            for (var i=0;i<str.length;i++){
              if(str[i].checked == true){
                if(selectIds==""){
                  grade = str[i].getAttribute('grade');//获取选中的第一行的等级
                  selectIds+=str[i].value;
                }else {
                  if(grade==str[i].getAttribute('grade')){//用第一行的等级与其他行等级比较
                    selectIds+=","+str[i].value;
                  }else{
                    bootbox.alert("系统评级等级不一致不能同时修改等级");
                    return;
                  }
                }
              }
            }

            $.ajax( {
              type : "POST",
              beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeaderName, csrfToken);
              },
              url: "${ctxPath}/api/1/amlGradeMerc/updateMercAmlGrade",
              data : "ids="+selectIds+"&reason="+result+"&updateGrade="+updateGrade,
              dataType: "json",
              async:true,
              success : function(data, textStatus, jqXHR) {
                if(data.code==200){
                  bootbox.alert("设置成功！", function() {
                    window.location.href="${ctxPath}/amlGradeMerc/toQueryAmlRiskGradeMerc?_initQuery=true";
                  });
                }else{
                  bootbox.alert("设置失败！"+data.message, function() {
                    window.location.href="${ctxPath}/amlGradeMerc/toQueryAmlRiskGradeMerc?_initQuery=true";
                  });
                }
              }
            });
          }
        });
      }
    }

    function checkRiskGrade(status){
      var selectIds="";
      var len = $("input[name='choose']:checked").length;
      if (len == 0) {
        bootbox.alert("请选择数据");
        return;
      }else {
        var str=document.getElementsByName("choose");
        for (var i=0;i<str.length;i++){
          if(str[i].checked == true){
            if(selectIds==""){
              selectIds+=str[i].value;
            }else {
              selectIds+=","+str[i].value;
            }
          }
        }

        $.ajax( {
          type : "POST",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfToken);
          },
          url: "${ctxPath}/api/1/amlGradeMerc/updateMercAmlGradeStatus",
          data : "ids="+selectIds+"&status="+status,
          dataType: "json",
          async:true,
          success : function(data, textStatus, jqXHR) {
            if(data.code==200){
              bootbox.alert("审核成功！", function() {
                window.location.href="${ctxPath}/amlGradeMerc/toQueryAmlRiskGradeMerc?_initQuery=true";
              });
            }else{
              bootbox.alert("审核失败！"+data.message, function() {
                window.location.href="${ctxPath}/amlGradeMerc/toQueryAmlRiskGradeMerc?_initQuery=true";
              });
            }
          }
        });
      }
    }

    $(function () {
      $("#setLow").click(function () {
        setRiskGrade("LOW");
      });

      $("#setMiddle").click(function () {
        setRiskGrade("MIDDLE");
      });

      $("#setHigh").click(function () {
        setRiskGrade("HIGH");
      });

      $("#setAgree").click(function () {
        checkRiskGrade("AGREE");
      });

      $("#setDisagree").click(function () {
        checkRiskGrade("DISAGREE");
      });

      $("#checkAll").click(function() {
        var choosetotal = $("input[name='choosetotal']");
        if (choosetotal[0].checked == true) {
          $("input[name='choose']").attr("checked", true);
        } else {
          $("input[name='choose']").attr("checked", false);
        }
      });

    });


    function getDetail(id,type) {
      window.location.href="${ctxPath}/amlGradeMerc/getMercAmlRiskGradeDetail?id="+id+"&operType="+$("#operType").val();
    }

    //绑定form的查询到tables
    $(function(){
      $('form#riskGradeForm').queryTables({
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "merchantid"},
          {"data": "merchantname"},
          {"data": "grade"},
          {"data": "totalscore"},
          {"data": "createtime"},
          {"data": "checkgrade"},
          {"data": "lastgrade"},
          {"data": "status"},
          {"data": null}
        ],
        columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              var operType = $("#operType").val();
              if((operType=="CHECK"&&row.status=='05')||(operType=="COMMIT"&&row.status!='05')){
                return checkBoxTemplate({id:data,grade:row.grade,status:row.status});
              }else{
                return "";
              }
            }
          },

          {
            targets:[3,6,7],
            render:function(data, type, row, meta){
              if(data==null){
                return "";
              }
              var dataMap={"00":{text:"高"},"01":{text:"中"},"02":{text:"低"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 5,
            render:function(data, type, row, meta){
              return moment(data).format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets:8,
            render:function(data, type, row, meta){
              var dataMap={"00":{"cssClass":"label-primary",text:"有效"},"01":{"cssClass":"label-danger",text:"无效"},"05":{"cssClass":"label-danger",text:"修改待审核"},"04":{"cssClass":"label-danger",text:"审核拒绝"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 9,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "查看", "fn": "getDetail("+row.id+")", "cssClass": "fa-tag"}
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
