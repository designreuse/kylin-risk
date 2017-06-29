package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.LoanFundsLimitDao;
import com.rkylin.risk.core.entity.LoanFundsLimit;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2017-1-3 17:15
 */
@Repository("loanFundsLimitDao")
public class LoanFundsLimitDaoImpl extends BaseDaoImpl<LoanFundsLimit> implements
        LoanFundsLimitDao {
    @Override
    public LoanFundsLimit queryOne(Integer id) {
        return super.get(id);
    }

    @Override
    public List<LoanFundsLimit> queryAll() {
        return super.selectAllList();
    }

    @Override
    public List<LoanFundsLimit> queryLoanSales() {
        return super.selectList("queryLoanSales");
    }

    @Override
    public List<LoanFundsLimit> queryRepayYears() {
        return super.selectList("queryRepayYears");
    }

    @Override
    public void insert(LoanFundsLimit loanFundsLimit) {
        super.add(loanFundsLimit);
    }

    @Override
    public void delete(Integer id) {
        super.del(id);
    }

    @Override
    public void update(LoanFundsLimit loanFundsLimit) {
        super.modify(loanFundsLimit);
    }

    @Override
    public void batchUpdateSalesByType(List<LoanFundsLimit> loanFundsLimitList) {
        super.modify("updateBatchSalesByType", loanFundsLimitList);
    }

    @Override
    public void batchUpdateLimitById(List<LoanFundsLimit> loanFundsLimitList) {
        super.modify("updateBatchLimitById", loanFundsLimitList);
    }

}
