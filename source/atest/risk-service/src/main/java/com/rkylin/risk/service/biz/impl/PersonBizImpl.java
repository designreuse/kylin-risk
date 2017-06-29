package com.rkylin.risk.service.biz.impl;

import com.Rop.api.request.WheatfieldAccountinfoQueryRequest;
import com.Rop.api.response.WheatfieldAccountinfoQueryResponse;
import com.google.common.eventbus.EventBus;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Course;
import com.rkylin.risk.core.entity.CustomerMobilelist;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.CourseService;
import com.rkylin.risk.core.service.CustomerMobilelistService;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.bean.ListenerBean;
import com.rkylin.risk.service.bean.MitouBean;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.bean.RiskMesageBean;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import com.rkylin.risk.service.biz.PersonBiz;
import com.rkylin.risk.service.biz.ReptileBiz;
import com.rkylin.risk.service.utils.ROPUtil;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Component("personBiz")
@Slf4j
public class PersonBizImpl extends BaseBizImpl implements PersonBiz {
  @Resource
  private VelocityEngine velocityEngine;
  @Resource
  private CourseService courseService;
  @Resource
  private LogicRuleCalBiz logicRuleCalBiz;
  @Resource
  private CustomerinfoService customerinfoService;
  @Resource
  private OperateFlowService operateFlowService;
  @Resource
  private ReptileBiz reptileBiz;
  @Resource
  private CustomerMobilelistService mobilelistService;
  @Value("${maven.groupPath}")
  private String groupPath;
  @Resource
  private EventBus flowStatusEventBus;
  @Resource
  private ROPUtil accountROP;
  @Resource
  private OrderService orderService;

  @Override
  public boolean personmsg(PersonFactor personFactor) {
    if (isNotEmpty(personFactor.getCheckorderid()) && !"null".equals(
        personFactor.getCheckorderid())) {
      // 验证该流程是否已经执行
      OperateFlow operateFlow =
          operateFlowService.queryByCheckorderid(personFactor.getCheckorderid());
      if (operateFlow != null) {
        log.info("【dubbo服务】----流程ID为【{}】的流程已经审核, 直接返回审核结果----", personFactor.getCheckorderid());
        String resultstatus = operateFlow.getResultstatus();
        return !("2".equals(resultstatus) || "3".equals(resultstatus));
      }
    }
    ResultBean resultBean = new ResultBean();
    // 验证数据有效性
    String personvalidate = personFactorValidate(personFactor);
    if (isNotEmpty(personvalidate)) {
      setRefuseOperateStatus(personFactor, "3", personvalidate, "SYSTEM");
      resultHandler(resultBean, personFactor, "3", personvalidate);
      return false;
    }
    // 白名单确认
    String whiteStr = whiteListValidate(personFactor.getUserid());
    if (isNotEmpty(whiteStr)) {
      log.info("【dubbo服务】{}在【白名单】中,不进入校验规则,直接通过请求", personFactor.getUserid());
      resultHandler(resultBean, personFactor, null, null);
      return true;
    }

    // 黑名单验证
    String blackStr = blackListValidate(personFactor.getUserid(),
        personFactor.getCertificatenumber());
    if (isNotEmpty(blackStr)) {
      log.info("【dubbo服务】{}在【黑名单】中,不进入校验规则,直接驳回请求", personFactor.getUserid());
      setRefuseOperateStatus(personFactor, "3", blackStr, null);
      resultHandler(resultBean, personFactor, "3", blackStr);
      return false;
    }
    //如果是工商银行则续议,悦视觉不过银行卡验证
    if (!RuleConstants.YUESHIJUE_CHANNEL_ID.equals(personFactor.getConstid())
        && !RuleConstants.MEIRONG_CHANNEL_ID.equals(personFactor.getConstid())
        && !RuleConstants.LVYOU_CHANNEL_ID.equals(personFactor.getConstid())) {
      personFactor = queryBankinfo(personFactor);
      if (isNotEmpty(personFactor.getBankcode()) && "102".equals(personFactor.getBankcode())) {
        log.info("【dubbo服务】{}账户为工商银行卡,直接续议", personFactor.getUserid());
        setRefuseOperateStatus(personFactor, "3", "银行卡为工商银行卡", null);
        resultHandler(resultBean, personFactor, "3", "银行卡为工商银行卡");
        return false;
      }
    }

    // 课栈网
    if ("M000004".equals(personFactor.getConstid()) && checkRule(
        RuleConstants.KEZHAN_PERSON_RULE, groupPath + RuleConstants.KEZHAN_CHANNEL_ID)) {
      LogicRuleBean logicRuleBean = calculatePersonRule(personFactor);
      resultBean = logicRuleCalBiz.calPersionalRuleByKezhanwang(logicRuleBean);

      // 帮帮助学
    } else if (RuleConstants.BANGBANG_CHANNEL_ID.equals(personFactor.getConstid()) && checkRule(
        RuleConstants.BANGBANG_PERSON_RULE, groupPath + RuleConstants.BANGBANG_CHANNEL_ID)) {
      LogicRuleBean logicRuleBean = calculatePersonRule(personFactor);
      resultBean = logicRuleCalBiz.calPersionalRuleByBestudent(logicRuleBean);
    }

    if (!"1".equals(resultBean.getIsOff())) {
      if (isNotEmpty(personFactor.getCheckorderid()) && !"null".equals(
          personFactor.getCheckorderid())) {
        // 发起爬虫
        reptileBiz.requestVerifyReptile(1, personFactor.getUsername(),
            personFactor.getCertificatenumber(), personFactor.getMobilephone(), null, null,
            personFactor.getCheckorderid());
      }
    } else {
      // 规则验证高风险
      log.info("【dubbo服务】{},风控验证为高风险，原因：{}", personFactor.getUserid(), resultBean.getOffMsg());
      setRefuseOperateStatus(personFactor, "3", resultBean.getOffMsg(), "WARNSET");
      resultHandler(resultBean, personFactor, "3", resultBean.getOffMsg());
      return false;
    }

    //订单状态验证
    String orderStaVali = orderStatusValidate(personFactor);

    if (isNotEmpty(personFactor.getCheckorderid()) && !"null".equals(
        personFactor.getCheckorderid()) && isNotEmpty(personFactor.getUrlkey())
        && !"null".equals(
        personFactor.getUrlkey())) {

      if (RuleConstants.YUESHIJUE_CHANNEL_ID.equals(personFactor.getConstid())
          || RuleConstants.MEIRONG_CHANNEL_ID.equals(personFactor.getConstid())) {
        setMitouOperateStatus(personFactor,orderStaVali);
      } else if ("M000004".equals(personFactor.getConstid())
          || RuleConstants.BANGBANG_CHANNEL_ID.equals(personFactor.getConstid())) {
        //课栈&&帮帮
        setRiskMsgOperateStatus(personFactor, resultBean.getOffMsg()+orderStaVali);
      }
    }
    resultHandler(resultBean, personFactor, null, null);
    return true;
  }

  /**
   * 更新表
   */
  private void resultHandler(ResultBean resultBean, PersonFactor personFactor, String status,
      String reason) {
    Customerinfo extcust = customerinfoService.queryOne(personFactor.getUserid());
    CustomerMobilelist mobilelistinfo = mobilelistService.queryOne(personFactor.getUserid());
    Customerinfo customerinfo = generateCustomerinfo(personFactor);
    log.info("【dubbo服务】风控系统更新客户表");
    if (extcust != null) {
      customerinfo.setId(extcust.getId());
      customerinfoService.update(customerinfo);
    } else {
      customerinfoService.insert(customerinfo);
    }
    if (isNotEmpty(personFactor.getMobilelist())) {
      log.info("【dubbo服务】风控系统更新通讯录");
      CustomerMobilelist mobile = new CustomerMobilelist();
      mobile.setCustomerid(personFactor.getUserid());
      mobile.setMobilelist(personFactor.getMobilelist());
      if (mobilelistinfo != null) {
        mobile.setId(mobilelistinfo.getId());
        mobilelistService.update(mobile);
      } else {
        mobilelistService.insert(mobile);
      }
    }
    if (isNotEmpty(personFactor.getCheckorderid()) && !"null".equals(
        personFactor.getCheckorderid())) {
      OperateFlow operateFlow = generateOperateFlow(personFactor, status, reason);
      operateFlow.setRuleid(resultBean.getRuleids());
      log.info("【dubbo服务】更新工作流信息表");
      if (operateFlow.getId() != null) {
        operateFlowService.updateOperteFlow(operateFlow);
      } else {
        operateFlowService.insert(operateFlow);
      }
    }
  }

  @Override
  public String orderStatusValidate(PersonFactor personFactor){
    Map map = new HashMap<>();
    List<Order> personOrders =  orderService.queryByIdenAndStatus(personFactor.getCertificatenumber(),null);
    map.put("personOrders", personOrders);
    if(isNotEmpty(personFactor.getFirstid())){
      boolean firstFlag = true;
      List<Order> firstOrders =  orderService.queryByIdenAndStatus(personFactor.getFirstid(),null);
      map.put("firstFlag", firstFlag);
      map.put("firstOrders", firstOrders);
    }
    return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template/person-status.vm",
        "UTF-8",map).replaceAll("\n","").replaceAll("\r","");
  }
  private OperateFlow generateOperateFlow(PersonFactor personFactor, String status,
      String reason) {
    OperateFlow operateFlow =
        operateFlowService.queryByCheckorderid(personFactor.getCheckorderid());
    if (operateFlow == null) {
      operateFlow = new OperateFlow();
    }
    operateFlow.setResultstatus(status);
    operateFlow.setReason(reason);
    operateFlow.setCheckorderid(personFactor.getCheckorderid());
    operateFlow.setCustomerid(personFactor.getUserid());
    operateFlow.setClassname(personFactor.getClassname());
    operateFlow.setClassprice(amountValueOf(personFactor.getClassprice(), new Amount(0))
        .divide(new Amount(100), 2, RoundingMode.FLOOR));
    operateFlow.setCourseId(personFactor.getCourseId());
    operateFlow.setCorporationId(personFactor.getCorporationId());
    operateFlow.setCorporationname(
        personFactor.getCorporationname() == null ? personFactor.getUniversityName()
            : personFactor.getCorporationname());
    operateFlow.setCouserStairClassify(personFactor.getCouserStairClassify());
    operateFlow.setCourseSecondaryClassify(personFactor.getCourseSecondaryClassify());
    operateFlow.setConstId(personFactor.getConstid());
    operateFlow.setSchoolarea(personFactor.getSchoolArea());
    operateFlow.setEnroldate(personFactor.getEnrolDate());
    operateFlow.setFinishschool(personFactor.getFinishSchool());
    operateFlow.setWorkcompanyname(personFactor.getWorkCompanyName());
    operateFlow.setWorkcompanynature(personFactor.getWorkCompanyNature());
    operateFlow.setWorkcompanyaddress(personFactor.getWorkCompanyAddress());
    operateFlow.setWorktitle(personFactor.getWorkTitle());
    operateFlow.setUrlkeys(personFactor.getUrlkey());
    operateFlow.setIsstudent(
        RuleConstants.YUESHIJUE_CHANNEL_ID.equals(personFactor.getConstid()) ? "0"
            : personFactor.getIsstudent());
    operateFlow.setCreditcard(personFactor.getCreditcard());
    operateFlow.setBusinesstype(personFactor.getBusinessType());
    return operateFlow;
  }

  private Customerinfo generateCustomerinfo(PersonFactor personFactor) {
    Customerinfo customerinfo = new Customerinfo();
    BeanMappper.fastCopy(personFactor, customerinfo);
    customerinfo.setCustomerid(personFactor.getUserid());
    customerinfo.setCustomername(personFactor.getUsername());
    customerinfo.setCertificateid(personFactor.getCertificatenumber());
    customerinfo.setChannel(personFactor.getConstid());
    customerinfo.setBankname(personFactor.getBankname() == null ? personFactor.getTaccountbank()
        : personFactor.getBankname());
    customerinfo.setBancode(personFactor.getBankcode());
    customerinfo.setBankbranch(
        personFactor.getBankbranch() == null ? personFactor.getTaccountbranch()
            : personFactor.getBankbranch());
    customerinfo.setTcaccount(personFactor.getTcaccount());
    customerinfo.setNation(personFactor.getNation());
    customerinfo.setAddress(personFactor.getAddress());
    customerinfo.setFirstid(personFactor.getFirstid());
    customerinfo.setFirstrelation(personFactor.getFirstrelation());
    customerinfo.setSecondid(personFactor.getSecondid());
    customerinfo.setSecondrelation(personFactor.getSecondrelation());
    customerinfo.setQqnum(personFactor.getQqnum());
    customerinfo.setJdnum(personFactor.getJdnum());
    customerinfo.setAlipaynum(personFactor.getAlipaynum());
    customerinfo.setTaccountprovince(personFactor.getTaccountprovince());
    customerinfo.setTaccountcity(personFactor.getTaccountcity());
    customerinfo.setEmail(personFactor.getEmail());
    return customerinfo;
  }

  /*
  设置续议或驳回审核状态，并发邮件
   */
  private void setRefuseOperateStatus(PersonFactor personFactor, String status,
      String reason, String emailType) {
    if (isNotEmpty(personFactor.getCheckorderid()) && !"null".equals(
        personFactor.getCheckorderid())) {
      String resureason = StringUtils.isEmpty(reason) ? null : "风控系统返回：" + reason;
      ListenerBean listenerBean = new ListenerBean();
      listenerBean.setStatus(status);
      listenerBean.setReason(resureason);
      listenerBean.setCheckorderid(personFactor.getCheckorderid());
      listenerBean.setBussinesstype(personFactor.getBusinessType());
      flowStatusEventBus.post(listenerBean);
    }
    //发送邮件
    super.sendMails(reason, personFactor.getConstid(),
        personFactor.getUserid(), emailType, Constants.HIGH_LEVEL);
  }

  /*
  设置风险提示审核状态，并发邮件
   */
  private void setRiskMsgOperateStatus(PersonFactor personFactor, String riskmsg) {
    RiskMesageBean riskMesageBean = new RiskMesageBean();
    riskMesageBean.setRiskmsg(riskmsg);
    riskMesageBean.setPersonFactor(personFactor);
    flowStatusEventBus.post(riskMesageBean);
  }

  /*
   设置风险提示审核状态，并发邮件
 */
  private void setMitouOperateStatus(PersonFactor personFactor,String orderStaVali) {
    MitouBean mitouBean = new MitouBean();
    mitouBean.setPersonFactor(personFactor);
    mitouBean.setRiskmsg(orderStaVali);
    flowStatusEventBus.post(mitouBean);
  }

  /**
   * 个人审核规则处理
   */
  private LogicRuleBean calculatePersonRule(PersonFactor personFactor) {
    LogicRuleBean logicRuleBean = new LogicRuleBean();

    String age = personFactor.getAge() != null ? personFactor.getAge() : null;
    logicRuleBean.setAge(new Amount(age));

    // 判断是否为高中学历
    if (isNotEmpty(personFactor.getEducation()) && "1".equals(personFactor.getEducation())) {
      log.info("【dubbo服务】学历为初中及以下,低于高中学历");
      logicRuleBean.setDegree("UNDERHIGHSCHOOL");
    }

    logicRuleBean.setIsCardNum(String.valueOf(isNotEmpty(personFactor.getCertificatenumber())));
    Course course = null;
    // 课程名称是否包含
    String classname = personFactor.getClassname();
    // 转换课程名称为大写并去除空格
    classname = classname.toUpperCase().replaceAll("\\s", "");
    List<Course> courses =
        courseService.queryByUserrelatedid(personFactor.getUserrelatedid());
    if (courses != null && courses.size() > 0) {
      String coursename = null;
      Course temp = null;
      int len = courses.size();
      for (int i = 0; i < len; i++) {
        temp = courses.get(i);
        coursename = temp.getCourseName().toUpperCase().replaceAll("\\s", "");
        if (coursename.equals(classname)) {
          logicRuleBean.setIsCourseName("true");
          course = temp;
        }
      }
    } else {
      logicRuleBean.setIsCourseName("false");
    }

    //课程单价
    Amount classprice =
        new Amount(personFactor.getClassprice() == null ? "0" : personFactor.getClassprice());
    if (course != null) {
      if (isNotEmpty(course.getCoursePrice())
          && course.getCoursePrice().contains("-")) {
        String pricemax = course.getCoursePrice().split("-")[1];
        logicRuleBean.setCoursePriceMaxRate(
            classprice.divide(new Amount(pricemax),
                2, RoundingMode.FLOOR));
      } else if (isNotEmpty(course.getCoursePrice())) {
        logicRuleBean.setCoursePriceMaxRate(
            classprice.divide(new Amount(course.getCoursePrice()),
                2, RoundingMode.FLOOR));
      } else {
        logicRuleBean.setCoursePriceRate(new Amount("121"));
        log.info("机构{}，未提交该课程最高单价：{}", personFactor.getUserrelatedid(),
            personFactor.getClassname());
      }
      logicRuleBean.setCourseClass(course.getCourseType());
    } else {
      logicRuleBean.setCoursePriceRate(new Amount("10"));
      log.info("机构{}，未提交该课程：{}", personFactor.getUserrelatedid(), personFactor.getClassname());
    }

    log.info("【dubbo服务】风控系统个人审核规则:{}",
        logicRuleBean.toString());
    return logicRuleBean;
  }

  public String personFactorValidate(PersonFactor person) {
    if (StringUtils.isEmpty(person.getUserid())) {
      return "申请人id为空";
    }

    if (StringUtils.isEmpty(person.getUserrelatedid())) {
      return "培训机构id为空";
    }
    if (StringUtils.isEmpty(person.getConstid())) {
      return "机构号为空";
    }
    if (StringUtils.isEmpty(person.getUsername())) {
      return "姓名为空";
    }
    if (StringUtils.isEmpty(person.getCertificatenumber())) {
      return "身份证号码为空";
    }
    if (person.getCertificatenumber().trim().length() != 18) {
      return "身份证号码长度不等于18位";
    }
    if (StringUtils.isEmpty(person.getTcaccount())) {
      return "银行卡卡号为空";
    }
    if (StringUtils.isEmpty(person.getMobilephone())) {
      return "手机号码为空";
    }
    if (StringUtils.isEmpty(person.getCertificateexpiredate())) {
      return "身份证失效日期为空";
    }
    return null;
  }

  private PersonFactor queryBankinfo(PersonFactor factor) {
    WheatfieldAccountinfoQueryRequest accountRequest =
        new WheatfieldAccountinfoQueryRequest();
    accountRequest.setUserid(factor.getUserid());
    accountRequest.setUsertype("2");
    accountRequest.setConstid(factor.getConstid());
    if (RuleConstants.BANGBANG_CHANNEL_ID.equals(factor.getConstid())) {
      accountRequest.setProductid(RuleConstants.BANGBANG_PRODUCTID);
    } else if ("M000004".equals(factor.getConstid())) {
      accountRequest.setProductid(RuleConstants.KEZHAN_CHANNEL_ID);
    }
    accountRequest.setObjorlist("1");
    WheatfieldAccountinfoQueryResponse accountinforesponse =
        accountROP.getResponse(accountRequest, "json");
    if (accountinforesponse != null) {
      log.info("rop请求账户信息结果为：{}", accountinforesponse.getIs_success());
      if ("true".equals(accountinforesponse.getIs_success())) {
        log.info("银行编码为" + accountinforesponse.getAccountinfos().get(0).getBankhead());
        factor.setBankname(accountinforesponse.getAccountinfos().get(0).getBankheadname());
        factor.setBankcode(accountinforesponse.getAccountinfos().get(0).getBankhead());
        factor.setBankbranch(accountinforesponse.getAccountinfos().get(0).getBankbranchname());
        factor.setTcaccount(
            accountinforesponse.getAccountinfos().get(0) == null ? factor.getTcaccount()
                : accountinforesponse.getAccountinfos().get(0).getAccount_number());
      }
    }

    return factor;
  }
}
