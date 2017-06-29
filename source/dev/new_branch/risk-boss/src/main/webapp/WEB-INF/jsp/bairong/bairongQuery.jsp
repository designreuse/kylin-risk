<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>百融查询</title>
</head>
<body >
<div id="wrapper" class="divContainer">
    <div class="wrapper wrapper-content animated fadeInRight backPageDiv">
        <div class="ibox-content m-b-sm border-bottom" id="divContent">
            <form role="form" id="bairongform"  data-table="queryTable"  data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >姓名</label>
                            <div class="col-sm-10">
                                <input  type="text" id="name" name="name"  placeholder="姓名"  class=" form-control" required="true">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >手机号</label>
                            <div class="col-sm-10">
                                <input  type="text" id="mobile" name="mobile"  placeholder="手机号"  class=" form-control" required="true"  data-parsley-mobile="#mobile">
                            </div>
                        </div>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >身份证号</label>
                            <div class="col-sm-10">
                                <input type="text" id="idNumber" name="idNumber"  placeholder="身份证号" class=" form-control" required="true">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" >查询百融</label>
                            <div class="col-sm-10">
                                <select id="queryAgain" name="queryAgain"  cssClass="form-control"  style="width:200px;"  required="true">
                                    <option value="false" selected>否</option>
                                    <option value="true" >是</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row query-div">
                    <button type="button" id="submitQuery" class="btn btn-w-m btn-success">查询</button>
                    <button type="reset" class="btn btn-w-m btn-info">重置</button>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox">
                            <div class="ibox-content">
                                <table id="queryTable" class="table table-striped table-bordered table-hover" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>渠道id</th>
                                        <th>姓名</th>
                                        <th>手机号</th>
                                        <th>身份证号</th>
                                        <th>特殊名单</th>
                                        <th>操作人</th>
                                        <th>操作时间</th>
                                    </tr>
                                    </thead>
                                     <tbody id="tableList">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script id="table-template" type="text/x-handlebars-template">
       {{#each this.data}}
       {{#list this}}
           <tr>
                 <td>{{channel}}</td>
                 <td>{{userName}}</td>
                 <td>{{mobile}}</td>
                 <td>{{idNumber}}</td>
                 <td>{{type result}}</td>
                 <td>{{querier}}</td>
                 <td>{{requestTime}}</td>
           </tr>
       {{/list}}
       {{/each}}
</script>


</body>
<div id="siteMeshJavaScript">
    <script type="text/javascript">
        var csrfToken = "${_csrf.token}";
        var csrfHeaderName = "${_csrf.headerName}";
        var tableTemplate =$("#table-template").html();
        var tableSource = Handlebars.compile(tableTemplate);


        $(function () {
              var initparsley = $("#bairongform").parsley();
                    $("#submitQuery").click(function () {
                        var queryAgain= $("#queryAgain").val();

                        if(initparsley.validate()){
                        var idNumber= $("#idNumber").val();
                        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                        if(reg.test(idNumber) == false)
                        {
                            bootbox.alert("身份证输入不合法");
                            return  false;
                        }

                    $("#bairongform").ajaxSubmit({
                        type: 'post',
                        dataType: 'json',
                        data: {queryAgain: queryAgain},
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(csrfHeaderName, csrfToken);
                        },
                        url: '${ctxPath}/api/1/bairong/queryBaiRong',
                        success: function (msg, textStatus, jqXHR) {
                            //模拟的json对象
                            var jsonData = $.parseJSON(msg.data);
                            if(msg.code == 200) {
                                if(jsonData.code == 200) {
                                    $('#tableList').html(tableSource(jsonData));
                                }else{
                                    bootbox.alert("查询失败！");
                                }
                            }else{
                                bootbox.alert("查询失败！");
                            }
                        }
                    });
                      }
            });
        });
                //判断风险得分分类
                Handlebars.registerHelper("type",function(value){
                    if(value==1){
                        return "是";
                    }else {
                        return "否";
                    }
                });


                Handlebars.registerHelper("list",function(data,options){
                    var result=data["result"];
                    var jsonResult = $.parseJSON(result);
                    delete data["result"];
                    var ret="";
                    var dataStr= jsonResult["flag_specialList_c"];
                    data.result=dataStr;
                    ret=options.fn(data);
                    return ret;
                });



    </script>

</div>
</html>
