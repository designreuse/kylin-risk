package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.RiskEvent;
import com.rkylin.risk.core.service.RiskEventService;
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
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by v-cuixiaofang on 2015/9/21.
 */
@Controller
@RequestMapping("riskEvent")
@Slf4j
public class RiskEventAction {

    @Resource
    private RiskEventService riskEventService;

    /**
     * 跳转到新增风险事件页面
     */
    @RequestMapping("toQueryRiskEventAdd")
    public ModelAndView queryRiskEvent() {
        ModelAndView modelAndView = new ModelAndView("riskEvent/riskEventAddQuery");
        return modelAndView;
    }

    /**
     * 跳转到风险事件查询页面
     */
    @RequestMapping("toQueryRiskEvent")
    public ModelAndView toQueryRiskEvent() {
        return new ModelAndView("riskEvent/riskEventQuery");
    }

    @RequestMapping("toAjaxAddRiskEvent")
    public ModelAndView toAddRiskEvent(@RequestParam String billIds, String cusid, String cusnum) {
        ModelAndView view = new ModelAndView("riskEvent/riskEventAdd");
        view.addObject("billIds", billIds);
        view.addObject("cusid", cusid);
        view.addObject("cusnum", cusnum);
        return view;
    }

    @RequestMapping("toAjaxRiskEventDetail")
    public ModelAndView getRiskEventById(@RequestParam Integer ids, String dealType) {
        RiskEvent riskEvent1 = riskEventService.queryOne(ids);
        ModelAndView view = new ModelAndView("riskEvent/riskEventDetail");
        //dealType  modify---->修改  query---->查询
        view.addObject("dealType", dealType);
        view.addObject("riskEvent", riskEvent1);
        return view;
    }

    @RequestMapping("exportRiskEventExcel")
    public void exportRiskEventExcel(HttpServletResponse response,
                                     @ModelAttribute RiskEvent riskEvent) {
        response.setContentType(Constants.CONTENT_TYPE);
        try {
            response.addHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode("风险事件.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.info("coding exception", e);
        }

        List<RiskEvent> riskEvents = riskEventService.exportRiskEventExcel(riskEvent);

        String[] headers = {"主键", "客户名称", "事件名称", "事件类型", "处理结果",
                "操作人", "操作时间"};
        String[] filedNames = {"id", "customerid", "eventname", "eventtype",
                "eventresult", "operatorname", "createtime"};

        ExportExcel<RiskEvent> export = new ExportExcel<RiskEvent>();

        try {
            OutputStream out = response.getOutputStream();
            export.exportExcel("风险事件", headers, riskEvents, filedNames, out, null);
        } catch (FileNotFoundException e) {
            log.info(e.getMessage(), e);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }
}
