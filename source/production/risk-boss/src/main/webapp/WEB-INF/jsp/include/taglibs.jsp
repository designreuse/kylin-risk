<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="/risk/tags/util" prefix="r" %>
<%@ taglib uri="/risk/tag/dict" prefix="dict" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<c:set var="cssPath" value="${pageContext.request.contextPath}/css"/>
<c:set var="jsPath" value="${pageContext.request.contextPath}/js"/>
<c:set var="libPath" value="${pageContext.request.contextPath}/lib"/>
<c:set var="noScript" value="<div class='alert alert-danger text-center'>您的浏览器不支持JavaScript，请启用JavaScript</div>"/>

