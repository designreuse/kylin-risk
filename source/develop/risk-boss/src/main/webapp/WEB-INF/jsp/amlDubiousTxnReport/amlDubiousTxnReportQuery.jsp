<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>反洗钱交易查询</title>
</head>
<body>
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox float-e-margins">
          <div class="ibox-content m-b-sm border-bottom">
            <form  role="form" id="billform" action="${ctxPath}/amlDubiousTxn/queryAmlDubTxn" method="get" data-table="queryTable">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="row">
                <div class="col-sm-2" style="margin-left: 80px">
                  <div class="form-group">
                    <label class="control-label" for="customnum">客户编号</label>
                    <input type="text" class="form-control input-sm" id="customnum" name="customnum" placeholder="客户编号" class="form-control">
                  </div>
                </div>
                <div class="col-sm-2">
                  <div class="form-group">
                    <label class="control-label" for="customname">客户名称</label>
                    <input type="text" class="form-control input-sm" id="customname" name="customname"  placeholder="客户名称" class="form-control">
                  </div>
                </div>
                <div class="col-sm-2">
                  <div class="form-group">
                    <label class="control-label" for="risklevel">风险等级</label>
                    <dict:select dictcode="risklevel" id="risklevel" name="risklevel" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
                  </div>
                </div>
                <div class="col-md-2">
                  <label>预警日期开始</label>
                  <input type="text" id="warndatebeg" name="warndatebeg" onFocus="WdatePicker()" class="form-control Wdate">
                </div>
                <div class="col-md-2">
                  <label>预警日期结束</label>
                  <input type="text" id="warndateend" name="warndateend"  onFocus="WdatePicker()" class="form-control Wdate">
                </div>
              </div>


              <div align="center">
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
                      <th>预警编号</th>
                      <th>交易笔数</th>
                      <th>交易金额</th>
                      <th>风险等级</th>
                      <th>预警日期</th>
                      <th>规则编号</th>
                      <th>处理意见</th>
                      <th>来源</th>
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
        columns: [
          {"data": "customnum"},
          {"data": "customname"},
          {"data": "warnnum"},
          {"data": "txncount"},
          {"data": "txnaccount"},
          {"data": "risklevel"},
          {"data": "warndate"},
          {"data": "ruleid"},
          {"data": "dealopinion"},
          {"data": "source"}
        ],
        columnDefs:[
          {
            targets:5,
            render:function(data, type, row, meta){
              var dataMap={'00':'高','01':'中','02':'低'};
              return dataMap[data]||data;
            }
          }
        ]
      });

      $("#export").click(function () {
        location.href = "${ctxPath}/amldubioustxnreport/exportAmlTxn?" + $("form").serialize()
      });
    });


  </script>
</div>
</html>
