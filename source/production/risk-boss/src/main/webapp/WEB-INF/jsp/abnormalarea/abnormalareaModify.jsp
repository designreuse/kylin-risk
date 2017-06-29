<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>修改异常地区代码</title>
</head>
<body>
<div id="wrapper" >
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>修改异常地区代码<small>.</small></h5>
            </div>
            <div class="ibox-content">
              <form id="updateAbnormalarea" method="post" class="form-inline" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${abnormalArea.id}">
                <div align="center">
                  <table  >
                    <tr style=" height:50px;">
                      <th><label>地区代码:</label></th>
                      <td>
                        <input type="text" readonly="readonly" name="code" class="form-control" value="${abnormalArea.code}" style="width:250px">
                      </td>
                      <th><label>所在省:</label></th>
                      <td>
                        <input type="text"   name="provnm" class="form-control" value="${abnormalArea.provnm}" style="width:250px" required>
                      </td>
                    </tr>
                    <tr style=" height:50px;">
                      <th><label>所在市:</label></th>
                      <td>
                        <input type="text"  name="citynm" class="form-control" value="${abnormalArea.citynm}" style="width:250px" required>
                      </td>
                      <th><label>所在县名:</label></th>
                      <td>
                        <input type="text"  name="countynm" class="form-control" value="${abnormalArea.countynm}" style="width:250px" required>
                      </td>
                    </tr>

                    <tr style=" height:50px;">
                      <th><label>地区名称:</label></th>
                      <td>
                        <input type="text"  name="name" class="form-control" value="${abnormalArea.name}" style="width:250px" required>
                      </td>
                      <th><label>所属反洗钱组织:</label></th>
                      <td>
                        <select  style="width:250px" class="form-control m-b" id="type" name="type"  required>
                          <option >---组织1---</option>
                          <option value="1"<c:if test="${abnormalArea.type=='1'}">selected</c:if>>组织1</option>
                          <option value="2" <c:if test="${abnormalArea.type=='2'}">selected</c:if>>组织2</option>
                        </select>
                      </td>
                    </tr>
                  </table>
                </div>
                <div align="center">
                  <button id="update" type="button" class="btn btn-w-m btn-success"><strong>确认修改</strong></button>
                  <button type="button" id="back" class="btn btn-w-m btn-info"><strong>取消</strong></button>
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
    $(function(){
      $("#back").backup();
      var initparsley=$("#updateAbnormalarea").parsley();
      $("#update").click(function(){
        $("#updateAbnormalarea").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/abnormalarea/abnormalareaModify',
          success: function(data,textStatus,jqXHR){
            if(data.code==200){
              bootbox.alert("修改成功！", function () {
                $.backquery();
              });
            }else{
              bootbox.alert("修改失败！");
            }
          }
        });
      });

    });
  </script>
</div>
</html>
