package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.dto.ChannelsBean;
import com.rkylin.risk.core.entity.Authorization;
import com.rkylin.risk.core.entity.Factor;
import com.rkylin.risk.core.service.FactorService;
import com.rkylin.risk.core.service.OperatorLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 201508240185 on 2015/10/9.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/factor")
public class FactorRestAtion {
  @Resource
  private FactorService factorService;
  @Resource
  private OperatorLogService operatorLogService;

  @RequestMapping("addCustFactor")
  public Factor addFactor(@ModelAttribute Factor factor, ChannelsBean channels,
      @RequestParam String addType, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    Factor addfactor = factorService.addFactor(factor, "00", "01", auth, addType, channels);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "Factor", "添加");
    return addfactor;
  }

  @RequestMapping("modifyCustFactor")
  public Factor modifyFactor(@ModelAttribute Factor factor, ChannelsBean channels,
      @RequestParam String beforeWeight, String addType, HttpServletRequest request) {
    Authorization auth = (Authorization) request.getSession().getAttribute("auth");
    Factor addfactor = factorService.modifyFactor(factor, auth, beforeWeight, addType, channels);
    operatorLogService.insert(auth.getUserId(), auth.getUsername(), "Factor", "更新");
    return addfactor;
  }

  @RequestMapping("factorDelete")
  public Factor deleteFactor(@RequestParam String id) {
    factorService.deleteFactor(Integer.valueOf(id));
    return new Factor();
  }
}
