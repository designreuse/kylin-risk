package com.rkylin.risk.service.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.core.entity.IdentifyRecord;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.IdentifyRecordService;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.mitou.api.MitouRiskMsgApi;
import java.io.IOException;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 风险信息获取(运营平台查询)
 * Created by chenfumin on 2017/1/17.
 */
@Slf4j
@Component("mitouRiskMsgApi")
public class MitouRiskMsgApiImpl implements MitouRiskMsgApi{

  @Resource
  private OperateFlowService operateFlowService;

  @Resource
  private IdentifyRecordService identifyRecordService;

  @Resource
  private ObjectMapper objectMapper;

  @Override
  public String getRiskMsgMitou(String checkorderid) {

    log.info("----------->风险信息获取(运营平台查询)<--------------");
    log.info("----------->接收到的工作流号: " + checkorderid);

    String riskscore = "0";
    String huotivalue = "0";
    String temp = "0";

    if (StringUtils.isBlank(checkorderid)) {
      return handleResult("1", "工作流号不能为空", riskscore, huotivalue);
    }

    try {

      OperateFlow operateFlow = operateFlowService.queryByCheckorderid(checkorderid);

      if (operateFlow != null) {

        temp = operateFlow.getMtcreidtScore();

        log.info("-------->查询到工作流, 获取到分数：" + temp);

        riskscore = StringUtils.isBlank(temp) ? riskscore : temp;

        IdentifyRecord identifyRecord = new IdentifyRecord();
        identifyRecord.setCheckorderid(operateFlow.getCustomerid());
        identifyRecord.setInterfacename("photoMatchPolicePhoto");

        identifyRecord = identifyRecordService.queryOne(identifyRecord);

        if (identifyRecord != null) {
          temp = identifyRecord.getSimilarity();

          log.info("------>查询到身份表, 获取活体识别度: " + temp);

          huotivalue = StringUtils.isBlank(temp) ? huotivalue : temp;
        } else {
          log.info("=============》没有查询到身份表");
        }

        log.info("查询成功：风险提示分数: {}, 活体识别: {}", riskscore, huotivalue);

        return handleResult("0", "查询成功", riskscore, huotivalue);
      }
    } catch (Exception e) {
      log.info("============》风控异常", e);
      return handleResult("1", "风控异常", riskscore, huotivalue);
    }
    return handleResult("0", "没有对应的工作流信息", riskscore, huotivalue);

  }

  @Setter
  @Getter
  @AllArgsConstructor
  @ToString
  private class Result {
    private String flag;
    private String msg;
    private String riskscore;
    private String huotivalue;
  }

  public String handleResult(String flag, String msg, String riskscore, String huotivalue) {
    Result result = new Result(flag, msg, riskscore, huotivalue);
    if (log.isDebugEnabled()) {
      log.debug("返回结果-------->" + result);
    }
    try {
      return objectMapper.writeValueAsString(result);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

}
