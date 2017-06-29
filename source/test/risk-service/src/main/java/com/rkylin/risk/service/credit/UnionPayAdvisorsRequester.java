package com.rkylin.risk.service.credit;

import com.rkylin.risk.commons.entity.Amount;
import com.rkylin.risk.core.dto.MailBean;
import com.rkylin.risk.core.entity.CreditResult;
import com.rkylin.risk.core.enumtype.CreditProductType;
import com.rkylin.risk.core.service.CreditResultService;
import com.rkylin.risk.core.utils.MailUtil;
import com.rkylin.risk.service.bean.CreditRequestEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import upa.client.UPAClient;

/**
 * Created by tomalloc on 16-11-30.
 */
@Slf4j
public class UnionPayAdvisorsRequester extends AbstractCreditRequester {

  private String resourceId;

  @Resource
  private CreditResultService creditResultService;

  public UnionPayAdvisorsRequester(String resourceId) {
    this.resourceId = resourceId;
  }

  @Resource
  private UPAClient upaClient;

  @Resource
  private MailUtil mailUtil;

  @Setter
  private boolean sendMail;




  private String[] unionpayInterruptNotifyMail;

  private static final String UNION_SERVICE_PAUSE_NOTIFY = "template/credit-unionpay.vm";

  public void setUnionpayInterruptNotifyMail(String unionpayInterruptNotifyMail) {
    if(unionpayInterruptNotifyMail!=null){
      this.unionpayInterruptNotifyMail = unionpayInterruptNotifyMail.split(",");
    }
  }

  @Override
  public List<CreditResult> queryCreditResult(CreditRequestEntity requestEntity, String[] module) {
    return creditResultService.queryUnionPayCreditResult(requestEntity.getBankCard());
  }

  @Override protected CreditResult request(CreditResult creditResult, String[] module) {
    String bankCard = creditResult.getBankCard();
    int maxRetryCount=3;
    int retryCount=0;
    while (retryCount<maxRetryCount){
      retryCount++;
      JSONObject jsonObject = upaClient.getCommonScoresByAccountNo(bankCard, resourceId);
      String code = jsonObject.getString("error_code");
      //错误码描述
      String riskCode = CreditResultCode.SYSTEM_ERROR;
      int price = 0;
      if ("0".equals(code)) {
        //TODO 错误码处理
        riskCode = CreditResultCode.TARGET_HIT;
        price = 3;
      } else if ("10005".equals(code)) {
        riskCode = CreditResultCode.TARGET_MISSING;
      } else if ("10101".equals(code)) {
        log.warn("connect unionpay advisor vpn exception,current retry count={}",retryCount);
        //客户端连接服务器异常，重拾3次
        if(retryCount!=maxRetryCount){
          continue;
        }
        String workflowId = creditResult.getWorkflowId();
        String orderId = creditResult.getOrderId();
        log.error("unionpay advisor service interrupt,request bankcard={},workflowId={},orderId={}",
            bankCard, workflowId, orderId);
        Map<String, Object> map = new HashMap<>();
        map.put("bankcard", bankCard);
        map.put("workflowId", workflowId);
        map.put("orderId", orderId);

        if(sendMail){
          MailBean mailBean = MailBean.builder()
              .subject("银联智策服务中断")
              .data(map)
              .template(UNION_SERVICE_PAUSE_NOTIFY)
              .toEmails(unionpayInterruptNotifyMail)
              .build();
          try {
            mailUtil.send(mailBean);
          } catch (MessagingException e) {
            log.error("银联智策服务中断，发送邮件通知失败", e);
          }
        }
      }
      creditResult.setResult(jsonObject.toString());
      creditResult.setCreditProduct(CreditProductType.UNIONPAY.name());
      creditResult.setCreditCode(code);
      creditResult.setRiskCode(riskCode);
      creditResult.setRequestTime(new DateTime());
      //银联智策不存在模块
      creditResult.setCreditModuleId(-1);
      //TODO 单价处理
      creditResult.setPrice(new Amount(price));
      break;
    }

    return creditResult;
  }
}
