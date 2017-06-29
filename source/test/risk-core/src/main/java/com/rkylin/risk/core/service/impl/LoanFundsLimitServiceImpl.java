package com.rkylin.risk.core.service.impl;

import com.rkylin.risk.core.dao.LoanFundsLimitDao;
import com.rkylin.risk.core.entity.LoanFundsLimit;
import com.rkylin.risk.core.service.LoanFundsLimitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2017-1-3 17:40
 */
@Service
public class LoanFundsLimitServiceImpl implements LoanFundsLimitService {
    @Resource
    private LoanFundsLimitDao loanFundsLimitDao;

    @Override
    public LoanFundsLimit queryOne(Integer id) {
        return loanFundsLimitDao.queryOne(id);
    }

    @Override
    public List<LoanFundsLimit> queryAll() {
        return loanFundsLimitDao.queryAll();
    }

    @Override
    public List<LoanFundsLimit> queryLoanSales() {
        return loanFundsLimitDao.queryLoanSales();
    }

    @Override
    public List<LoanFundsLimit> queryRepayYears() {
        return loanFundsLimitDao.queryRepayYears();
    }

    @Override
    public void insert(LoanFundsLimit loanFundsLimit) {
        loanFundsLimitDao.insert(loanFundsLimit);
    }

    @Override
    public void delete(Integer id) {
        loanFundsLimitDao.delete(id);
    }

    @Override
    public void update(LoanFundsLimit loanFundsLimit) {
        loanFundsLimitDao.update(loanFundsLimit);
    }

    @Override
    public void batchUpdateSalesByType(List<LoanFundsLimit> loanFundsLimitList) {
        loanFundsLimitDao.batchUpdateSalesByType(loanFundsLimitList);
    }

    @Override
    public void batchUpdateLimitById(List<LoanFundsLimit> loanFundsLimitList) {
        loanFundsLimitDao.batchUpdateLimitById(loanFundsLimitList);
    }
}
