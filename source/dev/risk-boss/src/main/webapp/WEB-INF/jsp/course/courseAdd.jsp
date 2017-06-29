<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>添加课程</title>
</head>
<body>
<form id="addCourseform" method="post" data-parsley-validate>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
        <div class="ibox-content   float-e-margins">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">机构名称:</label>
                    <div class="col-sm-3 m-b">
                        <select data-placeholder="Choose a Merchant..." id="merchantId" name="merchantId"
                                class="chosen-select" style="width:308px;" tabindex="1">
                            <c:forEach items="${merchants}" var="merchant">
                                <option value="${merchant.merchantid}">${merchant.merchantname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">课程类别:</label>
                    <div class="col-sm-3 m-b">
                        <select id="courseType" name="courseTypeId" class="form-control" required></select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">课程名称:</label>
                    <div class="col-sm-3 m-b">
                        <input class="form-control " type="text" id="courseName" name="courseName" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">课程单价:</label>
                    <div class="col-sm-3 m-b">
                        <input class="form-control " type="text" id="coursePrice" name="coursePrice" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">适用年龄:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="stuage" id="stuAge" name="stuAge" nullLabel="---请选择---" nullOption="true"
                                     cssClass="form-control" ></dict:select>
                    </div>
                </div>
            </div>
            <div class="row query-div">
                <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="添加">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
            </div>
        </div>
    </div>
</form>
</body>


<div id="siteMeshJavaScript">
    <script type="text/javascript">
        var csrfToken = "${_csrf.token}";
        var csrfHeaderName = "${_csrf.headerName}";

        $(document).ready(function () {
            addCourseCategory($("#courseType"));
        });


        function addCourseCategory(factorSelect) {
            factorSelect.append("<option value=''>---请选择---</option>");
            $.getJSON("${ctxPath}/courseType/queryCourseType", function (json) {
                $.each(json.data, function (index, types) {
                    factorSelect.append('<option value="' + types['id'] + '">' + types['coursetypename'] + '</option>');
                });

            });
        }


        $(function () {
            $("#back").backup();
        });

        $(function () {
            var initparsley = $("#addCourseform").parsley();
            $("#submitAdd").click(function () {
                var courseType = $("#courseType").find("option:selected").text();
                var merchantName = $("#merchantId").find("option:selected").text();

                $("#addCourseform").ajaxSubmit({
                    type: 'post',
                    data: {courseType: courseType,merchantName: merchantName},
                    dataType: 'json',
                    beforeSubmit: function () {
                        return initparsley.validate();

                    },
                    url: '${ctxPath}/api/1/course/addCourse',
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("添加成功！", function () {
                                $.backquery();
                            });
                        } else {
                            bootbox.alert("添加失败: " + data.message);
                        }
                    }
                });
            });
        });
    </script>
</div>
</html>
