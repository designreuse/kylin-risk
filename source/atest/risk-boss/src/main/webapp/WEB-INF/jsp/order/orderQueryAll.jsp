<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>交易查询</title>
</head>
<body>
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="orderform" id="orderform" action="${ctxPath}/order/queryOrderCondition" method="get" data-table="queryTable">
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
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="orderid">交易流水号：</label>
              <input type="text" class="form-control input-sm" id="orderid" name="orderid"
                     placeholder="交易流水号" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="customnum">客户编号：</label>
              <input type="text" class="form-control input-sm" id="customnum" name="customnum"
                     placeholder="客户编号" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="risklevel">风险等级</label>
              <dict:select dictcode="risklevel" id="risklevel" name="risklevel"  nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="ordertimes">开始交易日期</label>
              <input type="text" id="ordertimes" name="ordertimes" placeholder="开始交易日期" class="form-control input-sm Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'ordertimee\')}'})">
            </div>
          </div>
          <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label" for="ordertimee">截止交易日期</label>
            <input type="text" id="ordertimee" name="ordertimee" placeholder="截止交易日期" class="form-control input-sm Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'ordertimes\')}'})">
          </div>
        </div>
        </div>
        <div class="row query-div">
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
          <button id="export" type="button" class="btn btn-w-m btn-info"><strong>&nbsp;&nbsp;导出</strong></button>
        </div>


      </form>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th >交易日期</th>
                <th >交易流水号</th>
                <th >业务类型</th>
                <th >客户编号</th>
                <th >客户名称</th>
                <th >交易金额(元)</th>
                <th >交易评分</th>
                <th >商品名称</th>
                <th >产品</th>
                <th >商品数量</th>
                <th >商品单价</th>
                <th>风险等级</th>
              </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content1">
      <div class="modal-content">
        加载中...
      </div>

    </div>
  </div>
</div>
<script id="status-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>
</body>

<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);
    //绑定form的查询到tables
    $(function(){

      $("#myModal").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
      });

      $('form#orderform').queryTables({
        "serverSide": true,
        "pageLength":10,
        columns: [
          {"data": "ordertime"},
          {"data": "orderid"},
          {"data": "ordertypeid"},
          {"data": "userid"},
          {"data": "customername"},
          {"data": "amount"},
          {"data": "score"},
          {"data": "goodsname"},
          {"data": "rootinstcd"},
          {"data": "goodscnt"},
          {"data": "unitprice"},
          {"data": "risklevel"},
        ],
        columnDefs:[
          {
            targets: [1,3,4,6,7,9,10],
            render:function(data, type, row, meta){
              if(data==null){
                return "";
              }else{
                return data;
              }
            }
          },
          {
            targets: 0,
            render:function(data, type, row, meta){
              return moment(data).format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets: 2,
            render:function(data, type, row, meta){
              var dataMap={"B1":{"cssClass":"label-primary",text:"充值"},"B11":{"cssClass":"label-primary",text:"充值"},
                "B12":{"cssClass":"label-primary",text:"充值"},
                "B13":{"cssClass":"label-primary",text:"充值"}, "B14":{"cssClass":"label-primary",text:"充值"},
                "B15":{"cssClass":"label-primary",text:"充值"},"B2":{text:"提现"},
                "M20005":{text:"放款"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 5,
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
            targets: 8,
            render:function(data, type, row, meta){
              var dataMap={"M000001":{text:"丰年"},"M000003":{text:"会堂"},"M0000031":{text:"会唐用户场地方子账户"},
                           "M000004":{text:"课栈"},"M000005":{"cssClass":"label-primary",text:"君融贷"},"M000006":{text:"食全食美"},
                           "M000007":{text:"棉庄"},"M000008":{text:"云农场"},"M000009":{text:"指尖微客"},
                           "M000010":{text:"蚂蚁HR"},"M000011":{text:"展酷"},"M000012":{text:"指尖代言"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 11,
            render:function(data, type, row, meta){
              var dataMap={"00":{text:"高"},"01":{text:"中"},"02":{text:"低"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },

        ]
      });

      $("#export").click(function () {
        location.href = "${ctxPath}/order/exportOrderExcel?" + $("form").serialize();
      });
    });
  </script>
</div>
</html>