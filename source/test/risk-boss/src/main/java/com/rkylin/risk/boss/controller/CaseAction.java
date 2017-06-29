package com.rkylin.risk.boss.controller;

import com.rkylin.risk.boss.util.ExportExcel;
import com.rkylin.risk.core.Constants;
import com.rkylin.risk.core.entity.Case;
import com.rkylin.risk.core.service.CaseService;
import com.rkylin.risk.core.service.DubioustxnService;
import com.rkylin.risk.core.service.RiskEventService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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

/**
 * Created by 201507270241 on 2015/9/23.
 */
@Controller
@RequestMapping("case")
@Slf4j
public class CaseAction {
    private static final String CASETYPE = "casetype";
    @Resource
    private DubioustxnService dubioustxnService;
    @Resource
    private RiskEventService riskEventService;
    @Resource
    private CaseService caseService;

    @RequestMapping("toQueryCase")
    public ModelAndView toQueryCase() {
        return new ModelAndView("case/caseQuery");
    }

    @RequestMapping("toAjaxGetCaseDetail")
    public ModelAndView getCaseDetail(@RequestParam Integer id, String casetype) {
        ModelAndView view = new ModelAndView("case/caseDetail");
        view.addObject(CASETYPE, casetype);
        if ("0".equals(casetype)) {
            //可疑交易
            view.addObject("dubioustxnList", dubioustxnService.queryByCaseId(id));
        } else {
            //风险事件
            view.addObject("riskEventList", riskEventService.queryByCaseId(id));
        }
        return view;
    }

    @RequestMapping("exportCaseExcel")
    public void exportCaseExcel(HttpServletResponse response, @ModelAttribute Case cases,
                                String createtimebeg, String createtimeend) {
        DateTimeFormatter dtFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime start = StringUtils.isEmpty(createtimebeg) ? null
                : DateTime.parse(createtimebeg, dtFormat);
        DateTime end = StringUtils.isEmpty(createtimeend) ? null
                : DateTime.parse(createtimeend, dtFormat);

        response.setContentType(Constants.CONTENT_TYPE);
        try {
            response.addHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode("案例.xls", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.info("coding exception", e);
        }

        List<Case> casesList = caseService.exportCaseExcel(cases, start, end);

        String[] headers = {"案例名称", "案例类型", "案例描述", "生成时间", "创建人姓名",
                "状态", "主键"};
        String[] filedNames = {"casename", CASETYPE, "casedesc", "createtime",
                "operatorname", "status", "id"};

        ExportExcel<Case> export = new ExportExcel<>();
        try {
            OutputStream out = response.getOutputStream();
            export.exportExcel("交易评分", headers, casesList, filedNames, out, null);
        } catch (FileNotFoundException e) {
            log.info(e.getMessage(), e);
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }

    }
}
