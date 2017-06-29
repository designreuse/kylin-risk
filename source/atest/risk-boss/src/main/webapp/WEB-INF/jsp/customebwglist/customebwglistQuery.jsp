<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
    <title>客户黑白灰名单</title>
    <style>
        .dataTables_wrapper {
            padding-bottom: 0px
        }
    </style>
</head>
<body>
<div id="wrapper" class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
        <div class="ibox-content m-b-sm border-bottom">
            <form role="form" id="form" action="${ctxPath}/customerbwg/queryCustomebwglist"
                  method="get" data-table="queryTable">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" id="flag" value="${flag}">
                <div class="row">
                    <div class="col-sm-4">
                        <label class="control-label" for="customername">客户名称</label>
                        <input type="text" class="form-control input-sm" id="customername"
                               name="customername" placeholder="客户名称" class="form-control">
                    </div>
                    <div class="col-sm-4">
                        <label class="control-label" for="customernum">客户ID</label>
                        <input type="text" class="form-control input-sm" id="customernum"
                               name="customernum" placeholder="客户ID" class="form-control">
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="type">名单类型</label>
                            <dict:select dictcode="type" id="type" name="type" nullLabel="请选择"
                                         nullOption="true" cssClass="form-control"></dict:select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="cardtype">证件类型</label>
                            <dict:select dictcode="80004" id="cardtype" name="cardtype"
                                         nullLabel="请选择" nullOption="true"
                                         cssClass="form-control"></dict:select>
                        </div>
                    </div>

                    <div class="col-sm-4">
                        <div class="form-group">
                            <label class="control-label" for="cardnum">证件号码：</label>
                            <input type="text" class="form-control input-sm" id="cardnum"
                                   name="cardnum" placeholder="证件号码" class="form-control">
                        </div>
                    </div>
                </div>

                <div align="center">
                    <button type="submit" class="btn btn-w-m btn-success"><strong>查询</strong>
                    </button>
                    <c:if test="${flag=='02'}">
                        <div class="btn-group">
                            <button type="button" data-toggle="dropdown"
                                    class="btn btn-primary dropdown-toggle" aria-expanded="false">
                                <strong> 新增名单 </strong> <span class="caret"></span></button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="javascript:void(0);" onclick="toAjaxQueryCusBWG('black')" >新增黑名单</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" onclick="toAjaxQueryCusBWG('white')" >新增白名单</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" onclick="toAjaxQueryCusBWG('gray')" >新增灰名单</a>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </form>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="ibox">
                    <div class="ibox-content">
                        <table id="queryTable"
                               class="table table-striped table-bordered table-hover"
                               cellspacing="0">
                            <thead>
                            <tr>
                                <th><input type="checkbox" name='choosetotal'
                                           onclick='setChecked()'>全选<span
                                        class="footable-sort-indicator"></span></th>
                                <th>客户ID</th>
                                <th>客户名称</th>
                                <th>名单类型</th>
                                <th>证件类型</th>
                                <th>证件号码</th>
                                <th>账户号</th>
                                <th>名单来源</th>
                                <th>生效时间</th>
                                <th>失效时间</th>
                                <th>状态</th>
                            </tr>
                            </thead>
                        </table>
                        <c:if test="${flag=='01'}">
                            <a href="#">
                                <button type="button" id="bathVerify" class="btn btn-white"><i
                                        class="fa fa-github-square"></i>&nbsp;&nbsp;审核
                                </button>
                            </a>
                        </c:if>
                        <c:if test="${flag=='02'}">
                            <a href="#">
                                <button type="button" id="delete" class="btn btn-white"><i
                                        class="fa fa-trash-o"></i>&nbsp;&nbsp;删除
                                </button>
                            </a>

                            <a href="#">
                                <button type="button" id="bathRemove" class="btn btn-white"><i
                                        class="fa fa-wrench"></i>&nbsp;&nbsp;移除
                                </button>
                            </a>
                        </c:if>
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
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">操作</h4>
            </div>
            <div class="modal-content">
                加载中...
            </div>

        </div>
    </div>
</div>


<script id="checkbox-tpl" type="text/x-handlebars-template">
    <input type="checkbox" name="choose" value="{{id}}" status="{{status}}"/>
</script>

<script id="oper-tpl" type="text/x-handlebars-template">
    <div class="btn-group">
        {{#each func}}
        <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}"><i
                class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}
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
        var checkboxSource = $("#checkbox-tpl").html();
        var checkBoxTemplate = Handlebars.compile(checkboxSource);
        var operTpl = $("#oper-tpl").html();

        //预编译模板
        var operTemplate = Handlebars.compile(operTpl);
        var statusSource = $("#status-tpl").html();
        var statusTemplate = Handlebars.compile(statusSource);
        //绑定form的查询到tables
        $(function () {
            var flag = $("#flag").val();
            $('form#form').queryTables({
                "pageLength": 10,
                columns: [{"data": "id"}, {"data": "customerid"}, {"data": "customername"}, {"data": "type"}, {"data": "certificatetype"}, {"data": "certificateid"}, {"data": "accountnum"}, {"data": "source"}, {"data": "effecttime"}, {"data": "failuretime"}, {"data": "status"},],
                columnDefs: [{
                    targets: 0, render: function (data, type, row, meta) {
                        if (flag == "01") {
                            if (row["status"] == "02" || row["status"] == "03") {
                                return checkBoxTemplate({id:data,status:row.status});
                            } else {
                                return "";
                            }
                        } else {
                            if (row["status"]
                                    == "00"
                                    || row["status"]
                                    == "01"
                                    || row["status"]
                                    == "04") {
                                return checkBoxTemplate({id: data,status:row.status});
                            } else {
                                return "";
                            }
                        }
                    }
                },
                {
                    targets:[2,5,6,8,9],
                    render: function (data, type, row, meta) {
                        if (data == null || data == undefined) {
                            return "";
                        } else {
                            return data;
                        }

                    }
                },
                {
                    targets: 3, render: function (data, type, row, meta) {
                        var dataMap = {'01': '黑', '02': '白', '03': '灰'};
                        return dataMap[data] || data;
                    }
                },
                    {
                        targets: 4, render: function (data, type, row, meta) {
                            var dataMap = {
                                '111': '身份证',
                                '112': '临时居民身份证',
                                '114': '军官证',
                                '116': '暂住证',
                                undefined: '空'
                            };
                            return dataMap[data] || data;
                        }
                    },

                    {
                        targets: 7, render: function (data, type, row, meta) {
                            var dataMap = {'01': '内部', '02': '外部', '00': '其他'};
                            return dataMap[data] || data;
                        }
                    },
                    {
                        targets: 10, render: function (data, type, row, meta) {
                        var dataMap = {
                            '00': '有效',
                            '01': '无效',
                            '02': '新增待审核',
                            '03': '移除待审核',
                            '04': '审核拒绝'
                        };
                        return dataMap[data] || data;
                    }
                    }]
            });

            $("#myModal").on("hidden.bs.modal", function () {
                $(this).removeData("bs.modal");
            });

            $("#delete").click(function () {
                var deleteIds = ids();
                var len = $("input[name='choose']:checked").length;
                if (len == 0) {
                    bootbox.alert("请选择数据");

                } else {
                    bootbox.prompt("请输入删除的原因", function (result) {
                        if (result === null) {

                        } else {
                            $.ajax({
                                type: "POST",
                                beforeSend: function (xhr) {
                                    xhr.setRequestHeader(csrfHeaderName, csrfToken);
                                },
                                url: "${ctxPath}/api/1/customerbwg/deleteBWGfromCustom",
                                data: "deleteIds=" + deleteIds + "&reason=" + result,
                                dataType: "json",
                                success: function (data, textStatus, jqXHR) {
                                    if (data.code == 200) {
                                        bootbox.alert("删除成功！", function () {
                                            $('#form').submit();
                                        });
                                    } else {
                                        bootbox.alert("删除失败" + data.message);
                                    }
                                }
                            });
                        }
                    });
                }
            });

            $("#bathRemove").click(function () {
                var len = $("input[name='choose']:checked").length;
                var removeStatus = true;
                if (len == 0) {
                    bootbox.alert("请选择数据");
                    return;
                } else {
                    var removeids = "";
                    var str = document.getElementsByName("choose");
                    for (var i = 0; i < str.length; i++) {
                        //var status = str[i].parentNode.parentNode.lastChild.innerHTML;
                        var status = str[i].getAttribute('status');
                        if (status != '00') {
                            removeStatus = false;
                            bootbox.alert("仅能移除状态为“有效”的数据");
                            break;
                        } else {
                            if (str[i].checked == true) {
                                if (removeids == "") {
                                    removeids += str[i].value;
                                } else {
                                    removeids += "," + str[i].value;
                                }
                            }
                        }
                    }
                    if (removeStatus) {
                        bootbox.prompt("请输入移除的原因", function (result) {
                            if (result === null) {

                            } else {
                                $.ajax({
                                    type: "POST",
                                    beforeSend: function (xhr) {
                                        xhr.setRequestHeader(csrfHeaderName, csrfToken);
                                    },
                                    url: "${ctxPath}/api/1/customerbwg/customerbwgModify",
                                    data: "ids=" + removeids + "&opertype=remove&reason=" + result,
                                    dataType: "json",
                                    success: function (data, textStatus, jqXHR) {
                                        if (data.code == 200) {
                                            bootbox.alert("移除成功！", function () {
                                                $('#form').submit();                                            });
                                        } else {
                                            bootbox.alert("移除失败！" + data.message);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });

            $("#bathVerify").click(function () {
                var verifyIds = ids();
                var len = $("input[name='choose']:checked").length;
                if (len == 0) {
                    bootbox.alert("请选择数据");

                } else {
                    bootbox.dialog({
                        title: "审核.",
                        message: '<div class="row">  '
                        + '<div class="col-md-12"> '
                        + '<form class="form-horizontal"> '
                        + '<div class="form-group"> '
                        + '<label class="col-md-4 control-label" for="awesomeness">审核结果</label> '
                        + '<div class="col-md-4"> <div class=""> <label for="awesomeness-0"> '
                        + '<input type="radio" name="awesomeness" id="awesomeness-0" value="00" checked="checked"> '
                        + '通过</label> '
                        + '</div><div class=""> <label for="awesomeness-1"> '
                        + '<input type="radio" name="awesomeness" id="awesomeness-1" value="04">拒绝 </label> '
                        + '</div> '
                        + '</div> </div>'
                        + '<div class="form-group"> '
                        + '<label class="col-md-4 control-label" for="reason">备注</label> '
                        + '<div class="col-md-6"> '
                        + '<input id="reason" name="reason" type="text" placeholder="备注" class="form-control input-md"> '
                        + '</div> '
                        + '</div> '
                        + '</form> </div>  </div>',
                        buttons: {
                            success: {
                                label: "提 交", className: "btn-primary", callback: function () {
                                    var reason = $('#reason').val();
                                    var result = $("input[name='awesomeness']:checked").val();
                                    $.ajax({
                                        type: "POST",
                                        beforeSend: function (xhr) {
                                            xhr.setRequestHeader(csrfHeaderName, csrfToken);
                                        },
                                        url: "${ctxPath}/api/1/customerbwg/customerbwgVerify",
                                        data: "ids="
                                        + verifyIds
                                        + "&reason="
                                        + reason
                                        + "&status="
                                        + result,
                                        dataType: "json",
                                        success: function (data, textStatus, jqXHR) {
                                            if (data.code == 200) {
                                                bootbox.alert("审核成功！", function () {
                                                    $('#form').submit();                                                });
                                            } else {
                                                bootbox.alert("审核失败！" + data.message);
                                            }
                                        }
                                    });

                                }
                            }
                        }
                    });
                }
            });
        });

        function toAjaxQueryCusBWG(type){
            $.ajaxParamBack({"url":"${ctxPath}/customerbwg/toAjaxQueryCustomerbwg","data":"type="+type});
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

        //获取所选择的id
        function ids() {
            var Ids = "";
            var str = document.getElementsByName("choose");
            for (var i = 0; i < str.length; i++) {
                if (str[i].checked == true) {
                    if (Ids == "") {
                        Ids += str[i].value;
                    } else {
                        Ids += "," + str[i].value;
                    }
                }
            }
            return Ids;
        }

    </script>
</div>
</html>