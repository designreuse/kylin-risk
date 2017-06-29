package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.ChannelFlow;
import com.rkylin.risk.core.service.ChannelFlowService;
import com.rkylin.risk.core.service.DictionaryService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wjr on 2016-12-28.
 */
@Controller
@RequestMapping("channelFlow")
public class ChannelFlowAction {

  @Resource
  private ChannelFlowService channelFlowService;
  @Resource
  private DictionaryService dictionaryService;
  @RequestMapping("toQueryChannelFlow")
  public ModelAndView toQueryChannelFlow(HttpServletRequest request) {
    ModelAndView view = new ModelAndView("channelFlow/channelFlowQuery");
    return view;
  }

  @RequestMapping("toAjaxAddChannelFlow")
  public ModelAndView toAjaxAddChannelFlow(@RequestParam String id, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("channelFlow/channelFlowAdd");
    return view;
  }

  @RequestMapping("toAjaxQueryChannelFlowDetail")
  public ModelAndView toQueryChannelFlowDetail(@RequestParam String id, HttpServletRequest request) {
    ModelAndView view = new ModelAndView("channelFlow/channelFlowModify");
    ChannelFlow channelFlow = channelFlowService.get(Integer.valueOf(id));
    view.addObject("channelFlow", channelFlow);
    return view;
  }

}
