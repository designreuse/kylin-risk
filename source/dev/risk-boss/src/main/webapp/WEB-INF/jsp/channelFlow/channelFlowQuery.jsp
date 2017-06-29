<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>业务流程管理</title>
</head>
<body>
<div class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
        <div class="ibox-content m-b-sm border-bottom">
            <form id="channelFlowForm" action="${ctxPath}/channelFlow/queryChannelFlow"
                  data-table="queryTable">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-channelFlow">
                            <label class="col-sm-2 control-label">渠道名称</label>
                            <div class="col-sm-10">
                                <dict:select dictcode="channel" id="channelcode" name="channelcode"
                                             nullLabel="请选择" nullOption="true"
                                             cssClass="form-control"></dict:select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row query-div">
                    <a href="toAjaxAddChannelFlow" class="ajaxback-requst" id="addChannelFlow">
                        <button type="button" class="btn btn-w-m btn-primary">添加</button>
                    </a>
                    <button type="submit" class="btn btn-w-m btn-success">查询</button>
                    <button type="reset" class="btn btn-w-m btn-info">重置</button>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content">
                                <a href="#">
                                    <button type="button" id="deleteSelected"
                                            class="btn-white btn btn-xs">
                                        <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong>
                                    </button>
                                </a>
                                <br>
                                <table id="queryTable"
                                       class="table table-striped table-bordered table-hover"
                                       cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选<span class="footable-sort-indicator"></span></th>
                                        <th>渠道名称</th>
                                        <th>产品名称</th>
                                        <th>爬虫</th>
                                        <th>身份证验证</th>
                                        <th>银行卡验证</th>
                                        <th>手持身份证验证</th>
                                        <th>米投</th>
                                        <th>百融学历</th>
                                        <th>百融特殊名单</th>
                                        <th>百融多次查询</th>
                                        <th>银联智策</th>
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
<script id="checkbox-tpl" type="text/x-handlebars-template">
    <input type="checkbox" name="choose" onclick="setChooseChecked()" value="{{id}}"/>
</script>
<script id="oper-tpl" type="text/x-handlebars-template">
    <div class="btn-channelFlow">
        {{#each func}}
        <button type="button" class="btn-white btn btn-xs" onclick="{{this.fn}}">

            <i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}
        </button>
        {{/each}}
    </div>
</script>
</body>
<div id="siteMeshJavaScript">
    <script src="${libPath}/layer/layer.min.js"></script>
    <script type="text/javascript">
        var csrfToken = "${_csrf.token}";
        var csrfHeaderName = "${_csrf.headerName}";
        var checkboxSource = $("#checkbox-tpl").html();
        var checkBoxTemplate = Handlebars.compile(checkboxSource);
        var operTpl = $("#oper-tpl").html();
        var operTemplate = Handlebars.compile(operTpl);

        function getDetail(id) {
            $.ajaxParamBack({
                "url": "${ctxPath}/channelFlow/toAjaxQueryChannelFlowDetail", "data": "id=" + id
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

        function setChooseChecked(){
            if(($("input[name='choose']:checked").length+1 < $("input[type='checkbox']").length) || ($("input[type='checkbox']").length < 2)){
                $("input[name='choosetotal']").attr("checked",false);
            }else{
                $("input[name='choosetotal']").attr("checked",true);
            }
        }

        function deleteChannelFlow(ids) {
            if (confirm("确定删除？")) {
                $.ajax({
                    type: "POST",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(csrfHeaderName, csrfToken);
                    },
                    url: "${ctxPath}/api/1/channelFlow/deleteChannelFlow",
                    data: "id=" + ids,
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("删除成功！", function () {
                                $('#channelFlowForm').submit();
                                setChooseChecked();
                            });
                        } else {
                            bootbox.alert("删除失败！");
                        }
                    }
                });
            }
        }

        $(function () {
            $.ajaxBack();
            $("#deleteSelected").click(function () {
                var selectIds = "";
                var result = $("input[name='choose']:checked");
                if (result.length == 0) {
                    bootbox.alert("请选择数据");
                } else {
                    for (var i = 0; i < result.length; i++) {
                        selectIds = selectIds + result[i].value + ",";
                    }
                    selectIds = selectIds.substr(0, selectIds.length - 1);
                    deleteChannelFlow(selectIds);
                }
            });
        });

        $(function () {
            $('form#channelFlowForm').queryTables({
                "pageLength": 10,
                "columns": [{"data": "id"}, {"data": "channelname"}, {"data": "productname"}, {"data": "crawler"}, {"data": "idcardvalidate"}, {"data": "bankcardvalidate"}, {"data": "idcardhandvalidate"}, {"data": "mitou"}, {"data": "bairongeducational"}, {"data": "bairongspecial"}, {"data": "bairongquery"}, {"data": "unionpayadvisors"}, {"data": null}],
                columnDefs: [{
                    targets: 0, render: function (data, type, row, meta) {
                        setChooseChecked();
                        return checkBoxTemplate({"id": data});
                    }
                }, {
                    targets: [3, 4, 5, 6, 7, 8, 9, 10, 11],
                    render: function (data, type, row, meta) {
                        var dataMap = {'00': '是', '01': '否'};
                        return dataMap[data] || data;
                    }
                }, {
                    targets: 12, render: function (data, type, row, meta) {
                        var context = {
                            func: [{
                                "text": "编辑",
                                "fn": "getDetail(" + row.id + ")",
                                "cssClass": "fa-wrench"
                            }, {
                                "text": "删除",
                                "fn": "deleteChannelFlow(" + row.id + ")",
                                "cssClass": "fa-pencil",
                                "isexecute": row.isexecute
                            }]
                        };
                        var html = operTemplate(context);
                        return html;
                    }
                }]
            });
        });

    </script>
</div>
</html>
