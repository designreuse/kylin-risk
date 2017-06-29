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
          <form id="addForm" method="post"  class="form-inline">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="addIds" id="addIds" value="${ids}">
            <div align="center">
              <table  >
                <tr style=" height:80px;">
                  <th><label>案例名称:</label></th>
                  <td >
                    <input id="casename" placeholder="请输入案例名称" type="text" name="casename"
                          class="form-control " />
                  </td>
                </tr>
                <tr style=" height:80px;">
                  <th><label>案例描述:</label></th>
                  <td >
                    <textarea style="width: 400px; height: 300px;" placeholder="请输入案例描述" name="casedesc"></textarea>
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
    var csrfHeaderName="${_csrf.headerName}";
    $(function(){
      var initparsley=$("#addForm").parsley();
      $("#submitButton").click(function(){
        $("#addForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/dubioustxn/addCase',
          success: function(data,textStatus,jqXHR){
            if(textStatus=="success"){
              $('#myModal').modal('hide');
              bootbox.alert("添加成功！", function () {
                /* window.location.href=""*/
              });
            }else{
              $('#myModal').modal('hide');
              bootbox.alert("添加失败！", function () {
              });
            }
          }
        });
      });

    });
  </script>
</div>
</html>
