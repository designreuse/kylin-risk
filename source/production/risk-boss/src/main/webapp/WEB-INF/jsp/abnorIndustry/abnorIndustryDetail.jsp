<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>异常行业代码详细信息</title>
</head>
<body>
<form id="updForm" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="id" value="${abnorIndustry.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <table class="table">
          <tr>
            <th>异常行业名称</th>
            <td><input type="text" id="codenm" name="codenm" value="${abnorIndustry.codenm}" required></td>
          </tr>
          <tr>
            <th>异常行业代码</th>
            <td>
              <input type="text" id="codeid" name="codeid" value="${abnorIndustry.codeid}" required>
            </td>
          </tr>
          <tr>
            <th>所属反洗钱行业类型</th>
            <td>
              <select id="type" name="type" required>
                <option>--请选择--</option>
                <option value="00" <c:if test="${abnorIndustry.type=='00'}">selected</c:if>>赌场或其他赌博机构</option>
                <option value="01" <c:if test="${abnorIndustry.type=='01'}">selected</c:if>>彩票销售行业构</option>
                <option value="02" <c:if test="${abnorIndustry.type=='02'}">selected</c:if>>贵金属、稀有矿石或珠宝行业</option>
                <option value="03" <c:if test="${abnorIndustry.type=='03'}">selected</c:if>>与武器有关的行业</option>
                <option value="04" <c:if test="${abnorIndustry.type=='04'}">selected</c:if>>珠宝、证券、基金（待确认）、典当、房产、娱乐场所、奢侈品、游戏、点卡、视频、积分换购</option>
              </select>
            </td>
          </tr>
        </table>
        <center>
          <c:if test="${dealType=='modify'}">
            <input type="button" id="submitModify" class="btn btn-w-m btn-success" value="提交修改">&nbsp;&nbsp;&nbsp;&nbsp;
          </c:if>
          <input type="button" id="back" class="btn btn-w-m btn-default" value="返回">
        </center>
      </div>
    </div>
  </div>
</form>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    $(function(){
      $("#back").backup();
    });
    $(function(){
      var initparsley=$("#updForm").parsley();
      $("#submitModify").click(function(){
        $("#updForm").ajaxSubmit({
          type:'post',
          dataType: 'json',
          beforeSubmit:function(){
            return initparsley.validate();
          },
          url: '${ctxPath}/api/1/abnorIndustry/updateAbnorIndustry',
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
      });
    });
  </script>
</div>
</html>
