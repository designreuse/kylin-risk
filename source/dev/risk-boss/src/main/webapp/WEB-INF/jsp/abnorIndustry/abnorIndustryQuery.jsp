<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>异常行业代码查询</title>
</head>
<body>
<div class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
        <div class="ibox-content m-b-sm border-bottom">
            <form role="form" id="form" action="${ctxPath}/abnorIndustry/queryAbnorIndustry" data-table="queryTable">
            <div class="row">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="control-label" for="codenm">异常行业名称</label>
                        <input type="text" id="codenm" name="codenm" value="${abnorIndustrys.codenm}" placeholder="异常行业名称" class="form-control">
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="form-group">
                        <label class="control-label" for="codeid">异常行业代码</label>
                        <input type="text" id="codeid" name="codeid" value="${abnorIndustrys.codeid}" placeholder="异常行业代码" class="form-control">
                    </div>
                </div>
            </div>
            <div class="row query-div">
                <button type="submit" class="btn btn-w-m btn-success">查询</button>
                <button type="reset" class="btn btn-w-m btn-info">重置</button>
                <a href="${ctxPath}/abnorIndustry/_toImportAbnorIndustry" data-toggle="modal" data-target="#myModal">
                    <button class="btn btn-w-m btn-primary " type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">导入异常行业代码</span></button>
                </a>
                <button id="export" type="button" class="btn btn-w-m btn-warning">导出</button>
            </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="ibox">
                    <div class="ibox-content">
                        <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                            <thead>
                            <tr>
                                <th>异常行业名称</th>
                                <th>异常行业代码</th>
                                <th>所属反洗钱行业类型</th>
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
        var operTpl = $("#oper-tpl").html();
        //预编译模板
        var operTemplate = Handlebars.compile(operTpl);
        var statusSource   = $("#status-tpl").html();
        var statusTemplate = Handlebars.compile(statusSource);

        function getDetail(id,type) {
            var codenm=$("#codenm").val();
            var codeid=$("#codeid").val();
            $.ajaxParamBack({"url":"${ctxPath}/abnorIndustry/toAjaxGetAbnorIndustryDetail","data":"ids="+id+"&dealType="+type+"&codeid="+codeid+"&codenm="+codenm});
        }

        //绑定form的查询到tables
        $(function(){
            $('form#form').queryTables({
                "pageLength":10,
                columns: [
                    {"data": "codenm"},
                    {"data": "codeid"},
                    {"data": "type"},
                    {"data": null}
                ],
                columnDefs:[
                    {
                        targets:2,
                        render:function(data, type, row, meta){
                            var dataMap={"00":{text:"赌场或其他赌博机构"},
                                "01":{text:"彩票销售行业构"},
                                "02":{text:"贵金属、稀有矿石或珠宝行业"},
                                "03":{text:"与武器有关的行业"},
                                "04":{text:"珠宝、证券、基金（待确认）、典当、房产、娱乐场所、奢侈品、游戏、点卡、视频、积分换购"}
                            };
                            var map=dataMap[data];
                            return map?statusTemplate(map):'';
                        }
                    },
                    {
                        targets: 3,
                        render: function (data, type, row, meta) {
                            var context =
                            {
                                func: [
                                    {"text": "查看", "fn": "getDetail("+row.id+",'query')", "cssClass": "fa-wrench"},
                                    {"text": "编辑", "fn": "getDetail("+row.id+",'modify')", "cssClass": "fa-pencil"}
                                ]
                            };
                            var html = operTemplate(context);
                            return html;
                        }
                    }
                ]
            });
        });
        $("#export").click(function () {
            location.href = "${ctxPath}/abnorIndustry/exportAbnorIndustryExcel?" + $("form").serialize();
        });
    </script>
</div>
</html>