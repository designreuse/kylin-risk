package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.WarningSet;
import com.rkylin.risk.core.service.OperatorLogService;
import com.rkylin.risk.core.service.WarningSetService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508031790 on 2015/9/6.
 */

@RestController
@RequestMapping("/api/1/warningset")
public class WarningSetRestAction {
  private static final String Warn = "WarningSet";
  @Resource
  private WarningSetService warningSetService;
  @Resource
  private OperatorLogService operatorLogService;

  @RequestMapping("addWarningSet")
  public WarningSet addWarningSet(@ModelAttribute WarningSet warningset, String operIds,
      HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    warningSetService.addWarningSet(warningset, operIds);
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, Warn, "新增预警设置");
    return warningset;
  }

  @RequestMapping("modifyWarnSet")
  public WarningSet warningsetModify(@ModelAttribute WarningSet ws, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    WarningSet warningSet = warningSetService.update(ws);
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, Warn, "编辑预警设置");
    return warningSet;
  }

  @RequestMapping("delWarnSet")
  public void delWarnSet(String deleteIds, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    warningSetService.delWarnset(deleteIds);
    Short userId = null;
    String userName = "";
    if (auth != null) {
      userId = auth.getUserId();
      userName = auth.getUsername();
    }
    operatorLogService.insert(userId, userName, Warn, "删除预警设置");
  }
}
