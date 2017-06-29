package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.FactorTempl;
import com.rkylin.risk.core.service.FactorTemplService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lina on 2016-5-23.
 */
@RestController
@RequestMapping("api/1/factorTempl")
public class FactorTemplRestAction {
  @Resource
  private FactorTemplService factorTemplService;

  @RequestMapping("addFactorTempl")
  public FactorTempl addFactorTempl(@RequestParam String libraryid, String groupid) {
    factorTemplService.add(groupid, libraryid);
    return new FactorTempl();
  }

  @RequestMapping("updateFactorTempl")
  public FactorTempl updateFactorTempl(@ModelAttribute FactorTempl factorTempl) {
    factorTemplService.updateFactorTempl(factorTempl);
    return new FactorTempl();
  }

  @RequestMapping("deleteFactorTempl")
  public FactorTempl deleteFactorTempl(@RequestParam String id) {
    factorTemplService.deleteFactorTempl(id);
    return new FactorTempl();
  }

  @RequestMapping("queryByGroup")
  public List<FactorTempl> queryByGroup(@RequestParam String groupid) {
    return factorTemplService.queryByGroup(Short.parseShort(groupid));
  }
}
