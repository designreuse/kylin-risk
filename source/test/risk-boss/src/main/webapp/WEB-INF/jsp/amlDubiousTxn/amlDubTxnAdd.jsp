<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>反洗钱可疑交易添加</title>
</head>
<body>
<form id="addForm" method="post" action="${ctxPath}/api/1/amlDubTxn/addAmlDubiousTxn?${_csrf.parameterName}=${_csrf.token}" data-parsley-validate>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="txnIds" id="txnIds"/>
    <input type="hidden" name="txncount" id="txncount"/>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content m-b-sm border-bottom">
            <div class="row">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="panel-options">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#tab-1" data-toggle="tab" aria-expanded="true">客户信息</a></li>
                                <li class=""><a href="#tab-3" data-toggle="tab" aria-expanded="false">交易信息</a></li>
                                <li class=""><a href="#tab-4" data-toggle="tab" aria-expanded="false">基本信息</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab-1">
                                <table class="table">
                                    <tr>
                                        <th>客户号：</th>
                                        <td><input type="text" id="customerid" name="customnum" value="${customers.customerid}" required></td>
                                    </tr>
                                    <tr>
                                        <th>客户名称：</th>
                                        <td><input type="text" id="customername" name="customname" value="${customers.customername}"></td>
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
                                        <th>选择交易</th>
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
                                            <td><input type="checkbox" value="${txn.id}" id="choose" name="choose">
                                                <input type="hidden" value="${txn.txnamt}" id="txnamt" name="txnaccount">
                                            </td>
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
                            <div class="tab-pane" id="tab-4">
                                <div class="row">
                                <table class="table">
                                    <tr>
                                        <th>风险级别：</th>
                                        <td>
                                            <dict:select dictcode="risklevel" id="risklevel" name="risklevel"></dict:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>触犯规则：</th>
                                        <td>
                                            <select data-placeholder="Choose a Rule..." id="ruleid" name="ruleid" class="chosen-select" style="width: 350px"  tabindex="2">

                                                <c:forEach items="${riskrules}" var="rule">
                                                    <option value="${rule.id}">${rule.rulename}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>文件：</th>
                                        <td><input  type="file" class="form-control input-sm" id="fileUpload" name="fileUpload" class="form-control"
                                                    accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/msword" required>
                                        </td>
                                    </tr>
                                </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <center>
                    <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="提交">
                    <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
                </center>
            </div>
        </div>
    </div>
</form>
</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        var csrfToken="${_csrf.token}";
        var csrfHeaderName="${_csrf.headerName}";
        var initparsley=$("#addForm").parsley();
        $(function(){
            $("#customerid").change(function(){
                initparsley.destroy();
                $("#addForm").attr("action" ,"${ctxPath}/amlDubiousTxn/toAjaxGetCustomInfo");
                $("#addForm").submit();
            });
        });
        $(function(){
            $("#submitAdd").click(function(){
                var result=$("input[name='choose']:checked").length;
                if (result == 0) {
                    bootbox.alert("请选择交易数据");

                } else {
                    var str=document.getElementsByName("choose");
                    var str1=document.getElementsByName("txnaccount");
                    var txnIds=[];
                    var txnaccount=0;
                    for (var i=0;i<str.length;i++){
                        if(str[i].checked == true){
                            txnIds[i]=str[i].value;
                            txnaccount=parseFloat(txnaccount)+parseFloat(str1[i].value);
                        }
                    }

                    $("#txnIds").val(txnIds);
                    $("#txncount").val(str.length);
                    $("#txnamt").val(txnaccount);
                    $("#addForm").ajaxSubmit({
                        type:'post',
                        dataType: 'json',
                        beforeSubmit:function(){
                            return initparsley.validate();
                        },
                        success: function(data,textStatus,jqXHR){
                            if(data.code==200){
                                bootbox.alert("添加成功！", function () {
                                    window.location.href="${ctxPath}/amlDubiousTxn/toQueryAmlDubiousTxn";
                                });
                            }else{
                                bootbox.alert("添加失败！");
                            }
                        }
                    });
                }
            });
        });
        $(function(){
            $("#back").backup();
        })
    </script>
</div>
</html>
