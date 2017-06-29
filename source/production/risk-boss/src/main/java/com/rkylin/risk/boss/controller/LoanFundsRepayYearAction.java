package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.LoanFundsLimit;
import com.rkylin.risk.core.entity.LoanFundsRepayYear;
import com.rkylin.risk.core.service.LoanFundsLimitService;
import com.rkylin.risk.core.service.LoanFundsRepayYearService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2016-12-13 15:58
 */
@RequestMapping("loanrepayyear")
@Controller
public class LoanFundsRepayYearAction {
    @Resource
    private LoanFundsRepayYearService  loanFundsRepayYearService;

    @Resource
    private LoanFundsLimitService loanFundsLimitService;

    @RequestMapping("toQueryLoanRepayYear")
    public ModelAndView toQueryLoanYear() {
        ModelAndView view = new ModelAndView("loan/loanRepayYearQuery");
        List<LoanFundsRepayYear> loanFundsRepaySalesList = loanFundsRepayYearService.queryLoanSales();
        view.addObject("loanFundsRepaySales", loanFundsRepaySalesList);
        List<LoanFundsRepayYear> loanFundsRepayYearList = loanFundsRepayYearService.queryAll();
        view.addObject("loanFundsRepayYears", loanFundsRepayYearList);
        List<LoanFundsLimit> loanFundsLimits = loanFundsLimitService.queryRepayYears();
        view.addObject("loanFundsLimits", loanFundsLimits);
        return view;
    }

}
