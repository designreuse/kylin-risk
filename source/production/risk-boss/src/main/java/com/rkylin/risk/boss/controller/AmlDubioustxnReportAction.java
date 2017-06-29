package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.dto.AmlDubiousTxnExportBean;
import com.rkylin.risk.core.entity.AmlDubiousTxn;
import com.rkylin.risk.core.service.AmlDubiousTxnService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * Created by 201508031790 on 2015/9/25.
 */
@Controller
@RequestMapping("amldubioustxnreport")
@Slf4j
public class AmlDubioustxnReportAction {

  @Resource
  private AmlDubiousTxnService amlDubiousTxnService;

  @RequestMapping("toQueryAmlDubioustxnReport")
  public ModelAndView toQuerySimpleBill() {
    return new ModelAndView("amlDubiousTxnReport/amlDubiousTxnReportQuery");
  }

  @RequestMapping(value = "exportAmlTxn")
  public void exportExcel(HttpServletRequest req, HttpServletResponse resp,
      @ModelAttribute AmlDubiousTxn adt, String warndatebeg, String warndateend) {
    resp.setContentType("application/x-download");
    try {
      resp.addHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode("反洗钱可疑交易明细.xls", "UTF-8"));
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
    DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
    LocalDate start =
        StringUtils.isEmpty(warndatebeg) ? null : LocalDate.parse(warndatebeg, dtFormat);
    LocalDate end =
        StringUtils.isEmpty(warndateend) ? null : LocalDate.parse(warndateend, dtFormat);
    List<Map> maplist = amlDubiousTxnService.queryByCondition(adt, start, end);
    List<AmlDubiousTxnExportBean> dataset = new ArrayList<>();
    ExportExcel<AmlDubiousTxnExportBean> ex = new ExportExcel<>();
    String[] headers = {"客户编号", "客户名称", "预警编号", "交易笔数", "风险等级", "预警日期", "规则编号", "处理意见", "来源"};
    String[] filedNames =
        {"customnum", "customname", "warnnum", "txncount", "risklevel", "warndate", "ruleid",
            "dealopinion", "source"};

    if (!maplist.isEmpty()) {
      for (Map map : maplist) {
        AmlDubiousTxnExportBean adt1 = new AmlDubiousTxnExportBean();
        adt1.setCustomnum((String) map.get("customnum"));
        adt1.setCustomname((String) map.get("customname"));
        adt1.setWarnnum((String) map.get("warnnum"));
        adt1.setTxncount((String) map.get("txncount"));
        adt1.setTxnaccount(map.get("txnaccount").toString());
        adt1.setRisklevel((String) map.get("risklevel"));
        adt1.setWarndate(map.get("warndate").toString());
        adt1.setRuleid(map.get("ruleid").toString());
        adt1.setDealopinion((String) map.get("dealopinion"));
        adt1.setSource((String) map.get("source"));
        dataset.add(adt1);
      }
    }
    try {
      OutputStream out = resp.getOutputStream();
      ex.exportExcel("反洗钱可疑交易明细", headers, dataset, filedNames, out, null);
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
