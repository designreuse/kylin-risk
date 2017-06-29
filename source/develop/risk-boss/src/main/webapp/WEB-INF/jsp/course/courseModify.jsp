<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>课程详细信息</title>
</head>
<body>
<form id="updCourseform" method="post" data-parsley-validate>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${course.id}"/>
    <input type="hidden" name="initialtype" id="initialtype" value="${course.courseType}"/>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content   float-e-margins">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">机构名称:</label>
                    <div class="col-sm-3 m-b">
                        <select data-placeholder="Choose a Merchant..." id="merchantId" name="merchantId"
                                class="chosen-select" style="width:308px;" tabindex="1">
                            <c:forEach items="${merchants}" var="merchant">
                                <option value="${merchant.merchantid}"
                                        <c:if test="${course.merchantId==merchant.merchantid}">selected</c:if>>${merchant.merchantname}</option>
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
                        <input class="form-control " type="text" id="courseName" name="courseName"
                               value="${course.courseName}" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">课程单价:</label>
                    <div class="col-sm-3 m-b">
                        <input class="form-control " type="text" id="coursePrice" name="coursePrice"
                               value="${course.coursePrice}" required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">适用年龄:</label>
                    <div class="col-sm-3 m-b">
                        <dict:select dictcode="stuage" id="stuAge" name="stuAge" nullLabel="---请选择---" nullOption="true"
                                     value="${course.stuAge}" cssClass="form-control"></dict:select>
                    </div>
                </div>
            </div>
            <div class="row query-div">
                <input type="button" id="submitModify" class="btn btn-w-m btn-success" value="修改">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
            </div>
        </div>
    </div>
</form>
</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        $(document).ready(function () {
            addCourseCategory($("#courseType"), $("#initialtype").val());
        });

        function addCourseCategory(factorSelect, initype) {
            factorSelect.append("<option value=''>---请选择---</option>");
            $.getJSON("${ctxPath}/courseType/queryCourseType", function (json) {
                $.each(json.data, function (index, factors) {
                    if (initype == factors['coursetypename']) {
                        factorSelect.append('<option value="' + factors['id'] + '" selected="selected">' + factors['coursetypename'] + '</option>');
                    } else {
                        factorSelect.append('<option value="' + factors['id'] + '">' + factors['coursetypename'] + '</option>');
                    }
                });

            });
        }

        $(function () {
            $("#back").click(function () {
                window.location.href = "${ctxPath}/course/toQueryCourse";
            });
        });
        $(function () {
            var initparsley = $("#updCourseform").parsley();
            $("#submitModify").click(function () {
                var courseType = $("#courseType").find("option:selected").text();
                var merchantName = $("#merchantId").find("option:selected").text();

                $("#updCourseform").ajaxSubmit({
                    type: 'post',
                    data: {courseType: courseType,merchantName: merchantName},
                    dataType: 'json',
                    beforeSubmit: function () {
                        return initparsley.validate();
                    },
                    url: '${ctxPath}/api/1/course/updateCourse',
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("修改成功！", function () {
                                window.location.href = "${ctxPath}/course/toQueryCourse?_initQuery=true";
                            });
                        } else {
                            bootbox.alert("修改失败: " + data.message);

                        }
                    }
                });
            });
        });
    </script>
</div>
</html>

