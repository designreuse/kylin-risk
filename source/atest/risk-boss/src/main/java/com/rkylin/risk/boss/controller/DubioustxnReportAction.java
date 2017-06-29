package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.dto.DubioustxnExportBean;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 201508031790 on 2015/9/23.
 */
@Slf4j
@Controller
@RequestMapping("dubioustxnreport")
public class DubioustxnReportAction {

  @Resource
  private OrderService orderService;

  @RequestMapping("toQueryDubioustxnReport")
  public ModelAndView toQuerySimpleBill() {

    return new ModelAndView("dubioustxnReport/dubioustxnReportQuery");
  }

  @RequestMapping(value = "exportDubioustxnExcel")
  public void exportExcel(HttpServletResponse resp, @ModelAttribute Order order,
      String customername, String risklevel, String timebegin, String timeend) {

    resp.setContentType("application/x-download");
    try {
      resp.addHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode("可疑交易明细.xls", "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      log.info("编码异常", e);
    }
    DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTime start = StringUtils.isEmpty(timebegin) ? null : DateTime.parse(timebegin, dtFormat);
    DateTime end = StringUtils.isEmpty(timeend) ? null : DateTime.parse(timeend, dtFormat);

    List<Map> maplist = orderService.queryByCondition(order, customername, risklevel, start, end);

    ExportExcel<DubioustxnExportBean> ex = new ExportExcel<DubioustxnExportBean>();
    String[] headers = {"客户编号", "客户名称", "内部订单号", "商户编号", "身份证号码", "金额", "交易时间", "预警级别", "产品类型"};
    String[] filedNames =
        {"userid", "customername", "txnid", "merchid", "dbtridnum", "txnamt", "txntime",
            "risklevel", "prodid"};
    //从数据库中读取数据
    List<DubioustxnExportBean> dataset = new ArrayList<DubioustxnExportBean>();
    if (!maplist.isEmpty()) {
      for (Map map : maplist) {
        DubioustxnExportBean exportbean1 = new DubioustxnExportBean();

        exportbean1.setUserid((String) map.get("userid"));
        exportbean1.setCustomername((String) map.get("customername"));
        exportbean1.setTxnid((String) map.get("orderId"));
        exportbean1.setMerchid((String) map.get(null));
        exportbean1.setDbtridnum((String) map.get(null));
        exportbean1.setTxnamt(map.get("amount").toString());
        exportbean1.setTxntime(
            map.get("orderDate") + " " + map.get("orderTime"));
        exportbean1.setRisklevel((String) map.get("risklevel"));
        exportbean1.setProdid((String) map.get("productId"));
        dataset.add(exportbean1);
      }
    }

    try {
      OutputStream out = resp.getOutputStream();
      ex.exportExcel("可疑交易明细", headers, dataset, filedNames, out, null);
      out.close();
    } catch (FileNotFoundException e) {
      log.info(e.getMessage(), e);
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    } catch (Exception e) {
      log.info(e.getMessage(), e);
    }
  }
}
