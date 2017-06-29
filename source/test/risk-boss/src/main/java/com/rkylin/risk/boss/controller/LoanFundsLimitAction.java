package com.rkylin.risk.boss.controller;

import com.rkylin.risk.core.entity.LoanFundsLimit;
import com.rkylin.risk.core.service.LoanFundsLimitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2016-12-13 15:58
 */
@RequestMapping("loanfundslimit")
@Controller
public class LoanFundsLimitAction {
    @Resource
    private LoanFundsLimitService loanFundsLimitService;

    @RequestMapping("toQueryLoanFundsLimit")
    public ModelAndView toQueryLoanFundsLimit() {
        ModelAndView view = new ModelAndView("loan/loanFundsLimitQuery");
        List<LoanFundsLimit> loanFundsLimitSales = loanFundsLimitService.queryLoanSales();
        view.addObject("loanFundsLimitSales", loanFundsLimitSales);
        if(loanFundsLimitSales!=null && loanFundsLimitSales.size()>0){
            view.addObject("profilerate",loanFundsLimitSales.get(0).getProfilerate());
        }
        List<LoanFundsLimit> loanFundsLimitLists = loanFundsLimitService.queryAll();
        view.addObject("loanFundsLimitLists", loanFundsLimitLists);
        return view;
    }

}
