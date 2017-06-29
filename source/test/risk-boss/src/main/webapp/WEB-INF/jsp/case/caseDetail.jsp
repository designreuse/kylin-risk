<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>可疑交易信息</title>
</head>
<body>
<form  id="updForm" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <c:if test="${casetype=='0'}">
        <table class="table">
          <thead>
          <tr>
            <th>风险交易号</th>
            <th>客户编号</th>
            <th>客户名称</th>
            <th>交易金额</th>
            <th>报警状态</th>
            <th>预警日期</th>
            <th>可疑预警规则</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${dubioustxnList}" var="txn">
            <tr>
              <td>${txn.txnum}</td>
              <td>${txn.customnum}</td>
              <td>${txn.customname}</td>
              <td>${txn.txnaccount}</td>
              <td><dict:write dictcode="warnstatus" code="${txn.warnstatus}"></dict:write></td>
              <td><r:formateDateTime value="${txn.warndate}" pattern="yyyy-MM-dd"/></td>
              <td>${txn.ruleid}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
        </c:if>
        <c:if test="${casetype=='1'}">
          <table class="table">
            <thead>
            <tr>
              <th>客户编号</th>
              <th>风险事件编号</th>
              <th>风险事件名称</th>
              <th>风险事件类型</th>
              <th>风险事件来源</th>
              <th>风险事件概述</th>
              <th>风险事件处理结果</th>
              <th>创建人姓名</th>
              <th>生成时间</th>
              <th>状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${riskEventList}" var="even">
              <tr>
                <td>${even.customerid}</td>
                <td>${even.eventcode}</td>
                <td>${even.eventname}</td>
                <td>${even.eventtype}</td>
                <td>${even.eventsource}</td>
                <td>${even.eventdesc}</td>
                <td><c:if test="${even.eventresult=='0'}">未处理</c:if><c:if test="${even.eventresult=='1'}">已处理</c:if></td>
                <td>${even.operatorname}</td>
                <td><r:formateDateTime value="${even.createtime}" pattern="yyyy-MM-dd  HH:mm:ss"/></td>
                <td><dict:write dictcode="status" code="${even.status}"></dict:write></td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </c:if>
        <center>
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
      $("#back").backup();
    });
  </script>
</div>
</html>
