package com.rkylin.risk.core.dao.impl;

import com.rkylin.risk.core.dao.LoanFundsRepayYearDao;
import com.rkylin.risk.core.entity.LoanFundsRepayYear;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wanglingzhi
 * @Date 2017-1-3 17:20
 */
@Repository("loanFundsRepayYearDao")
public class LoanFundsRepayYearDaoImpl extends BaseDaoImpl<LoanFundsRepayYear> implements
        LoanFundsRepayYearDao {
    @Override
    public LoanFundsRepayYear queryOne(Integer id) {
        return super.get(id);
    }

    @Override
    public List<LoanFundsRepayYear> queryAll() {
        return super.selectAllList();
    }

    @Override
    public List<LoanFundsRepayYear> queryLoanSales() {
        return super.selectList("queryLoanSales");
    }

    @Override
    public void insert(LoanFundsRepayYear loanFundsRepayYear) {
        super.add(loanFundsRepayYear);
    }

    @Override
    public void delete(Integer id) {
        super.del(id);
    }

    @Override
    public void update(LoanFundsRepayYear loanFundsRepayYear) {
        super.modify(loanFundsRepayYear);
    }

    @Override
    public void batchUpdateByType(List<LoanFundsRepayYear> loanFundsRepayYearList) {
        super.modify("updateBatchByType", loanFundsRepayYearList);
    }

    @Override
    public void batchUpdateById(List<LoanFundsRepayYear> loanFundsRepayYearList) {
        super.modify("updateBatchById", loanFundsRepayYearList);
    }


}
