package com.rkylin.risk.boss.quartz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.boss.biz.MitouBiz;
import com.rkylin.risk.boss.resteasy.bean.ApiMitouConstants;
import com.rkylin.risk.boss.resteasy.bean.MitouGetUserInfoRequestParam;
import com.rkylin.risk.boss.resteasy.bean.MitouGetUserInfoResponseParam;
import com.rkylin.risk.boss.resteasy.bean.MitouResponseParam;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.rkylin.risk.boss.resteasy.bean.ApiMitouConstants.MITOU_API_WAIT;

/**
 * Created by qiuxian on 2016-11-14.
 */
@Slf4j
@Component("mitouReportScheduler")
public class MitouReportScheduler {
  @Resource
  private OperateFlowService operateFlowService;
  @Resource
  private MitouBiz mitouBiz;

  @Value("${mitou.platid}")
  private Integer platid;

  public static final String YUESHIJUE_CHANELID = "M000025";

  @Resource
  private ObjectMapper jsonMapper;

  public void GetMitoureport() {
    log.info("定时器获取米投风控报告开始------------------");
    Map map = new HashMap();
    map.put("mtApiStatus", MITOU_API_WAIT);
    List<String> list = new ArrayList<>();
    list.add(ApiMitouConstants.MEIRONG_CHANNEL_ID);
    list.add(ApiMitouConstants.XINGREN_CHANNEL_ID);
    map.put("list", list);
    List<OperateFlow> operateFlowsList = operateFlowService.queryByConstidAndStatus(map);
    log.info("operateFlowsList.size------------------:" + operateFlowsList.size());
    if (operateFlowsList != null && operateFlowsList.size() > 0) {
      for (OperateFlow operateFlow : operateFlowsList) {
        MitouGetUserInfoRequestParam param = new MitouGetUserInfoRequestParam();
        param.setPlatuserid(operateFlow.getCustomerid());
        try {
          MitouResponseParam<MitouGetUserInfoResponseParam> response =
              mitouBiz.requestUserinfo(platid, param);
          log.info(operateFlow.getCheckorderid() + "-----" + (response == null ? ""
              : response.toString()));
          if (response != null && response.getFlag() == 0 && response.getData() != null) {
            if (ApiMitouConstants.NORMAL.equals(response.getData().getMainStatus())
                || ApiMitouConstants.FOREVER_REFUSE.equals(response.getData().getMainStatus())) {
              operateFlow.setMtMsg(response.getMsg());
              operateFlow.setMtReport(jsonMapper.writeValueAsString(response.getData()));
              operateFlow.setMtAvailableAmount(response.getData().getAvailableAmount() == null ? "0"
                  : String.valueOf(response.getData().getAvailableAmount()));
              operateFlow.setMtcreditAmount(response.getData().getCreditAmount() == null ? "0"
                  : String.valueOf(response.getData().getCreditAmount()));
              operateFlow.setMtcreidtScore(response.getData().getCreidtScore());
              operateFlow.setMtmainstatus(response.getData().getMainStatus());
              operateFlow.setMtApiStatus(ApiMitouConstants.MITOU_API_SUCCESS);
              operateFlow.setSceneStatus(response.getData().getSceneStatus().toString());
              operateFlow.setMtverifystatus(
                  response.getData().getCardIdStatus()
                      + "|"
                      + response.getData().getContactStatus()
                      + "|"
                      + response.getData().getMobileAuthStatus()
                      + "|"
                      + response.getData().getStudentInfoStatus()
                      + "|"
                      + response.getData().getBankInfoStatus()
                      + "|"
                      + response.getData().getWorkInfoStatus()
                      + "|"
                      + response.getData().getOnlineRetailerStatus()
                      + "|"
                      + response.getData().getMatesCardidStatus()
                      + "|"
                      + response.getData().getContractStatus()
                      + "|"
                      + response.getData().getOrderStatus()
                    );
              operateFlow.setMtverifyreason(
                  response.getData().getCardIdReason()
                      + "|"
                      + response.getData().getContactReason()
                      + "|"
                      + response.getData().getMobileAuthReason()
                      + "|"
                      + response.getData().getStudentReason()
                      + "|"
                      + response.getData().getBankInfoReason()
                      + "|"
                      + response.getData().getWorkInfoReason()
                      + "|"
                      + response.getData().getOnlineRetailerReason()
                      + "|"
                      + response.getData().getMatesCardidReason()
                      + "|"
                      + response.getData().getContractReason()
                      + "|"
                      + response.getData().getOrderReason()
              );
              operateFlow.setCheckorderid(operateFlow.getCheckorderid());
              operateFlowService.updateMitouResponse(operateFlow);
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          log.info("获取米投风控报告异常------------------checkorderid:" + operateFlow.getCheckorderid());
          continue;
        }
      }
    }
    log.info("定时器获取米投风控报告结束------------------");
  }
}
