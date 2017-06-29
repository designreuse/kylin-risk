<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
    <title>日志查询</title>
</head>
<body>
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">

      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>查询条件</h5>
            </div>
            <div class="ibox-content m-b-sm border-bottom">
              <form  role="form" id="operLogform" action="${ctxPath}/operatorLog/queryOperatorLogs" method="get" data-table="queryTable">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row ">
                  <div class="col-sm-4 ">
                    <div class="form-group">
                      <label for="username">操作员</label>
                      <input type="text" class="form-control input-sm" name="username" id="username" >
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label for="startTimeStr">操作时间开始</label>
                      <input type="text" class="form-control input-sm Wdate" name="startTimeStr" id="startTimeStr" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" >
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label for="endTimeStr">操作时间结束</label>
                      <input type="text" class="form-control input-sm Wdate" name="endTimeStr" id="endTimeStr" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" >
                    </div>
                  </div>
                </div>
                <div class="row ">
                  <div class="col-sm-4 ">
                    <div class="form-group">
                      <label for="operateobj">操作实体</label>
                      <input type="text" class="form-control input-sm" name="operateobj" id="operateobj">
                    </div>
                    </div>
                  <div class="col-sm-4 ">
                    <div class="form-group">
                      <label for="operatedes">操作详细描述</label>
                      <input type="text" class="form-control input-sm" name="operatedes" id="operatedes" >
                    </div>
                    </div>
                </div>
                <div class="row">
                  <div class="col-lg-12" align="center">
                    <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong></button>
                    <button type="reset" class="btn btn-w-m btn-info"><strong>重置</strong></button>
                  </div>
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
                  <div class="table-responsive">
                    <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                      <thead>
                        <tr>
                          <th>操作员</th>
                          <th>操作时间</th>
                          <th>操作实体</th>
                          <th>操作描述</th>
                          <th>备注</th>
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
  </div>

</body>
<div id="siteMeshJavaScript">

  <script type="text/javascript">

    //绑定form的查询到tables
    $(function(){
      $('form#operLogform').queryTables({
        "pageLength":10,
        columns: [
          {"data": "username"},
          {"data": "operatedate"},
          {"data": "operateobj"},
          {"data": "operatedes"},
          {"data": "operateremark"}
        ],
        columnDefs:[
          {
            targets:1,
            render:function(data, type, row, meta){
              return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
            }
          }

        ]
      });
    });
  </script>
</div>
</html>
