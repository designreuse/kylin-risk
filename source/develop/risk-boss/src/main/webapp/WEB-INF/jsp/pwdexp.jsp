<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>密码修改</title>
  <script type="text/javascript">

  </script>
</head>
<body>
<form action="${ctxPath}/operator/toModifyPwd" method="post">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="modifyType" value="${modifyType}">
    <input type="hidden" name="username" value="${username}">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12" align="center">
                <h1>密码修改</h1>
                <p>${errormessage}</p>
                <p>
                    <input type="submit" class="btn btn-primary btn-lg" value="马上修改">
                </p>
            </div>
    </div>
    </div>
</form>
</body>
</html>
