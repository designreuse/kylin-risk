<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>操作成功</title>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>操作信息成功<small>.信息如下</small></h5>
              <div class="ibox-tools">
                <a class="collapse-link">
                  <i class="fa fa-chevron-up"></i>
                </a>
                <a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
                  <i class="fa fa-wrench"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                  <li><a href="form_basic.html#">Config option 1</a>
                  </li>
                  <li><a href="form_basic.html#">Config option 2</a>
                  </li>
                </ul>
                <a class="close-link">
                  <i class="fa fa-times"></i>
                </a>
              </div>
            </div>
            <div class="ibox-content">
              <form method="post" action="" class="form-inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div align="center">
                  <table  >
                    <tr style=" height:50px;">
                      <th><label>登录名:</label></th>
                      <td>
                        <input type="text" readonly="readonly" name="username" class="form-control" value="${operator.username}" style="width:250px">
                      </td>
                      <th><label>真实姓名:</label></th>
                      <td>
                        <input type="text"  readonly="readonly" name="realname" class="form-control" value="${operator.realname}" style="width:250px">
                      </td>
                    </tr>
                    <tr style=" height:50px;">
                      <th><label>电话号码:</label></th>
                      <td>
                        <input type="text"  name="phone" class="form-control" value="${operator.phone}" style="width:250px">
                      </td>
                      <th><label>Email:</label></th>
                      <td>
                        <input type="text"  name="email" class="form-control" value="${operator.email}" style="width:250px">
                      </td>
                    </tr>

                    <tr style=" height:50px;">
                      <th><label>生效日期:</label></th>
                      <td>
                        <input name="passwdeffdate"class="form-control" style="width:250px" value="${operator.passwdeffdate}"readonly="readonly"/>
                      </td>

                      <th><label>失效日期:</label></th>
                      <td>
                        <input name="passwdexpdate" type="text" name="passwdexpdate"   class="form-control" value="${operator.passwdexpdate}" style="width:250px"readonly="readonly" />
                      </td>
                    </tr>

                    <tr  style=" height:50px;">
                      <th><label>操作员类型:</label></th>
                      <td>
                        <select  style="width:250px" class="form-control m-b" id="opertype" name="opertype" value="${operator.opertype}">
                          <c:if test="${operator.opertype=='admin'}"><option value="admin">管理员</option></c:if>
                          <c:if test="${operator.opertype=='oper'}"> <option value="oper">操作员</option> </c:if>
                        </select>
                      </td>
                      <th><label>状态:</label></th>
                      <td>
                        <div class="onoffswitch">
                          <input type="checkbox"  <c:if test="${operator.status=='00'}">checked="checked"</c:if> id="status" name="status" class="onoffswitch-checkbox" >
                          <label class="onoffswitch-label" for="status">
                            <span class="onoffswitch-inner"></span>
                            <span class="onoffswitch-switch"></span>
                          </label>
                        </div>
                      </td>
                    </tr>

                    <tr>
                      <th>角色信息</th>
                      <td colspan="3" style="padding-right:10px">
                        <table class="table table-bordered table-striped" >
                          <tr>
                            <%int i = 0;%>
                            <c:forEach items="${newList}" var="role">
                              <td width="25%">
                                <c:if test='${role.remark=="checked"}'>
                                  <input type="checkbox" checked="checked" name="roleIds" value="${role.id}">
                                </c:if>
                                <c:if test='${role.remark!="checked"}'>
                                  <input type="checkbox" name="roleIds" value="${role.id}">
                                </c:if>
                                &nbsp;${role.rolename}
                              </td>
                              <%
                                if( ++i%4 == 0){
                                  i=0;
                                  out.println("</tr><tr>");
                                }
                              %>
                            </c:forEach>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                </div>
                <div align="center">
                  <button type="button" onclick="back()" class="btn btn-w-m btn-success"><strong>返回</strong></button>
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
    function back(){
      window.location.href="${ctxPath}/operator/toQueryOperator";
    }
  </script>
</div>
</html>
