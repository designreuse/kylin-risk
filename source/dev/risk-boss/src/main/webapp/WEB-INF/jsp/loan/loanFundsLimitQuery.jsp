<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>基于中小企业还款能力的贷款限额测算</title>
    <style type="text/css">
        caption,td,input,th,h4
        {
            text-align:center;
        }
        h5{
            text-align:right;
        }
        input{
            border:0px ;
        }

    </style>
</head>
<body >
<div id="wrapper" class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
        <div class="ibox-content m-b-sm border-bottom" id="divContent">
            <form role="form" id="fundlimitform"  data-table="queryTable"  data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="loanFundSales" id="loanFundSales"/>
                <input type="hidden" name="loanFundEbitdas" id="loanFundEbitdas"/>
                <input type="hidden" name="loanFundLimits" id="loanFundLimits"/>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content" style="width:100%;overflow:auto;">
                                <table id="queryTable" style="width:100%;" class="table table-striped table-bordered table-hover" cellspacing="0">
                                    <caption align="top">
                                        <h3>基于中小企业还款能力的贷款限额测算</h3><br/>
                                        <h3>———流动资金贷款</h3><br/>
                                        <h5>单位： 万元</h5>
                                    </caption>
                                 <thead>
                                     <tr>
                                         <th>企业规模</th>
                                         <th colspan="2">小型</th>
                                         <th colspan="3">中小型</th>
                                         <th colspan="3">中型及以上</th>
                                     </tr>
                                 </thead>
                                    <tbody id="tableList">
                                     <tr>
                                         <td>年销售额</td>
                                         <c:forEach items="${loanFundsLimitSales}" var="loanSale" varStatus="status">
                                             <td class="${status.index+1}"><input type="text" class="false" name="annualsales"  id="${loanSale.companytype}_annualsales" value="${loanSale.annualsales}"></td>
                                         </c:forEach>
                                     </tr>
                                     <tr>
                                         <td>毛利润率</td>
                                         <td colspan="8"><input type="text" name="profilerate" id="profilerate" value="${profilerate}"></td>
                                     </tr>
                                     <tr>
                                         <td>EBITDA</td>
                                         <c:forEach items="${loanFundsLimitSales}" var="loanSale">
                                             <td><input type="text" class="false" name="ebitda" id="${loanSale.companytype}_ebitda" value="${loanSale.ebitda}" readonly="true"></td>
                                         </c:forEach>
                                     </tr>
                                     <tr>
                                         <td>偿还年限</td>
                                         <td colspan="8">可支持的流动资金贷款总额（本公司+其他金融机构）</td>
                                     </tr>
                                     <input type = hidden name=count id=count value="${loanFundsLimitLists.size()}">
                                     <c:forEach items="${loanFundsLimitLists}" var="loanLimit" varStatus="status" step="8">
                                         <c:if test="${status.index%8==0}">
                                             <tr>
                                                 <td><c:if test="${status.index/8==0}">4.0</c:if>
                                                     <c:if test="${status.index/8==1}">3.5</c:if>
                                                     <c:if test="${status.index/8==2}">3.0</c:if>
                                                     <c:if test="${status.index/8==3}">2.5</c:if>
                                                     <c:if test="${status.index/8==4}">2.0</c:if>
                                                     <c:if test="${status.index/8==5}">1.5</c:if>
                                                 </td>
                                                 <c:forEach items="${loanFundsLimitLists}" var="loanLimit" varStatus="vstatus" begin="${status.index}" end="${status.index+7}" step="1">
                                                     <td><input type="text" class="false" id="${loanLimit.id}" name="loanlimit"  value="${loanLimit.loanlimit}" readonly="true"></td>
                                                 </c:forEach>
                                             </tr>
                                         </c:if>
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

        $(document).ready(function () {
            $.getJSON("${ctxPath}/api/1/loanfundslimit/queryRepayyear", function (json) {
                $.each(json.data, function (index, factors) {
                    var i="";
                    var repayYear =factors['repayyear'];
                    if(repayYear==4){i=5;}
                    else if(repayYear==3.5){i=6;}
                    else if(repayYear==3){i=7;}
                    else if(repayYear==2.5){i=8;}
                    else if(repayYear==2){i=9;}
                    else if(repayYear==1.5){i=10;}
                    if (factors['customertype'] == "A级") {
                            if (factors['companytype'] == "1") {
                                $("table").find("tr").eq(i).find("td").eq(1).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(2).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(1).find("input").css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(2).find("input").css("background-color", "#FEDC7D");
                            } else if (factors['companytype'] == "2") {
                                $("table").find("tr").eq(i).find("td").eq(3).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(4).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(5).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(3).find("input").css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(4).find("input").css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(5).find("input").css("background-color", "#FEDC7D");
                            } else if (factors['companytype'] == "3") {
                                $("table").find("tr").eq(i).find("td").eq(6).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(7).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(8).css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(6).find("input").css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(7).find("input").css("background-color", "#FEDC7D");
                                $("table").find("tr").eq(i).find("td").eq(8).find("input").css("background-color", "#FEDC7D");

                            }
                        }else  if (factors['customertype'] == "B级") {
                            if (factors['companytype'] == "1") {
                                $("table").find("tr").eq(i).find("td").eq(1).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(2).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(1).find("input").css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(2).find("input").css("background-color", "#B4D692");
                            } else if (factors['companytype'] == "2") {
                                $("table").find("tr").eq(i).find("td").eq(3).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(4).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(5).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(3).find("input").css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(4).find("input").css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(5).find("input").css("background-color", "#B4D692");
                            } else if (factors['companytype'] == "3") {
                                $("table").find("tr").eq(i).find("td").eq(6).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(7).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(8).css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(6).find("input").css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(7).find("input").css("background-color", "#B4D692");
                                $("table").find("tr").eq(i).find("td").eq(8).find("input").css("background-color", "#B4D692");

                            }
                        } else  if (factors['customertype'] == "C级") {
                            if (factors['companytype'] == "1") {
                                $("table").find("tr").eq(i).find("td").eq(1).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(2).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(1).find("input").css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(2).find("input").css("background-color", "#F3BA8D");
                            } else if (factors['companytype'] == "2") {
                                $("table").find("tr").eq(i).find("td").eq(3).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(4).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(5).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(3).find("input").css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(4).find("input").css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(5).find("input").css("background-color", "#F3BA8D");
                            } else if (factors['companytype'] == "3") {
                                $("table").find("tr").eq(i).find("td").eq(6).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(7).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(8).css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(6).find("input").css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(7).find("input").css("background-color", "#F3BA8D");
                                $("table").find("tr").eq(i).find("td").eq(8).find("input").css("background-color", "#F3BA8D");

                            }
                        }else  if (factors['customertype'] == "D级") {
                            if (factors['companytype'] == "1") {
                                $("table").find("tr").eq(i).find("td").eq(1).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(2).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(1).find("input").css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(2).find("input").css("background-color", "#BCCEEC");
                            } else if (factors['companytype'] == "2") {
                                $("table").find("tr").eq(i).find("td").eq(3).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(4).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(5).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(3).find("input").css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(4).find("input").css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(5).find("input").css("background-color", "#BCCEEC");
                            } else if (factors['companytype'] == "3") {
                                $("table").find("tr").eq(i).find("td").eq(6).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(7).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(8).css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(6).find("input").css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(7).find("input").css("background-color", "#BCCEEC");
                                $("table").find("tr").eq(i).find("td").eq(8).find("input").css("background-color", "#BCCEEC");
                            }
                        }
                });
            });
        });

        function jointFieldsParam() {
            var salesfield = "";
            $("input[name='annualsales'][class='true']").each(function () {
                var id =  $(this).attr("id");
                var companytype = id.substr(0,id.indexOf("_"));
                var fieldTem = companytype + "_" + $("#" + id).val() + ",";
                salesfield += fieldTem;
            });
            $("#loanFundSales").val(salesfield);

            var ebitdafield = "";
            $("input[name='ebitda'][class='true']").each(function () {
                var id =  $(this).attr("id");
                var companytype = id.substr(0,id.indexOf("_"));
                var fieldTem = companytype + "_" + $("#" + id).val()  + ",";
                ebitdafield += fieldTem;

            });

            $("#loanFundEbitdas").val(ebitdafield);

            var limitfield = "";
            $("input[name='loanlimit'][class='true']").each(function () {
                var id = $(this).attr("id");
                var fieldTem = id + "_" + $("#" + id).val() + ",";
                limitfield += fieldTem;
            });
            $("#loanFundLimits").val(limitfield);
        }

        $("input[name='annualsales']").change(function(){
            $(this).attr("class", "true");
            var col = $(this).parent("td").attr("class");
            var annualsale = $("table").find("tr").eq(1).find("td").eq(col).find("input").val();
            var profile =$("#profilerate").val().replace("%","")/100;
            var changeebitda = annualsale * profile;
             $("table").find("tr").eq(3).find("td").eq(col).find("input").val(changeebitda);
             $("table").find("tr").eq(3).find("td").eq(col).find("input").attr("class", "true");
            var count =$("#count").val();
            var ebitda =$("table").find("tr").eq(3).find("td").eq(col).find("input").val();
            for(var i =5; i<=count/8+4;i++){
                var year =$("table").find("tr").eq(i).find("td").eq(0).text();
                var loanlimit = year * ebitda;
                $("table").find("tr").eq(i).find("td").eq(col).find("input").val(loanlimit);
                $("table").find("tr").eq(i).find("td").eq(col).find("input").attr("class", "true");
            }
        });


        $("input[name='profilerate']").change(function(){
            for(var i=1; i<=8;i++) {
                var annualsale = $("table").find("tr").eq(1).find("td").eq(i).find("input").val();
                $("table").find("tr").eq(1).find("td").eq(i).find("input").attr("class", "true");
                var profile =$("#profilerate").val().replace("%","")/100;
                var changeebitda = annualsale * profile;
                $("table").find("tr").eq(3).find("td").eq(i).find("input").val(changeebitda);
                $("table").find("tr").eq(3).find("td").eq(i).find("input").attr("class", "true");
                var count =$("#count").val();
                var ebitda =$("table").find("tr").eq(3).find("td").eq(i).find("input").val();
                for(var j =5; j<=count/8+4;j++){
                    var year =$("table").find("tr").eq(j).find("td").eq(0).text();
                    var loanlimit = year * ebitda;
                    $("table").find("tr").eq(j).find("td").eq(i).find("input").val(loanlimit);
                    $("table").find("tr").eq(j).find("td").eq(i).find("input").attr("class", "true");
                }
            }
        });

            $(function () {
            $("#submitQuery").click(function () {
                jointFieldsParam();
                $("#fundlimitform").ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(csrfHeaderName, csrfToken);
                    },
                    url: '${ctxPath}/api/1/loanfundslimit/batchUpdateFundsLimit',
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("修改成功！",function(){
                                window.location.href="${ctxPath}/loanfundslimit/toQueryLoanFundsLimit";
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
