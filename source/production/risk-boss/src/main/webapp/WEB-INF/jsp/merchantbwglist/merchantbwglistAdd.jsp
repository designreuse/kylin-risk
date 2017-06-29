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
       <strong>添加企业黑名单</strong><small>.</small></h5>
      </c:if>
      <c:if test="${type=='white'}">
      <strong>添加企业白名单</strong><small>.</small></h5>
      </c:if>
      <c:if test="${type=='gray'}">
        <strong>添加企业灰名单</strong><small>.</small></h5>
      </c:if>
    </div>
    <div class="ibox-content m-b-sm border-bottom">
      <form role="form" id="addForm" action="${ctxPath}/merchant/queryMercByCondition"  method="get" data-table="queryTable1">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row">
          <div class="col-sm-2" style="margin-left: 80px">
            <div class="form-group">
              <label class="control-label" for="merchantid">企业编号</label>
              <input type="text" class="form-control input-sm" id="merchantid" name="merchantid" placeholder="企业编号" class="form-control">
            </div>
          </div>
          <div class="col-sm-2">
            <div class="form-group">
              <label class="control-label" for="merchantname">企业名称</label>
              <input type="text" class="form-control input-sm" id="merchantname" name="merchantname"  placeholder="企业名称" class="form-control">
            </div>
          </div>
          <div class="col-sm-2">
            <div class="form-group">
              <label class="control-label" for="ownercertid">法人证件号</label>
              <input type="text" class="form-control input-sm" id="ownercertid" name="ownercertid"  placeholder="法人证件号" class="form-control">
            </div>
          </div>
          <div class="col-sm-2">
            <div class="form-group">
              <label class="control-label" for="corporationid">营业执照号</label>
              <input type="text" class="form-control input-sm" id="corporationid" name="corporationid"  placeholder="营业执照号" class="form-control">
            </div>
          </div>
        </div>
        <div align="center">
          <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
          <button type="button" id="back"class="btn btn-w-m btn-success"><strong>返回</strong></button>
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
                <th>企业编号</th>
                <th>企业名称</th>
                <th>法人证件号</th>
                <th>营业执照号</th>
                <th>企业状态</th>
              </tr>
              </thead>
            </table>
            <a id="aa" href="#"  data-toggle="modal" data-target="#myModal1">
              <button type="button" id ="add" class="btn-white btn btn-xs"><i class="fa fa-check-square"></i>&nbsp;&nbsp;<strong>添加</strong></button>
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1">
  <div class="modal-dialog" role="document">
    <div class="modal-content1">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel1">选择名单的生效与失效日期</h4>
      </div>
      <div class="modal-content">
        加载中...
      </div>

    </div>
  </div>
</div>

<script id="checkbox-tpl" type="text/x-handlebars-template">
  <input type="checkbox" name="addChoose" value="{{id}}"/>
</script>

</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}"
    var checkboxSource   = $("#checkbox-tpl").html();
    var checkBoxTemplate = Handlebars.compile(checkboxSource);
    //绑定form的查询到tables
    $(function(){
      $("#myModal1").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
      });
      $('form#addForm').queryTables({
        "pageLength":10,
        columns: [
          {"data": "merchantid"},
          {"data": "merchantid"},
          {"data": "merchantname"},
          {"data": "ownercertid"},
          {"data": "corporationid"},
          {"data": "merchantstatus"}
        ],
        columnDefs:[
          {
            targets:0,
            render:function(data, type, row, meta){
              return checkBoxTemplate({id:data});
            }
          },
          {
            targets:5,
            render:function(data, type, row, meta){
              var dataMap={'01':'已保存','02':'正常','03':'已到期','04':'未生效'}
              return dataMap[data]||data;
            }
          }
        ]
      });

      $("#back").backup();

      $("#aa").click(function(){
        var addIds = "";
        var str = document.getElementsByName("addChoose");
        for (var i = 0; i < str.length; i++) {
          if (str[i].checked == true) {
            if (addIds == "") {
              addIds += str[i].value;
            } else {
              addIds += "," + str[i].value;
            }
          }
        }
        $("#aa").attr("href","${ctxPath}/mercbwg/_toAddMerBwg?type=${type}&ids="+addIds);
      })


    });



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