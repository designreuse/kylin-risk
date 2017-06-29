package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.utils.MailUtil;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouRiskDataBean;
import com.rkylin.risk.service.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.service.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.MitouBiz;
import com.rkylin.risk.service.biz.MitouRiskBiz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 米投风控
 *
 * @author qiuxian
 * @create 2016-11-28 17:11
 **/
@Component("mitouRiskBiz")
@Slf4j
public class MitouRiskBizImpl implements MitouRiskBiz {
  @Resource
  private MailUtil mailUtil;
  @Resource
  private MitouBiz mitouBiz;
  @Value("${mitou.platid}")
  private Integer platid;
  @Value("${mail.flag}")
  private boolean mailFlag;

  public Map requestMitouRisk(Map<String, Object> map, PersonFactor factor) {
    Map resultmap = new HashMap();
    try {
      ImageEntity<byte[]> entity1 = (ImageEntity) map.get("idcard");
      ImageEntity<byte[]> entity2 = (ImageEntity) map.get("idcardBack");
      ImageEntity<byte[]> entity3 = (ImageEntity) map.get("idcardPersonPic");

      MitouRiskDataRequestParam param = new MitouRiskDataRequestParam();
      MitouRiskDataBean bean = new MitouRiskDataBean();
      //身份信息
      MitouRiskDataBean.CardId cardinfo = bean.new CardId();
      cardinfo.setCardId(factor.getCertificatenumber());
      cardinfo.setName(factor.getUsername());
      cardinfo.setNation(factor.getNation());
      cardinfo.setAddress(factor.getAddress());
      cardinfo.setStartDate(factor.getCertificatestartdate());
      cardinfo.setEndDate(factor.getCertificateexpiredate());
      cardinfo.setFrontImage(
          "data:image/" + map.get("idcardsuffix")
              + ";base64,"
              + DatatypeConverter.printBase64Binary(
              entity1.getData()));
      cardinfo.setReverseImage(
          "data:image/" + map.get("idcardBacksuffix")
              + ";base64,"
              + DatatypeConverter.printBase64Binary(
              entity2.getData()));
      cardinfo.setSelfDeclaration("data:image/" + map.get("idcardPersonPicsuffix")
          + ";base64,"
          + DatatypeConverter.printBase64Binary(
          entity3.getData()));
      //联系人信息
      MitouRiskDataBean.Contact contactinfo = bean.new Contact();
      contactinfo.setQq(factor.getQqnum());
      contactinfo.setFirstContactName(factor.getFirstman());
      contactinfo.setFirstContactMobile(factor.getFirstmobile());
      contactinfo.setFirstContactRelationship(factor.getFirstrelation());
      contactinfo.setSecondContactName(factor.getSecondman());
      contactinfo.setSecondContactMobile(factor.getSecondmobile());
      contactinfo.setSecondContactRelationship(factor.getSecondrelation());
      //银行信息
      MitouRiskDataBean.BankInfo bankinfo = bean.new BankInfo();
      bankinfo.setCardNo(factor.getTcaccount());
      bankinfo.setBankDeposit(factor.getTaccountbank());
      bankinfo.setBankBranch(factor.getTaccountbranch());
      bankinfo.setBankReservePhone(factor.getMobilephone());
      bankinfo.setBankProvince(factor.getTaccountprovince());
      bankinfo.setBankCity(factor.getTaccountcity());

      //学生
      if ("1".equals(factor.getIsstudent())) {
        MitouRiskDataBean.EducationInfo educationInfo = bean.new EducationInfo();
        educationInfo.setEducation(getEducationInfo(factor.getEducation()));
        educationInfo.setEnrolDate(factor.getEnrolDate() + "0908");
        educationInfo.setSchoolArea(factor.getSchoolArea());
        educationInfo.setUniversityName(factor.getUniversityName());
        bean.setEducationInfo(educationInfo);
        //不是学生
      } else if ("0".equals(factor.getIsstudent())) {
        MitouRiskDataBean.WorkInfo workInfo = bean.new WorkInfo();
        workInfo.setWorkCompanyName(factor.getWorkCompanyName());
        bean.setWorkInfo(workInfo);
      }
      bean.setCardId(cardinfo);
      bean.setContact(contactinfo);
      bean.setBankInfo(bankinfo);
      param.setPlatuserid(factor.getUserid());
      param.setRisk_data(bean);

      MitouResponseParam<MitouRiskDataResponseParam> riskrequest =
          mitouBiz.riskrequest(platid, param);
      MitouResponseParam<MitouRiskDataResponseParam> response = riskrequest;
      log.info("----------Mitouresponse:" + response.toString());
      if (response.getFlag() == 0) {
        resultmap.put("result", "success");
        return resultmap;
      } else {
        //发送邮件
        sendMails(response.getMsg(), factor.getConstid(), factor.getUserid(),
            "WARNSET", Constants.MIDDLE_LEVEL);
        resultmap.put("result", "failure");
        resultmap.put("msg", response.getMsg());
        return resultmap;
      }
    } catch (Exception e) {
      resultmap.put("result", "failure");
      resultmap.put("msg", "米投--提交风控参数接口异常");
      log.info("米投--提交风控参数接口异常");
      e.printStackTrace();
      return resultmap;
    }
  }

  public String getEducationInfo(String education) {
    String educationStr = "";
    switch (education) {
      case "1":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "2":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "3":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "4":
        educationStr = "PUTONG_TOPUP";
        break;
      case "5":
        educationStr = "PUTONG_BENKE";
        break;
      case "6":
        educationStr = "PUTONG_SHUOSHI";
        break;
      case "7":
        educationStr = "PUTONG_BOSHI";
        break;
      case "9":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "0":
        educationStr = "PUTONG_ZHUANKE";
        break;
    }
    return educationStr;
  }

  public void sendMails(String emailbody, String constid, String userid, String emailType,
      String riskLevel) {
    try {
      if (mailFlag) {
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
      } else {
        log.info("邮件发送标志为：false，暂不做邮件处理。");
      }
    } catch (Exception e) {
      log.info("邮件发送失败");
      e.printStackTrace();
    }
  }

  public String[] getOperatorEmails(String constid, String emailType, String riskLevel) {
    String[] operatorEmails = null;
    List<String> emailList = null;
    if ("SYSTEM".equals(emailType)) {
      operatorEmails = Constants.SYSTEM_EMAIL_OPERATOR.split(",");
    } else if ("WARNSET".equals(emailType)) {
      emailList = Lists.newArrayList();
      emailList.add("qiuxian@rongcapital.cn");
      emailList.add("chenfumin@rongcapital.cn");
      operatorEmails = emailList.toArray(new String[emailList.size()]);
    }
    return operatorEmails;
  }
}
