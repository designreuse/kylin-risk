 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>新增名单</title>
  <style>
    .dataTables_wrapper{padding-bottom: 0px}
    </style>
</head>
<body>
<div id="wrapper" >
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-title">
      <h5>
        <c:if test="${type=='black'}">
        <strong>添加客户黑名单</strong><small>.</small></h5>
      </c:if>
      <c:if test="${type=='white'}">
        <strong>添加客户白名单</strong><small>.</small></h5>
      </c:if>
      <c:if test="${type=='gray'}">
        <strong>添加客户灰名单</strong><small>.</small></h5>
      </c:if>
    </div>
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="addForm" action="${ctxPath}/customer/queryCusByCondition"  method="get" data-table="queryTable1">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
          <div class="col-sm-2" style="margin-left: 80px">
            <div class="form-group">
              <label class="control-label" for="customerid">客户编号</label>
              <input type="text" class="form-control input-sm" id="customerid" name="customerid" placeholder="客户编号" class="form-control">
            </div>
          </div>
          <div class="col-sm-2">
            <div class="form-group">
              <label class="control-label" for="customername">客户名称</label>
              <input type="text" class="form-control input-sm" id="customername" name="customername"  placeholder="客户名称" class="form-control">
            </div>
          </div>
          <div class="col-sm-2">
            <div class="form-group">
              <label class="control-label" for="certificateid">证件号</label>
              <input type="text" class="form-control input-sm" id="certificateid" name="certificateid"  placeholder="证件号" class="form-control">
            </div>
          </div>
          <div class="col-md-2">
            <label>开户日期开始</label>
            <input type="text" id="timebegin" name="timebegin" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'timeend\')}'})" class="form-control">
          </div>
          <div class="col-md-2">
            <label>开户日期结束</label>
            <input type="text" id="timeend" name="timeend" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'timebegin\')}'})" class="form-control">
          </div>
        </div>
        <div align="center">
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="button" id="back" class="btn btn-w-m btn-success"><strong>返回</strong></button>
        </div>
      </form>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-content">
            <table id="queryTable1" class="table table-striped table-bordered table-hover" cellspacing="0">
              <thead>
              <tr>
                <th><input type="checkbox" name='addChoosetotal' onclick='setChecked()'>全选<span class="footable-sort-indicator"></span></th>
                <th>客户编号</th>
                <th>客户名称</th>
                <th>证件号</th>
                <th>开户日期</th>
              </tr>
              </thead>
            </table>
            <a id="aa" href="#" data-toggle="modal" data-target="#myModal">
              <button id ="add" type="button" class="btn btn-w-m btn-white"><i class="fa fa-check-square"></i>&nbsp;&nbsp;添加</button>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content1">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">选择名单的生效与失效日期</h4>
      </div>
      <div class="modal-content">
        加载中...
      </div>

    </div>
  </div>
</div>


<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox"  name="addChoose" value="{{id}}"/>
</script>

</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}";
    var checkboxSource   = $("#checkbox-tpl").html();
    var checkBoxTemplate = Handlebars.compile(checkboxSource);
    //绑定form的查询到tables


    $(function(){
      $("#back").backup();
      $("#myAddModal").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
      });


      $('form#addForm').queryTables({
        "pageLength":10,
        "serverSide": true,
        "bAutoWidth": false,
        columns: [
          {"data": "customerid"},
          {"data": "customerid"},
          {"data": "customername"},
          {"data": "certificateid"},
          {"data": "createtime"}
        ],
        columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({id:data});
            }
          },
          {
            targets:4,
            render:function(data, type, row, meta){
              return data.substring(0,19);
            }
          }
        ]
      });

      $("#aa").click(function(){

        var addIds = ids();
        var len=$("input[name='addChoose']:checked").length;
        if(len==0){
          bootbox.alert("请选择数据",function(){
            $('#myAddModal').modal('hide');
          });
        }else{

          $("#aa").attr("href","${ctxPath}/customerbwg/_toAddCustomBwg?type=${type}&ids="+addIds);

        }

       });

    });

    //获取所选择的id
    function ids(){
      var Ids="";
      var str=document.getElementsByName("addChoose");
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
    function setChecked(){
      var choosetotal=$("input[name='addChoosetotal']");
      if(choosetotal[0].checked==true){
        $("input[name='addChoose']").attr("checked",true);
      }else{
        $("input[name='addChoose']").attr("checked",false);
      }
    }


  </script>
</div>
</html>