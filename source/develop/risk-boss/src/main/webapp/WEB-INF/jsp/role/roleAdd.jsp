<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>添加角色</title>
</head>
<body>
<form method="post" id="roleAddForm">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                      <td><input name="rolename" placeholder="请输入角色名称" id="rolename" required></td>
                    </tr>
                    <tr>
                      <th>说明：</th>
                      <td><input placeholder="请输入说明" name="remark" ></td>
                    </tr>
                  </table>
                </div>
                <div class="tab-pane" id="tab-2">
                  <%--一级菜单--%>
                  <c:forEach items="${menuFirstLev}" var="menuFir" varStatus="all">
                      <div class="col-lg-4">
                        <div class="panel panel-default">
                          <div class="panel-heading">
                            <input type="checkbox" levFlag="firLev${all.count}" onclick="setChecked(this,'firLev',${all.count},'')" name="menuchoose" value="${menuFir.id}">${menuFir.menuname}
                          </div>
                          <div class="panel-body">
                                <%--二级菜单--%>
                              <c:forEach items="${menuSecondLev}" var="menuSec" varStatus="status">
                              <c:if test="${menuSec.parentid==menuFir.id}">
                                  <input type="checkbox" levFlag="secLev${all.count}_${status.index}" onclick="setChecked(this,'secLev',${all.count},'${status.index}')" name="menuchoose" value="${menuSec.id}">
                                <a data-toggle="collapse" href="#faq${menuSec.id}" aria-expanded="true">
                                  ${menuSec.menuname}
                                </a>
                            <div id="faq${menuSec.id}" class="panel-collapse collapse">
                              <div class="faq-answer">
                                <table>
                                  <tbody>
                                  <tr>
                                  <c:forEach items="${functionsList}" var="fun" varStatus="i">
                                    <td>
                                      <c:if test="${fun.menuid==menuSec.id}">
                                      <input type="checkbox" levFlag="thrLev${all.count}_${status.index}" onclick="setChecked(this,'thrLev',${all.count},${status.index})" name="funchoose" value="${fun.id}">${fun.functionname}
                                      </c:if>
                                    </td>
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
                        <%--<input type="checkbox" name="funchoose" value="${fun.id}">${fun.functionname}</td>--%>
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
            <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
          </center>
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
        window.location.href="${ctxPath}/role/toQueryRole";
      });
    });

    $(function(){
      var initparsley=$("#roleAddForm").parsley();
      $("#submitAdd").click(function(){
        if($("input[name='menuchoose']:checked").length<1){
          alert("请选择菜单信息");
          return;
        }
        if($("input[name='funchoose']:checked").length<1){
          alert("请选择功能信息");
          return;
        }
        $("#roleAddForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/role/addRole',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("添加成功！", function () {
                window.location.href="${ctxPath}/role/toQueryRole";
              });
            }else{
              bootbox.alert("添加失败！", function () {
                window.location.href="${ctxPath}/role/toQueryRole";
              });
            }
          }
        });
      });
    });

    function setChecked(obj,levtype,num,index){

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
