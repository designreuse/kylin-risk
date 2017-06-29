
package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.entity.AbnormalCountrycode;
import com.rkylin.risk.core.service.AbnorCountryService;
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
 * Created by 201507270241 on 2015/8/31.
 */
@Controller
@RequestMapping("abnorCountry")
@Slf4j
public class AbnorCountryAction {
  @Resource
  private AbnorCountryService abnorCountryService;

  @RequestMapping("toQueryAbnorCountry")
  public ModelAndView toQueryAbnorCountry(
      @ModelAttribute AbnormalCountrycode abnormalCountrycodes) {
    ModelAndView view = new ModelAndView("abnorCountry/abnorCountryQuery");
    view.addObject("abnormalCountrycodes", abnormalCountrycodes);
    return view;
  }

  @RequestMapping("getAbnorCountryDetail")
  public ModelAndView queryOne(@ModelAttribute AbnormalCountrycode abnormalCountrycodes,
      @RequestParam Integer ids, String dealType) {
    AbnormalCountrycode abnormalCountrycode = abnorCountryService.queryOne(ids);
    ModelAndView view = new ModelAndView("abnorCountry/abnorCountryDetail");
    //dealType  modify---->修改  query---->查询
    view.addObject("dealType", dealType);
    view.addObject("abnormalCountrycodes", abnormalCountrycodes);
    view.addObject("abnormalCountrycode", abnormalCountrycode);
    return view;
  }

  @RequestMapping("_toImportAbnorCountry")
  public ModelAndView toImportAbnorCountry() {
    return new ModelAndView("abnorCountry/abnorCountryImport");
  }

  @RequestMapping(value = "exportAbnorCountryExcel")
  public void exportAbnorCountryExcel(HttpServletResponse resp,
      @ModelAttribute AbnormalCountrycode abnormalCountrycode) {

    resp.setContentType("application/x-download");
    try {
      resp.addHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode("异常国家代码.xls", "UTF-8"));
      List<AbnormalCountrycode> countrycodeList = abnorCountryService.queryAll(abnormalCountrycode);
      ExportExcel<AbnormalCountrycode> ex = new ExportExcel<AbnormalCountrycode>();
      String[] headers =
          {"中文全称", "中文简称", "英文全称", "英文简称", "英文代码（两位）", "英文代码（三位）", "数字代码", "所属反洗钱组织名单"};
      String[] filedNames =
          {"cnnm", "cnabb", "ennm", "enabb", "twoletter", "threelteeer", "threenum", "type"};

      OutputStream out = resp.getOutputStream();
      ex.exportExcel("异常国家代码", headers, countrycodeList, filedNames, out, null);
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
