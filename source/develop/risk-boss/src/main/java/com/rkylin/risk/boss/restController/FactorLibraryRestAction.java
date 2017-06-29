package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.FactorLibrary;
import com.rkylin.risk.core.service.FactorLibraryService;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lina on 2016-7-28.
 */
@Slf4j
@RestController
@RequestMapping("/api/1/factorLibrary")
public class FactorLibraryRestAction {

  @Resource
  private FactorLibraryService factorLibraryService;

  @RequestMapping("modifyFactorStatus")
  public FactorLibrary modifyFactorStatus(@RequestParam String id, String status) {
    factorLibraryService.updateFactorStatus(id, status);
    return new FactorLibrary();
  }

  @RequestMapping("addFactorLibrary")
  public FactorLibrary addFactorLibrary(@ModelAttribute FactorLibrary factor) {
    factorLibraryService.addFactoryLibrary(factor);
    return new FactorLibrary();
  }

  @RequestMapping("updateFactorLibrary")
  public FactorLibrary updateFactorLibrary(@ModelAttribute FactorLibrary factor) {
    factorLibraryService.update(factor);
    return new FactorLibrary();
  }

  @RequestMapping("queryFactorByType")
  public List<FactorLibrary> queryFactorByType(@RequestParam String type) {
    return factorLibraryService.queryByType(type);
  }
}
