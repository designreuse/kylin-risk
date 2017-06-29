package com.rkylin.risk.boss.restController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkylin.risk.boss.page.DataTableResultDTO;
import com.rkylin.risk.core.entity.LoanFundsLimit;
import com.rkylin.risk.core.entity.LoanFundsRepayYear;
import com.rkylin.risk.core.service.LoanFundsLimitService;
import com.rkylin.risk.core.service.LoanFundsRepayYearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author wanglingzhi
 * @Date 2017-1-4 11:50
 */
@RestController
@RequestMapping("api/1/loanfundslimit")
@Slf4j
public class LoanFundsLimitRestAction {

    @Resource
    private LoanFundsLimitService loanFundsLimitService;

    @Resource
    private LoanFundsRepayYearService loanFundsRepayYearService;

    @Resource
    private ObjectMapper objectMapper;

    @RequestMapping("batchUpdateFundsLimit")
    public LoanFundsLimit batchUpdateFundsLimit(@RequestParam String loanFundSales, @RequestParam String loanFundEbitdas, @RequestParam String profilerate, @RequestParam String loanFundLimits) {
        String loanSales[] = loanFundSales.split(",");
        String loanEbitdas[] = loanFundEbitdas.split(",");
        List<LoanFundsLimit> LoanFundsSalesList = new ArrayList<>();
        if (!loanFundSales.equals("") && loanSales.length == loanEbitdas.length) {
            for (int i = 0; i < loanSales.length; i++) {
                String saleArray[] = loanSales[i].split("_");
                String ebitdasArray[] = loanEbitdas[i].split("_");
                LoanFundsLimit loanFundsSales = new LoanFundsLimit();
                loanFundsSales.setCompanytype(saleArray[0]);
                loanFundsSales.setAnnualsales(Integer.parseInt(saleArray[1]));
                loanFundsSales.setEbitda(Double.parseDouble(ebitdasArray[1]));
                loanFundsSales.setProfilerate(profilerate);
                LoanFundsSalesList.add(loanFundsSales);
            }

            loanFundsLimitService.batchUpdateSalesByType(LoanFundsSalesList);
        }
        if(!loanFundLimits.equals("")) {
            String loanLimits[] = loanFundLimits.split(",");
            List<LoanFundsLimit> loanFundsLimitList = new ArrayList<>();
            for (int i = 0; i < loanLimits.length; i++) {
                String limitArray[] = loanLimits[i].split("_");
                LoanFundsLimit loanFundsLimit = new LoanFundsLimit();
                loanFundsLimit.setLoanlimit(Double.parseDouble(limitArray[1]));
                loanFundsLimit.setId(Integer.parseInt(limitArray[0]));
                loanFundsLimitList.add(loanFundsLimit);
            }
            loanFundsLimitService.batchUpdateLimitById(loanFundsLimitList);
        }
        return new LoanFundsLimit();
    }

    @RequestMapping("queryRepayyear")
    public void repayYearsQuery( HttpServletResponse response) throws IOException {
        DataTableResultDTO resultDTO = new  DataTableResultDTO();
        List<LoanFundsRepayYear> loanFundsRepayYearList = loanFundsRepayYearService.queryAll();
        resultDTO.setData(loanFundsRepayYearList);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.write(objectMapper.writeValueAsString(resultDTO));
        }
        response.setContentType("application/json");
    }


    }


