<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
</head>
<body>
<div id="wrapper">
  <div class="row">
    <div class="col-lg-12">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
          <form id="addBwgForm" method="post" data-parsley-validate  class="form-inline">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="addIds" id="addIds" value="${ids}">
            <input type="hidden" name="type" id="type" value="${type}">

            <div align="center">
              <table  >
                <tr style=" height:50px;">
                  <th><label>生效日期:</label></th>
                  <td >
                    <input id="datebegin" placeholder="请选择生效日期(可为空)" type="text" name="effecttime"
                           onClick="WdatePicker()"   class="form-control Wdate" required />
                  </td>
                  <th><label>失效日期:</label></th>
                  <td >
                    <input id="dateend" placeholder="请选择生效日期(可为空)" type="text" name="failuretime"
                           onClick="WdatePicker()"   class="form-control Wdate" required />
                  </td>
                </tr>

              </table>
            </div>
            <div class="modal-footer" style="border-top:0px solid">
              <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              <button type="button" id="submitButton" class="btn btn-primary">确认添加</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >

    var csrfToken="${_csrf.token}";
    var csrfHeaderName="${_csrf.headerName}"
    $(function(){
      $("#submitButton").click(function(){
        if($("#addBwgForm").parsley().validate()){
          $("#addBwgForm").ajaxSubmit({
            type:'post',
            dataType: 'json',
            url: '${ctxPath}/api/1/merchantbwg/addBWGfromMerc',
            success: function(data,textStatus,jqXHR){
              if(data.code==200){
                $('#myModal1').modal('hide');
                bootbox.alert("添加成功！",function(){
                  $.backquery();
                });
              }else{
                bootbox.alert("添加失败！", function () {
                  $('#myModal1').modal('hide')
                });
              }
            }
          });
        }

      });
    });
  </script>
</div>
</html>
