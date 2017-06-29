<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>流动资金贷款最大偿还年限</title>
    <style type="text/css">
        th, td ,select {
            text-align: center;
        }

        td{
            border:5px solid #9C9AA0;
        }

        input{
            border:1px;
        }
    </style>
</head>
<body>
<div id="wrapper" class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
        <div class="ibox-content m-b-sm border-bottom" id="divContent">
            <form role="form" id="repayyearform" data-table="queryTable" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="loanFundSales" id="loanFundSales"/>
                <input type="hidden" name="loanFundYears" id="loanFundYears"/>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content">
                                <table id="queryTable" class="table table-striped table-bordered table-hover"
                                       cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th style="background-color:#A4BBDB">客户评级</th>
                                        <th style="background-color:#A4BBDB">小型</th>
                                        <th style="background-color:#A4BBDB">中小型</th>
                                        <th style="background-color:#A4BBDB">中型及以上</th>
                                    </tr>
                                    </thead>
                                    <tbody id="tableList">
                                    <tr>
                                        <td style="background-color:#A2D655">年销售额</td>
                                        <c:forEach items="${loanFundsRepaySales}" var="loanSale">
                                            <td style="background-color:#A2D655"><input style="background-color:#A2D655;text-align:right;width:40px;" type="text" name="annualsaleslower" id="${loanSale.companytype}_annualsaleslower" value="${loanSale.annualsaleslower}">&nbsp&nbsp万~
                                                <input style="background-color:#A2D655;text-align:left;width:40px;" type="text" name="_annualsalesupper" id="${loanSale.companytype}_annualsalesupper" value="${loanSale.annualsalesupper}">万
                                            </td>
                                        </c:forEach>
                                    </tr>
                                    <c:forEach items="${loanFundsRepayYears}"  varStatus="status" step="3">
                                        <c:if test="${status.index%3==0}">
                                            <tr>
                                                <td style="background-color:#CBD99D"><c:if test="${status.index/3==0}">A级</c:if>
                                                    <c:if test="${status.index/3==1}">B级</c:if>
                                                    <c:if test="${status.index/3==2}">C级</c:if>
                                                    <c:if test="${status.index/3==3}">D级</c:if>
                                                </td>
                                                <c:forEach items="${loanFundsRepayYears}" var="loanYear"
                                                           varStatus="vstatus" begin="${status.index}"
                                                           end="${status.index+2}" step="1">
                                                    <td>
                                                        <select id="${loanYear.id}" name="repayyear" class="false" required="true">
                                                            <c:forEach items="${loanFundsLimits}" var="loanlimit">
                                                                <option value="${loanlimit.repayyear}"
                                                                        <c:if test="${loanYear.repayyear==loanlimit.repayyear}">selected</c:if>>${loanlimit.repayyear}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <!--input type="text" class="false" id="${loanYear.id}" name="repayyear" value="${loanYear.repayyear}"!-->
                                                    </td>
                                                </c:forEach>
                                            </tr>
                                        </c:if >
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="row query-div">
                                    <button type="button" id="submitQuery" class="btn btn-w-m btn-success">提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        var csrfToken = "${_csrf.token}";
        var csrfHeaderName = "${_csrf.headerName}";

        $("select[name='repayyear']").change(function(){
            $(this).attr("class", "true");
        });

        function jointFieldsParam() {
            var salesfield = "";
            $("input[id*='annualsales']").each(function () {
                var companytype = $(this).attr("id").substr(0,1);
                if ("input[id ^= companytype + '_annualsales']") {
                    var fieldTem = companytype + "_" + $("#" + companytype + '_annualsaleslower').val() +"_"+ $("#" + companytype + '_annualsalesupper').val() + ",";
                }
                salesfield += fieldTem;
            });
            $("#loanFundSales").val(salesfield);

            var yearfield = "";
            $("select[name='repayyear'][class='true']").each(function () {
                var id =$(this).attr("id");
                var fieldTem =id + "_" + $("#" + id).find("option:selected").text()+ ",";
                yearfield += fieldTem;

            });
            $("#loanFundYears").val(yearfield);
        }


        $(function () {
            $("#submitQuery").click(function () {
                jointFieldsParam();
                $("#repayyearform").ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(csrfHeaderName, csrfToken);
                    },
                    url: '${ctxPath}/api/1/loanrepayyear/batchUpdateRepayYear',
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("修改成功！",function(){
                                window.location.href="${ctxPath}/loanrepayyear/toQueryLoanRepayYear";
                            });
                        } else {
                            bootbox.alert("修改失败！");
                        }
                    }
                });
            });
        });

    </script>

</div>
</html>
