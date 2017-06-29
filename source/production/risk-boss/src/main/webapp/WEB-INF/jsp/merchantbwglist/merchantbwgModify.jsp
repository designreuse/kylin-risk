<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>修改名单</title>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-content">
              <form id="updatForm" method="post"  class="form-inline" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${merchantbwglist.id}">
                <input type="hidden" name="opertype" value="time">
                <div class="row">
                  <div class="col-sm-6">
                    <label class="control-label" for="effecttime">生效时间</label>
                    <input type="text" onClick="WdatePicker()" class="form-control input-sm" id="effecttime"  value="${merchantbwglist.effecttime}" name="effecttime" class="form-control" required>
                  </div>
                  <div class="col-sm-6">
                    <label class="control-label" for="failuretime">失效时间</label>
                    <input type="text"  onClick="WdatePicker()"  class="form-control input-sm" id="failuretime" name="failuretime" value="${merchantbwglist.failuretime}" class="form-control" required>
                  </div>
                </div>
                <div align="center" style="margin-top: 10px">
                  <button id="updateButton" type="button" class="btn btn-w-m btn-success"><strong>确认</strong></button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    $(function(){
      var initparsley=$("#updatForm").parsley();
      $("#updateButton").click(function(){
        $("#updatForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/merchantbwg/merchantbwgModify',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                window.location.href="${ctxPath}/mercbwg/toQueryMercbwg?_initQuery=true";
              });
            }else{
              alert("修改失败");
              //bootbox.alert("修改失败！", function () {
              //  window.location.href="${ctxPath}/customerbwg/toQueryCustomerbwg?_initQuery=true";
              //});
            }
          }
        });
      });

    });
  </script>
</div>
</html>
