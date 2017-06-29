<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>修改预警设置</title>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>修改预警设置<small>.</small></h5>

            </div>
            <div class="ibox-content">
              <form id="updateWSForm" method="post" action="${ctxPath}/api/1/warningset/modifyWarnSet" class="form-inline" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${warningset.id}">
                <div align="center">
                  <table  >
                    <tr style=" height:50px;">
                      <th><label>操作员ID:</label></th>
                      <td>
                        <input type="text" readonly="readonly" name="operatorid" class="form-control" value="${warningset.operatorid}" style="width:250px">
                      </td>
                      <th><label>操作员账号:</label></th>
                      <td>
                        <input type="text"  readonly="readonly" name="username" class="form-control" value="${warningset.username}" style="width:250px">
                      </td>
                    </tr>
                    <tr style=" height:50px;">
                      <th><label>风险等级:</label></th>
                      <td >
                        <dict:select dictcode="risklevel" id="risklevel" name="risklevel"   cssClass="form-control" value="${warningset.risklevel}"></dict:select>
                      </td>
                      <th><label>预警类型:</label></th>
                      <td >
                        <dict:select dictcode="warntype" id="warntype" name="warntype"   cssClass="form-control" value="${warningset.warntype}" ></dict:select>
                      </td>

                    </tr>

                    <tr style=" height:50px;">
                      <th><label>生效日期:</label></th>
                      <td >
                        <input id=effdate" placeholder="请选择生效日期" type="text" name="effdate"
                               onClick="WdatePicker()"   class="form-control Wdate"  value="${warningset.effdate}" required/>
                      </td>

                      <th><label>状态:</label></th>
                      <td>
                        <div class="onoffswitch">
                          <input type="checkbox"  <c:if test="${warningset.status=='00'}">checked="checked"</c:if> id="status" name="status" class="onoffswitch-checkbox" >
                          <label class="onoffswitch-label" for="status">
                            <span class="onoffswitch-inner"></span>
                            <span class="onoffswitch-switch"></span>
                          </label>
                        </div>
                      </td>
                    </tr>

                  </table>
                </div>
                <div align="center">
                  <button type="button"  id="updateButton" type="button" class="btn btn-w-m btn-success"><strong>确认修改</strong></button>
                  <button type="button" onclick="back()" class="btn btn-w-m btn-info"><strong>取消</strong></button>
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
      window.location.href="${ctxPath}/warningset/toQueryWarningSet?_initQuery=true";
    }
    $(function(){
      var initparsley=$("#updateWSForm").parsley();
      $("#updateButton").click(function(){
        $("#updateWSForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/warningset/modifyWarnSet',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                window.location.href="${ctxPath}/warningset/toQueryWarningSet?_initQuery=true";
              });
            }else{
              bootbox.alert("修改失败！", function () {
                window.location.href="${ctxPath}/warningset/toQueryWarningSet?_initQuery=true";
              });
            }
          }
        });
      });
    });
  </script>
</div>
</html>
