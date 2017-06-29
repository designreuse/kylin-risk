<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
    <title>业务流程修改</title>
</head>
<body>
<form id="modifyChannelFlowform" method="post" data-parsley-validate class="form-horizontal">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden"  name="id" value="${channelFlow.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content   float-e-margins">
        <div class="row">
            <div class="form-channelFlow">
                <label class="col-sm-2 control-label">渠道:</label>
                <div class="col-sm-3 m-b">
                    <input class="form-control "  type="text" name="channelname" value="${channelFlow.channelname}" readonly>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-channelFlow">
                <label class="col-sm-2 control-label">产品:</label>
                <div class="col-sm-3 m-b">
                    <input class="form-control "  type="text" name="productname" value="${channelFlow.productname}"readonly>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-channelFlow">
                <label class="col-sm-2 control-label">个人信息流程:</label>
                <div class="col-sm-7">
                    <div class="panel panel-default panel-body" id="channel">
                        <input id="crawler" name="crawler" type="checkbox" <c:if test="${channelFlow.crawler=='00'}">checked="checked"</c:if>>爬虫
                        <input id="idcardvalidate" name="idcardvalidate" type="checkbox" <c:if test="${channelFlow.idcardvalidate=='00'}">checked="checked"</c:if>>身份证验证
                        <input id="bankcardvalidate" name="bankcardvalidate" type="checkbox" <c:if test="${channelFlow.bankcardvalidate=='00'}">checked="checked"</c:if>>银行卡验证
                        <input id="idcardhandvalidate" name="idcardhandvalidate" type="checkbox" <c:if test="${channelFlow.idcardhandvalidate=='00'}">checked="checked"</c:if>>手持身份证验证
                        <input id="mitou" name="mitou" type="checkbox" <c:if test="${channelFlow.mitou=='00'}">checked="checked"</c:if>>米投
                        <input id="bairongeducational" name="bairongeducational" type="checkbox" <c:if test="${channelFlow.bairongeducational=='00'}">checked="checked"</c:if>>百融学历
                        <input id="bairongspecial" name="bairongspecial" type="checkbox" <c:if test="${channelFlow.bairongspecial=='00'}">checked="checked"</c:if>>百融特殊名单
                        <input id="bairongquery" name="bairongquery" type="checkbox" <c:if test="${channelFlow.bairongquery=='00'}">checked="checked"</c:if>>百融多次查询
                        <input id="unionpayadvisors" name="unionpayadvisors" type="checkbox" <c:if test="${channelFlow.unionpayadvisors=='00'}">checked="checked"</c:if>>银联智策
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-channelFlow">
                <div class="row query-div">
                    <input type="button" id="submitAdd" class="btn btn-w-m btn-success" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" id="back" class="btn btn-w-m btn-default" value="取消">
                </div>
            </div>
        </div>
    </div>
  </div>
</form>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
      var csrfToken="${_csrf.token}";
      var csrfHeaderName="${_csrf.headerName}";
      $(function(){
          $("#submitAdd").click(function(){
              if($("#channel input[type='checkbox']:checked").length < 1){
                  bootbox.alert("请选择个人信息流程！");
                  return ;
              }
              subRuleAdd();
          });
          $("#back").backup();
      });

      function subRuleAdd(){
          $("#channel input[type='checkbox']").each(function(i){
              if($(this).attr("checked")) {
                  $(this).val("00");
              }else{
                  $(this).attr("checked","checked");
                  $(this).val("01");
              }
          });
          $("#modifyChannelFlowform").ajaxSubmit({
              type:'post',
              dataType: 'json',
              url: '${ctxPath}/api/1/channelFlow/updateChannelFlow',
              success: function(data,textStatus,jqXHR){
                  if(data.code==200){
                      bootbox.alert("修改成功！", function () {
                          $.backquery();
                      });
                  }else{
                      bootbox.alert("修改失败！");
                  }
              }
          });
      }
  </script>
</div>
</html>
