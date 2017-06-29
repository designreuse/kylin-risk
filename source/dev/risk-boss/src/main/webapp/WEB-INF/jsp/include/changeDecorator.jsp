<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <sitemesh:write property='meta' />
    <title><sitemesh:write property='title' /></title>
    <%@ include file="/WEB-INF/jsp/include/defaultHeader.jsp" %>
    <sitemesh:write property='head' />
    <sitemesh:write property='div.siteMeshCSS' />
</head>
<body>
<noscript>${noScript}</noscript>
<sitemesh:write property='body' />
<%@ include file="/WEB-INF/jsp/include/defaultScript.jsp" %>
<script src="${libPath}/inspinia/inspinia.js"></script>


<sitemesh:write property='div.siteMeshJavaScript' />
</body>
</html>