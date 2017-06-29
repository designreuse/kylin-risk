<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>规则详细信息</title>
</head>
<body>
<form id="updRuleform" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="id" value="${riskrule.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <table class="table">
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
              <div class="switch">
                <div class="onoffswitch">
                  <input id="status" type="checkbox" name="status" value="00" <c:if test="${riskrule.status=='00'}">checked</c:if> class="onoffswitch-checkbox"  >
                  <label class="onoffswitch-label" for="status">
                    <span class="onoffswitch-inner"></span>
                    <span class="onoffswitch-switch"></span>
                  </label>
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <th>业务类型</th>
            <td>
              <dict:write dictcode="businesstype" code="${riskrule.businesstype}"></dict:write>
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
      var type = ${type};
      $("#back").click(function(){
        if(type=='01'){
          window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxnCondition?_initQuery=true";
        }else{
          window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxn?_initQuery=true";
        }
      });
    });
    $(function(){
      var initparsley=$("#updRuleform").parsley();
      $("#submitModify").click(function(){
        $("#updRuleform").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/riskrule/updateRiskrule',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                window.location.href="${ctxPath}/riskrule/toQueryRiskrule";
              });
            }else{
              bootbox.alert("修改失败！", function () {
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

