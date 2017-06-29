package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.entity.RiskEvent;
import com.rkylin.risk.core.service.CaseDubTxnService;
import com.rkylin.risk.core.service.CaseService;
import com.rkylin.risk.core.service.RiskEventService;
import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by v-cuixiaofang on 2015/9/23.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/riskEvent")
public class RiskEventRestAction {

  @Resource
  private RiskEventService riskEventService;
  @Resource
  CaseService caseService;
  @Resource
  CaseDubTxnService caseDubTxnService;

  /**
   * 添加风险事件查询
   */
  @RequestMapping("addRiskEvent")
  public RiskEvent addRiskEvent(@ModelAttribute RiskEvent riskEvent,
      @RequestParam("fileUpload") CommonsMultipartFile file, String billIds,
      HttpServletRequest request) throws IllegalStateException, IOException {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    RiskEvent addEvent = null;
    if (riskEvent != null) {
      // 判断文件是否存在
      String path = "";
      if (!file.isEmpty()) {
        path = Constants.FILE_PATH + file;
        File localFile = new File(path);
        try {
          FileUtils.copyInputStreamToFile(file.getInputStream(), localFile);
        } catch (IllegalStateException e) {
          log.info(e.getMessage(), e);
        } catch (IOException e) {
          log.info(e.getMessage(), e);
        }
      }
      addEvent = riskEventService.addRiskEvent(riskEvent, billIds, path, auth);
    }
    return addEvent;
  }

  @RequestMapping("_toAddCase")
  public ModelAndView toAddCase(String ids) {
    ModelAndView modelAndView = new ModelAndView("riskEvent/caseAdd");
    modelAndView.addObject("ids", ids);
    return modelAndView;
  }

  @RequestMapping(value = "addCase", method = RequestMethod.POST)
  public RiskEvent addCase(@ModelAttribute Case cases, String addIds, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    riskEventService.addCase(cases, addIds, auth);
    return new RiskEvent();
  }

  @RequestMapping("updateRiskEvent")
  public RiskEvent update(@ModelAttribute RiskEvent riskEvent, HttpServletRequest request) {
    if (riskEvent != null) {
      Authorization auth = (Authorization) request.getSession().getAttribute("auth");
      riskEvent = riskEventService.updateRiskEvent(riskEvent, auth);
    }
    return riskEvent;
  }

  @RequestMapping("deleteEvent")
  public RiskEvent deleteEvent(@RequestParam Integer[] deleteIds, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    riskEventService.deleteRiskEvent(deleteIds, auth);
    return new RiskEvent();
  }
}
