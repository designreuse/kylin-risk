<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子库修改</title>
</head>
<body>
<form id="modifyFactorLibraryform" method="post" data-parsley-validate class="form-horizontal">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden"  name="id" value="${factorLibrary.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content   float-e-margins">
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label " >因子名称:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="name" name="name" value="${factorLibrary.name}" required>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label " >因子代码:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="code" name="code" value="${factorLibrary.code}" required readonly>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-2 control-label" >因子类型:</label>
                <div class="col-sm-3 m-b">
                    <dict:select dictcode="factortype" id="factortype" name="type" nullLabel="请选择" nullOption="true" cssClass="form-control" value="${factorLibrary.type}"></dict:select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="status">状态:</label>
                <div class="col-sm-3">
                    <dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true" cssClass="form-control" value="${factorLibrary.status}"></dict:select>
                </div>
            </div>
        </div>

        <div class="row query-div">
          <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="修改">&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
        </div>
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
              window.location.href="${ctxPath}/factorLibrary/toQueryFactorLibrary";
          });
      });
      $(function(){
          $("#submitAdd").click(function(){
            subRuleAdd();
          });
      });

      function subRuleAdd(){
          var initparsley=$("#modifyFactorLibraryform").parsley();
          $("#modifyFactorLibraryform").ajaxSubmit({
              type:'post',
              dataType: 'json',
              beforeSubmit:function(){
                  return initparsley.validate();
              },
              url: '${ctxPath}/api/1/factorLibrary/updateFactorLibrary',
              success: function(data,textStatus,jqXHR){
                  if(data.code==200){
                      bootbox.alert("修改成功！", function () {
                          window.location.href="${ctxPath}/factorLibrary/toQueryFactorLibrary";
                      });
                  }else{
                      bootbox.alert("修改失败！");
                  }
              }
          });
      }
  </script>
</div>
</html>
