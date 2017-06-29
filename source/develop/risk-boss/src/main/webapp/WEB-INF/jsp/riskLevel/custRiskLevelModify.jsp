<%--suppress ALL --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>

<html>
<head>
    <title>客户风险评级设置</title>
</head>
<body>
<div id="wrapper">
    <div  class="gray-bg">
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>客户风险评级设置<small>.</small></h5>

                        </div>
                        <div class="ibox-content">
                            <form id="custRiskLevel" method="post" action="${ctxPath}/riskLevel/updateLevelCustom" class="form-inline" data-parsley-validate>
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
                                                <td rowspan="2">国籍</td>
                                                <td rowspan="2"><span>12</span></td>
                                                <td rowspan="2">国籍</td>
                                                <td>一般国家或地区</td>
                                                <td><input type="text" class="form-control" name="levels[0].score" value="${levels[0].score}" data-parsley-type="integer" required max="100" id="score0"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[0].checkscore" value="${levels[0].checkscore}"  readonly  CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[0].account" value="${levels[0].account}"  id="account0" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[0].weight" value="6" id="weight0">
                                                    <input type="text" class="form-control" name="levels[0].remark" value="${levels[0].remark}">
                                                    <input type="hidden" name="levels[0].id" value="${levels[0].id}">
                                                    <input type="hidden" name="levels[0].target" value="country">
                                                    <input type="hidden" name="levels[0].project" value="country">
                                                    <input type="hidden" name="levels[0].classscore" value="generalRiskCountry">
                                                    <input type="hidden" name="levels[0].classdesc" value="一般国家或地区">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>高风险国家或地区（异常国家列表）</td>
                                                <td><input type="text" class="form-control" name="levels[1].score" value="${levels[1].score}" data-parsley-type="integer" required max="100"  id="score1"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[1].checkscore" value="${levels[1].checkscore}"  readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[1].account" value="${levels[1].account}"  id="account1" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[1].weight" value="6" readonly id="weight1">
                                                    <input type="text" class="form-control" name="levels[1].remark" value="${levels[1].remark}">
                                                    <input type="hidden" name="levels[1].id" value="${levels[1].id}">
                                                    <input type="hidden" name="levels[1].target" value="country">
                                                    <input type="hidden" name="levels[1].project" value="country">
                                                    <input type="hidden" name="levels[1].classscore" value="highRiskCountry">
                                                    <input type="hidden" name="levels[1].classdesc" value="高风险国家或地区">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="3">地域</td>
                                                <td rowspan="3"><span>12</span></td>
                                                <td rowspan="3">高、中、低风险地区</td>
                                                <td>上海、北京</td>
                                                <td><input type="text" class="form-control" name="levels[2].score" value="${levels[2].score}" data-parsley-type="integer" required max="100"  id="score2"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[2].checkscore" value="${levels[2].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[2].account" value="${levels[2].account}"  id="account2" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[2].weight" value="4" id="weight2">
                                                    <input type="text" class="form-control" name="levels[2].remark" value="${levels[2].remark}">
                                                    <input type="hidden" name="levels[2].id" value="${levels[2].id}">
                                                    <input type="hidden" name="levels[2].target" value="area">
                                                    <input type="hidden" name="levels[2].project" value="riskAreas">
                                                    <input type="hidden" name="levels[2].classscore" value="SHorBJ">
                                                    <input type="hidden" name="levels[2].classdesc" value="上海、北京">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>其他城市</td>
                                                <td><input type="text" class="form-control" name="levels[3].score" value="${levels[3].score}" data-parsley-type="integer" required max="100"  id="score3"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[3].checkscore" value="${levels[3].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[3].account" value="${levels[3].account}"  id="account3" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[3].weight" value="4" readonly id="weight3">
                                                    <input type="text" class="form-control" name="levels[3].remark" value="${levels[3].remark}">
                                                    <input type="hidden" name="levels[3].id" value="${levels[3].id}">
                                                    <input type="hidden" name="levels[3].target" value="area">
                                                    <input type="hidden" name="levels[3].project" value="riskAreas">
                                                    <input type="hidden" name="levels[3].classscore" value="otherAreas">
                                                    <input type="hidden" name="levels[3].classdesc" value="其他城市">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>高风险地区（异常地区列表）</td>
                                                <td><input type="text" class="form-control" name="levels[4].score" value="${levels[4].score}" data-parsley-type="integer" required max="100"  id="score4"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[4].checkscore" value="${levels[4].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[4].account" value="${levels[4].account}"  id="account4" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[4].weight" value="4" id="weight4">
                                                    <input type="text" class="form-control" name="levels[4].remark" value="${levels[4].remark}">
                                                    <input type="hidden" name="levels[4].id" value="${levels[4].id}">
                                                    <input type="hidden" name="levels[4].target" value="area">
                                                    <input type="hidden" name="levels[4].project" value="riskAreas">
                                                    <input type="hidden" name="levels[4].classscore" value="highRiskAreas">
                                                    <input type="hidden" name="levels[4].classdesc" value="高风险地区（异常地区列表）">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="10">客户信息</td>
                                                <td rowspan="5"><span>10</span></td>
                                                <td rowspan="5">年龄</td>
                                                <td>16-24</td>
                                                <td><input type="text" class="form-control" name="levels[5].score" value="${levels[5].score}" data-parsley-type="integer" required max="100"  id="score5"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[5].checkscore" value="${levels[5].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[5].account" value="${levels[5].account}"  id="account5" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[5].weight" value="2" id="weight5">
                                                    <input type="text" class="form-control" name="levels[5].remark" value="${levels[5].remark}">
                                                    <input type="hidden" name="levels[5].id" value="${levels[5].id}">
                                                    <input type="hidden" name="levels[5].target" value="userInfo">
                                                    <input type="hidden" name="levels[5].project" value="age">
                                                    <input type="hidden" name="levels[5].classscore" value="16to24">
                                                    <input type="hidden" name="levels[5].classdesc" value="16-24">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>25-40</td>
                                                <td><input type="text" class="form-control" name="levels[6].score" value="${levels[6].score}" data-parsley-type="integer" required max="100"  id="score6"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[6].checkscore" value="${levels[6].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[6].account" value="${levels[6].account}"  id="account6" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[6].weight" value="2" id="weight6">
                                                    <input type="text" class="form-control" name="levels[6].remark" value="${levels[6].remark}">
                                                    <input type="hidden" name="levels[6].id" value="${levels[6].id}">
                                                    <input type="hidden" name="levels[6].target" value="userInfo">
                                                    <input type="hidden" name="levels[6].project" value="age">
                                                    <input type="hidden" name="levels[6].classscore" value="25to40">
                                                    <input type="hidden" name="levels[6].classdesc" value="25-40">
                                                </td>
                                            </tr><tr>
                                                <td>41-55</td>
                                                <td><input type="text" class="form-control" name="levels[7].score" value="${levels[7].score}" data-parsley-type="integer" required max="100"  id="score7"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[7].checkscore" value="${levels[7].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[7].account" value="${levels[7].account}"  id="account7" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[7].weight" value="2" id="weight7">
                                                    <input type="text" class="form-control" name="levels[7].remark" value="${levels[7].remark}">
                                                    <input type="hidden" name="levels[7].id" value="${levels[7].id}">
                                                    <input type="hidden" name="levels[7].target" value="userInfo">
                                                    <input type="hidden" name="levels[7].project" value="age">
                                                    <input type="hidden" name="levels[7].classscore" value="41to55">
                                                    <input type="hidden" name="levels[7].classdesc" value="41-55">
                                                </td>
                                            </tr><tr>
                                                <td>56-70</td>
                                                <td><input type="text" class="form-control" name="levels[8].score" value="${levels[8].score}" data-parsley-type="integer" required max="100"  id="score8"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[8].checkscore" value="${levels[8].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[8].account" value="${levels[8].account}"  id="account8" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[8].weight" value="2" id="weight8">
                                                    <input type="text" class="form-control" name="levels[8].remark" value="${levels[8].remark}">
                                                    <input type="hidden" name="levels[8].id" value="${levels[8].id}">
                                                    <input type="hidden" name="levels[8].target" value="userInfo">
                                                    <input type="hidden" name="levels[8].project" value="age">
                                                    <input type="hidden" name="levels[8].classscore" value="56to70">
                                                    <input type="hidden" name="levels[8].classdesc" value="56-70">
                                                </td>
                                            </tr><tr>
                                                <td>大于70</td>
                                                <td><input type="text" class="form-control" name="levels[9].score" value="${levels[9].score}" data-parsley-type="integer" required max="100"  id="score9"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[9].checkscore" value="${levels[9].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[9].account" value="${levels[9].account}"  id="account9" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[9].weight" value="2" id="weight9">
                                                    <input type="text" class="form-control" name="levels[9].remark" value="${levels[9].remark}">
                                                    <input type="hidden" name="levels[9].id" value="${levels[9].id}">
                                                    <input type="hidden" name="levels[9].target" value="userInfo">
                                                    <input type="hidden" name="levels[9].project" value="age">
                                                    <input type="hidden" name="levels[9].classscore" value="over70">
                                                    <input type="hidden" name="levels[9].classdesc" value="大于70">
                                                </td>
                                            </tr><tr>
                                                <td rowspan="5"><span>15</span></td>
                                                <td rowspan="5">实名认证信息按业务的实名认证等级</td>
                                                <td>线下强实名：（线下银行卡及身份证实名认证）</td>
                                                <td><input type="text" class="form-control" name="levels[10].score" value="${levels[10].score}" data-parsley-type="integer" required max="100"  id="score10"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[10].checkscore" value="${levels[10].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[10].account" value="${levels[10].account}"  id="account10" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[10].weight" value="3" id="weight10">
                                                    <input type="text" class="form-control" name="levels[10].remark" value="${levels[10].remark}">
                                                    <input type="hidden" name="levels[10].id" value="${levels[10].id}">
                                                    <input type="hidden" name="levels[10].target" value="userInfo">
                                                    <input type="hidden" name="levels[10].project" value="IdentifyLeval">
                                                    <input type="hidden" name="levels[10].classscore" value="identifyAndBankCard">
                                                    <input type="hidden" name="levels[10].classdesc" value="线下强实名：（线下银行卡及身份证实名认证）">
                                                </td>
                                            </tr><tr>
                                                <td>线下强实名（身份证实名+银行卡实名认证）</td>
                                                <td><input type="text" class="form-control" name="levels[11].score" value="${levels[11].score}" data-parsley-type="integer" required max="100"  id="score11"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[11].checkscore" value="${levels[11].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[11].account" value="${levels[11].account}"  id="account11" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[11].weight" value="3" id="weight11">
                                                    <input type="text" class="form-control" name="levels[11].remark" value="${levels[11].remark}">
                                                    <input type="hidden" name="levels[11].id" value="${levels[11].id}">
                                                    <input type="hidden" name="levels[11].target" value="userInfo">
                                                    <input type="hidden" name="levels[11].project" value="IdentifyLeval">
                                                    <input type="hidden" name="levels[11].classscore" value="powerfulIdentify">
                                                    <input type="hidden" name="levels[11].classdesc" value="线下强实名：（线下银行卡及身份证实名认证）">
                                                </td>
                                            </tr><tr>
                                                <td>仅身份证实名认证</td>
                                                <td><input type="text" class="form-control" name="levels[12].score" value="${levels[12].score}" data-parsley-type="integer" required max="100"  id="score12"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[12].checkscore" value="${levels[12].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[12].account" value="${levels[12].account}"  id="account12" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[12].weight" value="3" id="weight12">
                                                    <input type="text" class="form-control" name="levels[12].remark" value="${levels[12].remark}">
                                                    <input type="hidden" name="levels[12].id" value="${levels[12].id}">
                                                    <input type="hidden" name="levels[12].target" value="userInfo">
                                                    <input type="hidden" name="levels[12].project" value="IdentifyLeval">
                                                    <input type="hidden" name="levels[12].classscore" value="onlyIdentifyCard">
                                                    <input type="hidden" name="levels[12].classdesc" value="仅身份证实名认证">
                                                </td>
                                            </tr><tr>
                                                <td>仅作银行卡鉴权</td>
                                                <td><input type="text" class="form-control" name="levels[13].score" value="${levels[13].score}" data-parsley-type="integer" required max="100"  id="score13"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[13].checkscore" value="${levels[13].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[13].account" value="${levels[13].account}"  id="account13" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[13].weight" value="3" id="weight13">
                                                    <input type="text" class="form-control" name="levels[13].remark" value="${levels[13].remark}">
                                                    <input type="hidden" name="levels[13].id" value="${levels[13].id}">
                                                    <input type="hidden" name="levels[13].target" value="userInfo">
                                                    <input type="hidden" name="levels[13].project" value="IdentifyLeval">
                                                    <input type="hidden" name="levels[13].classscore" value="onlyBankCard">
                                                    <input type="hidden" name="levels[13].classdesc" value="仅作银行卡鉴权">
                                                </td>
                                            </tr><tr>
                                                <td>未实名（未做身份实名验证）</td>
                                                <td><input type="text" class="form-control" name="levels[14].score" value="${levels[14].score}" data-parsley-type="integer" required max="100"  id="score14"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[14].checkscore" value="${levels[14].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[14].account" value="${levels[14].account}"  id="account14" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[14].weight" value="3" id="weight14">
                                                    <input type="text" class="form-control" name="levels[14].remark" value="${levels[14].remark}">
                                                    <input type="hidden" name="levels[14].id" value="${levels[14].id}">
                                                    <input type="hidden" name="levels[14].target" value="userInfo">
                                                    <input type="hidden" name="levels[14].project" value="IdentifyLeval">
                                                    <input type="hidden" name="levels[14].classscore" value="noIdentify">
                                                    <input type="hidden" name="levels[14].classdesc" value="未实名（未做身份实名验证）">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="4">职业类别</td>
                                                <td rowspan="4"><span>16</span></td>
                                                <td rowspan="4">一般/高风险行业</td>
                                                <td>除高风险行业之外的行业</td>
                                                <td><input type="text" class="form-control" name="levels[15].score" value="${levels[15].score}" data-parsley-type="integer" required max="100"  id="score15"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[15].checkscore" value="${levels[15].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[15].account" value="${levels[15].account}"  id="account15" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[15].weight" value="4" id="weight15">
                                                    <input type="text" class="form-control" name="levels[15].remark" value="${levels[15].remark}">
                                                    <input type="hidden" name="levels[15].id" value="${levels[15].id}">
                                                    <input type="hidden" name="levels[15].target" value="occuptionCategory">
                                                    <input type="hidden" name="levels[15].project" value="riskLevel">
                                                    <input type="hidden" name="levels[15].classscore" value="exceptHighRisk">
                                                    <input type="hidden" name="levels[15].classdesc" value="除高风险行业之外的行业">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>无职业客户信息</td>
                                                <td><input type="text" class="form-control" name="levels[16].score" value="${levels[16].score}" data-parsley-type="integer" required max="100"  id="score16"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[16].checkscore" value="${levels[16].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[16].account" value="${levels[16].account}"  id="account16" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[16].weight" value="4" id="weight16">
                                                    <input type="text" class="form-control" name="levels[16].remark" value="${levels[16].remark}">
                                                    <input type="hidden" name="levels[16].id" value="${levels[16].id}">
                                                    <input type="hidden" name="levels[16].target" value="occuptionCategory">
                                                    <input type="hidden" name="levels[16].project" value="riskLevel">
                                                    <input type="hidden" name="levels[16].classscore" value="noUserInfo">
                                                    <input type="hidden" name="levels[16].classdesc" value="无职业客户信息">
                                                </td>
                                            </tr><tr>
                                                <td>职业信息为其他</td>
                                                <td><input type="text" class="form-control" name="levels[17].score" value="${levels[17].score}" data-parsley-type="integer" required max="100"  id="score17"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[17].checkscore" value="${levels[17].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[17].account" value="${levels[17].account}"  id="account17" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[17].weight" value="4" id="weight17">
                                                    <input type="text" class="form-control" name="levels[17].remark" value="${levels[17].remark}">
                                                    <input type="hidden" name="levels[17].id" value="${levels[17].id}">
                                                    <input type="hidden" name="levels[17].target" value="occuptionCategory">
                                                    <input type="hidden" name="levels[17].project" value="riskLevel">
                                                    <input type="hidden" name="levels[17].classscore" value="occupyOther">
                                                    <input type="hidden" name="levels[17].classdesc" value="职业信息为其他">
                                                </td>
                                            </tr><tr>
                                                <td>高风险行业P2P、小贷公司、珠宝、证券、基金、典当、房产、娱乐场所、奢侈品、游戏、点卡、视频、积分换购、虚拟货币</td>
                                                <td><input type="text" class="form-control" name="levels[18].score" value="${levels[18].score}" data-parsley-type="integer" required max="100"  id="score18"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[18].checkscore" value="${levels[18].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[18].account" value="${levels[18].account}"  id="account18" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[18].weight" value="4" id="weight18">
                                                    <input type="text" class="form-control" name="levels[18].remark" value="${levels[18].remark}">
                                                    <input type="hidden" name="levels[18].id" value="${levels[18].id}">
                                                    <input type="hidden" name="levels[18].target" value="occuptionCategory">
                                                    <input type="hidden" name="levels[18].project" value="riskLevel">
                                                    <input type="hidden" name="levels[18].classscore" value="highRisk">
                                                    <input type="hidden" name="levels[18].classdesc" value="高风险行业P2P、小贷公司、珠宝、证券、基金、典当、房产、娱乐场所、奢侈品、游戏、点卡、视频、积分换购、虚拟货币">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="5">客户注册时间</td>
                                                <td rowspan="5"><span>10</span></td>
                                                <td rowspan="5">客户注册时间</td>
                                                <td>小于6个月</td>
                                                <td><input type="text" class="form-control" name="levels[19].score" value="${levels[19].score}" data-parsley-type="integer" required max="100"  id="score19"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[19].checkscore" value="${levels[19].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[19].account" value="${levels[19].account}"  id="account19" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[19].weight" value="2" id="weight19">
                                                    <input type="text" class="form-control" name="levels[19].remark" value="${levels[19].remark}">
                                                    <input type="hidden" name="levels[19].id" value="${levels[19].id}">
                                                    <input type="hidden" name="levels[19].target" value="registTime">
                                                    <input type="hidden" name="levels[19].project" value="registTime">
                                                    <input type="hidden" name="levels[19].classscore" value="low6month">
                                                    <input type="hidden" name="levels[19].classdesc" value="小于6个月">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>6月-1年</td>
                                                <td><input type="text" class="form-control" name="levels[20].score" value="${levels[20].score}" data-parsley-type="integer" required max="100"  id="score20"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[20].checkscore" value="${levels[20].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[20].account" value="${levels[20].account}"  id="account20" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[20].weight" value="2" id="weight20">
                                                    <input type="text" class="form-control" name="levels[20].remark" value="${levels[20].remark}">
                                                    <input type="hidden" name="levels[20].id" value="${levels[20].id}">
                                                    <input type="hidden" name="levels[20].target" value="registTime">
                                                    <input type="hidden" name="levels[20].project" value="registTime">
                                                    <input type="hidden" name="levels[20].classscore" value="6monthto1year">
                                                    <input type="hidden" name="levels[20].classdesc" value="6月-1年">
                                                </td>
                                            </tr><tr>
                                                <td>1年-2年</td>
                                                <td><input type="text" class="form-control" name="levels[21].score" value="${levels[21].score}" data-parsley-type="integer" required max="100"  id="score21"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[21].checkscore" value="${levels[21].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[21].account" value="${levels[21].account}"  id="account21" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[21].weight" value="2" id="weight21">
                                                    <input type="text" class="form-control" name="levels[21].remark" value="${levels[21].remark}">
                                                    <input type="hidden" name="levels[21].id" value="${levels[21].id}">
                                                    <input type="hidden" name="levels[21].target" value="registTime">
                                                    <input type="hidden" name="levels[21].project" value="registTime">
                                                    <input type="hidden" name="levels[21].classscore" value="1yearTo2year">
                                                    <input type="hidden" name="levels[21].classdesc" value="1年-2年">
                                                </td>
                                            </tr><tr>
                                                <td>2年-5年</td>
                                                <td><input type="text" class="form-control" name="levels[22].score" value="${levels[22].score}" data-parsley-type="integer" required max="100"  id="score22"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[22].checkscore" value="${levels[22].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[22].account" value="${levels[22].account}"  id="account22" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[22].weight" value="2" id="weight22">
                                                    <input type="text" class="form-control" name="levels[22].remark" value="${levels[22].remark}">
                                                    <input type="hidden" name="levels[22].id" value="${levels[22].id}">
                                                    <input type="hidden" name="levels[22].target" value="registTime">
                                                    <input type="hidden" name="levels[22].project" value="registTime">
                                                    <input type="hidden" name="levels[22].classscore" value="2yearTo5year">
                                                    <input type="hidden" name="levels[22].classdesc" value="2年-5年">
                                                </td>
                                            </tr><tr>
                                                <td>大于5年</td>
                                                <td><input type="text" class="form-control" name="levels[23].score" value="${levels[23].score}" data-parsley-type="integer" required max="100"  id="score23"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[23].checkscore" value="${levels[23].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[23].account" value="${levels[23].account}"  id="account23" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[23].weight" value="2" id="weight23">
                                                    <input type="text" class="form-control" name="levels[23].remark" value="${levels[23].remark}">
                                                    <input type="hidden" name="levels[23].id" value="${levels[23].id}">
                                                    <input type="hidden" name="levels[23].target" value="registTime">
                                                    <input type="hidden" name="levels[23].project" value="registTime">
                                                    <input type="hidden" name="levels[23].classscore" value="over5year">
                                                    <input type="hidden" name="levels[23].classdesc" value="大于5年">
                                                </td>
                                            </tr><tr>
                                                <td rowspan="2">客户信息的完备度</td>
                                                <td rowspan="2"><span>10</span></td>
                                                <td rowspan="2">客户信息的完备度</td>
                                                <td>客户信息完备</td>
                                                <td><input type="text" class="form-control" name="levels[24].score" value="${levels[24].score}" data-parsley-type="integer" required max="100"  id="score24"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[24].checkscore" value="${levels[24].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[24].account" value="${levels[24].account}"  id="account24" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[24].weight" value="5" id="weight24">
                                                    <input type="text" class="form-control" name="levels[24].remark" value="${levels[24].remark}">
                                                    <input type="hidden" name="levels[24].id" value="${levels[24].id}">
                                                    <input type="hidden" name="levels[24].target" value="completionDegree">
                                                    <input type="hidden" name="levels[24].project" value="completionDegree">
                                                    <input type="hidden" name="levels[24].classscore" value="completely">
                                                    <input type="hidden" name="levels[24].classdesc" value="客户信息完备">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>其他信息不全</td>
                                                <td><input type="text" class="form-control" name="levels[25].score" value="${levels[25].score}" data-parsley-type="integer" required max="100"  id="score25"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[25].checkscore" value="${levels[25].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[25].account" value="${levels[25].account}"  id="account25" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[25].weight" value="5" id="weight25">
                                                    <input type="text" class="form-control" name="levels[25].remark" value="${levels[25].remark}">
                                                    <input type="hidden" name="levels[25].id" value="${levels[25].id}">
                                                    <input type="hidden" name="levels[25].target" value="completionDegree">
                                                    <input type="hidden" name="levels[25].project" value="completionDegree">
                                                    <input type="hidden" name="levels[25].classscore" value="otherInfoNotComplete">
                                                    <input type="hidden" name="levels[25].classdesc" value="其他信息不全">
                                                </td>
                                            <tr>
                                                <td rowspan="3">历史交易情况</td>
                                                <td rowspan="3"><span>15</span></td>
                                                <td rowspan="3">历史交易情况</td>
                                                <td>在上次评级到本次评级时间内未触犯规则</td>
                                                <td><input type="text" class="form-control" name="levels[26].score" value="${levels[26].score}" data-parsley-type="integer" required max="100"  id="score26"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[26].checkscore" value="${levels[26].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[26].account" value="${levels[26].account}"  id="account26" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[26].weight" value="5" id="weight26">
                                                    <input type="text" class="form-control" name="levels[26].remark" value="${levels[26].remark}">
                                                    <input type="hidden" name="levels[26].id" value="${levels[26].id}">
                                                    <input type="hidden" name="levels[26].target" value="historicalTransaction">
                                                    <input type="hidden" name="levels[26].project" value="historicalTransaction">
                                                    <input type="hidden" name="levels[26].classscore" value="noIllegal">
                                                    <input type="hidden" name="levels[26].classdesc" value="在上次评级到本次评级时间内未触犯规则">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>发生高风险事件5起以上或商户客户高风险数量10人以内</td>
                                                <td><input type="text" class="form-control" name="levels[27].score" value="${levels[27].score}" data-parsley-type="integer" required max="100"  id="score27"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[27].checkscore" value="${levels[27].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[27].account" value="${levels[27].account}"  id="account27" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[27].weight" value="5" id="weight27">
                                                    <input type="text" class="form-control" name="levels[27].remark" value="${levels[27].remark}">
                                                    <input type="hidden" name="levels[27].id" value="${levels[27].id}">
                                                    <input type="hidden" name="levels[27].target" value="historicalTransaction">
                                                    <input type="hidden" name="levels[27].project" value="historicalTransaction">
                                                    <input type="hidden" name="levels[27].classscore" value="middleIllegal">
                                                    <input type="hidden" name="levels[27].classdesc" value="发生高风险事件5起以上或商户客户高风险数量10人以内">
                                                </td>
                                            </tr><tr>
                                                <td>发生高风险事件10起以上或商户客户高风险数量10人以上</td>
                                                <td><input type="text" class="form-control" name="levels[28].score" value="${levels[28].score}" data-parsley-type="integer" required max="100"  id="score28"></td>
                                                <td>
                                                    <input type="text" class="form-control" name="levels[28].checkscore" value="${levels[28].checkscore}" readonly CHECK="Y">
                                                    <input type="text" class="form-control" name="levels[28].account" value="${levels[28].account}"  id="account28" readonly CHECK="N">
                                                </td>
                                                <td>
                                                    <input type="hidden" class="form-control" name="levels[28].weight" value="5" id="weight28">
                                                    <input type="text" class="form-control" name="levels[28].remark" value="${levels[28].remark}">
                                                    <input type="hidden" name="levels[28].id" value="${levels[28].id}">
                                                    <input type="hidden" name="levels[28].target" value="historicalTransaction">
                                                    <input type="hidden" name="levels[28].project" value="historicalTransaction">
                                                    <input type="hidden" name="levels[28].classscore" value="highIllegal">
                                                    <input type="hidden" name="levels[28].classdesc" value="发生高风险事件10起以上或商户客户高风险数量10人以上">
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
