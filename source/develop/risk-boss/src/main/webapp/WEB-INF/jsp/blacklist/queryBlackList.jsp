<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询黑名单</title>
</head>
<body>
<div id="wrapper">
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
      <div class="ibox-content m-b-sm border-bottom">
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="order_id">Order ID</label>
              <input type="text" id="order_id" name="order_id" value="" placeholder="Order ID" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="status">Order status</label>
              <input type="text" id="status" name="status" value="" placeholder="Status" class="form-control">
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="customer">Customer</label>
              <input type="text" id="customer" name="customer" value="" placeholder="Customer" class="form-control">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="date_added">Date added</label>
              <div class="input-group date">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="date_added" type="text" class="form-control Wdate" value="03/04/2014" onClick="WdatePicker()">
              </div>
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="date_modified">Date modified</label>
              <div class="input-group date">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="date_modified" type="text" class="form-control" value="03/06/2014">
              </div>
            </div>
          </div>
          <div class="col-sm-4">
            <div class="form-group">
              <label class="control-label" for="amount">Amount</label>
              <input type="text" id="amount" name="amount" value="" placeholder="Amount" class="form-control">
            </div>
          </div>
        </div>

      </div>

      <div class="row">
        <div class="col-lg-12">
          <div class="ibox">
            <div class="ibox-content">
              <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                <thead>
                <tr>
                  <th>Name</th>
                  <th>Position</th>
                  <th>Office</th>
                  <th>Extn.</th>
                  <th>Start date</th>
                  <th>Salary</th>
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
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    $(function(){
      $("#queryTable").DataTable({
        paging: true,
        language: {
          "paginate": {
            "first": "首页",
            "last": "尾页",
            "previous": "<",
            "next": ">"
          }
        },
        columns: [
          { "data": "name" },
          { "data": "position" },
          { "data": "office" },
          { "data": "extn" },
          { "data": "start_date" },
          { "data": "salary" }
        ],
        data:[
          {
            "name": "Tiger Nixon",
            "position": "System Architect",
            "salary": "$320,800",
            "start_date": "2011/04/25",
            "office": "Edinburgh",
            "extn": "5421"
          },
          {
            "name": "Garrett Winters",
            "position": "Accountant",
            "salary": "$170,750",
            "start_date": "2011/07/25",
            "office": "Tokyo",
            "extn": "8422"
          },
          {
            "name": "Ashton Cox",
            "position": "Junior Technical Author",
            "salary": "$86,000",
            "start_date": "2009/01/12",
            "office": "San Francisco",
            "extn": "1562"
          },
          {
            "name": "Cedric Kelly",
            "position": "Senior Javascript Developer",
            "salary": "$433,060",
            "start_date": "2012/03/29",
            "office": "Edinburgh",
            "extn": "6224"
          },
          {
            "name": "Airi Satou",
            "position": "Accountant",
            "salary": "$162,700",
            "start_date": "2008/11/28",
            "office": "Tokyo",
            "extn": "5407"
          },
          {
            "name": "Brielle Williamson",
            "position": "Integration Specialist",
            "salary": "$372,000",
            "start_date": "2012/12/02",
            "office": "New York",
            "extn": "4804"
          },
          {
            "name": "Herrod Chandler",
            "position": "Sales Assistant",
            "salary": "$137,500",
            "start_date": "2012/08/06",
            "office": "San Francisco",
            "extn": "9608"
          },
          {
            "name": "Rhona Davidson",
            "position": "Integration Specialist",
            "salary": "$327,900",
            "start_date": "2010/10/14",
            "office": "Tokyo",
            "extn": "6200"
          }
        ]
      });
    });
  </script>
</div>
</html>
