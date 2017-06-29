<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>个人信息审核</title>
</head>
<body>
<div id="wrapper" class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
        <div class="ibox-content m-b-sm border-bottom" id="divContent">
            <form role="form" id="operFlowform" action="${ctxPath}/operFlow/queryOperFlow" data-table="queryTable">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">工作流ID</label>
                            <div class="col-sm-10">
                                <input type="text" id="checkorderid" name="checkorderid" placeholder="工作流ID"
                                       class=" form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">客户编号</label>
                            <div class="col-sm-10">
                                <input type="text" id="customerid" name="customerid" placeholder="客户编号"
                                       class=" form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-6">
                    <div class="form-group">
                            <label class="col-sm-2 control-label ">结果状态</label>
                            <div class="col-sm-10">
                                <dict:select dictcode="flowstatus" id="resultstatus" name="resultstatus"
                                             nullLabel="---请选择---" nullOption="true"
                                             cssClass="form-control"></dict:select>
                            </div>
                        </div>
                        </div>
                </div>
                <br>
                <div class="row query-div">
                    <button type="submit" class="btn btn-w-m btn-success">查询</button>
                    <button type="reset" class="btn btn-w-m btn-info">重置</button>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content">
                                <br>
                                <table id="queryTable" class="table table-striped table-bordered table-hover"
                                       cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>工作流ID</th>
                                        <th>客户编号</th>
                                        <th>结果状态</th>
                                        <th>拒绝原因</th>
                                        <th>风险提示</th>
                                        <th>课程名称</th>
                                        <th>课程价格</th>
                                        <th>创建时间</th>
                                        <th>更新时间</th>
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
        {{#each func}}
        <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}
        </button>
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
        var statusSource = $("#status-tpl").html();
        var statusTemplate = Handlebars.compile(statusSource);
        var operTpl = $("#oper-tpl").html();
        var operTemplate = Handlebars.compile(operTpl);


        function queryFlowRule(ruleids) {
            $.ajaxParamBack({"url":"${ctxPath}/ruleHis/toAjaxQueryRuleHis","data":"queryId="+ruleids+"&queryType=ruleid"});
        }


        //绑定form的查询到tables
        $(function () {
            $('form#operFlowform').queryTables({
                "pageLength": 10,
                "serverSide": true,
                columns: [
                    {"data": "checkorderid"},
                    {"data": "customerid"},
                    {"data": "resultstatus"},
                    {"data": "reason"},
                    {"data": "riskmsg"},
                    {"data": "classname"},
                    {"data": "classprice"},
                    {"data": "createtime"},
                    {"data": "updatetime"},
                    {"data": "ruleid"}
                ],
                columnDefs: [
                    {
                        targets: [0, 1, 3, 4, 5],
                        render: function (data, type, row, meta) {
                            if (data == null) {
                                return "";
                            } else {
                                return data;
                            }
                        }
                    },
                    {
                        targets: 2,
                        render: function (data, type, row, meta) {
                            var dataMap = {
                                "1": {"cssClass": "label-primary", text: "黑名单"},
                                "2": {"cssClass": "label-warning", text: "驳回"},
                                "3": {"cssClass": "label-danger", text: "拒绝"}
                            };
                            var map = dataMap[data];
                            return map ? statusTemplate(map) : '';
                        }
                    },

                    {
                        targets: [7, 8],
                        render: function (data, type, row, meta) {
                            return moment(data).format('YYYY-MM-DD HH:mm:ss');
                        }
                    },

                    {
                        targets: 9,
                        render: function (data, type, row, meta) {
                            var context =
                            {
                                func: [
                                    {
                                        "text": "查看规则",
                                        "fn": "queryFlowRule('" + data + "')",
                                        "cssClass": "fa-wrench"
                                    }
                                ]
                            };
                            var html = operTemplate(context);
                            return html;
                        }
                    }
                ]
            });
        });

    </script>

</div>
</html>
