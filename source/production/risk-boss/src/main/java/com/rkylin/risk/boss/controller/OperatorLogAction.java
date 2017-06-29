package com.rkylin.risk.boss.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rkylin.risk.core.entity.Operatorlog;
import com.rkylin.risk.core.service.OperatorLogService;

/**
 * Created by 201508240185 on 2015/8/26.
 */
@Controller
@RequestMapping("operatorLog")
public class OperatorLogAction {

  @Resource
  private OperatorLogService operatorLogService;

  /**
   * 查询跳转
   */
  @RequestMapping("toQueryOperatorLog")
  public ModelAndView toQueryOperatorLog() {
    return new ModelAndView("operatorLog/operatorLogQuery");
  }

  /**
   * 查询操作日志
   */
  @RequestMapping("queryOperatorLogs")
  public ModelAndView queryOperatorLogs(@ModelAttribute Operatorlog operLog,
      @RequestParam String endTimeStr, @RequestParam String startTimeStr) {
    ModelAndView view = new ModelAndView("operatorLog/operatorLogQuery");
    //日期转换格式
    DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    //判断日期是否为空并转换类型
    DateTime start =
        StringUtils.isEmpty(startTimeStr) ? null : DateTime.parse(startTimeStr, dtFormat);
    DateTime end = StringUtils.isEmpty(endTimeStr) ? null : DateTime.parse(endTimeStr, dtFormat);
    //设置日期起始时间
    operLog.setOperatedate(start);
    //查询日志列表
    List<Operatorlog> logs = operatorLogService.query(operLog, end);
    view.addObject("logList", logs);
    view.addObject("operLog", operLog);
    view.addObject("endTime", endTimeStr);
    view.addObject("startTime", startTimeStr);
    return view;
  }
}
