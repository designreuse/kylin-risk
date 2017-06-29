package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.CustomebwgList;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.entity.Group;
import com.rkylin.risk.core.entity.IdCardBlackList;
import com.rkylin.risk.core.entity.Operator;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.entity.Rule;
import com.rkylin.risk.core.entity.WarningSet;
import com.rkylin.risk.core.service.CustomerbwgService;
import com.rkylin.risk.core.service.DictionaryService;
import com.rkylin.risk.core.service.GroupService;
import com.rkylin.risk.core.service.IdCardBlackService;
import com.rkylin.risk.core.service.OperatorService;
import com.rkylin.risk.core.service.RuleService;
import com.rkylin.risk.core.service.WarningSetService;
import com.rkylin.risk.core.utils.MailUtil;
import com.rkylin.risk.order.bean.SimpleOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.core.utils.ObjectUtils.amountValueOf;
import static com.rkylin.risk.core.utils.ObjectUtils.intValueOf;

/**
 * Created by cuixiaofang on 2016-3-29.
 */
@Slf4j
@Component("baseBiz")
public class BaseBizImpl{
  @Resource
  private RuleService ruleService;
  @Resource
  private WarningSetService warningSetService;
  @Resource
  private OperatorService operatorService;
  @Resource
  private DictionaryService dictionaryService;
  @Resource
  private CustomerbwgService customerbwgService;
  @Resource
  private IdCardBlackService idCardBlackService;
  @Resource
  private GroupService groupService;

  @Resource
  private MailUtil mailUtil;

  @Value("${mail.flag}")
  private boolean mailFlag;

  /**
   * 添加Order数据
   */
  protected Order reSetOrder(SimpleOrder simpleOrder) {
    Order order = new Order();
    order.setOrderid(simpleOrder.getOrderId());
    order.setCheckorderid(simpleOrder.getCheckorderid());
    order.setOrdertypeid(simpleOrder.getOrderTypeId());
    order.setOrderdate(
        LocalDate.parse(simpleOrder.getOrderDate(), DateTimeFormat.forPattern("yyyyMMdd")));
    DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    order.setOrdertime(DateTime.parse(simpleOrder.getOrderTime(), format));
    order.setRootinstcd(simpleOrder.getRootInstCd());
    order.setProductid(simpleOrder.getProductId());
    order.setUserid(simpleOrder.getUserId());
    order.setUserrelateid(simpleOrder.getUserRelateId());
    order.setProviderid(simpleOrder.getProviderId());
    order.setUserorderid(simpleOrder.getUserOrderId());
    order.setAmount(amountValueOf(simpleOrder.getAmount(), null));
    order.setMobile(simpleOrder.getMobile());
    order.setCardNum(simpleOrder.getCardNum());
    order.setIdentityCard(simpleOrder.getIdentityCard());
    order.setOrdercontrol(simpleOrder.getOrderControl());
    order.setRespcode(simpleOrder.getRespCode());
    order.setStatusid(simpleOrder.getStatusId());
    order.setGoodsname(simpleOrder.getGoodsName());
    order.setGoodsdetail(simpleOrder.getGoodsDetail());
    order.setGoodscnt(intValueOf(simpleOrder.getGoodsCnt(), null));
    order.setUnitprice(amountValueOf(simpleOrder.getUnitPrice(), null));
    order.setUserrelateid(
        simpleOrder.getUserRelateId() != null ? simpleOrder.getUserRelateId() : null);
    order.setRiskstatus("1");
    return order;
  }

  /**
   * 查询可执行规则
   */
  public Rule getRuleByProduct(String productid, String type) {
    List<Rule> rules = ruleService.queryRuleProByPro(productid, type);
    if (rules.isEmpty()) {
      return null;
    }
    return rules.get(0);
  }

  /**
   * 白名单确认
   */
  public String whiteListValidate(String customerid) {
    if (StringUtils.isNotEmpty(customerid)) {
      CustomebwgList customebwgList =
          customerbwgService.queryByCustomeridAndType(customerid, Constants.WHITE);
      if (customebwgList != null) {
        return "白名单客户";
      }
    }
    return null;
  }

  /**
   * 黑名单验证
   *
   * @param customerid 客户编号
   * @param certificateId 身份证号
   * @return retStr为null表示未拦截，否则表示已拦截
   */
  public String blackListValidate(String customerid, String certificateId) {
    if (StringUtils.isNotEmpty(customerid)) {
      CustomebwgList customebwgList = customerbwgService.queryByCustomeridAndType(customerid,
          Constants.BLACK);
      if (customebwgList != null) {
        return "客户黑名单拦截";
      }
    }
    if (StringUtils.isNotEmpty(certificateId)) {
      IdCardBlackList idCardBlackList = idCardBlackService.queryByIdentnum(certificateId);
      if (idCardBlackList != null) {
        return "身份证黑名单拦截";
      }
    }
    return null;
  }

  /**
   * 发送邮件
   */
  public void sendMails(String emailbody, String constid, String userid, String emailType,
      String riskLevel) {
    try {
      if(mailFlag){
        String[] operatorEmails = getOperatorEmails(constid, emailType, riskLevel);
        if (operatorEmails != null && operatorEmails.length > 0) {
          log.info("开始发送邮件");
          //发送邮件
          Map map = new HashMap();
          map.put("userid", userid);
          map.put("refusedMsg", emailbody);
          MailBean mailBean = MailBean.builder()
              .data(map)
              .template(Constants.USER_REMIND_MAIL_TEMPLATE)
              .toEmails(operatorEmails)
              .subject("风控系统风险提醒")
              .build();

          log.info("邮箱发件人{},发送内容{}", StringUtils.join(operatorEmails, ","),
              emailbody);
          boolean flag = mailUtil.send(mailBean);
          log.info("发送邮件结果为{}", flag);
        }
      }else{
        log.info("邮件发送标志为：false，暂不做邮件处理。");
      }
    } catch (Exception e) {
      log.info("邮件发送失败");
      e.printStackTrace();
    }
  }

  public String[] getOperatorEmails(String constid, String emailType, String riskLevel) {
    String[] operatorEmails = null;
    if ("SYSTEM".equals(emailType)) {
      operatorEmails = Constants.SYSTEM_EMAIL_OPERATOR.split(",");
    } else if ("WARNSET".equals(emailType)) {
      List<String> emailList = Lists.newArrayList();
      List<WarningSet> warningSets =
          warningSetService.queryIsSendMails(LocalDate.now(), Constants.WARN_EMAIL, riskLevel);
      if (CollectionUtils.isNotEmpty(warningSets)) {
        List<Operator> operators = operatorService.queryByOperatorIds(warningSets);
        for (Operator oper : operators) {
          String products = oper.getProducts();
          if (products != null && !"-1".equals(products)) {
            DictionaryCode dictionary =
                dictionaryService.queryByCode(products.split(",")[0]);
            if (dictionary != null) {
              if (dictionary.getDictcode().equals(constid)) {
                emailList.add(oper.getEmail());
              }
            }
          }
        }
      }
      if (CollectionUtils.isNotEmpty(emailList)) {
        operatorEmails = emailList.toArray(new String[emailList.size()]);
      }
    }

    return operatorEmails;
  }

  public boolean checkRule(String issueartifactid, String issuegroupid) {
    Group group =
        groupService.queryByTimeAndStatus(issueartifactid, issuegroupid);
    return group != null;
  }
}
