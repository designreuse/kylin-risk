package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.LoanFundsRepayYearDao;
import com.rkylin.risk.core.entity.LoanFundsRepayYear;
import com.rkylin.risk.core.service.LoanFundsRepayYearService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2017-1-3 17:44
 */
@Service
public class LoanFundsRepayYearServiceImpl implements LoanFundsRepayYearService {
    @Resource
    private LoanFundsRepayYearDao loanFundsRepayYearDao;

    @Override
    public LoanFundsRepayYear queryOne(Integer id) {
        return loanFundsRepayYearDao.queryOne(id);
    }

    @Override
    public List<LoanFundsRepayYear> queryAll() {
        return loanFundsRepayYearDao.queryAll();
    }

    @Override
    public List<LoanFundsRepayYear> queryLoanSales() {
        return loanFundsRepayYearDao.queryLoanSales();
    }

    @Override
    public void insert(LoanFundsRepayYear loanFundsRepayYear) {
        loanFundsRepayYearDao.insert(loanFundsRepayYear);
    }

    @Override
    public void delete(Integer id) {
        loanFundsRepayYearDao.delete(id);
    }

    @Override
    public void update(LoanFundsRepayYear loanFundsRepayYear) {
        loanFundsRepayYearDao.update(loanFundsRepayYear);
    }

    @Override
    public void batchUpdateByType(List<LoanFundsRepayYear> loanFundsRepayYearList) {
        loanFundsRepayYearDao.batchUpdateByType(loanFundsRepayYearList);
    }

    @Override
    public void batchUpdateById(List<LoanFundsRepayYear> loanFundsRepayYearList) {
        loanFundsRepayYearDao.batchUpdateById(loanFundsRepayYearList);
    }
}
