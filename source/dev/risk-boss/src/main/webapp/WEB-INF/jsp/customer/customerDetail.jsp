<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<form id="form" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <div class="panel">
          <div class="panel-heading">
            <div class="panel-options">
              <ul class="nav nav-tabs">
                <li class="active"><a href="#tab-1" data-toggle="tab" aria-expanded="true">基本信息</a></li>
                <%--<li class=""><a href="#tab-2" data-toggle="tab" aria-expanded="false">账户信息</a></li>
                <li class=""><a href="#tab-3" data-toggle="tab" aria-expanded="false">角色功能</a></li>--%>
              </ul>
            </div>
          </div>
          <div class="panel-body">
            <div class="tab-content">
              <div class="tab-pane active" id="tab-1">
                <table class="table">
                  <tr>
                    <th>客户编号</th>
                    <td><input type="text" id="customerid" name="customerid" value="${customerinfo.customerid}"></td>
                    <th>客户名称</th>
                    <td>
                      <input type="text" id="customername" name="customername" value="${customerinfo.customername}">
                    </td>
                  </tr>
                  <tr>
                    <th>证件类型</th>
                    <td><input type="text" id="certificatetype" name="certificatetype" value="${customerinfo.certificatetype}"></td>
                  </tr>
                </table>
              </div>
              <%--<div class="tab-pane" id="tab-2">--%>
                <%--<table class="table">--%>
                  <%--<tr>--%>
                    <%--<th>银行卡类型</th>--%>
                    <%--<td><input type="text" id="accounttype" name="accounttype" value="${customer.account.accounttype}"></td>--%>
                    <%--<th>银行卡号</th>--%>
                    <%--<td>--%>
                      <%--<input type="text" id="accountid" name="accountid" value="${customer.account.accountid}">--%>
                    <%--</td>--%>
                  <%--</tr>--%>
                  <%--<tr>--%>
                    <%--<th>账户状态</th>--%>
                    <%--<td><input type="text" id="status" name="status" value="${customer.account.status}"></td>--%>
                  <%--</tr>--%>
                <%--</table>--%>
              <%--</div>--%>
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
  $(function(){
    $("#back").backup();
  });
</script>
</div>
</html>
