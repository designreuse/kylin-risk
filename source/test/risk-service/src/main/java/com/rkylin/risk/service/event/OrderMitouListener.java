package com.rkylin.risk.service.event;

import com.google.common.eventbus.Subscribe;
import com.rkylin.risk.core.entity.OperateFlow;
import com.rkylin.risk.core.service.OperateFlowService;
import com.rkylin.risk.service.bean.MitouOrderBean;
import com.rkylin.risk.service.biz.MitouRiskBiz;
import com.rkylin.risk.service.biz.impl.BaseBizImpl;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiuxian
 * @create 2016-09-28 18:33
 **/
@Slf4j
public class OrderMitouListener extends BaseBizImpl {

  @Resource
  private MitouRiskBiz mitouRiskBiz;
  @Resource
  private OperateFlowService operateFlowService;

  @Subscribe
  public void postOrderToMitou(MitouOrderBean event) {
    log.info("提交订单到米投，checkorderid:{}", event.getSimpleOrder().getCheckorderid());
    Map resultmap = mitouRiskBiz.requestMitouForOrder(event.getSimpleOrder());
    String flag = resultmap.get("result").toString();
    String policeresult=resultmap.get("policeresult").toString();
    log.info("---------flag:{}",flag);
    OperateFlow operateFlow =
        operateFlowService.queryByCheckorderid(event.getSimpleOrder().getCheckorderid());
    if (operateFlow != null) {
      if ("success".equals(flag)) {
        //已提交订单到米投
        operateFlow.setOrderstatus("1");
      } else if ("datafalse".equals(flag)) {
        operateFlow.setOrderstatus("4");
      } else if ("exception".equals(flag)) {
        operateFlow.setOrderstatus("2");
      }
      operateFlow.setRiskmsg(operateFlow.getRiskmsg()+";"+policeresult);
      operateFlow.setOrdercommitcount("1");
      operateFlowService.updateOperFlowByOrder(operateFlow);
      log.info("修改工作流订单提交状态，checkorderid:{}",event.getSimpleOrder().getCheckorderid());
    }

  }
}
