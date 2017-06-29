<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>企业查询</title>
  <style>
    .dataTables_wrapper{padding-bottom: 0px}
  </style>
</head>
<body>
<div id="wrapper" >
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="form" action="${ctxPath}/merchant/queryMerchantByAll" method="get" data-table="queryTable">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
          <div class="col-sm-4">
            <label class="control-label" for="merchantid">企业号</label>
            <input type="text" class="form-control input-sm" id="merchantid" name="merchantid" placeholder="企业号" class="form-control">
          </div>
          <div class="col-sm-4">
            <label class="control-label" for="merchantname">企业名称</label>
            <input type="text" class="form-control input-sm" id="merchantname" name="merchantname" placeholder="企业名称" class="form-control">
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="corptype">公司类型</label>
              <dict:select dictcode="80004" id="corptype" name="corptype"  nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="corpname">公司名称</label>
              <input type="text" class="form-control input-sm" id="corpname" name="corpname"  placeholder="公司名称" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="createtime">开业时间</label>
              <input type="text" class="form-control input-sm" id="createtime" name="createtime"  placeholder="开业时间" class="form-control">
            </div>
          </div>
        </div>

        <div align="center" >
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
          <a href="${ctxPath}/merchant/_toImportMerchant" data-toggle="modal" data-target="#myModal">
            <button class="btn btn-w-m btn-primary " type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">导入企业Excel</span></button>
          </a>
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
                <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选<span class="footable-sort-indicator"></span></th>
                <th>客户号</th>
                <th>客户名称</th>
                <th>公司类型</th>
                <th>公司名称</th>
                <th>渠道ID</th>
                <th>公司营业执照号</th>
                <th>企业注册日期</th>
                <th>企业到期日期</th>
                <th>操作</th>
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
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">操作</h4>
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

    //预编译模板
    var operTemplate = Handlebars.compile(operTpl);
    var statusSource   = $("#status-tpl").html();
    var statusTemplate = Handlebars.compile(statusSource);
    //绑定form的查询到tables
    $(function(){
      var flag=$("#flag").val();
      $('form#form').queryTables({
        "pageLength":10,
        "serverSide": true,
        "bAutoWidth": false,
        columns: [
          {"data": "id"},
          {"data": "merchantid"},
          {"data": "merchantname"},
          {"data": "corptype"},
          {"data": "corpname"},
          {"data": "channelid"},
          {"data": "corporationid"},
          {"data": "merchstartdate"},
          {"data": "merchduedate"},
          {"data": null}
        ],
        columnDefs:[
          {
            targets:[1,2,3,4,5,6,7,8],
            render:function(data, type, row, meta){
              if(data==null){
                return "";
              }else{
                return data;
              }
            }
          },
          {
            targets: [7,8],
            render:function(data, type, row, meta){
              return moment(data).format('YYYY-MM-DD HH:mm:ss');
            }
          },
          {
            targets:9,
            render: function (data, type, row, meta) {
              var context =
              {
                func: [
                  {"text": "查看", "fn": "getDetail('"+data.merchantid+"')", "cssClass": "fa-wrench"}
                ]
              };
              var html = operTemplate(context);
              return html;
            }
          }
        ]
      });

    });

    function getDetail(merchantid){
      window.location.href="${ctxPath}/merchant/merchantDetail?id="+merchantid;
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

  </script>
</div>
</html>