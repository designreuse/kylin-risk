package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.DubiousExportBean;
import com.rkylin.risk.core.entity.Dubioustxn;
import com.rkylin.risk.core.entity.Riskrule;
import com.rkylin.risk.core.entity.Customerinfo;
import com.rkylin.risk.core.entity.Order;
import com.rkylin.risk.core.service.CustomerinfoService;
import com.rkylin.risk.core.service.DubioustxnService;
import com.rkylin.risk.core.service.OrderService;
import com.rkylin.risk.core.service.RiskruleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by v-wangwei on 2015/9/17 0017.
 */
@Controller
@RequestMapping("dubioustxn")
@Slf4j
public class DubioustxnAction {
    @Resource
    DubioustxnService dubioustxnService;
    @Resource
    RiskruleService riskruleService;
    @Resource
    CustomerinfoService customerinfoService;
    @Resource
    OrderService orderService;

    @RequestMapping("toQueryDubioustxn")
    public ModelAndView toQueryDubioustxn() {
        ModelAndView view = new ModelAndView("dubioustxn/dubioustxnQuery");
        return view;
    }

    @RequestMapping("toQueryDubioustxnCondition")
    public ModelAndView toQueryDubioustxnCondition() {
        ModelAndView view = new ModelAndView("dubioustxn/dubioustxnQueryCondition");
        return view;
    }

    @RequestMapping("getRiskruleDetail")
    public ModelAndView queryDetail(@ModelAttribute Dubioustxn dubioustxn,
                                    @RequestParam Integer ids, String type) {
        Riskrule riskrule = riskruleService.queryOne(ids);
        ModelAndView view = new ModelAndView("dubioustxn/riskRuleDetail");
        view.addObject("type", type);
        view.addObject("riskrule", riskrule);
        //dealType  modify---->修改  query---->查询
        view.addObject("dubioustxn", dubioustxn);
        return view;
    }

    @RequestMapping("queryCustomerDetail")
    public ModelAndView queryDetail(@RequestParam String ids, Integer simpleBillId, String type) {
        Customerinfo customer = customerinfoService.queryOne(ids);
        if (customer == null) {
            customer = new Customerinfo();
        }
        List<Order> simpleBillList = orderService.queryByAmlDub(simpleBillId);
        ModelAndView view = new ModelAndView("dubioustxn/queryCustomerDetail");
        view.addObject("type", type);
        view.addObject(customer);
        view.addObject(simpleBillList);
        return view;
    }

    @RequestMapping("exportDubioustxnExcel")
    public void exportDubioustxnExcel(HttpServletResponse response, String productall,
                                      String[] products, String productnull, String customnum,
                                      String warnstatus, String warndates, String warndatee) {
        DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime beginWarnDate = StringUtils.isEmpty(warndates) ? null
                : DateTime.parse(warndates, dtFormat);
        DateTime endWarnDate = StringUtils.isEmpty(warndatee) ? null
                : DateTime.parse(warndatee, dtFormat);

        response.setContentType(Constants.CONTENT_TYPE);
        try {
            response.addHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode("可疑交易.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.info("coding exception", e);
        }

        List<Map> listmap = dubioustxnService.exportDubiousExcel(productall, products,
                productnull, customnum, warnstatus, beginWarnDate, endWarnDate);

        String[] headers = {"预警日期", "客户编号", "客户名称", "交易金额(元)",
                "预警状态", "风险等级", "风险规则", "处理结果"};
        String[] filedNames = {"warndate", "customnum", "customname", "txnaccount",
                "warnstatus", "risklevel", "rulename", "dealopinion"};

        List<DubiousExportBean> listdubioustxn = new ArrayList<DubiousExportBean>();
        if (listmap != null && !listmap.isEmpty()) {
            for (Map map : listmap) {
                DubiousExportBean deb = new DubiousExportBean();
                deb.setWarndate((Date) map.get("warndate"));
                deb.setCustomnum((String) map.get("customnum"));
                deb.setCustomname((String) map.get("customname"));
                deb.setTxnaccount((BigDecimal) map.get("txnaccount"));
                deb.setWarnstatus((String) map.get("warnstatus"));
                deb.setRisklevel((String) map.get("risklevel"));
                deb.setRulename((String) map.get("rulename"));
                deb.setDealopinion((String) map.get("dealopinion"));
                listdubioustxn.add(deb);
            }
        }

        ExportExcel<DubiousExportBean> export = new ExportExcel<DubiousExportBean>();
        try {
            OutputStream out = response.getOutputStream();
            export.exportExcel("可疑交易", headers, listdubioustxn, filedNames, out, "yyyy-MM-dd");
        } catch (FileNotFoundException e) {
            log.info(e.getMessage(), e);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }

    }
}
