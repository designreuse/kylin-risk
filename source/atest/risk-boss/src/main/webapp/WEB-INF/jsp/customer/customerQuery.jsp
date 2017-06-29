<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>客户查询</title>
</head>
<body>
<div id="wrapper" class="divContainer">
  <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="form" action="${ctxPath}/customer/queryCustomer" method="get" data-table="queryTable">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
          <div class="col-sm-4">
            <label class="control-label" for="customerid">客户号</label>
            <input type="text" class="form-control input-sm" id="customerid" name="customerid" placeholder="客户号" class="form-control">
          </div>
          <div class="col-sm-4">
              <label class="control-label" for="customername">客户名称</label>
              <input type="text" class="form-control input-sm" id="customername" name="customername" placeholder="客户名称" class="form-control">
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="certificatetype">证件类型</label>
              <dict:select dictcode="80004" id="certificatetype" name="certificatetype"  nullLabel="请选择" nullOption="true" cssClass="form-control"></dict:select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="certificateid">证件号码</label>
              <input type="text" class="form-control input-sm" id="certificateid" name="certificateid"  placeholder="证件号码" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="createtime">开户日期</label>
              <input type="text" class="form-control input-sm" id="createtime" name="createtime"  placeholder="开户日期" class="form-control">
            </div>
          </div>
        </div>

        <div align="center" >
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
          <a href="${ctxPath}/customer/_toImportCustomer" data-toggle="modal" data-target="#myModal">
            <button class="btn btn-w-m btn-primary " type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">导入客户信息</span></button>
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
                      <th>证件类型</th>
                      <th>证件号码</th>
                      <%--<th>融资年限</th>--%>
                      <th>来源渠道</th>
                      <th>创建时间</th>
                      <th>更新时间</th>
                      <th>分数</th>
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
<%--<div id="">
<div class="scrollWrapper"></div>
<div class="scrollWrapper"></div>
</div>--%>
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
      "pageLength": 10,
      "serverSide": true,
      "bAutoWidth": false,
      columns: [
        {"data": "id"},
        {"data": "customerid"},
        {"data": "customername"},
        {"data": "certificatetype"},
        {"data": "certificateid"},
        /*{"data": "financingtime"},*/
        {"data": "channel"},
        {"data": "createtime"},
        {"data": "updatetime"},
        {"data": "score"},
        {"data": null}
      ],
      columnDefs:[
        {
          targets:3,
          render:function(data, type, row, meta){
            var dataMap={'111':'身份证','112':'临时居民身份证','114':'军官证','116':'暂住证',
              undefined:'其他'};
            return dataMap[data]||data;
          }
        },
        {
          targets:[4,5],
          render:function(data, type, row, meta){
            if(data==null){
              return "";
            }else{
              return data;
            }
          }
        },
        {
          targets: [6,7],
          render:function(data, type, row, meta){
            if(data==null){
              return "";
            }else{
              return moment(data).format('YYYY-MM-DD HH:mm:ss');
            }
          }
        },
        {
          targets:9,
          render: function (data, type, row, meta) {
            var context =
            {
              func: [
                {"text": "查看", "fn": "getDetail('"+data.customerid+"')", "cssClass": "fa-wrench"}
              ]
            };
            var html = operTemplate(context);
            return html;
          }
        }
      ]
    });

  });

    function getDetail(customerid){
      $.ajaxParamBack({"url":"${ctxPath}/customer/toAjaxQueryCustomerDetail","data":"ids="+customerid});
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