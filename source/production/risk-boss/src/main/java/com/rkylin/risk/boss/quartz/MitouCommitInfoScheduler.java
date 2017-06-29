package com.rkylin.risk.boss.quartz;

import com.google.common.collect.Lists;
import com.rkylin.risk.boss.biz.MitouBiz;
import com.rkylin.risk.boss.resteasy.bean.ApiMitouConstants;
import com.rkylin.risk.boss.resteasy.bean.MitouResponseParam;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataBean;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.CustomerMobilelist;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.IdentifyRecord;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.CustomerMobilelistService;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.IdentifyRecordService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.utils.MailUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.boss.resteasy.bean.ApiMitouConstants.MITOU_API_FALURE;
import static com.rkylin.risk.boss.resteasy.bean.ApiMitouConstants.MITOU_API_SUCCESS;

/**
 * Created by chenfumin on 2016/11/21.
 */
@Slf4j
@Component("mitouCommitInfoScheduler")
public class MitouCommitInfoScheduler {

  @Resource
  private OperateFlowService operateFlowService;

  @Resource
  private CustomerinfoService customerinfoService;

  @Resource
  private CustomerMobilelistService customerMobilelistService;

  @Resource
  private IdentifyRecordService identifyRecordService;

  @Resource
  private MitouBiz mitouBiz;

  @Resource
  private OrderService orderService;

  @Resource
  private MailUtil mailUtil;

  @Value("${yueshijue.decode.privateKey}")
  private String yueshijue_privateKey;

  @Value("${mitou.platid}")
  private Integer platid;

  @Value("${mail.flag}")
  private boolean mailFlag;

  public void resubmitMitouInfo() {

    log.info("【米投定时提交风控数据】------------- 定时任务开始, 时间: {} -------------",
        LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

    Map params = new HashMap();
    params.put("mtApiStatus", MITOU_API_FALURE);
    List<String> list = new ArrayList<>();
    list.add(ApiMitouConstants.MEIRONG_CHANNEL_ID);
    list.add(ApiMitouConstants.XINGREN_CHANNEL_ID);
    params.put("list", list);
    List<OperateFlow> operateFlows = operateFlowService.queryByConstidAndStatus(params);
    if (operateFlows != null && operateFlows.size() > 0) {
      for (OperateFlow operateFlow : operateFlows) {
        String pcount =
            operateFlow.getPersoncommitcount() == null ? "0" : operateFlow.getPersoncommitcount();
        String ordercount =
            operateFlow.getOrdercommitcount() == null ? "0" : operateFlow.getOrdercommitcount();
        try {
          Customerinfo customerinfo = customerinfoService.queryOne(operateFlow.getCustomerid());
          if (customerinfo != null) {
            MitouRiskDataRequestParam param = new MitouRiskDataRequestParam();
            MitouRiskDataBean bean = new MitouRiskDataBean();
            param.setFirstKeyTags(0);
            param.setImgDeliveryMode(ApiMitouConstants.MITOU_IMG_DELIVERY_MODE_ROP);
            if (ApiMitouConstants.BANGBANG_CHANNEL_ID.equals(operateFlow.getConstId())
                || ApiMitouConstants.KEZHAN_CHANNEL_ID.equals(operateFlow.getConstId())) {
              param.setImgDecodeKey(ApiMitouConstants.MITOU_IMG_DECODE_KEY_KZW);
            } else {
              param.setImgDecodeKey(ApiMitouConstants.MITOU_IMG_DECODE_KEY_YSJ);
            }
            if (ApiMitouConstants.XINGREN_CHANNEL_ID.equals(operateFlow.getConstId())) {
              param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_APP);
            } else {
              param.setDataSource(ApiMitouConstants.MITOU_DATA_SOURCE_H5);
            }
            //身份信息
            MitouRiskDataBean.CardId cardinfo = bean.new CardId();
            cardinfo.setCardId(customerinfo.getCertificateid());
            cardinfo.setName(customerinfo.getCustomername());
            cardinfo.setNation(customerinfo.getNation());
            cardinfo.setAddress(customerinfo.getAddress());
            cardinfo.setStartDate(customerinfo.getCertificatestartdate());
            cardinfo.setEndDate(customerinfo.getCertificateexpiredate());
            IdentifyRecord identifyRecord = new IdentifyRecord();
            identifyRecord.setCheckorderid(operateFlow.getCheckorderid());
            identifyRecord.setCustomerid(operateFlow.getCustomerid());
            identifyRecord.setInterfacename("idcardMatchPolicePhoto");
            identifyRecord=identifyRecordService.queryOne(identifyRecord);
            if (identifyRecord != null) {
              cardinfo.setCardIdCompare(identifyRecord.getSimilarity());
            } else {
              cardinfo.setCardIdCompare("0");
            }

            String[] splitUrlkey = operateFlow.getUrlkeys().split("\\|");
            if (splitUrlkey.length == 1) {
              cardinfo.setFrontImage(splitUrlkey[0]);
            } else if (splitUrlkey.length == 2) {
              cardinfo.setFrontImage(splitUrlkey[0]);
              cardinfo.setReverseImage(splitUrlkey[1]);
            } else if (splitUrlkey.length == 3) {
              cardinfo.setFrontImage(splitUrlkey[0]);
              cardinfo.setReverseImage(splitUrlkey[1]);
              if (!ApiMitouConstants.XINGREN_CHANNEL_ID.equals(operateFlow.getConstId())) {
                cardinfo.setSelfDeclaration(splitUrlkey[2]);
              }
            }
            //照片信息
            MitouRiskDataBean.Photo Photo = bean.new Photo();
            Photo.setContractPhoto(operateFlow.getContracturlkey());
            Photo.setLogoGroupPhoto(operateFlow.getLogourlkey());

            //联系人信息
            MitouRiskDataBean.Contact contactinfo = bean.new Contact();
            contactinfo.setQq(customerinfo.getQqnum());
            contactinfo.setFirstContactName(customerinfo.getFirstman());
            contactinfo.setFirstContactMobile(customerinfo.getFirstmobile());
            contactinfo.setFirstContactRelationship(customerinfo.getFirstrelation());
            contactinfo.setSecondContactName(customerinfo.getSecondman());
            contactinfo.setSecondContactMobile(customerinfo.getSecondmobile());
            contactinfo.setSecondContactRelationship(customerinfo.getSecondrelation());
            contactinfo.setEmail(customerinfo.getEmail());

            //配偶信息 选填  悦视觉填
            MitouRiskDataBean.MateCardId mateCardId = bean.new MateCardId();
            mateCardId.setCardId(customerinfo.getFirstid());
            mateCardId.setName(customerinfo.getFirstman());
            mateCardId.setSex(customerinfo.getMatesex());
            mateCardId.setNation(customerinfo.getMatenation());
            mateCardId.setAddress(customerinfo.getMateaddress());
            mateCardId.setStartDate(customerinfo.getMatestartdate());
            mateCardId.setEndDate(customerinfo.getMateenddate());
            String[] mateurlkey = customerinfo.getMateurlkey().split("\\|");
            mateCardId.setFrontImage(mateurlkey[0]);
            mateCardId.setReverseImage(mateurlkey[1]);
            mateCardId.setMatesMobile(customerinfo.getFirstmobile());
            if ("1".equals(operateFlow.getPhotographytype())) {
              mateCardId.setPhotographyType("0");
            } else if ("2".equals(operateFlow.getPhotographytype())) {
              mateCardId.setPhotographyType("1");
            }
            mateCardId.setChildName(customerinfo.getChildname());
            mateCardId.setChildCardId(customerinfo.getChildcardid());

            //银行信息
            MitouRiskDataBean.BankInfo bankinfo = bean.new BankInfo();
            bankinfo.setCardNo(customerinfo.getTcaccount());
            bankinfo.setBankDeposit(customerinfo.getBankname());
            bankinfo.setBankBranch(customerinfo.getBankbranch());
            bankinfo.setBankReservePhone(customerinfo.getMobilephone());
            bankinfo.setBankProvince(customerinfo.getTaccountprovince());
            bankinfo.setBankCity(customerinfo.getTaccountcity());
            bankinfo.setCheckMethod("1");

            //学生
            if ("1".equals(operateFlow.getIsstudent())) {
              bean.setIdentityType(1);
              MitouRiskDataBean.EducationInfo educationInfo = bean.new EducationInfo();
              educationInfo.setProvince(operateFlow.getSchoolprovince());
              educationInfo.setCity(operateFlow.getSchoolcity());
              educationInfo.setEducation(getEducationInfo(customerinfo.getEducation()));
              educationInfo.setEnrolDate(operateFlow.getEnroldate() + "0908");
              educationInfo.setSchoolArea(operateFlow.getSchoolarea());
              educationInfo.setUniversityName(
                  operateFlow.getCorporationname() == null ? operateFlow.getFinishschool()
                      : operateFlow.getCorporationname());
              bean.setEducationInfo(educationInfo);

              //不是学生
            } else if ("0".equals(operateFlow.getIsstudent())) {
              bean.setIdentityType(2);
              MitouRiskDataBean.WorkInfo workInfo = bean.new WorkInfo();
              workInfo.setUnitName(operateFlow.getWorkcompanyname());
              workInfo.setJobTitle(operateFlow.getWorktitle());
              workInfo.setSalary(operateFlow.getSalary());
              workInfo.setUnitAddress(operateFlow.getWorkcompanyaddress());
              workInfo.setUnitFullAddress(operateFlow.getUnitfulladdress());
              workInfo.setCurrentAddress(
                  operateFlow.getCurrentaddress() == null ? customerinfo.getAddress()
                      : operateFlow.getCurrentaddress());
              workInfo.setCurrentFullAddress(operateFlow.getCurrentfulladdress());
              bean.setWorkInfo(workInfo);
            }

            if("2".equals(operateFlow.getOrderstatus())){
              Order orders= orderService.queryByCheckorderid(operateFlow.getCheckorderid());

              MitouRiskDataBean.Order order = bean.new Order();
              order.setOrderNo(orders.getUserorderid());
              order.setOrderName(orders.getGoodsname());

              SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
              order.setApplyDate(sdf2.format(orders.getOrdertime()));
              order.setSubmitCheckDate(sdf2.format(orders.getOrdertime()));
              String svcid = orders.getSvcid();
              if (svcid != null) {
                if (svcid.contains("YLMR")) {
                  order.setOrderScene("0");
                } else if (svcid.contains("SYCP")) {
                  order.setOrderScene("2");
                } else if (svcid.contains("LYCP")) {
                  order.setOrderScene("2");
                }
              }
              order.setOrderPrice(orders.getAmount().divide(new Amount(100)).toString());
              order.setLoanAmount(orders.getAmount().divide(new Amount(100)).toString());
              order.setLoanTerm(orders.getLoanterm());
              order.setFundProvider(orders.getFundprovider());
              order.setMerchantName(orders.getUserrelatename());
              order.setMerchantId(svcid);

              IdentifyRecord identifyRecords = new IdentifyRecord();
              identifyRecords.setCheckorderid(operateFlow.getCheckorderid());
              identifyRecords.setCustomerid(operateFlow.getCustomerid());
              identifyRecords.setInterfacename("photoMatchPolicePhoto");
              identifyRecords=identifyRecordService.queryOne(identifyRecords);
              if (identifyRecords != null) {
                order.setFaceCompare(identifyRecords.getSimilarity());
              } else {
                order.setFaceCompare("0");
              }
              order.setFaceImage(orders.getUrlkey());
              order.setLongitude(orders.getLongitude());
              order.setLatitude(orders.getLatitude());
              bean.setOrder(order);
            }

            bean.setCardId(cardinfo);
            bean.setPhoto(Photo);
            bean.setContact(contactinfo);
            bean.setMateCardId(mateCardId);
            bean.setBankInfo(bankinfo);
            CustomerMobilelist customerMobilelist =
                customerMobilelistService.queryOne(operateFlow.getCustomerid());
            if (customerMobilelist != null) {
              bean.setContactsList(customerMobilelist.getMobilelist());
            } else {
              bean.setContactsList("");
            }
            param.setPlatuserid(operateFlow.getCustomerid());
            param.setRiskData(bean);

            MitouResponseParam<MitouRiskDataResponseParam> riskrequest =
                mitouBiz.riskrequest(platid, param);
            MitouResponseParam<MitouRiskDataResponseParam> response = riskrequest;
            log.info("----------Mitouresponse:" + response.toString());
            response = mitouBiz.riskrequest(platid, param);
            if (response != null && response.getFlag() == 0) {
              log.info("【米投定时提交风控数据】工作流号: {} 重新提交风控数据成功", operateFlow.getCheckorderid());
              operateFlow.setMtApiStatus(MITOU_API_SUCCESS);
              operateFlow.setOrderstatus(MITOU_API_SUCCESS);
            } else {
              log.info("【米投定时提交风控数据】工作流号: {} 重新提交风控数据失败, 原因: 返回码: {}, {}",
                  operateFlow.getCheckorderid(), response.getFlag(),
                  response.getMsg());
              operateFlow.setMtMsg(operateFlow.getMtMsg() + response.getMsg());
              operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_FORBIDE);
            }

            operateFlow.setPersoncommitcount(String.valueOf(Integer.valueOf(pcount) + 1));
            operateFlow.setOrdercommitcount(String.valueOf(Integer.valueOf(ordercount) + 1));
            operateFlowService.updateMitouResponse(operateFlow);
          }
        } catch (Exception e) {
          operateFlow.setPersoncommitcount(String.valueOf(Integer.valueOf(pcount) + 1));
          operateFlow.setOrdercommitcount(String.valueOf(Integer.valueOf(ordercount) + 1));
          operateFlowService.updateMitouResponse(operateFlow);
          log.info("定时提交米投工作流异常：{}",operateFlow.getCheckorderid());
        }
      }
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
        educationStr = "PUTONG_BENKE";
        break;
      case "7":
        educationStr = "PUTONG_SHUOSHI";
        break;
      case "8":
        educationStr = "PUTONG_BOSHI";
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
              .template("template/mail/user-remind.vm")
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
