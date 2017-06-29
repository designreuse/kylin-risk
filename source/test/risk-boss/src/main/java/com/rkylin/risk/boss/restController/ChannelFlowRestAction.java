package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.ChannelFlow;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.service.ChannelFlowService;
import com.rkylin.risk.core.service.DictionaryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wjr on 2016-12-28.
 */
@RestController
@RequestMapping("/api/1/channelFlow")
@Slf4j
public class ChannelFlowRestAction {
  @Resource
  private ChannelFlowService channelFlowService;
  @Resource
  private DictionaryService dictionaryService;
  @RequestMapping("deleteChannelFlow")
  public ChannelFlow deleteChannelFlow(@RequestParam String id) {
    channelFlowService.delChannelFlow(id);
    return new ChannelFlow();
  }

  @RequestMapping("addChannelFlow")
  public ChannelFlow addChannelFlow(@ModelAttribute ChannelFlow channelFlow, HttpServletRequest request) {
    if(channelFlow!=null){
      DictionaryCode dic= dictionaryService.queryByDictAndCode(channelFlow.getChannelcode(), channelFlow.getProductcode());
      channelFlow.setChannelname(dic.getDictname());
      channelFlow.setProductname(dic.getName());
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      channelFlow.setOperator(auth.getRealname());
      channelFlowService.addChannelFlow(channelFlow);
    }
    return new ChannelFlow();
  }

  @RequestMapping("updateChannelFlow")
  public ChannelFlow updateChannelFlow(@ModelAttribute ChannelFlow channelFlow) {
    channelFlowService.updateChannelFlow(channelFlow);
    return new ChannelFlow();
  }

  @RequestMapping("checkExistProduct")
  public boolean checkExistProduct(@RequestParam String productcode) {
    if (!StringUtils.isEmpty(productcode)) {
      ChannelFlow channelFlow = new ChannelFlow();
      channelFlow.setProductcode(productcode);
      int i = channelFlowService.checkExistProduct(channelFlow);
      if (i > 0) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

}
