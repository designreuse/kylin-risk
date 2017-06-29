package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Functions;
import com.rkylin.risk.core.service.FunctionService;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @company: rkylin
 * @author: tongzhuyu
 * @since: 2015/8/28 version: 1.0
 */
@RestController
@RequestMapping("api/1/functions")
public class FunctionsRestAction {

  @Resource
  private FunctionService functionService;

  /**
   * 添加功能
   */
  @RequestMapping(value = "addFunction", method = RequestMethod.POST)
  public Functions insert(@ModelAttribute Functions function) {
    if (function != null) {
      function.setCheckflag(Constants.TRUE);
      function.setStatus(Constants.ACTIVE);
      functionService.insert(function);
    }
    return function;
  }

  /**
   * 更新功能
   */
  @RequestMapping(value = "updateFunction", method = RequestMethod.POST)
  public Functions update(@ModelAttribute Functions function) {
    if (function != null) {
      if (StringUtils.isAnyBlank(function.getStatus())) {
        function.setStatus(Constants.INACTIVE);
      }
      functionService.update(function);
    }
    return new Functions();
  }

  /**
   * 删除功能
   */
  @RequestMapping(value = "deleteFunction")
  public Functions delete(@RequestParam Short[] ids) {
    functionService.delete(ids);
    return new Functions();
  }

  @RequestMapping("/exceptions")
  public String exception() {
    throw new NullPointerException();
  }
}
