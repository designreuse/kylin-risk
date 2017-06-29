<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>规则信息</title>
</head>
<body>
<form id="ruleform">
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <table class="table">
          <tr>
            <th>来源渠道</th>
            <td>${riskrule.acqid}</td>
          </tr>
          <tr>
            <th>业务渠道ID</th>
            <td>${riskrule.channelid}</td>
          </tr>
          <tr>
            <th>商户ID</th>
            <td>${riskrule.merchid}</td>
          </tr>
          <tr>
            <th>规则名称</th>
            <td>${riskrule.rulename}</td>
          </tr>
          <tr>
            <th>规则类别</th>
            <td>
              <dict:write dictcode="category" code="${riskrule.category}"></dict:write>
            </td>
          </tr>
          <tr>
            <th>规则描述</th>
            <td>${riskrule.discribe}</td>
          </tr>
          <tr>
            <th>规则状态</th>
            <td>
              <dict:write dictcode="status" code="${riskrule.status}"></dict:write>
            </td>
          </tr>
          <tr>
            <th>业务类型</th>
            <td>
              <dict:write dictcode="30010" code="${riskrule.businesstype}"></dict:write>
            </td>
          </tr>
          <tr>
            <th>触发行为</th>
            <td>
              <dict:write dictcode="behavior" code="${riskrule.behavior}"></dict:write>
            </td>
          </tr>
          <tr>
            <th>风险类型</th>
            <td>
              <dict:write dictcode="risktype" code="${riskrule.risktype}"></dict:write>
            </td>
          </tr>
          <tr>
            <th>风险评分</th>
            <td>${riskrule.riskscore}</td>
          </tr>
          <tr>
            <th>文件名称</th>
            <td>${riskrule.filename}</td>
          </tr>
          <tr>
            <th>备注</th>
            <td>${riskrule.remark}</td>
          </tr>
        </table>
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
    })
  </script>
</div>
</html>

