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
            <td><input type="text" id="rulename" name="rulename" value="${riskrule.rulename}" required></td>
          </tr>
          <tr>
            <th>规则类别</th>
            <td>
              <dict:select dictcode="category" id="category" name="category" value="${riskrule.category}" cssClass="form-control"></dict:select>
            </td>
          </tr>
          <tr>
            <th>规则描述</th>
            <td><input type="text" id="discribe" name="discribe" value="${riskrule.discribe}" required></td>
          </tr>
          <tr>
            <th>规则状态</th>
            <td>
              <div class="switch">
                <div class="onoffswitch">
                  <input id="status" type="checkbox" name="status" value="00" <c:if test="${riskrule.status=='00'}">checked</c:if> class="onoffswitch-checkbox" >
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
              <dict:select dictcode="30010" id="businesstype" name="businesstype" value="${riskrule.businesstype}" cssClass="form-control"></dict:select>
            </td>
          </tr>
          <tr>
            <th>触发行为</th>
            <td>
              <dict:select dictcode="behavior" id="behavior" name="behavior" value="${riskrule.behavior}" cssClass="form-control"></dict:select>
            </td>
          </tr>
          <tr>
            <th>风险类型</th>
            <td>
              <dict:select dictcode="risktype" id="risktype" name="risktype" value="${riskrule.risktype}" cssClass="form-control"></dict:select>
            </td>
          </tr>
          <tr>
            <th>风险评分</th>
            <td><input type="text" id="riskscore" name="riskscore" value="${riskrule.riskscore}" required></td>
          </tr>
          <tr>
            <th>文件名称</th>
            <td><input type="text" id="filename" name="filename" value="${riskrule.filename}"></td>
          </tr>
          <tr>
            <th>备注</th>
            <td><input type="text" id="remark" name="remark" value="${riskrule.remark}"></td>
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
      $("#back").backup();
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
                $.backquery();
              });
            }else{
              bootbox.alert("修改失败！");
            }
          }
        });
      });
    });
  </script>
</div>
</html>

