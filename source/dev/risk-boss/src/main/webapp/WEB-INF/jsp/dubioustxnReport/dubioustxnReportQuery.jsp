<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
    <title>可疑交易查询</title>
</head>
<body>
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-content m-b-sm border-bottom">
              <form  role="form" id="billform" action="${ctxPath}/dubioustxnReport/queryDubioustxnReport" method="get" data-table="queryTable">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <c:choose>
                  <c:when test="${not empty sessionScope.auth.products}">
                    <c:forEach items="${sessionScope.auth.products}" var="products">
                      <c:choose>
                        <c:when test="${products == '-1'}">
                          <input type="hidden" name="productall" value=""/>
                        </c:when>
                        <c:otherwise>
                          <input type="hidden" name="products" value="${products}"/>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </c:when>
                  <c:otherwise>
                    <input type="hidden" name="productnull" value="0"/>
                  </c:otherwise>
                </c:choose>
                <div class="row">
                  <div class="col-sm-3" >
                    <div class="form-group">
                      <label class="control-label" for="userid">客户编号</label>
                      <input type="text" class="form-control input-sm" id="userid" name="userid" placeholder="客户编号" class="form-control">
                    </div>
                  </div>
                  <div class="col-sm-3">
                    <div class="form-group">
                      <label class="control-label" for="customername">客户名称</label>
                      <input type="text" class="form-control input-sm" id="customername" name="customername"  placeholder="客户名称" class="form-control">
                    </div>
                  </div>
                  <div class="col-sm-3">
                    <div class="form-group">
                      <label class="control-label" for="orderid">内部订单号</label>
                      <input type="text" class="form-control input-sm" id="orderid" name="orderid"  placeholder="内部订单号" class="form-control">
                    </div>
                  </div>
                  <div class="col-sm-3">
                    <div class="form-group">
                      <label class="control-label" for="risklevel">预警级别</label>
                      <dict:select dictcode="risklevel" id="risklevel" name="risklevel" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-3">
                    <label>交易日期开始</label>
                    <input type="text" id="timebegin" name="timebegin" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,maxDate:'#F{$dp.$D(\'timeend\')||\'new Date()\'}'})" class="form-control Wdate">
                  </div>
                  <div class="col-md-3">
                    <label>交易日期截止</label>
                    <input type="text" id="timeend" name="timeend"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,minDate:'#F{$dp.$D(\'timebegin\')}',maxDate:'#F{\'new Date()\'}'})" class="form-control Wdate">
                  </div>
                </div>
                <div class="row query-div">
                    <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
                    <button id="export" type="button" class="btn btn-w-m btn-info"><strong>&nbsp;&nbsp;导出</strong></button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-content">
              <div class="row">
                <div class="col-lg-12">
                    <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                      <thead>
                        <tr>
                          <th>客户编号</th>
                          <th>客户名称</th>
                          <th>内部订单号</th>
                          <th>身份证号</th>
                          <th>银行卡号</th>
                          <th>交易时间</th>
                          <th>金额(元)</th>
                          <th>预警级别</th>
                          <th>产品类型</th>
                          <th>渠道类型</th>
                          <th>通道类型</th>
                        </tr>
                      </thead>
                    </table>

                  </div>
                </div>
              </div>
          </div>
        </div>
      </div>
  </div>
  </div>

</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    //绑定form的查询到tables
    $(function(){
      $('form#billform').queryTables({
        "pageLength":10,
        "serverSide": true,
        columns: [
          {"data": "userid"},
          {"data": "customername"},
          {"data": "orderid"},
          {"data": "identitycard"},
          {"data": "cardnum"},
          {"data": "ordertime"},
          {"data": "amount"},
          {"data": "risklevel"},
          {"data": "productid"},
          {"data": "rootinstcd"},
          {"data": "ordertypeid"}
        ],
        columnDefs:[
          {
            targets:[0,1,2,3,4,9,10],
            render:function(data, type, row, meta){
              if(data==null){
                return "";
              }else{
                return data;
              }
            }
          },
          {
            targets:5,
            render:function(data, type, row, meta){
              if(data!=null){
                return moment(data).format('YYYY-MM-DD HH:mm:ss');
              }else{
                return "";
              }
            }
          },
          {
            targets: 6,
            render:function(data, type, row, meta){
              if(data == null){
                return "";
              }else{
                var yuan = (Math.round((data/100)*100)/100).toFixed(2);
                return parseFloat(yuan);
              }
            }
          },
          {
            targets:7,
            render:function(data, type, row, meta){
              if(data==null){
                return "";
              }else {
                var dataMap = {'00': '高', '01': '中', '02': '低'};
                return dataMap[data] || data;
              }
            }
          },
          {
            targets:8,
            render:function(data, type, row, meta){
              var dataMap={'P000011':'君融贷'};
              return dataMap[data]||data;
            }
          }
        ]
      });

      $("#export").click(function () {
        location.href = "${ctxPath}/dubioustxnreport/exportDubioustxnExcel?" + $("form").serialize()
      });
    });


  </script>
</div>
</html>
