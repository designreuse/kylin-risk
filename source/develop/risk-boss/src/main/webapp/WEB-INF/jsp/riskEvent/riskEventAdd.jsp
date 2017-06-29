<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>风险事件管理</title>
    <style>
       .fileupload-new{
         width: 240px;
       }
    </style>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title" align="center">
              <h5>确认风险事件</h5>
            </div>
            <div class="ibox-content">
              <form id="addForm" method="post" action="${ctxPath}/api/1/riskEvent/addRiskEvent?${_csrf.parameterName}=${_csrf.token}" class="form-inline"  data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" id="billIds" name="billIds" value="${billIds}" />
                <input type="hidden" id="cusid" name="cusid" value="${cusid}" />
                <input type="hidden" id="customerid" name="customerid" value="${cusnum}" />
                <div align="center">
                  <table>
                    <tr style=" height:50px;">
                      <th><label>风险事件名称:</label></th>
                      <td>
                        <input type="text" id="eventname" name="eventname" placeholder="请输入风险事件名称" class="form-control"  style="width:200px" required>
                      </td>
                      <th><label>风险事件类型:</label></th>
                      <td>
                        <input type="text"  name="eventtype" placeholder="请输入风险事件类型" class="form-control"  style="width:200px" required>
                      </td>
                    </tr>
                    <tr style=" height:50px;">
                      <th><label>风险事件来源:</label></th>
                      <td>
                        <input type="text" id="eventsource" name="eventsource" placeholder="请输入风险事件来源" class="form-control"  style="width:200px" required>
                      </td>
                    </tr>
                    <tr style=" height:50px;">
                      <th><label>风险事件概述:</label></th>
                      <td colspan="2">
                        <textarea class="form-control" id="eventdesc" name="eventdesc" style="width:250px"></textarea>
                      </td>
                    </tr>
                    <tr  style=" height:50px;">
                      <th><label>风险事件处理结果:</label></th>
                      <td>
                        <div class="form-group">
                          <select id="eventresult" name="eventresult" class="form-control">
                            <option value="1">已处理
                            </option>
                            <option value="0">未处理
                            </option>
                          </select>
                        </div>
                      </td>
                    </tr>
                    <tr  style=" height:55px;">
                      <th><label>上传文件:</label></th>
                      <td>
                        <input  type="file" class="form-control input-sm" id="fileUpload" name="fileUpload" class="form-control"
                                accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/msword">
                      </td>
                    </tr>
                  </table>
                </div>
                <div align="center" style=" height:50px;">
                  <button type="button" id="submitButton"  class="btn btn-w-m btn-success"><strong>添加</strong></button>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

    var csrfToken="${_csrf.token}";
    var csrfHeaderName="${_csrf.headerName}";

    function back(){
      window.location.href="${ctxPath}/riskEvent/toQueryRiskEventAdd";
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
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("添加成功！", function () {
                window.location.href="${ctxPath}/riskEvent/toQueryRiskEvent?_initQuery=true"
              });
            }else{
              bootbox.alert("添加失败！", function () {
                window.location.href="${ctxPath}/riskEvent/toQueryRiskEventAdd"
              });
            }
          }
        });
      });
    });
  </script>
</div>
</body>
</html>
