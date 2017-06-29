<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>国家代码查询</title>
</head>
<body>
<div class="divContainer">
<div class="wrapper wrapper-content animated fadeInRight ecommerce backPageDiv">
    <div class="ibox-content m-b-sm border-bottom">
        <form role="form" id="form" action="${ctxPath}/abnorCountry/queryAbnorCountry" data-table="queryTable" >
            <div class="row">
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="cnnm">中文全称</label>
                        <input type="text" id="cnnm" name="cnnm" value="${abnormalCountrycodes.cnnm}" placeholder="中文全称" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="cnabb">中文简称</label>
                        <input type="text" id="cnabb" name="cnabb" value="${abnormalCountrycodes.cnabb}" placeholder="中文简称" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="ennm">英文全称</label>
                        <input type="text" id="ennm" name="ennm" value="${abnormalCountrycodes.ennm}" placeholder="英文全称" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="enabb">英文简称</label>
                        <input type="text" id="enabb" name="enabb" value="${abnormalCountrycodes.enabb}" placeholder="英文简称" class="form-control">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="twoletter">英文代码（两位）</label>
                        <input type="text" id="twoletter" name="twoletter" value="${abnormalCountrycodes.twoletter}" placeholder="英文代码（两位）" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="threelteeer">英文代码（三位）</label>
                        <input type="text" id="threelteeer" name="threelteeer" value="${abnormalCountrycodes.threelteeer}" placeholder="英文代码（三位）" class="form-control">
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label class="control-label" for="threenum">数字代码</label>
                        <input type="text" id="threenum" name="threenum" value="${abnormalCountrycodes.threenum}" placeholder="数字代码" class="form-control">
                    </div>
                </div>
            </div>
            <div class="row query-div">
                <button type="submit" class="btn btn-w-m btn-success">查询</button>
                <button type="reset" class="btn btn-w-m btn-info">重置</button>
                <a href="${ctxPath}/abnorCountry/_toImportAbnorCountry" data-toggle="modal" data-target="#myModal">
                    <button class="btn btn-w-m btn-primary " type="button"><i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">导入异常国家代码</span></button>
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
                                    <th>中文全称</th>
                                    <th>中文简称</th>
                                    <th>英文全称</th>
                                    <th>英文简称</th>
                                    <th>英文代码（两位）</th>
                                    <th>英文代码（三位）</th>
                                    <th>数字代码</th>
                                    <th>所属反洗钱组织名单</th>
                                    <th>最后修改人</th>
                                    <th>修改时间</th>
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
        <button class="btn-white btn btn-xs" onclick="{{this.fn}}"><i class="fa {{this.cssClass}}"></i>&nbsp;&nbsp;{{this.text}}</button>
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
        var csrfHeaderName = "${_csrf.headerName}"
        var operTpl = $("#oper-tpl").html();
        //预编译模板
        var operTemplate = Handlebars.compile(operTpl);
        var statusSource   = $("#status-tpl").html();
        var statusTemplate = Handlebars.compile(statusSource);

        function getDetail(id,type) {
            var cnnm=$("#cnnm").val();
            var cnabb=$("#cnabb").val();
            var ennm=$("#ennm").val();
            var enabb=$("#enabb").val();
            var twoletter=$("#twoletter").val();
            var threelteeer=$("#threelteeer").val();
            var threenum=$("#threenum").val();
            $.ajaxParamBack({"url":"${ctxPath}/abnorCountry/toAjaxGetAbnorCountryDetail","data":"ids="+id+"&dealType="+type
            +"&cnnm="+cnnm+"&cnabb="+cnabb+"&ennm="+ennm+"&enabb="+enabb+"&twoletter="+twoletter+"&threelteeer="+threelteeer+"&threenum="+threenum});

        }

        //绑定form的查询到tables
        $(function(){
            $('form#form').queryTables({
                "pageLength":10,
                columns: [
                    {"data": "cnnm"},
                    {"data": "cnabb"},
                    {"data": "ennm"},
                    {"data": "enabb"},
                    {"data": "twoletter"},
                    {"data": "threelteeer"},
                    {"data": "threenum"},
                    {"data": "type"},
                    {"data": "username"},
                    {"data": "updatetime"},
                    {"data": null}
                ],
                columnDefs:[
                    {
                        targets:7,
                        render:function(data, type, row, meta){
                            var dataMap={"00":{text:"FATF等反洗钱组织成员国"},
                                "01":{text:"被FinCEN、FATF等国际反洗钱组织、美国海外资产控制办公室（OFAC）列入制裁名单的国家或地区"},
                                "02":{text:"贩毒、恐怖活动或犯罪活动猖獗国家或地区"},
                                "03":{text:"“避税天堂”的国家或地区"},
                                "04":{text:"国家有关部门发布的制裁、禁运的国家和地区，或支持恐怖活动的国家和地区"},
                                "05":{text:"联合国、其他国际组织采取制裁措施的国家和地区"},
                                "06":{text:"金融行动特别工作组（FATF）发布的“不合作国家与地区”"},
                                "07":{text:"洗钱高风险离岸金融中心"}
                            };
                            var map=dataMap[data];
                            return map?statusTemplate(map):'';
                        }
                    },
                    {
                        targets: 9,
                        render:function(data, type, row, meta){
                            return moment(data, "YYYY-MM-DD HH:mm:ss.SSS").format('YYYY-MM-DD HH:mm:ss');
                        }
                    },
                    {
                        targets: 10,
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
            location.href = "${ctxPath}/abnorCountry/exportAbnorCountryExcel?" + $("form").serialize();
        });
    </script>
</div>
</html>