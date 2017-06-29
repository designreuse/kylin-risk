<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>可疑交易详细信息</title>
</head>
<body>
<form id="form" role="form" data-table="queryTable" >
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content m-b-sm border-bottom">
            <div class="row">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="panel-options">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#tab-1" data-toggle="tab" aria-expanded="true">客户信息</a></li>
                                <li class=""><a href="#tab-3" data-toggle="tab" aria-expanded="false">交易信息</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab-1">
                                <table class="table">
                                    <tr>
                                        <th>客户号：</th>
                                        <td>${customers.customerid}</td>
                                    </tr>
                                    <tr>
                                        <th>客户名称：</th>
                                        <td>${customers.customername}</td>
                                    </tr>
                                    <tr>
                                        <th>证件类型：</th>
                                        <td><dict:write dictcode="80004" code="${customers.certificatetype}"></dict:write></td>
                                    </tr>
                                    <tr>
                                        <th>证件号码：</th>
                                        <td>${customers.certificateid}</td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tab-pane" id="tab-3">
                                <table id="queryTable" class="table" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>交易流水</th>
                                        <th>交易类型</th>
                                        <th>交易金额</th>
                                        <th>交易币种</th>
                                        <th>交易时间</th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${simpleBills}" var="txn">
                                            <tr>
                                                <td>${txn.orderid}</td>
                                                <td>${txn.ordertypeid}</td>
                                                <td>${txn.amount.divide(100.0)}</td>
                                                <td>人民币（元）</td>
                                                <td><r:formateDateTime value="${txn.ordertime}" pattern="yyyy-MM-dd hh24:mm:ss"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <center>
                    <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
                </center>
            </div>
        </div>
    </div>
</form>
</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        $(function(){
            $("#back").backup();
        })
    </script>
</div>
</html>
