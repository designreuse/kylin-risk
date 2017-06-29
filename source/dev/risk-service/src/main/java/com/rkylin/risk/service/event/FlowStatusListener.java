package com.rkylin.risk.service.event;

import com.google.common.eventbus.Subscribe;
import com.rkylin.oprsystem.system.service.FlowStatusChangeService;
import com.rkylin.risk.service.bean.ListenerBean;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lina on 2016-8-23.
 */
@Slf4j
public class FlowStatusListener {
  @Resource
  private  FlowStatusChangeService flowStatusChangeService;
  @Resource
  private  FlowStatusChangeService newflowStatusChangeService;

  @Subscribe
  public void updateFlowStatusListen(ListenerBean event) {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      log.warn("sleep睡眠被打断, 异常信息{}", e);
    }

        if("1".equals(event.getBussinesstype())){
      log.info("大金融，修改新运营平台审批流程状态为：{},原因为：{},风险提示为：{}", event.getStatus(), event.getReason(),
          event.getRiskmsg());
      newflowStatusChangeService.updateFlowStatusId(event.getCheckorderid(),
          event.getStatus(), event.getReason(), event.getRiskmsg());
    }else{
      log.info("课栈卡，修改运营平台审批流程状态为：{},原因为：{},风险提示为：{}", event.getStatus(), event.getReason(),
          event.getRiskmsg());
      flowStatusChangeService.updateFlowStatusId(event.getCheckorderid(),
          event.getStatus(), event.getReason(), event.getRiskmsg());
    }

  }
}
