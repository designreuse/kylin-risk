 <%--
  Created by IntelliJ IDEA.
  User: v-wangwei
  Date: 2015/9/6 0006
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title></title>

</head>
<body>
<form id="form" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <div class="panel">
          <div class="panel-heading">
            <div class="panel-options">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tab-1" data-toggle="tab" aria-expanded="true">基本信息</a></li>
                <li class=""><a href="#tab-2" data-toggle="tab" aria-expanded="false">账户信息</a></li>
                <li class=""><a href="#tab-3" data-toggle="tab" aria-expanded="false">交易信息</a></li>
                <%--<li class=""><a href="#tab-3" data-toggle="tab" aria-expanded="false">角色功能</a></li>--%>
              </ul>
            </div>
          </div>
          <div class="panel-body">
            <div class="tab-content">
              <div class="tab-pane active" id="tab-1">
                <table class="table">
                  <tr>
                    <th>客户编号</th>
                    <td><input type="text" id="customerid" name="customerid" value="${customer.customerid}"></td>
                    <th>客户名称</th>
                    <td>
                      <input type="text" id="customername" name="customername" value="${customer.customername}">
                    </td>
                  </tr>
                  <tr>
                    <th>证件类型</th>
                    <td><input type="text" id="certificatetype" name="certificatetype" value="${customer.certificatetype}"></td>
                  </tr>
                </table>
              </div>
              <div class="tab-pane" id="tab-2">
                <table class="table">
                  <tr>
                    <th>银行卡类型</th>
                    <td><input type="text" id="accounttype" name="accounttype" value="${customer.account.accounttype}"></td>
                    <th>银行卡号</th>
                    <td>
                      <input type="text" id="accountid" name="accountid" value="${customer.account.accountid}">
                    </td>
                  </tr>
                  <tr>
                    <th>账户状态</th>
                    <td><input type="text" id="status" name="status" value="${customer.account.status}"></td>
                  </tr>
                </table>
              </div>
              <div class="tab-pane" id="tab-3">
                <table id="queryTable" class="table" cellspacing="0">
                  <thead>
                  <tr>
                    <th>交易流水</th>
                    <th>交易类别</th>
                    <th>交易金额</th>
                    <th>交易币种</th>
                    <th>交易时间</th>
                    <th>付款方</th>
                    <th>收款方</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${simpleBillList}" var="txn">
                    <tr>
                      <td>${txn.txnno}</td>
                      <td><dict:write dictcode="30008" code="${txn.txntp}"></dict:write> </td>
                      <td>${txn.txnamt}</td>
                      <td>${txn.txnccy}</td>
                      <td><r:formateDateTime value="${txn.txntime}" pattern="yyyy-MM-dd"/></td>
                      <td>${txn.dbtrname}</td>
                      <td>${txn.cdtrname}</td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
        <center>
          <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
        </center>
      </div>
    </div>
  </div>
</form>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    /*var operTpl = $("#oper-tpl").html();
     //预编译模板
     var operTemplate = Handlebars.compile(operTpl);
     var statusSource   = $("#status-tpl").html();
     var statusTemplate = Handlebars.compile(statusSource);*/
    var type = ${type};

    $(function(){
      $("#back").click(function(){
        if(type=='01'){
          window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxnCondition?_initQuery=true";
        }else{
          window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxn?_initQuery=true";
        }
      });
    });
    function queryInfo(){
      $("#form").attr("action" ,"${ctxPath}/customer/customerQuery");
      $('#form').submit();
    }
  </script>

</div>
</html>
