package com.rkylin.risk.service.event;

import com.google.common.eventbus.Subscribe;
import com.rkylin.oprsystem.system.service.FlowStatusChangeService;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.service.bean.RiskMesageBean;
import com.rkylin.risk.service.biz.KezhanBiz;
import com.rkylin.risk.service.biz.impl.BaseBizImpl;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qiuxian
 * @create 2016-09-28 18:33
 **/
@Slf4j
public class PersonOCRListener extends BaseBizImpl {
  @Resource
  private FlowStatusChangeService flowStatusService;
  @Resource
  private KezhanBiz kezhanBiz;
  @Resource
  private OperateFlowService operateFlowService;

  @Subscribe
  public void updateRiskMsgListen(RiskMesageBean event) {
    //OCR接口
    String riskmessage = kezhanBiz.downloadFile(event.getPersonFactor());

    String message = riskmessage + event.getRiskmsg();
    if (StringUtils.isNotEmpty(message)) {
      OperateFlow operateFlow =
          operateFlowService.queryByCheckorderid(event.getPersonFactor().getCheckorderid());
      if (operateFlow == null) {
        operateFlow = new OperateFlow();
        operateFlow.setRiskmsg(message);
        operateFlow.setCheckorderid(event.getPersonFactor().getCheckorderid());
        operateFlowService.insert(operateFlow);
      } else {
        operateFlow.setRiskmsg(message);
        operateFlowService.updateOperteFlow(operateFlow);
      }

      log.info("修改运营平台审批风险提示为：{}", event.getRiskmsg());
      flowStatusService.updateFlowStatusId(event.getPersonFactor().getCheckorderid(),
          null, null, "风控系统返回：" + message);
      //发送邮件-风险提示取消邮件发送
      /*sendMails(message, event.getPersonFactor().getConstid(),
          event.getPersonFactor().getUserid(), "WARNSET", Constants.MIDDLE_LEVEL);*/
      log.info("【dubbo服务】{},风控验证为中风险，原因：{}", event.getPersonFactor().getUserid(), message);
    }
  }



}
