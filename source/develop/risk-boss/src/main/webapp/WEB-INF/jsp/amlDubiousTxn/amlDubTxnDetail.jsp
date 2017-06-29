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
                                <li class=""><a href="#tab-2" data-toggle="tab" aria-expanded="false">账户信息</a></li>
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
                                        <th>客户类型：</th>
                                        <td><dict:write dictcode="90036" code="${customers.customertype}"></dict:write></td>
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
                            <div class="tab-pane" id="tab-2">
                                <table class="table">
                                    <tr>
                                        <th>账户号：</th>
                                        <td>${customers.account.accountid}</td>
                                    </tr>
                                    <tr>
                                        <th>渠道编号：</th>
                                        <td>${customers.account.bizsysid}</td>
                                    </tr>
                                    <tr>
                                        <th>账户类型：</th>
                                        <td><dict:write dictcode="80003" code="${customers.account.accounttype}"></dict:write></td>
                                    </tr>
                                    <tr>
                                        <th>账户币种：</th>
                                        <td><dict:write dictcode="90038" code="${customers.account.accountccy}"></dict:write></td>
                                    </tr>
                                    <tr>
                                        <th>账户余额：</th>
                                        <td>${customers.account.accountbalance}</td>
                                    </tr>
                                    <tr>
                                        <th>账户状态：</th>
                                        <td><dict:write dictcode="60015" code="${customers.account.status}"></dict:write></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="tab-pane" id="tab-3">
                                <table id="queryTable" class="table" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>交易流水</th>
                                        <th>交易类别</th>
                                        <th>交易金额</th>
                                        <th>交易币种</th>
                                        <th>交易时间</th>
                                        <th>付款方</th>
                                        <th>收款方</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${simpleBills}" var="txn">
                                            <tr>
                                                <td>${txn.txnno}</td>
                                                <td><dict:write dictcode="30008" code="${txn.txntp}"></dict:write> </td>
                                                <td>${txn.txnamt}</td>
                                                <td>${txn.txnccy}</td>
                                                <td><r:formateDateTime value="${txn.txntime}" pattern="yyyy-MM-dd"/></td>
                                                <td>${txn.dbtrname}</td>
                                                <td>${txn.cdtrname}</td>
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
            $("#back").click(function(){
                window.location.href="${ctxPath}/amlDubiousTxn/toQueryAmlDubiousTxn.action?_initQuery=true";
            });
        })
    </script>
</div>
</html>
