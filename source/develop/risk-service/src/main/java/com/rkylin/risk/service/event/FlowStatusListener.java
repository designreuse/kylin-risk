package com.rkylin.risk.service.event;

import com.google.common.eventbus.Subscribe;
import com.rkylin.oprsystem.system.service.FlowStatusChangeService;
import com.rkylin.risk.service.bean.ListenerBean;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lina on 2016-8-23.
 */
@Slf4j
public class FlowStatusListener {
  private final FlowStatusChangeService flowStatusService;

  FlowStatusListener(FlowStatusChangeService flowStatusService) {
    this.flowStatusService = flowStatusService;
  }

  @Subscribe
  public void updateFlowStatusListen(ListenerBean event) {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
    }
    log.info("修改运营平台审批流程状态为：{},原因为：{},风险提示为：{}", event.getStatus(), event.getReason(),
        event.getRiskmsg());
    flowStatusService.updateFlowStatusId(event.getCheckorderid(),
        event.getStatus(), event.getReason(), event.getRiskmsg());
  }
}
