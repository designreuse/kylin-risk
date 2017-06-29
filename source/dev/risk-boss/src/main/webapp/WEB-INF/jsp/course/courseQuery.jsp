<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>课程管理查询</title>
</head>
<body >
<div id="wrapper" class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
        <div class="ibox-content m-b-sm border-bottom" id="divContent">
            <form role="form" id="courseform"  action="${ctxPath}/course/queryCourse"  data-table="queryTable">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="initialtype" id="initialtype" />
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >机构名称</label>
                            <div class="col-sm-10">
                                <input  type="text" id="merchantname" name="merchantName"  placeholder="机构名称"  class=" form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >课程类型</label>
                            <div class="col-sm-10">
                                <select name="courseType" id="coursetype"  class="form-control"></select>
                            </div>
                        </div>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >课程名称</label>
                            <div class="col-sm-10">
                                <input type="text" id="coursename" name="courseName"  placeholder="课程名称" class=" form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row query-div">
                    <a href="toAjaxAddCourse" class="ajaxback-requst">
                        <button type="button" id="toAddCourse" class="btn btn-w-m btn-primary">添加</button>
                    </a>
                    <button type="submit" class="btn btn-w-m btn-success">查询</button>
                    <button type="reset" class="btn btn-w-m btn-info">重置</button>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content">
                                <a href="#">
                                    <button type="button"  id ="deleteSelected" class="btn-white btn btn-xs">
                                        <i class="fa fa-trash-o"></i>&nbsp;&nbsp;<strong>删除</strong></button>
                                </a>
                                <br>
                                <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" name='choosetotal' onclick='setChecked()'>全选</th>
                                        <th>机构名称</th>
                                        <th>课程类型</th>
                                        <th>课程名称</th>
                                        <th>课程单价</th>
                                        <th>适用年龄</th>
                                        <th>最后修改日期</th>
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
        var operTemplate = Handlebars.compile(operTpl);
        var statusSource   = $("#status-tpl").html();
        var statusTemplate = Handlebars.compile(statusSource);


        $(document).ready(function(){
            $.ajaxBack();
        });


        function addCourseCategory(factorSelect,initype,asyncFlag){
            factorSelect.append("<option value=''>---请选择---</option>");
            $.ajaxSettings.async = asyncFlag;
            $.getJSON("${ctxPath}/courseType/queryCourseType",function(json){
                $.each(json.data, function(index,types){
                    if (initype == types['coursetypename']) {
                        factorSelect.append('<option value="' + types['coursetypename']+ '" selected="selected">' + types['coursetypename'] + '</option>');
                    }else{
                        factorSelect.append('<option value="' + types['coursetypename']  + '">' + types['coursetypename'] + '</option>');
                    }
                });
            });
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

        $("#deleteSelected").click(function(){
            var selectIds = getSelectedId();
            if (selectIds!="") {
                deleteCourse(selectIds);
            }
        });

        function getSelectedId(){
            var selectIds = "";
            var result = $("input[name='choose']:checked");
            if (result.length == 0) {
                alert("请选择数据");
            } else {
                for (var i = 0; i < result.length; i++) {
                    selectIds = selectIds + result[i].value + ",";
                }
                selectIds = selectIds.substr(0, selectIds.length - 1);

            }
            return selectIds;
        }

        function getDetail(id){
            $.ajaxParamBack({"url":"${ctxPath}/course/toAjaxCourseDetail","data":"id="+id});
        }

        function deleteCourse(ids){
            if (confirm("确定删除？")) {
                pushPageState();
                $.ajax({
                    type: "POST",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(csrfHeaderName, csrfToken);
                    },
                    url: "${ctxPath}/api/1/course/deleteCourse",
                    data: "id=" + ids,
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                        if (data.code==200) {
                            bootbox.alert("删除成功！",function(){
                              //  window.location.href="${ctxPath}/course/toQueryCourse?_initQuery=true";
                                history.back();
                            });
                        } else {
                            bootbox.alert("删除失败！"),function(){
                                window.location.href="${ctxPath}/course/toQueryCourse?_initQuery=true";
                            }
                        }
                    }
                });
            }
        }
        //绑定form的查询到tables
        $(function(){
            $('form#courseform').queryTables({
                "pageLength":10,
                "serverSide": true,
                iDisplayStart:0,
                columns: [
                    {"data": "id"},
                    {"data": "merchantName"},
                    {"data": "courseType"},
                    {"data": "courseName"},
                    {"data": "coursePrice"},
                    {"data": "stuAge"},
                    {"data": "updatedTime"},
                    {"data": null}
                ],
                columnDefs:[
                    {
                        targets:0,
                        render:function(data, type, row, meta){
                            return checkBoxTemplate({id:data});
                        }
                    },
                    {
                        targets:[1,3,5],
                        render:function(data, type, row, meta){
                            if(data==null){
                                return "";
                            }else{
                                return data;
                            }
                        }
                    },

                    {
                        targets: 6,
                        render:function(data, type, row, meta){
                            return moment(data).format('YYYY-MM-DD HH:mm:ss');
                        }
                    },
                    {
                        targets: 7,
                        render: function (data, type, row, meta) {
                            var context =
                            {
                                func: [
                                    {"text": "编辑", "fn": "getDetail("+row.id+")", "cssClass": "fa-wrench"},
                                    {"text": "删除", "fn": "deleteCourse("+row.id+")", "cssClass": "fa-pencil"}

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
