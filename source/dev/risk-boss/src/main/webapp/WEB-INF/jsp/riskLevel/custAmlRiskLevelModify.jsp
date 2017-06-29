<%--suppress ALL --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
  <title>客户反洗钱风险评级设置</title>
</head>
<body>
<div id="wrapper">
  <div  class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-lg-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>客户反洗钱风险评级设置<small>.</small></h5>
            </div>
            <div class="ibox-content">
              <form id="custRiskLevel" method="post" action="${ctxPath}/amlriskLevel/updateAmlLevelCustom" class="form-inline" data-parsley-validate>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden"  id="message" name="message" value="${message}">
                <input type="hidden"  id="operType" name="operType" value="${operType}">
                <div align="center">
                  <table class="table table-striped table-bordered">
                    <thead>
                    <th>指标</th>
                    <th>权重</th>
                    <th>评定项目</th>
                    <th>记分分类</th>
                    <th>分类评分</th>
                    <th CHECK="Y">待审核分类评分</th>
                    <th CHECK="N">计算分值</th>
                    <th>备注</th>
                    </thead>
                    <tbody>
                    <tr>
                      <td rowspan="9">国籍或地区</td>
                      <td rowspan="9"><span>10</span></td>
                      <td rowspan="9">危险国家</td>
                      <td>FATF等反洗钱组织成员国</td>
                      <td><input type="text" class="form-control" name="levels[0].score" value="${levels[0].score}" data-parsley-type="integer" required max="100" id="score0"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[0].checkscore" value="${levels[0].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[0].account" value="${levels[0].account}"  id="account0" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[0].weight" value="10" id="weight0">
                        <input type="text" class="form-control" name="levels[0].remark" value="${levels[0].remark}">
                        <input type="hidden" name="levels[0].id" value="${levels[0].id}">
                        <input type="hidden" name="levels[0].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[0].project" value="dangerCountry">
                        <input type="hidden" name="levels[0].classscore" value="FATF">
                        <input type="hidden" name="levels[0].classdesc" value="FATF等反洗钱组织成员国">
                      </td>
                    </tr>
                    <tr>
                      <td>被FinCEN、FATF等国际反洗钱组织、美国海外资产控制办公室（OFAC）列入制裁名单的国家或地区</td>
                      <td><input type="text" class="form-control" name="levels[1].score" value="${levels[1].score}" data-parsley-type="integer" required max="100" id="score1"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[1].checkscore" value="${levels[1].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[1].account" value="${levels[1].account}"  id="account1" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[1].weight" value="10" id="weight1">
                        <input type="text" class="form-control" name="levels[1].remark" value="${levels[1].remark}">
                        <input type="hidden" name="levels[1].id" value="${levels[1].id}">
                        <input type="hidden" name="levels[1].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[1].project" value="dangerCountry">
                        <input type="hidden" name="levels[1].classscore" value="FinCEN、FATF、OFAC">
                        <input type="hidden" name="levels[1].classdesc" value="被FinCEN、FATF等国际反洗钱组织、美国海外资产控制办公室（OFAC）列入制裁名单的国家或地区">
                      </td>
                    </tr>
                    <tr>
                      <td>贩毒、恐怖活动或犯罪活动猖獗国家或地区</td>
                      <td><input type="text" class="form-control" name="levels[2].score" value="${levels[2].score}" data-parsley-type="integer" required max="100" id="score2"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[2].checkscore" value="${levels[2].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[2].account" value="${levels[2].account}"  id="account2" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[2].weight" value="10" id="weight2">
                        <input type="text" class="form-control" name="levels[2].remark" value="${levels[2].remark}">
                        <input type="hidden" name="levels[2].id" value="${levels[2].id}">
                        <input type="hidden" name="levels[2].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[2].project" value="dangerCountry">
                        <input type="hidden" name="levels[2].classscore" value="dragAndTerri">
                        <input type="hidden" name="levels[2].classdesc" value="贩毒、恐怖活动或犯罪活动猖獗国家或地区">
                      </td>
                    </tr>
                    <tr>
                      <td>“避税天堂”的国家或地区</td>
                      <td><input type="text" class="form-control" name="levels[3].score" value="${levels[3].score}" data-parsley-type="integer" required max="100" id="score3"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[3].checkscore" value="${levels[3].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[3].account" value="${levels[3].account}"  id="account3" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[3].weight" value="10" id="weight3">
                        <input type="text" class="form-control" name="levels[3].remark" value="${levels[3].remark}">
                        <input type="hidden" name="levels[3].id" value="${levels[3].id}">
                        <input type="hidden" name="levels[3].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[3].project" value="dangerCountry">
                        <input type="hidden" name="levels[3].classscore" value="tax-havens">
                        <input type="hidden" name="levels[3].classdesc" value="“避税天堂”的国家或地区">
                      </td>
                    </tr>

                    <tr>
                      <td>国家有关部门发布的制裁、禁运的国家和地区，或支持恐怖活动的国家和地区</td>
                      <td><input type="text" class="form-control" name="levels[4].score" value="${levels[4].score}" data-parsley-type="integer" required max="100" id="score4"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[4].checkscore" value="${levels[4].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[4].account" value="${levels[4].account}"  id="account4" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[4].weight" value="10" id="weight4">
                        <input type="text" class="form-control" name="levels[4].remark" value="${levels[4].remark}">
                        <input type="hidden" name="levels[4].id" value="${levels[4].id}">
                        <input type="hidden" name="levels[4].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[4].project" value="dangerCountry">
                        <input type="hidden" name="levels[4].classscore" value="Noentry-country">
                        <input type="hidden" name="levels[4].classdesc" value="国家有关部门发布的制裁、禁运的国家和地区，或支持恐怖活动的国家和地区">
                      </td>
                    </tr>

                    <tr>
                      <td>联合国、其他国际组织采取制裁措施的国家和地区</td>
                      <td><input type="text" class="form-control" name="levels[5].score" value="${levels[5].score}" data-parsley-type="integer" required max="100" id="score5"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[5].checkscore" value="${levels[5].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[5].account" value="${levels[5].account}"  id="account5" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[5].weight" value="10" id="weight5">
                        <input type="text" class="form-control" name="levels[5].remark" value="${levels[5].remark}">
                        <input type="hidden" name="levels[5].id" value="${levels[5].id}">
                        <input type="hidden" name="levels[5].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[5].project" value="dangerCountry">
                        <input type="hidden" name="levels[5].classscore" value="UN-sactions">
                        <input type="hidden" name="levels[5].classdesc" value="联合国、其他国际组织采取制裁措施的国家和地区">
                      </td>
                    </tr>

                    <tr>
                      <td>金融行动特别工作组（FATF）发布的“不合作国家与地区”</td>
                      <td><input type="text" class="form-control" name="levels[6].score" value="${levels[6].score}" data-parsley-type="integer" required max="100" id="score6"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[6].checkscore" value="${levels[6].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[6].account" value="${levels[6].account}"  id="account6" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[6].weight" value="10" id="weight6">
                        <input type="text" class="form-control" name="levels[6].remark" value="${levels[6].remark}">
                        <input type="hidden" name="levels[6].id" value="${levels[6].id}">
                        <input type="hidden" name="levels[6].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[6].project" value="dangerCountry">
                        <input type="hidden" name="levels[6].classscore" value="NO-Cooperation-country">
                        <input type="hidden" name="levels[6].classdesc" value="金融行动特别工作组（FATF）发布的“不合作国家与地区”">
                      </td>
                    </tr>

                    <tr>
                      <td>洗钱高风险离岸金融中心</td>
                      <td><input type="text" class="form-control" name="levels[7].score" value="${levels[7].score}" data-parsley-type="integer" required max="100" id="score7"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[7].checkscore" value="${levels[7].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[7].account" value="${levels[7].account}"  id="account7" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[7].weight" value="10" id="weight7">
                        <input type="text" class="form-control" name="levels[7].remark" value="${levels[7].remark}">
                        <input type="hidden" name="levels[7].id" value="${levels[7].id}">
                        <input type="hidden" name="levels[7].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[7].project" value="dangerCountry">
                        <input type="hidden" name="levels[7].classscore" value="ML-high-level">
                        <input type="hidden" name="levels[7].classdesc" value="洗钱高风险离岸金融中心">
                      </td>
                    </tr>

                    <tr>
                      <td>被监管机构确定为高风险的国家或地区</td>
                      <td><input type="text" class="form-control" name="levels[8].score" value="${levels[8].score}" data-parsley-type="integer" required max="100" id="score8"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[8].checkscore" value="${levels[8].checkscore}"  readonly  CHECK="Y">
                        <input type="text" class="form-control" name="levels[8].account" value="${levels[8].account}"  id="account8" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[8].weight" value="10" id="weight8">
                        <input type="text" class="form-control" name="levels[8].remark" value="${levels[8].remark}">
                        <input type="hidden" name="levels[8].id" value="${levels[8].id}">
                        <input type="hidden" name="levels[8].target" value="cuntryAndarea">
                        <input type="hidden" name="levels[8].project" value="dangerCountry">
                        <input type="hidden" name="levels[8].classscore" value="regulator-high-level">
                        <input type="hidden" name="levels[8].classdesc" value="被监管机构确定为高风险的国家或地区">
                      </td>
                    </tr>


                    <tr>
                      <td rowspan="5">行业</td>
                      <td rowspan="5"><span>10</span></td>
                      <td rowspan="5">危险行业</td>
                      <td>赌场或其他赌博机构</td>
                      <td><input type="text" class="form-control" name="levels[9].score" value="${levels[9].score}" data-parsley-type="integer" required max="100"  id="score9"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[9].checkscore" value="${levels[9].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[9].account" value="${levels[9].account}"  id="account9" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[9].weight" value="10" id="weight9">
                        <input type="text" class="form-control" name="levels[9].remark" value="${levels[2].remark}">
                        <input type="hidden" name="levels[9].id" value="${levels[9].id}">
                        <input type="hidden" name="levels[9].target" value="industry">
                        <input type="hidden" name="levels[9].project" value="dangerIndustry">
                        <input type="hidden" name="levels[9].classscore" value="gamble">
                        <input type="hidden" name="levels[9].classdesc" value="赌场或其他赌博机构">
                      </td>
                    </tr>

                   <tr>
                      <td>彩票销售行业</td>
                      <td><input type="text" class="form-control" name="levels[10].score" value="${levels[10].score}" data-parsley-type="integer" required max="100"  id="score10"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[10].checkscore" value="${levels[10].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[10].account" value="${levels[10].account}"  id="account10" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[10].weight" value="10" id="weight10">
                        <input type="text" class="form-control" name="levels[10].remark" value="${levels[10].remark}">
                        <input type="hidden" name="levels[10].id" value="${levels[10].id}">
                        <input type="hidden" name="levels[10].target" value="industry">
                        <input type="hidden" name="levels[10].project" value="dangerIndustry">
                        <input type="hidden" name="levels[10].classscore" value="lottery">
                        <input type="hidden" name="levels[10].classdesc" value="彩票销售行业">
                      </td>
                    </tr>

                    <tr>
                      <td>贵金属、稀有矿石或珠宝行业</td>
                      <td><input type="text" class="form-control" name="levels[11].score" value="${levels[11].score}" data-parsley-type="integer" required max="100"  id="score11"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[11].checkscore" value="${levels[11].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[11].account" value="${levels[11].account}"  id="account11" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[11].weight" value="10" id="weight11">
                        <input type="text" class="form-control" name="levels[11].remark" value="${levels[11].remark}">
                        <input type="hidden" name="levels[11].id" value="${levels[11].id}">
                        <input type="hidden" name="levels[11].target" value="industry">
                        <input type="hidden" name="levels[11].project" value="dangerIndustry">
                        <input type="hidden" name="levels[11].classscore" value="noble-metal-jewels">
                        <input type="hidden" name="levels[11].classdesc" value="贵金属、稀有矿石或珠宝行业">
                      </td>
                    </tr>

                    <tr>
                      <td>与武器有关的行业</td>
                      <td><input type="text" class="form-control" name="levels[12].score" value="${levels[12].score}" data-parsley-type="integer" required max="100"  id="score12"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[12].checkscore" value="${levels[12].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[12].account" value="${levels[12].account}"  id="account12" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[12].weight" value="10" id="weight12">
                        <input type="text" class="form-control" name="levels[12].remark" value="${levels[12].remark}">
                        <input type="hidden" name="levels[12].id" value="${levels[12].id}">
                        <input type="hidden" name="levels[12].target" value="industry">
                        <input type="hidden" name="levels[12].project" value="dangerIndustry">
                        <input type="hidden" name="levels[12].classscore" value="weapon">
                        <input type="hidden" name="levels[12].classdesc" value="与武器有关的行业">
                      </td>
                    </tr>

                    <tr>
                      <td>典当、房产、娱乐场所、奢侈品、游戏、点卡、视频、积分换购</td>
                      <td><input type="text" class="form-control" name="levels[13].score" value="${levels[13].score}" data-parsley-type="integer" required max="100"  id="score13"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[13].checkscore" value="${levels[13].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[13].account" value="${levels[13].account}"  id="account13" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[13].weight" value="10" id="weight13">
                        <input type="text" class="form-control" name="levels[13].remark" value="${levels[13].remark}">
                        <input type="hidden" name="levels[13].id" value="${levels[13].id}">
                        <input type="hidden" name="levels[13].target" value="industry">
                        <input type="hidden" name="levels[13].project" value="dangerIndustry">
                        <input type="hidden" name="levels[13].classscore" value="others">
                        <input type="hidden" name="levels[13].classdesc" value="典当、房产、娱乐场所、奢侈品、游戏、点卡、视频、积分换购">
                      </td>
                    </tr>


                    <tr>
                      <td rowspan="4">身份因素</td>
                      <td rowspan="4"><span>10</span></td>
                      <td rowspan="4">外国政要或危险身份</td>
                      <td>为外国政要、政党要员、司法和军事高级官员、客户的股东、董事或实际控制人为外国政要</td>
                      <td><input type="text" class="form-control" name="levels[14].score" value="${levels[14].score}" data-parsley-type="integer" required max="100"  id="score14"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[14].checkscore" value="${levels[14].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[14].account" value="${levels[14].account}"  id="account14" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[14].weight" value="10" id="weight14">
                        <input type="text" class="form-control" name="levels[14].remark" value="${levels[14].remark}">
                        <input type="hidden" name="levels[14].id" value="${levels[14].id}">
                        <input type="hidden" name="levels[14].target" value="identity">
                        <input type="hidden" name="levels[14].project" value="identity">
                        <input type="hidden" name="levels[14].classscore" value="political-VIP">
                        <input type="hidden" name="levels[14].classdesc" value="为外国政要、政党要员、司法和军事高级官员、客户的股东、董事或实际控制人为外国政要">
                      </td>
                    </tr>
                    <tr>
                      <td>为已经或正在接受行政或司法调查的客户</td>
                      <td><input type="text" class="form-control" name="levels[15].score" value="${levels[15].score}" data-parsley-type="integer" required max="100"  id="score15"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[15].checkscore" value="${levels[15].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[15].account" value="${levels[15].account}"  id="account15" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[15].weight" value="10" id="weight15">
                        <input type="text" class="form-control" name="levels[15].remark" value="${levels[15].remark}">
                        <input type="hidden" name="levels[15].id" value="${levels[15].id}">
                        <input type="hidden" name="levels[15].target" value="identity">
                        <input type="hidden" name="levels[15].project" value="identity">
                        <input type="hidden" name="levels[15].classscore" value="prison-customer">
                        <input type="hidden" name="levels[15].classdesc" value="为已经或正在接受行政或司法调查的客户">
                      </td>
                    </tr>
                    <tr>
                      <td>是否为洗钱风险较高的行业经营管理或工作人员</td>
                      <td><input type="text" class="form-control" name="levels[16].score" value="${levels[16].score}" data-parsley-type="integer" required max="100"  id="score16"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[16].checkscore" value="${levels[16].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[16].account" value="${levels[16].account}"  id="account16" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[16].weight" value="10" id="weight16">
                        <input type="text" class="form-control" name="levels[16].remark" value="${levels[16].remark}">
                        <input type="hidden" name="levels[16].id" value="${levels[16].id}">
                        <input type="hidden" name="levels[16].target" value="identity">
                        <input type="hidden" name="levels[16].project" value="identity">
                        <input type="hidden" name="levels[16].classscore" value="ML-highlevel-person">
                        <input type="hidden" name="levels[16].classdesc" value="是否为洗钱风险较高的行业经营管理或工作人员">
                      </td>
                    </tr>

                    <tr>
                      <td>是否为洗钱风险较高行业的经营管理人（法定代表人、财务负责人、负责人及其关系人等）</td>
                      <td><input type="text" class="form-control" name="levels[17].score" value="${levels[17].score}" data-parsley-type="integer" required max="100"  id="score17"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[17].checkscore" value="${levels[17].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[17].account" value="${levels[17].account}"  id="account17" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[17].weight" value="10" id="weight17">
                        <input type="text" class="form-control" name="levels[17].remark" value="${levels[17].remark}">
                        <input type="hidden" name="levels[17].id" value="${levels[17].id}">
                        <input type="hidden" name="levels[17].target" value="identity">
                        <input type="hidden" name="levels[17].project" value="identity">
                        <input type="hidden" name="levels[17].classscore" value="ML-highlevel-manager">
                        <input type="hidden" name="levels[17].classdesc" value="是否为洗钱风险较高行业的经营管理人（法定代表人、财务负责人、负责人及其关系人等）">
                      </td>
                    </tr>

                    <tr>
                      <td rowspan="5">交易行为</td>
                      <td rowspan="5"><span>10</span></td>
                      <td rowspan="5">异常交易</td>
                      <td>30天触犯支付业务类可疑预警规则3次以上</td>
                      <td><input type="text" class="form-control" name="levels[18].score" value="${levels[18].score}" data-parsley-type="integer" required max="100"  id="score18"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[18].checkscore" value="${levels[18].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[18].account" value="${levels[18].account}"  id="account18" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[18].weight" value="10" id="weight18">
                        <input type="text" class="form-control" name="levels[18].remark" value="${levels[18].remark}">
                        <input type="hidden" name="levels[18].id" value="${levels[18].id}">
                        <input type="hidden" name="levels[18].target" value="transaction">
                        <input type="hidden" name="levels[18].project" value="unnormal-transaction">
                        <input type="hidden" name="levels[18].classscore" value="30days-payrule-3times">
                        <input type="hidden" name="levels[18].classdesc" value="30天触犯支付业务类可疑预警规则3次以上">
                      </td>
                    </tr>

                    <tr>
                      <td>30天触犯其他业务类可疑预警规则3次以上</td>
                      <td><input type="text" class="form-control" name="levels[19].score" value="${levels[19].score}" data-parsley-type="integer" required max="100"  id="score19"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[19].checkscore" value="${levels[19].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[19].account" value="${levels[19].account}"  id="account19" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[19].weight" value="10" id="weight19">
                        <input type="text" class="form-control" name="levels[19].remark" value="${levels[19].remark}">
                        <input type="hidden" name="levels[19].id" value="${levels[19].id}">
                        <input type="hidden" name="levels[19].target" value="transaction">
                        <input type="hidden" name="levels[19].project" value="unnormal-transaction">
                        <input type="hidden" name="levels[19].classscore" value="30days-otherrule-3times">
                        <input type="hidden" name="levels[19].classdesc" value="30天触犯其他业务类可疑预警规则3次以上">
                      </td>
                    </tr>

                    <tr>
                      <td>同一客户开立多个账户，频繁发生大量交易(付款方)</td>
                      <td><input type="text" class="form-control" name="levels[20].score" value="${levels[20].score}" data-parsley-type="integer" required max="100"  id="score20"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[20].checkscore" value="${levels[20].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[20].account" value="${levels[20].account}"  id="account20" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[20].weight" value="10" id="weight20">
                        <input type="text" class="form-control" name="levels[20].remark" value="${levels[20].remark}">
                        <input type="hidden" name="levels[20].id" value="${levels[20].id}">
                        <input type="hidden" name="levels[20].target" value="transaction">
                        <input type="hidden" name="levels[20].project" value="unnormal-transaction">
                        <input type="hidden" name="levels[20].classscore" value="numerous-transaction">
                        <input type="hidden" name="levels[20].classdesc" value="同一客户开立多个账户，频繁发生大量交易(付款方)">
                      </td>
                    </tr>

                    <tr>
                      <td>高风险行业客户，频繁发生单笔金额较大交易（收款方）</td>
                      <td><input type="text" class="form-control" name="levels[21].score" value="${levels[21].score}" data-parsley-type="integer" required max="100"  id="score21"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[21].checkscore" value="${levels[21].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[21].account" value="${levels[21].account}"  id="account21" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[21].weight" value="10" id="weight21">
                        <input type="text" class="form-control" name="levels[21].remark" value="${levels[21].remark}">
                        <input type="hidden" name="levels[21].id" value="${levels[21].id}">
                        <input type="hidden" name="levels[21].target" value="transaction">
                        <input type="hidden" name="levels[21].project" value="unnormal-transaction">
                        <input type="hidden" name="levels[21].classscore" value="frequently-bigaccount-transaction">
                        <input type="hidden" name="levels[21].classdesc" value="高风险行业客户，频繁发生单笔金额较大交易（收款方）">
                      </td>
                    </tr>
                    <tr>
                      <td>在上次评级到本次评级时间内所有交易，未触犯规则</td>
                      <td><input type="text" class="form-control" name="levels[22].score" value="${levels[22].score}" data-parsley-type="integer" required max="100"  id="score22"></td>
                      <td>
                        <input type="text" class="form-control" name="levels[22].checkscore" value="${levels[22].checkscore}" readonly CHECK="Y">
                        <input type="text" class="form-control" name="levels[22].account" value="${levels[22].account}"  id="account22" readonly CHECK="N">
                      </td>
                      <td>
                        <input type="hidden" class="form-control" name="levels[22].weight" value="10" id="weight22">
                        <input type="text" class="form-control" name="levels[22].remark" value="${levels[22].remark}">
                        <input type="hidden" name="levels[22].id" value="${levels[22].id}">
                        <input type="hidden" name="levels[22].target" value="transaction">
                        <input type="hidden" name="levels[22].project" value="normal-transaction">
                        <input type="hidden" name="levels[22].classscore" value="transaction_normal">
                        <input type="hidden" name="levels[22].classdesc" value="在上次评级到本次评级时间内所有交易，未触犯规则">
                      </td>
                    </tr>

                    </tbody>

                  </table>
                </div>
                <div align="center">
                  <c:if test="${operType=='COMMIT'}">
                    <button id="updateButton" type="submit" class="btn btn-w-m btn-success"><strong>确认修改</strong></button>
                  </c:if>
                  <c:if test="${operType=='CHECK'}">
                    <button id="agreeButton" type="submit" class="btn btn-w-m btn-success"><strong>审核通过</strong></button>
                    <button id="disagreeButton" type="submit" class="btn btn-w-m btn-primary"><strong>审核拒绝</strong></button>
                  </c:if>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<div id="siteMeshJavaScript">
  <script type="text/javascript" >
    $(function(){
      //设置初始化操作返回信息
      var message = $("#message").val();
      if(message!=''){
        if(message=='success'){
          bootbox.alert("修改成功！");
        }else{
          bootbox.alert("修改失败");
        }
      }
      //设置计算分值
      $('input[id^="score"]').change(function(){
        var num = this.id.substring(5);
        var weight = $("#weight"+num).val();
        var score = this.value;
        if($.isNumeric(score)){
          $("#account"+num).val(weight*score);
        }else{
          $("#account"+num).val("");
        }
      });
      //根据操作类型限制相关信息的显示和隐藏
      var Type = $("#operType").val();
      if(Type=="CHECK"||Type=="COMMITTED"){
        $("*[CHECK='N']").each(function(){
          $(this).hide();
        });
        $("*[CHECK='Y']").each(function(){
          $(this).show();
        });
      }else{
        $("*[CHECK='N']").each(function(){
          $(this).show();
        });
        $("*[CHECK='Y']").each(function(){
          $(this).hide();
        });
      }
      //设置提交按钮的提交事件
      $("#updateButton").click(function(){//修改按钮
        $("#operType").val("COMMIT");
      });
      $("#agreeButton").click(function(){//审核通过按钮
        $("#operType").val("AGREE");
      });
      $("#disagreeButton").click(function(){//审核拒绝按钮
        $("#operType").val("DISAGREE");
      });
    });
  </script>
</div>
</html>
