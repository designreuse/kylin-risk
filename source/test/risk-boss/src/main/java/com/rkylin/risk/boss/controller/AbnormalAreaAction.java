package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.entity.AbnormalArea;
import com.rkylin.risk.core.entity.DictionaryCode;
import com.rkylin.risk.core.service.AbnormalAreaService;
import com.rkylin.risk.core.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 201508031790 on 2015/8/31.
 */

@Controller
@RequestMapping("abnormalarea")
@Slf4j
public class AbnormalAreaAction {

  @Resource
  private AbnormalAreaService abnormalAreaService;
  @Resource
  private DictionaryService dictionaryService;

  @RequestMapping("toQueryAbnormalArea")
  public ModelAndView toAbnormalArea(@ModelAttribute AbnormalArea area) {
    ModelAndView mv = new ModelAndView("abnormalarea/abnormal_area");
    mv.addObject("abnormalArea", area);
    return mv;
  }

  @RequestMapping("toAjaxModifyAbnormalArea")
  public ModelAndView toAbnormalAreaModify(@ModelAttribute AbnormalArea area) {
    ModelAndView modelAndView = new ModelAndView("abnormalarea/abnormalareaModify");
    modelAndView.addObject("condition", area);
    //        area=abnormalAreaService.queryById(area);
    modelAndView.addObject("abnormalArea", abnormalAreaService.queryById(area));
    return modelAndView;
  }

/*    @RequestMapping("queryAbnormalArea")
    public  ModelAndView queryAbnormalArea(@ModelAttribute AbnormalArea abnormalArea){
        ModelAndView modelAndView=new ModelAndView("abnormalarea/abnormal_area");
        List<AbnormalArea> abnormalareaList=abnormalAreaService.queryByCondition(abnormalArea);
        modelAndView.addObject("abnormalareaList",abnormalareaList);
        modelAndView.addObject("abnormalArea",abnormalArea);
        return  modelAndView;
    }*/

  @RequestMapping("_toImportAbnormalArea")
  public ModelAndView toImportAbnormalArea() {
    return new ModelAndView("abnormalarea/abnormalareaImport");
  }

  @RequestMapping(value = "exportAbnormalArea")
  public void exportExcel(HttpServletResponse resp, @ModelAttribute AbnormalArea abnormalArea) {
    try {
      resp.setContentType("application/x-download");
      resp.addHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode("异常地区代码.xls", "UTF-8"));
      List<AbnormalArea> list = abnormalAreaService.queryByCondition(abnormalArea);
      List<AbnormalArea> list1 = new ArrayList<>();
      ExportExcel<AbnormalArea> ex = new ExportExcel<>();
      String[] headers = {"地区代码", "所在省", "所在市", "所在县区", "地区名称", "修改时间", "所属反洗钱组织名单类型"};
      String[] filedNames = {"code", "provnm", "citynm", "countynm", "name", "updatetime", "type"};

      for (AbnormalArea abnormalArea1 : list) {
        String type = abnormalArea1.getType();
        DictionaryCode dictionaryCode = new DictionaryCode();
        dictionaryCode.setDictcode("amlorganiz");
        dictionaryCode.setCode(type);
        List<DictionaryCode> dictionaryCodes = dictionaryService.queryAllDict(dictionaryCode);
        String typeName = type;
        if (!dictionaryCodes.isEmpty()) {
          typeName = dictionaryCodes.get(0).getName();
        }
        abnormalArea1.setType(typeName);
        list1.add(abnormalArea1);
      }
      OutputStream out = resp.getOutputStream();
      ex.exportExcel("异常地区代码", headers, list1, filedNames, out, null);
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
