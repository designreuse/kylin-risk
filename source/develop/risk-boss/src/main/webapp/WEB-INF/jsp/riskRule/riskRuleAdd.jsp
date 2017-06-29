<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>添加规则</title>
</head>
<body>
<form id="addRuleform" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <table class="table">
          <tr>
            <th>规则名称</th>
            <td><input type="text" id="rulename" name="rulename" required></td>
          </tr>
          <tr>
            <th>规则类别</th>
            <td>
              <dict:select dictcode="category" id="category" name="category"></dict:select>
            </td>
          </tr>
          <tr>
            <th>规则描述</th>
            <td><input type="text" id="discribe" name="discribe" required></td>
          </tr>
          <tr>
            <th>业务类型</th>
            <td>
              <dict:select dictcode="30010" id="businesstype" name="businesstype"></dict:select>
            </td>
          </tr>
          <tr>
            <th>触发行为</th>
            <td>
              <dict:select dictcode="behavior" id="behavior" name="behavior"></dict:select>
            </td>
          </tr>
          <tr>
            <th>风险类型</th>
            <td>
              <dict:select dictcode="risktype" id="risktype" name="risktype"></dict:select>
            </td>
          </tr>
          <tr>
            <th>风险评分</th>
            <td><input type="text" id="riskscore" name="riskscore" required></td>
          </tr>
          <tr>
            <th>文件名称</th>
            <td><input type="text" id="filename" name="filename"></td>
          </tr>
          <tr>
            <th>备注</th>
            <td><input type="text" id="remark" name="remark"></td>
          </tr>
        </table>
        <center>
          <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
        </center>
      </div>
    </div>
  </div>
</form>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    var csrfToken="${_csrf.token}";
    var csrfHeaderName="${_csrf.headerName}";

    $(function(){
      $("#back").click(function(){
        window.location.href="${ctxPath}/riskrule/toQueryRiskrule";
      });
    });

    $(function(){
      var initparsley=$("#addRuleform").parsley();
      $("#submitAdd").click(function(){
        $("#addRuleform").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/riskrule/addRiskrule',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("添加成功！", function () {
                window.location.href="${ctxPath}/riskrule/toQueryRiskrule";
              });
            }else{
              bootbox.alert("添加失败！", function () {
                window.location.href="${ctxPath}/riskrule/toQueryRiskrule";
              });
            }
          }
        });
      });
    });
  </script>
</div>
</html>
