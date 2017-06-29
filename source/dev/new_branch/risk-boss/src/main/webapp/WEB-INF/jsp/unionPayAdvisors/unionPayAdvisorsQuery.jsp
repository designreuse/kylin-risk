<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>银联智策查询</title>
</head>
<body >
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight backPageDiv" >
    <div class="ibox-content m-b-sm border-bottom">
      <form id="ruleForm" action="" data-parsley-validate data-table="queryTable">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="rolename" id="rolename" value="${rolename}"/>
        <div class="row">
          <div class="col-sm-6">
            <div class="form-group">
              <label class="col-sm-2 control-label" >银行卡号</label>
              <div class="col-sm-10">
                <input type="text" id="bankCard" name="bankCard"  placeholder="银行卡号" class="form-control" required>
              </div>
            </div>
          </div>
          <div class="col-sm-6">
            <div class="form-group">
              <label class="col-sm-2 control-label" >查询银联智策</label>
              <div class="col-sm-10">
                <select id="queryAgain" name="queryAgain" style="width:350px;" tabindex="4" required>
                <option value="false" selected>否</option>
                <option value="true" >是</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="row query-div">
          <button type="button" id="submitQuery" class="btn btn-w-m btn-success">查询</button>
          <button type="reset" class="btn btn-w-m btn-info">重置</button>
        </div>
        <div class="row">
          <div class="col-lg-12">
            <div class="ibox">
              <div class="ibox-content">
                <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                  <thead>
                  <tr>
                    <th>渠道</th>
                    <th>银行卡号</th>
                    <th>风险得分</th>
                    <th>风险得分分类</th>
                    <th>操作人</th>
                    <th>操作时间</th>
                  </tr>
                  </thead>
                  <tbody id="tableList">

                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script id="table-template" type="text/x-handlebars-template">
  {{#each this.data}}
  {{#list this}}
  <tr>
    <td>{{channel}}</td>
    <td>{{bankCard}}</td>
    {{#compare creditCode}}
      <td>{{score result.CSRL001}}</td>
      <td>{{type result.CSRL002}}</td>
    {{else}}
      <td></td>
      <td></td>
    {{/compare}}
    <td>{{querier}}</td>
    <td>{{requestTime}}</td>
  </tr>
  {{/list}}
  {{/each}}
</script>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var source   = $("#table-template").html();
    var template = Handlebars.compile(source);
    $(function () {
      var reg = /^(\d{16}|\d{19})$/;
      $("#submitQuery").click(function () {
        var bankCard=$("#bankCard").val();
        var queryAgain=$("#queryAgain").val();
        if(reg.test(bankCard)){
          $.ajax({
            type: "POST",
            beforeSend: function (xhr) {
              xhr.setRequestHeader(csrfHeaderName, csrfToken);
            },
            url: "${ctxPath}/api/1/unionPayAdvisors/queryUnionPayAdvisors",
            data: {"bankCard":bankCard,"queryAgain":queryAgain},
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
              var d= $.parseJSON(data);
              if(d.code=="200"){
                $('#tableList').html(template(d));
              }else{
                bootbox.alert("查询结果异常！");
              }
            }
          });
        }else{
          bootbox.alert("请填写16位或19位的银行卡号！");
        }
    });

      Handlebars.registerHelper("compare",function(v1,options){
        if(v1==0){
          //满足添加继续执行
          return options.fn(this);
        }else{
          //不满足条件执行{{else}}部分
          return options.inverse(this);
        }
      });

      //判断风险得分分类
      Handlebars.registerHelper("type",function(value){
        if(0<value<=4){
          return "<=4	高逾期风险";
        }else if(value<=6){
          return "5-6	中高逾期风险";
        }else if(value<=11){
          return "7-11	中低逾期风险";
        }else if(value==9990){
          return "9990极大风险（黑名单）";
        }else if(value==9991){
          return "9991	极小风险（白名单）";
        }else{
          return "";
        }
      });

      //判断风险得分
      Handlebars.registerHelper("score",function(value){
        if(0<value<=400){
          return "(0 400] 高逾期风险";
        }else if(400<value<=500){
          return "[401 500]	中高逾期风险";
        }else if(500<value<=600){
          return "[501 600]	中低逾期风险";
        }else if(600<value<=700){
          return "[601 700]	中低逾期风险";
        }else if(700<value<=1000){
          return "[701 1000] 中低逾期风险";
        }else if(value==9990){
          return "9990 极大风险（黑名单）";
        }else if(value==9991){
          return "9991 极小风险（白名单）";
        }else{
          return "";
        }
      });
      Handlebars.registerHelper("list",function(data,options){
        var result=data["result"];
        var jsonResult = $.parseJSON(result);
        delete data["result"];
        var ret="";
        var dataArr= jsonResult["data"];
        if(dataArr){
          for(var i in dataArr){
            data.result=dataArr[i];
            ret+=options.fn(data);
          }
        }
        return ret;
      });
    });

  </script>

</div>
</html>
