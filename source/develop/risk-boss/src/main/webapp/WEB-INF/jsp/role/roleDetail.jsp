<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>角色详细信息</title>
</head>
<body>
<form id="roleModifyForm" method="post">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="id" value="${role.id}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
      <div class="ibox-content m-b-sm border-bottom">
        <div class="row">
          <div class="panel">
            <div class="panel-heading">
              <div class="panel-options">
                <ul class="nav nav-tabs">
                  <li class="active"><a href="#tab-1" data-toggle="tab" aria-expanded="true">角色基本信息</a></li>
                  <li class=""><a href="#tab-2" data-toggle="tab" aria-expanded="false">角色权限</a></li>
                  <%--<li class=""><a href="#tab-3" data-toggle="tab" aria-expanded="false">角色功能</a></li>--%>
                </ul>
              </div>
            </div>
            <div class="panel-body">
              <div class="tab-content">
                <div class="tab-pane active" id="tab-1">
                  <table class="table">
                    <tr>
                      <th>角色名称：</th>
                      <td><input name="rolename" id="rolename" value="${role.rolename}" required></td>
                    </tr>
                    <tr>
                      <th>说明：</th>
                      <td><input name="remark" value="${role.remark}"></td>
                    </tr>
                    <tr>
                      <th>状态：</th>
                      <td>
                        <%--<select id="status"  name="status">--%>
                          <%--<option value="01" <c:if test="${role.status=='01'}"> selected</c:if>>---- 无效- ---</option>--%>
                          <%--<option value="00" <c:if test="${role.status=='00'}">selected</c:if>>---- 有效- ---</option>--%>
                        <%--</select>--%>
                          <div class="switch">
                            <div class="onoffswitch">
                              <input id="status" type="checkbox" name="status" value="00" <c:if test="${role.status=='00'}">checked</c:if> class="onoffswitch-checkbox" id="example1">
                              <label class="onoffswitch-label" for="status">
                                <span class="onoffswitch-inner"></span>
                                <span class="onoffswitch-switch"></span>
                              </label>
                            </div>
                          </div>
                      </td>
                    </tr>
                  </table>
                </div>
                <div class="tab-pane" id="tab-2">
                  <%--一级菜单--%>
                  <c:forEach items="${menuFirstLev}" var="menuFir" varStatus="all">
                    <%--<c:if test="${all.count%3==1}">--%>
                      <div class="row">
                    <%--</c:if>--%>
                    <div class="col-lg-4">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <input type="checkbox" checkCoun='fir' levFlag="firLev${all.count}" onclick="setChecked(this,'firLev',${all.count},'')" name="menuchoose" <c:if test="${menuFir.menuurl=='1'}">checked</c:if> value="${menuFir.id}">${menuFir.menuname}
                        </div>
                        <div class="panel-body">
                          <%--二级菜单--%>
                          <c:forEach items="${menuSecondLev}" var="menuSec"  varStatus="status">
                            <c:if test="${menuSec.parentid==menuFir.id}">
                              <input type="checkbox" levFlag="secLev${all.count}_${status.index}" onclick="setChecked(this,'secLev',${all.count},${status.index})" name="menuchoose" <c:if test="${menuSec.menuurl=='1'}">checked</c:if> value="${menuSec.id}">
                              <a data-toggle="collapse" href="#faq${menuSec.id}" aria-expanded="true">
                                  ${menuSec.menuname}
                              </a>
                          <div id="faq${menuSec.id}" class="panel-collapse collapse">
                            <div class="faq-answer">
                              <table>
                                <tbody>
                                <tr>
                                <c:forEach items="${functionsList}" var="fun" varStatus="i">
                                  <%--<c:if test="${i.count%8==1}">
                                    <tr>
                                  </c:if>--%>
                                  <td>
                                    <c:if test="${fun.menuid==menuSec.id}">
                                    <input type="checkbox" levFlag="thrLev${all.count}_${status.index}" onclick="setChecked(this,'thrLev',${all.count},${status.index})"  name="funchoose" <c:if test="${fun.url=='1'}">checked</c:if> value="${fun.id}">
                                    <span <c:if test="${fun.url=='1'}">style="color: #eb1d0b"</c:if>>${fun.functionname}</span></td>
                                    </c:if>
                                  <%--<c:if test="${i.count%8==0}">
                                    </tr>
                                  </c:if>--%>
                                </c:forEach>
                                </tr>
                                </tbody>
                              </table>
                            </div>
                        </div>
                          </c:if>
                          </c:forEach>
                          </p>
                        </div>
                      </div>
                    </div>
                    <%--<c:if test="${all.count%3==0}">--%>
                      </div>
                    <%--</c:if>--%>
                  </c:forEach>
                </div>
                </div>
                <%--<div class="tab-pane" id="tab-3">--%>
                  <%--<table class="table">--%>
                    <%--<tbody>--%>
                    <%--<c:forEach items="${functionsList}" var="fun" varStatus="i">--%>
                      <%--<c:if test="${i.count%8==1}">--%>
                        <%--<tr>--%>
                      <%--</c:if>--%>
                      <%--<td>--%>
                        <%--<input type="checkbox" name="funchoose" <c:if test="${fun.url=='1'}">checked</c:if> value="${fun.id}"><span <c:if test="${fun.url=='1'}">style="color: #eb1d0b"</c:if>>${fun.functionname}</span></td>--%>
                        <%--<c:if test="${i.count%8==0}">--%>
                        <%--</tr>--%>
                      <%--</c:if>--%>
                    <%--</c:forEach>--%>
                    <%--</tbody>--%>
                  <%--</table>--%>
                <%--</div>--%>
              </div>
            </div>
          <center>
            <c:if test="${dealType=='modify'}">
              <input type="button" id="submitModify" class="btn btn-w-m btn-success" value="提交修改">&nbsp;&nbsp;&nbsp;&nbsp;
            </c:if>
            <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
          </center>
          </div>
        </div>
      </div>
    </div>
</form>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    $(function(){
      $("#back").click(function(){
        window.location.href="${ctxPath}/role/toQueryRole.action?_initQuery=true&rolename=${roles.rolename}&status=${roles.status}";
      });
    });
    $(function(){
      var initparsley=$("#roleModifyForm").parsley();
      $("#submitModify").click(function(){
        if($("input[name='menuchoose']:checked").length<1){
          alert("请选择菜单信息");
          return;
        }
        if($("input[name='funchoose']:checked").length<1){
          alert("请选择功能信息");
          return;
        }
        $("#roleModifyForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/role/updateRole',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                window.location.href="${ctxPath}/role/toQueryRole";
              });
            }else{
              bootbox.alert("修改失败！", function () {
                window.location.href="${ctxPath}/role/toQueryRole";
              });
            }
          }
        });
      });
    });

   function setChecked(obj,levtype,num,index) {
     if("thrLev"==levtype){//操作三级功能菜单
       var levFlagsec="secLev"+num+"_"+index;
       var levFlagfir="firLev"+num;
       if(obj.checked==true) {
         $("form input[levFlag="+levFlagsec+"]").attr("checked", true);
         $("form input[levFlag="+levFlagfir+"]").attr("checked", true);
       }else{
         if($("input[levFlag="+"thrLev"+num+"_"+index+"]:checked").length<1){
           $("form input[levFlag="+levFlagsec+"]").attr("checked", false);
         }
         if ($("input[levFlag^=" + "secLev" + num + "_]:checked").length < 1) {
           $("form input[levFlag=" + levFlagfir + "]").attr("checked", false);
         }
       }
       }
      if("secLev"== levtype){
        //操作二级菜单
        var levFlagres="firLev"+num;
        var thirdLev="thrLev"+num+"_"+index;
        if (obj.checked == true) {
          $("form input[levFlag=" + levFlagres + "]").attr("checked", true);
          $("form input[levFlag=" + thirdLev + "]").attr("checked", true);
        } else {
          if ($("input[levFlag^=" + "secLev" + num + "_]:checked").length < 1) {
            $("form input[levFlag=" + levFlagres + "]").attr("checked", false);
          }
          $("form input[levFlag=" + thirdLev + "]").attr("checked", false);
        }
      }

     if("firLev"==levtype){//操作一级菜单
       var secLev="secLev"+num;
       var threeLev="thrLev"+num;
       if(obj.checked==true) {
         $("form input[levFlag^="+secLev+"]").attr("checked", true);
         $("form input[levFlag^="+threeLev+"]").attr("checked", true);

       }else {
         $("form input[levFlag^=" + secLev + "]").attr("checked", false);
         $("form input[levFlag^=" + threeLev + "]").attr("checked", false);
       }
     }
   }

  </script>
</div>
</html>
