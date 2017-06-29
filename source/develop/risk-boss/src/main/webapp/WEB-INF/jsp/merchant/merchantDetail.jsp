<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>企业信息</title>
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
                    <th>企业号</th>
                    <td><input type="text" id="merchantid" name="merchantid" value="${merchant.merchantid}"></td>
                    <th>企业名称</th>
                    <td>
                      <input type="text" id="merchantname" name="merchantname" value="${merchant.merchantname}">
                    </td>
                  </tr>
                  <tr>
                    <th>公司类型</th>
                    <td><input type="text" id="corptype" name="corptype" value="${merchant.corptype}"></td>
                  </tr>
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
  function queryInfo(){
    $("#form").attr("action" ,"${ctxPath}/merchant/queryMerchant");
    $('#form').submit();
  }

  $(function(){
    $("#back").click(function(){
      window.location.href="${ctxPath}/merchant/toQueryMerchant?_initQuery=true";
    });
  });
</script>
</div>
</html>
