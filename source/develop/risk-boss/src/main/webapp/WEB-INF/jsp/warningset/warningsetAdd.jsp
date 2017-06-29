<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>添加预警设置</title>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>添加预警设置<small>.</small></h5>
            </div>
            <div class="ibox-content">
              <form id="addForm" method="post"  class="form-inline" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div align="center">
                  <table  >
                    <tr style=" height:50px;">
                      <th><label>风控系统账户号:</label></th>
                      <td colspan="3">
                        <div class="form-group">
                          <div class="input-group">
                            <select data-placeholder="请选择账号..." class="chosen-select" name="operIds" multiple style="width:350px;" tabindex="4">
                                <c:forEach  items="${operatorlist}" var="operator">
                                  <option value="${operator.id}">${operator.username}</option>
                                </c:forEach>
                            </select required>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr  style=" height:50px;">
                      <th><label>风险等级:</label></th>
                      <td colspan="2">
                        <dict:select dictcode="risklevel" id="risklevel" name="risklevel"   cssClass="form-control" ></dict:select>
                      </td>

                      <th><label>生效日期:</label></th>
                      <td >
                        <input id="passwdeffdate" placeholder="请选择生效日期" type="text" name="effdate"
                               onClick="WdatePicker()"   class="form-control Wdate" required/>
                      </td>

                    </tr>
                    <tr>
                      <th><label>预警类型:</label></th>
                      <td colspan="2">
                        <dict:select dictcode="warntype" id="warntype" name="warntype"   cssClass="form-control" ></dict:select>
                      </td>
                      <th><label>状态:</label></th>
                      <td>
                        <div class="onoffswitch">
                          <input type="checkbox"  checked="checked" id="status" name="status" class="onoffswitch-checkbox" >
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
                  <button type="button" id="submitButton"  class="btn btn-w-m btn-success"><strong>添加</strong></button>
                  <button type="button"  onclick="back()" class="btn btn-w-m btn-info"><strong>取消</strong></button>
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
      var initparsley=$("#addForm").parsley();
      $("#submitButton").click(function(){
        $("#addForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/warningset/addWarningSet',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("添加成功！", function () {
                window.location.href="${ctxPath}/warningset/toQueryWarningSet?_initQuery=true";
              });
            }else{
              bootbox.alert("添加失败！", function () {
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
