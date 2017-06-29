<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子库添加</title>
</head>
<body>
<form id="addFactorLibraryform" method="post" data-parsley-validate class="form-horizontal">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content   float-e-margins">
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label " >因子名称:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="name" name="name" required>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label " >因子代码:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="code" name="code" required>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label" >因子类型:</label>
              <div class="col-sm-3 m-b">
                  <dict:select dictcode="factortype" id="factortype" name="type" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="status">状态:</label>
                <div class="col-sm-3">
                    <dict:select dictcode="status" id="status" name="status" escapeValue="02,03,99,04,05" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
                </div>
            </div>
        </div>

        <div class="row query-div">
          <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="添加">&nbsp;&nbsp;&nbsp;&nbsp;
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
          var initparsley=$("#addFactorLibraryform").parsley();
          $("#addFactorLibraryform").ajaxSubmit({
              type:'post',
              dataType: 'json',
              beforeSubmit:function(){
                  return initparsley.validate();
              },
              url: '${ctxPath}/api/1/factorLibrary/addFactorLibrary',
              success: function(data,textStatus,jqXHR){
                  if(data.code==200){
                      bootbox.alert("添加成功！", function () {
                          window.location.href="${ctxPath}/factorLibrary/toQueryFactorLibrary";
                      });
                  }else{
                      bootbox.alert("添加失败！");
                  }
              }
          });
      }
  </script>
</div>
</html>
