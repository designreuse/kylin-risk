package com.rkylin.risk.boss.restController;

import com.rkylin.risk.core.entity.FactorLibrary;
import com.rkylin.risk.core.service.FactorLibraryService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
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

  @RequestMapping("checkCode")
  public boolean checkCode(@RequestParam String code, @RequestParam String id,
      HttpServletRequest request) {
    if (!StringUtils.isEmpty(code)) {
      FactorLibrary factor = new FactorLibrary();
      if (!StringUtils.isEmpty(id)) {
        factor.setId(Short.parseShort(id));
      }
      factor.setCode(code);
      int i = factorLibraryService.checkExistCode(factor);
      if (i > 0) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }
}
