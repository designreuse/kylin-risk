package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.entity.AbnorIndustry;
import com.rkylin.risk.core.service.AbnorIndustryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by 201507270241 on 2015/8/28.
 */
@Controller
@RequestMapping("abnorIndustry")
@Slf4j
public class AbnorIndustryAction {
  @Resource
  private AbnorIndustryService abnorIndustryService;

  @RequestMapping("toQueryAbnorIndustry")
  public ModelAndView toQueryAbnorIndustry(@ModelAttribute AbnorIndustry abnorIndustrys) {
    ModelAndView view = new ModelAndView("abnorIndustry/abnorIndustryQuery");
    view.addObject("abnorIndustrys", abnorIndustrys);
    return view;
  }

  @RequestMapping("toAjaxGetAbnorIndustryDetail")
  public ModelAndView queryOne(@ModelAttribute AbnorIndustry abnorIndustrys,
      @RequestParam Integer ids, String dealType) {
    AbnorIndustry abnorIndustry = abnorIndustryService.queryOne(ids);
    ModelAndView view = new ModelAndView("abnorIndustry/abnorIndustryDetail");
    //dealType  modify---->修改  query---->查询
    view.addObject("dealType", dealType);
    view.addObject("abnorIndustrys", abnorIndustrys);
    view.addObject("abnorIndustry", abnorIndustry);
    return view;
  }

  @RequestMapping("_toImportAbnorIndustry")
  public ModelAndView toImportAbnorIndustry() {
    return new ModelAndView("abnorIndustry/abnorIndustryImport");
  }

  @RequestMapping(value = "exportAbnorIndustryExcel")
  public void exportAbnorIndustryExcel(HttpServletResponse resp,
      @ModelAttribute AbnorIndustry abnorIndustry) {
    try {
      resp.setContentType("application/x-download");
      resp.addHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode("异常行业代码.xls", "UTF-8"));
      List<AbnorIndustry> industryList = abnorIndustryService.queryAll(abnorIndustry);
      ExportExcel<AbnorIndustry> ex = new ExportExcel<AbnorIndustry>();
      String[] headers = {"异常行业名称", "异常行业代码", "所属反洗钱行业类型"};
      String[] filedNames = {"codenm", "codeid", "type"};

      OutputStream out = resp.getOutputStream();
      ex.exportExcel("异常行业代码", headers, industryList, filedNames, out, null);
      out.close();
      log.info("excel导出成功！");
    } catch (FileNotFoundException e) {
      log.info(e.getMessage(), e);
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
  }
}
