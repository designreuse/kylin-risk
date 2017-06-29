<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>添加功能</title>
</head>
<body>
<form id="addFunform" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
      <div class="ibox-content m-b-sm border-bottom">
        <div class="row">
          <table class="table">
            <tr>
              <th>功能名称：</th>
              <td><input type="text" id="functionname" placeholder="请输入功能名称" name="functionname" required></td>
            </tr>
            <tr>
              <th>功能类型：</th>
              <td>
                <dict:select dictcode="funtype" id="functiontype" name="functiontype"></dict:select>
              </td>
            </tr>
            <tr>
              <th>URL：</th>
              <td><input type="text" id="url" name="url" placeholder="请输入URL" required></td>
            </tr>
            <tr>
              <th>所属菜单：</th>
              <td>
                <%--<select id="menuid" name="menuid">--%>
                  <%--<c:forEach items="${menuList}" var="menu">--%>
                    <%--<option value="${menu.id}">${menu.menuname}</option>--%>
                  <%--</c:forEach>--%>
                <%--</select>--%>
                <div class="input-group">
                  <select data-placeholder="Choose a Country..." id="menuid" name="menuid" class="chosen-select" style="width:200px;" tabindex="2">
                    <c:forEach items="${menuList}" var="menu">
                      <option value="${menu.id}">${menu.menuname}</option>
                    </c:forEach>
                  </select>
                </div>
              </td>
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
        window.location.href="${ctxPath}/function/toQueryFunction";
      });
    });

    $(function(){
      var initparsley=$("#addFunform").parsley();
      $("#submitAdd").click(function(){
        $("#addFunform").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/functions/addFunction',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("添加成功！", function () {
                window.location.href="${ctxPath}/function/toQueryFunction";
              });
            }else{
              bootbox.alert("添加失败！", function () {
                window.location.href="${ctxPath}/function/toQueryFunction";
              });
            }
          }
        });
      });
    });
  </script>
</div>
</html>
