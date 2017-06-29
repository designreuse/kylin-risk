<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>课程类型修改</title>
</head>
<body>
<form id="modifyCourseTypeform" method="post" data-parsley-validate class="form-horizontal">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="id" value="${courseType.id}"/>
    <div class="wrapper wrapper-content animated fadeInRight ecommerce">
        <div class="ibox-content   float-e-margins">
            <div class="row">
                <div class="form-group">
                    <label class="col-sm-2 control-label ">课程类型名称:</label>
                    <div class="col-sm-3 m-b">
                        <input class="form-control " type="text" id="coursetypename"
                               name="coursetypename" value="${courseType.coursetypename}"
                               required="true"
                               data-parsley-length="[3, 45]"
                               data-parsley-length-message="长度大于3小于45"
                               data-parsley-remote
                               data-parsley-remote-validator="checktypename"
                               data-parsley-remote-message="输入的课程类型名称已存在">
                    </div>
                </div>
            </div>
            <div class="row query-div">
                <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
            </div>
        </div>
    </div>
    </div>
</form>
</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        var csrfToken = "${_csrf.token}";
        var csrfHeaderName = "${_csrf.headerName}";
        $(function () {
            $("#back").click(function () {
                window.location.href = "${ctxPath}/courseType/toQueryCourseType";
            });
        });
        $(function () {
            $("#submitAdd").click(function () {
                subCourseTypeAdd();
            });
	    
            window.Parsley.addAsyncValidator('checktypename', function (xhr) {
                        return xhr.responseJSON.data;
                    }, '${ctxPath}/api/1/courseType/checkTypeName'
            );

        });
        function subCourseTypeAdd() {
            if($("#modifyCourseTypeform").parsley().validate()){
                $("#modifyCourseTypeform").ajaxSubmit({
                    type: 'post',
                    dataType: 'json',
                    /*beforeSubmit: function () {
                        $("#modifyCourseTypeform").parsley().validate();
                     },*/
                    url: '${ctxPath}/api/1/courseType/updateCourseType',
                    success: function (data, textStatus, jqXHR) {
                        if (data.code == 200) {
                            bootbox.alert("保存成功！", function () {
                                window.location.href = "${ctxPath}/courseType/toQueryCourseType";
                            });
                        } else {
                            bootbox.alert("保存失败！");
                        }
                    }
                });
            }
        }
    </script>
</div>
</html>
