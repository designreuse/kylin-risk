<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>修改密码</title>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>修改密码<small>.</small></h5>
            </div>
            <div class="ibox-content">
              <form method="post" id="passwdform" class="form-horizontal" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" id="flag">
                <input type="hidden" id="username" name="username" value="${sessionScope.auth.username}">
                <div class="form-group">
                  <label class="col-sm-2 control-label">旧密码</label>
                  <div class="col-sm-4">
                    <input type="password" id="oldpwd" name="oldpwd" class="form-control" placeholder="请输入旧密码" onchange="checkoldpasswd()" >
                  <span id="oldpwdMsg" style="color: red"></span>
                  </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">新密码</label>
                  <div class="col-sm-4">
                    <input type="password" id="passwd" name="passwd" class="form-control" placeholder="请输入新密码" required data-parsley-trigger="blur" data-parsley-notequalto="#oldpwd" data-parsley-notequalto-message="不能与原密码一致" required data-parsley-length="[6, 10]">
                    <span id="newPWD"></span>
                  </div>
                </div>

                <div class="hr-line-dashed"></div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">确认新密码</label>
                  <div class="col-sm-4">
                    <input type="password" id="confirmpwd" name="confirmpwd" class="form-control" name="password" placeholder="请确认新密码" required data-parsley-trigger="blur" data-parsley-equalto="#passwd" data-parsley-equalto-message="与新密码不一致">
                  </div>
                </div>
                <div class="hr-line-dashed"></div>
                <div align="center">
                  <button type="button"  id="buttonsubmit" class="btn btn-w-m btn-success"><strong>修改</strong></button>
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
    function checkoldpasswd(){
      var oldpwd=$("#oldpwd").val();
      $.ajax({
        type : "POST",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url : "${ctxPath}/api/1/operator/checkPasswd",
        data : "oldpwd="+oldpwd,
        dataType: "text",
        success : function(data, textStatus, jqXHR) {
          if("false" == $.trim(data)){
            $("#oldpwdMsg").html("密码错误！");
            $("#flag").val("false");
          }else{
            $("#oldpwdMsg").html("");
            $("#flag").val("true");
          }
        }
      });
    }

    var form =  $("#passwdform").parsley();

    $(function(){
      $('#buttonsubmit').click(function(){
        form.validate();
        var falg=$("#flag").val();
        if(falg=="true"){
        }else{
          bootbox.alert("原密码不正确");
          return false;
        }
        var passwd=$("#passwd").val();
        $('#passwdform').ajaxSubmit({
                  type:'post',
                  dataType: 'json',
                  url: '${ctxPath}/api/1/operator/updatePasswd',
                  success: function(data,textStatus,jqXHR){
                    if(data.code == 200){
                      bootbox.alert("修改密码成功！", function () {
                        window.location.href="${ctxPath}/operator/toModifyPassWd";
                      });
                    }else{
                      bootbox.alert("修改密码失败！", function () {
                        window.location.href="${ctxPath}/operator/toModifyPassWd";
                      });
                    }
                  }
        });
      });
    });

    </script>
  </div>

</html>
