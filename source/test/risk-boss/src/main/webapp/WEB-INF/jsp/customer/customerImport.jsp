<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
</head>
<body>
<div id="wrapper" >
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="fileform" method="post" data-parsley-validate action="${ctxPath}/api/1/customer/importCustomerInfo?${_csrf.parameterName}=${_csrf.token}" class="form-inline"  enctype="multipart/form-data" >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="row">
          <div class="col-sm-8">
            <label class="control-label" for="fileUpload">上传文件</label>
            <input  type="file" class="form-control input-sm" required id="fileUpload" name="fileUpload" class="form-control" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" >
          </div>
        </div>
        <div class="modal-footer" style="border-top:0px solid">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <input type="button" id="submitButton" class="btn btn-primary" value="导入"/>
        </div>
      </form>
    </div>
  </div>
</div>



</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    $(function(){
      $("#submitButton").click(function(){
        $("#fileform").ajaxSubmit({
          dataType: 'json',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("上传成功！", function () {
                $('#myModal').modal('hide');
                window.location.href="${ctxPath}/customer/toQueryCustomer.action?_initQuery=true";
              });
            }else{
              bootbox.alert(data.message, function () {
                $('#myModal').modal('hide');
              });
            }
          }
        });
      });

      $("#myModal").on("hidden", function() {
        $(this).removeData("modal");});

    });
  </script>
</div>
</html>
