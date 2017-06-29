package com.rkylin.risk.service.biz.impl;

import com.google.common.eventbus.EventBus;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.LogicRuleBean;
import com.rkylin.risk.core.dto.ResultBean;
import com.rkylin.risk.core.entity.Course;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.CourseService;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.utils.BeanMappper;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.bean.ListenerBean;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.IdentifyBiz;
import com.rkylin.risk.service.biz.LogicRuleCalBiz;
import com.rkylin.risk.service.biz.PersonBiz;
import com.rkylin.risk.service.biz.ReptileBiz;
import java.math.RoundingMode;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Created by cuixiaofang on 2016-7-28.
 */
@Component("personBiz")
@Slf4j
public class PersonBizImpl extends BaseBizImpl implements PersonBiz {
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
/*  @Resource
  private CustomerMobilelistService mobilelistService;*/

  @Value("${maven.groupPath}")
  private String groupPath;

  @Resource
  private EventBus flowStatusEventBus;

  @Resource
  private IdentifyBiz identifyBiz;

  @Override
  public boolean personmsg(PersonFactor personFactor) {
    // 验证该流程是否已经执行
    OperateFlow operateFlow =
        operateFlowService.queryByCheckorderid(personFactor.getCheckorderid());
    ResultBean resultBean = new ResultBean();
    if (operateFlow != null) {
      log.info("【dubbo服务】----流程ID为【{}】的流程已经审核, 直接返回审核结果----", personFactor.getCheckorderid());
      String resultstatus = operateFlow.getResultstatus();
      return !("2".equals(resultstatus) || "3".equals(resultstatus));
    }
    OperateFlow opf = new OperateFlow();
    // 验证数据有效性
    String personvalidate = personFactorValidate(personFactor);
    if (isNotEmpty(personvalidate)) {
      return resultHandler(null, null, null, personFactor, personvalidate, null, opf);
    }
    // 白名单确认
    String whiteStr = whiteListValidate(personFactor.getUserid());
    if (isNotEmpty(whiteStr)) {
      log.info("【dubbo服务】{}在【白名单】中,不进入校验规则,直接通过请求", personFactor.getUserid());
      return resultHandler(null, null, whiteStr, personFactor, null, null, opf);
    }

    // 黑名单验证
    String blackStr = blackListValidate(personFactor.getUserid(),
        personFactor.getCertificatenumber());
    if (isNotEmpty(blackStr)) {
      log.info("【dubbo服务】{}在【黑名单】中,不进入校验规则,直接驳回请求", personFactor.getUserid());
      return resultHandler(null, blackStr, null, personFactor, null, null, opf);
    }
    //文件下载报错异常
    StringBuffer message = new StringBuffer();
   /*  Map<String, Object> returnMap = identifyBiz.downloadFile(personFactor);

    if (returnMap.get("downloadresult") != null) {
      message.append(returnMap.get("downloadmsg").toString());
    } else {
      //续议
      if (returnMap.get("result") != null && "error".equals(returnMap.get("result"))) {
        return resultHandler(null, null, null, personFactor, null,
            returnMap.get("errormsg").toString(),
            opf);
      } else if (returnMap.get("info") != null) {
        IdCardEntity.Info info = (IdCardEntity.Info) returnMap.get("info");
        //鹏元征信验证身份证
        Map<String, Object> creditPymap =
            identifyBiz.creditPY(personFactor, info.getName(), info.getNumber());
        if (creditPymap.get("result") != null && "error".equals(creditPymap.get("result"))) {
          return resultHandler(null, null, null, personFactor, null,
              returnMap.get("errormsg").toString(),
              opf);
        } else {
          message.append(
              creditPymap.get("warning") == null ? "" : creditPymap.get("warning").toString());
        }
        //鹏元征信验证姓名与手机号码
        log.info("验证姓名与手机号码开始");
        Map<String, Object> phonecreditPymap =
            identifyBiz.creditPhoneCheck(personFactor);
        message.append(phonecreditPymap.get("warning") == null ? ""
            : phonecreditPymap.get("warning").toString());
        log.info("验证姓名与手机号码结束");
      }
      message.append(
          returnMap.get("warning") == null ? "" : returnMap.get("warning").toString());
    }
    if (isNotEmpty(personFactor.getMobilelist())) {
      if (!personFactor.getMobilelist().contains(personFactor.getFirstmobile())) {
        message.append(" 第一联系人不存在于客户通讯录内");
      }
      if (!personFactor.getMobilelist().contains(personFactor.getSecondmobile())) {
        message.append(" 第二联系人不存在于客户通讯录内");
      }
    }*/
    // 课栈网
    if ("M000004".equals(personFactor.getConstid()) && checkRule(
        RuleConstants.KEZHAN_PERSON_RULE, groupPath + RuleConstants.KEZHAN_CHANNEL_ID)) {
      LogicRuleBean logicRuleBean = calculatePersonRule(personFactor);
      resultBean = logicRuleCalBiz.calPersionalRuleByKezhanwang(logicRuleBean);

      //  帮帮助学
    } else if (RuleConstants.BANGBANG_CHANNEL_ID.equals(personFactor.getConstid()) && checkRule(
        RuleConstants.BANGBANG_PERSON_RULE, groupPath + RuleConstants.BANGBANG_CHANNEL_ID)) {
      LogicRuleBean logicRuleBean = calculatePersonRule(personFactor);
      resultBean = logicRuleCalBiz.calPersionalRuleByBestudent(logicRuleBean);
    }
    if (!"1".equals(resultBean.getIsOff())) {
      resultBean.setOffMsg(resultBean.getOffMsg() + message);
      reptileBiz.requestVerifyReptile(1, personFactor.getUsername(),
          personFactor.getCertificatenumber(), personFactor.getMobilephone(), null, null,
          personFactor.getCheckorderid());
    }
    return resultHandler(resultBean, null, null, personFactor, null, null, opf);
  }

  /**
   * 更新表
   */
  private boolean resultHandler(ResultBean resultBean,
      String blackStr, String whiteStr, PersonFactor personFactor, String validateStr,
      String photoStr, OperateFlow opf) {
    boolean flag = true;
    Customerinfo extcust = customerinfoService.queryOne(personFactor.getUserid());
    //  CustomerMobilelist mobilelistinfo = mobilelistService.queryOne(personFactor.getUserid());

    Customerinfo customerinfo = generateCustomerinfo(personFactor);
    OperateFlow operateFlow = generateOperateFlow(personFactor, opf);
    if (isEmpty(whiteStr)) {
      if (isNotEmpty(blackStr)) {
        flag = false;
        setOperateStatus(operateFlow, personFactor, "3", blackStr, null, null, null);
      } else if (isNotEmpty(validateStr)) {
        flag = false;
        setOperateStatus(operateFlow, personFactor, "3", validateStr, null, "SYSTEM", null);
      } else if (isNotEmpty(photoStr)) {
        flag = false;
        setOperateStatus(operateFlow, personFactor, "3", photoStr, null, "WARNSET", null);
      } else {
        operateFlow.setRuleid(resultBean.getRuleids());
        if ("1".equals(resultBean.getIsOff())) {
          flag = false;
          setOperateStatus(operateFlow, personFactor, "3", resultBean.getOffMsg(), null, "WARNSET",
              Constants.HIGH_LEVEL);
        } else if (isNotEmpty(resultBean.getRuleids())) {
          setOperateStatus(operateFlow, personFactor, null, null, resultBean.getOffMsg(), "WARNSET",
              Constants.MIDDLE_LEVEL);
        }
      }
    }
    log.info("【dubbo服务】风控系统更新客户表");
    if (extcust != null) {
      customerinfo.setId(extcust.getId());
      customerinfoService.update(customerinfo);
    } else {
      customerinfoService.insert(customerinfo);
    }
/*    if (isNotEmpty(personFactor.getMobilelist())) {
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
    }*/
    log.info("【dubbo服务】风控系统更新风险等级管理表");
    operateFlowService.insert(operateFlow);
    return flag;
  }

  private OperateFlow generateOperateFlow(PersonFactor personFactor, OperateFlow operateFlow) {
    operateFlow.setCheckorderid(personFactor.getCheckorderid());
    operateFlow.setCustomerid(personFactor.getUserid());
    operateFlow.setClassname(personFactor.getClassname());
    operateFlow.setClassprice(amountValueOf(personFactor.getClassprice(), new Amount(0))
        .divide(new Amount(100), 2, RoundingMode.FLOOR));
    return operateFlow;
  }

  private Customerinfo generateCustomerinfo(PersonFactor personFactor) {
    Customerinfo customerinfo = new Customerinfo();

    BeanMappper.fastCopy(personFactor, customerinfo);
    customerinfo.setCustomerid(personFactor.getUserid());
    customerinfo.setCustomername(personFactor.getUsername());
    customerinfo.setCertificateid(personFactor.getCertificatenumber());
    customerinfo.setChannel(personFactor.getConstid());
    return customerinfo;
  }

  /*
  设置审核状态，并发邮件
   */
  private void setOperateStatus(OperateFlow operateFlow, PersonFactor personFactor, String status,
      String reason, String riskmsg, String emailType, String riskLevel) {
    operateFlow.setResultstatus(status);
    operateFlow.setReason(reason);
    operateFlow.setRiskmsg(riskmsg);
    String resureason = StringUtils.isEmpty(reason) ? null : "风控系统返回：" + reason;
    String resuriskmsg = StringUtils.isEmpty(riskmsg) ? null : "风控系统返回：" + riskmsg;
    ListenerBean listenerBean = new ListenerBean();
    listenerBean.setStatus(status);
    listenerBean.setReason(resureason);
    listenerBean.setRiskmsg(resuriskmsg);
    listenerBean.setCheckorderid(operateFlow.getCheckorderid());
    flowStatusEventBus.post(listenerBean);
    //发送邮件
    super.sendMails(reason == null ? riskmsg : reason, personFactor.getConstid(),
        personFactor.getUserid(), emailType, riskLevel);
  }

  /**
   * 个人审核规则处理
   */
  private LogicRuleBean calculatePersonRule(PersonFactor personFactor) {
    LogicRuleBean logicRuleBean = new LogicRuleBean();

    String age = personFactor.getAge() != null ? personFactor.getAge() : null;
    logicRuleBean.setAge(new Amount(age));

    //判断是否为高中学历
    //        if (isNotEmpty(personFactor.getEducation())
    //                && "高中".equals(personFactor.getEducation())) {
    //            logicRuleBean.setDegree("UNDERHIGHSCHOOL");
    //        }

    logicRuleBean.setIsCardNum(String.valueOf(isNotEmpty(personFactor.getCertificatenumber())));
    Course course = null;
    //课程名称是否包含
    String classname = personFactor.getClassname();
    // TODO 转换课程名称为大写并去除空格
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

    Amount classprice = new Amount(personFactor.getClassprice());
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
    if (StringUtils.isEmpty(person.getCheckorderid())) {
      return "工作流ID";
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
    if (StringUtils.isEmpty(person.getCertificatestartdate()) || "-1".equals(
        person.getCertificatestartdate())) {
      return "身份证生效日期为空";
    }
    if (StringUtils.isEmpty(person.getCertificateexpiredate()) || "-1".equals(
        person.getCertificateexpiredate())) {
      return "身份证失效日期为空";
    }
    if (StringUtils.isEmpty(person.getTcaccount())) {
      return "银行卡卡号为空";
    }
    if (StringUtils.isEmpty(person.getMobilephone())) {
      return "手机号码为空";
    }
    if (StringUtils.isEmpty(person.getAge()) || "-1".equals(person.getAge())) {
      return "年龄为空";
    }
    if (StringUtils.isEmpty(person.getClassname())) {
      return "课程名称为空";
    }
    if (StringUtils.isEmpty(person.getClassprice())
        || "0".equals(person.getClassprice())
        || "-1".equals(person.getClassprice())) {
      return "课程单价为空";
    }
    if (StringUtils.isEmpty(person.getClassname())) {
      return "课程名称为空";
    }
  /*  if (StringUtils.isEmpty(person.getUrlkey())) {
      return "urlke为空";
    }
    if (StringUtils.isEmpty(person.getFirstman())) {
      return "第一联系人姓名为空";
    }
    if (StringUtils.isEmpty(person.getFirstmobile())) {
      return "第一联系人手机号码为空";
    }
    if (StringUtils.isEmpty(person.getSecondman())) {
      return "第二联系人姓名为空";
    }
    if (StringUtils.isEmpty(person.getSecondmobile())) {
      return "第二联系人手机号码为空";
    }*/

    return null;
  }
}
