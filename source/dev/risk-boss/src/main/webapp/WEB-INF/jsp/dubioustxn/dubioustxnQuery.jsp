<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>可疑交易处理</title>
  <%--<meta http-equiv="refresh" content="5">--%>
</head>
<body>
  <di class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <form role="form" id="form">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox">
            <div class="ibox-content">
              <div class="table-responsive">
              <table id="tabId" class="table table-striped table-bordered table-hover" cellspacing="0">
                <thead>
                <tr>
                  <th>风险交易流水号</th>
                  <th>客户编号</th>
                  <th>客户名称</th>
                  <th>交易金额(元)</th>
                  <th>预警日期</th>
                  <th>风险等级</th>
                  <th>风险规则</th>
                  <th>操作</th>
                  <th>id</th>
                </tr>
                </thead>
              </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
  </di>
<script id="backlist-tpl" type="text/x-handlebars-template">
  <div class="btn-toolbar">
    {{#each func}}
    <%--<input type="button" class="btn {{this.cssClass}} dropdown-toggle" onclick="passOrRefuse({{this.id}},'{{this.button}}')" value="{{this.button}}"/>--%>
    <button type="button" class="btn {{this.cssClass}} dropdown-toggle btn-xs" onclick="{{this.fn}}">{{this.button}}</button>
    {{/each}}
    <div class="btn-group">
    <button type="button" data-toggle="dropdown" class="btn btn-warning dropdown-toggle btn-xs " aria-expanded="false">加入黑名单<span class="caret"></span></button>
    <ul class="dropdown-menu">
      {{#each func1}}
      <li>
        <a onclick="{{this.onclik}}"  class="font-bold" value="{{this.text}}">{{this.text}}</a>
      </li>
      {{/each}}
    </ul>
    </div>
  </div>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
  <div class="btn-group">
    {{#each func}}
    <button class="btn-white btn btn-xs" type="button" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
    {{/each}}
  </div>
</script>
<script id="status-tpl" type="text/x-handlebars-template">
  <span class="label {{cssClass}}">{{text}}</span>
</script>
</body>

<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var srfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var operTpl = $("#oper-tpl").html();
    var operTemplate = Handlebars.compile(operTpl);
    var blacklistSource   = $("#backlist-tpl").html();
    var blacklistTemplate = Handlebars.compile(blacklistSource);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);
    $(document).ready(function() {
      queryDataTable();
      var int=setInterval(queryDataTable,50000);
    } );

    function queryDataTable(){
      $('table#tabId').DataTable( {
        "ajax":"${ctxPath}/api/1/dubioustxn/queryDubioustxn",
        "paging": false,
        "deferRender": true,
        "info":false,
        "columns": [
          {"data": "txnum"},
          {"data": "customnum"},
          {"data": "customname"},
          {"data": "txnaccount"},
          {"data": "warndate"},
          {"data": "risklevel"},
          {"data": "ruleid"},
          {"data": null},
          {"data":"id"}
        ],columnDefs:[
          {
            targets: [0,1,2],
            render: function (data, type, row, meta) {
              if(data==null){
                return "";
              }else{
                return data;
              }
            }
          },
          {
            targets: 3,
            render:function(data, type, row, meta){
              if(data == null){
                return "";
              }else{
                return (Math.round((data/100)*100)/100).toFixed(2);
              }
            }
          },
          {
            targets: 5,
            render:function(data, type, row, meta){
              var dataMap={"00":{text:"高"},"01":{text:"中"},"02":{text:"低"}};
              var map=dataMap[data];
              return map?statusTemplate(map):'';
            }
          },
          {
            targets: 6,
            render:function(data, type, row, meta){
              var context =
              {
                func: [
                  {"text": "查看",  "fn":"queryDubioustxnRule('"+data+"')"}
                ]
              };
              return data?operTemplate(context):"";
            }
          },
          {
            targets: 7,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"button": "关闭报警",  "fn": "closeWarn("+row.id+")", "cssClass": "btn-primary"}
                ],
                func1:[
                  {"text":"加入客户黑名单",id:row.id,"onclik":"customerblackList("+row.id+")"},
                  {"text":"加入身份证黑名单",id:row.id,"onclik":"idCardblackList("+row.id+")"}
                ]
              };
              return blacklistTemplate(context);
            }
          },
          {
            targets:8,
            "visible": false
          }
        ]
      } );
    }

    function queryDubioustxnRule(ruleids){
      $.ajaxParamBack({"url":"${ctxPath}/ruleHis/toAjaxQueryRuleHis","data":"queryId="+ruleids+"&queryType=ruleid"});
    }

    //添加客户黑名单
    function customerblackList(id){
      $.ajax({
        type:'POST' ,
        "paging": false,
        "deferRender": true,
        "info":false,
        url:'${ctxPath}/api/1/dubioustxn/addCustomeBlackList',
//        data:{ids:id,type:type},
        data: {id:id},
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeaderName, srfToken);
        },
        dataType: 'json',
        success:function(data,textStatus,jqXHR){
          if(data.code == 200){
            bootbox.alert("操作成功！", function () {
              window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxn";
            });
          }else{
            bootbox.alert("操作失败！");
          }
        }
      });
    }

    //添加身份证黑名单（此功能暂时屏蔽）
    function idCardblackList(id){
      $.ajax({
        type:'POST' ,

        url:'${ctxPath}/api/1/dubioustxn/addIdCardBlackList',
        data: {id:id},
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeaderName, srfToken);
        },
        dataType: 'json',
        async:false,
        success:function(data,textStatus,jqXHR){
          if(data.code == 200){
            bootbox.alert("操作成功！", function () {
              window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxn";
            });
          }else{
            bootbox.alert("操作失败！");
          }
        }
      });
    }

    //全选
    function setChecked(){
      var choosetotal=$("input[name='choosetotal']");
      if(choosetotal[0].checked==true){
        $("input[name='choose']").attr("checked",true);
      }else{
        $("input[name='choose']").attr("checked",false);
      }
    }

    //关闭报警
    function closeWarn(id){
      $.ajax({
        type:'POST' ,
        url:'${ctxPath}/api/1/dubioustxn/dealDubioustxn',
        data: {ids:id},
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeaderName, srfToken);
        },
        dataType: 'json',
        async:false,
        success:function(data,textStatus,jqXHR){
          if(data.code == 200){
            bootbox.alert("操作成功！", function () {
              window.location.href="${ctxPath}/dubioustxn/toQueryDubioustxn";
            });
          }else{
            bootbox.alert("操作失败！");
          }
        }
      });

    }


  </script>
</div>
</html>