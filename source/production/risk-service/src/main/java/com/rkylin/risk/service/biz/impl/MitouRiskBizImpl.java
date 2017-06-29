package com.rkylin.risk.service.biz.impl;

import com.google.common.collect.Lists;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.utils.MailUtil;
import com.rkylin.risk.order.bean.SimpleOrder;
import com.rkylin.risk.service.RuleConstants;
import com.rkylin.risk.service.api.ApiMitouConstants;
import com.rkylin.risk.service.bean.MitouResponseParam;
import com.rkylin.risk.service.bean.MitouRiskDataBean;
import com.rkylin.risk.service.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.service.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.service.bean.PersonFactor;
import com.rkylin.risk.service.biz.MitouBiz;
import com.rkylin.risk.service.biz.MitouRiskBiz;
import com.rkylin.risk.service.biz.XingrenBiz;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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
  @Resource
  private XingrenBiz xingrenBiz;
  @Resource
  private OperateFlowService operateFlowService;
  @Value("${mitou.platid}")
  private Integer platid;
  @Value("${mail.flag}")
  private boolean mailFlag;

  public Map requestMitouRisk(Map<String, Object> map, PersonFactor factor, String simility) {
    Map resultmap = new HashMap();
    try {
      MitouRiskDataRequestParam param = new MitouRiskDataRequestParam();
      MitouRiskDataBean bean = new MitouRiskDataBean();
      param.setFirstKeyTags(0);
      param.setImgDeliveryMode(ApiMitouConstants.MITOU_IMG_DELIVERY_MODE_ROP);
      if (RuleConstants.BANGBANG_CHANNEL_ID.equals(factor.getConstid())
          || RuleConstants.KEZHAN_CONST_ID.equals(factor.getConstid())) {
        param.setImgDecodeKey(ApiMitouConstants.MITOU_IMG_DECODE_KEY_KZW);
      } else {
        param.setImgDecodeKey(ApiMitouConstants.MITOU_IMG_DECODE_KEY_YSJ);
      }
      if (RuleConstants.XINGREN_CHANNEL_ID.equals(factor.getConstid())) {
        param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_APP);
      } else {
        param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_H5);
      }
      //身份信息
      MitouRiskDataBean.CardId cardinfo = bean.new CardId();
      cardinfo.setCardId(factor.getCertificatenumber());
      cardinfo.setName(factor.getUsername());
      cardinfo.setNation(factor.getNation());
      cardinfo.setAddress(factor.getAddress());
      cardinfo.setStartDate(factor.getCertificatestartdate());
      cardinfo.setEndDate(factor.getCertificateexpiredate());
      cardinfo.setCardIdCompare(simility);
      String[] splitUrlkey = factor.getUrlkey().split("\\|");
      if (splitUrlkey.length == 1) {
        cardinfo.setFrontImage(splitUrlkey[0]);
      } else if (splitUrlkey.length == 2) {
        cardinfo.setFrontImage(splitUrlkey[0]);
        cardinfo.setReverseImage(splitUrlkey[1]);
      } else if (splitUrlkey.length == 3) {
        cardinfo.setFrontImage(splitUrlkey[0]);
        cardinfo.setReverseImage(splitUrlkey[1]);
        if (!RuleConstants.XINGREN_CHANNEL_ID.equals(factor.getConstid())) {
          cardinfo.setSelfDeclaration(splitUrlkey[2]);
        }
      }
      //照片信息
      MitouRiskDataBean.Photo Photo = bean.new Photo();
      if(factor.getContracturlkey()!=null){
        String contracturlkey=factor.getContracturlkey();
        contracturlkey.replace("[\"", "").replace("\"]","").replace("\",\"","|");
        Photo.setContractPhoto(contracturlkey);
      }
      if(factor.getLogourlkey()!=null){
        String logourlkey=factor.getLogourlkey();
        logourlkey.replace("[\"", "").replace("\"]","").replace("\",\"","|");
        Photo.setLogoGroupPhoto(logourlkey);
      }

      //联系人信息
      MitouRiskDataBean.Contact contactinfo = bean.new Contact();
      contactinfo.setQq(factor.getQqnum());
      contactinfo.setFirstContactName(factor.getFirstman());
      contactinfo.setFirstContactMobile(factor.getFirstmobile());
      contactinfo.setFirstContactRelationship(factor.getFirstrelation());
      contactinfo.setSecondContactName(factor.getSecondman());
      contactinfo.setSecondContactMobile(factor.getSecondmobile());
      contactinfo.setSecondContactRelationship(factor.getSecondrelation());
      contactinfo.setEmail(factor.getEmail());

      //配偶信息 选填  悦视觉填
      MitouRiskDataBean.MateCardId mateCardId = bean.new MateCardId();
      mateCardId.setCardId(factor.getFirstid());
      mateCardId.setName(factor.getFirstman());
      if(factor.getFirstid()!=null){
        String id=factor.getFirstid();
        id=id.substring(id.length()-1,id.length());
        mateCardId.setSex(Integer.parseInt(id)%2==0?"女":"男");
      }else{
        mateCardId.setSex("女");
      }
      mateCardId.setNation(factor.getMatenation());
      mateCardId.setAddress(factor.getMateaddress());
      mateCardId.setStartDate(factor.getMatestartDate());
      mateCardId.setEndDate(factor.getMateendDate());
      if(factor.getMateurlkey()!=null){
        String[] mateurlkey = factor.getMateurlkey().split("\\|");
        mateCardId.setFrontImage(mateurlkey[0]);
        mateCardId.setReverseImage(mateurlkey[1]);
      }
      mateCardId.setMatesMobile(factor.getFirstmobile());
      if("1".equals(factor.getPhotographyType())){
        mateCardId.setPhotographyType("0");
      }else if("2".equals(factor.getPhotographyType())){
        mateCardId.setPhotographyType("1");
      }
      mateCardId.setChildName(factor.getChildName());
      mateCardId.setChildCardId(factor.getChildCardId());

      //银行信息
      MitouRiskDataBean.BankInfo bankinfo = bean.new BankInfo();
      bankinfo.setCardNo(factor.getTcaccount());
      bankinfo.setBankDeposit(factor.getTaccountbank());
      bankinfo.setBankBranch(factor.getTaccountbranch());
      bankinfo.setBankReservePhone(factor.getMobilephone());
      bankinfo.setBankProvince(factor.getTaccountprovince());
      bankinfo.setBankCity(factor.getTaccountcity());
      bankinfo.setCheckMethod("1");

      //学生
      if ("1".equals(factor.getIsstudent())) {
        bean.setIdentityType(1);
        MitouRiskDataBean.EducationInfo educationInfo = bean.new EducationInfo();
        educationInfo.setProvince(factor.getProvince());
        educationInfo.setCity(factor.getCity());
        educationInfo.setEducation(getEducationInfo(factor.getEducation()));
        educationInfo.setEnrolDate(factor.getEnrolDate() + "0908");
        educationInfo.setSchoolArea(factor.getSchoolArea());
        educationInfo.setUniversityName(factor.getUniversityName());
        bean.setEducationInfo(educationInfo);

        //不是学生
      } else if ("0".equals(factor.getIsstudent())) {
        bean.setIdentityType(2);
        MitouRiskDataBean.WorkInfo workInfo = bean.new WorkInfo();
        workInfo.setUnitName(factor.getWorkCompanyName());
        workInfo.setJobTitle(factor.getWorkTitle()==null?factor.getOccupation():factor.getWorkTitle());
        workInfo.setSalary(factor.getSalary());
        workInfo.setUnitAddress(factor.getWorkCompanyAddress());
        workInfo.setUnitFullAddress(factor.getUnitFullAddress());
        workInfo.setCurrentAddress(
            factor.getCurrentAddress() == null ? factor.getAddress() : factor.getCurrentAddress());
        workInfo.setCurrentFullAddress(factor.getCurrentFullAddress());
        bean.setWorkInfo(workInfo);
      }

      bean.setCardId(cardinfo);
      bean.setPhoto(Photo);
      bean.setContact(contactinfo);
      bean.setMateCardId(mateCardId);
      bean.setBankInfo(bankinfo);
      bean.setContactsList(factor.getMobilelist());
      param.setPlatuserid(factor.getUserid());
      param.setRiskData(bean);

      log.info("需要提交给米投的数据-->" + param);

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
        resultmap.put("result", "datafalse");
        if (response.getMsg() != null) {
          resultmap.put("msg", response.getMsg().length() <= 200 ? response.getMsg()
              : response.getMsg().substring(0, 200));
        }
        return resultmap;
      }
    } catch (Exception e) {
      resultmap.put("result", "exception");
      resultmap.put("msg", "米投--提交风控参数接口异常");
      log.info("米投--提交风控参数接口异常");
      e.printStackTrace();
      return resultmap;
    }
  }

  @Override
  public Map requestMitouForOrder(SimpleOrder simpleOrder) {
    Map resultmap = new HashMap();
    Map photomap = new HashMap();
    try {
      OperateFlow operateFlow=operateFlowService.queryByCheckorderid(simpleOrder.getCheckorderid());
      MitouRiskDataRequestParam param = new MitouRiskDataRequestParam();
      param.setFirstKeyTags(0);
      MitouRiskDataBean bean = new MitouRiskDataBean();
      param.setImgDeliveryMode(ApiMitouConstants.MITOU_IMG_DELIVERY_MODE_ROP);
      param.setImgDecodeKey(ApiMitouConstants.MITOU_IMG_DECODE_KEY_YSJ);
      param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_APP);
      if(operateFlow!=null){
        String isstudent=operateFlow.getIsstudent();
        if("1".equals(isstudent)){
          bean.setIdentityType(1);
        }else{
          bean.setIdentityType(2);
        }
      }
      String urlkey = "";
      String loanterm = "";
      String funderprovider = "";
      String longitude = "";
      String latitude = "";
      if (simpleOrder.getSvcString() != null) {
        JSONObject js = JSONObject.fromObject(simpleOrder.getSvcString());
        urlkey = js.get("urlkey") == null ? "" : js.get("urlkey").toString();
        longitude = js.get("longitude") == null ? "0" : js.get("longitude").toString();
        latitude = js.get("latitude") == null ? "0" : js.get("latitude").toString();
        funderprovider = js.get("fundProvider") == null ? "融数金服" : js.get("fundProvider").toString();
        loanterm = js.get("loanTerm") == null ? "" : js.get("loanTerm").toString();
      }
      MitouRiskDataBean.Order order = bean.new Order();
      order.setOrderNo(simpleOrder.getUserOrderId());
      order.setOrderName(simpleOrder.getGoodsName());
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
      Date date = (Date) sdf1.parse(simpleOrder.getOrderTime());
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      order.setApplyDate(sdf2.format(date));
      order.setSubmitCheckDate(sdf2.format(date));
      String svcid = simpleOrder.getSvcId();
      if (svcid != null) {
        if (svcid.contains("YLMR")) {
          order.setOrderScene("0");
        } else if (svcid.contains("SYCP")) {
          order.setOrderScene("2");
        } else if (svcid.contains("LYCP")) {
          order.setOrderScene("2");
        }
      }
      order.setOrderPrice(new Amount(simpleOrder.getAmount()).divide(new Amount(100)).toString());
      order.setLoanAmount(new Amount(simpleOrder.getAmount()).divide(new Amount(100)).toString());
      order.setLoanTerm(loanterm);
      order.setFundProvider(funderprovider);
      order.setMerchantName(simpleOrder.getUserRelateName());
      order.setMerchantId(simpleOrder.getSvcId());
      simpleOrder.setSvcString(urlkey);
      photomap = xingrenBiz.requestPhotoinfo(simpleOrder);
      order.setFaceImage(urlkey);
      order.setFaceCompare(photomap.get("simitity").toString());
      order.setLongitude(longitude);
      order.setLatitude(latitude);
      bean.setOrder(order);
      param.setRiskData(bean);
      param.setPlatuserid(simpleOrder.getUserId());
      MitouResponseParam<MitouRiskDataResponseParam> riskrequest =
          mitouBiz.riskrequest(platid, param);
      MitouResponseParam<MitouRiskDataResponseParam> response = riskrequest;
      log.info("----------Mitouresponse:" + response.toString());
      if (response.getFlag() == 0) {
        resultmap.put("result", "success");
      } else {
        resultmap.put("result", "datafalse");
        resultmap.put("msg", response.getMsg());
      }
      resultmap.put("policeresult", photomap.get("policemsg"));
      return resultmap;
    } catch (Exception e) {
      resultmap.put("result", "exception");
      resultmap.put("msg", "米投--提交风控参数接口异常");
      resultmap.put("policeresult", photomap.get("policemsg"));
      log.info("米投--提交风控参数接口异常");
      e.printStackTrace();
      return resultmap;
    }
  }

  public String getEducationInfo(String education) {
    String educationStr = "";
    switch (education) {
      case "初中及以下":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "普通专科":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "高中":
        educationStr = "PUTONG_ZHUANKE";
        break;
      case "大专":
        educationStr = "PUTONG_TOPUP";
        break;
      case "普通本科":
        educationStr = "PUTONG_BENKE";
        break;
      case "6":
        educationStr = "PUTONG_BENKE";
        break;
      case "硕士":
        educationStr = "PUTONG_SHUOSHI";
        break;
      case "博士":
        educationStr = "PUTONG_BOSHI";
        break;
      case "0":
        educationStr = "PUTONG_ZHUANKE";
        break;
      default:
        educationStr="PUTONG_BENKE";
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



