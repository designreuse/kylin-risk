package com.rkylin.risk.boss.quartz;

import com.Rop.api.request.FsFileurlGetRequest;
import com.Rop.api.response.FsFileurlGetResponse;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.rkylin.facerecognition.api.vo.ImageEntity;
import com.rkylin.risk.boss.biz.MitouBiz;
import com.rkylin.risk.boss.resteasy.bean.ApiMitouConstants;
import com.rkylin.risk.boss.resteasy.bean.MitouResponseParam;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataBean;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataRequestParam;
import com.rkylin.risk.boss.resteasy.bean.MitouRiskDataResponseParam;
import com.rkylin.risk.boss.util.MagicNumberUtils;
import com.rkylin.risk.boss.util.MulThreadRSAUtils;
import com.rkylin.risk.boss.util.ROPUtil;
import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.exception.RiskRestErrorException;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.core.utils.MailUtil;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
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
  private MitouBiz mitouBiz;

  @Resource
  private ROPUtil fileROP;

  @Resource
  private MailUtil mailUtil;

  @Value("${yueshijue.decode.privateKey}")
  private String yueshijue_privateKey;

  @Value("${mitou.platid}")
  private Integer platid;

  public void resubmitMitouInfo() {

    log.info("【米投定时提交风控数据】------------- 定时任务开始, 时间: {} -------------",
        LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

    Map params = new HashMap();
    params.put("mtApiStatus", MITOU_API_FALURE);
    List<String> list = new ArrayList<>();
    list.add(ApiMitouConstants.MEIRONG_CHANNEL_ID);
    params.put("list", list);

    List<OperateFlow> operateFlows = operateFlowService.queryByConstidAndStatus(params);

    if (operateFlows != null && operateFlows.size() > 0) {

      MitouRiskDataBean bean = null;
      MitouRiskDataRequestParam param = null;
      MitouResponseParam<MitouRiskDataResponseParam> response = null;

      List<String> failList = Lists.newArrayList();

      for (OperateFlow operateFlow : operateFlows) {
        Customerinfo customerinfo = customerinfoService.queryOne(operateFlow.getCustomerid());
        if (customerinfo != null) {

          Map map = getImagemap(operateFlow.getUrlkeys());

          if (map.get("message") != null) {
            log.info("【米投定时提交风控数据】，工作流ID: {}, 获取照片出现异常: {}, 结束本次提交", operateFlow.getCheckorderid(), map.get("message"));
            continue;
          }

          ImageEntity<byte[]> entity1 = (ImageEntity) map.get("idcard");
          ImageEntity<byte[]> entity2 = (ImageEntity) map.get("idcardBack");
          ImageEntity<byte[]> entity3 = (ImageEntity) map.get("idcardPersonPic");

          bean = new MitouRiskDataBean();
          //身份信息
          MitouRiskDataBean.CardId cardinfo = bean.new CardId();
          cardinfo.setCardId(customerinfo.getCertificateid());
          cardinfo.setName(customerinfo.getCustomername());
          cardinfo.setNation(customerinfo.getNation());
          cardinfo.setAddress(customerinfo.getAddress());
          cardinfo.setStartDate(customerinfo.getCertificatestartdate());
          cardinfo.setEndDate(customerinfo.getCertificateexpiredate());
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
          contactinfo.setQq(customerinfo.getQqnum());
          contactinfo.setFirstContactName(customerinfo.getFirstman());
          contactinfo.setFirstContactMobile(customerinfo.getFirstmobile());
          contactinfo.setFirstContactRelationship(customerinfo.getFirstrelation());
          contactinfo.setSecondContactName(customerinfo.getSecondman());
          contactinfo.setSecondContactMobile(customerinfo.getSecondmobile());
          contactinfo.setSecondContactRelationship(customerinfo.getSecondrelation());

          //银行信息
          MitouRiskDataBean.BankInfo bankinfo = bean.new BankInfo();
          bankinfo.setCardNo(customerinfo.getTcaccount());
          bankinfo.setBankDeposit(customerinfo.getBankname());
          bankinfo.setBankBranch(customerinfo.getBankbranch());
          bankinfo.setBankReservePhone(customerinfo.getMobilephone());
          bankinfo.setBankProvince(customerinfo.getTaccountprovince());
          bankinfo.setBankCity(customerinfo.getTaccountcity());

          //学生
          if ("1".equals(operateFlow.getIsstudent())) {
            MitouRiskDataBean.EducationInfo educationInfo = bean.new EducationInfo();
            educationInfo.setEducation(getEducationInfo(customerinfo.getEducation()));
            educationInfo.setEnrolDate(operateFlow.getEnroldate() + "0908");
            educationInfo.setSchoolArea(operateFlow.getSchoolarea());
            educationInfo.setUniversityName(operateFlow.getCorporationname());
            bean.setEducationInfo(educationInfo);
            //不是学生
          } else if ("0".equals(operateFlow.getIsstudent())) {
            MitouRiskDataBean.WorkInfo workInfo = bean.new WorkInfo();
            workInfo.setWorkCompanyName(operateFlow.getWorkcompanyname());
            bean.setWorkInfo(workInfo);
          }
          bean.setCardId(cardinfo);
          bean.setContact(contactinfo);
          bean.setBankInfo(bankinfo);

          param = new MitouRiskDataRequestParam();
          param.setPlatuserid(operateFlow.getCustomerid());
          param.setRisk_data(bean);

          response = mitouBiz.riskrequest(platid, param);

          if (response != null && response.getFlag() == 0) {
            log.info("【米投定时提交风控数据】工作流号: {} 重新提交风控数据成功", operateFlow.getCheckorderid());
            operateFlow.setMtApiStatus(MITOU_API_SUCCESS);
            operateFlowService.updateMitouResponse(operateFlow);
          } else {
            failList.add("<工作流ID: " + operateFlow.getCheckorderid() + ", 用户ID: " + operateFlow.getCustomerid() + ">");
            // 发送邮件, 避免频繁发送邮件，取消发送
            // sendMails(response.getMsg(), operateFlow.getConstId(), operateFlow.getCustomerid(),"WARNSET", Constants.MIDDLE_LEVEL);
            log.info("【米投定时提交风控数据】工作流号: {} 重新提交风控数据失败, 异常原因: 异常代码: {}, {}",
                operateFlow.getCheckorderid(), response.getFlag(),
                response.getMsg());
          }
          int failSize = failList.size();
          log.info("【米投定时提交风控数据】本次共需要重新提交风控数据{}个, 提交成功{}个, 提交失败{}个", operateFlows.size(),
              operateFlows.size() - failSize, failSize);
          if (failSize > 0) {
            log.info("【米投定时提交风控数据】失败的用户ID列表: {}", failList);
          }
        } else {
          log.info("【米投定时提交风控数据】没有找到ID为: {} 对应的用户信息", operateFlow.getCustomerid());
        }
      }
    } else {
      log.info("【米投定时提交风控数据】本次没有需要提交风控数据的流程!");
    }

    log.info("【米投定时提交风控数据】------------- 定时任务结束, 时间: {} -------------",
        LocalDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
  }

  private Map getImagemap(String urlKey) {
    Map<String, Object> imageResult = new HashMap<>();
    StringBuffer sbf = new StringBuffer();
    String[] splitUrlkey = urlKey.split("\\|");
    for (int i = 0; i < splitUrlkey.length; i++) {
      String urlkey = splitUrlkey[i];
      String fileUrl = "";
      FsFileurlGetRequest fileurlRequest = new FsFileurlGetRequest();
      fileurlRequest.setUrl_key(urlkey);
      try {
        FsFileurlGetResponse filrUrlResponse = fileROP.getResponse(fileurlRequest, "json");
        if (filrUrlResponse != null && filrUrlResponse.getFile_url() != null) {
          fileUrl = filrUrlResponse.getFile_url();
          log.info("['" + urlkey + "'ysj文件查询成 功：] " + filrUrlResponse.getBody());
        } else {
          sbf.append("附件下载失败；");
          log.info("['" + urlkey + "'ysj文件下载失败：] " + filrUrlResponse);
          imageResult.put("message", sbf.toString());
          return imageResult;
        }
      } catch (Exception e) {
        log.info(urlkey + "ysj附件下载异常，异常信息:{}", Throwables.getStackTraceAsString(e));
        sbf.append("附件下载异常");
        imageResult.put("message", sbf.toString());
        return imageResult;
      }
      InputStream inputStream = null;

      try {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent",
            "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        inputStream = conn.getInputStream();

        //获取自己数组
        log.info("开始读取数据流");
        byte[] encodedData = IOUtils.toByteArray(inputStream);
        log.info("附件大小：" + new Amount(encodedData.length).divide(1024) + "kb");
        log.info("读取数据流完成");
        byte[] dencodedDate = null;
        //私钥解密
        log.info(urlkey + "-->开始解密");
        dencodedDate = MulThreadRSAUtils.decryptByPublicKey(encodedData, yueshijue_privateKey);
        // Files.write(Paths.get("D:\\byte.123"),dencodedDate);
        log.info(urlkey + "-->解密完成");
        String md5CheckSum = DigestUtils.md5Hex(dencodedDate);
        String suffix = MagicNumberUtils.fileType(dencodedDate);
        log.info("-->图片类型：" + suffix);
        ImageEntity entity = new ImageEntity();
        entity.setType(ImageEntity.Type.BYTES);
        entity.setData(dencodedDate);
        switch (i) {
          case 0:
            imageResult.put("idcard", entity);
            imageResult.put("idcardmd5check", md5CheckSum);
            imageResult.put("idcardsuffix", suffix);
            break;
          case 1:
            imageResult.put("idcardBack", entity);
            imageResult.put("idcardBackmd5check", md5CheckSum);
            imageResult.put("idcardBacksuffix", suffix);
            break;
          case 2:
            imageResult.put("idcardPersonPic", entity);
            imageResult.put("idcardPersonPicmd5check", md5CheckSum);
            imageResult.put("idcardPersonPicsuffix", suffix);
            break;
          default:
            log.info("no photo");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return imageResult;
  }

  public static String getEducationInfo(String education) {
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
      try {
        log.info("邮箱发件人{},发送内容{}", StringUtils.join(operatorEmails, ","),
            emailbody);
        boolean flag = mailUtil.send(mailBean);
        log.info("发送邮件结果为{}", flag);
      } catch (MessagingException e) {
        throw new RiskRestErrorException("系统错误!");
      }
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
