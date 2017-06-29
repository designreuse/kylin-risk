<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>风险事件详细信息</title>
</head>
<body>
<form id="updEventform" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="id" value="${riskEvent.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <table class="table">
          <tr>
            <th>客户ID</th>
            <td><input type="text" id="cusid" name="cusid" value="${riskEvent.cusid}" disabled="true" ></td>
          </tr>
          <tr>
            <th>客户编号</th>
            <td><input type="text" id="customerid" name="customerid" value="${riskEvent.customerid}" disabled="true" required></td>
          </tr>
          <tr>
            <th>风险事件编号</th>
            <td><input type="text" id="eventcode" name="eventcode" value="${riskEvent.eventcode}" required></td>
          </tr>
          <tr>
            <th>风险事件名称</th>
            <td><input type="text" id="eventname" name="eventname" value="${riskEvent.eventname}" required></td>
          </tr>
          <tr>
            <th>风险事件类型</th>
            <td>
              <input type="text" id="eventtype" name="eventtype" value="${riskEvent.eventtype}" required>
            </td>
          </tr>
          <tr>
            <th>风险事件来源</th>
            <td><input type="text" id="eventsource" name="eventsource" value="${riskEvent.eventsource}" required></td>
          </tr>

          <tr>
            <th>风险事件处理结果</th>
            <td><input type="text" id="eventresult" name="eventresult" value="${riskEvent.eventresult}" required></td>
          </tr>
          <tr>
            <th>文件路径</th>
            <td><a href="${riskEvent.filepath}">${riskEvent.filepath}</a></td>
          </tr>
          <tr>
            <th>生成时间</th>
            <td>
              <r:formateDateTime value="${riskEvent.createtime}" pattern="yyyy-MM-dd HH:mm:ss"></r:formateDateTime>
            </td>
          </tr>
          <tr>
            <th>创建人ID</th>
            <td><input type="text" id="operatorid" name="operatorid" value="${riskEvent.operatorid}" disabled="true"></td>
          </tr>
          <tr>
            <th>创建人姓名</th>
            <td><input type="text" id="operatorname" name="operatorname" value="${riskEvent.operatorname}" disabled="true "></td>
          </tr>
          <tr>
            <th>状态</th>
            <td>
              <div class="onoffswitch">
                <input type="checkbox"  <c:if test="${riskEvent.status=='00'}">checked="checked"</c:if> id="status" name="status" class="onoffswitch-checkbox" >
                <label class="onoffswitch-label" for="status">
                  <span class="onoffswitch-inner"></span>
                  <span class="onoffswitch-switch"></span>
                </label>
              </div>
            </td>
          </tr>
          <tr>
            <th>风险事件概述</th>
            <td><input type="text" id="eventdesc" name="eventdesc" value="${riskEvent.eventdesc}" required></td>
          </tr>
        </table>
        <center>
          <c:if test="${dealType=='modify'}">
            <input type="button" id="submitModify" class="btn btn-w-m btn-success" value="提交修改">&nbsp;&nbsp;&nbsp;&nbsp;
          </c:if>
          <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
        </center>
      </div>
    </div>
  </div>
</form>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    $(function(){
      $("#back").click(function(){
        window.location.href="${ctxPath}/riskEvent/toQueryRiskEvent.action?_initQuery=true&rulename=${riskrules.rulename}&status=${riskrules.status}&risktype=${riskrules.risktype}";
      });
    });
    $(function(){
      var initparsley=$("#updEventform").parsley();
      $("#submitModify").click(function(){
        $("#updEventform").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/riskEvent/updateRiskEvent',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                window.location.href="${ctxPath}/riskEvent/toQueryRiskEvent";
              });
            }else{
              bootbox.alert("修改失败！", function () {
                window.location.href="${ctxPath}/riskEvent/toQueryRiskEvent";
              });
            }
          }
        });
      });
    });
  </script>
</div>
</html>

