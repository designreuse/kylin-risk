<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>风险事件管理</title>
</head>
<body>
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="eventFormAdd" id="eventFormAdd" method="get" data-table="queryTable" >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="txnid">内部订单号：</label>
              <input type="text" class="form-control input-sm" id="txnid" name="txnid"
                     placeholder="内部订单号" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="userid">客户号：</label>
              <input type="text" class="form-control input-sm" id="userid" name="userid"
                     placeholder="客户号" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="customername">客户名称：</label>
              <input type="text" class="form-control input-sm" id="customername" name="customername"
                     placeholder="客户名称" class="form-control">
            </div>
          </div>
        </div>
        <div class="row query-div">
          <button type="button" id="subQuery" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
        </div>
      </form>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox float-e-margins">
          <div class="ibox-content">
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选</th>
                <th>客户号</th>
                <th>客户名称</th>
                <th>内部订单号</th>
                <th>交易金额</th>
                <th>交易日期</th>
                <th>交易时间</th>
                <th>商品名称</th>
                <th>商品描述</th>
                <th>商品数量</th>
                <th>商品单价</th>
              </tr>
              </thead>
            </table>
            <a href="#" id="toAdd">
              <button id="add" class="btn btn-white"><i class="fa fa-plus-circle"></i>&nbsp;&nbsp;添加</button>
            </a>
        </div>
      </div>
    </div>
  </div>
  </div>
</div>
<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="choose" value="{{id}}" cusnum="{{cusnum}}" cusid="{{cusid}}"/>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    <button class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
    {{/each}}
  </div>
</script>
<script id="status-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var checkboxSource   = $("#checkbox-tpl").html();
    var checkBoxTemplate = Handlebars.compile(checkboxSource);
    var operTpl = $("#oper-tpl").html();
    //预编译模板
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    $(function () {
      $("#add").click(function () {
        var addIds = "";
        var cusid="";
        var cusnum="";
        var result = $("input[name='choose']:checked").length;
        if (!result) {
          bootbox.alert("请选择数据");
          return;
        }
        var str = document.getElementsByName("choose");
        for (var i = 0; i < str.length; i++) {
          if (str[i].checked == true) {
            if (!addIds) {
              addIds += str[i].value;
              cusid = str[i].getAttribute('cusid');//获取选中的第一行的客户信息
              cusnum = str[i].getAttribute('cusnum');
            } else {
              addIds += "," + str[i].value;
            }
          }
        }
        $.ajaxParamBack({"url":"${ctxPath}/riskEvent/toAjaxAddRiskEvent","data":"billIds=" + addIds+"&cusid="+cusid+"&cusnum="+cusnum});
      });
    });


    //全选
    function setChecked() {
      var choosetotal = $("input[name='choosetotal']");
      $("input[name='choose']").attr("checked", choosetotal[0].checked);
    }

    $(function (){
      $("#subQuery").click(function () {
        if(!$("#txnid").val()&&!$("#userid").val()&&!$("#customername").val()){
          bootbox.alert("请输入条件！");
          return;
        }
        $("#eventFormAdd").attr("action" ,"${ctxPath}/riskEvent/querySimpleBillByCon");
        $('#eventFormAdd').submit();
      })
    });

    //绑定form的查询到tables
    $(function(){
      $('form#eventFormAdd').queryTables({
        "serverSide": true,
        "pageLength":10,
        "bAutoWidth": false,
        columns: [
          {"data": "id"},
          {"data": "userid"},
          {"data": "customername"},
          {"data": "orderid"},
          {"data": "amount"},
          {"data": "orderdate"},
          {"data": "ordertime"},
          {"data": "goodsname"},
          {"data": "goodsdetail"},
          {"data": "goodscnt"},
          {"data": "unitprice"}
        ],
        columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({id:data,cusid:row.custid,cusnum:row.userid});
            }
          },
          {
            targets: [2,5,7,8,9,10],
            render: function (data, type, row, meta) {
              if (!data) {
                return "";
              } else {
                return data;
              }
            }
          },
          {
            targets:6,
            render:function(data, type, row, meta){
              return moment(data).format('YYYY-MM-DD HH:mm:ss');
              //return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
            }
          }

        ]
      });
    });
  </script>
</div>
</html>
