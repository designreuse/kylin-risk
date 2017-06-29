<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>功能详细信息</title>
</head>
<body>
<form  id="updFunform" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="id" value="${function.id}"/>
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="ibox-content m-b-sm border-bottom">
        <div class="row">
          <table class="table">
            <tr>
              <th>功能名称</th>
              <td><input type="text" id="functionname" name="functionname" value="${function.functionname}" required></td>
            </tr>
            <tr>
              <th> 功能类型</th>
              <td>
                <dict:select dictcode="funtype" id="functiontype" name="functiontype" value="${function.functiontype}"></dict:select>
              </td>
            </tr>
            <tr>
              <th>URL</th>
              <td><input type="text" id="url" name="url" value="${function.url}" required></td>
            </tr>
            <tr>
              <th>状态</th>
              <td>
                  <div class="switch">
                    <div class="onoffswitch">
                      <input id="status" type="checkbox" name="status" value="00" <c:if test="${function.status=='00'}">checked</c:if> class="onoffswitch-checkbox" id="example1">
                      <label class="onoffswitch-label" for="status">
                        <span class="onoffswitch-inner"></span>
                        <span class="onoffswitch-switch"></span>
                      </label>
                    </div>
                  </div>
              </td>
            </tr>
            <tr>
              <th>所属菜单</th>
              <td>
                <%--<select id="menuid" name="menuid">--%>
                  <%--<c:forEach items="${menuList}" var="menu">--%>
                    <%--<option value="${menu.id}" <c:if test="${function.menuid==menu.id}">selected</c:if>>${menu.menuname}</option>--%>
                  <%--</c:forEach>--%>
                <%--</select>--%>
                <div class="input-group">
                  <select data-placeholder="Choose a Country..." id="menuid" name="menuid" class="chosen-select" style="width:200px;" tabindex="2">
                    <c:forEach items="${menuList}" var="menu">
                      <option value="${menu.id}" <c:if test="${function.menuid==menu.id}">selected</c:if>>${menu.menuname}</option>
                    </c:forEach>
                  </select>
                </div>
              </td>
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
      $("#back").click(function(){
        window.location.href="${ctxPath}/function/toQueryFunction.action?_initQuery=true&functionname=${functions.functionname}&status=${functions.status}&url=${functions.url}&functiontype=${functions.functiontype}";
      });
    });
    $(function(){
      var initparsley=$("#updFunform").parsley();
      $("#submitModify").click(function(){
        $("#updFunform").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/functions/updateFunction',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                window.location.href="${ctxPath}/function/toQueryFunction";
              });
            }else{
              bootbox.alert("修改失败！", function () {
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
