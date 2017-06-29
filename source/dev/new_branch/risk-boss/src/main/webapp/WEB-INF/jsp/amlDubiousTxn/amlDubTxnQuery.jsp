<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>反洗钱可疑交易预警查询</title>
</head>
<body>
<div class="divContainer">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="form" action="${ctxPath}/amlDubiousTxn/queryAmlDubTxn" data-table="queryTable">
      <div class="row">
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label" for="customnum">客户号</label>
            <input type="text" id="customnum" name="customnum" value="${amlDubiousTxn.customnum}" placeholder="客户号" class="form-control">
          </div>
        </div>
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label" for="customertype">客户类型</label>
            <dict:select dictcode="90036" id="customertype" name="customertype" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label" for="customname">客户名称</label>
            <input type="text" id="customname" name="customname" value="${amlDubiousTxn.customname}" placeholder="客户名称" class="form-control">
          </div>
        </div>
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label" for="risklevel">风险等级</label>
            <input type="text" id="risklevel" name="risklevel" value="${amlDubiousTxn.risklevel}" placeholder="风险等级" class="form-control">
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label" for="ruleid">预警规则</label>
            <select data-placeholder="Choose a Rule..." id="ruleid" name="ruleid" class="chosen-select form-control" tabindex="2">
              <option value="">请选择</option>
              <c:forEach items="${riskrules}" var="rule">
                <option value="${rule.id}">${rule.rulename}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label" for="source">来源</label>
            <dict:select dictcode="amlsource" id="source" name="source" nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
          </div>
        </div>
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label">预警日期(开始)</label>
            <input type="text" id="warndatebeg" name="warndatebeg" class="form-control Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'warndateend\',{M:-1,d:1})}',maxDate:'#F{$dp.$D(\'warndateend\')||\'new Date()\'}',onpicked:pickedFunc,oncleared:clearedFunc})">
          </div>
        </div>
        <div class="col-sm-3">
          <div class="form-group">
            <label class="control-label">预警日期(截止)</label>
            <input type="text" class="form-control Wdate" name="warndateend" id="warndateend" onfocus="picker2rule(this)" >
          </div>
        </div>
      </div>
      <div class="row query-div">
        <a href="toAjaxAddAmlDubTxn" class="ajaxback-requst">
          <button type="button" class="btn btn-w-m btn-primary">添加</button>&nbsp;&nbsp;&nbsp;&nbsp;
        </a>
        <input type="submit" class="btn btn-w-m btn-success" value="查询">&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="reset" class="btn btn-w-m btn-info" value="重置">
      </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0"data-page-size="15">
              <thead>
              <tr>
                <th>预警编号</th>
                <th>客户号</th>
                <th>客户名称</th>
                <th>交易笔数</th>
                <th>交易金额(元)</th>
                <th>风险级别</th>
                <th>来源</th>
                <th>预警日期</th>
                <th>可疑预警规则</th>
                <th>备注</th>
                <th>操作</th>
              </tr>
              </thead>
            </table>
          </div>
        </div>
      </div>
    </div>
      </form>
    </div>
  </div>
</div>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    <button type="button" class="btn-white btn btn-xs" onclick="{{this.func.fn}}"><i class="fa {{this.func.cssClass}}"></i>&nbsp;&nbsp;{{this.func.text}}</button>
  </div>
  {{#if link}}
  <a href="{{this.link}}">下载文件</a>
  {{/if}}
</script>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var operTpl = $("#oper-tpl").html();
    //预编译模板
    var operTemplate = Handlebars.compile(operTpl);
    //绑定form的查询到tables
    $(function(){
      $.ajaxBack();
      $('form#form').queryTables({
        "pageLength":10,
        columns: [
          {"data": "warnnum"},
          {"data": "customnum"},
          {"data": "customname"},
          {"data": "txncount"},
          {"data": "txnaccount"},
          {"data": "risklevel"},
          {"data": "source"},
          {"data": "warndate"},
          {"data": "ruleid"},
          {"data": "dealopinion"},
          {"data": "filepath"}
        ],
        columnDefs:[
          {
            targets: 0,
            render: function (data, type, row, meta) {
              return "<a onclick='toAjaxQueryDetailInfo(\""+row.customnum+"\",\""+row.id+"\")' href='javascript:void(0);' ><button type='button' class='btn btn-xs btn-white'>"+data+"</button></a>";
            }
          },
          {
            targets: 4,
            render:function(data, type, row, meta){
              if(data == null){
                return "";
              }else{
                return (Math.round((data/100)*100)/100).toFixed(2);
              }
            }
          },
          {
            targets:5,
            render:function(data, type, row, meta){
              var dataMap={'00':'高','01':'中','02':'低'};
              return dataMap[data]||data;
            }
          },
          {
            targets:6,
            render:function(data, type, row, meta){
              var dataMap={'0':'手工录入','1':'系统生成'};
              return dataMap[data]||data;
            }
          },
          {
            targets: 8,
            render: function (data, type, row, meta) {

              return "<a onclick='toAjaxQueryRuleInfo(\""+data+"\")' href='javascript:void(0);' ><button type='button' class='btn btn-xs btn-white'>"+data+"</button></a>";
            }
          },
          {
            targets: 10,
            render: function (data, type, row, meta) {
              var context =
              {
                func: {"text": "添加备注", "fn": "setOption("+row.id+")", "cssClass": "fa-pencil"}
              };
              if(row.source=='0'&&data!=null){
                //context.func.push({"text": "下载文件", "fn": "uploadFile("+row.filepath+")", "cssClass": "fa-download"});
                context.link=data;
              }
              var html = operTemplate(context);
              return html;
            }
          }
        ]
      });
    });


    function toAjaxQueryDetailInfo(customerid,dubiousttxnid){
      $.ajaxParamBack({"url":"${ctxPath}/amlDubiousTxn/toAjaxQueryDetailInfo","data":"customerid="+customerid+"&dubioustxnid="+dubiousttxnid});
    }
    function toAjaxQueryRuleInfo(id){
      $.ajaxParamBack({"url":"${ctxPath}/amlDubiousTxn/toAjaxQueryRuleInfo","data":"id="+id});
    }
    function setOption(id) {
      bootbox.prompt("请输入备注", function (res) {
        if(res===null){
          bootbox.alert("请输入备注！");

        }else{
          $.ajax({
            type: "POST",
            beforeSend: function (xhr) {
              xhr.setRequestHeader(csrfHeaderName, csrfToken);
            },
            url: "${ctxPath}/api/1/amlDubTxn/updateAmlDubTxn",
            data: "id=" + id+"&dealopinion="+res,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
              if (data.code==200) {
                bootbox.alert("添加成功！",function() {
                  window.location.href="${ctxPath}/amlDubiousTxn/toQueryAmlDubiousTxn.action?_initQuery=true"
                });
              } else {
                bootbox.alert("添加失败！");
              }
            }
          });
        }
      });
    }

    //日期输入条件限制
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
    //第一个清空的时候的操作
    function clearedFunc(){
      md=new Date();
    }
    //给第二个输入框定义规则
    function picker2rule(date){
      WdatePicker({el:date,minDate:'#F{$dp.$D(\'warndatebeg\')}',maxDate:md});
    }
  </script>
</div>
</html>
