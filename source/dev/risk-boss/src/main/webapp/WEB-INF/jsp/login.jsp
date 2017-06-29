<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>风控系统</title>
</head>
<body>
<div class="middle-box text-center loginscreen animated fadeInDown">
  <div>

    <h3>风控系统</h3>

          <p class="red-bg">${errormessage}</p>
    <sf:form action="${ctxPath}/operatorLogin" method="post" cssClass="m-t" role="form" data-parsley-validate="true">
      <div class="form-group">
        <input type="text" name="username" class="form-control" placeholder="用户名" required>
      </div>
      <div class="form-group">
        <input type="password" name="passwd" class="form-control" placeholder="密码" required>
      </div>
      <div class="form-group">
        <div class="row">
          <div class="col-xs-7">
            <input type="text" name="randomCode" class="form-control" placeholder="验证码" required>
          </div>
          <div class="col-xs-5">
            <a href="#" id="code">
              <img src="${ctxPath}/api/1/randomCode/createCode.action" id="codeImage" />
            </a>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-primary block full-width m-b">登录</button>

    </sf:form>


  </div>

</div>
<div class="footer" >
  <div>
    <strong>Copyright</strong> &copy;Rkylin Company
  </div>
</div>
<script type="text/javascript">
  if(top.window.location.href!=window.location.href) {top.window.location.href=window.location.href;}
</script>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">

    $(function(){
      //刷新验证码+
      $("#code").click(function(){
        $("#codeImage").attr("src", "${ctxPath}/api/1/randomCode/createCode.action?now=" + new Date());
      });
    });

  </script>
</div>
</html>
