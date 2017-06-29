package com.rkylin.risk.boss.restController;


import com.rkylin.risk.core.entity.LoanFundsRepayYear;
import com.rkylin.risk.core.service.LoanFundsRepayYearService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author wanglingzhi
 * @Date 2017-1-4 11:50
 */
@RestController
@RequestMapping("api/1/loanrepayyear")
@Slf4j
public class LoanRepayYearRestAction {

    @Resource
    private LoanFundsRepayYearService loanFundsRepayYearService;

    @RequestMapping("batchUpdateRepayYear")
    public LoanFundsRepayYear batchUpdateRepayYear(@RequestParam String loanFundSales, @RequestParam String loanFundYears) {
        String loanSales[] = loanFundSales.split(",");
        List<LoanFundsRepayYear> loanFundsRepaySalesList = new ArrayList<>();
        for (int i = 0; i < loanSales.length; i++) {
            String saleArray[] = loanSales[i].split("_");
            LoanFundsRepayYear loanFundsRepayYear = new LoanFundsRepayYear();
            loanFundsRepayYear.setCompanytype(saleArray[0]);
            loanFundsRepayYear.setAnnualsaleslower(Integer.parseInt(saleArray[1]));
            if (saleArray.length == 3) {
                loanFundsRepayYear.setAnnualsalesupper(Integer.parseInt(saleArray[2]));
            }
            loanFundsRepaySalesList.add(loanFundsRepayYear);
        }

        loanFundsRepayYearService.batchUpdateByType(loanFundsRepaySalesList);

        if(!loanFundYears.equals("")) {
            String[] loanYears = loanFundYears.split(",");
            List<LoanFundsRepayYear> loanFundsRepayYearList = new ArrayList<>();
            for (int i = 0; i < loanYears.length; i++) {
                String yearArray[] = loanYears[i].split("_");
                LoanFundsRepayYear loanFundsRepayYear = new LoanFundsRepayYear();
                loanFundsRepayYear.setId(Integer.parseInt(yearArray[0]));
                loanFundsRepayYear.setRepayyear(Float.parseFloat(yearArray[1]));
                loanFundsRepayYearList.add(loanFundsRepayYear);
            }
            loanFundsRepayYearService.batchUpdateById(loanFundsRepayYearList);
        }
        return new LoanFundsRepayYear();
    }
}


