<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<html>
<head>
  <title>国家代码详细信息</title>
</head>
<body>
<form  id="updForm" method="post" data-parsley-validate>
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <input type="hidden" name="id" value="${abnormalCountrycode.id}"/>
  <div class="wrapper wrapper-content animated fadeInRight ecommerce">
    <div class="ibox-content m-b-sm border-bottom">
      <div class="row">
        <table class="table">
          <tr>
            <th>中文全称</th>
            <td><input type="text" id="cnnm" name="cnnm" value="${abnormalCountrycode.cnnm}" required></td>
          </tr>
          <tr>
            <th>中文简称</th>
            <td>
              <input type="text" id="cnabb" name="cnabb" value="${abnormalCountrycode.cnabb}" required>
            </td>
          </tr>
          <tr>
            <th>英文全称</th>
            <td><input type="text" id="ennm" name="ennm" value="${abnormalCountrycode.ennm}" required></td>
          </tr>
          <tr>
            <th>英文简称</th>
            <td>
              <input type="text" id="enabb" name="enabb" value="${abnormalCountrycode.enabb}" required>
            </td>
          </tr>
          <tr>
            <th>英文代码（两位）</th>
            <td><input type="text" id="twoletter" name="twoletter" value="${abnormalCountrycode.twoletter}" required></td>
          </tr>
          <tr>
            <th>英文代码（三位）</th>
            <td>
              <input type="text" id="threelteeer" name="threelteeer" value="${abnormalCountrycode.threelteeer}" required>
            </td>
          </tr>
          <tr>
            <th>数字代码</th>
            <td>
              <input type="text" id="threenum" name="threenum" value="${abnormalCountrycode.threenum}" required>
            </td>
          </tr>
          <tr>
            <th>所属反洗钱组织名单</th>
            <td>
              <select id="type" name="type" required>
                <option>---请选择---</option>
                <option value="00" <c:if test="${abnormalCountrycode.type=='00'}">selected</c:if>>FATF等反洗钱组织成员国</option>
                <option value="01" <c:if test="${abnormalCountrycode.type=='01'}">selected</c:if>>被FinCEN、FATF等国际反洗钱组织、美国海外资产控制办公室（OFAC）列入制裁名单的国家或地区</option>
                <option value="02" <c:if test="${abnormalCountrycode.type=='02'}">selected</c:if>>贩毒、恐怖活动或犯罪活动猖獗国家或地区</option>
                <option value="03" <c:if test="${abnormalCountrycode.type=='03'}">selected</c:if>>“避税天堂”的国家或地区</option>
                <option value="04" <c:if test="${abnormalCountrycode.type=='04'}">selected</c:if>>国家有关部门发布的制裁、禁运的国家和地区，或支持恐怖活动的国家和地区</option>
                <option value="05" <c:if test="${abnormalCountrycode.type=='05'}">selected</c:if>>联合国、其他国际组织采取制裁措施的国家和地区</option>
                <option value="06" <c:if test="${abnormalCountrycode.type=='06'}">selected</c:if>>金融行动特别工作组（FATF）发布的“不合作国家与地区”</option>
                <option value="07" <c:if test="${abnormalCountrycode.type=='07'}">selected</c:if>>洗钱高风险离岸金融中心</option>
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
          url: '${ctxPath}/api/1/abnorCountry/updateAbnorCountry',
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
