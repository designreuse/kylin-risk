<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>风控系统</title>
    <link href="${cssPath}/contabs.css" rel="stylesheet" type="text/css"/>
    <link href="${libPath}/bootstrap-contextmenu/jquery.contextMenu.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        html,body{
            overflow: hidden;
        }
    </style>
    <script type="text/javascript">
        if(top.window.location.href!=window.location.href) {top.window.location.href=window.location.href;}
    </script>
</head>
<body>
<div id="wrapper">
    <nav id="nav-panel" class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse" id="menudiv">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="form_basic.html#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">${sessionScope.auth.realname}</strong>
                             </span> <span class="text-muted text-xs block"><c:if test="${sessionScope.auth.operatorType eq 'admin'}">管理员</c:if><c:if test="${sessionScope.auth.operatorType eq 'oper'}">操作员</c:if><b
                                    class="caret"></b></span> </span> </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="${ctxPath}/operator/toModifyPassWd.action"  target="_parent">修改密码</a></li>
                        </ul>
                    </div>
                </li>
                <c:forEach items="${sessionScope.auth.menu.children}" var="menu" varStatus="i">
                    <c:if test="${i.count==1}">
                        <li class="active">
                            <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">${menu.menuname}</span><span
                                    class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <c:forEach items="${menu.children}" var="children">
                                    <li><a class="J_menuItem" href="${ctxPath}/${children.menuurl}">${children.menuname}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${i.count!=1}">
                        <li >
                        <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">${menu.menuname}</span><span
                                class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <c:forEach items="${menu.children}" var="children">
                                    <li><a class="J_menuItem"  href="${ctxPath}/${children.menuurl}">${children.menuname}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>

                </c:forEach>
            </ul>
        </div>
    </nav>

    <div id="page-wrapper" class="gray-bg">
        <div class="row">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">欢迎访问风控系统</span>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="form_basic.html#">
                            <i class="fa fa-bell"></i> <span class="label label-primary">8</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a href="profile.html">
                                    <div>
                                        <i class="fa fa-twitter fa-fw"></i> 3 New Followers
                                        <span class="pull-right text-muted small">12 minutes ago</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a href="notifications.html">
                                        <strong>See All Alerts</strong>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="${ctxPath}/operatorLogout">
                            <i class="fa fa-sign-out"></i> 退出
                        </a>
                    </li>
                </ul>

            </nav>
        </div>
        <%--
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>Basic Form</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="index.html">Home</a>
                    </li>
                    <li>
                        <a>Forms</a>
                    </li>
                    <li class="active">
                        <strong>Basic Form</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">
            </div>
        </div>
         --%>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div id="iframeMenuTabs" class="page-tabs-content">
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <%--<div id="context-menu" >--%>
                <%--<ul role="menu" class="dropdown-menu dropdown-menu-right">--%>
                    <%--<li class="J_tabCloseOther"><a>关闭其他</a>--%>
                    <%--</li>--%>
                    <%--<li class="divider"></li>--%>
                    <%--<li class="J_tabCloseAll"><a>关闭全部</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</div>--%>
        </div>

        <div class="tab-content J_mainContent" id="content-main">

        </div>
    </div>
</div>
</body>
<div id="siteMeshJavaScript">

    <%--<script src="${libPath}/bootstrap-contextmenu/bootstrap-contextmenu.js"></script>--%>
    <script src="${libPath}/metisMenu/jquery.metisMenu.js"></script>
    <script src="${libPath}/jQuery-contextMenu/jquery.contextMenu.js"></script>
    <script src="${libPath}/slimscroll/jquery.slimscroll.js"></script>
    <script src="${jsPath}/contabs.js"></script>
    <script src="${libPath}/layer/layer.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $('#menudiv').slimScroll({
                height: 'auto',
                size: '10px',
                color: 'black'
            });
            $('#side-menu').metisMenu();
        });
    </script>
</div>
</html>
