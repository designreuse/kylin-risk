<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>企业风控风险等级详细信息</title>
</head>
<body>
<div id="wrapper">
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="row">
      <div class="col-lg-12">
        <div class="ibox">
          <div class="ibox-title">
            <h5>企业风控风险等级详细信息</h5>
          </div>
          <form class="form-inline">
            <div class="panel panel-primary" >
              <div class="panel-heading">基本信息</div>
              <div class="panel-body">

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="id" id="id" value="${grade.id}"/>
                <input type="hidden" name="operType" id="operType" value="${operType}"/>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="customerid">企业编号：</label>
                      <p class="form-control-static" id="customerid">${merc.merchantid}</p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="channelid">渠道ID：</label>
                      <p class="form-control-static" id="channelid">${merc.channelid}</p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="customername">企业名称：</label>
                      <p class="form-control-static" id="customername">${merc.merchantname}</p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="customershortname">企业简称：</label>
                      <p class="form-control-static" id="customershortname"></p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="carttype">法人证件类型：</label>
                      <p class="form-control-static" id="carttype">${merc.ownercerttp}</p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="cardno">法人证件号码：</label>
                      <p class="form-control-static" id="cardno">${merc.ownercertid}</p>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="bizaddr">业务地址：</label>
                      <p class="form-control-static" id="bizaddr"></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="regcapital">注册资金：</label>
                      <p class="form-control-static" id="regcapital"></p>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="bizscope">业务范围：</label>
                      <p class="form-control-static" id="bizscope"></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="merchantstatus">企业状态：</label>
                      <p class="form-control-static" id="merchantstatus">
                        <dict:write dictcode="status" code="${merc.merchantstatus}"></dict:write>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="panel panel-primary">
              <div class="panel-heading">评级信息</div>
              <div class="panel-body">
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="grade">系统等级：</label>
                      <p class="form-control-static" id="grade"><dict:select  readonly="readonly" dictcode="risklevel" id="customertype" name="customertype" nullLabel="请选择" nullOption="true" cssClass="form-control" value="${grade.checkgrade}"></dict:select></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="createtime">创建时间：</label>
                      <p class="form-control-static" id="createtime"><r:formateDateTime value="${grade.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="lastgrade">上次评定等级：</label>
                      <p class="form-control-static" id="lastgrade"><dict:select readonly="readonly" dictcode="risklevel" id="customertype" name="customertype" nullLabel="请选择" nullOption="true" cssClass="form-control" value="${grade.lastgrade}"></dict:select></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="lasttime">上次评定日期：</label>
                      <p class="form-control-static" id="lasttime"><r:formateDateTime value="${grade.lasttime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="checkgrade">待审核等级：</label>
                      <p class="form-control-static" id="checkgrade"><dict:select  readonly="readonly" dictcode="risklevel" id="customertype" name="customertype" nullLabel="请选择" nullOption="true" cssClass="form-control" value="${grade.checkgrade}"></dict:select></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="committime">提交时间：</label>
                      <p class="form-control-static" id="committime"><r:formateDateTime value="${grade.committime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12">
                    <div class="form-group">
                      <label for="commitname">提交人：</label>
                      <p class="form-control-static" id="commitname">${grade.commitname}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="panel panel-primary">
              <div class="panel-heading">通讯信息</div>
              <div class="panel-body">
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="postno">邮政编码：</label>
                      <p class="form-control-static" id="postno"></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="conaddress">通讯地址：</label>
                      <p class="form-control-static" id="conaddress"></p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="contype">联系方式：</label>
                      <p class="form-control-static" id="contype"></p>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="phone">手机号码：</label>
                      <p class="form-control-static" id="phone"></p>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-12">
                    <div class="form-group">
                      <label for="email">电子邮箱：</label>
                      <p class="form-control-static" id="email"></p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="panel panel-primary">
              <div class="panel-heading">更改评级等级原因</div>
              <div class="panel-body">
                <div class="row">
                  <div class="col-sm-12">
                    <div class="form-group">
                      <label for="updateresion">更改评级等级原因：</label>
                      <p class="form-control-static" id="updateresion">${grade.updatereason}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row query-div">
              <c:if test="${operType=='COMMIT'&&grade.status!='05'}">
                <button type="button" id="setLow1" class="btn btn-w-m btn-primary">设为低风险</button>
                <button type="button" id="setMiddle1" class="btn btn-w-m btn-warning">设为中风险</button>
                <button type="button" id="setHigh1" class="btn btn-w-m btn-danger">设为高风险</button>
              </c:if>
              <c:if test="${operType=='CHECK'&&grade.status=='05'}">
                <button type="button" id="setAgree1" class="btn btn-w-m btn-primary">审核通过</button>
                <button type="button" id="setDisagree1" class="btn btn-w-m btn-warning">审核拒绝</button>
              </c:if>
              <button type="button" id="back" class="btn btn-w-m btn-default">返回</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript">
    var csrfToken = "${_csrf.token}";
    var csrfHeaderName = "${_csrf.headerName}"
    $(function(){
      $("#back").backup();
      $("#setLow1").click(function () {
        setRiskGrade1("LOW");
      });
      $("#setMiddle1").click(function () {
        setRiskGrade1("MIDDLE");
      });
      $("#setHigh1").click(function () {
        setRiskGrade1("HIGH");
      });
      $("#setAgree1").click(function () {
        checkRiskGrade1("AGREE");
      });
      $("#setDisagree1").click(function () {
        checkRiskGrade1("DISAGREE");
      });
    });

    function checkRiskGrade1(status){
      var selectId = $("#id").val();
      $.ajax( {
        type : "POST",
        beforeSend: function(xhr) {
          xhr.setRequestHeader(csrfHeaderName, csrfToken);
        },
        url: "${ctxPath}/api/1/amlGradeMerc/updateMercAmlGradeStatus",
        data : "ids="+selectId+"&status="+status,
        dataType: "json",
        success : function(data, textStatus, jqXHR) {
          if(data.code==200){
            bootbox.alert("审核成功！", function() {
              $.backquery();
            });
          }else{
            bootbox.alert("审核失败！"+data.message);
          }
        }
      });
    }

    function setRiskGrade1(grade){
      var selectId = $("#id").val();
      bootbox.prompt("请输入更改评级等级原因", function(result) {
        $.ajax( {
          type : "POST",
          beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfToken);
          },
          url: "${ctxPath}/api/1/amlGradeMerc/updateMercAmlGrade",
          data : "ids="+selectId+"&reason="+result+"&updateGrade="+grade,
          dataType: "json",
          success : function(data, textStatus, jqXHR) {
            if(data.code==200){
              bootbox.alert("设置成功！", function() {
                $.backquery();
              });
            }else{
              bootbox.alert("设置失败！"+data.message);
            }
          }
        });
      });
    }
  </script>
</div>
</html>
