<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>可疑交易查询</title>
</head>
<body>
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="dubicform" id="dubicform" action="${ctxPath}/dubioustxn/queryDubioustxnCondition" method="get" data-table="queryTable" >
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
              <label class="control-label" for="customnum">客户编号：</label>
              <input type="text" class="form-control input-sm" id="customnum" name="customnum"
                     placeholder="客户编号" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="warnstatus">预警状态</label>
              <dict:select dictcode="warnstatus" id="warnstatus" name="warnstatus"  nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
          <%--<div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="customname">客户名称：</label>
              <input type="text" class="form-control input-sm" id="customname" name="customname"
                     placeholder="客户名称" class="form-control">
            </div>
          </div>--%>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="warndates">开始预警日期</label>
              <input type="text" id="warndates" name="warndates" placeholder="开始预警日期" class="form-control input-sm Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'warndatee\',{M:-1,d:1})}',maxDate:'#F{$dp.$D(\'warndatee\')||\'new Date()\'}',onpicked:pickedFunc,oncleared:clearedFunc})">
            </div>
          </div>
          <div class="col-sm-4">
          <div class="form-group">
            <label class="control-label" for="warndatee">截止预警日期</label>
            <input type="text" id="warndatee" name="warndatee" placeholder="截止预警日期" class="form-control input-sm Wdate" onfocus="picker2rule(this)">
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
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选</th>
                <th >预警日期</th>
                <th >交易流水号</th>
                <th >客户编号</th>
                <th >客户名称</th>
                <th >交易金额(元)</th>
                <th>预警状态</th>
                <th>风险等级</th>
                <th>风险规则</th>
                <th>处理结果</th>
              </tr>
              </thead>
            </table>
            <a id="addCase" href="#"  data-toggle="modal" data-target="#myModal">
              <button type="button" id="add" class="btn btn-white  btn-xs">
                <strong>添加案例</strong>
              </button>
            </a>
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
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增案例</h4>
      </div>
      <div class="modal-content">
        加载中...
      </div>

    </div>
  </div>
</div>
<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="choose" value="{{id}}"/>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
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
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);

    $("#addCase").click(function(){
      var addIds = ids();
      var len=$("input[name='choose']:checked").length;
      if(len==0){
        bootbox.alert("请选择数据",function(){
          $('#myModal').modal('hide');
        });
      }else{
        $("#addCase").attr("href","${ctxPath}/api/1/dubioustxn/_toAddCase?ids="+addIds);
      }
    });

    //获取所选择的id
    function ids(){
      var Ids="";
      var str=document.getElementsByName("choose");
      for (var i=0;i<str.length;i++){
        if(str[i].checked == true){
          if(Ids==""){
            Ids+=str[i].value;
          }else {
            Ids+=","+str[i].value;
          }
        }
      }
      return Ids;
    }

    //全选
    function setChecked() {
      var choosetotal = $("input[name='choosetotal']");
      if (choosetotal[0].checked == true) {
        $("input[name='choose']").attr("checked", true);
      } else {
        $("input[name='choose']").attr("checked", false);
      }
    }
    function queryDubioustxnRule(ruleids){
      $.ajaxParamBack({"url":"${ctxPath}/ruleHis/toAjaxQueryRuleHis","data":"queryId="+ruleids+"&queryType=ruleid"});
    }
    //绑定form的查询到tables
    $(function(){

      $("#myModal").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
      });

      $('form#dubicform').queryTables({
        "serverSide": true,
        "pageLength":10,
        columns: [
          {"data": "id"},
          {"data": "warndate"},
          {"data": "txnum"},
          {"data": "customnum"},
          {"data": "customname"},
          {"data": "txnaccount"},
          {"data": "warnstatus"},
          {"data": "risklevel"},
          {"data": "ruleid"},
          {"data": "dealopinion"}
        ],
        columnDefs:[
          {
            targets: 0,
            render: function (data, type, row, meta) {
              return checkBoxTemplate({id:data});
            }
          },
          {
            targets: [4,9],
            render:function(data, type, row, meta){
              if(data==null){
                return "";
              }else{
                return data;
              }
            }
          },
          {
            targets: 5,
            render:function(data, type, row, meta){
              if(data == null){
                return "";
              }else{
                return (Math.round((data/100)*100)/100).toFixed(2);
              }
            }
          },
          {
            targets: 6,
            render: function (data, type, row, meta) {
              var dataMap={"00":{"cssClass":"label-primary",text:"开启"},"01":{"cssClass":"label-danger",text:"关闭"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 7,
            render:function(data, type, row, meta){
              var dataMap={"00":{text:"高"},"01":{text:"中"},"02":{text:"低"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 8,
            render:function(data, type, row, meta){
              var context =
              {
                func: [
                  {"text": "查看",  "fn":"queryDubioustxnRule('"+data+"')"}
                ]
              };
              return data?operTemplate(context):"";
            }
          }
        ]
      });
      $("#export").click(function () {
        location.href = "${ctxPath}/dubioustxn/exportDubioustxnExcel?" + $("form").serialize();
      });
    });


    var md=new Date();
    //第一个输入框选择日期
    function pickedFunc(){

      var Y=$dp.cal.getP('y'); //用内置方法获取到选中的年月日
      var M=$dp.cal.getP('M');
      var D=$dp.cal.getP('d');
      //间隔为30天
      //M=parseInt(M)-1;
      //D=parseInt(D)+30;
      //间隔为1个月
      D=parseInt(D)-1;
      var d = new Date();
      d.setFullYear(Y,M,D);//设置时间
      var nowDate=new Date();
      if(nowDate<=d){ //现在的时间比较
        md=nowDate;
      }else{
        var month=d.getMonth()+1; //月份的范围是（0到11）;
        md=d.getFullYear()+"-"+month+"-"+d.getDate(); //直接把d给过去会有问题，所以拼成字符串发过去
      }
    }
    //开始预警日期清空的时候的操作
    function clearedFunc(){
      md=new Date();
    }
    //结束预警日期规则
    function picker2rule(date){
      WdatePicker({el:date,minDate:'#F{$dp.$D(\'warndates\')}',maxDate:md});
    }
  </script>
</div>
</html>