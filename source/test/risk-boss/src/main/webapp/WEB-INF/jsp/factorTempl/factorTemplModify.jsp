<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>因子模板修改</title>
</head>
<body>
<form id="modifyFactorTemplform" method="post" data-parsley-validate class="form-horizontal">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="initialtype" id="initialtype" value="${factorTempl.groupid}"/>
    <input type="hidden"  name="id" value="${factorTempl.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content   float-e-margins">
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label " >因子名称:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="name" name="name" value="${factorTempl.name}" required>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label " >因子代码:</label>
              <div class="col-sm-3 m-b">
                <input class="form-control " type="text" id="code" name="code" value="${factorTempl.code}" required readonly>
              </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
              <label class="col-sm-2 control-label" >归属规则组:</label>
              <div class="col-sm-3 m-b">
                  <select name="groupid" id="rulegroup2" class="form-control" required></select>
              </div>
            </div>
        </div>

        <div class="row query-div">
          <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="修改">&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
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
          addRuleCategory($("#rulegroup2"),$("#initialtype").val());
          $("#back").backup();
      });
      $(function(){
          $("#submitAdd").click(function(){
            subRuleAdd();
          });
      });

      function addRuleCategory(factorSelect,initype){
          factorSelect.append("<option value=''>---请选择---</option>");
          $.getJSON("${ctxPath}/api/1/group/queryAllGroup",function(json){
              $.each(json.data, function(index,factors){
                  if(initype==factors['id']){
                      factorSelect.append('<option value="' + factors['id'] + '" selected="selected">' + factors['groupname'] + '</option>');
                  }else{
                      factorSelect.append('<option value="' + factors['id'] + '">' + factors['groupname'] + '</option>');
                  }
              });

          });
      }

      function subRuleAdd(){
          var initparsley=$("#modifyFactorTemplform").parsley();
          $("#modifyFactorTemplform").ajaxSubmit({
              type:'post',
              dataType: 'json',
              beforeSubmit:function(){
                  return initparsley.validate();
              },
              url: '${ctxPath}/api/1/factorTempl/updateFactorTempl',
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
      }
  </script>
</div>
</html>
